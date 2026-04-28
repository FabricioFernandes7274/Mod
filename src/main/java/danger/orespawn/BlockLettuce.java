/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockReed
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLettuce
extends BlockReed {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    protected BlockLettuce(int par1) {
        float var3 = 0.375f;
        //this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 1.0f, 0.5f + var3);
        //this.setTickRandomly(true);
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        Block bid = worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock();
        if (bid == Blocks.AIR) {
            return false;
        }
        return bid == OreSpawnMain.MyLettucePlant1 || bid == OreSpawnMain.MyLettucePlant2 || bid == OreSpawnMain.MyLettucePlant3 || bid == OreSpawnMain.MyLettucePlant4 || bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND;
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        boolean dontGrow = false;
        if (worldIn.isRemote) {
            return;
        }
        int var7 = worldIn.getBlockMetadata(par2, par3, par4);
        if ((var7 &= 0xFF) >= 4) {
            Block bid = worldIn.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
            if (bid == OreSpawnMain.MyLettucePlant1) {
                worldIn.setBlockState(par2, par3, par4, OreSpawnMain.MyLettucePlant2, 0, 2);
            } else if (bid == OreSpawnMain.MyLettucePlant2) {
                worldIn.setBlockState(par2, par3, par4, OreSpawnMain.MyLettucePlant3, 0, 2);
            } else if (bid == OreSpawnMain.MyLettucePlant3) {
                worldIn.setBlockState(par2, par3, par4, OreSpawnMain.MyLettucePlant4, 0, 2);
            }
        } else {
            Block bid = worldIn.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
            worldIn.setBlockState(par2, par3, par4, bid, var7 + 1, 2);
        }
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return OreSpawnMain.MyLettuce;
    }

    public int quantityDropped(Random par1Random) {
        if (this == OreSpawnMain.MyLettucePlant4) {
            return 2 + par1Random.nextInt(3);
        }
        return 0;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

