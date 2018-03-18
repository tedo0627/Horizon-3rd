package tedo.GatyaSystem.item.HorizonRare;

public class Athena extends HorizonRare{

	public Athena(Integer meta, int count) {
		super(310, 0, 1, "Athena");

		this.setName("§l§3A§bT§aH§dE§cN§6A§r");
		this.setLore("§fNo.017", "§7満腹度回復・暗視・耐久無限", "§7Rank:§eHR");

		this.addEnchant(0, 4);
		this.addEnchant(6, 3);
	}

	@Override
	public boolean isArmor() {
		return true;
	}

	@Override
	public boolean isHelmet() {
		return true;
	}
}
