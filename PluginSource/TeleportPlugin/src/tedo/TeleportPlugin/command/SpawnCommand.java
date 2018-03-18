package tedo.TeleportPlugin.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

public class SpawnCommand extends Command{

	public SpawnCommand() {
		super("spawn", "ワールドのリスポーン地点にテレポートする");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールからそのコマンドを実行できません");
			return false;
		}
		Player player = (Player) sender;
		player.teleport(player.getLevel().getSafeSpawn());
		player.sendMessage("§a>>§bテレポートしました");
		return true;
	}
}
