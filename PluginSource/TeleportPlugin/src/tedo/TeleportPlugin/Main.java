package tedo.TeleportPlugin;

import java.io.File;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.PluginBase;
import tedo.TeleportPlugin.command.AllTpCommand;
import tedo.TeleportPlugin.command.DeleteWorldCommand;
import tedo.TeleportPlugin.command.GenerateCommand;
import tedo.TeleportPlugin.command.HomeCommand;
import tedo.TeleportPlugin.command.HubCommand;
import tedo.TeleportPlugin.command.SpawnCommand;
import tedo.TeleportPlugin.command.WarpCommand;
import tedo.TeleportPlugin.command.WorldListCommand;
import tedo.TeleportPlugin.command.WorldTeleportCommand;

public class Main extends PluginBase implements Listener {

	public static Main main;

	public HashMap<String, Position> warp = new HashMap<String, Position>();

	@Override
	public void onEnable() {
		Main.main = this;

		File file = new File(this.getServer().getFilePath() + "worlds/");
		for (File world : file.listFiles()) {
			if (world.isDirectory()) {
				String name = world.getName();
				this.getServer().loadLevel(name);
				Level level = this.getServer().getLevelByName(name);
				if (level == null) {
					break;
				}
				Position pos = level.getSafeSpawn();
				level.loadChunk((int) pos.x >> 4, (int) pos.z >> 4);
			}
		}

		this.getServer().getPluginManager().registerEvents(this, this);

		this.getServer().getCommandMap().register("TeleportPlugin", new AllTpCommand());
		this.getServer().getCommandMap().register("TeleportPlugin", new DeleteWorldCommand());
		this.getServer().getCommandMap().register("TeleportPlugin", new GenerateCommand());
		this.getServer().getCommandMap().register("TeleportPlugin", new HomeCommand());
		this.getServer().getCommandMap().register("TeleportPlugin", new HubCommand());
		this.getServer().getCommandMap().register("TeleportPlugin", new SpawnCommand());
		this.getServer().getCommandMap().register("TeleportPlugin", new WarpCommand());
		this.getServer().getCommandMap().register("TeleportPlugin", new WorldListCommand());
		this.getServer().getCommandMap().register("TeleportPlugin", new WorldTeleportCommand());
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.namedTag.contains("TeleportPlugin")) {
			player.namedTag.putCompound("TeleportPlugin", new CompoundTag());
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Block block = event.getBlock();
		if (!(block.getId() == 63 || block.getId() == 68)) {
			return;
		}
		double x = block.x;
		double y = block.y;
		double z = block.z;
		Level level = block.getLevel();
		BlockEntity sign = level.getBlockEntity(new Vector3(x, y, z));
		if (!(sign instanceof BlockEntitySign)) {
			return;
		}
		String[] text = ((BlockEntitySign) sign).getText();
		Player player = event.getPlayer();
		if (text[0].equals("[wtp]")) {
			Level l = getServer().getLevelByName(text[1]);
			if (l != null) {
				player.teleport(l.getSafeSpawn());
				player.sendPopup("§bテレポートしました");
				event.setCancelled();
			}
		}else if (text[0].equals("[warp]")) {
			if (this.warp.containsKey(text[1])) {
				player.teleport(this.warp.get(text[1]));
				player.sendPopup("§bテレポートしました");
				event.setCancelled();
			}else{
				player.sendPopup("§bそのワープポイントは存在しません");
				event.setCancelled();
			}
		}
	}
}
