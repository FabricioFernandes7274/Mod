/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockTorch
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import danger.orespawn.EntityAnt;
import danger.orespawn.Kraken;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class KrakenRepellent
extends BlockTorch {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public KrakenRepellent(int par1) {
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);
        double var7 = (float)par2 + 0.5f;
        double var9 = (float)par3 + 0.7f;
        double var11 = (float)par4 + 0.5f;
        double var13 = 0.413;
        double var15 = 0.271;
        if (var6 == 1) {
            par1World.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 2) {
            par1World.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 3) {
            par1World.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
        } else if (var6 == 4) {
            par1World.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
        } else {
            par1World.spawnParticle("smoke", var7, var9 + 0.21, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var9 + 0.21, var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("reddust", var7, var9 + 0.21, var11, 0.0, 0.0, 0.0);
        }
    }

    public int tickRate(World par1World) {
        return 10;
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!par1World.isRemote) {
            this.findSomethingToRepell(par1World, par2, par3, par4);
            par1World.scheduleBlockUpdate(par2, par3, par4, (Block)this, this.tickRate(par1World));
        }
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        world.scheduleBlockUpdate(x, y, z, (Block)this, this.tickRate(world));
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block blockId) {
        world.scheduleBlockUpdate(x, y, z, (Block)this, this.tickRate(world));
    }

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    private void findSomethingToRepell(World par1World, int par2, int par3, int par4) {
        AxisAlignedBB bb = new AxisAlignedBB((double)((double)par2 - 20.0), (double)((double)par3 - 10.0), (double)((double)par4 - 20.0), (double)((double)par2 + 20.0), (double)((double)par3 + 40.0), (double)((double)par4 + 20.0));
        List var5 = par1World.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, bb);
        Iterator var2 = var5.iterator();
        net.minecraft.entity.EntityLivingBase var3 = null;
        while (var2.hasNext()) {
            double d;
            double f;
            double d3;
            double d2;
            double d1;
            var3 = (net.minecraft.entity.EntityLivingBase)var2.next();
            if (var3 != null && var3 instanceof Kraken) {
                d1 = var3.posX - (double)par2;
                d2 = var3.posY - 15.0 - (double)par3;
                d3 = var3.posZ - (double)par4;
                f = d1 * d1 + d2 * d2 + d3 * d3;
                f = Math.sqrt(f);
                if ((f = 20.0 - f) > 20.0) {
                    f = 20.0;
                }
                if (f < 0.0) {
                    f = 0.0;
                }
                d = (float)Math.atan2(var3.posX - (double)par2, var3.posZ - (double)par4);
                var3.motionX += (f *= 0.4) * Math.sin(d);
                var3.motionZ += f * Math.cos(d);
            }
            if (var3 == null || !(var3 instanceof EntityAnt)) continue;
            d1 = var3.posX - (double)par2;
            d2 = var3.posY - (double)par3;
            d3 = var3.posZ - (double)par4;
            f = d1 * d1 + d2 * d2 + d3 * d3;
            f = Math.sqrt(f);
            if ((f = 20.0 - f) > 20.0) {
                f = 20.0;
            }
            if (f < 0.0) {
                f = 0.0;
            }
            d = (float)Math.atan2(var3.posX - (double)par2, var3.posZ - (double)par4);
            var3.motionX += (f *= 0.4) * Math.sin(d);
            var3.motionZ += f * Math.cos(d);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

