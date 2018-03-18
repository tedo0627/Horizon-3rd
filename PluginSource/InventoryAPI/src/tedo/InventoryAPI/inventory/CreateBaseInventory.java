package tedo.InventoryAPI.inventory;

import java.io.IOException;
import java.nio.ByteOrder;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.ContainerOpenPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import tedo.InventoryAPI.InventoryAPI;

public abstract class CreateBaseInventory extends BaseInventory{

	public Player player = null;
	public int x, y, z;

	public int blockId;
	public String blockEntity = "";

	public CreateBaseInventory(InventoryHolder holder, InventoryType type) {
		super(holder, type);
	}

	public void sendBlock() {
		UpdateBlockPacket pk = new UpdateBlockPacket();
		pk.x = this.x;
		pk.y = this.y;
		pk.z = this.z;
		pk.blockId = this.blockId;
		pk.blockData = 0;
		pk.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
		this.player.dataPacket(pk);
	}

	public void sendBeforeBlock() {
		Block block = this.player.getLevel().getBlock(new Vector3(this.x, this.y, this.z));
		UpdateBlockPacket pk = new UpdateBlockPacket();
		pk.x = this.x;
		pk.y = this.y;
		pk.z = this.z;
		pk.blockId = block.getId();
		pk.blockData = block.getDamage();
		pk.flags = UpdateBlockPacket.FLAG_ALL_PRIORITY;
		this.player.dataPacket(pk);
	}

	public void setName(String name) {
		CompoundTag nbt = new CompoundTag()
				.putString("id", this.blockEntity)
				.putInt("x", this.x)
				.putInt("y", this.y)
				.putInt("z", this.z)
				.putString("CustomName", name);

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
	}

	@Override
	public void onOpen(Player player) {
		super.onOpen(player);
		ContainerOpenPacket pk = new ContainerOpenPacket();
		pk.windowid = (byte) player.getWindowId(this);
		pk.type = (byte) this.type.getNetworkType();
		pk.x = this.x;
		pk.y = this.y;
		pk.z = this.z;
		player.dataPacket(pk);
		this.sendContents(player);
	}

	@Override
	public void onClose(Player player) {
		InventoryAPI.main.removeInventory(player);
		sendBeforeBlock();
		ContainerClosePacket pk = new ContainerClosePacket();
		pk.windowid = (byte) player.getWindowId(this);
		player.dataPacket(pk);
		super.onClose(player);
	}
}
