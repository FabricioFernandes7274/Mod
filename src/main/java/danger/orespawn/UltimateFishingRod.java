/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UltimateFishingRod
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public UltimateFishingRod(int par1) {
        this.setMaxDurability(3000);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public boolean isFull3D() {
        return true;
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.UNBREAKING, 2);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.UNBREAKING, 2);
        }
    }

    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.fishEntity != null) {
            int var4 = par3EntityPlayer.fishEntity.handleHookRetraction();
            par1ItemStack.damageItem(var4, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer);
            par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
        } else {
            par2World.playSound(null, (Entity)par3EntityPlayer.posX, (Entity)par3EntityPlayer.posY, (Entity)par3EntityPlayer.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.HOSTILE, 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if (!par2World.isRemote) {
                par2World.spawnEntity((Entity)new UltimateFishHook(par2World, par3EntityPlayer));
            }
            par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
        }
        return par1ItemStack;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

