/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreUranium
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    private boolean glowing = false;
    private int glowcount = 0;

    public OreUranium(int par1) {
        super(Material.ROCK);
        this.setHardness(10.0f);
        this.setResistance(1.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        //this.setTickRandomly(true);
        this.glowing = false;
    }

    public int tickRate() {
        return 30;
    }

    public void onBlockClicked(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer) {
        this.glow(worldIn, par2, par3, par4);
        super.onBlockClicked(worldIn, par2, par3, par4, par5EntityPlayer);
    }

    public void onEntityWalking(World worldIn, int par2, int par3, int par4, Entity par5Entity) {
        this.glow(worldIn, par2, par3, par4);
        super.onEntityWalking(worldIn, par2, par3, par4, par5Entity);
    }

    public boolean onBlockActivated(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        this.glow(worldIn, par2, par3, par4);
        return super.onBlockActivated(worldIn, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }

    private void glow(World worldIn, int par2, int par3, int par4) {
        this.glowing = true;
        this.glowcount = 10;
        this.sparkle(worldIn, par2, par3, par4);
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (this.glowing) {
            this.sparkle(worldIn, par2, par3, par4);
            if (this.glowcount > 0) {
                --this.glowcount;
            } else {
                this.glowing = false;
            }
        }
    }

    private void sparkle(World worldIn, int par2, int par3, int par4) {
        Random var5 = worldIn.rand;
        double var6 = 0.0625;
        for (int var8 = 0; var8 < 6; ++var8) {
            double var9 = (float)par2 + var5.nextFloat();
            double var11 = (float)par3 + var5.nextFloat();
            double var13 = (float)par4 + var5.nextFloat();
            if (var8 == 0 && !worldIn.getBlockState(new BlockPos(par2, par3 + 1, par4)).getBlock().isOpaqueCube()) {
                var11 = (double)(par3 + 1) + var6;
            }
            if (var8 == 1 && !worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock().isOpaqueCube()) {
                var11 = (double)(par3 + 0) - var6;
            }
            if (var8 == 2 && !worldIn.getBlockState(new BlockPos(par2, par3, par4 + 1)).getBlock().isOpaqueCube()) {
                var13 = (double)(par4 + 1) + var6;
            }
            if (var8 == 3 && !worldIn.getBlockState(new BlockPos(par2, par3, par4 - 1)).getBlock().isOpaqueCube()) {
                var13 = (double)(par4 + 0) - var6;
            }
            if (var8 == 4 && !worldIn.getBlockState(new BlockPos(par2 + 1, par3, par4)).getBlock().isOpaqueCube()) {
                var9 = (double)(par2 + 1) + var6;
            }
            if (var8 == 5 && !worldIn.getBlockState(new BlockPos(par2 - 1, par3, par4)).getBlock().isOpaqueCube()) {
                var9 = (double)(par2 + 0) - var6;
            }
            if (!(var9 < (double)par2 || var9 > (double)(par2 + 1) || var11 < 0.0 || var11 > (double)(par3 + 1) || var13 < (double)par4) && !(var13 > (double)(par4 + 1))) continue;
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, var9, var11, var13, 0.0, 0.0, 0.0);
        }
    }

    public void dropBlockAsItemWithChance(World worldIn, int par2, int par3, int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(worldIn, par2, par3, par4, par5, par6, par7);
        int j1 = 5 + worldIn.rand.nextInt(5) + worldIn.rand.nextInt(10);
        if (par3 < 40) {
            this.dropXpOnBlockBreak(worldIn, par2, par3, par4, j1);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

