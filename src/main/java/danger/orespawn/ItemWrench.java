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
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.SideOnly;

public class ItemWrench
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemWrench(int i) {
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxDurability(100);
    }

    public boolean onLeftClickEntity(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, Entity entity) {
        if (entity != null && entity instanceof SpiderRobot && entity.getPassengers() == null) {
            EntityLiving e = (EntityLiving)entity;
            float h = e.getMaxHealth() - e.getHealth();
            e.setDead();
            this.dropItem(player.world, e, OreSpawnMain.SpiderRobotKit, 1, (int)h);
            for (int var3 = 0; var3 < 8; ++var3) {
                float f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                float f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
                float f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                player.world.spawnParticle("smoke", (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
                f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
                f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                player.world.spawnParticle("explode", (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
                f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
                f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                player.world.spawnParticle("reddust", (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
            }
            player.world.playSoundAtEntity((Entity)player, "random.explode", 0.5f, 1.5f);
        } else if (entity != null && entity instanceof AntRobot && entity.getPassengers() == null) {
            AntRobot e = (AntRobot)entity;
            if (e.getOwned() == 0) {
                if (e.getHealth() / e.getMaxHealth() > 0.5f) {
                    return false;
                }
                e.setOwned();
            }
            float h = e.getMaxHealth() - e.getHealth();
            e.setDead();
            this.dropItem(player.world, e, OreSpawnMain.AntRobotKit, 1, (int)h);
            for (int var3 = 0; var3 < 8; ++var3) {
                float f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                float f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
                float f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                player.world.spawnParticle("smoke", (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
                f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
                f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                player.world.spawnParticle("explode", (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
                f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
                f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
                player.world.spawnParticle("reddust", (double)((float)entity.posX + f1), (double)((float)entity.posY + f2), (double)((float)entity.posZ + f3), 0.0, 0.0, 0.0);
            }
            player.world.playSoundAtEntity((Entity)player, "random.explode", 0.5f, 1.5f);
        } else {
            return false;
        }
        stack.damageItem(2, (net.minecraft.entity.EntityLivingBase)player);
        if (stack.stackSize <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
        }
        return true;
    }

    private void dropItem(World world, EntityLiving e, Item index, int par1, int par2) {
        if (world.isRemote) {
            return;
        }
        ItemStack is = new ItemStack(index, par1, 0);
        is.setMetadata(par2);
        EntityItem var3 = new EntityItem(world, e.posX, e.posY + 1.0, e.posZ, is);
        world.spawnEntity((Entity)var3);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

