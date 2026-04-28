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
 *  net.minecraft.init.Blocks
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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraft.world.SideOnly;

public class BlockCrystalLeaves
extends BlockLeaves {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    protected BlockCrystalLeaves(int par1) {
        //this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(Item.getItemFromBlock((Block)this), 1, 0));
    }

    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        if (!par1World.isRemote) {
            if (par1World.rand.nextInt(100) == 1) {
                this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(OreSpawnMain.MyCrystalApple));
            }
            if (par1World.rand.nextInt(50) == 1) {
                if (this == OreSpawnMain.MyCrystalLeaves) {
                    this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(OreSpawnMain.MyCrystalPlant));
                }
                if (this == OreSpawnMain.MyCrystalLeaves2) {
                    this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(OreSpawnMain.MyCrystalPlant2));
                }
                if (this == OreSpawnMain.MyCrystalLeaves3) {
                    this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(OreSpawnMain.MyCrystalPlant3));
                }
            }
        }
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        int var7 = 2;
        int totaldist = 0;
        int chance = 20;
        if (par1World.provider.getDimension() == OreSpawnMain.DimensionID4) {
            chance = 100;
            var7 = 1;
        }
        if (!par1World.isRemote && par1World.checkChunksExist(par2 - var7, par3 - var7, par4 - var7, par2 + var7, par3 + var7, par4 + var7)) {
            for (int var12 = -var7; var12 <= var7; ++var12) {
                for (int var13 = -var7; var13 <= 0; ++var13) {
                    for (int var14 = -var7; var14 <= var7; ++var14) {
                        Block bid;
                        totaldist = Math.abs(var12) + Math.abs(var13) + Math.abs(var14);
                        if (totaldist > 3 || (bid = par1World.getBlock(par2 + var12, par3 + var13, par4 + var14)) == null || !bid.canSustainLeaves((IBlockAccess)par1World, par2 + var12, par3 + var13, par4 + var14)) continue;
                        bid = par1World.getBlock(par2, par3 - 1, par4);
                        if (bid == Blocks.AIR && par1World.rand.nextInt(chance) == 3) {
                            this.dropBlockAsItemWithChance(par1World, par2, par3 - 1, par4, 0, 0.0f, 0);
                        }
                        return;
                    }
                }
            }
            this.removeLeaves(par1World, par2, par3, par4);
        }
    }

    private void removeLeaves(World par1World, int par2, int par3, int par4) {
        this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
        par1World.setBlockState(par2, par3, par4, Blocks.AIR, 0, 2);
    }

    public boolean isOpaqueCube() {
        return OreSpawnMain.FastGraphicsLeaves != 0;
    }

    public boolean renderAsNormalBlock() {
        return OreSpawnMain.FastGraphicsLeaves != 0;
    }

    public int getRenderType() {
        if (OreSpawnMain.FastGraphicsLeaves != 0) {
            return super.getRenderType();
        }
        return 1;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        Block i1 = par1IBlockAccess.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
        return OreSpawnMain.FastGraphicsLeaves == 0 || i1 != this;
    }

    @SideOnly(value=Side.CLIENT)
    public int getBlockColor() {
        return 0xDDDDDD;
    }

    @SideOnly(value=Side.CLIENT)
    public int getRenderColor(int par1) {
        return 0xDDDDDD;
    }

    @SideOnly(value=Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return 0xDDDDDD;
    }

    @SideOnly(value=Side.CLIENT)
    public TextureAtlasSprite getIcon(int par1, int par2) {
        return null; //this.blockIcon;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }

    public String[] getUnlocalizedNames() {
        return null;
    }
}

