/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLunaMoth
extends EntityButterfly {
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;
    public int moth_type = OreSpawnMain.OreSpawnRand.nextInt(4);
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public EntityLunaMoth(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.5f);
        ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.1f);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int d;
        Block bid;
        int j;
        int i;
        int found = 0;
        for (i = -dy; i <= dy; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + dx, y + i, z + j)).getBlock();
                if ((bid == Blocks.TORCH || bid == OreSpawnMain.ExtremeTorch) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.TORCH && bid != OreSpawnMain.ExtremeTorch || (d = dx * dx + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x - dx;
                this.ty = y + i;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + dy, z + j)).getBlock();
                if ((bid == Blocks.TORCH || bid == OreSpawnMain.ExtremeTorch) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.TORCH && bid != OreSpawnMain.ExtremeTorch || (d = dy * dy + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y - dy;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dy; j <= dy; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + dz)).getBlock();
                if ((bid == Blocks.TORCH || bid == OreSpawnMain.ExtremeTorch) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.TORCH && bid != OreSpawnMain.ExtremeTorch || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        return found != 0;
    }

    @Override
    protected void updateAITasks() {
        int keep_trying = 25;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.getEntityWorld().rand.nextInt(100) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + this.getEntityWorld().rand.nextInt(10) - this.getEntityWorld().rand.nextInt(10), (int)this.posY + this.getEntityWorld().rand.nextInt(6) - 2, (int)this.posZ + this.getEntityWorld().rand.nextInt(10) - this.getEntityWorld().rand.nextInt(10));
                bid = this.getEntityWorld().getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                --keep_trying;
            }
        } else if (!this.getEntityWorld().isDaytime() && this.getEntityWorld().rand.nextInt(10) == 0) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 2; i < 15 && !this.scan_it((int)this.posX, (int)this.posY, (int)this.posZ, i, i, i); ++i) {
                if (i < 6) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos(this.tx, this.ty + 1, this.tz);
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * (double)0.1f;
        this.motionY += (Math.signum(var3) * 0.68 - this.motionY) * (double)0.1f;
        this.motionZ += (Math.signum(var5) * 0.5 - this.motionZ) * (double)0.1f;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.75f;
        this.rotationYaw += var8;
    }

    @Override
    protected void fall(float par1) {
    }

    @Override
    protected void updateFallState(double par1, boolean par3) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock(;
        if (bid != Blocks.AIR) {
            return false;
        }
        if (this.getEntityWorld().isDaytime()) {
            return false;
        }
        if (this.getEntityWorld().provider.getDimension() == OreSpawnMain.DimensionID4) {
            return true;
        }
        return !(this.posY < 50.0);
    }

    @Override
    public void initCreature() {
    }
}

