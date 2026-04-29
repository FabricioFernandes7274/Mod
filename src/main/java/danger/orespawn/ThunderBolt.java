/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.effect.EntityLightningBolt
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ThunderBolt
extends EntityThrowable {
    public ThunderBolt(World worldIn) {
        super(worldIn);
    }

    public ThunderBolt(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(worldIn, par2EntityLiving);
    }

    public ThunderBolt(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (par1RayTraceResult.entityHit != null) {
            float var2 = 40.0f;
            if (MyUtils.isRoyalty(par1RayTraceResult.entityHit)) {
                this.setDead();
                return;
            }
            par1RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), var2 / 2.0f);
            par1RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this.getThrower()), var2 / 2.0f);
            par1RayTraceResult.entityHit.setFire(1);
        }
        int mx = 20;
        for (int var3 = 0; var3 < mx; ++var3) {
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posY + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posZ + (double)this.getEntityWorld().rand.nextFloat(), 0.0, 0.0, 0.0);
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, this.posX + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posY + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posZ + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), 0.0, 0.0, 0.0);
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, this.getEntityWorld().rand.nextGaussian(), this.getEntityWorld().rand.nextGaussian(), this.getEntityWorld().rand.nextGaussian());
        }
        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.explode")), net.minecraft.util.SoundCategory.NEUTRAL, 0.5f, 1.0f + (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.5f));
        if (!this.getEntityWorld().isRemote) {
            this.getEntityWorld().createExplosion((Entity)this, this.posX, this.posY, this.posZ, 3.0f, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
        }
        this.getEntityWorld().addWeatherEffect((Entity)new EntityLightningBolt(this.getEntityWorld(), this.posX, this.posY + 1.0, this.posZ));
        this.setDead();
    }

    public void onUpdate() {
        super.onUpdate();
        int mx = 4;
        for (int i = 0; i < mx; ++i) {
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, this.getEntityWorld().rand.nextGaussian() / 10.0, this.getEntityWorld().rand.nextGaussian() / 10.0, this.getEntityWorld().rand.nextGaussian() / 10.0);
        }
    }
}

