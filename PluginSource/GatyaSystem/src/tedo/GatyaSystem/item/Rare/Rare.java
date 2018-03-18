package tedo.GatyaSystem.item.Rare;

import tedo.GatyaSystem.item.BaseItem;

public class Rare extends BaseItem{

	public Rare(int id, Integer meta, int count, String name) {
		super(id, meta, count, name);

		this.setRank("R");
	}
}
