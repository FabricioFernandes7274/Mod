/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class GoldFish
extends EntityAnimal {
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;

    public GoldFish(World par1World) {
        super(par1World);
        this.setSize(0.75f, 0.5f);
        this.experienceValue = 5;
        this.isImmuneToFire = false;
        //this.fireResistance = 5;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.22f);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return !this.world.isDaytime();
    }

    protected float getSoundVolume() {
        return 0.45f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected String getLivingSound() {
        return "splash";
    }

    protected String getHurtSound() {
        return "splash";
    }

    protected String getDeathSound() {
        return "orespawn:little_splat";
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    public int mygetMaxHealth() {
        return 6;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        int updown = 0;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if ((int)this.posY < 120) {
            updown = 2;
        }
        if ((int)this.posY > 140) {
            updown = -2;
        }
        if (this.rand.nextInt(300) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.1f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.rand.nextInt(5) + 5;
                xdir = this.rand.nextInt(5) + 5;
                if (this.rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }
                if (this.rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(11) - 5 + updown, (int)this.posZ + zdir);
                bid = this.world.getBlock(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.4 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.4 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.4 - this.motionX) * 0.3;
        this.motionY += (Math.signum(var3) * 0.7 - this.motionY) * 0.2;
        this.motionZ += (Math.signum(var5) * 0.4 - this.motionZ) * 0.3;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.75f;
        this.rotationYaw += var8 / 6.0f;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    protected Item getDropItem() {
        int i = this.world.rand.nextInt(3);
        if (i == 0) {
            return Item.getItemFromBlock((Block)Blocks.GOLD_BLOCK);
        }
        if (i == 1) {
            return OreSpawnMain.UraniumNugget;
        }
        if (i == 2) {
            return OreSpawnMain.TitaniumNugget;
        }
        return null;
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }
}

