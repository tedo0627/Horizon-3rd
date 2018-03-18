package tedo.GatyaSystem.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import tedo.GatyaSystem.item.BaseItem;

public class ChangeCommand extends Command{

	public ChangeCommand() {
		super("change", "ガチャの景品をエメラルドに変換するコマンド");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{});
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§a>>§bコンソールからこのコマンドを実行できません");
			return false;
		}
		Player player = (Player) sender;
		Item item = player.getInventory().getItemInHand();
		if (BaseItem.isBaseItem(item)) {
			if (!BaseItem.isOwner(item, player)) {
				player.sendMessage("§a>>§b自分がガチャで引いた景品以外を交換吸うことはできません");
				return false;
			}
			Item prize = Item.get(0);
			if (BaseItem.isRare(item)) {
				prize = Item.get(388, 0, 1);
			} else if (BaseItem.isBigRare(item)) {
				prize = Item.get(388, 0, 5);
			} else if (BaseItem.isHorizonRare(item) || BaseItem.isHorizonRarePlus(item)) {
				player.sendMessage("§a>>§bHR以上のレア度の景品は交換できません");
				return false;
			}
			player.getInventory().setItemInHand(Item.get(0));
			player.getInventory().addItem(prize);
			player.sendMessage("§a>>§bアイテムを交換しました");
		} else {
			player.sendMessage("§a>>§bそのアイテムは交換することができません");
			return false;
		}
		return false;
	}
}
