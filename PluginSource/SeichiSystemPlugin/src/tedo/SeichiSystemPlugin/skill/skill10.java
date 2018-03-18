package tedo.SeichiSystemPlugin.skill;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import tedo.SeichiSystemPlugin.SeichiSystemPlugin;

public class skill10 extends BaseSkill{

	public skill10(SeichiSystemPlugin main, Player player) {
		super(main, player);
	}

	public boolean use(Player player, SeichiSystemPlugin main, Boolean r) {
		boolean te = false;
		int x = (int) player.x;
		int y = (int) player.y;
		int z = (int) player.z;
		if (player.isSneaking() && player.pitch == 90) {
			if (r) {
				y-=14;
			}else{
				y-=13;
			}
		}
		if(r) {
			for (int y1 = y + 13; y1 >= y; y1--) {
				for (int x1 = x + 6; x1 >= x - 6; x1--) {
					for (int z1 = z + 6; z1 >= z - 6; z1--) {
						Vector3 pos = new Vector3(x1, y1, z1);
						if (breakBlock(pos)) {
							te = true;
						}
					}
				}
			}
		}else{
			for (int y1 = y + 12; y1 >= y; y1--) {
				for (int x1 = x + 6; x1 >= x - 6; x1--) {
					for (int z1 = z + 6; z1 >= z - 6; z1--) {
						Vector3 pos = new Vector3(x1, y1, z1);
						if (breakBlock(pos)) {
							te = true;
						}
					}
				}
			}
		}
		return te;
	}
}
