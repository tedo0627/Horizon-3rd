package tedo.SeichiSystemPlugin.skill;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockBedrock;
import cn.nukkit.block.BlockSlabStone;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import tedo.SeichiSystemPlugin.SeichiSystemPlugin;

public class BaseSkill {

	public SeichiSystemPlugin main;

	public Player player;

	public BaseSkill(SeichiSystemPlugin main, Player player) {
		this.main = main;
		this.player = player;
	}

	public boolean breakBlock(Vector3 pos) {
		Level level = this.player.getLevel();
		Block block = level.getBlock(pos);
		if (block instanceof BlockAir || block instanceof BlockBedrock) {
			return false;
		}
		if (!this.main.level.contains(level.getName())) {
			return false;
		}
		if (block instanceof BlockSlabStone && block.y == 6) {
			return false;
		}
		if (block.y < 6) {
			return false;
		}
		Vector3 upPos = new Vector3(pos.x, pos.y + 1, pos.z);
		Block upBlock = level.getBlock(upPos);
		switch (upBlock.getId()) {
			case 6: case 17: case 18: case 31: case 32: case 37: case 38: case 39:
			case 40: case 50: case 78: case 81: case 83: case 106: case 111: case 161: case 162: case 175:
				breakBlock(upPos);
			case 0: case 7:
				Item item = this.player.getInventory().getItemInHand();
				BlockBreakEvent ev = new BlockBreakEvent(this.player, block, item, false, false);
				this.main.getServer().getPluginManager().callEvent(ev);
				if (!(ev.isCancelled())) {
					if (isAddDamage(item)) {
						item.setDamage(item.getDamage() + 1);
						this.player.getInventory().setItemInHand(item);
					}
					if (item.isTool()) {
						if (item.getDamage() >= item.getMaxDurability()) {
							this.player.getInventory().setItemInHand(Item.get(0, 0, 0));
						}
					}
					sendAir((int) pos.x, (int) pos.y, (int) pos.z);
					level.setBlock(pos, Block.get(0));
					this.main.addCount(this.player, 1);
					this.main.sendStorage(this.player, ev.getDrops());
					BlockEntity blockEntity = level.getBlockEntity(pos);
					if (blockEntity != null) {
						if (blockEntity instanceof InventoryHolder) {
							if (blockEntity instanceof BlockEntityChest) {
								((BlockEntityChest) blockEntity).unpair();
							}
							for (Item chestItem : ((InventoryHolder) blockEntity).getInventory().getContents().values()) {
								level.dropItem(pos, chestItem);
							}
						}
						blockEntity.close();
					}
				}
				return true;
			default:
				this.player.sendTip("§b上から掘って下さい");
				return false;
		}
	}

	public void sendAir(int x, int y, int z) {
		UpdateBlockPacket pk = new UpdateBlockPacket();
		pk.x = x;
		pk.y = y;
		pk.z = z;
		pk.blockId = 0;
		pk.blockData = 0;
		pk.flags = UpdateBlockPacket.FLAG_NONE;
		this.player.dataPacket(pk);
	}

	public boolean isAddDamage(Item item) {
		if (!item.isTool()) {
			return false;
		}
		if (item.hasCompoundTag() && item.getNamedTag().contains("Unbreakable")) {
			return false;
		}
		if ((int) (Math.random() * 3) == 1) {
			if (item.getEnchantment(17) == null) {
				return true;
			} else {
				Enchantment enchant = item.getEnchantment(17);
				int level = enchant.getLevel();
				if ( (int) (Math.random() * level) == 1) {
					return true;
				}
			}
		}
		return false;
	}
}
