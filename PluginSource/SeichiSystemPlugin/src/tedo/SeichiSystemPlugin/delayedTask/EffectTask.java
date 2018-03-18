package tedo.SeichiSystemPlugin.delayedTask;

import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.PluginTask;
import tedo.SeichiSystemPlugin.SeichiSystemPlugin;

public class EffectTask extends PluginTask<SeichiSystemPlugin>{

	public EffectTask(SeichiSystemPlugin main) {
		super(main);
	}

	@Override
	public void onRun(int tick) {
		SeichiSystemPlugin main = this.owner;
		main.getServer().getOnlinePlayers().forEach((UUID, player) -> {
			if (player.hasEffect(3)) {
				player.removeEffect(3);
			}
			String name = player.getName().toLowerCase();
			int d = main.block.get(name) / 100;
			if (d >= 100) {
				d = 100;
			}
			main.block.put(name, 0);
			player.addEffect(Effect.getEffect(3).setDuration(10000 * 20).setAmplifier(d).setVisible(false));
		});
	}
}
