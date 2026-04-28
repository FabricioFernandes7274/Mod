/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.0f;
    private int my_index = 50;

    public SunspotUrchin(World par1World) {
        super(par1World);
    }

    public SunspotUrchin(World par1World, int par2) {
        super(par1World);
    }

    public SunspotUrchin(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }

    public SunspotUrchin(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
    }

    public SunspotUrchin(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    public int getUrchinIndex() {
        return this.my_index;
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (par1RayTraceResult.entityHit != null) {
            float var2 = 3.0f;
            if (par1RayTraceResult.entityHit instanceof EntityCreeper) {
                var2 = 6.0f;
            }
            if (!(par1RayTraceResult.entityHit instanceof net.minecraft.entity.player.EntityPlayer)) {
                par1RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), var2);
                if (!par1RayTraceResult.entityHit.isImmuneToFire()) {
                    par1RayTraceResult.entityHit.setFire(5);
                }
            }
        } else {
            int i = par1RayTraceResult.getBlockPos().getX();
            int j = par1RayTraceResult.getBlockPos().getY();
            int k = par1RayTraceResult.getBlockPos().getZ();
            switch (par1RayTraceResult.sideHit.getIndex()) {
                case 0: {
                    --j;
                    break;
                }
                case 1: {
                    ++j;
                    break;
                }
                case 2: {
                    --k;
                    break;
                }
                case 3: {
                    ++k;
                    break;
                }
                case 4: {
                    --i;
                    break;
                }
                case 5: {
                    ++i;
                }
            }
            if (this.world.isAirBlock(new net.minecraft.util.math.BlockPos(i, j, k))) {
                this.world.setBlock(i, j, k, (Block)Blocks.FIRE);
            }
        }
        for (int var3 = 0; var3 < 5; ++var3) {
            this.world.spawnParticle("smoke", this.posX, this.posY, this.posZ, (double)this.world.rand.nextFloat(), (double)this.world.rand.nextFloat(), (double)this.world.rand.nextFloat());
            this.world.spawnParticle("reddust", this.posX, this.posY, this.posZ, (double)this.world.rand.nextFloat(), (double)this.world.rand.nextFloat(), (double)this.world.rand.nextFloat());
        }
        if (!this.world.isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.setFire(1);
        this.my_rotation += 30.0f;
        while (this.my_rotation > 360.0f) {
            this.my_rotation -= 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
        this.world.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
    }
}

