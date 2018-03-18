package tedo.GatyaSystem.item;

public class ArousalStone extends BaseItem{

	public ArousalStone(Integer meta, int count) {
		super(377, 0, 1, "ArousalStone");

		this.setName("§b覚醒石");
		this.setLore("§bこのアイテムはHRツールを覚醒するときに使います");

		this.setUnbreak();
		this.addEnchant();

		this.setRank("ArousalStone");
	}
}
