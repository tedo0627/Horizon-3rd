package tedo.TeleportPlugin.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;

public class HomeCommand extends Command{

	public HomeCommand() {
		super("home", "ホームポイント関連のコマンド");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("ホームポイントの名前", CommandParameter.ARG_TYPE_STRING, false),
		});
		this.addCommandParameters("add", new CommandParameter[]{
				new CommandParameter("add", false, new String[]{"add"}),
				new CommandParameter("ホームポイントの名前", CommandParameter.ARG_TYPE_STRING, false),
		});
		this.addCommandParameters("del", new CommandParameter[]{
				new CommandParameter("del", false, new String[]{"del"}),
				new CommandParameter("ホームポイントの名前", CommandParameter.ARG_TYPE_STRING, false),
		});
		this.addCommandParameters("list", new CommandParameter[]{
				new CommandParameter("list", false, new String[]{"list"}),
		});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールからそのコマンドを実行できません");
			return false;
		}
		if (args.length < 1) {
			sender.sendMessage("§a>>§b/home [ホームポイントの名前]");
			sender.sendMessage("§a>>§b/home add [ホームポイントの名前]");
			sender.sendMessage("§a>>§b/home del [ホームポイントの名前]");
			sender.sendMessage("§a>>§b/home list");
			return false;
		}
		Player player = (Player) sender;
		double x, y, z;
		Level level;
		CompoundTag nbt;
		switch (args[0]) {
			case "add":
				if (args.length < 2) {
					sender.sendMessage("§a>>§b/home add [ホームポイントの名前]");
					return false;
				}
				x = Double.parseDouble(String.format("%.1f", player.x));
				y = Double.parseDouble(String.format("%.1f", player.y));
				z = Double.parseDouble(String.format("%.1f", player.z));
				level = player.getLevel();
				nbt = new CompoundTag()
						.putDouble("x", x)
						.putDouble("y", y)
						.putDouble("z", z)
						.putString("level", level.getName());
				player.namedTag.getCompound("TeleportPlugin").putCompound(args[1], nbt);
				player.sendMessage("§a>>§bホームポイントを作成しました");
			break;

			case "del":
				if (args.length < 2) {
					sender.sendMessage("§a>>§b/home del [ホームポイントの名前]");
					return false;
				}
				if (!player.namedTag.getCompound("TeleportPlugin").contains(args[1])) {
					player.sendMessage("§a>>§bホームポイント " + args[1] + " は存在しません");
					return false;
				}
				player.namedTag.getCompound("TeleportPlugin").remove(args[1]);
				player.sendMessage("§a>>§bホームポイント " + args[1] + " を削除しました");
			break;

			case "list":
				if (player.namedTag.getCompound("TeleportPlugin").getTags().size() < 1) {
					player.sendMessage("§a>>§bホームポイントが作成されていません");
					return false;
				}
				player.sendMessage("§a>>§bホームポイント一覧");
				player.namedTag.getCompound("TeleportPlugin").getTags().forEach((name, tag) -> {
					CompoundTag t = (CompoundTag) tag;
					player.sendMessage("§a>>§b" + name + " | x : " + t.getDouble("x") + " y : " + t.getDouble("y") + " z : " + t.getDouble("z") + " | level : " + t.getString("level"));
				});
			break;

			default:
				if (!player.namedTag.getCompound("TeleportPlugin").contains(args[0])) {
					player.sendMessage("§a>>§bホームポイント " + args[0] + " は存在しません");
					return false;
				}
				CompoundTag pos = player.namedTag.getCompound("TeleportPlugin").getCompound(args[0]);
				x = pos.getDouble("x");
				y = pos.getDouble("y");
				z = pos.getDouble("z");
				String levelName = pos.getString("level");
				level = player.getServer().getLevelByName(levelName);
				player.teleport(new Position(x, y, z, level));
				player.sendMessage("§a>>§bテレポートしました");
			break;
		}
		return true;
	}
}
