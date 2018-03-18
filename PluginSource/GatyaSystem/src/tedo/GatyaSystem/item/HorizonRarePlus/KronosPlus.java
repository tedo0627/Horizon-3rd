package tedo.GatyaSystem.item.HorizonRarePlus;

public class KronosPlus extends HorizonRarePlus {

	public KronosPlus(Integer meta, int count) {
		super(278, meta, count, "KronosPlus");
		this.setName("§l§3K§bR§aO§dN§cO§6S§r");
		this.setLore("§a覚醒§fNo.001", "§7範囲増加・耐久無限", "§7Rank:§eHR§b+");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
