package tedo.GatyaSystem.item.Rare;

public class Reametaru extends Rare{

	public Reametaru(Integer meta, int count) {
		super(277, 0, 1, "Reametaru");

		this.setName("§l§bReametaru§r");
		this.setLore("§fNo.072", "§7Rank:§aR");

		this.addEnchant(15, 5);
		this.addEnchant(17, 3);
	}

	@Override
	public boolean isShovel() {
		return true;
	}
}
