package tedo.GatyaSystem.item.HorizonRare;

public class Ceres extends HorizonRare{

	public Ceres(Integer meta, int count) {
		super(311, 0, 1, "Ceres");

		this.setName("§l§3C§bE§aR§dE§cS§r");
		this.setLore("§fNo.018", "§7耐性・耐久無限", "§7Rank:§eHR");

		this.addEnchant(0, 4);
		this.addEnchant(1, 4);
	}

	@Override
	public boolean isArmor() {
		return true;
	}

	@Override
	public boolean isChestplate() {
		return true;
	}
}
