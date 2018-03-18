package tedo.GatyaSystem;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityArmorChangeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemEmerald;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.potion.Effect;
import tedo.GatyaSystem.command.ChangeCommand;
import tedo.GatyaSystem.command.InventoryClearCommand;
import tedo.GatyaSystem.command.PrizeCommand;
import tedo.GatyaSystem.item.ArousalStone;
import tedo.GatyaSystem.item.BaseItem;
import tedo.GatyaSystem.item.BigRare.Depression;
import tedo.GatyaSystem.item.BigRare.Destroyer;
import tedo.GatyaSystem.item.BigRare.Gungnir;
import tedo.GatyaSystem.item.BigRare.IceBurn;
import tedo.GatyaSystem.item.BigRare.Kim;
import tedo.GatyaSystem.item.BigRare.Loading;
import tedo.GatyaSystem.item.BigRare.Mjolner;
import tedo.GatyaSystem.item.BigRare.Revolution;
import tedo.GatyaSystem.item.BigRare.StarPlatina;
import tedo.GatyaSystem.item.HorizonRare.Ares;
import tedo.GatyaSystem.item.HorizonRare.Athena;
import tedo.GatyaSystem.item.HorizonRare.Brionac;
import tedo.GatyaSystem.item.HorizonRare.Ceres;
import tedo.GatyaSystem.item.HorizonRare.Dios;
import tedo.GatyaSystem.item.HorizonRare.Erebos;
import tedo.GatyaSystem.item.HorizonRare.Hades;
import tedo.GatyaSystem.item.HorizonRare.Hermes;
import tedo.GatyaSystem.item.HorizonRare.Kronos;
import tedo.GatyaSystem.item.HorizonRare.Oneiros;
import tedo.GatyaSystem.item.HorizonRare.Orpheus;
import tedo.GatyaSystem.item.HorizonRare.Poseidon;
import tedo.GatyaSystem.item.HorizonRare.Risanaut;
import tedo.GatyaSystem.item.HorizonRare.Themis;
import tedo.GatyaSystem.item.HorizonRarePlus.BrionacPlus;
import tedo.GatyaSystem.item.HorizonRarePlus.DiosPlus;
import tedo.GatyaSystem.item.HorizonRarePlus.KronosPlus;
import tedo.GatyaSystem.item.HorizonRarePlus.OrpheusPlus;
import tedo.GatyaSystem.item.HorizonRarePlus.RisanautPlus;
import tedo.GatyaSystem.item.HorizonRarePlus.ThemisPlus;
import tedo.GatyaSystem.item.Rare.Devotion;
import tedo.GatyaSystem.item.Rare.Infinite;
import tedo.GatyaSystem.item.Rare.JetStream;
import tedo.GatyaSystem.item.Rare.MineFortuna;
import tedo.GatyaSystem.item.Rare.Normal;
import tedo.GatyaSystem.item.Rare.Phoenix;
import tedo.GatyaSystem.item.Rare.Reametaru;
import tedo.GatyaSystem.item.Rare.Standard;
import tedo.GatyaSystem.item.Rare.StoneColosseum;
import tedo.GatyaSystem.prize.PrizeBigRare;
import tedo.GatyaSystem.prize.PrizeHorizonRare;
import tedo.GatyaSystem.prize.PrizeRare;
import tedo.ItemStorage.ItemStorage;

public class GatyaSystem extends PluginBase implements Listener{

	public static GatyaSystem main;

	public ItemStorage item;

	public HashMap<Player, Integer> drop = new HashMap<Player, Integer>();

