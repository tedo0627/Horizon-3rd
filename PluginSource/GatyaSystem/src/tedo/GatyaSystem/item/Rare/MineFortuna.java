package tedo.GatyaSystem.item.Rare;

public class MineFortuna extends Rare{

	public MineFortuna(Integer meta, int count) {
		super(278, 1552, 1, "MineFortuna");

		this.setName("§l§bMineFortuna§r");
		this.setLore("§fNo.077", "§7Rank:§aR");

		this.addEnchant(15, 10);
		this.addEnchant(18, 10);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
