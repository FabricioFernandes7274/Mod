/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExperienceSword
extends ItemSword {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int weaponDamage;
    private final Item.ToolMaterial toolMaterial;
    private World world = null;
    private World worldObjr = null;

    public ExperienceSword(int par1, Item.ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        this.toolMaterial = par2EnumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDurability(1400);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.SHARPNESS, 2);
        par1ItemStack.addEnchantment(Enchantments.UNBREAKING, 3);
    }

    public void onUsingTick(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.SHARPNESS, 2);
            stack.addEnchantment(Enchantments.UNBREAKING, 3);
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        net.minecraft.entity.EntityLivingBase e = null;
        ItemOreSpawnArmor ia = null;
        net.minecraft.entity.player.EntityPlayer p = null;
        this.onUsingTick(stack, null, 0);
        if (this.world == null && !par2World.isRemote) {
            this.world = par2World;
        }
        if (this.worldr == null && par2World.isRemote) {
            this.worldr = par2World;
        }
        if (par2World.rand.nextInt(60) == 1 && par3Entity != null && par3Entity instanceof net.minecraft.entity.EntityLivingBase) {
            e = (net.minecraft.entity.EntityLivingBase)par3Entity;
            if (e instanceof net.minecraft.entity.player.EntityPlayer) {
                p = (net.minecraft.entity.player.EntityPlayer)e;
            }
            block6: for (int i = 1; i < 5; ++i) {
                Item it;
                ItemStack is = p.getEquipmentInSlot(i);
                if (is == null || (it = is.getItem()) == null || !(it instanceof ItemOreSpawnArmor) || (ia = (ItemOreSpawnArmor)it).get_armor_material() != 4) continue;
                switch (ia.get_armor_type()) {
                    case 0: {
                        if (!par2World.isRemote && p != null && par2World.rand.nextInt(10) == 1) {
                            p.addExperience(1);
                        }
                        par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, e.posX, e.posY + 1.5, e.posZ, par2World.rand.nextGaussian(), par2World.rand.nextGaussian(), par2World.rand.nextGaussian());
                        continue block6;
                    }
                    case 1: {
                        if (!par2World.isRemote && p != null && par2World.rand.nextInt(20) == 1) {
                            p.addExperience(1);
                        }
                        par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, e.posX, e.posY + 1.25, e.posZ, par2World.rand.nextGaussian(), par2World.rand.nextGaussian(), par2World.rand.nextGaussian());
                        continue block6;
                    }
                    case 2: {
                        if (!par2World.isRemote && p != null && par2World.rand.nextInt(30) == 1) {
                            p.addExperience(1);
                        }
                        par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, e.posX, e.posY + 0.75, e.posZ, par2World.rand.nextGaussian(), par2World.rand.nextGaussian(), par2World.rand.nextGaussian());
                        continue block6;
                    }
                    case 3: {
                        if (!par2World.isRemote && p != null && par2World.rand.nextInt(40) == 1) {
                            p.addExperience(1);
                        }
                        par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, e.posX, e.posY + 0.25, e.posZ, par2World.rand.nextGaussian(), par2World.rand.nextGaussian(), par2World.rand.nextGaussian());
                        continue block6;
                    }
                }
            }
        }
    }

    public int getDamageVsEntity(Entity par1Entity) {
        return this.weaponDamage;
    }

    public String getMaterialName() {
        return "Emerald";
    }

    public boolean hitEntity(ItemStack par1ItemStack, net.minecraft.entity.EntityLivingBase par2EntityLiving, net.minecraft.entity.EntityLivingBase par3EntityLiving) {
        float i = 0.0f;
        net.minecraft.entity.player.EntityPlayer p = null;
        Object l = null;
        if (par3EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            p = (net.minecraft.entity.player.EntityPlayer)par3EntityLiving;
        }
        if (par2EntityLiving != null && par2EntityLiving instanceof EntityLiving) {
            i = 10.0f;
        }
        if (i > 0.0f && p != null) {
            p.addExperience((int)i);
        }
        if (p != null && (i = (float)(p.experienceLevel / 2)) > 0.0f && par2EntityLiving != null) {
            par2EntityLiving.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)p), i);
        }
        if (this.worldr != null && par2EntityLiving != null) {
            int j = 0;
            while ((float)j <= i / 2.0f) {
                this.worldr.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, par2EntityLiving.posX, par2EntityLiving.posY + 1.0, par2EntityLiving.posZ, this.worldr.rand.nextGaussian(), this.worldr.rand.nextGaussian(), this.worldr.rand.nextGaussian());
                ++j;
            }
        }
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 3000;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

