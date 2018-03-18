package tedo.GatyaSystem.item.HorizonRarePlus;

public class OrpheusPlus extends HorizonRarePlus {

	public OrpheusPlus(Integer meta, int count) {
		super(279, 0, 1, "OrpheusPlus");

		this.setName("§l§3O§bR§aP§dH§cE§6U§eS§r");
		this.setLore("§a覚醒§fNo.006", "§7範囲増加・耐久無限", "§7Rank:§eHR§b+");

		this.addEnchant(15, 10);
		this.addEnchant(18, 10);
	}

	@Override
	public boolean isAxe() {
		return true;
	}
}
