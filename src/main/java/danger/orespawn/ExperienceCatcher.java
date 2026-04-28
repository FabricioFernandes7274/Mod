/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExperienceCatcher
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ExperienceCatcher(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer par2EntityPlayer, World world, net.minecraft.util.math.BlockPos pos, net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing facing, float par8, float par9, float par10) {
        par2EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
        System.out.printf("x, y,z, 7,8,9,10 == %d, %d, %d - %d, %f, %f, %f
", x, y, z, par7, Float.valueOf(par8), Float.valueOf(par9), Float.valueOf(par10));
        if (!par2EntityPlayer.world.isRemote) {
            AxisAlignedBB bb = new AxisAlignedBB((double)((double)x - 0.5 + (double)par8), (double)y, (double)((double)z - 0.5 + (double)par10), (double)((double)x + 0.5 + (double)par8), (double)((double)y + 2.0), (double)((double)z + 0.5 + (double)par10));
            List var5 = world.getEntitiesWithinAABB(EntityXPOrb.class, bb);
            for (Entity var3 : var5) {
                EntityXPOrb ex;
                if (!(var3 instanceof EntityXPOrb) || (ex = (EntityXPOrb)var3).xpValue < 3 || world.rand.nextInt(5) == 1) continue;
                var3.setDead();
                EntityItem var4 = null;
                ItemStack is = new ItemStack(Items.EXPERIENCE_BOTTLE, 1, 0);
                var4 = new EntityItem(par2EntityPlayer.world, (double)(par8 + (float)x), (double)y + 1.0, (double)(par10 + (float)z), is);
                if (var4 != null) {
                    par2EntityPlayer.world.spawnEntity((Entity)var4);
                }
                if ((var4 = new EntityItem(par2EntityPlayer.world, (double)(par8 + (float)x), (double)y + 1.0, (double)(par10 + (float)z), is = new ItemStack(Items.STRING, 1, 0))) != null) {
                    par2EntityPlayer.world.spawnEntity((Entity)var4);
                }
                if ((var4 = new EntityItem(par2EntityPlayer.world, (double)(par8 + (float)x), (double)y + 1.0, (double)(par10 + (float)z), is = new ItemStack(Items.STICK, 1, 0))) != null) {
                    par2EntityPlayer.world.spawnEntity((Entity)var4);
                }
                if (!par2EntityPlayer.isCreative()) {
                    par1ItemStack.setCount(par1ItemStack.getCount() - 1);
                }
                return true;
            }
            EntityItem var4 = null;
            ItemStack is = new ItemStack(OreSpawnMain.MyExperienceCatcher, 1, 0);
            var4 = new EntityItem(par2EntityPlayer.world, (double)(par8 + (float)x), (double)y + 1.0, (double)(par10 + (float)z), is);
            if (var4 != null) {
                par2EntityPlayer.world.spawnEntity((Entity)var4);
            }
            par1ItemStack.setCount(par1ItemStack.getCount() - 1);
        }
        return true;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
        return par1ItemStack;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

