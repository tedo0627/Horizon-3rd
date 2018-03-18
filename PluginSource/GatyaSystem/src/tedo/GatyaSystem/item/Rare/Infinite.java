package tedo.GatyaSystem.item.Rare;

public class Infinite extends Rare{

	public Infinite(Integer meta, int count) {
		super(278, 0, 1, "Infinite");

		this.setName("§l§bInfinite§r");
		this.setLore("§fNo.075", "§7Rank:§aR");

		this.addEnchant(15, 3);
		this.addEnchant(17, 3);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
