package tedo.OtherPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.network.protocol.AdventureSettingsPacket;
import cn.nukkit.network.protocol.AvailableCommandsPacket;
import cn.nukkit.network.protocol.BatchPacket;
import cn.nukkit.network.protocol.CommandBlockUpdatePacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.FullChunkDataPacket;
import cn.nukkit.network.protocol.InteractPacket;
import cn.nukkit.network.protocol.LevelEventPacket;
import cn.nukkit.network.protocol.MoveEntityPacket;
import cn.nukkit.network.protocol.MovePlayerPacket;
import cn.nukkit.network.protocol.PlayerActionPacket;
import cn.nukkit.network.protocol.SetEntityDataPacket;
import cn.nukkit.plugin.PluginBase;
import tedo.OtherPlugin.block.BlockDropper;
import tedo.OtherPlugin.blockentity.BlockEntityDropper;

public class OtherPlugin extends PluginBase implements Listener{

	public HashMap<Player, Integer> time = new HashMap<Player, Integer>();

	public ArrayList<Player> join = new ArrayList<Player>();

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);

		Block.list[Block.DROPPER] = BlockDropper.class;
		BlockEntity.registerBlockEntity("Dropper", BlockEntityDropper.class);
		Item.list[Item.DROPPER] = BlockDropper.class;
	}

	@EventHandler
	public void onPlayerPreLogin(PlayerPreLoginEvent event) {
		Player player = event.getPlayer();
		if (this.time.containsKey(player)) {
			player.kick("§b貴方はあと" + this.time.get(player) + "秒鯖に入ることができません", false);
			return;
		}
		if (!player.isOp()) {
			if (this.getServer().getOnlinePlayers().size() > 40) {
				player.kick("§b最大人数(40人)に達したため、鯖に1分間ログインできなくなりました");
				getServer().getScheduler().scheduleDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						timeTask(player);
					}
				}, 20);
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		this.join.add(player);
		player.sendCommandData();
	}

