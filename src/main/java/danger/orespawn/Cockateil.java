/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Cockateil
extends EntityAnimal {
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;
    public int birdtype;
    private boolean killedByPlayer = false;
    private static final ResourceLocation texture1 = new net.minecraft.util.ResourceLocation("orespawn", "Bird1.png");
    private static final ResourceLocation texture2 = new net.minecraft.util.ResourceLocation("orespawn", "Bird2.png");
    private static final ResourceLocation texture3 = new net.minecraft.util.ResourceLocation("orespawn", "Bird3.png");
    private static final ResourceLocation texture4 = new net.minecraft.util.ResourceLocation("orespawn", "Bird4.png");
    private static final ResourceLocation texture5 = new net.minecraft.util.ResourceLocation("orespawn", "Bird5.png");
    private static final ResourceLocation texture6 = new net.minecraft.util.ResourceLocation("orespawn", "Bird6.png");
    private int stuck_count = 0;
    private int lastX = 0;
    private int lastZ = 0;
    private int flyup = 0;

    public Cockateil(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.5f);
        ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.experienceValue = 2;
        this.isImmuneToFire = false;
        //this.fireResistance = 2;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.33f);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
    }

    public ResourceLocation getTexture() {
        this.birdtype = this.getBirdType();
        switch (this.birdtype) {
            case 0: {
                return texture1;
            }
            case 1: {
                return texture2;
            }
            case 2: {
                return texture3;
            }
            case 3: {
                return texture4;
            }
            case 4: {
                return texture5;
            }
            case 5: {
                return texture6;
            }
        }
        return null;
    }

    protected void entityInit() {
        super.entityInit();
        this.birdtype = this.getEntityWorld().rand.nextInt(6);
//         this.dataManager.register(22, (Object)this.birdtype);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int getBirdType() {
        return 0 /* this.dataManager.get(22) */;
    }

    public void setBirdType(int par1) {
//         this.dataManager.set(22, (Object)par1);
    }

    protected float getSoundVolume() {
        return 0.55f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected String getLivingSound() {
        if (this.getEntityWorld().isDaytime() && !this.getEntityWorld().isRaining()) {
            return "orespawn:birds";
        }
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    public boolean canBePushed() {
        return true;
    }

    public int mygetMaxHealth() {
        return 2;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Entity e = par1DamageSource.getTrueSource();
        if (e != null && e instanceof net.minecraft.entity.player.EntityPlayer) {
            this.killedByPlayer = true;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        } else {
            this.motionY = this.posY < (double)this.currentFlightTarget.getY() ? (this.motionY *= 0.7) : (this.motionY *= 0.5);
        }
    }

    public int getAttackStrength(Entity par1Entity) {
        return 1;
    }

    public void setFlyUp() {
        this.flyup = 2;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.getEntityWorld().rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 35;
        int stayup = 0;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.getEntityWorld().provider.getDimension() == OreSpawnMain.DimensionID4) {
            stayup = 2;
        }
        if (this.lastX == (int)this.posX && this.lastZ == (int)this.posZ) {
            ++this.stuck_count;
        } else {
            this.stuck_count = 0;
            this.lastX = (int)this.posX;
            this.lastZ = (int)this.posZ;
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.stuck_count > 40 || this.getEntityWorld().rand.nextInt(250) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 4.1f) {
            Block bid = Blocks.STONE;
            this.stuck_count = 0;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.getEntityWorld().rand.nextInt(8) + 5 - this.flyup * 2;
                xdir = this.getEntityWorld().rand.nextInt(8) + 5 - this.flyup * 2;
                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }
                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + this.getEntityWorld().rand.nextInt(9 + stayup) - 5 + this.flyup, (int)this.posZ + zdir);
                bid = this.getEntityWorld().getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.3 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.3 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.3 - this.motionX) * 0.25;
        this.motionY += (Math.signum(var3) * 0.699999 - this.motionY) * 0.200000001;
        this.motionZ += (Math.signum(var5) * 0.3 - this.motionZ) * 0.25;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.8f;
        this.rotationYaw += var8 / 3.0f;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean getCanSpawnHere() {
        if (!this.getEntityWorld().isDaytime()) {
            return false;
        }
        if (this.getEntityWorld().provider.getDimension() == OreSpawnMain.DimensionID4) {
            return true;
        }
        return !(this.posY < 50.0);
    }

    protected Item getDropItem() {
        this.birdtype = this.getBirdType();
        if (this.birdtype == 5 && this.killedByPlayer && this.getEntityWorld().rand.nextInt(3) == 1) {
            return OreSpawnMain.MyRuby;
        }
        return Items.FEATHER;
    }

    public void initCreature() {
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("BirdType", this.getBirdType());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.birdtype = par1NBTTagCompound.getInteger("BirdType");
        this.setBirdType(this.birdtype);
    }
}

