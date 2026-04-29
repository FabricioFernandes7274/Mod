/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.com.google.common.base.Predicate
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 */
package danger.orespawn;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class MyValentineTarget
extends MyEntityAITarget {
    net.minecraft.entity.EntityLivingBase targetEntity;
    net.minecraft.entity.EntityLivingBase Me;
    Class targetClass;
    int targetChance;
    private final com.google.common.base.Predicate targetEntitySelector;
//     private MyValentineTargetSorter theNearestAttackableTargetSorter;

    public MyValentineTarget(EntityLiving par1EntityLiving, Class par2Class, float par3, int par4, boolean par5, boolean par6) {
        this(par1EntityLiving, par2Class, par3, par4, par5, par6, null);
        this.Me = par1EntityLiving;
    }

    public MyValentineTarget(EntityLiving par1, Class par2, float par3, int par4, boolean par5, boolean par6, com.google.common.base.Predicate par7Predicate) {
        super(par1, par3, par5, par6);
        this.targetClass = par2;
        this.targetDistance = par3;
        this.targetChance = par4;
//         this.theNearestAttackableTargetSorter = new MyValentineTargetSorter(this, (Entity)par1);
        this.targetEntitySelector = par7Predicate;
        this.setMutexBits(1);
        this.Me = par1;
    }

    public boolean shouldExecute() {
        if (OreSpawnMain.valentines_day == 0) {
            return false;
        }
        if (this.Me != null && this.Me instanceof Girlfriend) {
            Girlfriend gf = (Girlfriend)this.Me;
            if (gf.feelingBetter != 0) {
                return false;
            }
        }
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(100) > this.targetChance) {
            return false;
        }
        List var5 = this.taskOwner.world.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand((double)this.targetDistance, 4.0, (double)this.targetDistance), this.targetEntitySelector);
//         Collections.sort(var5, this.theNearestAttackableTargetSorter);
        for (Entity var3 : var5) {
            net.minecraft.entity.EntityLivingBase var4;
            if (!(var3 instanceof net.minecraft.entity.EntityLivingBase) || !this.isSuitableTarget(var4 = (net.minecraft.entity.EntityLivingBase)var3, false)) continue;
            this.targetEntity = var4;
            return true;
        }
        this.targetEntity = null;
        return false;
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
}

