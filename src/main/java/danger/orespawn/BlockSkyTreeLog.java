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
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSkyTreeLog
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    protected BlockSkyTreeLog(int par1, int par2) {
        super(Material.WOOD);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(Item.getItemFromBlock((Block)this), 1, 0));
    }

    protected ItemStack createStackedBlock(int par1) {
        return new ItemStack(Item.getItemFromBlock((Block)this), 1, 0);
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock((Block)OreSpawnMain.MySkyTreeLog);
    }

    public void breakRecursor(World world, int x, int y, int z, int xf, int yf, int zf, int recursion) {
        int var7 = 1;
        if (recursion > 1000) {
            return;
        }
        for (int var9 = -var7; var9 <= var7; ++var9) {
            for (int var10 = -var7; var10 <= var7; ++var10) {
                for (int var11 = -var7; var11 <= var7; ++var11) {
                    Block var12;
                    if (var9 == 0 && var10 == 0 && var11 == 0 || x + var9 == xf && y + var10 == yf && z + var11 == zf || recursion > 0 && x + var9 >= xf - var7 && x + var9 <= xf + var7 && y + var10 >= yf - var7 && y + var10 <= yf + var7 && z + var11 >= zf - var7 && z + var11 <= zf + var7 || (var12 = world.getBlockState(new net.minecraft.util.math.BlockPos(x + var9, y + var10, z + var11)).getBlock()) != this) continue;
                    world.setBlockState(x + var9, y + var10, z + var11, Blocks.AIR, 0, 2);
                    this.dropBlockAsItem(world, x + var9, y + var10, z + var11, 0, 0);
                    this.breakRecursor(world, x + var9, y + var10, z + var11, x, y, z, recursion + 1);
                }
            }
        }
    }

    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
        par1World.setBlockState(par2, par3, par4, Blocks.AIR, 0, 2);
        this.breakRecursor(par1World, par2, par3, par4, par2, par3, par4, 0);
        this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5));
    }
}

