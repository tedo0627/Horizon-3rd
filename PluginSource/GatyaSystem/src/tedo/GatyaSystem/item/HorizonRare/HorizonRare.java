package tedo.GatyaSystem.item.HorizonRare;

import tedo.GatyaSystem.item.BaseItem;

public class HorizonRare extends BaseItem{

	public HorizonRare(int id, Integer meta, int count, String name) {
		super(id, meta, count, name);

		this.setUnbreak();
		this.setRank("HR");
	}
}
