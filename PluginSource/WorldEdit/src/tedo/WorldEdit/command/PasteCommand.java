package tedo.WorldEdit.command;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import tedo.WorldEdit.WorldEdit;

public class PasteCommand {

	public static void execution(Player player, WorldEdit main) {
		String name = player.getName();
		if (!main.isCopy(name)) {
			player.sendMessage("§a>>§bコピーしたものがありません");
			return;
		}
		if (!main.isVector1(name)) {
			player.sendMessage("§a>>§b一つ目の位置を決めてください");
			return;
		}
		Level level = player.getLevel();
		Vector3 pos = main.getVector1(name);
		int x = (int) pos.x;
		int y = (int) pos.y;
		int z = (int) pos.z;
		ArrayList<Integer> i = new ArrayList<Integer>();
		HashMap<String, Block> undo = new HashMap<String, Block>();
		Position po = new Position();
		po.level = level;
		main.getCopyBlock(name).forEach((p, block) -> {
			String[] ps = p.split(":");
			int xp = (int) Integer.valueOf(ps[0]) + x;
			int yp = (int) Integer.valueOf(ps[1]) + y;
			int zp = (int) Integer.valueOf(ps[2]) + z;
			po.x = xp;
			po.y = yp;
			po.z = zp;
			String pss = xp + ":" + yp + ":" + zp + ":" + level.getName();
			undo.put(pss, level.getBlock(po, false));
			level.setBlock(po, block, false, false);
			i.add(0);
		});
		main.addUndo(undo);
		player.sendMessage("§a>>§b" + i.size() + "ブロック編集が終了しました");
	}
}
