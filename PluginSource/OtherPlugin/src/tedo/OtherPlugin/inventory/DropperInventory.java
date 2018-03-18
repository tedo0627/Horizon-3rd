package tedo.OtherPlugin.inventory;

import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.inventory.InventoryType;
import tedo.OtherPlugin.blockentity.BlockEntityDropper;

public class DropperInventory extends ContainerInventory{

	public DropperInventory(BlockEntityDropper blockentity) {
		super(blockentity, InventoryType.DROPPER);
	}

	@Override
	public BlockEntityDropper getHolder() {
		return (BlockEntityDropper) super.getHolder();
	}
}
