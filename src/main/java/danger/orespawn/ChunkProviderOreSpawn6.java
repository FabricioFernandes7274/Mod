package danger.orespawn;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderOreSpawn6 implements IChunkGenerator {

    private final Random hellRNG;
    private final World world;
    
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;
    
    private double[] noiseField;
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] netherrackExclusivityNoise = new double[256];
    
    private double[] noiseData1;
    private double[] noiseData2;
    private double[] noiseData3;
    private double[] noiseData4;
    private double[] noiseData5;

    public ChunkProviderOreSpawn6(World worldIn, long seed) {
        this.world = worldIn;
        this.hellRNG = new Random(seed);
        
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);

        NoiseGenerator[] noiseGens = new NoiseGenerator[]{
            this.netherNoiseGen1, this.netherNoiseGen2, this.netherNoiseGen3, 
            this.slowsandGravelNoiseGen, this.netherrackExculsivityNoiseGen, 
            this.netherNoiseGen6, this.netherNoiseGen7
        };
        noiseGens = TerrainGen.getModdedNoiseGenerators(worldIn, this.hellRNG, noiseGens);
        
        this.netherNoiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
        this.netherNoiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
        this.netherNoiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
        this.slowsandGravelNoiseGen = (NoiseGeneratorOctaves)noiseGens[3];
        this.netherrackExculsivityNoiseGen = (NoiseGeneratorOctaves)noiseGens[4];
        this.netherNoiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
        this.netherNoiseGen7 = (NoiseGeneratorOctaves)noiseGens[6];
    }

    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        int b0 = 4; // Determina a altura da água (Y < 4)
        int b1 = 32;
        int k = b0 + 1;
        int b2 = 17;
        int l = b0 + 1;
        
        this.noiseField = this.initializeNoiseField(this.noiseField, x * b0, 0, z * b0, k, b2, l);
        
        IBlockState stone = Blocks.STONE.getDefaultState();
        IBlockState water = Blocks.WATER.getDefaultState();

        for (int i1 = 0; i1 < b0; ++i1) {
            for (int j1 = 0; j1 < b0; ++j1) {
                for (int k1 = 0; k1 < 16; ++k1) {
                    double d0 = 0.125D;
                    double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
                    double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
                    double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
                    double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
                    double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2) {
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k2 = 0; k2 < 4; ++k2) {
                                d15 += d16;
                                
                                int px = i2 + i1 * 4;
                                int py = k1 * 8 + l1;
                                int pz = k2 + j1 * 4;

                                if (d15 > 0.0D) {
                                    primer.setBlockState(px, py, pz, stone);
                                } else if (py < b0) {
                                    primer.setBlockState(px, py, pz, water);
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
        int b0 = 64;
        double d0 = 0.03125D;
        
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, x * 16, z * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, x * 16, 109, z * 16, 16, 1, 16, d0, 1.0D, d0);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, x * 16, z * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        IBlockState grass = Blocks.GRASS.getDefaultState();
        IBlockState dirt = Blocks.DIRT.getDefaultState();
        IBlockState stone = Blocks.STONE.getDefaultState();
        IBlockState water = Blocks.WATER.getDefaultState();

        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                int i1 = (int)(this.netherrackExclusivityNoise[k + l * 16] / 3.0D + 3.0D + this.hellRNG.nextDouble() * 0.25D);
                int j1 = -1;
                
                IBlockState topBlock = grass;
                IBlockState fillerBlock = dirt;

                for (int k1 = 127; k1 >= 0; --k1) {
                    IBlockState current = primer.getBlockState(k, k1, l);
                    
                    if (current.getBlock() == Blocks.AIR) {
                        j1 = -1;
                    } else if (current.getBlock() == Blocks.STONE) {
                        if (j1 == -1) {
                            if (i1 <= 0) {
                                topBlock = Blocks.AIR.getDefaultState();
                                fillerBlock = stone;
                            } else if (k1 >= b0 - 4 && k1 <= b0 + 1) {
                                topBlock = stone;
                                fillerBlock = stone;
                            }
                            
                            if (k1 < b0 && topBlock.getBlock() == Blocks.AIR) {
                                topBlock = water;
                            }
                            
                            j1 = i1;
                            
                            if (k1 >= b0 - 1) {
                                primer.setBlockState(k, k1, l, topBlock);
                            } else {
                                primer.setBlockState(k, k1, l, fillerBlock);
                            }
                        } else if (j1 > 0) {
                            --j1;
                            primer.setBlockState(k, k1, l, fillerBlock);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.hellRNG.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer primer = new ChunkPrimer();
        
        this.setBlocksInChunk(x, z, primer);
        this.replaceBiomeBlocks(x, z, primer);
        
        Chunk chunk = new Chunk(this.world, primer, x, z);
        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] initializeNoiseField(double[] noiseArray, int x, int y, int z, int xSize, int ySize, int zSize) {
        if (noiseArray == null) {
            noiseArray = new double[xSize * ySize * zSize];
        }
        
        double d0 = 684.412D;
        double d1 = 2053.236D;
        
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, x, y, z, xSize, 1, zSize, 1.0D, 0.0D, 1.0D);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, x, y, z, xSize, 1, zSize, 100.0D, 0.0D, 100.0D);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, x, y, z, xSize, ySize, zSize, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, x, y, z, xSize, ySize, zSize, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, x, y, z, xSize, ySize, zSize, d0, d1, d0);
        
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[ySize];

        for (int i2 = 0; i2 < ySize; ++i2) {
            adouble1[i2] = Math.cos((double)i2 * Math.PI * 6.0D / (double)ySize) * 2.0D;
            double d2 = i2;
            if (i2 > ySize / 2) d2 = ySize - 1 - i2;
            if (d2 < 4.0D) {
                d2 = 4.0D - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (int i2 = 0; i2 < xSize; ++i2) {
            for (int k2 = 0; k2 < zSize; ++k2) {
                double d3 = (this.noiseData4[l1] + 256.0D) / 512.0D;
                if (d3 > 1.0D) d3 = 1.0D;
                
                double d4 = 0.0D;
                double d5 = this.noiseData5[l1] / 8000.0D;
                if (d5 < 0.0D) d5 = -d5;
                d5 = d5 * 3.0D - 3.0D;
                
                if (d5 < 0.0D) {
                    d5 /= 2.0D;
                    if (d5 < -1.0D) d5 = -1.0D;
                    d5 /= 1.4D;
                    d5 /= 2.0D;
                    d3 = 0.0D;
                } else {
                    if (d5 > 1.0D) d5 = 1.0D;
                    d5 /= 6.0D;
                }
                
                d3 += 0.5D;
                d5 = d5 * (double)ySize / 16.0D;
                ++l1;
                
                for (int j2 = 0; j2 < ySize; ++j2) {
                    double d6 = 0.0D;
                    double d7 = adouble1[j2];
                    double d8 = this.noiseData2[k1] / 512.0D;
                    double d9 = this.noiseData3[k1] / 512.0D;
                    double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;
                    
                    d6 = d10 < 0.0D ? d8 : (d10 > 1.0D ? d9 : d8 + (d9 - d8) * d10);
                    d6 -= d7;

                    if (j2 > ySize - 4) {
                        double d11 = (float)(j2 - (ySize - 4)) / 3.0F;
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }
                    if ((double)j2 < d4) {
                        double d11 = (d4 - (double)j2) / 4.0D;
                        if (d11 < 0.0D) d11 = 0.0D;
                        if (d11 > 1.0D) d11 = 1.0D;
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }
                    
                    noiseArray[k1] = d6;
                    ++k1;
                }
            }
        }
        return noiseArray;
    }

    @Override
    public void populate(int x, int z) {
        int chunkX = x * 16;
        int chunkZ = z * 16;
        BlockPos blockpos = new BlockPos(chunkX, 0, chunkZ);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        
        this.hellRNG.setSeed(this.world.getSeed());
        long i1 = this.hellRNG.nextLong() / 2L * 2L + 1L;
        long j1 = this.hellRNG.nextLong() / 2L * 2L + 1L;
        this.hellRNG.setSeed((long)x * i1 + (long)z * j1 ^ this.world.getSeed());
        
        biome.decorate(this.world, this.hellRNG, blockpos);
        
        // Geração dos minérios do OreSpawn e as Árvores transferidos para o Populate
        Chunk chunk = this.world.getChunkFromChunkCoords(x, z);
        OreSpawnMain.Chunker.generateOresInChunk(this.world, this.hellRNG, chunkX, chunkZ, chunk);
        this.addScragglyTrees(this.world, chunkX, chunkZ);

        WorldEntitySpawner.performWorldGenSpawning(this.world, biome, chunkX + 8, chunkZ + 8, 16, 16, this.hellRNG);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
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

    // --- Sistema de Árvores Desgrenhadas ---
    
    public void addScragglyTrees(World world, int chunkX, int chunkZ) {
        int howmany = 1 + world.rand.nextInt(5);
        if (world.rand.nextInt(4) != 0) return;
        
        if (OreSpawnMain.LessLag == 1) howmany /= 2;
        if (OreSpawnMain.LessLag == 2) howmany /= 4;
        if (howmany == 0) return;
        
        for (int i = 0; i < howmany; ++i) {
            int posX = 2 + chunkX + world.rand.nextInt(12);
            int posZ = 2 + chunkZ + world.rand.nextInt(12);
            
            // O teto é de pedra, as árvores nascem na relva do chão (que fica cá para baixo)
            for (int posY = 120; posY > 50; --posY) {
                BlockPos pos = new BlockPos(posX, posY - 1, posZ);
                if (world.getBlockState(pos).getBlock() != Blocks.GRASS) continue;
                
                this.ScragglyTreeWithBranches(world, posX, posY, posZ);
                break;
            }
        }
    }

    public void makeScragglyBranch(World world, int x, int y, int z, int len, int biasx, int biasz) {
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
            
            if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }
            
            world.setBlockState(currentPos, Blocks.LOG.getDefaultState(), 2);
            
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (world.rand.nextInt(2) != 1) continue;
                    BlockPos leafPos = new BlockPos(x + m, y, z + n);
                    if (world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                        world.setBlockState(leafPos, OreSpawnMain.MyAppleLeaves.getDefaultState(), 2);
                    }
                }
            }
            if (world.rand.nextInt(2) == 1) {
                BlockPos leafUp = new BlockPos(x, y + 1, z);
                if (world.getBlockState(leafUp).getBlock() == Blocks.AIR) {
                    world.setBlockState(leafUp, OreSpawnMain.MyAppleLeaves.getDefaultState(), 2);
                }
            }
        }
    }

    public void ScragglyTreeWithBranches(World world, int x, int y, int z) {
        int i = 1 + world.rand.nextInt(3);
        int j = i + world.rand.nextInt(12);
        
        for (int k = 0; k < i; ++k) {
            BlockPos pos = new BlockPos(x, y + k, z);
            Block bid = world.getBlockState(pos).getBlock();
            if (k >= 1 && bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }
            world.setBlockState(pos, Blocks.LOG.getDefaultState(), 2);
        }
        
        y += i - 1;
        
        for (int k = i; k < j; ++k) {
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iy = world.rand.nextInt(4) > 0 ? 1 : 0;
            
            x += ix; y += iy; z += iz;
            BlockPos pos = new BlockPos(x, y, z);
            Block bid = world.getBlockState(pos).getBlock();
            
            if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) break;
            
            world.setBlockState(pos, Blocks.LOG.getDefaultState(), 2);
            
            if (world.rand.nextInt(4) == 1) {
                this.makeScragglyBranch(world, x, y, z, world.rand.nextInt(1 + j - k), world.rand.nextInt(2) - world.rand.nextInt(2), world.rand.nextInt(2) - world.rand.nextInt(2));
            }
            
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (world.rand.nextInt(2) != 1) continue;
                    BlockPos leafPos = new BlockPos(x + m, y, z + n);
                    if (world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                        world.setBlockState(leafPos, OreSpawnMain.MyAppleLeaves.getDefaultState(), 2);
                    }
                }
            }
            if (world.rand.nextInt(2) == 1) {
                BlockPos leafUp = new BlockPos(x, y + 1, z);
                if (world.getBlockState(leafUp).getBlock() == Blocks.AIR) {
                    world.setBlockState(leafUp, OreSpawnMain.MyAppleLeaves.getDefaultState(), 2);
                }
            }
        }
    }
}