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
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HerculesBeetle extends EntityMob {

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
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

    public HerculesBeetle(World worldIn) {
        super(worldIn);
        this.setSize(3.25f, 2.75f);
        ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.experienceValue = 200;
        //this.fireResistance = 100;
        this.isImmuneToFire = true;
//         this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, (double)0.9f, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 14, 1.0));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)0);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.HerculesBeetle_stats.attack);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.HerculesBeetle_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.HerculesBeetle_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
        this.posY += 0.5;
    }

    public int getHerculesBeetleHealth() {
        return (int)this.getHealth();
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE; }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    protected float getSoundVolume() {
        return 1.5f;
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
        var3 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(5) - (double)OreSpawnMain.OreSpawnRand.nextInt(5), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(5) - (double)OreSpawnMain.OreSpawnRand.nextInt(5), is);
        if (var3 != null) {
            this.getEntityWorld().spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        this.dropItemRand(OreSpawnMain.MyBigHammer, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int i = 4 + this.getEntityWorld().rand.nextInt(8);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.BEEF, 1);
        }
        i = 1 + this.getEntityWorld().rand.nextInt(5);
        block16: for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.getEntityWorld().rand.nextInt(20);
            switch (var3) {
                case 0: {
                    continue block16;
                }
                case 1: {
                    ItemStack is = this.dropItemRand(Items.DIAMOND, 1);
                    continue block16;
                }
                case 2: {
                    ItemStack is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIAMOND_BLOCK), 1);
                    continue block16;
                }
                case 3: {
                    ItemStack is = this.dropItemRand(Items.DIAMOND_SWORD, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.LOOTING, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block16;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block16;
                }
                case 4: {
                    ItemStack is = this.dropItemRand(Items.DIAMOND_SHOVEL, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block16;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block16;
                }
                case 5: {
                    ItemStack is = this.dropItemRand(Items.DIAMOND_PICKAXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block16;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block16;
                }
                case 6: {
                    ItemStack is = this.dropItemRand(Items.DIAMOND_AXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block16;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block16;
                }
                case 7: {
                    ItemStack is = this.dropItemRand(Items.DIAMOND_HOE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block16;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block16;
                }
                case 8: {
                    ItemStack is = this.dropItemRand((Item)Items.DIAMOND_HELMET, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(2));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block16;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block16;
                }
                case 9: {
                    ItemStack is = this.dropItemRand((Item)Items.DIAMOND_CHESTPLATE, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block16;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block16;
                }
                case 10: {
                    ItemStack is = this.dropItemRand((Item)Items.DIAMOND_LEGGINGS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block16;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block16;
                }
                case 11: {
                    ItemStack is = this.dropItemRand((Item)Items.DIAMOND_BOOTS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block16;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block16;
                }
                case 12: {
                    continue block16;
                }
            }
        }
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        double ks = 0.45;
        double inair = 1.25;
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
                float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
                if (par1Entity.isDead || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                    inair *= 2.0;
                }
                par1Entity.addVelocity(Math.cos(f3) * ks, inair * (double)Math.abs(this.getEntityWorld().rand.nextFloat()), Math.sin(f3) * ks);
            }
            return true;
        }
        return false;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (this.hurt_timer > 0) {
            return false;
        }
        if (!par1DamageSource.getDamageType().equals("cactus")) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
            this.hurt_timer = 20;
            Entity e = par1DamageSource.getTrueSource();
            if (e != null && e instanceof EntityLiving) {
                this.setAttackTarget((net.minecraft.entity.EntityLivingBase)((EntityLiving)e));
                this.setTarget(e);
                this.getNavigator().tryMoveToEntityLiving((Entity)((EntityLiving)e), 1.2);
            }
        }
        return ret;
    }

    protected void updateAITasks() {
        net.minecraft.entity.EntityLivingBase e = null;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.getEntityWorld().rand.nextInt(4) == 0) {
            e = this.getAttackTarget();
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getDistanceSq((Entity)e) < (double)((5.0f + e.width / 2.0f) * (5.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    if (this.getEntityWorld().rand.nextInt(3) == 0 || this.getEntityWorld().rand.nextInt(4) == 1) {
                        this.attackEntityAsMob((Entity)e);
                        if (!this.getEntityWorld().isRemote) {
                            if (this.getEntityWorld().rand.nextInt(3) == 1) {
                                this.getEntityWorld().playSound(null, e.posX, e.posY, e.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 1.4f, 1.0f);
                            } else {
                                this.getEntityWorld().playSound(null, e.posX, e.posY, e.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.0f);
                            }
                        }
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.2);
                }
            } else {
                this.setAttacking(0);
            }
        }
        if (this.getEntityWorld().rand.nextInt(150) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(2.0f);
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
        if (par1EntityLiving instanceof EntityCreeper) {
            return false;
        }
        if (par1EntityLiving instanceof HerculesBeetle) {
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
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(16.0, 6.0, 16.0));
//         Collections.sort(var5, this.TargetSorter);
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
        return 0 /* this.dataManager.get(20) */;
    }

    public final void setAttacking(int par1) {
//         this.dataManager.set(20, (Object)((byte)par1));
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int i;
        int j;
        int k;
        for (k = -3; k < 3; ++k) {
            for (j = -3; j < 3; ++j) {
                for (i = 0; i < 5; ++i) {
                    bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.getEntityWorld().getTileEntity(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int))this.posZ + k);
                    String s = tileentitymobspawner != null ? "Spawner" : "Spawner";
                    if (s == null || !s.equals("Hercules Beetle")) continue;
                    return true;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.getEntityWorld().isDaytime()) {
            return false;
        }
        if (this.posY < 50.0) {
            return false;
        }
        for (k = -2; k < 2; ++k) {
            for (j = -2; j < 2; ++j) {
                for (i = 2; i < 5; ++i) {
                    bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        HerculesBeetle target = null;
        target = (HerculesBeetle)this.getEntityWorld().findNearestEntityWithinAABB(HerculesBeetle.class, this.getEntityBoundingBox().expand(16.0, 6.0, 16.0), (Entity)this);
        return target == null;
    }
}


}