package tedo.TeleportPlugin.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;

public class WorldTeleportCommand extends Command{

	public WorldTeleportCommand() {
		super("wtp", "別ワールドにテレポートするコマンド");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("ワールド名", CommandParameter.ARG_TYPE_STRING, false),
		});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールからこのコマンドを実行できません");
		}
		if (args.length < 1) {
			sender.sendMessage("§a>>§b/wtp [ワールドの名前]");
			return false;
		}
		Player player = (Player) sender;
		Server server = player.getServer();
		Level level = server.getLevelByName(args[0]);
		if (level == null) {
			player.sendMessage("§a>>§bワールド " + args[0] + " は存在しません");
			return false;
		}
		player.teleport(level.getSafeSpawn());
		player.sendMessage("§a>>§bテレポートしました");
		return true;
	}
}
