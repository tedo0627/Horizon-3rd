package tedo.GatyaSystem.prize;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import tedo.GatyaSystem.GatyaRandom;
import tedo.GatyaSystem.item.BaseItem;

public class PrizeBigRare extends PluginBase{

	public static void add(Player player) {
		Item item = null;
		switch (GatyaRandom.select(9)) {
			case 1:
				item = Item.get(10051);
				break;

			case 2:
				item = Item.get(10052);
				break;

			case 3:
				item = Item.get(10053);
				break;

			case 4:
				item = Item.get(10054);
				break;

			case 5:
				item = Item.get(10055);
				break;

			case 6:
				item = Item.get(10056);
				break;

			case 7:
				item = Item.get(10057);
				break;

			case 8:
				item = Item.get(10058);
				break;

			case 9:
				item = Item.get(10059);
				break;
		}
		((BaseItem) item).setOwner(player.getName().toLowerCase());
		if (player.getInventory().canAddItem(item)) {
			player.getInventory().addItem(item);
		}else{
			Vector3 pos = new Vector3(player.x, player.y, player.z);
			player.getLevel().dropItem(pos, item);
		}
	}
}
