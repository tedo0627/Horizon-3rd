package tedo.GatyaSystem.item.BigRare;

public class Destroyer extends BigRare{

	public Destroyer(Integer meta, int count) {
		super(279, 0, 1, "Destroyer");

		this.setName("§l§eDestroyer§r");
		this.setLore("§fNo.053", "§7Rank:§bSR");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
		this.addEnchant(17, 15);
	}

	@Override
	public boolean isAxe() {
		return true;
	}
}
