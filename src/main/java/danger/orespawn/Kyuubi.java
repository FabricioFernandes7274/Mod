/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveThroughVillage
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAIWander
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntityPigZombie
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntitySmallFireball
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Kyuubi extends EntityMob {

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

    public Kyuubi(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 1.25f);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 30;
        //this.fireResistance = 1000;
        this.isImmuneToFire = true;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, (double)1.35f));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 10.0f));
        this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.TargetSorter = new GenericTargetSorter((Entity)this);
    }

    protected void entityInit() {
        super.entityInit();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Kyuubi_stats.attack);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Kyuubi_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Kyuubi_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.world.rand.nextInt(10) == 1) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
            this.setFire(5);
            if (this.isInWater()) {
                this.attackEntityAsMob((Entity)this);
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 1.75, this.posZ, 0.0, 0.0, 0.0);
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 1.75, this.posZ, 0.0, 0.0, 0.0);
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
            }
        }
    }

    public int getAttackStrength(Entity par1Entity) {
        return 3;
    }

    protected String getLivingSound() {
        return "orespawn:kyuubi_living";
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    protected float getSoundVolume() {
        return 0.75f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        int i = this.world.rand.nextInt(6);
        if (i == 0) {
            return Items.GOLD_NUGGET;
        }
        if (i == 1) {
            return OreSpawnMain.UraniumNugget;
        }
        if (i == 2) {
            return OreSpawnMain.TitaniumNugget;
        }
        return null;
    }

    public void initCreature() {
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    protected void updateAITasks() {
        net.minecraft.entity.EntityLivingBase e;
        if (this.isDead()) {
            return;
        }
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        super.updateAITasks();
        if (this.world.rand.nextInt(10) == 1 && (e = this.findSomethingToAttack()) != null) {
            this.faceEntity((Entity)e, 10.0f, 10.0f);
            this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.25);
            if (this.getDistanceSq((Entity)e) < 64.0 && (this.rand.nextInt(6) == 0 || this.rand.nextInt(8) == 1)) {
                EntitySmallFireball var2 = new EntitySmallFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, e.posX - this.posX, e.posY + 0.75 - (this.posY + 1.25), e.posZ - this.posZ);
                var2.setLocationAndAngles(this.posX, this.posY + 1.25, this.posZ, this.rotationYaw, this.rotationPitch);
                this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity((Entity)var2);
            }
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
        if (par1EntityLiving instanceof EntityMob) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPigZombie) {
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
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(12.0, 4.0, 12.0));
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

    public boolean getCanSpawnHere() {
        return true;
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), new ItemStack(index, par1, 0));
        this.world.spawnEntity((Entity)var3);
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        for (var4 = 0; var4 < 10; ++var4) {
            this.dropItemRand(Items.COAL, 1);
        }
        for (var4 = 0; var4 < 3; ++var4) {
            this.dropItemRand(Item.getItemFromBlock((Block)Blocks.REDSTONE_BLOCK), 1);
        }
        for (var4 = 0; var4 < 4; ++var4) {
            this.dropItemRand(Item.getItemFromBlock((Block)Blocks.QUARTZ_BLOCK), 1);
        }
    }
}


}