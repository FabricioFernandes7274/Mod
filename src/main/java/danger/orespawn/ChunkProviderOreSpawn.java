/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockFalling
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.IProgressUpdate
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.net.minecraft.util.math.BlockPos
 *  net.minecraft.world.SpawnerAnimals
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldType
 *  net.minecraft.world.biome.Biome
 *  net.minecraft.world.biome.Biome$SpawnListEntry
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.gen.MapGenBase
 *  net.minecraft.world.gen.MapGenCaves
 *  net.minecraft.world.gen.MapGenRavine
 *  net.minecraft.world.gen.NoiseGenerator
 *  net.minecraft.world.gen.NoiseGeneratorOctaves
 *  net.minecraft.world.gen.NoiseGeneratorPerlin
 *  net.minecraft.world.gen.structure.MapGenMineshaft
 *  net.minecraft.world.gen.structure.MapGenScatteredFeature
 *  net.minecraft.world.gen.structure.MapGenStronghold
 *  net.minecraftforge.event.terraingen.TerrainGen
 */
package danger.orespawn;
import net.minecraft.world.biome.BiomeProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderOreSpawn
implements net.minecraft.world.chunk.IChunkProvider {
    private Random rand;
    private NoiseGeneratorOctaves field_147431_j;
    private NoiseGeneratorOctaves field_147432_k;
    private NoiseGeneratorOctaves field_147429_l;
    private NoiseGeneratorPerlin field_147430_m;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private World world;
    private boolean mapFeaturesEnabled;
    private WorldType field_147435_p;
    private final double[] field_147434_q;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    private MapGenBase caveGenerator = new MapGenCaves();
    private ArrayList MyAmbientList = null;
    private ArrayList MyCritterList = null;
    private MapGenStronghold strongholdGenerator = new MapGenStronghold();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    private MapGenBase ravineGenerator = new MapGenRavine();
    private Biome[] biomesForGeneration;
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;
    int[][] field_73219_j = new int[32][32];
    private static final String __OBFID = "CL_00000396";

    public ChunkProviderOreSpawn(World worldIn, long par2, boolean par4) {
        this.world = worldIn;
        this.mapFeaturesEnabled = par4;
        this.field_147435_p = worldIn.getWorldType();
        this.world.rand = new Random(par2);
        this.field_147431_j = new NoiseGeneratorOctaves(this.world.rand, 16);
        this.field_147432_k = new NoiseGeneratorOctaves(this.world.rand, 16);
        this.field_147429_l = new NoiseGeneratorOctaves(this.world.rand, 8);
        this.field_147430_m = new NoiseGeneratorPerlin(this.world.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.world.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.world.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.world.rand, 8);
        this.field_147434_q = new double[825];
        this.parabolicField = new float[25];
        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f;
                this.parabolicField[j + 2 + (k + 2) * 5] = f = 10.0f / net.minecraft.util.math.MathHelper.sqrt((float)((float)(j * j + k * k) + 0.2f));
            }
        }
        NoiseGenerator[] noiseGens = new NoiseGenerator[]{this.field_147431_j, this.field_147432_k, this.field_147429_l, this.field_147430_m, this.noiseGen5, this.noiseGen6, this.mobSpawnerNoise};
        noiseGens = TerrainGen.getModdedNoiseGenerators((World)worldIn, (Random)this.world.rand, (NoiseGenerator[])noiseGens);
        this.field_147431_j = (NoiseGeneratorOctaves)noiseGens[0];
        this.field_147432_k = (NoiseGeneratorOctaves)noiseGens[1];
        this.field_147429_l = (NoiseGeneratorOctaves)noiseGens[2];
        this.field_147430_m = (NoiseGeneratorPerlin)noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
        this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];
    }

    public void func_147424_a(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_) {
        int b0 = 63;
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
        this.func_147423_a(p_147424_1_ * 4, 0, p_147424_2_ * 4);
        for (int k = 0; k < 4; ++k) {
            int l = k * 5;
            int i1 = (k + 1) * 5;
            for (int j1 = 0; j1 < 4; ++j1) {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;
                for (int k2 = 0; k2 < 32; ++k2) {
                    double d0 = 0.125;
                    double d1 = this.field_147434_q[k1 + k2];
                    double d2 = this.field_147434_q[l1 + k2];
                    double d3 = this.field_147434_q[i2 + k2];
                    double d4 = this.field_147434_q[j2 + k2];
                    double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;
                    for (int l2 = 0; l2 < 8; ++l2) {
                        double d9 = 0.25;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int i3 = 0; i3 < 4; ++i3) {
                            int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                            int short1 = 256;
                            j3 -= short1;
                            double d14 = 0.25;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;
                            for (int k3 = 0; k3 < 4; ++k3) {
                                double d;
                                d15 += d16;
                                p_147424_3_[j3 += short1] = d > 0.0 ? Blocks.STONE : (k2 * 8 + l2 < b0 ? Blocks.WATER : null);
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

    public void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, Biome[] p_147422_5_) {
        double d0 = 0.03125;
        this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, (double)(p_147422_1_ * 16), (double)(p_147422_2_ * 16), 16, 16, d0 * 2.0, d0 * 2.0, 1.0);
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome biomegenbase = p_147422_5_[l + k * 16];
                biomegenbase.genTerrainBlocks(this.world, this.world.rand, p_147422_3_, p_147422_4_, p_147422_1_ * 16 + k, p_147422_2_ * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }

    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    public Chunk provideChunk(int par1, int par2) {
        this.world.rand.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        Block[] ablock = new Block[65536];
        byte[] abyte = new byte[65536];
        this.func_147424_a(par1, par2, ablock);
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        this.replaceBlocksForBiome(par1, par2, ablock, abyte, this.biomesForGeneration);
        this.caveGenerator.generateChunk((net.minecraft.world.chunk.IChunkProvider)this, this.world, par1, par2, ablock);
        this.ravineGenerator.generateChunk((net.minecraft.world.chunk.IChunkProvider)this, this.world, par1, par2, ablock);
        Chunk chunk = new Chunk(this.world, ablock, abyte, par1, par2);
        OreSpawnMain.Chunker.generateOresInChunk(this.world, this.world.rand, par1 * 16, par2 * 16, chunk);
        chunk.generateSkylightMap();
        return chunk;
    }

    private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_) {
        double d0 = 684.412;
        double d1 = 684.412;
        double d2 = 512.0;
        double d3 = 512.0;
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0, 200.0, 0.5);
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001, 4.277575000000001, 8.555150000000001);
        this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412, 684.412, 684.412);
        this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412, 684.412, 684.412);
        boolean flag1 = false;
        boolean flag = false;
        int l = 0;
        int i1 = 0;
        double d4 = 8.5;
        for (int j1 = 0; j1 < 5; ++j1) {
            for (int k1 = 0; k1 < 5; ++k1) {
                float f = 0.0f;
                float f1 = 0.0f;
                float f2 = 0.0f;
                int b0 = 2;
                Biome biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];
                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        Biome biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.getBaseHeight();
                        float f4 = biomegenbase1.getHeightVariation();
                        if (this.field_147435_p == WorldType.AMPLIFIED && f3 > 0.0f) {
                            f3 = 1.0f + f3 * 2.0f;
                            f4 = 1.0f + f4 * 4.0f;
                        }
                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0f);
                        if (biomegenbase1.getBaseHeight() > biomegenbase.getBaseHeight()) {
                            f5 /= 2.0f;
                        }
                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }
                f /= f2;
                f1 /= f2;
                f = f * 0.9f + 0.1f;
                f1 = (f1 * 4.0f - 1.0f) / 8.0f;
                double d13 = this.field_147426_g[i1] / 8000.0;
                if (d13 < 0.0) {
                    d13 = -d13 * 0.3;
                }
                if ((d13 = d13 * 3.0 - 2.0) < 0.0) {
                    if ((d13 /= 2.0) < -1.0) {
                        d13 = -1.0;
                    }
                    d13 /= 1.4;
                    d13 /= 2.0;
                } else {
                    if (d13 > 1.0) {
                        d13 = 1.0;
                    }
                    d13 /= 8.0;
                }
                ++i1;
                double d12 = f1;
                double d14 = f;
                d12 += d13 * 0.2;
                d12 = d12 * 8.5 / 8.0;
                double d5 = 8.5 + d12 * 4.0;
                for (int j2 = 0; j2 < 33; ++j2) {
                    double d6 = ((double)j2 - d5) * 12.0 * 128.0 / 256.0 / d14;
                    if (d6 < 0.0) {
                        d6 *= 4.0;
                    }
                    double d7 = this.field_147428_e[l] / 512.0;
                    double d8 = this.field_147425_f[l] / 512.0;
                    double d9 = (this.field_147427_d[l] / 10.0 + 1.0) / 2.0;
                    double d10 = net.minecraft.util.math.MathHelper.clampedLerp((double)d7, (double)d8, (double)d9) - d6;
                    if (j2 > 29) {
                        double d11 = (float)(j2 - 29) / 3.0f;
                        d10 = d10 * (1.0 - d11) + -10.0 * d11;
                    }
                    this.field_147434_q[l] = d10;
                    ++l;
                }
            }
        }
    }

    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    public void populate(net.minecraft.world.chunk.IChunkProvider par1IChunkProvider, int par2, int par3) {
        BlockFalling.fallInstantly = false;
        int k = par2 * 16;
        int l = par3 * 16;
        Biome biomegenbase = this.world.getBiome(new net.minecraft.util.math.BlockPos(k + 16, 0, l + 16));
        this.world.rand.setSeed(this.world.getSeed());
        long i1 = this.world.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.world.rand.nextLong() / 2L * 2L + 1L;
        this.world.rand.setSeed((long)par2 * i1 + (long)par3 * j1 ^ this.world.getSeed());
        boolean flag = false;
        biomegenbase.decorate(this.world, this.world.rand, k, l);
        net.minecraft.world.WorldEntitySpawner.performWorldGenSpawning((World)this.world, (Biome)biomegenbase, (int)(k + 8), (int)(l + 8), (int)16, (int)16, (Random)this.world.rand);
        BlockFalling.fallInstantly = false;
    }

    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    public void saveExtraData() {
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "UtopiaDimension";
    }

    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        List l = null;
        ArrayList<net.minecraft.world.biome.Biome.SpawnListEntry> m = null;
        Iterator var2 = null;
        net.minecraft.world.biome.Biome.SpawnListEntry var3 = null;
        Biome var5 = null;
        var5 = this.world.getBiome(new net.minecraft.util.math.BlockPos(par2, 0, par4));
        if (var5 == null) {
            return null;
        }
        l = var5.getSpawnableList(par1EnumCreatureType);
        if (l == null) {
            return null;
        }
        if (par1EnumCreatureType == EnumCreatureType.MONSTER) {
            return null;
        }
        if (par1EnumCreatureType == EnumCreatureType.AMBIENT) {
            if (this.MyAmbientList == null) {
                this.MyAmbientList = new ArrayList();
                var2 = l.iterator();
                if (var2 == null) {
                    return null;
                }
                while (var2.hasNext()) {
                    var3 = (net.minecraft.world.biome.Biome.SpawnListEntry)var2.next();
                    if (var3.entityClass == EntityHorse.class) continue;
                    this.MyAmbientList.add(var3);
                }
            }
            return this.MyAmbientList;
        }
        var2 = l.iterator();
        if (var2 == null) {
            return null;
        }
        m = new ArrayList<net.minecraft.world.biome.Biome.SpawnListEntry>();
        var3 = null;
        while (var2.hasNext()) {
            var3 = (net.minecraft.world.biome.Biome.SpawnListEntry)var2.next();
            if (var3 == null || var3.entityClass == EntityHorse.class) continue;
            m.add(var3);
        }
        return m;
    }

    public net.minecraft.util.math.BlockPos findClosestStructure(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return "Stronghold".equals(p_147416_2_) && this.strongholdGenerator != null ? this.strongholdGenerator.getNearestInstance(p_147416_1_, p_147416_3_, p_147416_4_, p_147416_5_) : null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int par1, int par2) {
        if (this.isMapFeaturesEnabled()) {
            this.mineshaftGenerator.generateChunk((net.minecraft.world.chunk.IChunkProvider)this, this.world, par1, par2, (Block[])null);
            this.strongholdGenerator.generateChunk((net.minecraft.world.chunk.IChunkProvider)this, this.world, par1, par2, (Block[])null);
            this.scatteredFeatureGenerator.generateChunk((net.minecraft.world.chunk.IChunkProvider)this, this.world, par1, par2, (Block[])null);
        }
    }
}

