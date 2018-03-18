package tedo.AddGenerator;

import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;

public class AddGenerator extends PluginBase{

	public void onEnable() {
		Generator.addGenerator(LifeGenerator.class, "life", 0);
		Generator.addGenerator(OverLifeGenerator.class, "overlife", 0);
		Generator.addGenerator(SeichiGenerator.class, "seichi", 0);
		Generator.addGenerator(FlatGenerator.class, "newflat", 0);
	}
}
