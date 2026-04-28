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
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreCrystalCrystal
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public OreCrystalCrystal(int par1, float lv, float f1, float f2) {
        super(Material.ROCK);
        this.setHardness(f1);
        this.setResistance(f2);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setLightLevel(lv);
        //this.setTickRandomly(true);
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.rand.nextInt(20) == 0) {
            this.sparkle(worldIn, par2, par3, par4);
        }
    }

    private void sparkle(World worldIn, int par2, int par3, int par4) {
        boolean which = false;
        float dx = 0.5f;
        float dz = 0.5f;
        float dy = 0.5f;
        if (this == OreSpawnMain.TigersEye) {
            worldIn.spawnParticle("flame", (double)((float)par2 + dx), (double)par3 + (double)dy, (double)((float)par4 + dz), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 4.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 4.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 4.0f));
        } else {
            worldIn.spawnParticle("fireworksSpark", (double)((float)par2 + dx), (double)par3 + (double)dy, (double)((float)par4 + dz), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 4.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 4.0f), (double)((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 4.0f));
        }
    }

    public int getRenderType() {
        return 1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public void onBlockDestroyedByPlayer(World worldIn, int par2, int par3, int par4, int par5) {
        if (this == OreSpawnMain.CrystalCrystal && !worldIn.isRemote && worldIn.rand.nextInt(10) == 1) {
            worldIn.newExplosion((Entity)null, (double)((float)par2 + 0.5f), (double)((float)par3 + 0.5f), (double)((float)par4 + 0.5f), 1.0f, true, worldIn.getGameRules().getGameRuleBooleanValue("mobGriefing"));
        }
        super.onBlockDestroyedByPlayer(worldIn, par2, par3, par4, par5);
    }

    public void dropBlockAsItemWithChance(World worldIn, int par2, int par3, int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(worldIn, par2, par3, par4, par5, par6, par7);
        int j1 = 5 + worldIn.rand.nextInt(5) + worldIn.rand.nextInt(10);
        if (par3 < 40) {
            this.dropXpOnBlockBreak(worldIn, par2, par3, par4, j1);
        }
    }

    public int quantityDropped(Random par1Random) {
        if (this != OreSpawnMain.TigersEye) {
            return 1;
        }
        return par1Random.nextInt(2);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

