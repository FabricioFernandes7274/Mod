/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemExperienceTreeSeed
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemExperienceTreeSeed(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer par2net.minecraft.entity.player.EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if (!world.isRemote) {
            Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock();
            if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
                return false;
            }
            world.setBlockState(x, y + 1, z, OreSpawnMain.MyExperiencePlant, 0, 2);
        } else {
            for (int j1 = 0; j1 < 10; ++j1) {
                world.spawnParticle("happyVillager", (double)((float)x + world.rand.nextFloat()), (double)y + 1.0 + (double)world.rand.nextFloat(), (double)((float)z + world.rand.nextFloat()), 0.0, 0.0, 0.0);
            }
        }
        if (!par2net.minecraft.entity.player.EntityPlayer.capabilities.isCreativeMode) {
            --par1ItemStack.stackSize;
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5));
    }
}

