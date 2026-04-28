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
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemHoe
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
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.SideOnly;

public class UltimateHoe
extends ItemHoe {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public UltimateHoe(int par1, Item.ToolMaterial par2) {
        super(par2);
        this.maxStackSize = 1;
        this.setMaxDurability(3000);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.EFFICIENCY, 2);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.EFFICIENCY, 2);
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        this.onUsingTick(stack, null, 0);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        Block i1 = par3World.getBlock(par4, par5, par6);
        boolean air = par3World.isAirBlock(new net.minecraft.util.math.BlockPos(par4, par5 + 1, par6));
        if (par7 != 0 && air && (i1 == Blocks.GRASS || i1 == Blocks.DIRT)) {
            Block block = Blocks.FARMLAND;
            par3World.playSoundEffect((double)((float)par4 + 0.5f), (double)((float)par5 + 0.5f), (double)((float)par6 + 0.5f), block.stepSound.getDigResourcePath(), (block.stepSound.getVolume() + 1.0f) / 2.0f, block.stepSound.getFrequency() * 0.8f);
            if (par3World.isRemote) {
                return true;
            }
            for (int i = -1; i <= 1; ++i) {
                for (int k = -1; k <= 1; ++k) {
                    for (int j = -1; j <= 1; ++j) {
                        i1 = par3World.getBlock(par4 + i, par5 + j, par6 + k);
                        air = par3World.isAirBlock(new net.minecraft.util.math.BlockPos(par4 + i, par5 + j + 1, par6 + k));
                        if (!air || i1 != Blocks.GRASS && i1 != Blocks.DIRT) continue;
                        par3World.setBlock(par4 + i, par5 + j, par6 + k, block, 7, 2);
                    }
                }
            }
            par1ItemStack.damageItem(1, (net.minecraft.entity.EntityLivingBase)par2EntityPlayer);
            return true;
        }
        return false;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

