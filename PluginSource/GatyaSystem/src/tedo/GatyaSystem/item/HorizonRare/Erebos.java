package tedo.GatyaSystem.item.HorizonRare;

public class Erebos extends HorizonRare{

	public Erebos(Integer meta, int count) {
		super(444, 0, 1, "Erebos");

		this.setName("§l§3E§bR§aE§dB§cO§6S§r");
		this.setLore("§fNo.021", "§7火炎耐性・耐久無限", "§7Rank:§eHR");

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
