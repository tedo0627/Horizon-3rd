package tedo.GatyaSystem.item.HorizonRare;

public class Themis extends HorizonRare {

	public Themis(Integer meta, int count) {
		super(278, 0, 1, "Themis");

		this.setName("§l§3T§bH§aE§dM§cI§6S§r");
		this.setLore("§fNo.0147", "§7耐久無限", "§7Rank:§eHR");

		this.addEnchant(15, 10);
		this.addEnchant(18, 5);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
