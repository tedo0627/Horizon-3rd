package tedo.WorldEdit.command;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import tedo.WorldEdit.WorldEdit;

public class BlockConvertCommand {

	public static void execution(Player player, WorldEdit main) {
		String name = player.getName();
		if (!main.isVector1(name)) {
			player.sendMessage("§a>>§b一つ目の位置を決めてください");
			return;
		}
		if (!main.isVector2(name)) {
			player.sendMessage("§a>>§b二つ目の位置を決めてください");
			return;
		}
		Level level = player.getLevel();
		Vector3 pos1 = main.getVector1(name);
		Vector3 pos2 = main.getVector2(name);
		int x1 = (int) pos1.x;
		int y1 = 0;
		int z1 = (int) pos1.z;
		int x2 = (int) pos2.x;
		int y2 = 255;
		int z2 = (int) pos2.z;
		if (x1 > x2) {
			x1 = x1 + x2;
			x2 = x1 - x2;
			x1 = x1 - x2;
		}
		if (y1 > y2) {
			y1 = y1 + y2;
			y2 = y1 - y2;
			y1 = y1 - y2;
		}
		if (z1 > z2) {
			z1 = z1 + z2;
			z2 = z1 - z2;
			z1 = z1 - z2;
		}
		int x, y, z;
		int id;
		for (x = x1; x < x2; x++) {
			for (y = y1; y < y2; y++) {
				for (z = z1; z < z2; z++) {
					id = level.getBlockIdAt(x, y, z);
					switch (id) {
						case 43:
						case 44:
							if (level.getBlockDataAt(x, y, z) == 6) {
								level.setBlockDataAt(x, y, z, 7);
							} else if (level.getBlockDataAt(x, y, z) == 7) {
								level.setBlockDataAt(x, y, z, 7);
							}
							break;

						case 95:
							level.setBlockIdAt(x, y, z, 241);
							break;

						case 125:
							level.setBlockIdAt(x, y, z, 157);
							break;

						case 126:
							level.setBlockIdAt(x, y, z, 158);
							break;

						case 157:
							level.setBlockIdAt(x, y, z, 126);
							break;

						case 158:
							level.setBlockIdAt(x, y, z, 125);
							break;

						case 166:
							level.setBlockIdAt(x, y, z, 95);
							break;

						case 188:
						case 189:
						case 190:
						case 191:
						case 192:
							level.setBlockIdAt(x, y, z, 85);
							level.setBlockDataAt(x, y, z, id - 187);
							break;

						case 208:
							level.setBlockIdAt(x, y, z, 198);
							break;
					}
				}
			}
		}
		return;
	}

}
