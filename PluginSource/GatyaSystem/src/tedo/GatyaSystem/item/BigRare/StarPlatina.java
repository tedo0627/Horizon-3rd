package tedo.GatyaSystem.item.BigRare;

public class StarPlatina extends BigRare{

	public StarPlatina(Integer meta, int count) {
		super(277, 0, 1, "StarPlatina");

		this.setName("§l§eStarPlatina§r");
		this.setLore("§fNo.058", "§7Rank:§bSR");

		this.addEnchant(15, 5);
		this.addEnchant(17, 7);
	}

	@Override
	public boolean isShovel() {
		return true;
	}
}
