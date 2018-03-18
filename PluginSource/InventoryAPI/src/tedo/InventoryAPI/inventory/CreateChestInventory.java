package tedo.InventoryAPI.inventory;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;

public class CreateChestInventory extends CreateBaseInventory{

	public CreateChestInventory(Player player, int x, int y, int z) {
		super(new BlockEntityChest(player.getLevel().getChunk(x >> 4, z >> 4), new CompoundTag("")), InventoryType.CHEST);
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockId = 54;
		this.blockEntity = BlockEntity.CHEST;
		setSize(27);
		for (int i = 0; i < 27; i++) {
			setItem(i, Item.get(0));
		}
	}
}
