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
import net.minecraft.world.0f;

    public Shoes(World par1World) {
        super(par1World);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.dataManager.register(20, (Object)this.ShoeId);
    }

    public Shoes(World par1World, int par2) {
        super(par1World);
        this.ShoeId = par2;
        this.dataManager.register(20, (Object)this.ShoeId);
    }

    public Shoes(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.dataManager.register(20, (Object)this.ShoeId);
    }

    public Shoes(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
        this.ShoeId = par3;
        this.dataManager.register(20, (Object)this.ShoeId);
    }

    public Shoes(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.dataManager.register(20, (Object)this.ShoeId);
    }

    public int getShoeId() {
        return this.dataManager.get(20);
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (par1RayTraceResult.entityHit != null) {
            float var2 = 2.0f;
            if (this.getShoeId() == 6) {
                var2 = 6.0f;
            }
            if (par1RayTraceResult.entityHit instanceof EntityCreeper) {
                var2 += 4.0f;
            }
            if (par1RayTraceResult.entityHit instanceof Girlfriend) {
                var2 = 1.0f;
            }
            if (par1RayTraceResult.entityHit instanceof Boyfriend) {
                var2 = 1.0f;
            }
            if (par1RayTraceResult.entityHit instanceof net.minecraft.entity.player.EntityPlayer) {
                var2 = 0.0f;
            }
            if (OreSpawnMain.valentines_day != 0) {
                var2 = 10.0f;
            }
            par1RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), var2);
        }
        for (int var3 = 0; var3 < 4; ++var3) {
            this.world.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            this.world.spawnParticle("reddust", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
        }
        if (!this.world.isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.my_rotation += 20.0f;
        while (this.my_rotation > 360.0f) {
            this.my_rotation -= 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
    }
}

