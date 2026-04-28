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
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.net.minecraft.util.EnumFacing
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystalTorch
extends BlockTorch {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public BlockCrystalTorch(int par1) {
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.rand.nextInt(4) != 1) {
            return;
        }
        int var6 = worldIn.getBlockMetadata(par2, par3, par4);
        double var7 = (float)par2 + 0.5f;
        double var9 = (float)par3 + 0.7f;
        double var11 = (float)par4 + 0.5f;
        double var13 = 0.213;
        double var15 = 0.271;
        if (var6 == 1) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, var7 - var15, var9 + var13, var11, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f), (double)(worldIn.rand.nextFloat() / 8.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f));
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7 - var15, var9 + var13, var11, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f), (double)(worldIn.rand.nextFloat() / 10.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f));
        } else if (var6 == 2) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, var7 + var15, var9 + var13, var11, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f), (double)(worldIn.rand.nextFloat() / 8.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f));
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7 + var15, var9 + var13, var11, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f), (double)(worldIn.rand.nextFloat() / 10.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f));
        } else if (var6 == 3) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, var7, var9 + var13, var11 - var15, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f), (double)(worldIn.rand.nextFloat() / 8.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f));
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7, var9 + var13, var11 - var15, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f), (double)(worldIn.rand.nextFloat() / 10.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f));
        } else if (var6 == 4) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, var7, var9 + var13, var11 + var15, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f), (double)(worldIn.rand.nextFloat() / 8.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f));
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7, var9 + var13, var11 + var15, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f), (double)(worldIn.rand.nextFloat() / 10.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f));
        } else {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, var7, var9, var11, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f), (double)(worldIn.rand.nextFloat() / 8.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0f));
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7, var9, var11, (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f), (double)(worldIn.rand.nextFloat() / 10.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0f));
        }
    }

    private boolean isCrystalBlock(World worldIn, int par2, int par3, int par4) {
        Block l = worldIn.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
        if (l == OreSpawnMain.CrystalStone) {
            return true;
        }
        if (l == OreSpawnMain.CrystalGrass) {
            return true;
        }
        if (l == OreSpawnMain.MyCrystalTreeLog) {
            return true;
        }
        return l == OreSpawnMain.CrystalPlanksBlock;
    }

    private boolean isItSolidOnSide(World worldIn, int par2, int par3, int par4, net.minecraft.util.EnumFacing dir, boolean tf) {
        if (this.isCrystalBlock(worldIn, par2, par3, par4)) {
            return true;
        }
        return worldIn.isSideSolid(par2, par3, par4, dir, tf);
    }

    private boolean canPlaceTorchOn(World worldIn, int par2, int par3, int par4) {
        Block l = worldIn.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
        if (this.isCrystalBlock(worldIn, par2, par3, par4)) {
            return true;
        }
        if (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, (int)par2, (int)par3, (int)par4)) {
            return true;
        }
        return l != null && l.canPlaceTorchOnTop(worldIn, par2, par3, par4);
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        return this.isItSolidOnSide(worldIn, par2 - 1, par3, par4, net.minecraft.util.EnumFacing.EAST, true) || this.isItSolidOnSide(worldIn, par2 + 1, par3, par4, net.minecraft.util.EnumFacing.WEST, true) || this.isItSolidOnSide(worldIn, par2, par3, par4 - 1, net.minecraft.util.EnumFacing.SOUTH, true) || this.isItSolidOnSide(worldIn, par2, par3, par4 + 1, net.minecraft.util.EnumFacing.NORTH, true) || this.canPlaceTorchOn(worldIn, par2, par3 - 1, par4);
    }

    public int onBlockPlaced(World worldIn, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
        int j1 = par9;
        if (par5 == 1 && this.canPlaceTorchOn(worldIn, par2, par3 - 1, par4)) {
            j1 = 5;
        }
        if (par5 == 2 && (worldIn.isSideSolid(par2, par3, par4 + 1, net.minecraft.util.EnumFacing.NORTH, true) || this.isCrystalBlock(worldIn, par2, par3, par4 + 1))) {
            j1 = 4;
        }
        if (par5 == 3 && (worldIn.isSideSolid(par2, par3, par4 - 1, net.minecraft.util.EnumFacing.SOUTH, true) || this.isCrystalBlock(worldIn, par2, par3, par4 - 1))) {
            j1 = 3;
        }
        if (par5 == 4 && (worldIn.isSideSolid(par2 + 1, par3, par4, net.minecraft.util.EnumFacing.WEST, true) || this.isCrystalBlock(worldIn, par2 + 1, par3, par4))) {
            j1 = 2;
        }
        if (par5 == 5 && (worldIn.isSideSolid(par2 - 1, par3, par4, net.minecraft.util.EnumFacing.EAST, true) || this.isCrystalBlock(worldIn, par2 - 1, par3, par4))) {
            j1 = 1;
        }
        return j1;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

