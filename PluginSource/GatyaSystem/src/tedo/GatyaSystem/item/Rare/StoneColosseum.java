package tedo.GatyaSystem.item.Rare;

public class StoneColosseum extends Rare{

	public StoneColosseum(Integer meta, int count) {
		super(278, 0, 1, "StoneColosseum");

		this.setName("§l§bStoneColosseum§r");
		this.setLore("§fNo.074", "§7Rank:§aR");

		this.addEnchant(15, 4);
		this.addEnchant(17, 3);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
