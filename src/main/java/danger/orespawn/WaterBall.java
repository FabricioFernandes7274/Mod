/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WaterBall extends EntityMob {
    private int my_index = 49;

    public WaterBall(World worldIn) {
        super(worldIn);
    }

    public WaterBall(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(worldIn, par2EntityLiving);
    }

    public WaterBall(World world, double d, double e, double f) {
        super(world, d, e, f);
    }

    public int getWaterBallIndex() {
        return this.my_index;
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (par1RayTraceResult.entityHit != null) {
            Dragon d;
            float var2 = 2.0f;
            if (par1RayTraceResult.entityHit instanceof EntityCreeper) {
                var2 = 5.0f;
            }
            if (par1RayTraceResult.entityHit instanceof WaterDragon) {
                return;
            }
            if (par1RayTraceResult.entityHit instanceof AttackSquid) {
                return;
            }
            if (par1RayTraceResult.entityHit instanceof Dragon && (d = (Dragon)par1RayTraceResult.entityHit).getDragonType() != 0) {
                return;
            }
            if (par1RayTraceResult.entityHit instanceof net.minecraft.entity.player.EntityPlayer) {
                d = (net.minecraft.entity.player.EntityPlayer)par1RayTraceResult.entityHit;
                if (d.getRidingEntity() != null) {
                    return;
                }
            }
            par1RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, null), var2);
            if (this.getEntityWorld().rand.nextInt(10) == 1) {
                par1RayTraceResult.entityHit.dropItem(OreSpawnMain.MyWaterBall, 1);
            }
            par1RayTraceResult.entityHit.extinguish();
        }
        for (int var3 = 0; var3 < 8; ++var3) {
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_BUBBLE, this.posX + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posY + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posZ + (double)this.getEntityWorld().rand.nextFloat(), 0.0, 0.0, 0.0);
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_SPLASH, this.posX + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posY + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), this.posZ + (double)this.getEntityWorld().rand.nextFloat() - (double)this.getEntityWorld().rand.nextFloat(), 0.0, 0.0, 0.0);
        }
        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.splash")), 0.5f, 1.0f + (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.5f));
        if (!this.getEntityWorld().isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.my_rotation += 30.0f;
        while (this.my_rotation > 360.0f) {
            this.my_rotation -= 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
        this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_SPLASH, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
    }
}


}