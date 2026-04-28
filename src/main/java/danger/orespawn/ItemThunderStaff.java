/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemThunderStaff
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int ticker = 50;

    public ItemThunderStaff(int i) {
        this.maxStackSize = 1;
        this.setMaxDurability(50);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.getMaxDurability() - par1ItemStack.getMetadata() <= 1) {
            return par1ItemStack;
        }
        double xzoff = 1.0;
        double yoff = 1.55;
        ThunderBolt lb = new ThunderBolt(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer);
        lb.setLocationAndAngles(par3EntityPlayer.posX - xzoff * Math.sin(Math.toRadians(par3EntityPlayer.rotationYawHead + 45.0f)), par3EntityPlayer.posY + yoff, par3EntityPlayer.posZ + xzoff * Math.cos(Math.toRadians(par3EntityPlayer.rotationYawHead + 45.0f)), par3EntityPlayer.rotationYawHead, par3EntityPlayer.rotationPitch);
        lb.motionX *= 3.0;
        lb.motionY *= 3.0;
        lb.motionZ *= 3.0;
        par2World.spawnEntity((Entity)lb);
        par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
        par3EntityPlayer.addVelocity(Math.cos(Math.toRadians(par3EntityPlayer.rotationYawHead - 90.0f)) * 0.5, 0.15, Math.sin(Math.toRadians(par3EntityPlayer.rotationYawHead - 90.0f)) * 0.5);
        par1ItemStack.damageItem(1, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer);
        return par1ItemStack;
    }

    public void onUpdate(ItemStack par1ItemStack, World world, Entity par3Entity, int par4, boolean par5) {
        if (world.isRaining() && world.isThundering()) {
            if (this.ticker > 0) {
                --this.ticker;
            }
            if (this.ticker <= 0 && par1ItemStack.getMetadata() > 0) {
                par1ItemStack.setMetadata(par1ItemStack.getMetadata() - 1);
                this.ticker = 50;
            }
        }
    }

    public String getMaterialName() {
        return "Unknown";
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

