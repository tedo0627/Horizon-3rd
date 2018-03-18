package tedo.SeichiSystemPlugin.delayedTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import cn.nukkit.scheduler.PluginTask;
import tedo.SeichiSystemPlugin.SeichiSystemPlugin;

public class TopTask extends PluginTask<SeichiSystemPlugin>{

	public TopTask(SeichiSystemPlugin main) {
		super(main);
	}

	@Override
	public void onRun(int tick) {
		SeichiSystemPlugin main = this.owner;
		main.getServer().broadcastMessage("§e====30分間の整地量のランキング====");
		List<Entry<String, Integer>> top = new ArrayList<Entry<String, Integer>>(main.top30.entrySet());
        Collections.sort(top, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });
        int c = 1;
        for (Entry<String, Integer> entry : top) {
        	if (c < 4) {
        		main.getServer().broadcastMessage("§b" + c + "位 | " + entry.getKey() + " : " + entry.getValue() + "ブロック");
        	}
        	if (c > 5) {
        		return;
        	}
        	c++;
        }
        main.getServer().getOnlinePlayers().forEach((UUID, player) -> {
        	String name = player.getName().toLowerCase();
        	player.sendMessage("§a>>§b貴方は30分間で" + main.top30.get(name) + "ブロック破壊しました");
        });
        main.top30.forEach((name, count) -> main.top30.put(name, 0));
	}
}
