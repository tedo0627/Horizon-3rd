package tedo.GatyaSystem.item.HorizonRare;

public class Hermes extends HorizonRare{

	public Hermes(Integer meta, int count) {
		super(312, 0, 1, "Hermes");

		this.setName("§l§3H§bE§aR§dM§cE§6S§r");
		this.setLore("§fNo.019", "§7跳躍力・耐久無限", "§7Rank:§eHR");

		this.addEnchant(0, 4);
		this.addEnchant(2, 4);
	}

	@Override
	public boolean isArmor() {
		return true;
	}

	@Override
	public boolean isLeggings() {
		return true;
	}
}
