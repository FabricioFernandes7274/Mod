/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 *  net.minecraft.world.chunk.Chunk
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAppleSeed
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemAppleSeed(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if (!world.isRemote) {
            Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock();
            if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
                return false;
            }
            if (this == OreSpawnMain.MyAppleSeed) {
                this.makeTree(world, x, y, z, OreSpawnMain.MyAppleLeaves, null);
            } else if (this == OreSpawnMain.MyCherrySeed) {
                this.makeTree(world, x, y, z, OreSpawnMain.MyCherryLeaves, null);
            } else {
                this.makeTree(world, x, y, z, OreSpawnMain.MyPeachLeaves, null);
            }
        }
        if (!par2EntityPlayer.isCreative()) {
            par1ItemStack.setCount(par1ItemStack.getCount() - 1);
        }
        return true;
    }

    public void makeTree(World world, int x, int y, int z, Block blkid, Chunk chunk) {
        int j;
        Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock();
        if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
            return;
        }
        int h1 = 12;
        int h2 = 6;
        int h3 = 9;
        int h4 = 6;
        int h5 = 14;
        int w1 = 5;
        int w2 = 3;
        if (blkid == OreSpawnMain.MyPeachLeaves) {
            h1 = 10;
            h2 = 5;
            h3 = 7;
            h4 = 5;
            h5 = 12;
            w1 = 4;
            w2 = 2;
        }
        if (blkid == OreSpawnMain.MyCherryLeaves) {
            h1 = 8;
            h2 = 3;
            h3 = 5;
            h4 = 3;
            h5 = 10;
            w1 = 3;
            w2 = 1;
        }
        for (j = 1; j < h1; ++j) {
            world.setBlockState(x, y + j, z, Blocks.LOG, 0, 2);
        }
        for (j = 1; j < w1; ++j) {
            OreSpawnMain.setBlockSuperFast(world, x + j, y + h2, z, Blocks.LOG, 0, 2, chunk);
        }
        for (j = 1; j < w1; ++j) {
            OreSpawnMain.setBlockSuperFast(world, x - j, y + h2, z, Blocks.LOG, 0, 2, chunk);
        }
        for (j = 1; j < w1; ++j) {
            OreSpawnMain.setBlockSuperFast(world, x, y + h2, z + j, Blocks.LOG, 0, 2, chunk);
        }
        for (j = 1; j < w1; ++j) {
            OreSpawnMain.setBlockSuperFast(world, x, y + h2, z - j, Blocks.LOG, 0, 2, chunk);
        }
        for (j = 1; j < w2; ++j) {
            OreSpawnMain.setBlockSuperFast(world, x + j, y + h3, z, Blocks.LOG, 0, 2, chunk);
        }
        for (j = 1; j < w2; ++j) {
            OreSpawnMain.setBlockSuperFast(world, x - j, y + h3, z, Blocks.LOG, 0, 2, chunk);
        }
        for (j = 1; j < w2; ++j) {
            OreSpawnMain.setBlockSuperFast(world, x, y + h3, z + j, Blocks.LOG, 0, 2, chunk);
        }
        for (j = 1; j < w2; ++j) {
            OreSpawnMain.setBlockSuperFast(world, x, y + h3, z - j, Blocks.LOG, 0, 2, chunk);
        }
        for (int i = h4; i < h5; ++i) {
            int width = 6;
            if (i > 8) {
                width = 5;
            }
            if (i > 10) {
                width = 4;
            }
            if (blkid != OreSpawnMain.MyAppleLeaves) {
                --width;
            }
            for (j = -width; j <= width; ++j) {
                for (int k = -width; k <= width; ++k) {
                    bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k, y + i, z + j)).getBlock();
                    if (bid != Blocks.AIR) continue;
                    OreSpawnMain.setBlockSuperFast(world, x + k, y + i, z + j, blkid, 0, 2, chunk);
                }
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

