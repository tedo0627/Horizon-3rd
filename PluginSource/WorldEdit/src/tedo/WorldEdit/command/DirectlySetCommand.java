package tedo.WorldEdit.command;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import tedo.WorldEdit.WorldEdit;

public class DirectlySetCommand {

	public static void execution(Player player, WorldEdit main) {
		String name = player.getName();
		if (!main.isCopy(name)) {
			player.sendMessage("§a>>§bコピーしたものがありません");
			return;
		}
		Level level = player.getLevel();
		ArrayList<Integer> i = new ArrayList<Integer>();
		main.dcopy.forEach((p, id) -> {
			String[] ps = p.split(":");
			int xp = (int) Integer.valueOf(ps[0]);
			int yp = (int) Integer.valueOf(ps[1]);
			int zp = (int) Integer.valueOf(ps[2]);
			level.setBlockIdAt(xp, yp, zp, (int) id);
			i.add(0);
		});
		player.sendMessage("§a>>§b" + i.size() + "ブロック編集が終了しました");
	}
}
