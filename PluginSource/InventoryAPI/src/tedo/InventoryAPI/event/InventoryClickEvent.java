package tedo.InventoryAPI.event;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.item.Item;

public class InventoryClickEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}

	public Player player;
	public BaseInventory inventory;
	public Item item;
	public int slot, windowid;

	public InventoryClickEvent(Player player, BaseInventory inventory, Item item, int slot, int windowid) {
		this.player = player;
		this.inventory = inventory;
		this.item = item;
		this.slot = slot;
		this.windowid = windowid;
	}

	public Player getPlayer() {
		return this.player;
	}

	public BaseInventory getInventory() {
		return this.inventory;
	}

	public Item getItem() {
		return this.item;
	}

	public int getSlot() {
		return this.slot;
	}

	public int getWindowId() {
		return this.windowid;
	}
}
