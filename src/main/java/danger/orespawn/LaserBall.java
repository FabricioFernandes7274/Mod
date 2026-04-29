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

public class LaserBall extends EntityMob {
    private int my_index = 81;
    private int is_special = 0;
    private int is_iceball = 0;
    private int is_acid = 0;
    private int is_irukandji = 0;
    private int ticksalive = 0;

    public LaserBall(World worldIn) {
        super(worldIn);
    }

    public LaserBall(World worldIn, int par2) {
        super(worldIn);
    }

    public LaserBall(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(worldIn, par2EntityLiving);
    }

    public LaserBall(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(worldIn, par2EntityLiving);
    }

    public LaserBall(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
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
        } else if (this.is_irukandji != 0 && !this.getEntityWorld().isRemote) {
            this.dropItem(OreSpawnMain.MyIrukandji, 1);
        }
        if (this.is_acid == 0) {
            int mx = 10;
            if (this.is_special != 0) {
                mx = 20;
            }
            for (int var3 = 0; var3 < mx; ++var3) {
                this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posY + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posZ + (double)this.getEntityWorld().rand.nextFloat(), 0.0, 0.0, 0.0);
                this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, this.posX + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posY + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posZ + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), 0.0, 0.0, 0.0);
                this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, this.getEntityWorld().rand.nextGaussian(), this.getEntityWorld().rand.nextGaussian(), this.getEntityWorld().rand.nextGaussian());
            }
            this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.explode")), net.minecraft.util.SoundCategory.NEUTRAL, 0.5f, 1.0f + (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.5f));
            if (!(this.getEntityWorld().isRemote || this.is_special == 0 && this.is_iceball == 0)) {
                this.getEntityWorld().createExplosion((Entity)this, this.posX, this.posY, this.posZ, 3.0f, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
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
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, this.getEntityWorld().rand.nextGaussian() / 2.0, this.getEntityWorld().rand.nextGaussian() / 2.0, this.getEntityWorld().rand.nextGaussian() / 2.0);
            if (this.is_iceball != 0) continue;
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, this.getEntityWorld().rand.nextGaussian() / 10.0, this.getEntityWorld().rand.nextGaussian() / 10.0, this.getEntityWorld().rand.nextGaussian() / 10.0);
        }
    }
}


}