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
import net.minecraft.world.0f;
    private int my_index = 65;

    public InkSack(World par1World) {
        super(par1World);
    }

    public InkSack(World par1World, int par2) {
        super(par1World);
    }

    public InkSack(World par1World, EntityLiving par2EntityLiving) {
        super(par1World, (net.minecraft.entity.EntityLivingBase)par2EntityLiving);
    }

    public InkSack(World par1World, EntityLiving par2EntityLiving, int par3) {
        super(par1World, (net.minecraft.entity.EntityLivingBase)par2EntityLiving);
    }

    public InkSack(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
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
            if (par1RayTraceResult.entityHit instanceof net.minecraft.entity.EntityLivingBase && this.world.rand.nextInt(2) == 0) {
                ((net.minecraft.entity.EntityLivingBase)par1RayTraceResult.entityHit).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100 + 50 * this.world.rand.nextInt(8), 0));
            }
        }
        for (int var3 = 0; var3 < 4; ++var3) {
            this.world.spawnParticle("smoke", this.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posY + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posZ + (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
        }
        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.splash")), net.minecraft.util.SoundCategory.NEUTRAL, 0.5f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5f));
        if (!this.world.isRemote) {
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

