package tedo.GatyaSystem.item.BigRare;

public class IceBurn extends BigRare{

	public IceBurn(Integer meta, int count) {
		super(278, 0, 1, "IceBurn");

		this.setName("§l§eIceBurn§r");
		this.setLore("§fNo.056", "§7Rank:§bSR");

		this.addEnchant(15, 10);
		this.addEnchant(17, 10);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
