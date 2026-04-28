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
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreAmethyst
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public OreAmethyst(int i) {
        super(Material.ROCK);
        this.setHardness(10.0f);
        this.setResistance(4.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void dropBlockAsItemWithChance(World worldIn, int par2, int par3, int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(worldIn, par2, par3, par4, par5, par6, par7);
        int j1 = 5 + worldIn.rand.nextInt(5) + worldIn.rand.nextInt(5);
        this.dropXpOnBlockBreak(worldIn, par2, par3, par4, j1);
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return OreSpawnMain.MyAmethyst;
    }

    public int quantityDroppedWithBonus(int par1, Random par2Random) {
        return 1 + par2Random.nextInt(2);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

