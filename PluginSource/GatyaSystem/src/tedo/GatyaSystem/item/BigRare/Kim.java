package tedo.GatyaSystem.item.BigRare;

public class Kim extends BigRare{

	public Kim(Integer meta, int count) {
		super(278, 0, 1, "Kim");

		this.setName("§l§eKim§r");
		this.setLore("§fNo.057", "§7Rank:§bSR");

		this.addEnchant(15, 5);
		this.addEnchant(17, 7);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
