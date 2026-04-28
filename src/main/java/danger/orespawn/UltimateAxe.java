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
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemAxe
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
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.SideOnly;

public class UltimateAxe
extends ItemAxe {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int weaponDamage = 15;

    public UltimateAxe(int par1, Item.ToolMaterial par2) {
        super(par2);
        this.maxStackSize = 1;
        this.setMaxDurability(3000);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.EFFICIENCY, 5);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.EFFICIENCY, 5);
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        this.onUsingTick(stack, null, 0);
    }

    public boolean onLeftClickEntity(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, Entity entity) {
        if (entity != null && OreSpawnMain.ultimate_sword_pvp == 0) {
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

    public int getDamageVsEntity(Entity par1Entity) {
        if (par1Entity instanceof Girlfriend) {
            return 1;
        }
        if (par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
            return 1;
        }
        return this.weaponDamage;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

