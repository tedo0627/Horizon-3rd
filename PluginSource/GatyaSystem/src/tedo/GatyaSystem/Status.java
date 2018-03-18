package tedo.GatyaSystem;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.nbt.tag.CompoundTag;

public class Status implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		register(player);
	}

	public static void register(Player player) {
		if (!hasStatus(player)) {
			CompoundTag nbt = new CompoundTag()
					.putBoolean("prize", false);
			player.namedTag.putCompound("GatyaSystem", nbt);
		}
	}

	public static boolean hasStatus(Player player) {
		CompoundTag nbt = player.namedTag;
		return nbt.contains("GatyaSystem");
	}

	public static void setPrize(Player player, boolean prize) {
		CompoundTag nbt = player.namedTag.getCompound("GatyaSystem");
		nbt.putBoolean("prize", prize);
	}

	public static boolean isPrize(Player player) {
		CompoundTag nbt = player.namedTag.getCompound("GatyaSystem");
		return nbt.getBoolean("prize");
	}
}
