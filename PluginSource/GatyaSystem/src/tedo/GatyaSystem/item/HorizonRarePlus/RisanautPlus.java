package tedo.GatyaSystem.item.HorizonRarePlus;

public class RisanautPlus extends HorizonRarePlus{

	public RisanautPlus(Integer meta, int count) {
		super(279, 0, 1, "RisanaultPlus");
		this.setName("§l§3R§bI§aS§dA§cN§6A§eU§fT§r");
		this.setLore("§a覚醒§fNo.003", "§7範囲増加・耐久無限", "§7Rank:§eHR§b+");

		this.addEnchant(15, 10);
		this.addEnchant(16, 1);
	}

	@Override
	public boolean isAxe() {
		return true;
	}
}
