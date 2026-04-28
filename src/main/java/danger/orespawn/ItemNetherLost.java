/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
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
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNetherLost
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemNetherLost(int par1) {
        this.maxStackSize = 1;
        this.setMaxDurability(3000);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.SHARPNESS, 2);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.SHARPNESS, 2);
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        Block i;
        Item it;
        ItemStack is;
        net.minecraft.entity.EntityLivingBase e = null;
        net.minecraft.entity.player.EntityPlayer p = null;
        this.onUsingTick(stack, null, 0);
        if (par2World == null) {
            return;
        }
        if (par3Entity != null && par3Entity instanceof net.minecraft.entity.EntityLivingBase && (e = (net.minecraft.entity.EntityLivingBase)par3Entity) instanceof net.minecraft.entity.player.EntityPlayer && (is = (p = (net.minecraft.entity.player.EntityPlayer)e).getHeldItemMainhand()) != null && (it = is.getItem()) != null && it instanceof ItemNetherLost && par2World.provider.getDimension() == -1 && (i = par2World.getBlockState(new BlockPos((int)p.posX, (int)p.posY - 1, (int)).getBlock()p.posZ)) == Blocks.NETHERRACK) {
            par2World.setBlock((int)p.posX, (int)p.posY - 1, (int)p.posZ, Blocks.QUARTZ_BLOCK);
        }
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 3000;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

