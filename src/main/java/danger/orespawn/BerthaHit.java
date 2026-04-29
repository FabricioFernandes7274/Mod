/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BerthaHit
extends EntityThrowable {
    private int hit_type = 0;

    public BerthaHit(World worldIn) {
        super(worldIn);
    }

    public BerthaHit(World worldIn, int par2) {
        super(worldIn);
    }

    public BerthaHit(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(worldIn, par2EntityLiving);
        this.setSize(0.33f, 0.33f);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= (double)(net.minecraft.util.math.MathHelper.cos((float)(this.rotationYaw / 180.0f * (float)Math.PI)) * 0.16f);
        this.posY -= 0.1;
        this.posZ -= (double)(net.minecraft.util.math.MathHelper.sin((float)(this.rotationYaw / 180.0f * (float)Math.PI)) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        float f = 0.4f;
        this.motionX = -net.minecraft.util.math.MathHelper.sin((float)(this.rotationYaw / 180.0f * (float)Math.PI)) * net.minecraft.util.math.MathHelper.cos((float)(this.rotationPitch / 180.0f * (float)Math.PI)) * f;
        this.motionZ = net.minecraft.util.math.MathHelper.cos((float)(this.rotationYaw / 180.0f * (float)Math.PI)) * net.minecraft.util.math.MathHelper.cos((float)(this.rotationPitch / 180.0f * (float)Math.PI)) * f;
        this.motionY = -net.minecraft.util.math.MathHelper.sin((float)((this.rotationPitch + this.func_70183_g()) / 180.0f * (float)Math.PI)) * f;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 0.1f);
    }

    public BerthaHit(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(worldIn, par2EntityLiving);
    }

    public BerthaHit(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
    }

    public void setHitType(int i) {
        this.hit_type = i;
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (this.isDead) {
            return;
        }
        if (par1RayTraceResult.entityHit != null && null != null) {
            float f3;
            double inair;
            EntityTameable t;
            Entity e = par1RayTraceResult.entityHit;
            if (OreSpawnMain.big_bertha_pvp == 0 && e instanceof net.minecraft.entity.player.EntityPlayer || e instanceof Girlfriend || e instanceof Boyfriend) {
                this.setDead();
                return;
            }
            if (OreSpawnMain.big_bertha_pvp == 0 && e instanceof EntityTameable && (t = (EntityTameable)e).isTamed()) {
                this.setDead();
                return;
            }
            if (this.hit_type == 0 && this.getDistanceSq(null) < 81.0 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), (float)OreSpawnMain.bertha_stats.damage);
                e.setFire(10);
                double ks = 2.25;
                inair = 0.35;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.hit_type == 2 && this.getDistanceSq(null) < 101.0 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), (float)OreSpawnMain.royal_stats.damage);
                double ks = 1.5;
                inair = 0.25;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.hit_type == 3 && this.getDistanceSq(null) < 64.0 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), (float)OreSpawnMain.hammy_stats.damage);
                double ks = 1.25;
                inair = 0.65;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (!this.getEntityWorld().isRemote && this.hit_type == 3 && this.getDistanceSq(null) < 64.0) {
                    this.getEntityWorld().newExplosion((Entity)null, this.posX, this.posY, this.posZ, 1.5f, true, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
                }
            }
        } else if (!this.getEntityWorld().isRemote && this.hit_type == 3 && this.getDistanceSq(null) < 64.0) {
            this.getEntityWorld().newExplosion((Entity)null, this.posX, this.posY, this.posZ, 2.1f, true, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
        }
        this.setDead();
    }

    public void onUpdate() {
        super.onUpdate();
    }
}

