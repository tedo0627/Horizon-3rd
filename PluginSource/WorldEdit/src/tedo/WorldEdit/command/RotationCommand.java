package tedo.WorldEdit.command;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import tedo.WorldEdit.WorldEdit;

public class RotationCommand {

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
		Vector3 pos = new Vector3((int) player.x, (int) player.y, (int) player.z);
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
		HashMap<String, Integer> data = new HashMap<>();
		for (x = x1; x <= x2; x++) {
			for (y = y1; y <= y2; y++) {
				for (z = z1; z <= z2; z++) {
					id = level.getBlockIdAt(x, y, z);
					data.put(x + ":" + y + ":" + z, id);
				}
			}
		}
		double x3, z3;
		for (x = x1; x <= x2; x++) {
			for (y = y1; y <= y2; y++) {
				for (z = z1; z <= z2; z++) {
					for (int i = 90; i <= 360; i += 90) {
						double distance = Math.sqrt(((x - pos.x) * (x - pos.x)) + ((z - pos.z) * (z - pos.z)));
						double radian = Math.atan2(z - pos.z, x - pos.x);
						double degree = radian * 180d / Math.PI;
						degree += i;
						if (degree > 360) {
							degree -= 360;
						}
						radian = degree * Math.PI / 180;
						x3 = Math.cos(radian) * distance;
						z3 = Math.sin(radian) * distance;
						level.setBlockIdAt((int) (x3 + pos.x), y, (int) (z3 + pos.z), data.get(x + ":" + y + ":" + z));
					}
				}
			}
		}
		player.sendMessage("§a>>§b設置しました");
	}

}
