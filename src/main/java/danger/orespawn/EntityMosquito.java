/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMosquito
extends EntityAmbientCreature {
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;

    public EntityMosquito(World worldIn) {
        super(worldIn);
        this.setSize(0.2f, 0.2f);
        ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.experienceValue = 5;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.1f);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    protected float getSoundVolume() {
        return 0.4f;
    }

    protected float getSoundPitch() {
        return 1.5f;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE; }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    public int mygetMaxHealth() {
        return 2;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= (double)0.6f;
    }

    protected void updateAITasks() {
        int keep_trying = 50;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.getEntityWorld().rand.nextInt(20) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 3.0f) {
            net.minecraft.entity.player.EntityPlayer target = null;
            if (OreSpawnMain.OreSpawnRand.nextInt(4) == 0) {
                target = (net.minecraft.entity.player.EntityPlayer)this.getEntityWorld().findNearestEntityWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.getEntityBoundingBox().expand(10.0, 6.0, 10.0), (Entity)this);
                if (target != null) {
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)target.posX, (int)target.posY + 2, (int)target.posZ);
                } else {
                    Block bid = Blocks.STONE;
                    while (bid != Blocks.AIR && keep_trying != 0) {
                        this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + this.getEntityWorld().rand.nextInt(6) - this.getEntityWorld().rand.nextInt(6), (int)this.posY + this.getEntityWorld().rand.nextInt(6) - 2, (int)this.posZ + this.getEntityWorld().rand.nextInt(6) - this.getEntityWorld().rand.nextInt(6));
                        bid = this.getEntityWorld().getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                        --keep_trying;
                    }
                }
            } else {
                Block bid = Blocks.STONE;
                while (bid != Blocks.AIR && keep_trying != 0) {
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + this.getEntityWorld().rand.nextInt(6) - this.getEntityWorld().rand.nextInt(6), (int)this.posY + this.getEntityWorld().rand.nextInt(6) - 2, (int)this.posZ + this.getEntityWorld().rand.nextInt(6) - this.getEntityWorld().rand.nextInt(6));
                    bid = this.getEntityWorld().getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                    --keep_trying;
                }
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * (double)0.1f;
        this.motionY += (Math.signum(var3) * (double)0.7f - this.motionY) * (double)0.1f;
        this.motionZ += (Math.signum(var5) * 0.5 - this.motionZ) * (double)0.1f;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.3f;
        this.rotationYaw += var8;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public void initCreature() {
    }
}

