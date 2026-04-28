/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockReed
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QueenSpawnerBlock
extends BlockReed {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    protected QueenSpawnerBlock(int par1) {
        float var3 = 0.375f;
        //this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 1.0f, 0.5f + var3);
        //this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        return worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock().getMaterial().isSolid();
    }

    public void randomDisplayTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (!worldIn.isRemote) {
            this.updateTick(worldIn, par2, par3, par4, par5Random);
            return;
        }
        if (worldIn.rand.nextInt(20) != 1) {
            return;
        }
        for (int j1 = 0; j1 < 20; ++j1) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, (double)((float)par2 + worldIn.rand.nextFloat()), (double)par3 + (double)worldIn.rand.nextFloat(), (double)((float)par4 + worldIn.rand.nextFloat()), 0.0, 0.0, 0.0);
        }
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.isRemote) {
            return;
        }
        world.scheduleBlockUpdate(x, y, z, (Block)this, 100);
    }

    public void onBlockDestroyedByPlayer(World worldIn, int par2, int par3, int par4, int par5) {
        this.updateTick(worldIn, par2, par3, par4, null);
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.isRemote) {
            return;
        }
        if (OreSpawnMain.TheQueenEnable != 0) {
            QueenSpawnerBlock.spawnTheQueen(worldIn, par2, par3 + 8, par4);
        }
        worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4), Blocks.AIR.getStateFromMeta(0), 2);
        worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3 + 1, par4), Blocks.AIR.getStateFromMeta(0), 2);
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock((Block)OreSpawnMain.MyQueenSpawnerBlock);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public static Entity spawnTheQueen(World par0World, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)"The Queen", (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
            ((TheQueen)var8).setGuardMode(1);
        }
        return var8;
    }

    public boolean canBlockStay(World worldIn, int par2, int par3, int par4) {
        this.updateTick(worldIn, par2, par3, par4, null);
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

