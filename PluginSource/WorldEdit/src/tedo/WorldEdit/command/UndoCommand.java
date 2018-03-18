package tedo.WorldEdit.command;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import tedo.WorldEdit.WorldEdit;

public class UndoCommand {

	public static void execution(Player player, WorldEdit main) {
		HashMap<String, Block> undo = main.getUndo();
		if (undo == null) {
			player.sendMessage("§a>>§b戻すブロックのデータがありません");
			return;
		}
		ArrayList<Integer> i = new ArrayList<Integer>();
		Position pos = new Position();
		undo.forEach((p, block) -> {
			String[] ps = p.split(":");
			Level level = main.getServer().getLevelByName(ps[3]);
			pos.level = level;
			pos.x = Integer.valueOf(ps[0]);
			pos.y = Integer.valueOf(ps[1]);
			pos.z = Integer.valueOf(ps[2]);
			level.setBlock(pos, block, false, false);
			i.add(0);
		});
		player.sendMessage("§a>>§b" + i.size() + "ブロックを戻しました");
	}
}
