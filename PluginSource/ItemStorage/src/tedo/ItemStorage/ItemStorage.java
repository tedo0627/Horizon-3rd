package tedo.ItemStorage;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.PluginBase;

public class ItemStorage extends PluginBase implements Listener{

	public void onEnable(){
		this.getServer().getCommandMap().register("item", new ItemCommand(this));
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	public boolean hasItem(Player player, Item item) {
		String id = item.getId() + ":" + item.getDamage();
		CompoundTag nbt = player.namedTag.getCompound("ItemStorage");
		if (nbt.contains(id)) {
			int count = item.getCount();
			if (nbt.getInt(id) >= count) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void addItem(Player player, Item item) {
		String id = item.getId() + ":" + item.getDamage();
		int count = item.getCount();
		CompoundTag nbt = player.namedTag.getCompound("ItemStorage");
		if (nbt.contains(id)) {
			int co = nbt.getInt(id);
			nbt.putInt(id, co + count);
		} else {
			nbt.putInt(id, count);
		}
	}

	public void delItem(Player player, Item item) {
		String id = item.getId() + ":" + item.getDamage();
		int count = item.getCount();
		CompoundTag nbt = player.namedTag.getCompound("ItemStorage");
		if (nbt.contains(id)) {
			int co = nbt.getInt(id);
			co -= count;
			if (co < 1) {
				nbt.remove(id);
			} else {
				nbt.putInt(id, co);
			}
		}
	}

	public int getItemCount(Player player, Item item) {
		String id = item.getId() + ":" + item.getDamage();
		CompoundTag nbt = player.namedTag.getCompound("ItemStorage");
		if (nbt.contains(id)) {
			return nbt.getInt(id);
		} else {
			return 0;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.namedTag.contains("ItemStorage")) {
			player.namedTag.putCompound("ItemStorage", new CompoundTag());
		}
	}
}
