/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Firefly
extends EntityAmbientCreature {
    private static final ResourceLocation texture1 = new net.minecraft.util.ResourceLocation("orespawn", "Fireflytexture.png");
    int my_blink = 20 + this.rand.nextInt(20);
    int blinker = 0;
    int myspace = 0;
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;

    public Firefly(World par1World) {
        super(par1World);
        this.setSize(0.4f, 0.8f);
        this.getNavigator().setAvoidsWater(true);
        this.renderDistanceWeight = 3.0;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.1f);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    public ResourceLocation getTexture(Firefly a) {
        return texture1;
    }

    protected void entityInit() {
        super.entityInit();
    }

    public float getBlink() {
        if (this.blinker < this.my_blink / 2) {
            return 240.0f;
        }
        return 0.0f;
    }

    protected float getSoundVolume() {
        return 0.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    public int mygetMaxHealth() {
        return 1;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock((Block)OreSpawnMain.ExtremeTorch);
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.600000023841;
        ++this.blinker;
        if (this.blinker > this.my_blink) {
            this.blinker = 0;
        }
        if (this.isNoDespawnRequired()) {
            return;
        }
        long t = this.world.getWorldTime();
        if ((t %= 24000L) > 11000L) {
            return;
        }
        if (this.world.rand.nextInt(500) == 1) {
            this.setDead();
        }
    }

    protected void updateAITasks() {
        int keep_trying = 25;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.rand.nextInt(40) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.0f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + this.rand.nextInt(4) - this.rand.nextInt(4), (int)this.posY + this.rand.nextInt(4) - 2, (int)this.posZ + this.rand.nextInt(4) - this.rand.nextInt(4));
                bid = this.world.getBlock(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ());
                --keep_trying;
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.2 - this.motionX) * 0.1;
        this.motionY += (Math.signum(var3) * (double)0.7f - this.motionY) * 0.1;
        this.motionZ += (Math.signum(var5) * 0.2 - this.motionZ) * 0.1;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.2f;
        this.rotationYaw += var8 / 4.0f;
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
        Block bid = this.world.getBlock((int)this.posX, (int)this.posY, (int)this.posZ);
        if (bid != Blocks.AIR) {
            return false;
        }
        if (this.world.isDaytime()) {
            return false;
        }
        if (this.findBuddies() > 10) {
            return false;
        }
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID4) {
            return true;
        }
        return !(this.posY < 50.0);
    }

    private int findBuddies() {
        List var5 = this.world.getEntitiesWithinAABB(Firefly.class, this.boundingBox.expand(20.0, 8.0, 20.0));
        return var5.size();
    }

    public void initCreature() {
    }

    protected boolean canDespawn() {
        if (!this.world.isDaytime()) {
            return false;
        }
        return !this.isNoDespawnRequired();
    }
}

