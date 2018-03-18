package tedo.TeleportPlugin.command;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.Generator;

public class GenerateCommand extends Command{

	public GenerateCommand() {
		super("generate", "新規ワールドを作成するコマンド");

		this.setPermission("op");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("新しいワールド名", CommandParameter.ARG_TYPE_STRING, false),
		});
		this.addCommandParameters("type", new CommandParameter[]{
				new CommandParameter("新しいワールド名", CommandParameter.ARG_TYPE_STRING, false),
				new CommandParameter("ワールドのジェネレーター", CommandParameter.ARG_TYPE_STRING, false),
		});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		boolean check;
		Server server = sender.getServer();
		if (args.length < 1) {
			sender.sendMessage("§a>>§b/generate [ワールド名]");
			sender.sendMessage("§a>>§b/generate [ワールド名] [生成の種類]");
			return false;
		}
		if (args.length < 2) {
			check = server.generateLevel(args[0]);
		} else {
			check = server.generateLevel(args[0], new java.util.Random().nextLong(), Generator.getGenerator(args[1]));
		}
		if (check) {
			Level level = server.getLevelByName(args[0]);
			level.loadChunk((int) level.getSafeSpawn().x >> 4, (int) level.getSafeSpawn().z >> 4);
			sender.sendMessage("§a>>§bワールド " + args[0] + " を作成しました");
		} else {
			sender.sendMessage("§a>>§bワールド " + args[0] + " は既に存在しています");
		}
		return check;
	}
}