	public void onEnable(){
		this.item = (ItemStorage) this.getServer().getPluginManager().getPlugin("ItemStorage");
		if (this.item == null) {
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(new Status(), this);

		this.getServer().getCommandMap().register("GatyaSystem", new PrizeCommand());
		this.getServer().getCommandMap().register("GatyaSystem", new ChangeCommand());
		this.getServer().getCommandMap().register("GatyaSystem", new InventoryClearCommand());

		main = this;

		Item.list[10000] = ArousalStone.class;

		Item.list[10001] = KronosPlus.class;
		Item.list[10002] = BrionacPlus.class;
		Item.list[10003] = RisanautPlus.class;
		Item.list[10004] = ThemisPlus.class;
		Item.list[10005] = DiosPlus.class;
		Item.list[10006] = OrpheusPlus.class;

		Item.list[10011] = Kronos.class;
		Item.list[10012] = Brionac.class;
		Item.list[10013] = Risanaut.class;
		Item.list[10014] = Themis.class;
		Item.list[10015] = Dios.class;
		Item.list[10016] = Orpheus.class;
		Item.list[10017] = Athena.class;
		Item.list[10018] = Ceres.class;
		Item.list[10019] = Hermes.class;
		Item.list[10020] = Poseidon.class;
		Item.list[10021] = Erebos.class;
		Item.list[10022] = Ares.class;
		Item.list[10023] = Oneiros.class;
		Item.list[10024] = Hades.class;

		Item.list[10051] = Mjolner.class;
		Item.list[10052] = Gungnir.class;
		Item.list[10053] = Destroyer.class;
		Item.list[10054] = Revolution.class;
		Item.list[10055] = Loading.class;
		Item.list[10056] = IceBurn.class;
		Item.list[10057] = Kim.class;
		Item.list[10058] = StarPlatina.class;
		Item.list[10059] = Depression.class;

		Item.list[10071] = Standard.class;
		Item.list[10072] = Reametaru.class;
		Item.list[10073] = Devotion.class;
		Item.list[10074] = StoneColosseum.class;
		Item.list[10075] = Infinite.class;
		Item.list[10076] = JetStream.class;
		Item.list[10077] = MineFortuna.class;
		Item.list[10078] = Phoenix.class;
		Item.list[10079] = Normal.class;


		Item.addCreativeItem(Item.get(10000));

		Item.addCreativeItem(Item.get(10001));
		Item.addCreativeItem(Item.get(10002));
		Item.addCreativeItem(Item.get(10003));
		Item.addCreativeItem(Item.get(10004));
		Item.addCreativeItem(Item.get(10005));
		Item.addCreativeItem(Item.get(10006));

		Item.addCreativeItem(Item.get(10011));
		Item.addCreativeItem(Item.get(10012));
		Item.addCreativeItem(Item.get(10013));
		Item.addCreativeItem(Item.get(10014));
		Item.addCreativeItem(Item.get(10015));
		Item.addCreativeItem(Item.get(10016));
		Item.addCreativeItem(Item.get(10017));
		Item.addCreativeItem(Item.get(10018));
		Item.addCreativeItem(Item.get(10019));
		Item.addCreativeItem(Item.get(10020));
		Item.addCreativeItem(Item.get(10021));
		Item.addCreativeItem(Item.get(10022));
		Item.addCreativeItem(Item.get(10023));
		Item.addCreativeItem(Item.get(10024));

		Item.addCreativeItem(Item.get(10051));
		Item.addCreativeItem(Item.get(10052));
		Item.addCreativeItem(Item.get(10053));
		Item.addCreativeItem(Item.get(10054));
		Item.addCreativeItem(Item.get(10055));
		Item.addCreativeItem(Item.get(10056));
		Item.addCreativeItem(Item.get(10057));
		Item.addCreativeItem(Item.get(10058));
		Item.addCreativeItem(Item.get(10059));

		Item.addCreativeItem(Item.get(10071));
		Item.addCreativeItem(Item.get(10072));
		Item.addCreativeItem(Item.get(10073));
		Item.addCreativeItem(Item.get(10074));
		Item.addCreativeItem(Item.get(10075));
		Item.addCreativeItem(Item.get(10076));
		Item.addCreativeItem(Item.get(10077));
		Item.addCreativeItem(Item.get(10078));
		Item.addCreativeItem(Item.get(10079));
	}

	@EventHandler
	public void onPlayerIntaract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Item item = player.getInventory().getItemInHand();
		int count = 0;
		Block block = event.getBlock();
		if (item instanceof ItemEmerald) {
			event.setCancelled();
			if (player.isSneaking()) {
				for (int i = item.getCount(); i > 0; i--) {
					if (Gatya(player)) {
						count++;
					}
				}
				player.getInventory().setItemInHand(Item.get(0));
				player.sendMessage("§a>>§b" + item.getCount() + "回引き、" + count + "個はずれました；；");
			}else{
				if (Gatya(player)) {
					player.sendMessage("§a>>§bはずれました；；");
				}
				int c = item.getCount();
				if (c == 1) {
					player.getInventory().setItemInHand(Item.get(0));
				}else{
					item.setCount(c - 1);
					player.getInventory().setItemInHand(item);
				}
			}
		} else if (block.getId() == 246) {
			if (hasItem(player.getInventory())) {
				if (!BaseItem.isOwner(item, player)) {
					player.sendMessage("§a>>§bほかの人のガチャの景品では覚醒できません");
					return;
				}
				if (BaseItem.isSame(item, "Kronos")) {
					removeItem(player.getInventory());
					if (GatyaRandom.exe(10, 1)) {
						KronosPlus prize = new KronosPlus(0, 1);
						prize.setOwner(player.getName());
						player.getInventory().setItemInHand(prize);
						player.sendMessage("§a>>§b覚醒に成功しました");
					} else {
						player.sendMessage("§a>>§b覚醒に失敗しました");
					}
				} else if (BaseItem.isSame(item, "Brionac")) {
					removeItem(player.getInventory());
					if (GatyaRandom.exe(10, 1)) {
						BrionacPlus prize = new BrionacPlus(0, 1);
						prize.setOwner(player.getName());
						player.getInventory().setItemInHand(prize);
						player.sendMessage("§a>>§b覚醒に成功しました");
					} else {
						player.sendMessage("§a>>§b覚醒に失敗しました");
					}
				} else if (BaseItem.isSame(item, "Risanaut")) {
					removeItem(player.getInventory());
					if (GatyaRandom.exe(10, 1)) {
						RisanautPlus prize = new RisanautPlus(0, 1);
						prize.setOwner(player.getName());
						player.getInventory().setItemInHand(prize);
						player.sendMessage("§a>>§b覚醒に成功しました");
					} else {
						player.sendMessage("§a>>§b覚醒に失敗しました");
					}
				} else if (BaseItem.isSame(item, "Themis")) {
					removeItem(player.getInventory());
					if (GatyaRandom.exe(10, 1)) {
						ThemisPlus prize = new ThemisPlus(0, 1);
						prize.setOwner(player.getName());
						player.getInventory().setItemInHand(prize);
						player.sendMessage("§a>>§b覚醒に成功しました");
					} else {
						player.sendMessage("§a>>§b覚醒に失敗しました");
					}
				} else if (BaseItem.isSame(item, "Dios")) {
					removeItem(player.getInventory());
					if (GatyaRandom.exe(10, 1)) {
						DiosPlus prize = new DiosPlus(0, 1);
						prize.setOwner(player.getName());
						player.getInventory().setItemInHand(prize);
						player.sendMessage("§a>>§b覚醒に成功しました");
					} else {
						player.sendMessage("§a>>§b覚醒に失敗しました");
					}
				} else if (BaseItem.isSame(item, "Orpheus")) {
					removeItem(player.getInventory());
					if (GatyaRandom.exe(10, 1)) {
						OrpheusPlus prize = new OrpheusPlus(0, 1);
						prize.setOwner(player.getName());
						player.getInventory().setItemInHand(prize);
						player.sendMessage("§a>>§b覚醒に成功しました");
					} else {
						player.sendMessage("§a>>§b覚醒に失敗しました");
					}
				} else {
					player.sendMessage("§a>>§bそのアイテムは覚醒できません");
				}
			} else {
				player.sendMessage("§a>>§b覚醒石がないと覚醒することはできません");
			}
		}
	}

