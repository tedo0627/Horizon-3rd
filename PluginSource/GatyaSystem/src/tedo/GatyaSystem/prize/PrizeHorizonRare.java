package tedo.GatyaSystem.prize;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import tedo.GatyaSystem.GatyaRandom;
import tedo.GatyaSystem.item.BaseItem;

public class PrizeHorizonRare{

	public static void add(Player player) {
		Item item = null;
		switch (GatyaRandom.select(14)) {
			case 1:
				item = Item.get(10011);
				break;

			case 2:
				item = Item.get(10012);
				break;

			case 3:
				item = Item.get(10013);
				break;

			case 4:
				item = Item.get(10014);
				break;

			case 5:
				item = Item.get(10015);
				break;

			case 6:
				item = Item.get(10016);
				break;

			case 7:
				item = Item.get(10017);
				break;

			case 8:
				item = Item.get(10018);
				break;

			case 9:
				item = Item.get(10019);
				break;

			case 10:
				item = Item.get(10020);
				break;

			case 11:
				item = Item.get(10021);
				break;

			case 12:
				item = Item.get(10022);
				break;

			case 13:
				item = Item.get(10023);
				break;

			case 14:
				item = Item.get(10024);
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
