package tedo.GatyaSystem.command;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.inventory.PlayerInventory;

public class InventoryClearCommand extends Command{

	public ArrayList<Player> player = new ArrayList<Player>();

	public InventoryClearCommand() {
		super("invclear", "インベントリーを空っぽにするコマンド");

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
		if (this.player.contains(player)) {
			PlayerInventory inventory = player.getInventory();
			for (int slot = 0; slot < inventory.getSize(); slot++) {
				inventory.clear(slot);
			}
			player.sendMessage("§a>>§bインベントリーをからにしました");
			this.player.remove(player);
		} else {
			this.player.add(player);
			player.sendMessage("§a>>§b本当にインベントリーを空っぽにするならもう一度このコマンドを入力してください");
		}
		return true;
	}
}
