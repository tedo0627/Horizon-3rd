package tedo.TeleportPlugin.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

public class HubCommand extends Command{

	public HubCommand() {
		super("hub", "デフォルトのワールドのリスポーン地点にテレポートします");

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
		player.teleport(player.getServer().getDefaultLevel().getSafeSpawn());
		player.sendMessage("§a>>§bテレポートしました");
		return true;
	}
}
