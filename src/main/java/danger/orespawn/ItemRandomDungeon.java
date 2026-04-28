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
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.OreSpawnRand;

    public ItemRandomDungeon(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.FORTUNE, 2);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.FORTUNE, 2);
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        this.onUsingTick(stack, null, 0);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer par2EntityPlayer, World world, int clickedX, int clickedY, int clickedZ, int par7, float par8, float par9, float par10) {
        Block var1 = world.getBlockState(new net.minecraft.util.math.BlockPos(clickedX, clickedY, clickedZ)).getBlock();
        if (var1 != Blocks.STONE && var1 != Blocks.COBBLESTONE && var1 != Blocks.GRASS && var1 != Blocks.DIRT) {
            return false;
        }
        if (clickedY < 40) {
            return false;
        }
        if (!world.isRemote) {
            world.setBlockState(clickedX, clickedY + 1, clickedZ, OreSpawnMain.MyDungeonSpawnerBlock, 0, 2);
        }
        if (!par2EntityPlayer.isCreative()) {
            par1ItemStack.setCount(par1ItemStack.getCount() - 1);
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

