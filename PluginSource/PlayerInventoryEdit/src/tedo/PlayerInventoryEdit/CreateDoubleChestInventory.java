package tedo.PlayerInventoryEdit;

import java.io.IOException;
import java.nio.ByteOrder;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.ContainerOpenPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;

public class CreateDoubleChestInventory extends BaseInventory{

	public boolean open = false;

	public byte windowid = 64;

	public Player player = null;
	public int x = 0;
	public int y = 0;
	public int z = 0;

	public CreateDoubleChestInventory(Player player, int x, int y, int z) {
		super(new BlockEntityChest(player.getLevel().getChunk(x >> 4, z >> 4), new CompoundTag("")), InventoryType.DOUBLE_CHEST);
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = z;
		for (int i = 0; i < 54; i++) {
			setItem(i, Item.get(0));
		}
		setSize(54);
	}

	public void sendChest() {
		UpdateBlockPacket pk = new UpdateBlockPacket();
		pk.x = this.x;
		pk.y = this.y;
		pk.z = this.z;
		pk.blockId = 54;
		pk.blockData = 0;
		pk.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
		this.player.dataPacket(pk);

		pk.x++;
		this.player.dataPacket(pk);

		CompoundTag nbt;
		nbt = new CompoundTag()
				.putString("id", BlockEntity.CHEST)
				.putInt("x", x)
				.putInt("y", y)
				.putInt("z", z)
				.putInt("pairx", x + 1)
				.putInt("pairz", z);

		BlockEntityDataPacket pk1 = new BlockEntityDataPacket();
		pk1.x = x;
		pk1.y = y;
		pk1.z = z;
		try {
			pk1.namedTag = NBTIO.write(nbt, ByteOrder.LITTLE_ENDIAN, true);
		} catch (IOException e) {
            throw new RuntimeException(e);
		}
		this.player.dataPacket(pk1);

		nbt.putInt("pairx", x);
		pk1.x++;
		try {
			pk1.namedTag = NBTIO.write(nbt, ByteOrder.LITTLE_ENDIAN, true);
		} catch (IOException e) {
            throw new RuntimeException(e);
		}
		this.player.dataPacket(pk1);
	}

	public void sendBeforeBlock() {
		Level level = this.player.getLevel();
		Block block1 = level.getBlock(new Vector3(this.x, this.y, this.z));
		Block block2 = level.getBlock(new Vector3(this.x + 1, this.y, this.z));

		UpdateBlockPacket pk = new UpdateBlockPacket();
		pk.x = this.x;
		pk.y = this.y;
		pk.z = this.z;
		pk.blockId = block1.getId();
		pk.blockData = block1.getDamage();
		pk.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
		this.player.dataPacket(pk);

		pk.x++;
		pk.blockId = block2.getId();
		pk.blockData = block2.getDamage();
		this.player.dataPacket(pk);
	}

	public void onOpen() {
		ContainerOpenPacket pk = new ContainerOpenPacket();
		pk.windowid = this.windowid;
		pk.type = 0;
		pk.x = this.x;
		pk.y = this.y;
		pk.z = this.z;
		this.player.dataPacket(pk);
	}

	public void onClose() {
		ContainerClosePacket pk = new ContainerClosePacket();
		pk.windowid = this.windowid;
		this.player.dataPacket(pk);
		this.open = false;
	}

	@Override
	public void onOpen(Player player) {
		onOpen();
		sendContents(player);
	}

	@Override
	public void onClose(Player player) {
		onClose();
	}

	public void removeInventory(Player player) {
		player.removeWindow(this);
	}

	public void setChestName(String name) {
		CompoundTag nbt;
		nbt = getNBT(this.x, this.y, this.z, name, true);

		BlockEntityDataPacket pk = new BlockEntityDataPacket();
		pk.x = this.x;
		pk.y = this.y;
		pk.z = this.z;
		try {
			pk.namedTag = NBTIO.write(nbt, ByteOrder.LITTLE_ENDIAN, true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.player.dataPacket(pk);

		nbt = getNBT(this.x, this.y, this.z, name, false);
		pk.x++;
		try {
			pk.namedTag = NBTIO.write(nbt, ByteOrder.LITTLE_ENDIAN, true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.player.dataPacket(pk);
	}

	public CompoundTag getNBT(int x, int y, int z, String chest, boolean pair) {
		CompoundTag nbt = new CompoundTag()
				.putString("id", BlockEntity.CHEST)
				.putInt("x", this.x)
				.putInt("y", this.y)
				.putInt("z", this.z)
				.putString("CustomName", chest)
				.putInt("pairz", z);
		if (pair) {
			nbt.putInt("pairx", x + 1);
		}else{
			nbt.putInt("pairx", x - 1);
		}
		return nbt;
	}

	public void sendContents() {
		sendContents(this.player);
	}

	public void setInventory(int index, Item item) {
        this.slots.put(index, item.clone());
	}
}
