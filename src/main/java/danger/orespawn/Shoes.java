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


public class Shoes extends EntityMob {
    public Shoes(World worldIn) {
        super(worldIn);
        this.ShoeId = this.getEntityWorld().rand.nextInt(4) + 2;
//         this.dataManager.register(20, (Object)this.ShoeId);
    }

    public Shoes(World worldIn, int par2) {
        super(worldIn);
        this.ShoeId = par2;
//         this.dataManager.register(20, (Object)this.ShoeId);
    }

    public Shoes(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(worldIn, par2EntityLiving);
        this.ShoeId = this.getEntityWorld().rand.nextInt(4) + 2;
//         this.dataManager.register(20, (Object)this.ShoeId);
    }

    public Shoes(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(worldIn, par2EntityLiving);
        this.ShoeId = par3;
//         this.dataManager.register(20, (Object)this.ShoeId);
    }

    public Shoes(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
        this.ShoeId = this.getEntityWorld().rand.nextInt(4) + 2;
//         this.dataManager.register(20, (Object)this.ShoeId);
    }

    public int getShoeId() {
        return 0 /* this.dataManager.get(20) */;
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
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
        }
        if (!this.getEntityWorld().isRemote) {
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


}