package tedo.SeichiSystemPlugin.delayedTask;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.PluginTask;
import tedo.SeichiSystemPlugin.LevelSystem;
import tedo.SeichiSystemPlugin.SeichiSystemPlugin;
import tedo.SeichiSystemPlugin.skill.skill10;

public class AsaultTask extends PluginTask<SeichiSystemPlugin>{

	public AsaultTask(SeichiSystemPlugin main) {
		super(main);
	}

	@Override
	public void onRun(int tick) {
		SeichiSystemPlugin main = this.owner;
		main.getServer().getOnlinePlayers().forEach((UUID, player) -> {
			if (!canUseAsault(player)) {
				return;
			}
			String name = player.getName().toLowerCase();
			main.usedskill.put(name, false);
			boolean r = new skill10(main, player).use(player, main, main.isAddArea(player.getInventory().getItemInHand()));
			main.usedskill.put(name, true);
			LevelSystem l;
			if (main.limit.containsKey(name)) {
				l = new LevelSystem(main.data.get(name), main.limit.get(name));
			}else{
				l = new LevelSystem(main.data.get(name));
			}
			player.updateBossBar(main.getBossText(player), l.getProportion(), main.bossbar.get(name));
			if (main.asault.containsKey(name)) {
				if (r) {
					main.asault.put(name, 0);
				}else{
					int c = main.asault.get(name);
					c = c + 3;
					if (c < 200) {
						main.asault.put(name, c);
					}else{
						main.asault.put(name, 0);
						main.skill.put(player, false);
						player.sendMessage("§a>>§b一定の間ブロックを破壊しなかったため、スキルをoffにしました");
					}
				}
			}else{
				main.asault.put(name, 0);
			}
		});
	}

	public boolean canUseAsault(Player player) {
		SeichiSystemPlugin main = this.owner;
		String name = player.getName().toLowerCase();
		if (main.skilln.get(name) != 10) {
			return false;
		}
		Level level = player.getLevel();
		if (!main.level.contains(level.getName())) {
			return false;
		}
		if (!main.skill.get(name)) {
			return false;
		}
		if (!player.isSurvival()) {
			return false;
		}
		if (!player.getInventory().getItemInHand().isTool()) {
			return false;
		}
		return true;
	}
}
