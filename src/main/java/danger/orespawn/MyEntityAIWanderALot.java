/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.RandomPositionGenerator
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.util.math.Vec3d
 */
package danger.orespawn;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.Vec3d;

public class MyEntityAIWanderALot
extends EntityAIBase {
    private EntityCreature entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private int xzRange = 10;
    private int busy = 0;

    public MyEntityAIWanderALot(EntityCreature par1EntityCreature, int par1, double par2) {
        this.entity = par1EntityCreature;
        this.xzRange = par1;
        this.speed = par2;
        this.setMutexBits(1);
    }

    public void setBusy(int i) {
        this.busy = i;
    }

    public boolean shouldExecute() {
        if (this.busy != 0) {
            return false;
        }
        if (this.entity.world.rand.nextInt(30) != 0) {
            return false;
        }
        if (this.entity instanceof EntityTameable && ((EntityTameable)this.entity).isSitting()) {
            return false;
        }
        Vec3d var1 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.entity, (int)this.xzRange, (int)7);
        if (var1 == null) {
            return false;
        }
        this.xPosition = var1.x;
        this.yPosition = var1.y;
        this.zPosition = var1.z;
        return true;
    }

    public boolean continueExecuting() {
        return !this.entity.getNavigator().noPath();
    }

    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
}

