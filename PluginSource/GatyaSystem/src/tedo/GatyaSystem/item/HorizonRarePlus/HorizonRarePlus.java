package tedo.GatyaSystem.item.HorizonRarePlus;

import tedo.GatyaSystem.item.BaseItem;

public abstract class HorizonRarePlus extends BaseItem{

	public HorizonRarePlus(int id, Integer meta, int count, String name) {
		super(id, meta, count, name);

		this.setUnbreak();
		this.setRank("HRP");
	}
}
