package tedo.GatyaSystem.item.BigRare;

public class Gungnir extends BigRare{

	public Gungnir(Integer meta, int count) {
		super(277, 0, 1, "Gungnir");

		this.setName("§l§eGungnir§r");
		this.setLore("§fNo.052", "§7Rank:§bSR");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
		this.addEnchant(17, 15);
	}

	@Override
	public boolean isSword() {
		return true;
	}
}
