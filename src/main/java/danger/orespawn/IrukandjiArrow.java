/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.net.minecraft.network.play.server.SPacketChangeGameState
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.AxisAlignedBB;

import danger.orespawn.Boyfriend;
import danger.orespawn.Cephadrome;
import danger.orespawn.Dragon;
import danger.orespawn.Elevator;
import danger.orespawn.Girlfriend;
import danger.orespawn.OreSpawnMain;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IrukandjiArrow
extends EntityArrow {
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private int inData = 0;
    private boolean inGround = false;
    private int ticksInGround;
    private int ticksInAir = 0;
    private int knockbackStrength;

    public IrukandjiArrow(World par1World) {
        super(par1World);
    }

    public IrukandjiArrow(World par1World, EntityLiving par2EntityLiving, net.minecraft.entity.EntityLivingBase par3EntityLiving, float par4, float par5) {
        super(par1World, (net.minecraft.entity.EntityLivingBase)par2EntityLiving, par3EntityLiving, par4, par5);
    }

    public IrukandjiArrow(World par1World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer, float par4) {
        super(par1World, (net.minecraft.entity.EntityLivingBase)par3EntityPlayer, par4);
    }

    protected void entityInit() {
        this.dataManager.register(16, (Object)0);
    }

    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
        float var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(par1 * par1 + par3 * par3 + par5 * par5));
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        this.motionX = par1 *= (double)par7;
        this.motionY = par3 *= (double)par7;
        this.motionZ = par5 *= (double)par7;
        float var10 = net.minecraft.util.math.MathHelper.sqrt_double((double)(par1 * par1 + par5 * par5));
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0 / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0 / Math.PI);
        this.ticksInGround = 0;
    }

    public void onUpdate() {
        Block var16;
        super.onEntityUpdate();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float var1 = net.minecraft.util.math.MathHelper.sqrt_double((double)(this.motionX * this.motionX + this.motionZ * this.motionZ));
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0 / Math.PI);
        }
        if ((var16 = this.world.getBlockState(new net.minecraft.util.math.BlockPos(this.xTile, this.yTile, this.zTile)).getBlock()) != Blocks.AIR) {
            var16.setBlockBoundsBasedOnState((IBlockAccess)this.world, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB var2 = var16.getCollisionBoundingBoxFromPool(this.world, this.xTile, this.yTile, this.zTile);
            if (var2 != null && var2.isVecInside(new Vec3d((double)this.posX, (double)this.posY, (double)this.posZ))) {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            Block var18 = this.world.getBlockState(new net.minecraft.util.math.BlockPos(this.xTile, this.yTile, this.zTile)).getBlock();
            int var19 = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if (var18 != Blocks.AIR && var19 == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 50 && !this.world.isRemote) {
                    this.dropItem(OreSpawnMain.MyIrukandjiArrow, 1);
                    this.setDead();
                }
            } else {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            float var11;
            int var9;
            ++this.ticksInAir;
            Vec3d var17 = new Vec3d((double)this.posX, (double)this.posY, (double)this.posZ);
            Vec3d var3 = new Vec3d((double)(this.posX + this.motionX), (double)(this.posY + this.motionY), (double)(this.posZ + this.motionZ));
            RayTraceResult var4 = this.world.rayTraceBlocks(var17, var3, true);
            var17 = new Vec3d((double)this.posX, (double)this.posY, (double)this.posZ);
            var3 = new Vec3d((double)(this.posX + this.motionX), (double)(this.posY + this.motionY), (double)(this.posZ + this.motionZ));
            if (var4 != null) {
                var3 = new Vec3d((double)var4.hitVec.x, (double)var4.hitVec.y, (double)var4.hitVec.z);
            }
            Entity var5 = null;
            List var6 = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var7 = 0.0;
            for (var9 = 0; var9 < var6.size(); ++var9) {
                double var14;
                AxisAlignedBB var12;
                RayTraceResult var13;
                Object c;
                Entity var10 = (Entity)var6.get(var9);
                if (!var10.canBeCollidedWith() || var10 == this.shootingEntity && this.ticksInAir < 5 || var10 instanceof Elevator) continue;
                if (var10 instanceof Cephadrome) {
                    c = (Cephadrome)var10;
                    if (c.getPassengers() != null) continue;
                }
                if (var10 instanceof Dragon) {
                    c = (Dragon)var10;
                    if (((Dragon)((Object)c)).getPassengers() != null) continue;
                }
                if (var10 instanceof EntityHorse) {
                    c = (EntityHorse)var10;
                    if (((EntityHorse)c).getPassengers() != null) continue;
                }
                if ((var13 = (var12 = var10.boundingBox.expand((double)(var11 = 0.3f), (double)var11, (double)var11)).calculateIntercept(var17, var3)) == null || !((var14 = var17.distanceTo(var13.hitVec)) < var7) && var7 != 0.0) continue;
                var5 = var10;
                var7 = var14;
            }
            if (var5 != null) {
                var4 = new RayTraceResult(var5);
            }
            if (var4 != null) {
                if (var4.entityHit != null) {
                    float var23 = 100.0f;
                    if (OreSpawnMain.ultimate_sword_pvp == 0) {
                        EntityTameable t;
                        if (var4.entityHit instanceof net.minecraft.entity.player.EntityPlayer || var4.entityHit instanceof Girlfriend || var4.entityHit instanceof Boyfriend) {
                            net.minecraft.entity.EntityLivingBase e = (net.minecraft.entity.EntityLivingBase)var4.entityHit;
                            this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.bowhit")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f)));
                            this.setDead();
                            return;
                        }
                        if (var4.entityHit instanceof EntityTameable && (t = (EntityTameable)var4.entityHit).isTamed()) {
                            this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.bowhit")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f)));
                            this.setDead();
                            return;
                        }
                    }
                    if (this.getIsCritical()) {
                        var23 += (float)this.rand.nextInt((int)var23 / 2 + 2);
                    }
                    DamageSource var21 = null;
                    var21 = this.shootingEntity == null ? DamageSource.causeArrowDamage((EntityArrow)this, (Entity)this) : DamageSource.causeArrowDamage((EntityArrow)this, (Entity)this.shootingEntity);
                    if (this.isBurning()) {
                        var4.entityHit.setFire(5);
                    }
                    if (var4.entityHit.attackEntityFrom(var21, var23)) {
                        if (var4.entityHit instanceof EntityLiving) {
                            float var26;
                            EntityLiving var24 = (EntityLiving)var4.entityHit;
                            if (!this.world.isRemote) {
                                var24.setArrowCountInEntity(var24.getArrowCountInEntity() + 1);
                            }
                            if (this.knockbackStrength > 0 && (var26 = net.minecraft.util.math.MathHelper.sqrt_double((double)(this.motionX * this.motionX + this.motionZ * this.motionZ))) > 0.0f) {
                                var4.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * (double)0.6f / (double)var26, 0.1, this.motionZ * (double)this.knockbackStrength * (double)0.6f / (double)var26);
                            }
                            if (this.shootingEntity != null && var4.entityHit != this.shootingEntity && var4.entityHit instanceof net.minecraft.entity.player.EntityPlayer && this.shootingEntity instanceof net.minecraft.entity.player.EntityPlayerMP) {
                                ((net.minecraft.entity.player.EntityPlayerMP)this.shootingEntity).connection.sendPacket((Packet)new net.minecraft.network.play.server.SPacketChangeGameState(6, 0.0f));
                            }
                        }
                        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.bowhit")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f)));
                        this.setDead();
                    } else {
                        this.motionX *= -0.10000000149;
                        this.motionY *= -0.10000000149;
                        this.motionZ *= -0.10000000149;
                        this.rotationYaw += 180.0f;
                        this.prevRotationYaw += 180.0f;
                        this.ticksInAir = 0;
                    }
                } else {
                    this.xTile = var4.getBlockPos().getX();
                    this.yTile = var4.getBlockPos().getY();
                    this.zTile = var4.getBlockPos().getZ();
                    this.inData = this.world.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (float)(var4.hitVec.x - this.posX);
                    this.motionY = (float)(var4.hitVec.y - this.posY);
                    this.motionZ = (float)(var4.hitVec.z - this.posZ);
                    float var20 = net.minecraft.util.math.MathHelper.sqrt_double((double)(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ));
                    this.posX -= this.motionX / (double)var20 * 0.05;
                    this.posY -= this.motionY / (double)var20 * 0.05;
                    this.posZ -= this.motionZ / (double)var20 * 0.05;
                    this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.bowhit")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f)));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);
                }
            }
            if (this.getIsCritical()) {
                for (var9 = 0; var9 < 4; ++var9) {
                    this.world.spawnParticle("crit", this.posX + this.motionX * (double)var9 / 4.0, this.posY + this.motionY * (double)var9 / 4.0, this.posZ + this.motionZ * (double)var9 / 4.0, -this.motionX, -this.motionY + 0.2, -this.motionZ);
                }
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float var20 = net.minecraft.util.math.MathHelper.sqrt_double((double)(this.motionX * this.motionX + this.motionZ * this.motionZ));
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.rotationPitch = (float)(Math.atan2(this.motionY, var20) * 180.0 / Math.PI);
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f;
            }
            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
            float var22 = 0.99f;
            var11 = 0.05f;
            if (this.isInWater()) {
                for (int var25 = 0; var25 < 4; ++var25) {
                    float var26 = 0.25f;
                    this.world.spawnParticle("bubble", this.posX - this.motionX * (double)var26, this.posY - this.motionY * (double)var26, this.posZ - this.motionZ * (double)var26, this.motionX, this.motionY, this.motionZ);
                }
                var22 = 0.8f;
            }
            this.motionX *= (double)var22;
            this.motionY *= (double)var22;
            this.motionZ *= (double)var22;
            this.motionY -= (double)var11;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }

    public void setKnockbackStrength(int par1) {
        this.knockbackStrength = par1;
    }

    public void setDamage(double par1) {
    }

    public double getDamage() {
        return 100.0;
    }
}

