package tedo.GatyaSystem.item.HorizonRare;

public class Dios extends HorizonRare{

	public Dios(Integer meta, int count) {
		super(277, 0, 1, "Dios");

		this.setName("§l§3D§bI§aO§dS§r");
		this.setLore("§fNo.015", "§7耐久無限", "§7Rank:§eHR");

		this.addEnchant(15, 10);
		this.addEnchant(18, 5);
	}

	@Override
	public boolean isShovel() {
		return true;
	}
}
