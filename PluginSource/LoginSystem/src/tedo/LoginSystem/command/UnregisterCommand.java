package tedo.LoginSystem.command;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.nbt.tag.CompoundTag;
import tedo.LoginSystem.LoginSystem;
import tedo.LoginSystem.Status;

public class UnregisterCommand extends Command{

	public LoginSystem main;

	public UnregisterCommand(LoginSystem main) {
		super("unregister", "アカウント削除コマンド");

		this.setPermission("op");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_TARGET, false),
		});

		this.main = main;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length < 1) {
				sender.sendMessage("§a>>§b/unregister [プレイヤー名]");
				return false;
			}
		}
		String name = args[0].toLowerCase();
		if (new File(this.main.getServer().getDataPath() + "players/" + name + ".dat").exists()) {
			Player player = this.main.getServer().getPlayer(name);
			if (player != null && player.getName().toLowerCase().equals(name)) {
				Status.unRegister(player);
				player.kick("§cログインデーターを削除しました", false);
			} else {
				CompoundTag nbt = this.main.getServer().getOfflinePlayerData(name);
				Status.unRegister(nbt);
				this.main.getServer().saveOfflinePlayerData(name, nbt);
			}
			sender.sendMessage("§a>>§b" + args[0] + "のログインデーターを削除しました");
			return true;
		}else{
			sender.sendMessage("§a>>§b" + args[0] + "は存在しません");
			return false;
		}
	}

}
