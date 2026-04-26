/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.com.google.common.base.Predicate
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityTameable
 */
package danger.orespawn;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class MyEntityAINearestAttackableTarget
extends MyEntityAITarget {
    EntityLiving targetEntity;
    Class targetClass;
    int targetChance;
    private final com.google.common.base.Predicate targetEntitySelector;
    private MyEntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;

    public MyEntityAINearestAttackableTarget(EntityLiving par1EntityLiving, Class par2Class, float par3, int par4, boolean par5, boolean par6) {
        this(par1EntityLiving, par2Class, par3, par4, par5, par6, null);
    }

    public MyEntityAINearestAttackableTarget(EntityLiving par1, Class par2, float par3, int par4, boolean par5, boolean par6, com.google.common.base.Predicate par7Predicate) {
        super(par1, par3, par5, par6);
        this.targetClass = par2;
        this.targetDistance = par3;
        this.targetChance = par4;
        this.theNearestAttackableTargetSorter = new MyEntityAINearestAttackableTargetSorter(this, (Entity)par1);
        this.targetEntitySelector = par7Predicate;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (this.taskOwner instanceof EntityTameable && !((EntityTameable)this.taskOwner).isTamed()) {
            return false;
        }
        if (this.taskOwner instanceof Girlfriend && !((Girlfriend)this.taskOwner).isTamed()) {
            return false;
        }
        if (this.taskOwner instanceof Girlfriend && ((Girlfriend)this.taskOwner).isSitting()) {
            return false;
        }
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(100) > this.targetChance) {
            return false;
        }
        List var5 = this.taskOwner.world.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand((double)this.targetDistance, 4.0, (double)this.targetDistance), this.targetEntitySelector);
        Collections.sort(var5, this.theNearestAttackableTargetSorter);
        for (Entity var3 : var5) {
            EntityLiving var4 = (EntityLiving)var3;
            if (!this.isSuitableTarget((net.minecraft.entity.EntityLivingBase)var4, false)) continue;
            this.targetEntity = var4;
            return true;
        }
        this.targetEntity = null;
        return false;
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget((net.minecraft.entity.EntityLivingBase)this.targetEntity);
        super.startExecuting();
    }
}

