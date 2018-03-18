package tedo.InventoryAPI.inventory;

import java.io.IOException;
import java.nio.ByteOrder;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;

public class CreateDoubleChestInventory extends CreateBaseInventory{

	public CreateDoubleChestInventory(Player player, int x, int y, int z) {
		super(new BlockEntityChest(player.getLevel().getChunk(x >> 4, z >> 4), new CompoundTag("")), InventoryType.DOUBLE_CHEST);
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockId = 54;
		this.blockEntity = BlockEntity.CHEST;
		for (int i = 0; i < 54; i++) {
			setItem(i, Item.get(0));
		}
		setSize(54);
	}

	@Override
	public void sendBlock() {
		super.sendBlock();
		UpdateBlockPacket pk = new UpdateBlockPacket();
		pk.x = this.x + 1;
		pk.y = this.y;
		pk.z = this.z;
		pk.blockId = 54;
		pk.blockData = 0;
		pk.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
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

	@Override
	public void sendBeforeBlock() {
		super.sendBeforeBlock();
		Block block = this.player.getLevel().getBlock(new Vector3(this.x + 1, this.y, this.z));
		UpdateBlockPacket pk = new UpdateBlockPacket();
		pk.x = this.x + 1;
		pk.y = this.y;
		pk.z = this.z;
		pk.blockId = block.getId();
		pk.blockData = block.getDamage();
		pk.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
		this.player.dataPacket(pk);
	}

	@Override
	public void setName(String name) {
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
}
