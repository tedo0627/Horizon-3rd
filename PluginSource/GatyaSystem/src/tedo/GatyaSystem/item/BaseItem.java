package tedo.GatyaSystem.item;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

public abstract class BaseItem extends ItemTool {

	public BaseItem(int id, Integer meta, int count, String name) {
		super(id, meta, count, name);

		this.setDefaultName(name);
	}

	@Override
	public int getMaxDurability() {
		return ItemTool.DURABILITY_DIAMOND;
	}

	@Override
	public boolean isTool() {
		return true;
	}

	@Override
	public int getTier() {
		return ItemTool.TIER_DIAMOND;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	public Item setDefaultName(String name) {
		CompoundTag nbt;
		if (this.hasCompoundTag()) {
			nbt = this.getNamedTag();
		} else {
			nbt = new CompoundTag("tag");
		}
		nbt.putString("Name", name);
		this.setNamedTag(nbt);
		return this;
	}

	public static boolean isOwner(Item item, Player player) {
		return isOwner(item, player.getName().toLowerCase());
	}

	public static boolean isOwner(Item item, String name) {
		if (!item.hasCompoundTag()) {
			return false;
		}
		if (!item.getNamedTag().contains("Owner")) {
			return false;
		}
		if (item.getNamedTag().getString("Owner").equals(name)) {
			return true;
		} else {
			return false;
		}
	}

	public Item setUnbreak() {
		CompoundTag nbt;
		if (this.hasCompoundTag()) {
			nbt = this.getNamedTag();
		} else {
			nbt = new CompoundTag("tag");
		}
		nbt.putBoolean("Unbreakable", true);
		this.setNamedTag(nbt);
		return this;
	}

	public Item addEnchant(int id, int level) {
		CompoundTag nbt;
		if (this.hasCompoundTag()) {
			nbt = this.getNamedTag();
		} else {
			nbt = new CompoundTag("tag");
		}
		if (!nbt.contains("ench")) {
			nbt.putList(new ListTag<CompoundTag>("ench"));
		}
		ListTag<CompoundTag> ench = nbt.getList("ench", CompoundTag.class);
		for (int i = 0; i < ench.size(); i++) {
			if (ench.get(i).getShort("id") == id) {
				ench.get(i).putShort("lvl", level);
				return this;
			}
		}
		ench.add(new CompoundTag()
				.putShort("id", id)
				.putShort("lvl", level)
				);
		this.setNamedTag(nbt);
		return this;
	}

	public Item addEnchant() {
		CompoundTag nbt;
		if (this.hasCompoundTag()) {
			nbt = this.getNamedTag();
		} else {
			nbt = new CompoundTag("tag");
		}
		if (!nbt.contains("ench")) {
			nbt.putList(new ListTag<CompoundTag>("ench"));
		}
		this.setNamedTag(nbt);
		return this;
	}

	public Item setName(String name) {
		CompoundTag nbt;
		if (this.hasCompoundTag()) {
			nbt = this.getNamedTag();
		} else {
			nbt = new CompoundTag("tag");
		}
		if (!nbt.contains("display")) {
			nbt.putCompound("display", new CompoundTag());
		}
		nbt.getCompound("display").putString("Name", name);
		this.setNamedTag(nbt);
		return this;
	}

	public static boolean isSame(Item item, String name) {
		if (!item.hasCompoundTag()) {
			return false;
		}
		CompoundTag nbt = item.getNamedTag();
		if (!nbt.contains("Name")) {
			return false;
		}
		return nbt.getString("Name").equals(name);
	}

	public Item setRank(String rank) {
		CompoundTag nbt;
		if (this.hasCompoundTag()) {
			nbt = this.getNamedTag();
		} else {
			nbt = new CompoundTag("tag");
		}
		nbt.putString("Rank", rank);
		this.setNamedTag(nbt);
		return this;
	}

	public static String getRank(Item item) {
		if (!item.hasCompoundTag()) {
			return null;
		}
		CompoundTag nbt = item.getNamedTag();
		if (!nbt.contains("Rank")) {
			return null;
		}
		return nbt.getString("Rank");
	}

	public Item setOwner(String name) {
		CompoundTag nbt;
		if (this.hasCompoundTag()) {
			nbt = this.getNamedTag();
		} else {
			nbt = new CompoundTag("tag");
		}
		nbt.putString("Owner", name);
		this.setNamedTag(nbt);
		return this;
	}



	public static boolean isBaseItem(Item item) {
		return getRank(item) != null;
	}

	public static boolean isArousalStone(Item item) {
		String rank = BaseItem.getRank(item);
		if (rank == null) {
			return false;
		}
		if (rank.equals("ArousalStone")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isHorizonRarePlus(Item item) {
		String rank = BaseItem.getRank(item);
		if (rank == null) {
			return false;
		}
		if (rank.equals("HRP")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isHorizonRare(Item item) {
		String rank = BaseItem.getRank(item);
		if (rank == null) {
			return false;
		}
		if (rank.equals("HR")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isBigRare(Item item) {
		String rank = BaseItem.getRank(item);
		if (rank == null) {
			return false;
		}
		if (rank.equals("SR")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isRare(Item item) {
		String rank = BaseItem.getRank(item);
		if (rank == null) {
			return false;
		}
		if (rank.equals("R")) {
			return true;
		} else {
			return false;
		}
	}
}
