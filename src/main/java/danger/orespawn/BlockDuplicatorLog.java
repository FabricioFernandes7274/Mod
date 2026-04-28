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
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDuplicatorLog
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    protected BlockDuplicatorLog(int par1) {
        super(Material.WOOD);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        //this.setTickRandomly(true);
    }

    public int tickRate() {
        return 1;
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.isRemote) {
            return;
        }
        if (OreSpawnMain.enableduplicatortree != 0) {
            OreSpawnMain.OreSpawnTrees.DuplicatorTree(worldIn, par2, par3, par4);
        }
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(Item.getItemFromBlock((Block)this), 1, 0));
    }

    protected ItemStack createStackedBlock(int par1) {
        return new ItemStack((Block)this, 1, 0);
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

