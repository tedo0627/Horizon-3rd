package tedo.GatyaSystem.item.Rare;

public class Phoenix extends Rare{

	public Phoenix(Integer meta, int count) {
		super(278, 0, 1, "Phoenix");

		this.setName("§l§bPhoenix§r");
		this.setLore("§fNo.078", "§7Rank:§aR");

		this.addEnchant(16, 1);
		this.addEnchant(17, 5);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
