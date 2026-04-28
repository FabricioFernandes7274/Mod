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
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UltimateBow
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public UltimateBow(int par1) {
        this.maxStackSize = 1;
        this.setMaxDurability(1000);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.POWER, 5);
        par1ItemStack.addEnchantment(Enchantments.FLAME, 3);
        par1ItemStack.addEnchantment(Enchantments.PUNCH, 2);
        par1ItemStack.addEnchantment(Enchantments.INFINITY, 1);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.POWER, 5);
            stack.addEnchantment(Enchantments.FLAME, 3);
            stack.addEnchantment(Enchantments.PUNCH, 2);
            stack.addEnchantment(Enchantments.INFINITY, 1);
        }
    }

    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer, int par4) {
        int var10;
        UltimateArrow var8 = new UltimateArrow(par2World, par3EntityPlayer, 3.0f);
        if (par2World.rand.nextInt(4) == 1) {
            var8.setIsCritical(true);
        }
        if ((var10 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, (ItemStack)par1ItemStack)) > 0) {
            var8.setKnockbackStrength(var10);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, (ItemStack)par1ItemStack) > 0) {
            var8.setFire(100);
        }
        par1ItemStack.damageItem(1, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer);
        par2World.playSoundAtEntity((Entity)par3EntityPlayer, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + 0.5f);
        var8.setPickupDelay(2;
        if (!par2World.isRemote) {
            par2World.spawnEntity((Entity)var8);
        }
    }

    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 9000;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.BOW;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    public int getItemEnchantability() {
        return 50;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

