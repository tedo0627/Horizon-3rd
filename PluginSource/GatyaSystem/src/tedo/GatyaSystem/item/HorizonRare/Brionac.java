package tedo.GatyaSystem.item.HorizonRare;

public class Brionac extends HorizonRare{

	public Brionac(Integer meta, int count) {
		super(277, 0, 1, "Brionac");

		this.setName("§l§3B§bR§aI§dO§cN§6A§eC§r" );
		this.setLore("§fNo.012", "§7耐久無限", "§7Rank:§eHR");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
	}

	@Override
	public boolean isShovel() {
		return true;
	}
}
