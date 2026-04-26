/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLeaves
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityExpBottle
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.TextureAtlasSprite
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockExperienceLeaves
extends BlockLeaves {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    private TextureAtlasSprite generic_solid = null;

    protected BlockExperienceLeaves(int par1) {
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(Item.getItemFromBlock((Block)this), 1, 0));
    }

    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        if (!par1World.isRemote) {
            // empty if block
        }
    }

    public int quantityDropped(Random par1Random) {
        return 0;
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        int var7 = 2;
        int totaldist = 0;
        if (!par1World.isRemote && par1World.checkChunksExist(par2 - var7, par3 - var7, par4 - var7, par2 + var7, par3 + var7, par4 + var7)) {
            for (int var12 = -var7; var12 <= var7; ++var12) {
                for (int var13 = -var7; var13 <= 0; ++var13) {
                    for (int var14 = -var7; var14 <= var7; ++var14) {
                        Block bid;
                        totaldist = Math.abs(var12) + Math.abs(var13) + Math.abs(var14);
                        if (totaldist > 3 || (bid = par1World.getBlock(par2 + var12, par3 + var13, par4 + var14)) == null || !bid.canSustainLeaves((IBlockAccess)par1World, par2 + var12, par3 + var13, par4 + var14)) continue;
                        long t = par1World.getWorldTime();
                        if ((t %= 24000L) < 14000L || t > 22000L) {
                            return;
                        }
                        if (par1World.rand.nextInt(65) == 1 && (bid = par1World.getBlock(par2, par3 + 1, par4)) == Blocks.AIR) {
                            this.dropBlockAsItem(par1World, par2, par3 + 2, par4, new ItemStack(Items.EXPERIENCE_BOTTLE));
                        }
                        if (par1World.rand.nextInt(75) == 1 && (bid = par1World.getBlock(par2, par3 - 1, par4)) == Blocks.AIR) {
                            EntityExpBottle var2 = new EntityExpBottle(par1World, (double)par2, (double)(par3 - 1), (double)par4);
                            var2.setLocationAndAngles((double)par2, (double)(par3 - 1), (double)par4, 0.0f, 0.0f);
                            var2.setThrowableHeading((double)((par1World.rand.nextFloat() - par1World.rand.nextFloat()) / 2.0f), (double)-0.1f, (double)((par1World.rand.nextFloat() - par1World.rand.nextFloat()) / 2.0f), 0.4f, 5.0f);
                            par1World.spawnEntity((Entity)var2);
                        }
                        return;
                    }
                }
            }
            this.removeLeaves(par1World, par2, par3, par4);
        }
    }

    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        int i;
        Block bid;
        long t = par1World.getWorldTime();
        if ((t %= 24000L) < 13000L || t > 23000L) {
            return;
        }
        int rate = 0;
        if (t < 14000L) {
            rate = (14000 - (int)t) / 2;
        }
        if (t > 22000L) {
            rate = (int)(t - 22000L) / 2;
        }
        if (par1World.rand.nextInt(200 + rate) == 1 && (bid = par1World.getBlock(par2, par3 + 1, par4)) == Blocks.AIR) {
            for (i = 0; i < 10; ++i) {
                par1World.spawnParticle("fireworksSpark", (double)par2, (double)par3 + 1.25, (double)par4, par1World.rand.nextGaussian(), Math.abs(par1World.rand.nextGaussian()), par1World.rand.nextGaussian());
            }
        }
        if (par1World.rand.nextInt(40 + rate) == 1 && (bid = par1World.getBlock(par2, par3 - 1, par4)) == Blocks.AIR) {
            for (i = 0; i < 4; ++i) {
                par1World.spawnParticle("fireworksSpark", (double)par2, (double)par3 - 1.25, (double)par4, (double)(par1World.rand.nextFloat() - par1World.rand.nextFloat()), (double)(-Math.abs(par1World.rand.nextFloat())), (double)(par1World.rand.nextFloat() - par1World.rand.nextFloat()));
            }
        }
    }

    private void removeLeaves(World par1World, int par2, int par3, int par4) {
        this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
        par1World.setBlockState(par2, par3, par4, Blocks.AIR, 0, 2);
    }

    public boolean isOpaqueCube() {
        return OreSpawnMain.FastGraphicsLeaves != 0;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        Block i1 = par1IBlockAccess.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
        return OreSpawnMain.FastGraphicsLeaves == 0 || i1 != this;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5));
        this.generic_solid = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:generic_solid")));
    }

    @SideOnly(value=Side.CLIENT)
    public TextureAtlasSprite getIcon(int par1, int par2) {
        if (OreSpawnMain.FastGraphicsLeaves != 0) {
            return this.generic_solid;
        }
        return null; //this.blockIcon;
    }

    public String[] getUnlocalizedNames() {
        return null;
    }
}

