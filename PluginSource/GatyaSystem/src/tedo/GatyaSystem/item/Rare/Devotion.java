package tedo.GatyaSystem.item.Rare;

public class Devotion extends Rare{

	public Devotion(Integer meta, int count) {
		super(279, 0, 1, "Devotion");

		this.setName("§l§bDevotion§r");
		this.setLore("§fNo.073", "§7Rank:§aR");

		this.addEnchant(15, 5);
		this.addEnchant(17, 3);
	}

	@Override
	public boolean isAxe() {
		return true;
	}
}
