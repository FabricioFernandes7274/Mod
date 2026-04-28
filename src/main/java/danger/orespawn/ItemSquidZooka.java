/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SideOnly;

public class ItemSquidZooka
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemSquidZooka(int i) {
        this.maxStackSize = 1;
        this.setMaxDurability(100);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.getMaxDurability() - par1ItemStack.getMetadata() <= 1) {
            return par1ItemStack;
        }
        par2World.playSoundAtEntity((Entity)par3EntityPlayer, "random.explode", 0.5f, 0.5f);
        if (!par2World.isRemote) {
            double xzoff = 2.5;
            double yoff = 1.65;
            Entity e = ItemSquidZooka.spawnCreature(par2World, "Attack Squid", par3EntityPlayer.posX - xzoff * Math.sin(Math.toRadians(par3EntityPlayer.rotationYawHead + 15.0f)), par3EntityPlayer.posY + yoff, par3EntityPlayer.posZ + xzoff * Math.cos(Math.toRadians(par3EntityPlayer.rotationYawHead + 15.0f)));
            if (e instanceof AttackSquid) {
                AttackSquid a = (AttackSquid)e;
                a.setWasShot();
            }
            float f = 3.6f;
            e.motionX = -net.minecraft.util.math.MathHelper.sin((float)(par3EntityPlayer.rotationYaw / 180.0f * (float)Math.PI)) * net.minecraft.util.math.MathHelper.cos((float)(par3EntityPlayer.rotationPitch / 180.0f * (float)Math.PI)) * f;
            e.motionZ = net.minecraft.util.math.MathHelper.cos((float)(par3EntityPlayer.rotationYaw / 180.0f * (float)Math.PI)) * net.minecraft.util.math.MathHelper.cos((float)(par3EntityPlayer.rotationPitch / 180.0f * (float)Math.PI)) * f;
            e.motionY = -net.minecraft.util.math.MathHelper.sin((float)(par3EntityPlayer.rotationPitch / 180.0f * (float)Math.PI)) * f;
            e.motionX += (double)(par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
            e.motionY += (double)(par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
            e.motionZ += (double)(par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
        }
        par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
        par3EntityPlayer.addVelocity(Math.cos(Math.toRadians(par3EntityPlayer.rotationYawHead - 90.0f)) * 0.45, 0.1, Math.sin(Math.toRadians(par3EntityPlayer.rotationYawHead - 90.0f)) * 0.45);
        par1ItemStack.damageItem(1, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer);
        return par1ItemStack;
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
        }
        return var8;
    }

    public String getMaterialName() {
        return "Unknown";
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

