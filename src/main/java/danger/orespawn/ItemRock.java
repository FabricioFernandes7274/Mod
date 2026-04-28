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
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.SideOnly;

public class ItemRock
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemRock(int i) {
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        if (!par3EntityPlayer.isCreative()) {
            par1ItemStack.setCount(par1ItemStack.getCount() - 1);
        }
        par2World.playSoundAtEntity((Entity)par3EntityPlayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
        if (!par2World.isRemote) {
            if (this == OreSpawnMain.MySmallRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 1));
            }
            if (this == OreSpawnMain.MyRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 2));
            }
            if (this == OreSpawnMain.MyRedRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 3));
            }
            if (this == OreSpawnMain.MyGreenRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 4));
            }
            if (this == OreSpawnMain.MyBlueRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 5));
            }
            if (this == OreSpawnMain.MyPurpleRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 6));
            }
            if (this == OreSpawnMain.MySpikeyRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 7));
            }
            if (this == OreSpawnMain.MyTNTRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 8));
            }
            if (this == OreSpawnMain.MyCrystalRedRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 9));
            }
            if (this == OreSpawnMain.MyCrystalGreenRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 10));
            }
            if (this == OreSpawnMain.MyCrystalBlueRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 11));
            }
            if (this == OreSpawnMain.MyCrystalTNTRock) {
                par2World.spawnEntity((Entity)new EntityThrownRock(par2World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, 12));
            }
        }
        return par1ItemStack;
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if (!world.isRemote) {
            Entity e;
            if (x < 0) {
                ++x;
            }
            if (z < 0) {
                ++z;
            }
            if ((e = this.spawnCreature(world, "Rock", x, (double)y + 1.01, z)) != null) {
                RockBase r = (RockBase)e;
                if (this == OreSpawnMain.MySmallRock) {
                    r.placeRock(1);
                }
                if (this == OreSpawnMain.MyRock) {
                    r.placeRock(2);
                }
                if (this == OreSpawnMain.MyRedRock) {
                    r.placeRock(3);
                }
                if (this == OreSpawnMain.MyGreenRock) {
                    r.placeRock(4);
                }
                if (this == OreSpawnMain.MyBlueRock) {
                    r.placeRock(5);
                }
                if (this == OreSpawnMain.MyPurpleRock) {
                    r.placeRock(6);
                }
                if (this == OreSpawnMain.MySpikeyRock) {
                    r.placeRock(7);
                }
                if (this == OreSpawnMain.MyTNTRock) {
                    r.placeRock(8);
                }
                if (this == OreSpawnMain.MyCrystalRedRock) {
                    r.placeRock(9);
                }
                if (this == OreSpawnMain.MyCrystalGreenRock) {
                    r.placeRock(10);
                }
                if (this == OreSpawnMain.MyCrystalBlueRock) {
                    r.placeRock(11);
                }
                if (this == OreSpawnMain.MyCrystalTNTRock) {
                    r.placeRock(12);
                }
            }
        }
        if (!par2EntityPlayer.isCreative()) {
            par1ItemStack.setCount(par1ItemStack.getCount() - 1);
        }
        return true;
    }

    private Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            if (par2 > 0.0) {
                par2 += 0.5;
            }
            if (par2 < 0.0) {
                par2 -= 0.5;
            }
            if (par6 > 0.0) {
                par6 += 0.5;
            }
            if (par6 < 0.0) {
                par6 -= 0.5;
            }
            var8.setLocationAndAngles(par2, par4 + 0.01, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

