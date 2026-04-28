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
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class InstantShelter
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public InstantShelter(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10) {
        int deltax = 0;
        int deltaz = 0;
        boolean bid = false;
        int dirx = 0;
        int dirz = 0;
        int stuffdir = 0;
        int length = 3;
        int width = 3;
        int height = 3;
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
            int k;
            int j;
            int i;
            int x = cposx;
            int y = pposy - 1;
            int z = cposz;
            if (x - pposx < 0) {
                deltax = -1;
                stuffdir = 3;
            }
            if (x - pposx > 0) {
                deltax = 1;
                stuffdir = 2;
            }
            if (z - pposz < 0) {
                deltaz = -1;
                stuffdir = 5;
            }
            if (z - pposz > 0) {
                deltaz = 1;
                stuffdir = 4;
            }
            if (deltax == 0 && deltaz == 0) {
                return false;
            }
            if (deltax != 0 && deltaz != 0) {
                return false;
            }
            x = pposx;
            z = pposz;
            Player.world.playSoundAtEntity((Entity)Player, "random.explode", 1.0f, 1.5f);
            if (world.isRemote) {
                return true;
            }
            for (i = -width; i <= width; ++i) {
                for (j = -length; j <= length; ++j) {
                    for (k = 0; k <= height + 1; ++k) {
                        if (k == height + 1) {
                            world.setBlockState(x + i, y + k, z + j, Blocks.PLANKS);
                            continue;
                        }
                        if (k == 0) {
                            world.setBlockState(x + i, y + k, z + j, Blocks.COBBLESTONE);
                            continue;
                        }
                        if (i == width || j == length || i == -width || j == -length) {
                            if (k == height) {
                                world.setBlockState(x + i, y + k, z + j, Blocks.GLASS);
                                continue;
                            }
                            if ((k == 1 || k == 2) && i == deltax * width && j == deltaz * length) {
                                world.setBlockState(x + i, y + k, z + j, Blocks.AIR);
                                continue;
                            }
                            world.setBlockState(x + i, y + k, z + j, Blocks.PLANKS);
                            continue;
                        }
                        world.setBlockState(x + i, y + k, z + j, Blocks.AIR);
                    }
                }
            }
            i = 2;
            k = 1;
            j = length - 1;
            world.setBlockState(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax, Blocks.FURNACE);
            world// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //.setBlockMetadataWithNotify(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax, stuffdir, 3);
            i = 1;
            world.setBlockState(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax, Blocks.CRAFTING_TABLE);
            i = 0;
            world.setBlockState(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax, (Block)Blocks.CHEST);
            world// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //.setBlockMetadataWithNotify(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax, stuffdir, 3);
            TileEntityChest chest = (TileEntityChest)world.getTileEntity(new net.minecraft.util.math.BlockPos(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax));
            if (chest != null) {
                chest.setInventorySlotContents(0, new ItemStack(Items.COMPASS));
                chest.setInventorySlotContents(1, new ItemStack((Item)Items.MAP));
                chest.setInventorySlotContents(2, new ItemStack(Items.PORKCHOP, 8));
                chest.setInventorySlotContents(3, new ItemStack(Blocks.TORCH, 32));
                chest.setInventorySlotContents(4, new ItemStack(Items.COAL, 16));
                chest.setInventorySlotContents(5, new ItemStack(Items.BED));
                chest.setInventorySlotContents(6, new ItemStack(Items.BED));
                chest.setInventorySlotContents(7, new ItemStack(Items.WOODEN_DOOR));
                chest.setInventorySlotContents(8, new ItemStack(Items.IRON_PICKAXE));
                chest.setInventorySlotContents(9, new ItemStack(Items.IRON_SWORD));
                chest.setInventorySlotContents(10, new ItemStack(Items.IRON_AXE));
                chest.setInventorySlotContents(11, new ItemStack(Items.BUCKET));
                chest.setInventorySlotContents(12, new ItemStack(OreSpawnMain.MyOreSaltBlock, 4));
                chest.setInventorySlotContents(13, new ItemStack((Block)Blocks.CHEST));
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

