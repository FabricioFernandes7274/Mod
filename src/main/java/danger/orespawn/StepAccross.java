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
import net.minecraft.world.SideOnly;

public class StepAccross
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public StepAccross(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10) {
        Block bid;
        int deltax = 0;
        int deltaz = 0;
        int length = 33;
        int x = cposx;
        int y = cposy + 1;
        int z = cposz;
        float f = Player.rotationYawHead;
        f += 22.5f;
        f %= 360.0f;
        switch ((int)(f /= 45.0f)) {
            case 0: {
                deltax = 0;
                deltaz = 1;
                break;
            }
            case 1: {
                deltax = -1;
                deltaz = 1;
                break;
            }
            case 2: {
                deltax = -1;
                deltaz = 0;
                break;
            }
            case 3: {
                deltax = -1;
                deltaz = -1;
                break;
            }
            case 4: {
                deltax = 0;
                deltaz = -1;
                break;
            }
            case 5: {
                deltax = 1;
                deltaz = -1;
                break;
            }
            case 6: {
                deltax = 1;
                deltaz = 0;
                break;
            }
            case 7: {
                deltax = 1;
                deltaz = 1;
                break;
            }
        }
        if (deltax == 0 && deltaz == 0) {
            return false;
        }
        Player.world.playSoundAtEntity((Entity)Player, "random.explode", 1.0f, 1.5f);
        if (world.isRemote) {
            for (int var3 = 0; var3 < 6; ++var3) {
                world.spawnParticle("largesmoke", (double)((float)x + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)y + world.rand.nextFloat()), (double)((float)z + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                world.spawnParticle("largeexplode", (double)((float)x + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)y + world.rand.nextFloat()), (double)((float)z + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                world.spawnParticle("reddust", (double)((float)x + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)y + world.rand.nextFloat()), (double)((float)z + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
            }
            return true;
        }
        for (int k = 1; k < length && (bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax, y - 1, z + k * deltaz)).getBlock()) == Blocks.AIR; ++k) {
            world.setBlockState(x + k * deltax, y - 1, z + k * deltaz, Blocks.COBBLESTONE, 0, 2);
            if ((k - 1) % 8 != 0 || (bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax, y, z + k * deltaz)).getBlock()) != Blocks.AIR) continue;
            world.setBlockState(x + k * deltax, y, z + k * deltaz, OreSpawnMain.ExtremeTorch, 0, 2);
        }
        if (!Player.isCreative()) {
            par1ItemStack.setCount(par1ItemStack.getCount() - 1);
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

