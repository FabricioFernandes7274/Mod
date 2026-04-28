/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InstantGarden
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public InstantGarden(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10) {
        int deltax = 0;
        int deltaz = 0;
        boolean bid = false;
        int dirx = 0;
        int dirz = 0;
        int height = 10;
        int width = 7;
        int length = 18;
        if (cposx < 0) {
            dirx = -1;
        }
        if (cposz < 0) {
            dirz = -1;
        }
        int pposx = (int)(Player.posX + 0.99 * (double)dirx);
        int pposy = (int)Player.posY;
        int pposz = (int)(Player.posZ + 0.99 * (double)dirz);
        if (cposx - pposx == 0 || cposz - pposz == 0) {
            int j;
            int k;
            int i;
            int x = cposx;
            int y = pposy;
            int z = cposz;
            if (x - pposx < 0) {
                deltax = -1;
            }
            if (x - pposx > 0) {
                deltax = 1;
            }
            if (z - pposz < 0) {
                deltaz = -1;
            }
            if (z - pposz > 0) {
                deltaz = 1;
            }
            if (deltax == 0 && deltaz == 0) {
                return false;
            }
            if (deltax != 0 && deltaz != 0) {
                return false;
            }
            Player.world.playSound(null, Player.posX, Player.posY, Player.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.5f);
            if (world.isRemote) {
                return true;
            }
            for (i = 0; i < height; ++i) {
                for (k = 0; k < length; ++k) {
                    for (j = -width; j <= width; ++j) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax), Blocks.AIR.getStateFromMeta(0), 2);
                        if (i != 0) continue;
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i - 1, z + k * deltaz + j * deltax), (Block)Blocks.GRASS.getStateFromMeta(0), 2);
                    }
                }
            }
            for (k = 1; k < length - 1; ++k) {
                i = 0;
                for (j = -width; j <= width; ++j) {
                    if (i == 1) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), OreSpawnMain.MyRadishPlant.getStateFromMeta(0), 2);
                    }
                    if (i == 2) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), OreSpawnMain.MyLettucePlant1.getStateFromMeta(0), 2);
                    }
                    if (i == 3) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.CARROTS.getStateFromMeta(0), 2);
                    }
                    if (i == 4) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.WATER.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 2, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getStateFromMeta(0), 2);
                    }
                    if (i == 5) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.POTATOES.getStateFromMeta(0), 2);
                    }
                    if (i == 6) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.WHEAT.getStateFromMeta(0), 2);
                    }
                    if (i == 7) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), OreSpawnMain.MyTomatoPlant1.getStateFromMeta(0), 2);
                    }
                    if (i == 8) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.WATER.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 2, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getStateFromMeta(0), 2);
                    }
                    if (i == 9) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), OreSpawnMain.MyCornPlant1.getStateFromMeta(0), 2);
                    }
                    if (i == 10) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), OreSpawnMain.MyStrawberryPlant.getStateFromMeta(0), 2);
                    }
                    if (i == 11) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 2, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), (Block)Blocks.SAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.REEDS.getStateFromMeta(0), 2);
                    }
                    if (i == 12) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.WATER.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 2, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getStateFromMeta(0), 2);
                    }
                    if (i == 13) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getStateFromMeta(0), 2);
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.MELON_STEM.getStateFromMeta(0), 2);
                    }
                    ++i;
                }
            }
            if (!Player.isCreative()) {
                par1ItemStack.setCount(par1ItemStack.getCount() - 1);
            }
            return true;
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

