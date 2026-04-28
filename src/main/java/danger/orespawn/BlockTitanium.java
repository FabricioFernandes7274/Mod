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
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTitanium
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public BlockTitanium(int par1) {
        super(Material.ROCK);
        this.setHardness(5.0f);
        this.setResistance(5.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setLightLevel(0.5f);
    }

    public int tickRate() {
        return 100;
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.rand.nextInt(20) == 0) {
            this.sparkle(worldIn, par2, par3, par4);
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
            int which = worldIn.rand.nextInt(3);
            if (which == 0) {
                worldIn.spawnParticle("flame", var9, var11, var13, 0.0, 0.0, 0.0);
            }
            if (which == 1) {
                worldIn.spawnParticle("smoke", var9, var11, var13, 0.0, 0.0, 0.0);
            }
            if (which != 2) continue;
            worldIn.spawnParticle("reddust", var9, var11, var13, 0.0, 0.0, 0.0);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

