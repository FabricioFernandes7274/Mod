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
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemPickaxe
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.SideOnly;

public class EmeraldPickaxe
extends ItemPickaxe {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int weaponDamage = 10;

    public EmeraldPickaxe(int par1, Item.ToolMaterial par2) {
        super(par2);
        this.maxStackSize = 1;
        this.setMaxDurability(1300);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        this.onUsingTick(stack, null, 0);
    }

    public int getDamageVsEntity(Entity par1Entity) {
        return this.weaponDamage;
    }

    public int getDamageVsEntity() {
        return this.weaponDamage;
    }

    public String getMaterialName() {
        return "Emerald";
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

