package tedo.PlayerInventoryEdit;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.ContainerSetSlotPacket;
import cn.nukkit.network.protocol.DataPacket;

public class OfflineContents implements Listener{

	public PlayerInventoryEdit main;

	public OfflineContents(PlayerInventoryEdit main) {
		this.main = main;
	}

	@EventHandler
	public void onDataPacketReceive(DataPacketReceiveEvent event) {
		DataPacket packet = event.getPacket();
		if (packet instanceof ContainerSetSlotPacket) {
			Player player = event.getPlayer();
			if (!this.main.hasOffline(player)) return;
			event.setCancelled();
			ContainerSetSlotPacket pk = (ContainerSetSlotPacket) packet;
			setInventoryItem(player, pk, this.main.getInventory(player));
		}else if (packet instanceof ContainerClosePacket) {
			Player player = event.getPlayer();

			if (!this.main.hasNBT(player)) return;

			CompoundTag nbt = this.main.getNBT(player);
			nbt.putList(new ListTag<CompoundTag>("Inventory"));
			CreateDoubleChestInventory inventory = this.main.getInventory(player);

			for (int slot = 0; slot < 54; slot++) {
				Item item = inventory.getItem(slot);
				if (slot >= 0 && slot < 27) {
					nbt.getList("Inventory", CompoundTag.class).add(NBTIO.putItemHelper(item, slot + 18));
				} else if (slot >= 36 && slot < 45) {
					nbt.getList("Inventory", CompoundTag.class).add(NBTIO.putItemHelper(item, slot - 27));
				} else if (slot >= 45 && slot < 49) {
					nbt.getList("Inventory", CompoundTag.class).add(NBTIO.putItemHelper(item, slot + 55));
				}
			}

			this.main.getServer().saveOfflinePlayerData(this.main.getOffline(player), nbt);

			this.main.getInventory(player).removeInventory(player);
			this.main.getInventory(player).sendBeforeBlock();

			this.main.removeInventory(player);
			this.main.removeNBT(player);
			this.main.removeOffline(player);
		}
	}

	public static void setInventoryItem(Player player, ContainerSetSlotPacket pk, CreateDoubleChestInventory inventory) {
		int slot = pk.slot;
		Item item = pk.item;
		if (pk.windowid == 0) {
			if (item.getId() == 102 && item.getCustomName().equals("§a仕切り")) {
				Item item1 = player.getInventory().getItem(slot);
				if (item1.getId() != 0) {
					player.getInventory().setItem(slot, Item.get(0));
				}else{
					player.getInventory().sendContents(player);
				}
				return;
			}
			player.getInventory().setItem(slot, item);
		} else if (pk.windowid == 64) {
			if (item.getId() == 102 && item.getCustomName().equals("§a仕切り")) {
				Item item1 = inventory.getItem(slot);
				if (item1.getId() != 0) {
					if (27 <= slot && slot <= 35) {
						inventory.sendContents();
					}else if (49 <= slot && slot <= 53) {
						inventory.sendContents();
					}else{
						inventory.setInventory(slot, Item.get(0));
					}
				}else{
					inventory.sendContents();
				}
				return;
			}
			if (slot == 45) {
				if (item.isHelmet() || item.getId() == 0) {
					inventory.setInventory(slot, item);
				}else{
					inventory.setInventory(slot, Item.get(0));
					inventory.sendContents();
					player.getInventory().addItem(item);
				}
			} else if (46 == slot) {
				if (item.isChestplate() || item.getId() == 0) {
					inventory.setInventory(slot, item);
				}else{
					inventory.setInventory(slot, Item.get(0));
					inventory.sendContents();
					player.getInventory().addItem(item);
				}
			} else if (47 == slot) {
				if (item.isLeggings() || item.getId() == 0) {
					inventory.setInventory(slot, item);
				}else{
					inventory.setInventory(slot, Item.get(0));
					inventory.sendContents();
					player.getInventory().addItem(item);
				}
			} else if (48 == slot) {
				if (item.isBoots() || item.getId() == 0) {
					inventory.setInventory(slot, item);
				}else{
					inventory.setInventory(slot, Item.get(0));
					inventory.sendContents();
					player.getInventory().addItem(item);
				}
			} else if (27 <= slot && slot <= 35) {
				inventory.sendContents();
				player.getInventory().addItem(item);
			} else if (49 <= slot && slot <= 53) {
				inventory.sendContents();
				player.getInventory().addItem(item);
			} else {
				inventory.setInventory(slot, item);
			}
		} else {
			player.getInventory().sendContents(player);
			inventory.sendContents();
		}
	}
}
