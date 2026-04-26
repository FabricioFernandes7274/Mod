/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class LaserBall
extends EntityThrowable {
    private float my_rotation = 0.0f;
    private int my_index = 81;
    private int is_special = 0;
    private int is_iceball = 0;
    private int is_acid = 0;
    private int is_irukandji = 0;
    private int ticksalive = 0;

    public LaserBall(World par1World) {
        super(par1World);
    }

    public LaserBall(World par1World, int par2) {
        super(par1World);
    }

    public LaserBall(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }

    public LaserBall(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
    }

    public LaserBall(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    public int getLaserBallIndex() {
        return this.my_index;
    }

    public void setSpecial() {
        this.is_special = 1;
    }

    public void setIceBall() {
        this.is_iceball = 1;
    }

    public void setAcid() {
        this.is_acid = 1;
    }

    public void setIrukandji() {
        this.is_irukandji = 1;
        this.is_acid = 1;
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (par1RayTraceResult.entityHit != null) {
            Dragon d;
            float var2 = 16.0f;
            if (this.is_irukandji != 0) {
                par1RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), 100.0f);
                this.setDead();
                return;
            }
            if (this.is_acid != 0) {
                if (par1RayTraceResult.entityHit instanceof TrooperBug) {
                    this.setDead();
                    return;
                }
                if (par1RayTraceResult.entityHit instanceof SpitBug) {
                    this.setDead();
                    return;
                }
            }
            if (this.is_iceball == 0 && this.is_acid == 0) {
                if (par1RayTraceResult.entityHit instanceof Robot2) {
                    this.setDead();
                    return;
                }
                if (par1RayTraceResult.entityHit instanceof Robot3) {
                    this.setDead();
                    return;
                }
                if (par1RayTraceResult.entityHit instanceof Robot4) {
                    this.setDead();
                    return;
                }
                if (par1RayTraceResult.entityHit instanceof Robot5) {
                    this.setDead();
                    return;
                }
                if (par1RayTraceResult.entityHit instanceof GiantRobot) {
                    this.setDead();
                    return;
                }
            }
            if (par1RayTraceResult.entityHit instanceof Dragon && this.is_acid == 0) {
                d = (Dragon)par1RayTraceResult.entityHit;
                if (d.getPassengers() != null) {
                    this.setDead();
                    return;
                }
                if (d.getDragonType() != 0 && this.is_iceball != 0) {
                    this.setDead();
                    return;
                }
            }
            if (par1RayTraceResult.entityHit instanceof net.minecraft.entity.player.EntityPlayer && this.is_acid == 0) {
                d = (net.minecraft.entity.player.EntityPlayer)par1RayTraceResult.entityHit;
                if (d.getRidingEntity() != null) {
                    this.setDead();
                    return;
                }
            }
            par1RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), var2);
            if (this.is_iceball == 0) {
                par1RayTraceResult.entityHit.setFire(1);
            }
        } else if (this.is_irukandji != 0 && !this.world.isRemote) {
            this.dropItem(OreSpawnMain.MyIrukandji, 1);
        }
        if (this.is_acid == 0) {
            int mx = 10;
            if (this.is_special != 0) {
                mx = 20;
            }
            for (int var3 = 0; var3 < mx; ++var3) {
                this.world.spawnParticle("smoke", this.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posY + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posZ + (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
                this.world.spawnParticle("largesmoke", this.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posY + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posZ + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
                this.world.spawnParticle("fireworksSpark", this.posX, this.posY, this.posZ, this.world.rand.nextGaussian(), this.world.rand.nextGaussian(), this.world.rand.nextGaussian());
            }
            this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.explode", 0.5f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5f);
            if (!(this.world.isRemote || this.is_special == 0 && this.is_iceball == 0)) {
                this.world.createExplosion((Entity)this, this.posX, this.posY, this.posZ, 3.0f, this.world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            }
        }
        this.setDead();
    }

    public void onUpdate() {
        ++this.ticksalive;
        if (this.ticksalive > 200) {
            this.setDead();
            return;
        }
        super.onUpdate();
        this.my_rotation += 50.0f;
        while (this.my_rotation > 360.0f) {
            this.my_rotation -= 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
        if (this.is_acid != 0) {
            return;
        }
        int mx = 4;
        if (this.is_special != 0) {
            mx = 10;
        }
        if (this.is_iceball != 0 && this.is_special == 0) {
            mx = 2;
        }
        for (int i = 0; i < mx; ++i) {
            this.world.spawnParticle("fireworksSpark", this.posX, this.posY, this.posZ, this.world.rand.nextGaussian() / 2.0, this.world.rand.nextGaussian() / 2.0, this.world.rand.nextGaussian() / 2.0);
            if (this.is_iceball != 0) continue;
            this.world.spawnParticle("reddust", this.posX, this.posY, this.posZ, this.world.rand.nextGaussian() / 10.0, this.world.rand.nextGaussian() / 10.0, this.world.rand.nextGaussian() / 10.0);
        }
    }
}

