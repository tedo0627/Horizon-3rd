package tedo.GatyaSystem.item.HorizonRarePlus;

public class DiosPlus extends HorizonRarePlus {

	public DiosPlus(Integer meta, int count) {
		super(277, 0, 1, "DiosPlus");

		this.setName("§l§3D§bI§aO§dS§r");
		this.setLore("§a覚醒§fNo.005", "§7範囲増加・耐久無限", "§7Rank:§eHR§b+");

		this.addEnchant(15, 10);
		this.addEnchant(18, 10);
	}

	@Override
	public boolean isShovel() {
		return true;
	}
}