//	@EventHandler
	public void onDataPacketReceive(DataPacketReceiveEvent event) {
		DataPacket packet = event.getPacket();
		if (packet instanceof BatchPacket) {
		} else if (packet instanceof InteractPacket) {
		} else if (packet instanceof MovePlayerPacket) {
		} else if (packet instanceof MoveEntityPacket) {
		} else if (packet instanceof SetEntityDataPacket) {
		} else if (packet instanceof LevelEventPacket) {
		} else if (packet instanceof FullChunkDataPacket) {
		} else if (packet instanceof PlayerActionPacket) {
		} else if (packet instanceof CommandBlockUpdatePacket) {
			CommandBlockUpdatePacket pk = (CommandBlockUpdatePacket) packet;
			System.out.print(pk.command);
		}
	}

	@EventHandler
	public void DataPacketSend(DataPacketSendEvent event) {
		DataPacket packet = event.getPacket();
		if (packet instanceof AdventureSettingsPacket) {
			Player player = event.getPlayer();
			if (!this.join.contains(player)) {
				getServer().getScheduler().scheduleDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						permissionTask(player);
					}
				}, 20);
				event.setCancelled();
			}
		} else if (packet instanceof AvailableCommandsPacket) {
			Player player = event.getPlayer();
			if (!this.join.contains(player)) {
				((AvailableCommandsPacket) packet).commands = "";
			}
		}
	}

	public void permissionTask(Player player) {
		if (player.isOnline()) {
			player.getAdventureSettings().update();
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		List<Block> b = event.getBlockList();
		b.clear();
		event.setBlockList(b);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setKeepInventory(true);
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.toWeatherState()) {
			event.setCancelled();
		}
	}

	public void timeTask(Player player) {
		int time = this.time.get(player);
		if (time < 1) {
			this.time.remove(player);
			return;
		}
		this.time.put(player, time);
		getServer().getScheduler().scheduleDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				timeTask(player);
			}
		}, 20);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Item item = event.getItem();
		if (item.getEnchantment(Enchantment.ID_SILK_TOUCH) != null && item.getEnchantment(Enchantment.ID_SILK_TOUCH).getLevel() > 0) {
			Block block = event.getBlock();
			int id = block.getId();
			Item[] items = new Item[1];
			switch (id) {
				case 1:
					if (item.getDamage() == 0) {
						if (item.isPickaxe()) {
							items[0] = Item.get(1, 0, 1);
							event.setDrops(items);
						}
					}
				break;

				case 2:
					items[0] = Item.get(2, 0, 1);
					event.setDrops(items);
				break;

				case 13:
					items[0] = Item.get(13, 0, 1);
					event.setDrops(items);
				break;

				case 16:
					if (item.isPickaxe()) {
						items[0] = Item.get(16, 0, 1);
						event.setDrops(items);
					}
				break;

				case 18:
					items[0] = Item.get(18, block.getDamage(), 1);
					event.setDrops(items);
				break;

				case 20:
					items[0] = Item.get(20, 0, 1);
					event.setDrops(items);
				break;

				case 21:
					if (item.isPickaxe()) {
						items[0] = Item.get(21, 0, 1);
						event.setDrops(items);
					}
				break;

				case 56:
					if (item.isPickaxe()) {
						items[0] = Item.get(56, 0, 1);
						event.setDrops(items);
					}
				break;

				case 73:
					if (item.isPickaxe()) {
						items[0] = Item.get(73, 0, 1);
						event.setDrops(items);
					}
				break;

				case 74:
					if (item.isPickaxe()) {
						items[0] = Item.get(74, 0, 1);
						event.setDrops(items);
					}
				break;

				case 79:
					if (item.isPickaxe()) {
						items[0] = Item.get(79, 0, 1);
						event.setDrops(items);
					}
				break;

				case 89:
					items[0] = Item.get(89, 0, 1);
					event.setDrops(items);
				break;

				case 102:
					items[0] = Item.get(102, 0, 1);
					event.setDrops(items);
				break;

				case 103:
					items[0] = Item.get(103, 0, 1);
					event.setDrops(items);
				break;

				case 129:
					items[0] = Item.get(129, 0, 1);
					event.setDrops(items);
				break;

				case 130:
					if (item.isPickaxe()) {
						items[0] = Item.get(130, 0, 1);
						event.setDrops(items);
					}
				break;

				case 153:
					if (item.isPickaxe()) {
						items[0] = Item.get(153, 0, 1);
						event.setDrops(items);
					}
				break;

				case 161:
					items[0] = Item.get(161, item.getDamage(), 1);
					event.setDrops(items);
				break;
			}
		}else {
			if (item.getEnchantment(Enchantment.ID_FORTUNE_DIGGING) != null && item.isPickaxe()) {
				int level = item.getEnchantment(Enchantment.ID_FORTUNE_DIGGING).getLevel();
				level = level > 3 ? 3 : level;
				Block block = event.getBlock();
				Item[] items = new Item[1];
				switch (block.getId()) {
					case 14:
						items[0] = Item.get(266, 0, 1);
						event.setDrops(items);
					break;

					case 15:
						items[0] = Item.get(265, 0, 1);
						event.setDrops(items);
					break;

					case 16:
						items[0] = Item.get(263, 0, fortune(level));
						event.setDrops(items);
					break;

					case 21:
						items[0] = Item.get(351, 4, fortune(level));
						event.setDrops(items);
					break;

					case 56:
						items[0] = Item.get(264, 0, fortune(level));
						event.setDrops(items);
					break;

					case 73:
						items[0] = Item.get(331, 0, fortune(level));
						event.setDrops(items);
					break;

					case 74:
						items[0] = Item.get(331, 0, fortune(level));
						event.setDrops(items);
					break;

					case 129:
						items[0] = Item.get(388, 0, fortune(level));
						event.setDrops(items);
					break;

					case 153:
						items[0] = Item.get(406, 0, fortune(level));
						event.setDrops(items);
					break;
				}
			}
		}
	}

	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		switch(command.getName()){
			case "id":
				Player player = (Player) sender;
				Item item = player.getInventory().getItemInHand();
				sender.sendMessage("§a>>§b貴方が手に持ってるアイテムのIDは" + String.valueOf(item.getId()) + ":" + String.valueOf(item.getDamage()) + "です");
			break;
		}
		return false;
	}

	public int fortune(int level) {
		switch (new Random().nextInt(level)) {
			case 0:
			case 1:
				return 1;

			case 2:
				return 2;

			case 3:
				return 3;

			default:
				return 4;
		}
	}
}
