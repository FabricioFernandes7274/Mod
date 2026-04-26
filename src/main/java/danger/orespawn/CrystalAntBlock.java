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
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.TextureAtlasSprite
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalAntBlock
extends Block {
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite[] field_94364_a;

    protected CrystalAntBlock(int par1) {
        super(Material.GRASS);
        //this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @SideOnly(value=Side.CLIENT)
    public TextureAtlasSprite getIcon(int par1, int par2) {
        if (this.field_94364_a == null) {
            return null;
        }
        return par1 == 1 ? this.field_94364_a[0] : (par1 == 0 ? this.field_94364_a[1] : this.field_94364_a[2]);
    }

    @SideOnly(value=Side.CLIENT)
    public TextureAtlasSprite getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        if (this.field_94364_a == null) {
            return null;
        }
        if (par5 == 1) {
            return this.field_94364_a[0];
        }
        if (par5 == 0) {
            return this.field_94364_a[1];
        }
        return this.field_94364_a[2];
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        int howmany = 0;
        if (!par1World.isRemote) {
            if (par1World.isRaining()) {
                return;
            }
            Block bid = par1World.getBlock(par2, par3 + 1, par4);
            if (bid == Blocks.AIR) {
                howmany = OreSpawnMain.OreSpawnRand.nextInt(6) + 2;
                for (int i = 0; i < howmany; ++i) {
                    if (this == OreSpawnMain.MyAntBlock) {
                        if (OreSpawnMain.BlackAntEnable == 0) continue;
                        CrystalAntBlock.spawnCreature(par1World, "Ant", (double)par2 + 0.5, (double)par3 + 1.01, (double)par4 + 0.5);
                        continue;
                    }
                    if (this == OreSpawnMain.MyRedAntBlock) {
                        if (OreSpawnMain.RedAntEnable == 0) continue;
                        CrystalAntBlock.spawnCreature(par1World, "Red Ant", (double)par2 + 0.5, (double)par3 + 1.01, (double)par4 + 0.5);
                        continue;
                    }
                    if (this == OreSpawnMain.MyUnstableAntBlock) {
                        if (OreSpawnMain.UnstableAntEnable == 0) continue;
                        CrystalAntBlock.spawnCreature(par1World, "Unstable Ant", (double)par2 + 0.5, (double)par3 + 1.01, (double)par4 + 0.5);
                        continue;
                    }
                    if (this == OreSpawnMain.TermiteBlock) {
                        if (OreSpawnMain.TermiteEnable == 0) continue;
                        CrystalAntBlock.spawnCreature(par1World, "Termite", (double)par2 + 0.5, (double)par3 + 1.01, (double)par4 + 0.5);
                        continue;
                    }
                    if (this == OreSpawnMain.CrystalTermiteBlock) {
                        if (OreSpawnMain.TermiteEnable == 0) continue;
                        CrystalAntBlock.spawnCreature(par1World, "Termite", (double)par2 + 0.5, (double)par3 + 1.01, (double)par4 + 0.5);
                        continue;
                    }
                    if (OreSpawnMain.RainbowAntEnable == 0) continue;
                    CrystalAntBlock.spawnCreature(par1World, "Rainbow Ant", (double)par2 + 0.5, (double)par3 + 1.01, (double)par4 + 0.5);
                }
            }
        }
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock((Block)this);
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap par1net.minecraft.client.renderer.texture.TextureMap) {
        this.field_94364_a = new TextureAtlasSprite[3];
        this.field_94364_a[0] = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:crystaltermite_top");
        this.field_94364_a[1] = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:crystaltermite_bottom");
        this.field_94364_a[2] = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:crystaltermite_side");
    }
}

