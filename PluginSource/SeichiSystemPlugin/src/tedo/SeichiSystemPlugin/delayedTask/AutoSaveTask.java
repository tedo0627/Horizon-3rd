package tedo.SeichiSystemPlugin.delayedTask;

import cn.nukkit.scheduler.PluginTask;
import tedo.SeichiSystemPlugin.SeichiSystemPlugin;

public class AutoSaveTask extends PluginTask<SeichiSystemPlugin>{

	public AutoSaveTask(SeichiSystemPlugin main) {
		super(main);
	}

	@Override
	public void onRun(int tick) {
		SeichiSystemPlugin main = this.owner;
		main.getServer().broadcastMessage("§a>>§bオートセーブを開始します");
		main.getServer().getScheduler().scheduleDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				main.getServer().broadcastMessage("§a>>§bオートセーブを開始します");
				main.save();
				main.getServer().broadcastMessage("§a>>§bオートセーブが終了しました");
			}
		}, 20);
	}
}
