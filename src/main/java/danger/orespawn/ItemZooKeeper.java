/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemZooKeeper
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemZooKeeper(int i) {
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setMaxDurability(1);
    }

    public boolean onLeftClickEntity(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, Entity entity) {
        for (int var3 = 0; var3 < 8; ++var3) {
            float f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            float f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
            float f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            player.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
            f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
            f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            player.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
            f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
            f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            player.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
        }
        player.world.playSound(null, player.posX, player.posY, player.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 0.5f, 1.5f);
        if (entity == null || !(entity instanceof EntityLiving)) {
            return false;
        }
        EntityLiving e = (EntityLiving)entity;
        e.enablePersistence();
        stack.damageItem(2, (net.minecraft.entity.EntityLivingBase)player);
        if (stack.getCount() <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

