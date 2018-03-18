package tedo.PlayerInventoryEdit;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.ContainerSetSlotPacket;
import cn.nukkit.network.protocol.DataPacket;

public class OnlineContents implements Listener{

	public PlayerInventoryEdit main;

	public OnlineContents(PlayerInventoryEdit main) {
		this.main = main;
	}

	@EventHandler
	public void onDataPacketReiceve(DataPacketReceiveEvent event) {
		DataPacket pk = event.getPacket();
		if (pk instanceof ContainerSetSlotPacket) {
			Player player = event.getPlayer();
			if (this.main.hasOnline(player)) {
				ContainerSetSlotPacket pk1 = (ContainerSetSlotPacket) pk;
				int slot = pk1.slot;
				Item item = pk1.item;
				if (pk1.windowid == 64) {
					CreateDoubleChestInventory inventory = this.main.getInventory(player);
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
						}
						inventory.sendContents();
						return;
					}
					event.setCancelled();
					Player target = this.main.getOnline(player);
					if (!target.isOnline()) {
						OfflineContents.setInventoryItem(player, pk1, inventory);
						return;
					}
					if (27 <= slot && slot <= 35) {
						inventory.sendContents();
						player.getInventory().addItem(item);
					}else if (49 <= slot && slot <= 53) {
						inventory.sendContents();
						player.getInventory().addItem(item);
					}else if (45 == slot) {
						if (item.isHelmet() || item.getId() == 0) {
							target.getInventory().setArmorItem(slot - 45, item);
							target.getInventory().sendArmorContents(target);
							inventory.setInventory(slot, item);
						}else{
							inventory.setInventory(slot, Item.get(0));
							inventory.sendContents();
							player.getInventory().addItem(item);
						}
					}else if (46 == slot) {
						if (item.isChestplate() || item.getId() == 0) {
							target.getInventory().setArmorItem(slot - 45, item);
							target.getInventory().sendArmorContents(target);
							inventory.setInventory(slot, item);
						}else{
							inventory.setInventory(slot, Item.get(0));
							inventory.sendContents();
							player.getInventory().addItem(item);
						}
					}else if (47 == slot) {
						if (item.isLeggings() || item.getId() == 0) {
							target.getInventory().setArmorItem(slot - 45, item);
							target.getInventory().sendArmorContents(target);
							inventory.setInventory(slot, item);
						}else{
							inventory.setInventory(slot, Item.get(0));
							inventory.sendContents();
							player.getInventory().addItem(item);
						}
					}else if (48 == slot) {
						if (item.isBoots() || item.getId() == 0) {
							target.getInventory().setArmorItem(slot - 45, item);
							target.getInventory().sendArmorContents(target);
							inventory.setInventory(slot, item);
						}else{
							inventory.setInventory(slot, Item.get(0));
							inventory.sendContents();
							player.getInventory().addItem(item);
						}
					}else if (36 <= slot && slot <= 44) {
						target.getInventory().setItem(slot - 36, item);
						target.getInventory().sendContents(target);
						inventory.setInventory(slot, item);
					}else if (0 <= slot && slot <= 26) {
						target.getInventory().setItem(slot + 9, item);
						target.getInventory().sendContents(target);
						inventory.setInventory(slot, item);
					}
				}else if (pk1.windowid == 0) {
					if (item.getId() == 102 && item.getCustomName().equals("§a仕切り")) {
						if (player.getInventory().getItem(slot).getId() != 0) {
							player.getInventory().setItem(slot, Item.get(0));
						}
						player.getInventory().sendContents(player);
						return;
					}
					player.getInventory().setItem(slot, item);
				}else{
					player.getInventory().sendContents(player);
					this.main.getInventory(player).sendContents();
				}
			} else if (true) {

			}
		}else if (pk instanceof ContainerClosePacket) {
			Player player = event.getPlayer();
			if (!this.main.hasOnline(player)) return;

			this.main.getInventory(player).removeInventory(player);
			this.main.getInventory(player).sendBeforeBlock();

			if (!this.main.getOnline(player).isOnline()) {
				offlineSave(this.main.getName(player), this.main.getInventory(player));
			} else {
				this.main.removeNow(this.main.getOnline(player));
			}

			this.main.removeInventory(player);
			this.main.removeOnline(player);
			this.main.removeName(player);
		}
	}

	@EventHandler
	public void onDataPacketSend(DataPacketSendEvent event) {
		DataPacket packet = event.getPacket();
		if (packet instanceof ContainerSetSlotPacket) {
			Player player = event.getPlayer();
			if (!this.main.hasNow(player)) {
				return;
			}
			if (this.main.getNow(player) == null) {
				this.main.removeNow(player);
				return;
			}
			event.setCancelled();
			ContainerSetSlotPacket pk = (ContainerSetSlotPacket) packet;
			if (pk.windowid == 0) {
				int slot = pk.slot;
				Item item = pk.item;
				Player opener = this.main.getNow(player);
				CreateDoubleChestInventory inventory = this.main.getInventory(opener);
				player.getInventory().slots.put(slot, item);
				if (slot >= 0 && slot < 9) {
					inventory.setInventory(slot + 36, item);
				} else if (slot >= 9 && slot < 36) {
					inventory.setInventory(slot - 9, item);
				} else if (slot >= 36 && slot < 40) {
					inventory.setInventory(slot + 9, item);
				}
				inventory.sendContents();
			}
		}
	}

	public void offlineSave(String name, CreateDoubleChestInventory inventory) {
		CompoundTag nbt = this.main.getServer().getOfflinePlayerData(name);
		nbt.putList(new ListTag<CompoundTag>("Inventory"));

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

		this.main.getServer().saveOfflinePlayerData(name, nbt);
	}
}
