package tedo.GatyaSystem.item.BigRare;

public class Depression extends BigRare{

	public Depression(Integer meta, int count) {
		super(279, 0, 1, "Depression");

		this.setName("§l§eDepression§r");
		this.setLore("§fNo.059", "§7Rank:§bSR");

		this.addEnchant(15, 5);
		this.addEnchant(17, 7);
	}

	@Override
	public boolean isAxe() {
		return true;
	}
}
