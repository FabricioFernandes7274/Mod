/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockReed
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTomato
extends BlockReed {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    private int myMaxHeight = 0;

    protected BlockTomato(int par1) {
        float var3 = 0.375f;
        //this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 1.0f, 0.5f + var3);
        //this.setTickRandomly(true);
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        Block bid = worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock();
        if (bid == Blocks.AIR) {
            return false;
        }
        return bid == OreSpawnMain.MyTomatoPlant1 || bid == OreSpawnMain.MyTomatoPlant2 || bid == OreSpawnMain.MyTomatoPlant3 || bid == OreSpawnMain.MyTomatoPlant4 || bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND;
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        Block bid;
        int Height = 1;
        boolean dontGrow = false;
        if (worldIn.isRemote) {
            return;
        }
        if (this != OreSpawnMain.MyTomatoPlant1 && this != OreSpawnMain.MyTomatoPlant2) {
            return;
        }
        int var7 = worldIn.getBlockMetadata(par2, par3, par4);
        this.myMaxHeight = var7 >> 8;
        var7 &= 0xFF;
        if (this.myMaxHeight == 0) {
            this.myMaxHeight = 3 + OreSpawnMain.OreSpawnRand.nextInt(3);
        }
        if ((bid = worldIn.getBlockState(new BlockPos(par2, par3 + 1, par4)).getBlock()) == Blocks.AIR) {
            for (int var6 = 1; var6 < 10 && ((bid = worldIn.getBlockState(new BlockPos(par2, par3 - var6, par4)).getBlock()) == OreSpawnMain.MyTomatoPlant1 || bid == OreSpawnMain.MyTomatoPlant2 || bid == OreSpawnMain.MyTomatoPlant3 || bid == OreSpawnMain.MyTomatoPlant4); ++var6) {
                ++Height;
                if (bid != OreSpawnMain.MyTomatoPlant3 && bid != OreSpawnMain.MyTomatoPlant4) continue;
                dontGrow = true;
            }
            if (dontGrow) {
                this.myMaxHeight = Height;
            }
            if (var7 >= 5 - this.myMaxHeight / 3) {
                if (Height < this.myMaxHeight) {
                    worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3 + 1, par4), OreSpawnMain.MyTomatoPlant1.getStateFromMeta(this.myMaxHeight << 8), 2);
                    worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4), OreSpawnMain.MyTomatoPlant2.getStateFromMeta(this.myMaxHeight << 8), 2);
                } else {
                    for (int i = 0; i < this.myMaxHeight; ++i) {
                        bid = worldIn.getBlockState(new BlockPos(par2, par3 - i, par4)).getBlock();
                        if (bid == OreSpawnMain.MyTomatoPlant2) {
                            worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - i, par4), OreSpawnMain.MyTomatoPlant3.getStateFromMeta(this.myMaxHeight << 8), 2);
                            continue;
                        }
                        if (bid != OreSpawnMain.MyTomatoPlant3) continue;
                        worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - i, par4), OreSpawnMain.MyTomatoPlant4.getStateFromMeta(this.myMaxHeight << 8), 2);
                    }
                    bid = worldIn.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
                    worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4), bid.getStateFromMeta(this.myMaxHeight << 8), 2);
                }
            } else {
                bid = worldIn.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
                worldIn.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4), bid.getStateFromMeta(this.myMaxHeight << 8 | var7 + 1), 2);
            }
        }
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return OreSpawnMain.MyTomato;
    }

    public int quantityDropped(Random par1Random) {
        if (this == OreSpawnMain.MyTomatoPlant4) {
            return 2 + par1Random.nextInt(4);
        }
        return 0;
    }

    public Item itemPicked(World worldIn, int par2, int par3, int par4) {
        return OreSpawnMain.MyTomato;
    }

    protected Item getSeedItem() {
        return OreSpawnMain.MyTomato;
    }

    protected Item getCropItem() {
        return OreSpawnMain.MyTomato;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

