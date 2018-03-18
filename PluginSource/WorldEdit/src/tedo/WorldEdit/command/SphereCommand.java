package tedo.WorldEdit.command;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import tedo.WorldEdit.WorldEdit;

public class SphereCommand {

	public static void execution(Player player, String[] args, WorldEdit main) {
		String name = player.getName();
		if (args.length < 4) {
			player.sendMessage("§a>>§b/ sphere [ID] [半径]");
			return;
		}
		if (!main.isVector1(name)) {
			player.sendMessage("§a>>§b一つ目の位置を決めてください");
			return;
		}
		Level level = player.getLevel();
		Vector3 pos = main.getVector1(name);
		pos = pos.floor();
		int x = (int) pos.x;
		int y = (int) pos.y;
		int z = (int) pos.z;
		Item item = Item.fromString(args[2]);
		Block block = Block.get(item.getId(), item.getDamage());
		int distance = Integer.parseInt(args[3]);
		int r = 0;
		int x1 = x + distance;
		int x2 = x - distance;
		int y1 = y + distance;
		int y2 = y - distance;
		int z1 = z + distance;
		int z2 = z - distance;
		HashMap<String, Block> undo = new HashMap<String, Block>();
		Position set = new Position();
		set.level = level;
		for (y = y1; y >= y2; y--) {
			set.y = y;
			for (x = x1; x >= x2; x--) {
				set.x = x;
				for (z = z1; z >= z2; z--) {
					set.z = z;
					double dis = pos.distance(set);
					if (dis <= distance) {
						String ps = x + ":" + y + ":" + z + ":" + level.getName();
						undo.put(ps, level.getBlock(set, false));
						level.setBlock(set, block, false, false);
						r++;
					}
				}
			}
		}
		main.addUndo(undo);
		player.sendMessage("§a>>§b" + r + "ブロックの編集が終了しました");
	}
}
