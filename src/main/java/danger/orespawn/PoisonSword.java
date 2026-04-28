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
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PoisonSword
extends ItemSword {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int weaponDamage;
    private final Item.ToolMaterial toolMaterial;

    public PoisonSword(int par1, Item.ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        this.toolMaterial = par2EnumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDurability(1300);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.SHARPNESS, 1);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.SHARPNESS, 1);
        }
    }

    public String getMaterialName() {
        return "Emerald";
    }

    public boolean hitEntity(ItemStack par1ItemStack, net.minecraft.entity.EntityLivingBase par2EntityLiving, net.minecraft.entity.EntityLivingBase par3EntityLiving) {
        int var2 = 5;
        if (par2EntityLiving != null) {
            var2 = 10 + par2EntityLiving.world.rand.nextInt(10);
            par2EntityLiving.addPotionEffect(new PotionEffect(MobEffects.POISON, var2 * 20, 0));
            var2 = 10 + par2EntityLiving.world.rand.nextInt(10);
            par2EntityLiving.addPotionEffect(new PotionEffect(MobEffects.WITHER, var2 * 20, 0));
            var2 = 10 + par2EntityLiving.world.rand.nextInt(10);
            par2EntityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, var2 * 20, 0));
        }
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 3000;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

