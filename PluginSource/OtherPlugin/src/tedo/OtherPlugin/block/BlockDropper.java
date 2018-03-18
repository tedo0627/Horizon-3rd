package tedo.OtherPlugin.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockSolid;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import tedo.OtherPlugin.blockentity.BlockEntityDropper;

public class BlockDropper extends BlockSolid {

	public BlockEntityDropper blockentity;

	public boolean isActive = false;

	public BlockDropper() {
		super(0);
	}

	public BlockDropper(int meta) {
		super(meta);
	}

	@Override
	public int getId() {
		return DROPPER;
	}

	@Override
	public String getName() {
		return "Dropper";
	}

	@Override
	public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
		BlockFace facing = face.getOpposite();
		if (facing == BlockFace.UP) {
			facing = BlockFace.DOWN;
		}
		this.meta = facing.getIndex();
		this.level.setBlock(this, this);

		CompoundTag nbt = new CompoundTag()
				.putList(new ListTag<>("Items"))
				.putString("id", "Dropper")
				.putInt("x", (int) this.x)
				.putInt("y", (int) this.y)
				.putInt("z", (int) this.z);

		this.blockentity = new BlockEntityDropper(this.level.getChunk(this.getFloorX() >> 4, this.getFloorZ() >> 4), nbt);
		System.out.print("test1");
		return true;
	}

	@Override
	public boolean onActivate(Item item, Player player) {
		System.out.print("test2");
		BlockEntity blockEntity = this.level.getBlockEntity(this);

		if (blockEntity instanceof BlockEntityDropper) {
			return player.addWindow(((BlockEntityDropper) blockEntity).getInventory()) != -1;
		}

		return false;
	}

	@Override
	public int onUpdate(int type) {
		if (type == Level.BLOCK_UPDATE_NORMAL || type == Level.BLOCK_UPDATE_REDSTONE) {
			if (this.level.isBlockPowered(this)) {
				if (!this.isActive) {
					this.isActive = true;
					this.blockentity.dropItem();
					return 1;
				}
			} else {
				this.isActive = false;
			}
		}
		return 0;
	}

	@Override
	public double getResistance() {
		return 1.5;
	}

	@Override
	public double getHardness() {
		return 0.3;
	}
}
