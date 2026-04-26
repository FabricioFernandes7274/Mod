/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
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

public class ZooCage
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int cage_size = 2;

    public ZooCage(int i, int j) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.cage_size = j;
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10) {
        int length;
        boolean bid = false;
        int dirx = 0;
        int dirz = 0;
        int width = length = this.cage_size / 2 + 1;
        int height = length;
        if (cposx < 0) {
            dirx = -1;
        }
        if (cposz < 0) {
            dirz = -1;
        }
        int x = (int)(Player.posX + 0.99 * (double)dirx);
        int y = (int)Player.posY - 1;
        int z = (int)(Player.posZ + 0.99 * (double)dirz);
        Player.world.playSoundAtEntity((Entity)Player, "random.explode", 1.0f, 1.5f);
        if (world.isRemote) {
            return true;
        }
        for (int i = -width; i <= width; ++i) {
            for (int j = -length; j <= length; ++j) {
                for (int k = 0; k <= height + 1; ++k) {
                    if (k == height + 1) {
                        world.setBlockState(x + i, y + k, z + j, Blocks.QUARTZ_BLOCK);
                        continue;
                    }
                    if (k == 0) {
                        world.setBlockState(x + i, y + k, z + j, Blocks.QUARTZ_BLOCK);
                        continue;
                    }
                    if (i == width || j == length || i == -width || j == -length) {
                        world.setBlockState(x + i, y + k, z + j, Blocks.GLASS);
                        continue;
                    }
                    world.setBlockState(x + i, y + k, z + j, Blocks.AIR);
                }
            }
        }
        if (!Player.capabilities.isCreativeMode) {
            --par1ItemStack.stackSize;
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5));
    }
}

