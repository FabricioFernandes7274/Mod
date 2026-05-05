package danger.orespawn;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderOreSpawn3 implements IChunkGenerator {

    private final Random rand;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private final WorldType worldType;
    
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorPerlin noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    
    private final double[] noiseArray;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    
    private MapGenBase caveGenerator = new MapGenCaves();
    private MapGenStronghold strongholdGenerator = new MapGenStronghold();
    private MapGenMoreVillages villageGenerator = new MapGenMoreVillages();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    private MapGenBase ravineGenerator = new MapGenRavine();
    
    private Biome[] biomesForGeneration;
    private double[] noise3;
    private double[] noise1;
    private double[] noise2;
    private double[] noise6;

    public ChunkProviderOreSpawn3(World worldIn, long seed, boolean mapFeaturesEnabled) {
        this.world = worldIn;
        this.mapFeaturesEnabled = mapFeaturesEnabled;
        this.worldType = worldIn.getWorldInfo().getTerrainType();
        this.rand = new Random(seed);
        
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseArray = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0F / MathHelper.sqrt((float)(j * j + k * k) + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }

        NoiseGenerator[] noiseGens = new NoiseGenerator[]{this.noiseGen1, this.noiseGen2, this.noiseGen3, this.noiseGen4, this.noiseGen5, this.noiseGen6, this.mobSpawnerNoise};
        noiseGens = TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, noiseGens);
        this.noiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
        this.noiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
        this.noiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
        this.noiseGen4 = (NoiseGeneratorPerlin)noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
        this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];
        
        // Inicialização de geradores Forge
        this.caveGenerator = TerrainGen.getModdedMapGen(this.caveGenerator, InitMapGenEvent.EventType.CAVE);
        this.strongholdGenerator = (MapGenStronghold) TerrainGen.getModdedMapGen(this.strongholdGenerator, InitMapGenEvent.EventType.STRONGHOLD);
        this.mineshaftGenerator = (MapGenMineshaft) TerrainGen.getModdedMapGen(this.mineshaftGenerator, InitMapGenEvent.EventType.MINESHAFT);
        this.scatteredFeatureGenerator = (MapGenScatteredFeature) TerrainGen.getModdedMapGen(this.scatteredFeatureGenerator, InitMapGenEvent.EventType.SCATTERED_FEATURE);
        this.ravineGenerator = TerrainGen.getModdedMapGen(this.ravineGenerator, InitMapGenEvent.EventType.RAVINE);
        // A MapGenMoreVillages não é vanilla, logo não passamos pelo TerrainGen (a não ser que tenhas um evento custom)
    }

    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
        this.generateNoise(x * 4, 0, z * 4);

        IBlockState blockWater = Blocks.WATER.getDefaultState();
        IBlockState blockStone = Blocks.STONE.getDefaultState();

        for (int k = 0; k < 4; ++k) {
            int l = k * 5;
            int i1 = (k + 1) * 5;
            for (int j1 = 0; j1 < 4; ++j1) {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2) {
                    double d0 = 0.125D;
                    double d1 = this.noiseArray[k1 + k2];
                    double d2 = this.noiseArray[l1 + k2];
                    double d3 = this.noiseArray[i2 + k2];
                    double d4 = this.noiseArray[j2 + k2];
                    double d5 = (this.noiseArray[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.noiseArray[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.noiseArray[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.noiseArray[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3) {
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3) {
                                d15 += d16;
                                int px = i3 + k * 4;
                                int py = k2 * 8 + l2;
                                int pz = k3 + j1 * 4;

                                if (d15 > 0.0D) {
                                    primer.setBlockState(px, py, pz, blockStone);
                                } else if (py < 63) {
                                    primer.setBlockState(px, py, pz, blockWater);
                                }
                            }
                            d10 += d12;
                            d11 += d13;
                        }
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
        double d0 = 0.03125D;
        this.stoneNoise = this.noiseGen4.getRegion(this.stoneNoise, (double) (x * 16), (double) (z * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome biome = biomesIn[l + k * 16];
                biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + k, z * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        
        this.setBlocksInChunk(x, z, chunkprimer);
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
        
        this.caveGenerator.generate(this.world, x, z, chunkprimer);
        this.ravineGenerator.generate(this.world, x, z, chunkprimer);
        
        if (this.mapFeaturesEnabled) {
            this.villageGenerator.generate(this.world, x, z, chunkprimer);
            this.mineshaftGenerator.generate(this.world, x, z, chunkprimer);
            this.strongholdGenerator.generate(this.world, x, z, chunkprimer);
            this.scatteredFeatureGenerator.generate(this.world, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        chunk.generateSkylightMap();
        return chunk;
    }

    private void generateNoise(int x, int y, int z) {
        this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, x, z, 5, 5, 200.0D, 200.0D, 0.5D);
        this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, x, y, z, 5, 33, 5, 8.55515D, 4.277575D, 8.55515D);
        this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, x, y, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, x, y, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);

        int l = 0;
        int i1 = 0;

        for (int j1 = 0; j1 < 5; ++j1) {
            for (int k1 = 0; k1 < 5; ++k1) {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                Biome biome = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -2; l1 <= 2; ++l1) {
                    for (int i2 = -2; i2 <= 2; ++i2) {
                        Biome biome1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biome1.getBaseHeight();
                        float f4 = biome1.getHeightVariation();

                        if (this.worldType == WorldType.AMPLIFIED && f3 > 0.0F) {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);
                        if (biome1.getBaseHeight() > biome.getBaseHeight()) {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }

                f = f / f2;
                f1 = f1 / f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;

                double d13 = this.noise6[i1] / 8000.0D;
                if (d13 < 0.0D) d13 = -d13 * 0.3D;
                d13 = d13 * 3.0D - 2.0D;
                if (d13 < 0.0D) {
                    d13 = d13 / 2.0D;
                    if (d13 < -1.0D) d13 = -1.0D;
                    d13 = d13 / 1.4D;
                    d13 = d13 / 2.0D;
                } else {
                    if (d13 > 1.0D) d13 = 1.0D;
                    d13 = d13 / 8.0D;
                }

                ++i1;
                double d12 = (double)f1;
                double d14 = (double)f;
                d12 += d13 * 0.2D;
                d12 = d12 * 8.5D / 8.0D;
                double d5 = 8.5D + d12 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2) {
                    double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;
                    if (d6 < 0.0D) d6 *= 4.0D;

                    double d7 = this.noise1[l] / 512.0D;
                    double d8 = this.noise2[l] / 512.0D;
                    double d9 = (this.noise3[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.clampedLerp(d7, d8, d9) - d6;

                    if (j2 > 29) {
                        double d11 = (double)((float)(j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.noiseArray[l] = d10;
                    ++l;
                }
            }
        }
    }

    @Override
    public void populate(int x, int z) {
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        
        this.rand.setSeed(this.world.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());

        boolean flag = false;
        ChunkPos chunkpos = new ChunkPos(x, z);

        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generateStructure(this.world, this.rand, chunkpos);
            flag = this.villageGenerator.generateStructure(this.world, this.rand, chunkpos);
            this.strongholdGenerator.generateStructure(this.world, this.rand, chunkpos);
            this.scatteredFeatureGenerator.generateStructure(this.world, this.rand, chunkpos);
        }

        if (biome != net.minecraft.init.Biomes.DESERT && biome != net.minecraft.init.Biomes.DESERT_HILLS && !flag && this.rand.nextInt(4) == 0
            && TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.LAKE)) {
            int k1 = i + this.rand.nextInt(16) + 8;
            int l1 = this.rand.nextInt(256);
            int i2 = j + this.rand.nextInt(16) + 8;
            new WorldGenLakes(Blocks.WATER).generate(this.world, this.rand, new BlockPos(k1, l1, i2));
        }

        if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.LAVA) && !flag && this.rand.nextInt(8) == 0) {
            int k1 = i + this.rand.nextInt(16) + 8;
            int l1 = this.rand.nextInt(this.rand.nextInt(248) + 8);
            int i2 = j + this.rand.nextInt(16) + 8;
            if (l1 < 63 || this.rand.nextInt(10) == 0) {
                new WorldGenLakes(Blocks.LAVA).generate(this.world, this.rand, new BlockPos(k1, l1, i2));
            }
        }

        boolean doGen = TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.DUNGEON);
        for (int k1 = 0; doGen && k1 < 8; ++k1) {
            int l1 = i + this.rand.nextInt(16) + 8;
            int i2 = this.rand.nextInt(256);
            int j2 = j + this.rand.nextInt(16) + 8;
            new WorldGenDungeons().generate(this.world, this.rand, new BlockPos(l1, i2, j2));
        }

        biome.decorate(this.world, this.rand, blockpos);

        Chunk chunk = this.world.getChunkFromChunkCoords(x, z);
        // Mining Dimension gera ores normalmente, a menos que LessOre esteja desligado
        OreSpawnMain.Chunker.generateOresInChunk(this.world, this.rand, i, j, chunk);
        if (OreSpawnMain.LessOre == 0) {
            OreSpawnMain.Chunker.generateOresInChunk(this.world, this.rand, i, j, chunk);
        }

        if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.ICE)) {
            for (int k1 = 0; k1 < 16; ++k1) {
                for (int l1 = 0; l1 < 16; ++l1) {
                    BlockPos posSnow = this.world.getPrecipitationHeight(blockpos.add(k1, 0, l1));
                    BlockPos posIce = posSnow.down();
                    
                    if (this.world.canBlockFreezeWater(posIce)) {
                        this.world.setBlockState(posIce, Blocks.ICE.getDefaultState(), 2);
                    }
                    if (this.world.canSnowAt(posSnow, true)) {
                        this.world.setBlockState(posSnow, Blocks.SNOW_LAYER.getDefaultState(), 2);
                    }
                }
            }
        }

        WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.world.getBiome(pos);
        // Na Mining Dimension, se for um templo na selva ou algo parecido, faz spawn dos monstros de templo. Caso contrário, bioma normal.
        if (creatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.isInsideStructure(pos)) {
            return this.scatteredFeatureGenerator.getSpawnList();
        }
        return biome.getSpawnableList(creatureType);
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        if ("Stronghold".equals(structureName) && this.strongholdGenerator != null) {
            return this.strongholdGenerator.getNearestStructurePos(worldIn, position, findUnexplored);
        }
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this.world, x, z, null);
            this.villageGenerator.generate(this.world, x, z, null);
            this.strongholdGenerator.generate(this.world, x, z, null);
            this.scatteredFeatureGenerator.generate(this.world, x, z, null);
        }
    }
    
    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}