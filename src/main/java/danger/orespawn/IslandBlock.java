/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockReed
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IslandBlock
extends BlockReed {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    protected IslandBlock(int par1) {
        float var3 = 0.375f;
        //this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 1.0f, 0.5f + var3);
        //this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        return worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock().getMaterial().isSolid();
    }

    public void randomDisplayTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.rand.nextInt(20) != 1) {
            return;
        }
        for (int j1 = 0; j1 < 20; ++j1) {
            worldIn.spawnParticle("happyVillager", (double)((float)par2 + worldIn.rand.nextFloat()), (double)par3 + (double)worldIn.rand.nextFloat(), (double)((float)par4 + worldIn.rand.nextFloat()), 0.0, 0.0, 0.0);
        }
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        boolean isok = true;
        if (worldIn.isRemote) {
            return;
        }
        int n = 1 + worldIn.rand.nextInt(3);
        int m = 64;
        if (OreSpawnMain.IslandSizeFactor == 2) {
            m = 55;
        }
        if (OreSpawnMain.IslandSizeFactor == 1) {
            m = 45;
        }
        for (int i = 0; i < n; ++i) {
            int height = 12 + worldIn.rand.nextInt(m);
            isok = true;
            block1: for (int k = -10; k <= 10; ++k) {
                for (int j = -10; j <= 10; ++j) {
                    Block bid = worldIn.getBlockState(new BlockPos(par2 + j, par3 + height, par4 + k)).getBlock();
                    if (bid == Blocks.AIR) continue;
                    isok = false;
                    continue block1;
                }
            }
            if (!isok) continue;
            if (worldIn.rand.nextInt(25) == 1) {
                IslandBlock.spawnCreature(worldIn, "Island", par2, par3 + height, par4);
                continue;
            }
            IslandBlock.spawnCreature(worldIn, "IslandToo", par2, par3 + height, par4);
        }
        worldIn.setBlockState(par2, par3, par4, Blocks.AIR, 0, 2);
        worldIn.setBlockState(par2, par3 + 1, par4, Blocks.AIR, 0, 2);
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock((Block)OreSpawnMain.MyIslandBlock);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

