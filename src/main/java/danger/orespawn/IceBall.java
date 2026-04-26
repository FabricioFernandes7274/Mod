/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class IceBall
extends LaserBall {
    private int my_index = 84;
    private int icemaker = 0;

    public IceBall(World par1World) {
        super(par1World);
        super.setIceBall();
    }

    public IceBall(World par1World, int par2) {
        super(par1World);
        super.setIceBall();
    }

    public IceBall(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
        super.setIceBall();
    }

    public IceBall(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
        super.setIceBall();
    }

    public IceBall(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
        super.setIceBall();
    }

    public int getIceBallIndex() {
        return this.my_index;
    }

    public void setIceMaker(int i) {
        this.icemaker = i;
    }

    @Override
    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (par1RayTraceResult.entityHit != null && MyUtils.isRoyalty(par1RayTraceResult.entityHit)) {
            this.setDead();
            return;
        }
        super.onImpact(par1RayTraceResult);
        if (this.icemaker != 0) {
            for (int i = 0; i < 5; ++i) {
                int x = this.world.rand.nextInt(4);
                if (this.world.rand.nextInt(2) == 1) {
                    x = -x;
                }
                int y = this.world.rand.nextInt(4);
                if (this.world.rand.nextInt(2) == 1) {
                    y = -y;
                }
                int z = this.world.rand.nextInt(4);
                if (this.world.rand.nextInt(2) == 1) {
                    z = -z;
                }
                x = (int)((double)x + par1RayTraceResult.hitVec.x);
                y = (int)((double)y + par1RayTraceResult.hitVec.y);
                z = (int)((double)z + par1RayTraceResult.hitVec.z);
                this.world.setBlock(x, y, z, Blocks.ICE);
            }
        }
        this.setDead();
    }
}

