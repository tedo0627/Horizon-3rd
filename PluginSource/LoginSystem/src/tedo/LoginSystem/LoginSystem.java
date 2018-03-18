package tedo.LoginSystem;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.ContainerSetSlotPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.DropItemPacket;
import cn.nukkit.network.protocol.RemoveBlockPacket;
import cn.nukkit.network.protocol.UseItemPacket;
import cn.nukkit.plugin.PluginBase;
import tedo.LoginSystem.command.LoginCommand;
import tedo.LoginSystem.command.RegisterCommand;
import tedo.LoginSystem.command.UnregisterCommand;

public class LoginSystem extends PluginBase implements Listener{

	public HashMap<Player, Boolean> register = new HashMap<Player, Boolean>();
	public HashMap<Player, Boolean> login = new HashMap<Player, Boolean>();

	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);

		this.getServer().getCommandMap().register("register", new RegisterCommand(this));
		this.getServer().getCommandMap().register("login", new LoginCommand(this));
		this.getServer().getCommandMap().register("unregister", new UnregisterCommand(this));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (Status.isRegister(player)) {
			if (Status.hasLogin(player)) {
				player.sendMessage("§a>>§b貴方はログインに成功しました");
				player.sendMessage("§a>>§b貴方の端末の情報が変わっています");
				player.sendMessage("§a>>§b/login [パスワード] でログインしてください");
				player.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_NO_AI, true);
				this.login.put(player, true);
			}else{
				player.sendMessage("§a>>§b貴方はログインに成功しました");
			}
		}else{
			player.sendMessage("§a>>§bこの鯖で遊ぶにはアカウントを登録する必要があります");
			player.sendMessage("§a>>§b/register [パスワード] [パスワード] で登録してください");
			player.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_NO_AI, true);
			this.register.put(player, true);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (this.register.containsKey(player)) {
			this.register.remove(player);
		}
		if (this.login.containsKey(player)) {
			this.login.remove(player);
		}
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (this.register.containsKey(player)) {
			String[] m = event.getMessage().split(" ");
			if (!(m[0].equals("/register"))) {
				event.setCancelled();
				player.sendMessage("§a>>§b/register [パスワード] [パスワード] でアカウントを登録してください");
			}
		} else if (this.login.containsKey(player)) {
			String[] m = event.getMessage().split(" ");
			if (!(m[0].equals("/login"))) {
				event.setCancelled();
				player.sendMessage("§a>>§b/login [パスワード] でログインしてください");
			}
		}
	}

	@EventHandler
	public void DataPacketReceive(DataPacketReceiveEvent event) {
		DataPacket pk = event.getPacket();
		if (pk instanceof UseItemPacket || pk instanceof RemoveBlockPacket || pk instanceof DropItemPacket || pk instanceof ContainerSetSlotPacket) {
			Player player = event.getPlayer();
			if (this.register.containsKey(player) || this.login.containsKey(player)) {
				event.setCancelled();
			}
		}
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		if (this.register.containsKey(player)) {
			event.setCancelled();
			player.sendMessage("§a>>§b/register [パスワード] [パスワード] でアカウントを登録してください");
		}else if (this.login.containsKey(player)) {
			event.setCancelled();
			player.sendMessage("§a>>§b/login [パスワード] でログインしてください");
		}
	}
}
