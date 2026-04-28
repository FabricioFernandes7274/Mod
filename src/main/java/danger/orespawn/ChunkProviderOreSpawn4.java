/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.IProgressUpdate
 *  net.minecraft.world.net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.Biome
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.chunk.storage.ExtendedBlockStorage
 */
package danger.orespawn;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class ChunkProviderOreSpawn4
implements net.minecraft.world.chunk.IChunkProvider {
    private World world;
    private Random random;
    private final Block[] cachedBlockIDs = new Block[256];
    private final byte[] cachedBlockMetadata = new byte[256];

    public ChunkProviderOreSpawn4(World worldIn, long par2, boolean par4) {
        this.world = worldIn;
        this.random = new Random(par2);
        for (int j = 0; j < 8; ++j) {
            this.cachedBlockIDs[j] = j == 0 ? Blocks.BEDROCK : (j == 7 ? Blocks.GRASS : Blocks.DIRT);
        }
    }

    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    public Chunk provideChunk(int par1, int par2) {
        Chunk chunk = new Chunk(this.world, par1, par2);
        for (int k = 0; k < this.cachedBlockIDs.length; ++k) {
            Block block = this.cachedBlockIDs[k];
            if (block == null) continue;
            int l = k >> 4;
            ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[l];
            if (extendedblockstorage == null) {
                chunk.getBlockStorageArray()[l] = extendedblockstorage = new ExtendedBlockStorage(k, !!this.world.provider.hasSkyLight());
            }
            for (int i1 = 0; i1 < 16; ++i1) {
                for (int j1 = 0; j1 < 16; ++j1) {
                    extendedblockstorage.setExtBlockID(i1, k & 0xF, j1, block);
                    extendedblockstorage.setExtBlockMetadata(i1, k & 0xF, j1, (int)this.cachedBlockMetadata[k]);
                }
            }
        }
        this.addScragglyTrees(this.world, par1 * 16, par2 * 16, chunk);
        chunk.generateSkylightMap();
        return chunk;
    }

    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    public void populate(net.minecraft.world.chunk.IChunkProvider par1IChunkProvider, int par2, int par3) {
        int k = par2 * 16;
        int l = par3 * 16;
        this.random.setSeed(this.world.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long)par2 * i1 + (long)par3 * j1 ^ this.world.getSeed());
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
        return "DangerDimension";
    }

    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        Biome biomegenbase = this.world.getBiome(new net.minecraft.util.math.BlockPos(par2, 0, par4));
        return biomegenbase.getSpawnableList(par1EnumCreatureType);
    }

    public net.minecraft.util.math.BlockPos findClosestStructure(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int par1, int par2) {
    }

    public void addScragglyTrees(World world, int chunkX, int chunkZ, Chunk chunk) {
        int howmany = 1 + this.random.nextInt(10);
        if (OreSpawnMain.LessLag == 1) {
            howmany /= 2;
        }
        if (OreSpawnMain.LessLag == 2) {
            howmany /= 4;
        }
        if (howmany == 0) {
            return;
        }
        block0: for (int i = 0; i < howmany; ++i) {
            int posX = 2 + chunkX + this.random.nextInt(12);
            int posZ = 2 + chunkZ + this.random.nextInt(12);
            for (int posY = 20; posY > 2; --posY) {
                if (OreSpawnMain.getBlockIDInChunk(chunk, posX, posY - 1, posZ) != Blocks.GRASS) continue;
                this.ScragglyTreeWithBranches(world, posX, posY, posZ, chunk);
                continue block0;
            }
        }
    }

    public void makeScragglyBranch(World world, int x, int y, int z, int len, int biasx, int biasz, Chunk chunk) {
        for (int k = 0; k < len; ++k) {
            int iy;
            Block bid;
            int ix = this.random.nextInt(2) - this.random.nextInt(2) + biasx;
            int iz = this.random.nextInt(2) - this.random.nextInt(2) + biasz;
            if (ix > 1) {
                ix = 1;
            }
            if (ix < -1) {
                ix = -1;
            }
            if (iz > 1) {
                iz = 1;
            }
            if (iz < -1) {
                iz = -1;
            }
            if ((bid = OreSpawnMain.getBlockIDInChunk(chunk, x += ix, y += (iy = this.random.nextInt(3) > 0 ? 1 : 0), z += iz)) != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }
            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y, z, Blocks.LOG, 0);
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (this.random.nextInt(2) != 1 || (bid = OreSpawnMain.getBlockIDInChunk(chunk, x + m, y, z + n)) != Blocks.AIR) continue;
                    OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x + m, y, z + n, OreSpawnMain.MyAppleLeaves, 0);
                }
            }
            if (this.random.nextInt(2) != 1 || (bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + 1, z)) != Blocks.AIR) continue;
            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, OreSpawnMain.MyAppleLeaves, 0);
        }
    }

    public void ScragglyTreeWithBranches(World world, int x, int y, int z, Chunk chunk) {
        Block bid;
        int k;
        int i = 1 + this.random.nextInt(3);
        int j = i + this.random.nextInt(12);
        for (k = 0; k < i; ++k) {
            bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + k, z);
            if (k >= 1 && bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }
            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + k, z, Blocks.LOG, 0);
        }
        y += i - 1;
        for (k = i; k < j; ++k) {
            int ix = this.random.nextInt(2) - this.random.nextInt(2);
            int iz = this.random.nextInt(2) - this.random.nextInt(2);
            int iy = this.random.nextInt(4) > 0 ? 1 : 0;
            bid = OreSpawnMain.getBlockIDInChunk(chunk, x += ix, y += iy, z += iz);
            if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) break;
            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y, z, Blocks.LOG, 0);
            if (this.random.nextInt(4) == 1) {
                this.makeScragglyBranch(world, x, y, z, this.random.nextInt(1 + j - k), this.random.nextInt(2) - this.random.nextInt(2), this.random.nextInt(2) - this.random.nextInt(2), chunk);
            }
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (this.random.nextInt(2) != 1 || (bid = OreSpawnMain.getBlockIDInChunk(chunk, x + m, y, z + n)) != Blocks.AIR) continue;
                    OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x + m, y, z + n, OreSpawnMain.MyAppleLeaves, 0);
                }
            }
            if (this.random.nextInt(2) != 1 || (bid = OreSpawnMain.getBlockIDInChunk(chunk, x, y + 1, z)) != Blocks.AIR) continue;
            OreSpawnMain.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, OreSpawnMain.MyAppleLeaves, 0);
        }
    }
}

