package tedo.GatyaSystem.item.HorizonRare;

public class Orpheus extends HorizonRare{

	public Orpheus(Integer meta, int count) {
		super(279, 0, 1, "Orpheus");

		this.setName("§l§3O§bR§aP§dH§cE§6U§eS§r");
		this.setLore("§fNo.016", "§7耐久無限", "§7Rank:§eHR");

		this.addEnchant(15, 10);
		this.addEnchant(18, 5);
	}

	@Override
	public boolean isAxe() {
		return true;
	}
}
