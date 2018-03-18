package tedo.GatyaSystem.item.BigRare;

public class Loading extends BigRare{

	public Loading(Integer meta, int count) {
		super(278, 0, 1, "Loading");

		this.setName("§l§eLoading§r");
		this.setLore("§fNo.055", "§7Rank:§bSR");

		this.addEnchant(15, 10);
		this.addEnchant(18, 3);
		this.addEnchant(17, 10);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
