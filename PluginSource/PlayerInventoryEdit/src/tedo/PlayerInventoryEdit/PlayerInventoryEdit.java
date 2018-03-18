package tedo.PlayerInventoryEdit;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.PluginBase;

public class PlayerInventoryEdit extends PluginBase implements Listener{

	public HashMap<Player, CreateDoubleChestInventory> chest = new HashMap<Player, CreateDoubleChestInventory>();
	public HashMap<String, Player> player = new HashMap<String, Player>();

	public HashMap<Player, CreateDoubleChestInventory> inventory = new HashMap<Player, CreateDoubleChestInventory>();

	public HashMap<Player, Player> online = new HashMap<Player, Player>();
	public HashMap<Player, Player> now = new HashMap<Player, Player>();
	public HashMap<Player, String> name = new HashMap<Player, String>();

	public HashMap<Player, String> offline = new HashMap<Player, String>();
	public HashMap<Player, CompoundTag> nbt = new HashMap<Player, CompoundTag>();

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new OfflineContents(this), this);
		getServer().getPluginManager().registerEvents(new OnlineContents(this), this);

		getServer().getCommandMap().register("open", new OpenCommand(this));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (hasNow(player)) {
			removeNow(player);
		}
	}

	public void setInventory(Player player, CreateDoubleChestInventory inventory) {
		this.inventory.put(player, inventory);
	}

	public void removeInventory(Player player) {
		this.inventory.remove(player);
	}

	public CreateDoubleChestInventory getInventory(Player player) {
		return this.inventory.get(player);
	}



	public boolean hasOnline(Player player) {
		return this.online.containsKey(player);
	}

	public void setOnline(Player player, Player target) {
		this.online.put(player, target);
	}

	public void removeOnline(Player player) {
		this.online.remove(player);
	}

	public Player getOnline(Player player) {
		return this.online.get(player);
	}



	public boolean hasNow(Player player) {
		return this.now.containsKey(player);
	}

	public void setNow(Player player, Player p) {
		this.now.put(player, p);
	}

	public void removeNow(Player player) {
		this.now.remove(player);
	}

	public Player getNow(Player player) {
		return this.now.get(player);
	}



	public void setName(Player player, String name) {
		this.name.put(player, name);
	}

	public void removeName(Player player) {
		this.name.remove(player);
	}

	public String getName(Player player) {
		return this.name.get(player);
	}



	public boolean hasOffline(Player player) {
		return this.offline.containsKey(player);
	}

	public void setOffline(Player player, String name) {
		this.offline.put(player, name);
	}

	public void removeOffline(Player player) {
		this.offline.remove(player);
	}

	public String getOffline(Player player) {
		return this.offline.get(player);
	}



	public boolean hasNBT(Player player) {
		return this.nbt.containsKey(player);
	}

	public void setNBT(Player player, CompoundTag nbt) {
		this.nbt.put(player, nbt);
	}

	public void removeNBT(Player player) {
		this.nbt.remove(player);
	}

	public CompoundTag getNBT(Player player) {
		return this.nbt.get(player);
	}
}
