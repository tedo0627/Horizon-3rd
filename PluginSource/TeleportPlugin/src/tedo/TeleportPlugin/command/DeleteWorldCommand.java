package tedo.TeleportPlugin.command;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import tedo.TeleportPlugin.Main;

public class DeleteWorldCommand extends Command{

	public HashMap<String, String> data = new HashMap<String, String>();

	public DeleteWorldCommand() {
		super("deleteworld", "ワールドを削除するコマンド");

		this.setPermission("op");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("ワールド名", CommandParameter.ARG_TYPE_STRING, false)
		});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length < 1) {
			sender.sendMessage("§a>>§b/deleteworld [ワールド名]");
			return false;
		}
		if (!sender.getServer().isLevelLoaded(args[0])) {
			sender.sendMessage("§a>>§bワールド " + args[0] + " は読み込まれていません");
			return false;
		}
		if (sender.getServer().getDefaultLevel().getName().equals(args[0])) {
			sender.sendMessage("§a>>§bデフォルトのワールドを削除することはできません");
			return false;
		}
		String name = sender.getName();
		if (this.data.containsKey(name)) {
			if (this.data.get(name).equals(args[0])) {
				this.data.remove(name);
				Level level = Main.main.getServer().getLevelByName(args[0]);
				Map<Long, Player> players = level.getPlayers();
				for (Player player : players.values()) {
					player.teleport(player.getServer().getDefaultLevel().getSafeSpawn());
					player.sendMessage("§a>>§bワールドが削除されたため、テレポートされました");
				}
				level.unload();
				String path = level.getFolderName();
				File file = new File(sender.getServer().getFilePath() + "worlds/" + path + "/");
				remove(file);
				sender.sendMessage("§a>>§bワールド " + args[0] + " を削除しました");
				return true;
			} else {
				this.data.put(name, args[0]);
				sender.sendMessage("§a>>§b本当にワールド " + args[0] + " を削除しますか?");
				sender.sendMessage("§a>>§b削除するならば、再度同じコマンドを入力してください");
				return true;
			}
		} else {
			if (Main.main.getServer().getDefaultLevel().getName().equals(args[0])) {
				sender.sendMessage("§a>>§bデフォルトのワールドは削除できません");
				return false;
			}
			this.data.put(name, args[0]);
			sender.sendMessage("§a>>§b本当にワールド " + args[0] + " を削除しますか?");
			sender.sendMessage("§a>>§b削除するならば、再度同じコマンドを入力してください");
			return true;
		}
	}

	public void remove(File file) {
		if (file.isDirectory()) {
			for (File c : file.listFiles()) {
				remove(c);
			}
		}
		file.delete();
	}
}
