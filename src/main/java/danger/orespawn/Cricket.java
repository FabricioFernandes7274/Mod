/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class Cricket
extends EntityAnimal {
    public double moveSpeed = 0.15f;
    private int singing = 0;
    private int jumpcount = 0;

    public Cricket(World par1World) {
        super(par1World);
        this.setSize(0.1f, 0.1f);
        this.experienceValue = 1;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.4));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 8, 1.0));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)0);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int getSinging() {
        return this.dataManager.get(20);
    }

    public void setSinging(int par1) {
        this.dataManager.set(20, (Object)((byte)par1));
    }

    private void jumpAround() {
        this.motionY += (double)(0.55f + Math.abs(this.world.rand.nextFloat() * 0.35f));
        this.posY += 0.25;
        float f = 0.3f + Math.abs(this.world.rand.nextFloat() * 0.25f);
        float d = (float)((double)this.world.rand.nextFloat() * Math.PI * 2.0);
        this.motionX += (double)f * Math.sin(d);
        this.motionZ += (double)f * Math.cos(d);
        this.isAirBorne = true;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (!this.world.isRemote) {
            if (this.singing != 0) {
                --this.singing;
                if (this.singing <= 0) {
                    this.setSinging(0);
                }
            }
            if (this.jumpcount > 0) {
                --this.jumpcount;
            }
            if (this.jumpcount == 0 && this.world.rand.nextInt(50) == 1) {
                this.jumpAround();
                this.jumpcount = 50;
            }
        }
    }

    public boolean isAIEnabled() {
        return true;
    }

    public int mygetMaxHealth() {
        return 3;
    }

    protected String getLivingSound() {
        if (!this.world.isRemote) {
            if (this.world.rand.nextInt(2) == 0) {
                return null;
            }
            this.singing = 40;
            this.setSinging(this.singing);
        }
        return "orespawn:cricket";
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 0.7f;
    }

    protected void playStepSound(int par1, int par2, int par3, int par4) {
    }

    protected void dropFewItems(boolean par1, int par2) {
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 30.0) {
            return false;
        }
        return this.findBuddies() <= 5;
    }

    private int findBuddies() {
        List var5 = this.world.getEntitiesWithinAABB(Cricket.class, this.boundingBox.expand(20.0, 10.0, 20.0));
        return var5.size();
    }
}

