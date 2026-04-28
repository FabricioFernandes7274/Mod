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
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Ghost
extends EntityAmbientCreature {
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;

    public Ghost(World par1World) {
        super(par1World);
        this.setSize(0.5f, 1.5f);
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 5;
        this.noClip = true;
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
        return 0.3f;
    }

    protected float getSoundPitch() {
        return 1.5f;
    }

    protected String getLivingSound() {
        if (this.world.rand.nextInt(2) == 0) {
            return "orespawn:ghost_sound";
        }
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

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
        if (this.isNoDespawnRequired()) {
            this.noClip = false;
        }
        super.onUpdate();
        this.motionY *= 0.65;
    }

    protected void updateAITasks() {
        int i = 0;
        int j = 0;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.world.rand.nextInt(40) == 1 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.0f) {
            net.minecraft.entity.player.EntityPlayer target = null;
            target = (net.minecraft.entity.player.EntityPlayer)this.world.findNearestEntityWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.boundingBox.expand(16.0, 16.0, 16.0), (Entity)this);
            if (target != null) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)target.posX + this.rand.nextInt(3) - this.rand.nextInt(3), (int)target.posY + 1, (int)target.posZ + this.rand.nextInt(3) - this.rand.nextInt(3));
            } else {
                Block bid;
                for (i = 0; i < 3 && (bid = this.world.getBlock((int)this.posX, (int)this.posY + i, (int)this.posZ)) != Blocks.AIR; ++i) {
                }
                for (j = -1; j >= -3 && (bid = this.world.getBlock((int)this.posX, (int)this.posY + j, (int)this.posZ)) == Blocks.AIR; --j) {
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + this.rand.nextInt(10) - this.rand.nextInt(10), (int)this.posY + i + j + this.rand.nextInt(4) + 1, (int)this.posZ + this.rand.nextInt(10) - this.rand.nextInt(10));
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.1 - this.motionX) * 0.05;
        this.motionY += (Math.signum(var3) * 0.7 - this.motionY) * 0.1;
        this.motionZ += (Math.signum(var5) * 0.1 - this.motionZ) * 0.05;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.05f;
        this.rotationYaw += var8 / 6.0f;
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
        for (int k = -2; k < 2; ++k) {
            for (int j = -2; j < 2; ++j) {
                for (int i = 0; i < 5; ++i) {
                    Block bid = this.world.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Ghost")) continue;
                    return true;
                }
            }
        }
        return !this.world.isDaytime();
    }

    public void initCreature() {
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return ret;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        return ret;
    }
}

