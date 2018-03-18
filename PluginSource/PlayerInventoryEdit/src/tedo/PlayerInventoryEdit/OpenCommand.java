package tedo.PlayerInventoryEdit;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

public class OpenCommand extends Command{

	public PlayerInventoryEdit main;

	public Server server;

	public OpenCommand(PlayerInventoryEdit main) {
		super("open");
		this.setPermission("op");
		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_TARGET, false)
		});

		this.main = main;
		this.server = main.getServer();
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールから実行することはできません");
			return false;
		}
		Player player = this.server.getPlayer(args[0]);
		if (player == null) {
			if (new File(this.server.getDataPath() + "players/" + args[0].toLowerCase() + ".dat").exists()) {
				CompoundTag nbt = this.server.getOfflinePlayerData(args[0].toLowerCase());
				offlinePlayerInventory((Player) sender, nbt, args[0]);
			}else{
				sender.sendMessage("§a>>§b " + args[0] + " は存在しません");
				return false;
			}
		}else{
			onlinePlayerInventory(player, (Player) sender);
		}
		return false;
	}

	public void onlinePlayerInventory(Player player, Player opener) {
		int x = (int) opener.x;
		int y = (int) opener.y;
		int z = (int) opener.z;
		PlayerInventory inventory = player.getInventory();
		CreateDoubleChestInventory chest = new CreateDoubleChestInventory(opener, x, y - 2, z);
		chest.sendChest();
		for (int slot = 0; slot < 54; slot++) {
			Item item = inventory.getItem(slot);
			if (0 <= slot && slot <= 8) {
				chest.setItem(slot + 36, item);
			}else if (9 <= slot && slot <= 35) {
				chest.setItem(slot - 9, item);
			}else if (36 <= slot && slot <= 44) {
				chest.setItem(slot - 9, Item.get(102, 0, 1).setCustomName("§a仕切り"));
			}else if (45 <= slot && slot <= 53) {
				chest.setItem(slot, Item.get(102, 0, 1).setCustomName("§a仕切り"));
			}
		};
		Item[] armor = inventory.getArmorContents();
		for (int i = 0; i < armor.length; i++) {
			chest.setItem(i + 45, armor[i]);
		}
		this.main.setInventory(opener, chest);
		this.main.setOnline(opener, player);
		this.main.setName(opener, player.getName().toLowerCase());

		this.main.setNow(player, opener);

		chest.setChestName("§2" + player.getName() + " のインベントリー  §c※クラシック推奨");
		this.server.getScheduler().scheduleDelayedTask(this.main, new Runnable() {
			@Override
			public void run() {
				player.addWindow(chest, (int) chest.windowid);
			}
		}, 3);
	}

	public void offlinePlayerInventory(Player player, CompoundTag nbt, String name) {
		int x = (int) player.x;
		int y = (int) player.y;
		int z = (int) player.z;
		CreateDoubleChestInventory inventory = new CreateDoubleChestInventory(player, x, y - 2, z);
		for (int i = 27; i < 36; i++) {
			inventory.setItem(i, Item.get(102, 0, 1).setCustomName("§a仕切り"));
		}
		for (int i = 49; i < 54; i++) {
			inventory.setItem(i, Item.get(102, 0, 1).setCustomName("§a仕切り"));
		}
		if (nbt.contains("Inventory") && nbt.get("Inventory") instanceof ListTag) {
			ListTag<CompoundTag> inventoryList = nbt.getList("Inventory", CompoundTag.class);
			for (CompoundTag item : inventoryList.getAll()) {
				int slot = item.getByte("Slot");
				if (slot >= 100 && slot < 104) {
					inventory.setItem(slot - 55, NBTIO.getItemHelper(item));
				} else if (slot >= 9 && slot < 18) {
					inventory.setItem(slot + 27, NBTIO.getItemHelper(item));
				} else if (slot >= 18 && slot < 45) {
					inventory.setItem(slot - 18, NBTIO.getItemHelper(item));
				}
			}
		}
		this.main.setInventory(player, inventory);
		this.main.setNBT(player, nbt);
		this.main.setOffline(player, name);

		inventory.sendChest();
		inventory.setChestName("§2" + name + " のインベントリー  §c※クラシック推奨");
		this.server.getScheduler().scheduleDelayedTask(this.main, new Runnable() {
			@Override
			public void run() {
				player.addWindow(inventory, (int) inventory.windowid);
			}
		}, 3);
	}
}
