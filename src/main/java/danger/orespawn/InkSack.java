/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class InkSack extends EntityMob {
    private int my_index = 65;

    public InkSack(World worldIn) {
        super(worldIn);
    }

    public InkSack(World worldIn, int par2) {
        super(worldIn);
    }

    public InkSack(World worldIn, EntityLiving par2EntityLiving) {
        super(worldIn, (net.minecraft.entity.EntityLivingBase)par2EntityLiving);
    }

    public InkSack(World worldIn, EntityLiving par2EntityLiving, int par3) {
        super(worldIn, (net.minecraft.entity.EntityLivingBase)par2EntityLiving);
    }

    public InkSack(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
    }

    public int getInkSackIndex() {
        return this.my_index;
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (par1RayTraceResult.entityHit != null) {
            float var2 = 1.0f;
            if (par1RayTraceResult.entityHit instanceof EntityCreeper) {
                var2 = 4.0f;
            }
            if (par1RayTraceResult.entityHit instanceof WaterDragon) {
                return;
            }
            if (par1RayTraceResult.entityHit instanceof AttackSquid) {
                return;
            }
            par1RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), var2);
            if (par1RayTraceResult.entityHit instanceof net.minecraft.entity.EntityLivingBase && this.getEntityWorld().rand.nextInt(2) == 0) {
                ((net.minecraft.entity.EntityLivingBase)par1RayTraceResult.entityHit).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100 + 50 * this.getEntityWorld().rand.nextInt(8), 0));
            }
        }
        for (int var3 = 0; var3 < 4; ++var3) {
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posY + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posZ + (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
        }
        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.splash")), net.minecraft.util.SoundCategory.NEUTRAL, 0.5f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5f));
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
    }
}


}