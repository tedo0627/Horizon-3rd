package tedo.GatyaSystem.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import tedo.GatyaSystem.Status;

public class PrizeCommand extends Command{

	public PrizeCommand() {
		super("prize", "ガチャの当たりと大当たりを自動でエメラルドに交換するコマンド");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("onもしくはoff", false, new String[]{"on", "off"})
		});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールからこのコマンドを実行できません");
			return false;
		}
		Player player = (Player) sender;
		switch (args[0]) {
			case "on":
				Status.setPrize(player, true);
				player.sendMessage("§a>>§bあたりと大当たりの景品を自動でエメラルドに交換し、アイテムクラウドに送られるようになりました");
				return true;

			case "off":
				Status.setPrize(player, false);
				player.sendMessage("§a>>§bあたりと大当たりの景品を自動でエメラルドに交換し、アイテムクラウドに送られなくしました");
				return true;

			default:
				return false;
		}
	}

}
