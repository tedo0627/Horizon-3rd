package tedo.GatyaSystem.item.Rare;

public class Standard extends Rare{

	public Standard(Integer meta, int count) {
		super(278, 0, 1, "Standard");

		this.setName("§l§bStandard§r");
		this.setLore("§fNo.071", "§7Rank:§aR");

		this.addEnchant(15, 5);
		this.addEnchant(17, 3);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
