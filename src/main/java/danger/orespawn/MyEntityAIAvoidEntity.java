A/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.RandomPositionGenerator
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.pathfinding.net.minecraft.pathfinding.Path
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.util.math.Vec3d
 */
package danger.orespawn;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.Minecraft;

public class MyEntityAIAvoidEntity
extends EntityAIBase {
    private EntityCreature theEntity;
    private double farSpeed;
    private double nearSpeed;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private net.minecraft.pathfinding.Path entitynet.minecraft.pathfinding.Path;
    private PathNavigate entityPathNavigate;
    private Class targetEntityClass;

    public MyEntityAIAvoidEntity(EntityCreature par1EntityCreature, Class par2Class, float par3, double par4, double par6) {
        this.theEntity = par1EntityCreature;
        this.targetEntityClass = par2Class;
        this.distanceFromEntity = par3;
        this.farSpeed = par4;
        this.nearSpeed = par6;
        this.entityPathNavigate = par1EntityCreature.getNavigator();
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        Vec3d vec3;
        EntityCannonFodder cf;
        if (this.theEntity != null && this.theEntity instanceof EntityCannonFodder && (cf = (EntityCannonFodder)this.theEntity).get_is_activated() != 0) {
            return false;
        }
        if (this.targetEntityClass == net.minecraft.entity.player.EntityPlayer.class) {
            if (this.theEntity instanceof EntityTameable && ((EntityTameable)this.theEntity).isTamed()) {
                return false;
            }
            this.closestLivingEntity = this.theEntity.world.getClosestPlayerToEntity((Entity)this.theEntity, (double)this.distanceFromEntity);
            if (this.closestLivingEntity == null) {
                return false;
            }
        } else {
            List list = this.theEntity.world.selectEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand((double)this.distanceFromEntity, 3.0, (double)this.distanceFromEntity), IMob.targetEntitySelector);
            if (list.isEmpty()) {
                return false;
            }
            this.closestLivingEntity = (Entity)list.get(0);
        }
        if ((vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom((EntityCreature)this.theEntity, (int)16, (int)7, (Vec3)Vec3.createVectorHelper((double)this.closestLivingEntity.posX, (double)this.closestLivingEntity.posY, (double)this.closestLivingEntity.posZ))) == null) {
            return false;
        }
        if (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity.getDistanceSqToEntity((Entity)this.theEntity)) {
            return false;
        }
        this.entitynet.minecraft.pathfinding.Path = this.entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
        return this.entitynet.minecraft.pathfinding.Path == null ? false : this.entitynet.minecraft.pathfinding.Path.isDestinationSame(vec3);
    }

    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath();
    }

    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entitynet.minecraft.pathfinding.Path, this.farSpeed);
    }

    public void resetTask() {
        this.closestLivingEntity = null;
    }

    public void updateTask() {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }

    static EntityCreature func_98217_a(MyEntityAIAvoidEntity par0EntityAIAvoidEntity) {
        return par0EntityAIAvoidEntity.theEntity;
    }
}

