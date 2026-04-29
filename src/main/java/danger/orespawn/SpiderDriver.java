/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.monster.EntityCaveSpider
 *  net.minecraft.entity.monster.EntitySpider
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class SpiderDriver
extends EntitySpider {
//     private GenericTargetSorter TargetSorter = new GenericTargetSorter((Entity)this);

    public SpiderDriver(World worldIn) {
        super(worldIn);
        this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.5));
        this.tasks.addTask(3, (EntityAIBase)new MyEntityAIWander((EntityCreature)this, 0.65f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return this.getRidingEntity() == null;
    }

    public boolean isAIEnabled() {
        return true;
    }

    protected Entity findPlayerToAttack() {
        double d0 = 16.0;
        return this.getEntityWorld().getClosestVulnerablePlayerToEntity((Entity)this, d0);
    }

    protected void updateAITasks() {
        net.minecraft.entity.EntityLivingBase e;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL && this.getEntityWorld().rand.nextInt(5) == 0 && this.getRidingEntity() == null && (e = this.findSpiderRobot()) != null) {
            this.faceEntity((Entity)e, 10.0f, 10.0f);
            if (this.getDistanceSq((Entity)e) < (double)((4.0f + e.width / 2.0f) * (4.0f + e.width / 2.0f))) {
                this.startRiding((Entity)e);
            } else {
                this.getNavigator().tryMoveToEntityLiving((Entity)e, 0.55);
            }
        }
        if (this.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL && this.getEntityWorld().rand.nextInt(4) == 0 && this.getRidingEntity() != null && (e = this.findSomethingToAttack()) != null) {
            this.faceEntity((Entity)e, 10.0f, 10.0f);
            if (!(this.getDistanceSq((Entity)e) < (double)((11.0f + e.width / 2.0f) * (11.0f + e.width / 2.0f))) && this.getRidingEntity() instanceof SpiderRobot) {
                SpiderRobot sp = (SpiderRobot)this.getRidingEntity();
                double d1 = e.posZ - this.posZ;
                double d2 = e.posX - this.posX;
                double dd = Math.atan2(d1, d2);
                sp.goThisWay(0.35 * Math.cos(dd), 0.35 * Math.sin(dd));
            }
        }
    }

    protected void attackEntity(Entity par1Entity, float par2) {
        if (this.ticksExisted <= 0 && par2 < 2.0f && par1Entity.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY && par1Entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
            this.ticksExisted = 16;
            this.attackEntityAsMob(par1Entity);
            if (this.getEntityWorld().rand.nextInt(2) == 0) {
                ((net.minecraft.entity.EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 60, 0));
            }
        }
    }

    public int getTotalArmorValue() {
        if (this.getRidingEntity() != null) {
            return 8;
        }
        return 20;
    }

    private net.minecraft.entity.EntityLivingBase findSpiderRobot() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(SpiderRobot.class, this.getEntityBoundingBox().expand(25.0, 15.0, 25.0));
//         Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (var4.getPassengers() != null) continue;
            return var4;
        }
        return null;
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
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
        if (par1EntityLiving instanceof SpiderRobot) {
            return false;
        }
        if (par1EntityLiving instanceof SpiderDriver) {
            return false;
        }
        if (par1EntityLiving instanceof EntitySpider) {
            return false;
        }
        if (par1EntityLiving instanceof EntityCaveSpider) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.isCreative();
        }
        return !(this.getDistanceSq((Entity)par1EntityLiving) < 36.0);
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(35.0, 15.0, 35.0));
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

    public boolean getCanSpawnHere() {
        SpiderRobot target = null;
        target = (SpiderRobot)this.getEntityWorld().findNearestEntityWithinAABB(SpiderRobot.class, this.getEntityBoundingBox().expand(24.0, 12.0, 24.0), (Entity)this);
        if (target != null) {
            return true;
        }
        return super.getCanSpawnHere();
    }
}

