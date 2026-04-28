/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockCrops
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.TextureAtlasSprite
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraft.world.World;

public class BlockMosquitoPlant
extends BlockCrops {
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite[] field_94364_a;

    public BlockMosquitoPlant(int par1) {
        //this.setTickRandomly(true);
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        super.updateTick(worldIn, par2, par3, par4, par5Random);
        if (worldIn.isRemote) {
            return;
        }
        if (OreSpawnMain.MosquitoEnable == 0) {
            return;
        }
        int rate = worldIn.getBlockMetadata(par2, par3, par4);
        rate &= 7;
        if ((rate = 7 - rate) > 1 && OreSpawnMain.OreSpawnRand.nextInt(rate) != 0) {
            return;
        }
        Block bid = worldIn.getBlockState(new BlockPos(par2, par3 + 1, par4)).getBlock();
        if (bid == Blocks.AIR) {
            int howmany = 2 + OreSpawnMain.OreSpawnRand.nextInt(5);
            for (int i = 0; i < howmany; ++i) {
                BlockMosquitoPlant.spawnCreature(worldIn, "Mosquito", (double)par2 + 0.5, (double)par3 + 1.01, (double)par4 + 0.5);
            }
        }
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

    public TextureAtlasSprite getIcon(int par1, int par2) {
        if (par2 < 7) {
            if (par2 >= 6) {
                par2 = 4;
            }
            return this.field_94364_a[par2 >> 1];
        }
        return this.field_94364_a[3];
    }

    public int quantityDropped(Random par1Random) {
        return 1 + par1Random.nextInt(5);
    }

    protected Item getSeed() {
        return OreSpawnMain.MyMosquitoSeed;
    }

    protected Item getCrop() {
        return null;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap par1net.minecraft.client.renderer.texture.TextureMap) {
        this.field_94364_a = new TextureAtlasSprite[4];
        for (int i = 0; i < this.field_94364_a.length; ++i) {
            this.field_94364_a[i] = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:mosquito_" + i));
        }
    }
}

