package tedo.GatyaSystem.item.BigRare;

public class Mjolner extends BigRare{

	public Mjolner(Integer meta, int count) {
		super(278, 0, 1, "Mjolner");

		this.setName("§l§eMjolner§r");
		this.setLore("§fNo.051", "§7Rank:§bSR");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
		this.addEnchant(17, 15);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
