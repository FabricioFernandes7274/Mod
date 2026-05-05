package danger.orespawn;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkProviderOreSpawn4 implements IChunkGenerator {
    
    private final World world;
    private final Random random;

    public ChunkProviderOreSpawn4(World worldIn, long seed, boolean mapFeaturesEnabled) {
        this.world = worldIn;
        this.random = new Random(seed);
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer primer = new ChunkPrimer();
        
        // A Danger Dimension é um mundo plano muito baixo.
        // Camada 0: Bedrock | Camada 1 a 6: Dirt | Camada 7: Grass
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                primer.setBlockState(i, 0, j, Blocks.BEDROCK.getDefaultState());
                for (int y = 1; y <= 6; ++y) {
                    primer.setBlockState(i, y, j, Blocks.DIRT.getDefaultState());
                }
                primer.setBlockState(i, 7, j, Blocks.GRASS.getDefaultState());
            }
        }

        Chunk chunk = new Chunk(this.world, primer, x, z);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        int chunkX = x * 16;
        int chunkZ = z * 16;
        
        this.random.setSeed(this.world.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long)x * i1 + (long)z * j1 ^ this.world.getSeed());

        // Movido do generateChunk para o populate. 
        // As árvores muitas vezes ultrapassam as bordas do chunk, se o fizermos no WorldGen
        // principal causamos o erro de "Cascading WorldGen Lag". O populate é o local seguro!
        this.addScragglyTrees(this.world, chunkX, chunkZ);
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
        return null; // Danger Dimension não tem Strongholds ou estruturas geradas
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }

    // --- Sistema de Geração de "Scraggly Trees" (Árvores Desgrenhadas do OreSpawn) ---

    public void addScragglyTrees(World world, int chunkX, int chunkZ) {
        int howmany = 1 + this.random.nextInt(10);
        
        if (OreSpawnMain.LessLag == 1) howmany /= 2;
        if (OreSpawnMain.LessLag == 2) howmany /= 4;
        
        if (howmany == 0) return;
        
        for (int i = 0; i < howmany; ++i) {
            int posX = 2 + chunkX + this.random.nextInt(12);
            int posZ = 2 + chunkZ + this.random.nextInt(12);
            
            // Procura relva do topo para a base
            for (int posY = 20; posY > 2; --posY) {
                BlockPos pos = new BlockPos(posX, posY - 1, posZ);
                if (world.getBlockState(pos).getBlock() != Blocks.GRASS) continue;
                
                this.ScragglyTreeWithBranches(world, posX, posY, posZ);
                break; // Substitui o 'continue block0' original. Já encontrou e gerou, passa para a próxima árvore.
            }
        }
    }

    public void makeScragglyBranch(World world, int x, int y, int z, int len, int biasx, int biasz) {
        for (int k = 0; k < len; ++k) {
            int ix = this.random.nextInt(2) - this.random.nextInt(2) + biasx;
            int iz = this.random.nextInt(2) - this.random.nextInt(2) + biasz;
            int iy = this.random.nextInt(3) > 0 ? 1 : 0;
            
            if (ix > 1) ix = 1;
            if (ix < -1) ix = -1;
            if (iz > 1) iz = 1;
            if (iz < -1) iz = -1;
            
            x += ix;
            y += iy;
            z += iz;

            BlockPos currentPos = new BlockPos(x, y, z);
            Block bid = world.getBlockState(currentPos).getBlock();

            if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }
            world.setBlockState(currentPos, Blocks.LOG.getDefaultState(), 2);
            
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (this.random.nextInt(2) != 1) continue;
                    BlockPos leafPos = new BlockPos(x + m, y, z + n);
                    if (world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                        world.setBlockState(leafPos, OreSpawnMain.MyAppleLeaves.getDefaultState(), 2);
                    }
                }
            }
            if (this.random.nextInt(2) == 1) {
                BlockPos leafUp = new BlockPos(x, y + 1, z);
                if (world.getBlockState(leafUp).getBlock() == Blocks.AIR) {
                    world.setBlockState(leafUp, OreSpawnMain.MyAppleLeaves.getDefaultState(), 2);
                }
            }
        }
    }

    public void ScragglyTreeWithBranches(World world, int x, int y, int z) {
        int i = 1 + this.random.nextInt(3);
        int j = i + this.random.nextInt(12);
        
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
            int ix = this.random.nextInt(2) - this.random.nextInt(2);
            int iz = this.random.nextInt(2) - this.random.nextInt(2);
            int iy = this.random.nextInt(4) > 0 ? 1 : 0;
            
            x += ix;
            y += iy;
            z += iz;
            
            BlockPos pos = new BlockPos(x, y, z);
            Block bid = world.getBlockState(pos).getBlock();
            
            if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) break;
            
            world.setBlockState(pos, Blocks.LOG.getDefaultState(), 2);
            
            if (this.random.nextInt(4) == 1) {
                this.makeScragglyBranch(world, x, y, z, this.random.nextInt(1 + j - k), this.random.nextInt(2) - this.random.nextInt(2), this.random.nextInt(2) - this.random.nextInt(2));
            }
            
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (this.random.nextInt(2) != 1) continue;
                    BlockPos leafPos = new BlockPos(x + m, y, z + n);
                    if (world.getBlockState(leafPos).getBlock() == Blocks.AIR) {
                        world.setBlockState(leafPos, OreSpawnMain.MyAppleLeaves.getDefaultState(), 2);
                    }
                }
            }
            if (this.random.nextInt(2) == 1) {
                BlockPos leafUp = new BlockPos(x, y + 1, z);
                if (world.getBlockState(leafUp).getBlock() == Blocks.AIR) {
                    world.setBlockState(leafUp, OreSpawnMain.MyAppleLeaves.getDefaultState(), 2);
                }
            }
        }
    }
}