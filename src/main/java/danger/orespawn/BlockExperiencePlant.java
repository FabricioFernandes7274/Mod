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

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockExperiencePlant
extends BlockReed {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    protected BlockExperiencePlant(int par1) {
        float var3 = 0.375f;
        //this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 1.0f, 0.5f + var3);
        //this.setTickRandomly(true);
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        Block bid = worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock();
        if (bid == Blocks.AIR) {
            return false;
        }
        return bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND;
    }

    public void randomDisplayTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.rand.nextInt(20) != 1) {
            return;
        }
        for (int j1 = 0; j1 < 20; ++j1) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (double)((float)par2 + worldIn.rand.nextFloat()), (double)par3 + (double)worldIn.rand.nextFloat(), (double)((float)par4 + worldIn.rand.nextFloat()), 0.0, 0.0, 0.0);
        }
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.isRemote) {
            return;
        }
        if (worldIn.rand.nextInt(10) != 1) {
            return;
        }
        worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4), Blocks.AIR.getStateFromMeta(0), 2);
        OreSpawnMain.OreSpawnTrees.ExperienceTree(worldIn, par2, par3 - 1, par4);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock((Block)OreSpawnMain.MyExperiencePlant);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public int idPicked(World worldIn, int par2, int par3, int par4) {
        return 0;
    }

    protected Item getSeedItem() {
        return OreSpawnMain.MyExperienceTreeSeed;
    }

    protected int getCropItem() {
        return 0;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

