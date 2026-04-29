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
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SkateBow
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public SkateBow(int par1) {
        this.maxStackSize = 1;
        this.setMaxDurability(300);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
    }

    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer, int par4) {
        boolean flag;
        int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
        boolean bl = flag = par3EntityPlayer.isCreative() || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, (ItemStack)par1ItemStack) > 0;
        if (flag || par3EntityPlayer.inventory.hasItem(OreSpawnMain.MyIrukandjiArrow)) {
            int var10;
            float f = (float)var6 / 20.0f;
            if ((double)(f = (f * f + f * 2.0f) / 3.0f) < 0.1) {
                return;
            }
            if (f > 1.75f) {
                f = 1.75f;
            }
            IrukandjiArrow var8 = new IrukandjiArrow(par2World, par3EntityPlayer, f);
            if (par2World.rand.nextInt(20) == 1) {
                var8.setIsCritical(true);
            }
            if ((var10 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, (ItemStack)par1ItemStack)) > 0) {
                var8.setKnockbackStrength(var10);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, (ItemStack)par1ItemStack) > 0) {
                var8.setFire(100);
            }
            par1ItemStack.damageItem(1, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer);
            par2World.playSound(null, (Entity)par3EntityPlayer.posX, (Entity)par3EntityPlayer.posY, (Entity)par3EntityPlayer.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.HOSTILE, 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + 0.5f);
            if (!flag) {
                par3EntityPlayer.inventory.consumeInventoryItem(OreSpawnMain.MyIrukandjiArrow);
            }
            if (!par2World.isRemote) {
                par2World.spawnEntity((Entity)var8);
            }
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

