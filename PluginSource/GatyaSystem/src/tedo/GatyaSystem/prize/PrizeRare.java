package tedo.GatyaSystem.prize;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import tedo.GatyaSystem.GatyaRandom;
import tedo.GatyaSystem.item.BaseItem;

public class PrizeRare {

	public static void add(Player player) {
		Item item = null;
		switch (GatyaRandom.select(9)) {
			case 1:
				item = Item.get(10071);
				break;

			case 2:
				item = Item.get(10072);
				break;

			case 3:
				item = Item.get(10073);
				break;

			case 4:
				item = Item.get(10074);
				break;

			case 5:
				item = Item.get(10075);
				break;

			case 6:
				item = Item.get(10076);
				break;

			case 7:
				item = Item.get(10077);
				break;

			case 8:
				item = Item.get(10078);
				break;

			case 9:
				item = Item.get(10079);
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
