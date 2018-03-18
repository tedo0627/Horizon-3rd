package tedo.InventoryAPI;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.ContainerSetSlotPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.plugin.PluginBase;
import tedo.InventoryAPI.event.InventoryClickEvent;
import tedo.InventoryAPI.inventory.CreateBaseInventory;
import tedo.InventoryAPI.inventory.CreateChestInventory;
import tedo.InventoryAPI.inventory.CreateDoubleChestInventory;

public class InventoryAPI extends PluginBase implements Listener{

	public static InventoryAPI main;
	public HashMap<Player, BaseInventory> inventory = new HashMap<Player, BaseInventory>();

	public void onEnable() {
		main = this;
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onDataPacketReceive(DataPacketReceiveEvent event) {
		DataPacket packet = event.getPacket();
		if (packet instanceof ContainerSetSlotPacket) {
			Player player = event.getPlayer();
			if (hasInventory(player)) {
				ContainerSetSlotPacket pk = (ContainerSetSlotPacket) packet;
				BaseInventory inventory;
				if (pk.windowid == 0) {
					inventory = player.getInventory();
				} else {
					inventory = getInventory(player);
				}
				InventoryClickEvent ev = new InventoryClickEvent(player, inventory, pk.item, pk.slot, pk.windowid);
				this.getServer().getPluginManager().callEvent(ev);
				if (ev.isCancelled()) {
					inventory.sendContents(player);
				} else {
					inventory.slots.put(pk.slot, pk.item);
				}
				event.setCancelled();
			}
		} else if (packet instanceof ContainerClosePacket) {
			Player player = event.getPlayer();
			if (hasInventory(player)) {
				removeInventory(player);
			}
		}
	}

	public boolean hasInventory(Player player) {
		return this.inventory.containsKey(player);
	}

	public BaseInventory getInventory(Player player) {
		return this.inventory.get(player);
	}

	public void removeInventory(Player player) {
		if (!hasInventory(player)) {
			return;
		}
		BaseInventory inventory = getInventory(player);
		if (inventory instanceof CreateBaseInventory) {
			((CreateBaseInventory) inventory).sendBeforeBlock();
		}
		this.inventory.remove(player);
	}



	public CreateChestInventory getChestInventory(Player player, int x, int y, int z) {
		CreateChestInventory inventory = new CreateChestInventory(player, x, y, z);
		this.inventory.put(player, inventory);
		return inventory;
	}

	public CreateDoubleChestInventory getDoubleChestInventory(Player player, int x, int y, int z) {
		CreateDoubleChestInventory inventory = new CreateDoubleChestInventory(player, x, y, z);
		this.inventory.put(player, inventory);
		return inventory;
	}
}
