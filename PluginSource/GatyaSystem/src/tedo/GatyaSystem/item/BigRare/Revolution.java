package tedo.GatyaSystem.item.BigRare;

public class Revolution extends BigRare{

	public Revolution(Integer meta, int count) {
		super(278, 0, 1, "Revolution");

		this.setName("§l§eRevolution§r");
		this.setLore("§fNo.054", "§7Rank:§bSR");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
		this.addEnchant(17, 10);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
