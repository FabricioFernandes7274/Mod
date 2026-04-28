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
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Bertha
extends ItemSword {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public Bertha(int par1, Item.ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        this.maxStackSize = 1;
        this.setMaxDurability(9000);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        if (this == OreSpawnMain.MyRoyal) {
            par1ItemStack.addEnchantment(Enchantments.UNBREAKING, 5);
        } else if (this != OreSpawnMain.MyHammy) {
            par1ItemStack.addEnchantment(Enchantments.KNOCKBACK, 5);
            par1ItemStack.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1);
            par1ItemStack.addEnchantment(Enchantments.FIRE_ASPECT, 1);
        }
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, (ItemStack)stack);
        if (lvl == 0) {
            lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, (ItemStack)stack);
        }
        if (lvl <= 0) {
            if (this == OreSpawnMain.MyRoyal) {
                stack.addEnchantment(Enchantments.UNBREAKING, 5);
            } else if (this != OreSpawnMain.MyHammy) {
                stack.addEnchantment(Enchantments.KNOCKBACK, 5);
                stack.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1);
                stack.addEnchantment(Enchantments.FIRE_ASPECT, 1);
            }
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        this.onUsingTick(stack, null, 0);
    }

    public boolean onLeftClickEntity(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, Entity entity) {
        if (entity != null && OreSpawnMain.big_bertha_pvp == 0) {
            EntityTameable t;
            if (entity instanceof net.minecraft.entity.player.EntityPlayer || entity instanceof Girlfriend || entity instanceof Boyfriend) {
                return true;
            }
            if (entity instanceof EntityTameable && (t = (EntityTameable)entity).isTamed()) {
                return true;
            }
        }
        return false;
    }

    public boolean onEntitySwing(net.minecraft.entity.EntityLivingBase entityLiving, ItemStack stack) {
        if (entityLiving != null && entityLiving instanceof net.minecraft.entity.player.EntityPlayer && !entityLiving.world.isRemote) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)entityLiving;
            double xzoff = 2.0;
            double yoff = 1.55;
            BerthaHit lb = new BerthaHit(p.world, (net.minecraft.entity.EntityLivingBase)p);
            lb.setLocationAndAngles(p.posX - xzoff * Math.sin(Math.toRadians(p.rotationYawHead)), p.posY + yoff, p.posZ + xzoff * Math.cos(Math.toRadians(p.rotationYawHead)), p.rotationYawHead, p.rotationPitch);
            lb.motionX *= 2.0;
            lb.motionY *= 2.0;
            lb.motionZ *= 2.0;
            if (this == OreSpawnMain.MyRoyal) {
                lb.setHitType(2);
            }
            if (this == OreSpawnMain.MyHammy) {
                lb.setHitType(3);
            }
            p.world.spawnEntity((Entity)lb);
            stack.damageItem(1, (net.minecraft.entity.EntityLivingBase)p);
        }
        return false;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
        par1ItemStack.damageItem(1, (net.minecraft.entity.EntityLivingBase)par3EntityLiving);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 9000;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

