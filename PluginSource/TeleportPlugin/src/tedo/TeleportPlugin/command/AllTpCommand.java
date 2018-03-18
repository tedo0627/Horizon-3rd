package tedo.TeleportPlugin.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

public class AllTpCommand extends Command{

	public AllTpCommand() {
		super("alltp", "全プレイヤーをテレポートさせるコマンド");

		this.setPermission("op");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bこのコマンドはコンソールから実行できません");
			return false;
		}
		Player player = (Player) sender;
		player.getServer().getOnlinePlayers().forEach((UUID, p) -> {
			p.teleport(player);
		});

		player.sendMessage("§a>>§b全プレイヤーをテレポートしました");
		return true;
	}
}