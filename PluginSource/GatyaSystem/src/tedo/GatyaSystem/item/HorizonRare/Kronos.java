package tedo.GatyaSystem.item.HorizonRare;

public class Kronos extends HorizonRare{

	public Kronos(Integer meta, int count) {
		super(278, 0, 1, "Kronos");

		this.setName("§l§3K§bR§aO§dN§cO§6S§r");
		this.setLore("§fNo.011", "§7耐久無限", "§7Rank:§eHR");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
