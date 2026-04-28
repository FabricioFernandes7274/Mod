/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntityGhast
 *  net.minecraft.entity.monster.EntityPigZombie
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.pathfinding.net.minecraft.pathfinding.Path
 *  net.minecraft.pathfinding.PathPoint
 *  net.minecraft.util.MathHelper
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathPoint;

public abstract class MyEntityAITarget
extends EntityAIBase {
    protected EntityLiving taskOwner;
    protected float targetDistance;
    protected boolean shouldCheckSight;
    private boolean nearbyOnly;
    private int targetSearchStatus = 0;
    private int targetSearchDelay = 0;
    private int targetUnseenTicks = 0;

    public MyEntityAITarget(EntityLiving par1EntityLiving, float par2, boolean par3) {
        this(par1EntityLiving, par2, par3, false);
    }

    public MyEntityAITarget(EntityLiving par1EntityLiving, float par2, boolean par3, boolean par4) {
        this.taskOwner = par1EntityLiving;
        this.targetDistance = par2;
        this.shouldCheckSight = par3;
        this.nearbyOnly = par4;
    }

    public boolean continueExecuting() {
        net.minecraft.entity.EntityLivingBase var1 = this.taskOwner.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (!var1.isEntityAlive()) {
            this.taskOwner.setAttackTarget(null);
            return false;
        }
        if (this.taskOwner.getDistanceSq((Entity)var1) > (double)(this.targetDistance * this.targetDistance)) {
            return false;
        }
        if (this.taskOwner instanceof EntityTameable && ((EntityTameable)this.taskOwner).isTamed() && var1 instanceof EntityTameable && ((EntityTameable)var1).isTamed()) {
            return false;
        }
        if (this.shouldCheckSight) {
            if (this.taskOwner.getEntitySenses().canSee((Entity)var1)) {
                this.targetUnseenTicks = 0;
            } else if (++this.targetUnseenTicks > 60) {
                return false;
            }
        }
        return true;
    }

    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.targetUnseenTicks = 0;
    }

    public void resetTask() {
        this.taskOwner.setAttackTarget((net.minecraft.entity.EntityLivingBase)((EntityLiving)null));
    }

    protected boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this.taskOwner) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (this.taskOwner instanceof EntityTameable && ((EntityTameable)this.taskOwner).isTamed()) {
            if (par1EntityLiving instanceof EntityTameable && ((EntityTameable)par1EntityLiving).isTamed()) {
                return false;
            }
            if (par1EntityLiving == ((EntityTameable)this.taskOwner).getOwner()) {
                return false;
            }
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            return OreSpawnMain.valentines_day != 0;
        }
        if (par1EntityLiving instanceof EntityPigZombie) {
            return false;
        }
        if (par1EntityLiving instanceof EntityEnderman) {
            return false;
        }
        if (par1EntityLiving instanceof Mothra) {
            return true;
        }
        if (this.shouldCheckSight && !this.taskOwner.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof EntityCreeper) {
            return true;
        }
        if (par1EntityLiving instanceof EntityGhast) {
            return true;
        }
        if (this.nearbyOnly) {
            if (--this.targetSearchDelay <= 0) {
                this.targetSearchStatus = 0;
            }
            if (this.targetSearchStatus == 0) {
                int n = this.targetSearchStatus = this.canEasilyReach(par1EntityLiving) ? 1 : 2;
            }
            if (this.targetSearchStatus == 2) {
                return false;
            }
        }
        return true;
    }

    private boolean canEasilyReach(net.minecraft.entity.EntityLivingBase par1EntityLiving) {
        int var5;
        this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
        net.minecraft.pathfinding.Path var2 = this.taskOwner.getNavigator().getPathToEntityLiving((Entity)par1EntityLiving);
        if (var2 == null) {
            return false;
        }
        PathPoint var3 = var2.getFinalPathPoint();
        if (var3 == null) {
            return false;
        }
        int var4 = var3.x - net.minecraft.util.math.MathHelper.floor_double((double)par1EntityLiving.posX);
        return (double)(var4 * var4 + (var5 = var3.z - net.minecraft.util.math.MathHelper.floor_double((double)par1EntityLiving.posZ)) * var5) <= 2.25;
    }
}

