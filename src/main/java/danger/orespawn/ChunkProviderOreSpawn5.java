package danger.orespawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderOreSpawn5 implements IChunkGenerator {
    
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
    private Biome[] biomesForGeneration;
    
    private double[] noise3;
    private double[] noise1;
    private double[] noise2;
    private double[] noise6;

    public ChunkProviderOreSpawn5(World worldIn, long seed, boolean mapFeaturesEnabled) {
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
    }

    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
        this.generateNoise(x * 4, 0, z * 4);

        IBlockState blockWater = Blocks.WATER.getDefaultState();
        IBlockState crystalStone = OreSpawnMain.CrystalStone.getDefaultState();

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
                                    primer.setBlockState(px, py, pz, crystalStone);
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

    public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer) {
        double d0 = 0.03125D;
        this.stoneNoise = this.noiseGen4.getRegion(this.stoneNoise, (double) (x * 16), (double) (z * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int ix = 0; ix < 16; ++ix) {
            for (int iz = 0; iz < 16; ++iz) {
                int run = -1;
                int noiseVal = (int)(this.stoneNoise[iz + ix * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                
                IBlockState topBlock = OreSpawnMain.CrystalGrass.getDefaultState();
                IBlockState fillerBlock = OreSpawnMain.CrystalStone.getDefaultState();

                for (int y = 255; y >= 0; --y) {
                    if (y <= this.rand.nextInt(5)) {
                        primer.setBlockState(ix, y, iz, Blocks.BEDROCK.getDefaultState());
                        continue;
                    }

                    IBlockState current = primer.getBlockState(ix, y, iz);

                    if (current.getMaterial() == Material.AIR) {
                        run = -1;
                    } else if (current.getBlock() == OreSpawnMain.CrystalStone) {
                        if (run == -1) {
                            if (noiseVal <= 0) {
                                topBlock = Blocks.AIR.getDefaultState();
                                fillerBlock = OreSpawnMain.CrystalStone.getDefaultState();
                            } else if (y >= 59 && y <= 64) {
                                topBlock = OreSpawnMain.CrystalGrass.getDefaultState();
                                fillerBlock = OreSpawnMain.CrystalStone.getDefaultState();
                            }

                            if (y < 63 && topBlock.getMaterial() == Material.AIR) {
                                topBlock = Blocks.WATER.getDefaultState();
                            }

                            run = noiseVal;

                            if (y >= 62) {
                                primer.setBlockState(ix, y, iz, topBlock);
                            } else {
                                primer.setBlockState(ix, y, iz, fillerBlock);
                            }
                        } else if (run > 0) {
                            --run;
                            primer.setBlockState(ix, y, iz, fillerBlock);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        
        this.setBlocksInChunk(x, z, chunkprimer);
        this.replaceBiomeBlocks(x, z, chunkprimer);

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
                        if (biome1.getBaseHeight() > biome.getBaseHeight()) f5 /= 2.0F;

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
        int chunkX = x * 16;
        int chunkZ = z * 16;
        BlockPos blockpos = new BlockPos(chunkX, 0, chunkZ);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        
        this.rand.setSeed(this.world.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());

        biome.decorate(this.world, this.rand, blockpos);

        // Funções transferidas para a fase de Populate para evitar 'Cascading Worldgen Lag'
        CrystalMaze cm = new CrystalMaze();
        cm.buildCrystalMaze(this.world, chunkX, 25, chunkZ);
        
        this.generateCrystals(this.world, this.rand, chunkX, chunkZ);
        this.addCrystalTrees(this.world, this.rand, chunkX, chunkZ);
        this.generateCrystalOres(this.world, this.rand, chunkX, chunkZ);
        this.addCrystalFlowers(this.world, this.rand, chunkX, chunkZ);
        this.addRice(this.world, this.rand, chunkX, chunkZ);
        this.addQuinoa(this.world, this.rand, chunkX, chunkZ);

        WorldEntitySpawner.performWorldGenSpawning(this.world, biome, chunkX + 8, chunkZ + 8, 16, 16, this.rand);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }

    // --- Decorações Exclusivas da Dimensão de Cristal ---

    public void generateCrystals(World world, Random random, int chunkX, int chunkZ) {
        this.addPinkTourmaline(world, random, chunkX, chunkZ);
        this.addTigersEye(world, random, chunkX, chunkZ);
    }

    public void addPinkTourmaline(World world, Random random, int chunkX, int chunkZ) {
        if (random.nextInt(30) != 1) return;
        
        int randPosX = 3 + chunkX + random.nextInt(10);
        int randPosY = 30 + random.nextInt(5);
        int randPosZ = 3 + chunkZ + random.nextInt(10);
        int patchy = 1 + random.nextInt(10);
        
        for (int i = 0; i < patchy; ++i) {
            float dx = random.nextFloat() - random.nextFloat();
            float dz = random.nextFloat() - random.nextFloat();
            float dy = 0.5f + random.nextFloat() / 2.0f;
            int width = random.nextInt(2);
            int length = 1 + width * 3 + random.nextInt(15);
            float rx = randPosX;
            float ry = randPosY;
            float rz = randPosZ;
            
            for (int iy = 0; iy <= length; ++iy) {
                for (int ix = 0; ix <= width; ++ix) {
                    for (int iz = 0; iz <= width; ++iz) {
                        BlockPos pos = new BlockPos((int)(rx + ix), (int)ry, (int)(rz + iz));
                        world.setBlockState(pos, OreSpawnMain.CrystalCrystal.getDefaultState(), 2);
                    }
                }
                ry += dy; rx += dx; rz += dz;
            }
        }
    }

    public void addTigersEye(World world, Random random, int chunkX, int chunkZ) {
        if (random.nextInt(30) != 1) return;
        
        int randPosX = 3 + chunkX + random.nextInt(10);
        int randPosY = 5 + random.nextInt(5);
        int randPosZ = 3 + chunkZ + random.nextInt(10);
        int patchy = 1 + random.nextInt(5);
        
        for (int i = 0; i < patchy; ++i) {
            float dx = random.nextFloat() - random.nextFloat();
            float dz = random.nextFloat() - random.nextFloat();
            float dy = 0.5f + random.nextFloat() / 2.0f;
            int width = 0;
            int length = width * 3 + random.nextInt(6);
            float rx = randPosX;
            float ry = randPosY;
            float rz = randPosZ;
            
            for (int iy = 0; iy <= length; ++iy) {
                for (int ix = 0; ix <= width; ++ix) {
                    for (int iz = 0; iz <= width; ++iz) {
                        BlockPos pos = new BlockPos((int)(rx + ix), (int)ry, (int)(rz + iz));
                        world.setBlockState(pos, OreSpawnMain.TigersEye.getDefaultState(), 2);
                    }
                }
                ry += dy; rx += dx; rz += dz;
            }
        }
    }

    public void addCrystalTrees(World world, Random random, int chunkX, int chunkZ) {
        if (random.nextInt(5) != 0) return;
        
        int what = random.nextInt(5);
        int howmany = random.nextInt(8);
        if (what != 0) howmany *= 2;
        
        for (int i = 0; i < howmany; ++i) {
            int posX = 4 + chunkX + random.nextInt(8);
            int posZ = 4 + chunkZ + random.nextInt(8);
            
            for (int posY = 128; posY > 40; --posY) {
                BlockPos pos = new BlockPos(posX, posY, posZ);
                BlockPos posDown = pos.down();
                
                if (world.getBlockState(pos).getBlock() != Blocks.AIR || world.getBlockState(posDown).getBlock() != OreSpawnMain.CrystalGrass) {
                    continue;
                }
                
                if (what == 0) {
                    this.TallCrystalTree(world, posX, posY, posZ);
                } else {
                    this.ScragglyCrystalTreeWithBranches(world, posX, posY, posZ);
                }
                break;
            }
        }
    }

    public void makeScragglyCrystalBranch(World world, int x, int y, int z, int len, int biasx, int biasz) {
        for (int k = 0; k < len; ++k) {
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2) + biasx;
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2) + biasz;
            int iy = world.rand.nextInt(3) > 0 ? 1 : 0;
            
            if (ix > 1) ix = 1;
            if (ix < -1) ix = -1;
            if (iz > 1) iz = 1;
            if (iz < -1) iz = -1;
            
            x += ix; y += iy; z += iz;
            BlockPos currentPos = new BlockPos(x, y, z);
            Block bid = world.getBlockState(currentPos).getBlock();
            
            if (bid != Blocks.AIR && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves2) {
                return;
            }
            
            world.setBlockState(currentPos, OreSpawnMain.MyCrystalTreeLog.getDefaultState(), 2);
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    BlockPos leafPos = new BlockPos(x + m, y, z + n);
                    if (world.rand.nextInt(2) == 1 && world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                        world.setBlockState(leafPos, OreSpawnMain.MyCrystalLeaves2.getDefaultState(), 2);
                    }
                }
            }
            BlockPos leafUp = new BlockPos(x, y + 1, z);
            if (world.rand.nextInt(2) == 1 && world.getBlockState(leafUp).getBlock() == Blocks.AIR) {
                world.setBlockState(leafUp, OreSpawnMain.MyCrystalLeaves2.getDefaultState(), 2);
            }
        }
    }

    public void ScragglyCrystalTreeWithBranches(World world, int x, int y, int z) {
        int i = 1 + world.rand.nextInt(2);
        int j = i + world.rand.nextInt(8);
        
        for (int k = 0; k < i; ++k) {
            BlockPos pos = new BlockPos(x, y + k, z);
            Block bid = world.getBlockState(pos).getBlock();
            if (k >= 1 && bid != Blocks.AIR && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves2) {
                return;
            }
            world.setBlockState(pos, OreSpawnMain.MyCrystalTreeLog.getDefaultState(), 2);
        }
        
        y += i - 1;
        
        for (int k = i; k < j; ++k) {
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iy = world.rand.nextInt(4) > 0 ? 1 : 0;
            
            x += ix; y += iy; z += iz;
            BlockPos pos = new BlockPos(x, y, z);
            Block bid = world.getBlockState(pos).getBlock();
            
            if (bid != Blocks.AIR && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves2) break;
            
            world.setBlockState(pos, OreSpawnMain.MyCrystalTreeLog.getDefaultState(), 2);
            
            if (world.rand.nextInt(4) == 1) {
                this.makeScragglyCrystalBranch(world, x, y, z, world.rand.nextInt(1 + j - k), world.rand.nextInt(2) - world.rand.nextInt(2), world.rand.nextInt(2) - world.rand.nextInt(2));
            }
            
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    BlockPos leafPos = new BlockPos(x + m, y, z + n);
                    if (world.rand.nextInt(2) == 1 && world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                        world.setBlockState(leafPos, OreSpawnMain.MyCrystalLeaves2.getDefaultState(), 2);
                    }
                }
            }
            BlockPos leafUp = new BlockPos(x, y + 1, z);
            if (world.rand.nextInt(2) == 1 && world.getBlockState(leafUp).getBlock() == Blocks.AIR) {
                world.setBlockState(leafUp, OreSpawnMain.MyCrystalLeaves2.getDefaultState(), 2);
            }
        }
    }

    public void TallCrystalTree(World world, int x, int y, int z) {
        int i = 10 + world.rand.nextInt(12);
        if (OreSpawnMain.LessLag == 1) i -= 2;
        if (OreSpawnMain.LessLag == 2) i -= 4;
        
        int j = i + world.rand.nextInt(18 - OreSpawnMain.LessLag * 2);
        
        for (int k = 0; k < i; ++k) {
            BlockPos pos = new BlockPos(x, y + k, z);
            Block bid = world.getBlockState(pos).getBlock();
            if (k >= 1 && bid != Blocks.AIR && bid != OreSpawnMain.MyCrystalTreeLog && bid != OreSpawnMain.MyCrystalLeaves) {
                return;
            }
            world.setBlockState(pos, OreSpawnMain.MyCrystalTreeLog.getDefaultState(), 2);
        }
        
        y += i - 1;
        
        for (int k = i; k < j; ++k) {
            BlockPos pos = new BlockPos(x, ++y, z);
            Block bid = world.getBlockState(pos).getBlock();
            
            if (bid == Blocks.AIR || bid == OreSpawnMain.MyCrystalTreeLog || bid == OreSpawnMain.MyCrystalLeaves) {
                world.setBlockState(pos, OreSpawnMain.MyCrystalTreeLog.getDefaultState(), 2);
                
                if (k % 4 == 0) {
                    for (int m = -1; m < 2; ++m) {
                        for (int n = -1; n < 2; ++n) {
                            BlockPos leafPos = new BlockPos(x + m, y, z + n);
                            if (world.rand.nextInt(2) == 1 && world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                                world.setBlockState(leafPos, OreSpawnMain.MyCrystalLeaves.getDefaultState(), 2);
                            }
                        }
                    }
                }
            } else {
                break; // Bateu noutro bloco
            }
        }
        
        ++y;
        for (int m = -1; m < 2; ++m) {
            for (int n = -1; n < 2; ++n) {
                BlockPos leafPos = new BlockPos(x + m, y, z + n);
                if (world.rand.nextInt(2) == 1 && world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                    world.setBlockState(leafPos, OreSpawnMain.MyCrystalTreeLog.getDefaultState(), 2);
                }
            }
        }
        for (int m = -3; m < 4; ++m) {
            for (int n = -3; n < 4; ++n) {
                BlockPos leafPos = new BlockPos(x + m, y, z + n);
                if (world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                    world.setBlockState(leafPos, OreSpawnMain.MyCrystalLeaves.getDefaultState(), 2);
                }
            }
        }
        ++y;
        for (int m = -1; m < 2; ++m) {
            for (int n = -1; n < 2; ++n) {
                BlockPos leafPos = new BlockPos(x + m, y, z + n);
                if (world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                    world.setBlockState(leafPos, OreSpawnMain.MyCrystalLeaves.getDefaultState(), 2);
                }
            }
        }
    }

    public void generateCrystalOres(World world, Random random, int chunkX, int chunkZ) {
        int patchy = 25 + random.nextInt(30);
        if (random.nextInt(20) == 0) patchy += 30;
        
        for (int i = 0; i < patchy; ++i) {
            int randPosX = 2 + chunkX + random.nextInt(12);
            int randPosY = random.nextInt(128);
            int randPosZ = 2 + chunkZ + random.nextInt(12);
            
            if (randPosY <= 45) continue;
            
            Block b = Blocks.AIR;
            switch (random.nextInt(11)) {
                case 0: b = OreSpawnMain.MyUrchinSpawnBlock; break;
                case 1: b = OreSpawnMain.MyFlounderSpawnBlock; break;
                case 2: b = OreSpawnMain.MySkateSpawnBlock; break;
                case 3: b = OreSpawnMain.MyRotatorSpawnBlock; break;
                case 4: b = OreSpawnMain.MyPeacockSpawnBlock; break;
                case 5: b = OreSpawnMain.MyFairySpawnBlock; break;
                case 6: b = OreSpawnMain.MyDungeonBeastSpawnBlock; break;
                case 7: b = OreSpawnMain.MyVortexSpawnBlock; break;
                case 8: b = OreSpawnMain.MyRatSpawnBlock; break;
                case 9: b = OreSpawnMain.MyWhaleSpawnBlock; break;
                case 10: b = OreSpawnMain.MyIrukandjiSpawnBlock; break;
            }
            this.generateOre(world, random, randPosX, randPosY, randPosZ, b, 4, OreSpawnMain.CrystalStone);
        }
        
        patchy = 3 + random.nextInt(8);
        for (int i = 0; i < patchy; ++i) {
            int randPosX = 2 + chunkX + random.nextInt(12);
            int randPosY = random.nextInt(128);
            int randPosZ = 2 + chunkZ + random.nextInt(12);
            this.generateOre(world, random, randPosX, randPosY, randPosZ, OreSpawnMain.CrystalCoal, 6, OreSpawnMain.CrystalStone);
        }
        
        patchy = 15 + random.nextInt(20);
        for (int i = 0; i < patchy; ++i) {
            int randPosX = 2 + chunkX + random.nextInt(12);
            int randPosY = random.nextInt(128);
            int randPosZ = 2 + chunkZ + random.nextInt(12);
            if (randPosY >= 25) continue;
            this.generateOre(world, random, randPosX, randPosY, randPosZ, OreSpawnMain.CrystalRat, 6, OreSpawnMain.CrystalStone);
        }
        
        patchy = 12 + random.nextInt(20);
        for (int i = 0; i < patchy; ++i) {
            int randPosX = 2 + chunkX + random.nextInt(12);
            int randPosY = random.nextInt(128);
            int randPosZ = 2 + chunkZ + random.nextInt(12);
            if (randPosY >= 25) continue;
            this.generateOre(world, random, randPosX, randPosY, randPosZ, OreSpawnMain.CrystalFairy, 6, OreSpawnMain.CrystalStone);
        }
    }

    public boolean generateOre(World worldIn, Random par2Random, int x, int y, int z, Block newbid, int numberOfBlocks, Block oldbid) {
        float f = par2Random.nextFloat() * (float)Math.PI;
        double d0 = (float)(x + 8) + MathHelper.sin(f) * (float)numberOfBlocks / 8.0f;
        double d1 = (float)(x + 8) - MathHelper.sin(f) * (float)numberOfBlocks / 8.0f;
        double d2 = (float)(z + 8) + MathHelper.cos(f) * (float)numberOfBlocks / 8.0f;
        double d3 = (float)(z + 8) - MathHelper.cos(f) * (float)numberOfBlocks / 8.0f;
        double d4 = y + par2Random.nextInt(3) - 2;
        double d5 = y + par2Random.nextInt(3) - 2;
        
        for (int l = 0; l <= numberOfBlocks; ++l) {
            double d6 = d0 + (d1 - d0) * (double)l / (double)numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double)l / (double)numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double)l / (double)numberOfBlocks;
            double d9 = par2Random.nextDouble() * (double)numberOfBlocks / 16.0;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)numberOfBlocks) + 1.0f) * d9 + 1.0;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)numberOfBlocks) + 1.0f) * d9 + 1.0;
            
            int i1 = MathHelper.floor(d6 - d10 / 2.0);
            int j1 = MathHelper.floor(d7 - d11 / 2.0);
            int k1 = MathHelper.floor(d8 - d10 / 2.0);
            int l1 = MathHelper.floor(d6 + d10 / 2.0);
            int i2 = MathHelper.floor(d7 + d11 / 2.0);
            int j2 = MathHelper.floor(d8 + d10 / 2.0);
            
            for (int k2 = i1; k2 <= l1; ++k2) {
                double d12 = ((double)k2 + 0.5 - d6) / (d10 / 2.0);
                if (d12 * d12 < 1.0) {
                    for (int l2 = j1; l2 <= i2; ++l2) {
                        double d13 = ((double)l2 + 0.5 - d7) / (d11 / 2.0);
                        if (d12 * d12 + d13 * d13 < 1.0) {
                            for (int i3 = k1; i3 <= j2; ++i3) {
                                double d14 = ((double)i3 + 0.5 - d8) / (d10 / 2.0);
                                BlockPos pos = new BlockPos(k2, l2, i3);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0 && worldIn.getBlockState(pos).getBlock() == oldbid) {
                                    worldIn.setBlockState(pos, newbid.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void addRice(World world, Random random, int chunkX, int chunkZ) {
        if (random.nextInt(10) != 0) return;
        
        for (int i = 0; i < 5; ++i) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);
            for (int posY = 128; posY > 40; --posY) {
                BlockPos pos = new BlockPos(posX, posY, posZ);
                if (world.getBlockState(pos).getBlock() == Blocks.AIR && world.getBlockState(pos.down()).getBlock() == OreSpawnMain.CrystalGrass) {
                    world.setBlockState(pos, OreSpawnMain.MyRicePlant.getDefaultState(), 2);
                    break;
                }
            }
        }
    }

    public void addQuinoa(World world, Random random, int chunkX, int chunkZ) {
        if (random.nextInt(20) != 0) return;
        
        for (int i = 0; i < 5; ++i) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);
            for (int posY = 128; posY > 40; --posY) {
                BlockPos pos = new BlockPos(posX, posY, posZ);
                if (world.getBlockState(pos).getBlock() == Blocks.AIR && world.getBlockState(pos.down()).getBlock() == OreSpawnMain.CrystalGrass) {
                    world.setBlockState(pos, OreSpawnMain.MyQuinoaPlant1.getDefaultState(), 2);
                    break;
                }
            }
        }
    }

    public void addCrystalFlowers(World world, Random random, int chunkX, int chunkZ) {
        if (random.nextInt(3) != 0) return;
        
        int howmany = 1 + random.nextInt(13);
        int what = random.nextInt(4);
        
        for (int i = 0; i < howmany; ++i) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);
            for (int posY = 128; posY > 40; --posY) {
                BlockPos pos = new BlockPos(posX, posY, posZ);
                if (world.getBlockState(pos).getBlock() == Blocks.AIR && world.getBlockState(pos.down()).getBlock() == OreSpawnMain.CrystalGrass) {
                    Block flower = OreSpawnMain.CrystalFlowerRedBlock;
                    if (what == 1) flower = OreSpawnMain.CrystalFlowerGreenBlock;
                    else if (what == 2) flower = OreSpawnMain.CrystalFlowerBlueBlock;
                    else if (what == 3) flower = OreSpawnMain.CrystalFlowerYellowBlock;
                    
                    world.setBlockState(pos, flower.getDefaultState(), 2);
                    break;
                }
            }
        }
    }
}