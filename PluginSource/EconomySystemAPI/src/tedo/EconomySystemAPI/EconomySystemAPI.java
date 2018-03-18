package tedo.EconomySystemAPI;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class EconomySystemAPI extends PluginBase implements Listener{

	public Config config, data;
	public long configdata;
	public HashMap<String, Long> money = new HashMap<String, Long>();
	public ArrayList<String> login = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	public void onEnable(){
		this.getServer().getCommandMap().register("money", new MoneyCommand(this));

		this.getServer().getPluginManager().registerEvents(this, this);

		this.getDataFolder().mkdirs();
		this.data = new Config(new File(this.getDataFolder(), "data.yml"),Config.YAML);
		this.config = new Config(new File(this.getDataFolder(), "config.yml"),Config.YAML,
		new LinkedHashMap<String, Object>() {
			{
				put("初期金額", (long) 1000L);
			}
		}
		);
		this.configdata = Long.parseLong(String.valueOf(this.config.get("初期金額")));

		this.data.getAll().forEach((name, money) -> {
			try {
				long mo = Long.valueOf(String.valueOf(money));
				this.money.put(name, mo);
			} catch (Exception e) {
				this.config.remove(name);
				this.config.save();
			}
		});
		getServer().getScheduler().scheduleRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				save();
			}
		}, 20 * 60 * 10);
	}

	public void onDisable() {
		save();
	}

	public void save() {
		this.login.forEach((name) -> this.data.set(name, this.money.get(name)));
		this.data.save();
	}

	@EventHandler
	public void onPlayerPreLogin(PlayerPreLoginEvent event) {
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		if (!isMoney(name)) {
			this.money.put(name, this.configdata);
			getMoney(name);
		}
		this.login.add(name);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		this.data.set(name, this.money.get(name));
		this.data.save();
	}

	public String getName(String name) {
		Player player = getServer().getPlayer(name);
		if (player instanceof Player) {
			name = player.getName().toLowerCase();
		}else{
			name = name.toLowerCase();
		}
		return name;
	}

	//API

	public Boolean isMoney(String name) {
		name = name.toLowerCase();
		if (this.money.containsKey(name)) {
			return true;
		}else{
			return false;
		}
	}

	public long getMoney(String name) {
		name = name.toLowerCase();
		long money = (long) this.money.get(name);
		return money;
	}

	public void addMoney(String name, long money) {
		name = name.toLowerCase();
		long data = getMoney(name);
		data = data + money;
		this.money.put(name, data);
	}

	public void delMoney(String name, long money) {
		name = name.toLowerCase();
		long data = getMoney(name);
		data = data - money;
		this.money.put(name, data);
	}

	public void setMoney(String name, long money) {
		name = name.toLowerCase();
		this.money.put(name, money);
	}
}