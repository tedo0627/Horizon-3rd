package tedo.GatyaSystem.item.HorizonRarePlus;

public class BrionacPlus extends HorizonRarePlus{

	public BrionacPlus(Integer meta, int count) {
		super(277, 0, 1, "BrionacPlus");
		this.setName("§l§3B§bR§aI§dO§cN§6A§eC§r");
		this.setLore("§a覚醒§fNo.002", "§7範囲増加・耐久無限", "§7Rank:§eHR§b+");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
	}

	@Override
	public boolean isShovel() {
		return true;
	}
}
