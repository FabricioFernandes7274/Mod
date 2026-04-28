/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.SideOnly;

public class ItemSunFish
extends ItemFood {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemSunFish(int par1, int par2, float par3, boolean par4) {
        super(par2, par3, par4);
        this.setAlwaysEdible();
    }

    public void onFoodEaten(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        if (!par2World.isRemote && this == OreSpawnMain.MySunFish) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE.getId(), 6000, 0));
        }
        if (!par2World.isRemote && this == OreSpawnMain.MyButterCandy) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.SPEED.getId(), 2000, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST.getId(), 2000, 0));
        }
        if (!par2World.isRemote && this == OreSpawnMain.MyBacon) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION.getId(), 2000, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.STRENGTH.getId(), 2000, 0));
        }
        if (!par2World.isRemote && this == OreSpawnMain.MyCrystalApple) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION.getId(), 3000, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.STRENGTH.getId(), 3000, 0));
        }
        if (!par2World.isRemote && this == OreSpawnMain.MyLove) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION.getId(), 6000, 3));
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.STRENGTH.getId(), 6000, 2));
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE.getId(), 6000, 2));
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE.getId(), 6000, 1));
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.SPEED.getId(), 5000, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST.getId(), 5000, 0));
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

