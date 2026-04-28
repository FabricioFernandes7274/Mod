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
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCreeperLauncher
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemCreeperLauncher(int i) {
        this.setCreativeTab(CreativeTabs.REDSTONE);
        this.setMaxDurability(1);
    }

    public boolean onLeftClickEntity(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, Entity entity) {
        if (entity != null && entity instanceof EntityCreeper) {
            for (int var3 = 0; var3 < 6; ++var3) {
                float f1 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
                float f2 = 0.25f + player.world.rand.nextFloat() * 6.0f;
                float f3 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
                player.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, (double)(f2 / 4.0f), 0.0);
                f1 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
                f2 = 0.25f + player.world.rand.nextFloat() * 6.0f;
                f3 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
                player.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, (double)(f2 / 4.0f), 0.0);
                f1 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
                f2 = 0.25f + player.world.rand.nextFloat() * 6.0f;
                f3 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
                player.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, (double)(f2 / 4.0f), 0.0);
            }
        } else {
            return false;
        }
        player.world.playSound(null, (Entity)player.posX, (Entity)player.posY, (Entity)player.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 2.0f, 1.2f);
        EntityLiving e = (EntityLiving)entity;
        e.addVelocity(0.0, 4.5, 0.0);
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

