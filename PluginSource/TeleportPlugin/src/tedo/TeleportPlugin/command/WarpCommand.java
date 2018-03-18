package tedo.TeleportPlugin.command;

import java.io.File;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import tedo.TeleportPlugin.Main;

public class WarpCommand extends Command{

	public Config config;
	public HashMap<String, Position> warp = new HashMap<String, Position>();

	@SuppressWarnings("unchecked")
	public WarpCommand() {
		super("warp", "ワープ関連のコマンド");

		this.getCommandParameters().clear();

		this.addCommandParameters("delfault", new CommandParameter[]{
				new CommandParameter("ワープポイントの名前", CommandParameter.ARG_TYPE_STRING, false)
		});

		this.addCommandParameters("add", new CommandParameter[]{
				new CommandParameter("add", false, new String[]{"add"}),
				new CommandParameter("ワープポイントの名前", CommandParameter.ARG_TYPE_STRING, false)
		});

		this.addCommandParameters("del", new CommandParameter[]{
				new CommandParameter("del", false, new String[]{"del"}),
				new CommandParameter("ワープポイントの名前", CommandParameter.ARG_TYPE_STRING, false)
		});

		this.addCommandParameters("list", new CommandParameter[]{
				new CommandParameter("list", false, new String[]{"list"})
		});

		Main.main.warp = this.warp;
		Main.main.getDataFolder().mkdirs();
		this.config = new Config(new File(Main.main.getDataFolder(), "warp.yml"), Config.YAML);
		this.config.getAll().forEach((name, pos) -> {
			HashMap<String, String> data = (HashMap<String, String>) pos;
			double x = Double.parseDouble(data.get("x"));
			double y = Double.parseDouble(data.get("y"));
			double z = Double.parseDouble(data.get("z"));
			Level level = Main.main.getServer().getLevelByName(data.get("level"));
			this.warp.put(name, new Position(x, y, z, level));
		});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールからそのコマンドを実行できません");
			return false;
		}
		if (args.length < 1) {
			sender.sendMessage("§a>>§b/warp [ワープポイントの名前]");
			if (sender.isOp()) {
				sender.sendMessage("§a>>§b/warp add [ワープポイントの名前]");
				sender.sendMessage("§a>>§b/warp del [ワープポイントの名前]");
			}
			sender.sendMessage("§a>>§b/warp list");
			return false;
		}
		Player player = (Player) sender;
		Position pos;
		switch (args[0]) {
			case "add":
				if (!player.hasPermission("op")) {
					player.sendMessage("§a>>§b貴方はこのコマンドを使う権限がありません");
					return false;
				}
				if (args.length < 2) {
					player.sendMessage("§a>>§b/warp add [ワープポイントの名前]");
					return false;
				}
				if (this.warp.containsKey(args[1])) {
					player.sendMessage("§a>>§bワープポイント " + args[1] + " は作成済みです");
					return false;
				}
				double x = Double.parseDouble(String.format("%.1f", player.x));
				double y = Double.parseDouble(String.format("%.1f", player.y));
				double z = Double.parseDouble(String.format("%.1f", player.z));
				Level level = player.getLevel();
				pos = new Position(x, y, z, level);
				this.warp.put(args[1], pos);

				HashMap<String, String> data = new HashMap<String, String>();
				data.put("x", String.valueOf(x));
				data.put("y", String.valueOf(y));
				data.put("z", String.valueOf(z));
				data.put("level", level.getName());
				this.config.set(args[1], data);
				this.config.save();

				player.sendMessage("§a>>§bワープポイントを作成しました");
			break;

			case "del":
				if (!player.hasPermission("op")) {
					player.sendMessage("§a>>§b貴方はこのコマンドを使う権限がありません");
					return false;
				}
				if (args.length < 2) {
					player.sendMessage("§a>>§b/warp del [ワープポイントの名前]");
					return false;
				}
				if (!this.warp.containsKey(args[1])) {
					player.sendMessage("§a>>§bそのワープポイントは存在しません");
					return false;
				}
				this.warp.remove(args[1]);
				this.config.remove(args[1]);
				this.config.save();

				player.sendMessage("§a>>§bワープポイントを削除しました");
			break;

			case "list":
				if (this.warp.size() < 1) {
					player.sendMessage("§a>>§bまだワープポイントが存在しません");
					return false;
				}
				if (this.warp.size() == 1) {

				}
				player.sendMessage("§a>>§bワープポイント一覧");
				this.warp.forEach((name, position) -> {
					player.sendMessage("§a>>§b" + name + " | x : " + position.x + " y : " + position.y + " z : " + position.z + " | level : " + position.getLevel().getName());
				});
			break;

			case "warp":
				if (!this.warp.containsKey(args[1])) {
					player.sendMessage("§a>>§bワープポイント " + args[1] + " は存在しません");
					return false;
				}
				pos = this.warp.get(args[1]);
				player.teleport(pos);
				player.sendMessage("§a>>§bワープしました");
			break;
		}
		return true;
	}
}
