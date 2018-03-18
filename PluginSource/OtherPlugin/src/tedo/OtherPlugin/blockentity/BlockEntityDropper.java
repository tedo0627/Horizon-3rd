package tedo.OtherPlugin.blockentity;

import java.util.Random;

import cn.nukkit.block.BlockAir;
import cn.nukkit.blockentity.BlockEntityContainer;
import cn.nukkit.blockentity.BlockEntityNameable;
import cn.nukkit.blockentity.BlockEntitySpawnable;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.SmokeParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import tedo.OtherPlugin.inventory.DropperInventory;

public class BlockEntityDropper extends BlockEntitySpawnable implements InventoryHolder, BlockEntityContainer, BlockEntityNameable {

	public DropperInventory inventory;

	public BlockEntityDropper(FullChunk chunk, CompoundTag nbt) {
		super(chunk, nbt);
		this.inventory = new DropperInventory(this);
		this.inventory.setSize(9);

		if (!this.namedTag.contains("Items") || !(this.namedTag.get("Items") instanceof ListTag)) {
			this.namedTag.putList(new ListTag<CompoundTag>("Items"));
		}

		for (int i = 0; i < this.getSize(); i++) {
			this.inventory.setItem(i, this.getItem(i));
		}

		this.scheduleUpdate();
	}

	@Override
	public CompoundTag getSpawnCompound() {
		CompoundTag c = new CompoundTag()
				.putString("id", "Dropper")
				.putInt("x", (int) this.x)
				.putInt("y", (int) this.y)
				.putInt("z", (int) this.z);

		if (this.hasName()) {
			c.put("CustomName", this.namedTag.get("CustomName"));
		}

		return c;
	}

	@Override
	public boolean isBlockEntityValid() {
		return false;
	}

	public DropperInventory getInventory() {
		return inventory;
	}

	@Override
	public String getName() {
		return this.hasName() ? this.namedTag.getString("CustomName") : "Dropper";
	}

	@Override
	public boolean hasName() {
		return this.namedTag.contains("CustomName");
	}

	@Override
	public void setName(String name) {
		if (name == null || name.equals("")) {
			this.namedTag.remove("CustomName");
			return;
		}
		this.namedTag.putString("CustomName", name);
	}

	public int getSlotIndex(int index) {
		ListTag<CompoundTag> list = this.namedTag.getList("Items", CompoundTag.class);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getByte("Slot") == index) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Item getItem(int index) {
		int i = this.getSlotIndex(index);
		if (i < 0) {
			return new ItemBlock(new BlockAir(), 0, 0);
		} else {
			CompoundTag data = (CompoundTag) this.namedTag.getList("Items").get(i);
			return NBTIO.getItemHelper(data);
		}
	}

	@Override
	public void setItem(int index, Item item) {
		int i = this.getSlotIndex(index);
		CompoundTag d = NBTIO.putItemHelper(item, index);
		if (item.getId() == Item.AIR || item.getCount() <= 0) {
			if (i >= 0) {
				this.namedTag.getList("Items").getAll().remove(i);
			}
		} else if (i < 0) {
			(this.namedTag.getList("Items", CompoundTag.class)).add(d);
		} else {
			(this.namedTag.getList("Items", CompoundTag.class)).add(i, d);
		}
	}

	@Override
	public int getSize() {
		return 9;
	}

	@Override
	public void saveNBT() {
		this.namedTag.putList(new ListTag<CompoundTag>("Items"));
		for (int index = 0; index < this.getSize(); index++) {
			this.setItem(index, this.inventory.getItem(index));
		}
	}

	public void dropItem() {
		Item item = this.getInventory().getItem(new Random().nextInt(9));
		Item drop = item.clone();
		drop.setCount(1);
		this.getInventory().removeItem(drop);
		Vector3 pos = new Vector3(this.x, this.y + 1, this.z);
		this.level.dropItem(pos, drop);
		this.level.addParticle(new SmokeParticle(pos));
	}
}
