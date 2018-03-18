package tedo.GatyaSystem.item.Rare;

public class JetStream extends Rare{

	public JetStream(Integer meta, int count) {
		super(278, 0, 1, "JetStream");

		this.setName("§l§bJetStream§r");
		this.setLore("§fNo.076", "§7Rank:§aR");

		this.addEnchant(15, 5);
		this.addEnchant(17, 5);
	}

	@Override
	public boolean isPickaxe() {
		return true;
	}
}
