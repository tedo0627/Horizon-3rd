package tedo.GatyaSystem.item.HorizonRarePlus;

public class ThemisPlus extends HorizonRarePlus {

	public ThemisPlus(Integer meta, int count) {
		super(278, 0, 1, "ThemisPlus");

		this.setName("§l§3T§bH§aE§dM§cI§6S§r");
		this.setLore("§a覚醒§fNo.004", "§7範囲増加・耐久無限", "§7Rank:§eHR§b+");

		this.addEnchant(15, 10);
		this.addEnchant(18, 10);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
