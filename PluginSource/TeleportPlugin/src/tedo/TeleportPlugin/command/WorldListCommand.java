package tedo.TeleportPlugin.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

public class WorldListCommand extends Command{

	public WorldListCommand() {
		super("wlist", "読み込まれているワールドを確認する");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		sender.sendMessage("§a>>§b現在読み込まれているワールド");
		sender.getServer().getLevels().forEach((i, level) -> {
			sender.sendMessage("§a>>§b" + level.getName());
		});
		return true;
	}
}
