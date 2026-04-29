/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class PurplePower
extends EntityLiving {
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;
//     private GenericTargetSorter TargetSorter = null;
    private int purple_type = 0;

    public PurplePower(World worldIn) {
        super(worldIn);
        this.setSize(0.75f, 0.75f);
        this.experienceValue = 35;
        this.isImmuneToFire = true;
        //this.fireResistance = 25;
//         this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.noClip = true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(500.0);
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)0);
    }

    public void setPurpleType(int par1) {
        if (this.world == null) {
            return;
        }
        if (this.getEntityWorld().isRemote) {
            return;
        }
        this.purple_type = par1;
//         this.dataManager.set(20, (Object)par1);
    }

    public int getPurpleType() {
        return 0 /* this.dataManager.get(20) */;
    }

    protected boolean canDespawn() {
        return false;
    }

    protected float getSoundVolume() {
        return 0.75f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE; }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    public int mygetMaxHealth() {
        return 1000;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        Object e = null;
        super.onUpdate();
        this.motionY *= 0.6;
        if (this.getPurpleType() == 0) {
            if (this.getEntityWorld().isRemote && this.getEntityWorld().rand.nextInt(4) == 1) {
                this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY + 1.25, this.posZ, (double)((this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) / 2.0f), (double)((this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) / 2.0f), (double)((this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) / 2.0f));
            }
        } else if (this.getEntityWorld().isRemote && this.getEntityWorld().rand.nextInt(6) == 1) {
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY + (double)0.65f, this.posZ, (double)((this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) / 5.0f), (double)((this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) / 5.0f), (double)((this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) / 5.0f));
        }
        if (this.getEntityWorld().isRemote) {
            this.purple_type = this.getPurpleType();
        } else {
            this.setPurpleType(this.purple_type);
        }
        if (!this.getEntityWorld().isRemote && this.getEntityWorld().rand.nextInt(2500) == 1) {
            if (this.getPurpleType() == 10) {
                this.getEntityWorld().newExplosion((Entity)null, this.posX, this.posY + 0.25, this.posZ, 9.1f, true, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
            }
            this.setDead();
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.getEntityWorld().rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.55), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        net.minecraft.entity.EntityLivingBase e = null;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.getEntityWorld().rand.nextInt(300) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 2.1f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.getEntityWorld().rand.nextInt(10) + 8;
                xdir = this.getEntityWorld().rand.nextInt(10) + 8;
                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }
                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + this.getEntityWorld().rand.nextInt(20) - 10, (int)this.posZ + zdir);
                bid = this.getEntityWorld().getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        } else if (this.getEntityWorld().rand.nextInt(7) == 2 && this.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL && (e = this.findSomethingToAttack()) != null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + (double)(e.height / 2.0f)), (int)e.posZ);
            if (this.getDistanceSq((Entity)e) < (double)((4.0f + e.width / 2.0f) * (4.0f + e.width / 2.0f))) {
                this.attackEntityAsMob((Entity)e);
                this.setDead();
            }
        }
        if (this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.4 - this.motionX) * 0.2;
        this.motionY += (Math.signum(var3) * (double)0.7f - this.motionY) * 0.20000000149011612;
        this.motionZ += (Math.signum(var5) * 0.4 - this.motionZ) * 0.2;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.75f;
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

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        Entity e = par1DamageSource.getTrueSource();
        float dm = par2;
        if (e != null && e instanceof EntityArrow) {
            return false;
        }
        if (dm > 10.0f) {
            dm = 10.0f;
        }
        ret = super.attackEntityFrom(par1DamageSource, dm);
        if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + (double)(e.height / 2.0f)), (int)e.posZ);
        }
        return ret;
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public int getTotalArmorValue() {
        return 25;
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        EntityTameable e;
        if (this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.isCreative()) {
                return false;
            }
            return this.getPurpleType() <= 0 || this.getPurpleType() == 10;
        }
        if (this.getPurpleType() != 0 && this.getPurpleType() != 10 && par1EntityLiving instanceof EntityTameable && (e = (EntityTameable)par1EntityLiving).isTamed()) {
            return false;
        }
        return !MyUtils.isRoyalty((Entity)par1EntityLiving);
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(32.0, 24.0, 32.0));
//         Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false)) continue;
            return var4;
        }
        return null;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4 = false;
        if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
            net.minecraft.entity.EntityLivingBase e = (net.minecraft.entity.EntityLivingBase)par1Entity;
            if (this.getPurpleType() == 0 || this.getPurpleType() == 10) {
                e.setHealth(e.getHealth() / 4.0f - 1.0f);
                var4 = e.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), e.getMaxHealth() / 8.0f);
                if (this.getPurpleType() == 10) {
                    this.getEntityWorld().newExplosion((Entity)null, e.posX, e.posY - 0.25, e.posZ, 9.1f, true, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
                }
            } else {
                e.setHealth(e.getHealth() * 15.0f / 16.0f);
                var4 = e.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), 5.0f);
                if (this.getPurpleType() == 1) {
                    e.setFire(10);
                }
                if (this.getPurpleType() == 2) {
                    e.addPotionEffect(new PotionEffect(MobEffects.POISON, 50, 0));
                }
                if (this.getPurpleType() == 3) {
                    e.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 50, 0));
                }
            }
        }
        return var4;
    }

    protected Item getDropItem() {
        return null;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("PurpleType", this.purple_type);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.purple_type = par1NBTTagCompound.getInteger("PurpleType");
    }
}

