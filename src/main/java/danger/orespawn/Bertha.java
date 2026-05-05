package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class Bertha extends ItemSword {

    public Bertha(Item.ToolMaterial material) {
        super(material);
        this.setMaxDamage(9000);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (this == OreSpawnMain.MyRoyal) {
            stack.addEnchantment(Enchantments.UNBREAKING, 5);
        } else if (this != OreSpawnMain.MyHammy) {
            stack.addEnchantment(Enchantments.KNOCKBACK, 5);
            stack.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1);
            stack.addEnchantment(Enchantments.FIRE_ASPECT, 1);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // Garante que a espada sempre terá os encantamentos, mesmo se spawnada via comando/criativo
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
        if (lvl == 0) {
            lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
        }
        if (lvl <= 0) {
            if (this == OreSpawnMain.MyRoyal) {
                stack.addEnchantment(Enchantments.UNBREAKING, 5);
            } else if (this != OreSpawnMain.MyHammy) {
                stack.addEnchantment(Enchantments.KNOCKBACK, 5);
                stack.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1);
                stack.addEnchantment(Enchantments.FIRE_ASPECT, 1);
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity != null && OreSpawnMain.big_bertha_pvp == 0) {
            if (entity instanceof EntityPlayer || entity instanceof Girlfriend || entity instanceof Boyfriend) {
                return true; // Retornar true CANCELA o ataque
            }
            if (entity instanceof EntityTameable && ((EntityTameable)entity).isTamed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (entityLiving != null && entityLiving instanceof EntityPlayer && !entityLiving.world.isRemote) {
            EntityPlayer p = (EntityPlayer)entityLiving;
            double xzoff = 2.0;
            double yoff = 1.55;
            
            // Dispara a colisão destrutiva invisível da espada
            BerthaHit lb = new BerthaHit(p.world, p);
            lb.setLocationAndAngles(p.posX - xzoff * Math.sin(Math.toRadians(p.rotationYawHead)), p.posY + yoff, p.posZ + xzoff * Math.cos(Math.toRadians(p.rotationYawHead)), p.rotationYawHead, p.rotationPitch);
            lb.motionX *= 2.0;
            lb.motionY *= 2.0;
            lb.motionZ *= 2.0;
            
            if (this == OreSpawnMain.MyRoyal) {
                lb.setHitType(2);
            }
            if (this == OreSpawnMain.MyHammy) {
                lb.setHitType(3);
            }
            
            p.world.spawnEntity(lb);
            stack.damageItem(1, p);
        }
        return false;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 9000;
    }
}