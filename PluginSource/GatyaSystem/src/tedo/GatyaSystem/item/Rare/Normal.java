package tedo.GatyaSystem.item.Rare;

public class Normal extends Rare{

	public Normal(Integer meta, int count) {
		super(278, 0, 1, "Normal");

		this.setName("§l§bNormal§r");
		this.setLore("§fNo.079", "§7Rank:§aR");

		this.addEnchant();
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
