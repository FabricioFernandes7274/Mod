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
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMinersDream
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemMinersDream(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10) {
        int deltax = 0;
        int deltaz = 0;
        int dirx = 0;
        int dirz = 0;
        int height = 5;
        int width = 5;
        int length = 64;
        int torches = 5;
        int solid_count = 0;
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
            Block bid;
            int k;
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
            Player.world.playSoundAtEntity((Entity)Player, "random.explode", 1.0f, 1.5f);
            if (world.isRemote) {
                return true;
            }
            for (int i = 0; i < height; ++i) {
                for (k = 0; k < length; ++k) {
                    int j;
                    solid_count = 0;
                    for (j = -width; j <= width; ++j) {
                        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax)).getBlock();
                        if (bid == Blocks.STONE || bid == Blocks.DIRT || bid == Blocks.GRAVEL || bid == Blocks.FLOWING_WATER || bid == Blocks.WATER || bid == Blocks.FLOWING_LAVA || bid == Blocks.LAVA || bid == Blocks.NETHERRACK || bid == Blocks.END_STONE || bid == OreSpawnMain.CrystalStone) {
                            world.setBlockState(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax, Blocks.AIR, 0, 2);
                        }
                        if (i != height - 1) continue;
                        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax)).getBlock();
                        if (bid != Blocks.AIR) {
                            ++solid_count;
                        }
                        if (bid != Blocks.AIR && bid != Blocks.GRAVEL && bid != Blocks.SAND && bid != Blocks.FLOWING_WATER && bid != Blocks.WATER && bid != Blocks.FLOWING_LAVA && bid != Blocks.LAVA) continue;
                        if (world.provider.getDimension() == OreSpawnMain.DimensionID5) {
                            world.setBlockState(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax, OreSpawnMain.CrystalStone, 0, 2);
                            continue;
                        }
                        world.setBlockState(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax, Blocks.COBBLESTONE, 0, 2);
                    }
                    if (i != height - 1 || solid_count != 0) continue;
                    for (j = -width; j <= width; ++j) {
                        world.setBlockState(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax, Blocks.AIR, 0, 2);
                    }
                }
            }
            for (k = 0; k < length; k += torches) {
                bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax, y - 1, z + k * deltaz)).getBlock();
                if ((bid == Blocks.STONE || bid == Blocks.DIRT || bid == Blocks.GRAVEL || bid == Blocks.NETHERRACK || bid == Blocks.END_STONE || bid == Blocks.BEDROCK) && world.isAirBlock(x + k * deltax, y, z + k * deltaz)) {
                    world.setBlockState(x + k * deltax, y, z + k * deltaz, OreSpawnMain.ExtremeTorch, 0, 2);
                }
                if (bid != OreSpawnMain.CrystalStone || !world.isAirBlock(x + k * deltax, y, z + k * deltaz)) continue;
                world.setBlockState(x + k * deltax, y, z + k * deltaz, OreSpawnMain.CrystalTorch, 0, 2);
            }
            if (!Player.capabilities.isCreativeMode) {
                --par1ItemStack.stackSize;
            }
            return true;
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5));
    }
}

