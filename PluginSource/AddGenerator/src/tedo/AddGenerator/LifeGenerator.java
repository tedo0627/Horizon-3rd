package tedo.AddGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import cn.nukkit.Server;
import cn.nukkit.block.BlockDirt;
import cn.nukkit.block.BlockGravel;
import cn.nukkit.block.BlockOreCoal;
import cn.nukkit.block.BlockOreDiamond;
import cn.nukkit.block.BlockOreGold;
import cn.nukkit.block.BlockOreIron;
import cn.nukkit.block.BlockOreLapis;
import cn.nukkit.block.BlockOreRedstone;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.biome.Biome;
import cn.nukkit.level.generator.object.ore.OreType;
import cn.nukkit.level.generator.populator.Populator;
import cn.nukkit.level.generator.populator.PopulatorOre;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

public class LifeGenerator extends Generator{

    @Override
    public int getId() {
        return TYPE_FLAT;
    }

    private ChunkManager level;

    private NukkitRandom random;

    private final List<Populator> populators = new ArrayList<>();

    private int[][] structure;

    private final Map<String, Object> options;

    private int floorLevel;

    private String preset;

    private boolean init = false;

    private int biome;

    @Override
    public ChunkManager getChunkManager() {
        return level;
    }

    @Override
    public Map<String, Object> getSettings() {
        return this.options;
    }

    @Override
    public String getName() {
        return "life";
    }

    public LifeGenerator() {
        this(new HashMap<>());
    }

    public LifeGenerator(Map<String, Object> options) {
        this.preset = "2;7,2x3,2;1;";
        this.options = options;

        if (this.options.containsKey("decoration")) {
            PopulatorOre ores = new PopulatorOre();
            ores.setOreTypes(new OreType[]{
                    new OreType(new BlockOreCoal(), 20, 16, 0, 128),
                    new OreType(new BlockOreIron(), 20, 8, 0, 64),
                    new OreType(new BlockOreRedstone(), 8, 7, 0, 16),
                    new OreType(new BlockOreLapis(), 1, 6, 0, 32),
                    new OreType(new BlockOreGold(), 2, 8, 0, 32),
                    new OreType(new BlockOreDiamond(), 1, 7, 0, 16),
                    new OreType(new BlockDirt(), 20, 32, 0, 128),
                    new OreType(new BlockGravel(), 20, 16, 0, 128),
            });
            this.populators.add(ores);
        }
    }

    protected void parsePreset(String preset, int chunkX, int chunkZ) {
        try {
            this.preset = preset;
            String[] presetArray = preset.split(";");
            String blocks = presetArray.length > 1 ? presetArray[1] : "";
            this.biome = presetArray.length > 2 ? Integer.valueOf(presetArray[2]) : 1;
            String options = presetArray.length > 3 ? presetArray[1] : "";
            this.structure = new int[256][];
            int y = 0;
            for (String block : blocks.split(",")) {
                int id, meta = 0, cnt = 1;
                if (Pattern.matches("^[0-9]{1,3}x[0-9]$", block)) {
                    //AxB
                    String[] s = block.split("x");
                    cnt = Integer.valueOf(s[0]);
                    id = Integer.valueOf(s[1]);
                } else if (Pattern.matches("^[0-9]{1,3}:[0-9]{0,2}$", block)) {
                    //A:B
                    String[] s = block.split(":");
                    id = Integer.valueOf(s[0]);
                    meta = Integer.valueOf(s[1]);
                } else if (Pattern.matches("^[0-9]{1,3}$", block)) {
                    //A
                    id = Integer.valueOf(block);
                } else {
                    continue;
                }
                int cY = y;
                y += cnt;
                if (y > 0xFF) {
                    y = 0xFF;
                }
                for (; cY < y; ++cY) {
                    this.structure[cY] = new int[]{id, meta};
                }
            }
            this.floorLevel = y;
            for (; y <= 0xFF; ++y) {
                this.structure[y] = new int[]{0, 0};
            }
            for (String option : options.split(",")) {
                if (Pattern.matches("^[0-9a-z_]+$", option)) {
                    this.options.put(option, true);
                } else if (Pattern.matches("^[0-9a-z_]+\\([0-9a-z_ =]+\\)$", option)) {
                    String name = option.substring(0, option.indexOf("("));
                    String extra = option.substring(option.indexOf("(") + 1, option.indexOf(")"));
                    Map<String, Float> map = new HashMap<>();
                    for (String kv : extra.split(" ")) {
                        String[] data = kv.split("=");
                        map.put(data[0], Float.valueOf(data[1]));
                    }
                    this.options.put(name, map);
                }
            }
        } catch (Exception e) {
            Server.getInstance().getLogger().error("error while parsing the preset", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(ChunkManager level, NukkitRandom random) {
        this.level = level;
        this.random = random;
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        if (!this.init) {
            init = true;
            if (this.options.containsKey("preset") && !"".equals(this.options.get("preset"))) {
                this.parsePreset((String) this.options.get("preset"), chunkX, chunkZ);
            } else {
                this.parsePreset(this.preset, chunkX, chunkZ);
            }
        }
        FullChunk chunk = this.level.getChunk(chunkX, chunkZ);
        this.generateChunk(chunk);
    }

    private void generateChunk(FullChunk chunk) {
        chunk.setGenerated();
        int c = Biome.getBiome(biome).getColor();
        int R = c >> 16;
        int G = (c >> 8) & 0xff;
        int B = c & 0xff;

        for (int Z = 0; Z < 16; ++Z) {
            for (int X = 0; X < 16; ++X) {
                chunk.setBiomeId(X, Z, biome);
                chunk.setBiomeColor(X, Z, R, G, B);
                for (int y = 0; y < 256; ++y) {
                	if (y == 0) {
                        chunk.setBlock(X, y, Z, 7, 0);
                	} else if (0 < y && y <= 15) {
                        chunk.setBlock(X, y, Z, 1, 0);
                	} else if (15 < y && y <= 17) {
                        chunk.setBlock(X, y, Z, 3, 0);
                	} else if (y == 18) {
	            		chunk.setBlock(X, y, Z, 3, 0);
                	} else if (y == 19) {
                		int x = chunk.getX() * 16 + X;
                		int z = chunk.getZ() * 16 + Z;
                		x %= 24;
                		z %= 24;
            			if ( (1 <= x && x <= 4) || (-4 <= x && x <= -1) || (1 <= z && z <= 4) || (-4 <= z && z <= -1) ) {
            				chunk.setBlock(X, y, Z, 98, 0);
                		} else {
                			chunk.setBlock(X, y, Z, 2, 0);
                		}
                	}
                }
            }
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        this.random.setSeed(0xdeadbeef ^ (chunkX << 8) ^ chunkZ ^ this.level.getSeed());
        for (Populator populator : this.populators) {
            populator.populate(this.level, chunkX, chunkZ, this.random);
        }
    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(128, this.floorLevel, 128);
    }
}