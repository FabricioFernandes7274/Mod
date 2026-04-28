/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveThroughVillage
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Basilisk extends EntityMob {

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

    public Basilisk(World worldIn) {
        super(worldIn);
        this.setSize(1.6f, 3.5f);
        this.experienceValue = 150;
        //this.fireResistance = 2000;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 20, 1.0));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Basilisk_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)0);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Basilisk_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Basilisk_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    protected void jump() {
        this.motionY += 0.25;
        super.jump();
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isDead()) {
            return;
        }
        if (this.rand.nextInt(200) == 0) {
            this.heal(1.0f);
        }
    }

    public int getBasiliskHealth() {
        return (int)this.getHealth();
    }

    protected String getLivingSound() {
        if (this.rand.nextInt(2) == 0) {
            return "orespawn:basilisk_living";
        }
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:emperorscorpion_death";
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.BEEF;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(OreSpawnMain.MyBasiliskScale, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int i = 12 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.EMERALD, 1);
        }
        i = 8 + this.world.rand.nextInt(5);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.CHICKEN, 1);
        }
        i = 3 + this.world.rand.nextInt(5);
        block15: for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(15);
            switch (var3) {
                case 1: {
                    is = this.dropItemRand(Items.EMERALD, 1);
                    continue block15;
                }
                case 2: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.EMERALD_BLOCK), 1);
                    continue block15;
                }
                case 3: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldSword, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block15;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
                    continue block15;
                }
                case 4: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldShovel, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block15;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block15;
                }
                case 5: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldPickaxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block15;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
                    continue block15;
                }
                case 6: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldAxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block15;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block15;
                }
                case 7: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldHoe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block15;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block15;
                }
                case 8: {
                    is = this.dropItemRand((Item)OreSpawnMain.EmeraldHelmet, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block15;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
                    continue block15;
                }
                case 9: {
                    is = this.dropItemRand((Item)OreSpawnMain.EmeraldBody, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block15;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    continue block15;
                }
                case 10: {
                    is = this.dropItemRand((Item)OreSpawnMain.EmeraldLegs, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block15;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    continue block15;
                }
                case 11: {
                    is = this.dropItemRand((Item)OreSpawnMain.EmeraldBoots, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block15;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    continue block15;
                }
            }
        }
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
                int var2 = 8;
                if (this.world.getDifficulty() == EnumDifficulty.EASY) {
                    var2 = 10;
                }
                if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                    var2 = 12;
                } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                    var2 = 14;
                }
                if (this.world.rand.nextInt(3) == 0) {
                    ((net.minecraft.entity.EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, var2 * 20, 0));
                }
                double ks = 1.5;
                double inair = 0.15;
                float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
                if (par1Entity.isDead() || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                    inair *= 2.0;
                }
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            return true;
        }
        return false;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.hurt_timer > 0) {
            return false;
        }
        this.hurt_timer = 30;
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    protected void updateAITasks() {
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.world.rand.nextInt(5) == 0) {
            net.minecraft.entity.EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getDistanceSq((Entity)e) < (double)((6.0f + e.width / 2.0f) * (6.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(3) == 0 || this.world.rand.nextInt(4) == 1) {
                        this.attackEntityAsMob((Entity)e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.25);
                }
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 5));
                }
            } else {
                this.setAttacking(0);
            }
        }
        if (this.world.rand.nextInt(75) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(1.0f);
        }
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof Basilisk) {
            return false;
        }
        if (par1EntityLiving instanceof LeafMonster) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.isCreative()) {
                return false;
            }
        }
        return true;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(24.0, 7.0, 24.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false)) continue;
            return var4;
        }
        return null;
    }

    public final int getAttacking() {
        return this.dataManager.get(20);
    }

    public final void setAttacking(int par1) {
        this.dataManager.set(20, (Object)((byte)par1));
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int i;
        int j;
        int k;
        for (k = -3; k < 3; ++k) {
            for (j = -3; j < 3; ++j) {
                for (i = 0; i < 5; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)).getBlock()this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Basilisk")) continue;
                    return true;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.isDaytime()) {
            return false;
        }
        for (k = -1; k < 2; ++k) {
            for (j = -1; j < 2; ++j) {
                for (i = 1; i < 5; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)).getBlock()this.posZ + k);
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        Basilisk target = null;
        target = (Basilisk)this.world.findNearestEntityWithinAABB(Basilisk.class, this.boundingBox.expand(20.0, 6.0, 20.0), (Entity)this);
        return target == null;
    }
}


}