	@EventHandler
	public void onEntityArmorChange(EntityArmorChangeEvent event){
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Item item = event.getOldItem();
			if (item.getId() != 0 && item.hasCompoundTag() && item.getNamedTag().contains("Rank")) {
				if (BaseItem.isSame(item, "Athena")) {
					if (player.hasEffect(23)) player.removeEffect(23);
					if (player.hasEffect(16)) player.removeEffect(16);
				} else if (BaseItem.isSame(item, "Ceres")) {
					if (player.hasEffect(11)) player.removeEffect(11);
				} else if (BaseItem.isSame(item, "Hermes")) {
					if (player.hasEffect(8)) player.removeEffect(8);
					if (player.hasEffect(10)) player.removeEffect(10);
				} else if (BaseItem.isSame(item, "Poseidon")) {
					if (player.hasEffect(1)) player.removeEffect(1);
				} else if (BaseItem.isSame(item, "Erebos")) {
					if (player.hasEffect(12)) player.removeEffect(12);
				}
			}
			item = event.getNewItem();
			if (item.getId() != 0 && item.hasCompoundTag() && item.getNamedTag().contains("Rank")) {
				Effect effect;
				if (BaseItem.isSame(item, "Athena")) {
					effect = Effect.getEffect(23).setDuration(20 * 10000).setAmplifier(1).setVisible(false);
					player.addEffect(effect);
					effect = Effect.getEffect(16).setDuration(20 * 10000).setAmplifier(1).setVisible(false);
					player.addEffect(effect);
				} else if (BaseItem.isSame(item, "Ceres")) {
					effect = Effect.getEffect(11).setDuration(20 * 10000).setAmplifier(1).setVisible(false);
					player.addEffect(effect);
				} else if (BaseItem.isSame(item, "Hermes")) {
					effect = Effect.getEffect(8).setDuration(20 * 10000).setAmplifier(5).setVisible(false);
					player.addEffect(effect);
					effect = Effect.getEffect(10).setDuration(20 * 10000).setAmplifier(1).setVisible(false);
					player.addEffect(effect);
				} else if (BaseItem.isSame(item, "Poseidon")) {
					effect = Effect.getEffect(1).setDuration(20 * 10000).setAmplifier(10).setVisible(false);
					player.addEffect(effect);
				} else if (BaseItem.isSame(item, "Erebos")) {
					effect = Effect.getEffect(12).setDuration(20 * 10000).setAmplifier(1).setVisible(false);
					player.addEffect(effect);
				}
			}
		}
	}

	public boolean hasItem(Inventory inventory) {
		for (Item item : inventory.getContents().values()) {
			if (BaseItem.isSame(item, "ArousalStone")) {
				return true;
			}
		}
		return false;
	}

	public void removeItem(Inventory inventory) {
		for (int slot : inventory.getContents().keySet()) {
			Item item = inventory.getItem(slot);
			if (BaseItem.isSame(item, "ArousalStone")) {
				int count = item.getCount();
				count--;
				if (count < 1) {
					inventory.setItem(slot, Item.get(0));
				} else {
					item.setCount(count);
					inventory.setItem(slot, item);
				}
				return;
			}
		}
	}

	public boolean Gatya(Player player) {
		int r = GatyaRandom.getGatyRandom();
		if (r == 1) {
			PrizeHorizonRare.add(player);
			player.setSubtitle("§bおめでとうございます");
			player.sendTitle("§aHorizonRareを当てました");
			getServer().broadcastMessage("§a>>§b" + player.getName() + "がHorizonRareを当てました");
			return false;
		} else if (r == 2)  {
			if (Status.isPrize(player)) {
				Item item = Item.get(388, 0, 5);
				this.item.addItem(player, item);
			}else{
				PrizeBigRare.add(player);
			}
			player.sendMessage("§a>>§b大当たり");
			return false;
		} else if (r == 3) {
			if (Status.isPrize(player)) {
				Item item = Item.get(388, 0, 1);
				this.item.addItem(player, item);
			}else{
				PrizeRare.add(player);
			}
			player.sendMessage("§a>>§b当たり");
			return false;
		}else{
			return true;
		}
	}
}
