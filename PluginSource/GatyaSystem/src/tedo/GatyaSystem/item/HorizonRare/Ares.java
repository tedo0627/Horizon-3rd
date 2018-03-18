package tedo.GatyaSystem.item.HorizonRare;

public class Ares extends HorizonRare{

	public Ares(Integer meta, int count) {
		super(276, 0, 1, "Ares");

		this.setName("§l§3A§bR§aE§dS§r");
		this.setLore("§fNo.022", "§7耐久無限", "§7Rank:§eHR");

		this.addEnchant(9, 10);
	}

	@Override
	public boolean isSword() {
		return true;
	}
}
