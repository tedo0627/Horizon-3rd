package tedo.LoginSystem;

import cn.nukkit.Player;
import cn.nukkit.nbt.tag.CompoundTag;

public class Status {

	public static boolean isRegister(Player player) {
		CompoundTag nbt = player.namedTag;
		if (nbt.contains("LoginSystem")) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public static void register(Player player, String pass) {
		if (!isRegister(player)) {
			CompoundTag nbt = new CompoundTag()
					.putString("ip", player.getAddress())
					.putLong("cid", player.getClientId())
					.putString("pass", pass);
			player.namedTag.putCompound("LoginSystem", nbt);
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean hasLogin(Player player) {
		CompoundTag nbt = player.namedTag.getCompound("LoginSystem");
		String ip = player.getAddress();
		long cid = player.getClientId();
		if (nbt.getString("ip").equals(ip) && nbt.getLong("cid") == cid) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean canLogin(Player player, String pass) {
		CompoundTag nbt = player.namedTag.getCompound("LoginSystem");
		if (nbt.getString("pass").equals(pass)) {
			nbt.putString("ip", player.getAddress());
			nbt.putLong("cid", player.getClientId());
			return true;
		} else {
			return false;
		}
	}

	public static void unRegister(Player player) {
		unRegister(player.namedTag);
	}

	public static void unRegister(CompoundTag nbt) {
		nbt.remove("LoginSystem");
	}
}
