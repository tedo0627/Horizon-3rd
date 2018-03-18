package tedo.SeichiSystemPlugin;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.event.entity.EntitySpawnEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.RemoveBlockPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;

public class SeichiListener implements Listener{

	public SeichiSystemPlugin main;

	public SeichiListener(SeichiSystemPlugin main) {
		this.main = main;
	}

	@EventHandler
	public void onDataPacketReceive(DataPacketReceiveEvent event) {
		DataPacket packet = event.getPacket();
		if (packet instanceof RemoveBlockPacket) {
			Player player = event.getPlayer();
			if (player.isCreative()) {
				return;
			}
			event.setCancelled();
			SeichiSystemPlugin main = this.main;
			RemoveBlockPacket pk1 = (RemoveBlockPacket) packet;
			Vector3 pos = new Vector3(pk1.x, pk1.y, pk1.z);
			Block block = player.getLevel().getBlock(pos);
			Item item = player.getInventory().getItemInHand();
			if (main.level.contains(player.getLevel().getName())) {
				player.getLevel().sendBlocks(new Player[]{player}, new Block[]{block}, UpdateBlockPacket.FLAG_ALL_PRIORITY);
				if (main.cooltime.contains(player)) {
					return;
				}
//				if (main.skill.get(player)) {
//					main.useSkill(event);
//				}else{
//					main.handBlockBreak(event, true);
//				}
			} else {
				BlockBreakEvent ev = new BlockBreakEvent(player, block, item);
				this.main.getServer().getPluginManager().callEvent(ev);
				if (ev.isCancelled()) {
					player.getLevel().sendBlocks(new Player[]{player}, new Block[]{block}, UpdateBlockPacket.FLAG_ALL_PRIORITY);
					return;
				}
				for (Item drop : ev.getDrops()) {
					player.getInventory().addItem(drop);
				}
			}
		}
	}

	@EventHandler
	public void onBlockUpdate(BlockUpdateEvent event) {
		Block block = event.getBlock();
		int id = block.getId();
		if (this.main.level.contains(block.getLevel().getName())) {
			if (id == 8 || id == 9 || id == 10 || id  == 11) {
				event.setCancelled();
			}
		}
	}

	@EventHandler
	public void onEntitypawn(EntitySpawnEvent event) {
		Entity entity = event.getEntity();
		if (this.main.level.contains(entity.level.getName())) {
			if (entity.getName().equals("FallingSand")) {
				entity.close();
			}
		}
	}
}
