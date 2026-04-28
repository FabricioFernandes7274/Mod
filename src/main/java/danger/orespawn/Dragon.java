/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveIndoors
 *  net.minecraft.entity.ai.EntityAINearestAttackableTarget
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITempt
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntitySmallFireball
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.net.minecraft.util.text.TextComponentString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Dragon
extends EntityTameable {
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double boatYawHead;
    private int updateit = 1;
    private int color = 1;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private int wing_sound = 0;
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;
    private boolean target_in_sight = false;
    private int owner_flying = 0;
    private int flyaway = 0;
    private int stuck_count = 0;
    private int lastX = 0;
    private int lastZ = 0;
    private int unstick_timer = 0;
    private int fireballticker = 0;
    private float moveSpeed = 0.32f;
    private float deltasmooth = 0.0f;
    private int dragontype = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Dragon(World worldIn) {
        super(worldIn);
        this.setSize(1.5f, 1.25f);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 100;
        //this.fireResistance = 1000;
        this.isImmuneToFire = true;
        this.setSitting(false);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIFollowOwner(this, 1.1f, 12.0f, 2.0f));
        this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.25, Items.BEEF, false));
        this.tasks.addTask(3, (EntityAIBase)new MyEntityAIWander((EntityCreature)this, 0.75f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 9.0f));
        this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 0, true, false, IMob.targetEntitySelector));
        }
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.getPassengers() = null;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
    }

    public Dragon(World worldIn, double par2, double par4, double par6) {
        this(worldIn);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(35.0);
    }

    public boolean shouldRiderSit() {
        return true;
    }

    public int getTrackingRange() {
        return 128;
    }

    public int getUpdateFrequency() {
        return 10;
    }

    public boolean sendsVelocityUpdates() {
        return true;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)0);
        this.dataManager.register(21, (Object)0);
        this.dataManager.register(22, (Object)0);
        this.dataManager.register(24, (Object)1);
        this.setActivity(0);
        this.setAttacking(0);
        this.setTamed(false);
        if (this.renderdata == null) {
            this.renderdata = new RenderInfo();
        }
        this.renderdata.rf1 = 0.0f;
        this.renderdata.rf2 = 0.0f;
        this.renderdata.rf3 = 0.0f;
        this.renderdata.rf4 = 0.0f;
        this.renderdata.ri1 = 0;
        this.renderdata.ri2 = 0;
        this.renderdata.ri3 = 0;
        this.renderdata.ri4 = 0;
    }

    public int mygetMaxHealth() {
        return 200;
    }

    public int getDragonHealth() {
        return (int)this.getHealth();
    }

    public RenderInfo getRenderInfo() {
        return this.renderdata;
    }

    public void setRenderInfo(RenderInfo r) {
        this.renderdata.rf1 = r.rf1;
        this.renderdata.rf2 = r.rf2;
        this.renderdata.rf3 = r.rf3;
        this.renderdata.rf4 = r.rf4;
        this.renderdata.ri1 = r.ri1;
        this.renderdata.ri2 = r.ri2;
        this.renderdata.ri3 = r.ri3;
        this.renderdata.ri4 = r.ri4;
    }

    public int getTotalArmorValue() {
        return 14;
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
    }

    public boolean isAIEnabled() {
        return true;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int d;
        Block bid;
        int j;
        int i;
        int found = 0;
        for (i = -dy; i <= dy; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + dx, y + i, z + j)).getBlock();
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dx * dx + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x - dx;
                this.ty = y + i;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + dy, z + j)).getBlock();
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dy * dy + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y - dy;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dy; j <= dy; ++j) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + dz)).getBlock();
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        return found != 0;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public String getLivingSound() {
        if (this.isSitting()) {
            return null;
        }
        if (this.getAttacking() == 1 && this.getPassengers() == null) {
            return "orespawn:roar";
        }
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    protected float getSoundVolume() {
        return 0.6f;
    }

    public float getSoundPitch() {
        return 0.75f;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 1.3;
    }

    protected Item getDropItem() {
        return Items.BEEF;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(5) - (double)OreSpawnMain.OreSpawnRand.nextInt(5), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(5) - (double)OreSpawnMain.OreSpawnRand.nextInt(5), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int i = 1 + this.world.rand.nextInt(6);
        for (int var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.BEEF, 1);
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        double ks = 1.75;
        double inair = 0.1;
        float iskraken = 1.0f;
        if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
            if (par1Entity instanceof Kraken) {
                iskraken = 2.0f;
            }
            par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), iskraken * 35.0f);
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            if (par1Entity.isDead() || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                inair *= 2.0;
            }
            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        Entity e = null;
        if (this.hurt_timer > 0) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("cactus")) {
            return ret;
        }
        if (par1DamageSource.getDamageType().equals("inFire")) {
            return ret;
        }
        if (par1DamageSource.getDamageType().equals("onFire")) {
            return ret;
        }
        if (par1DamageSource.getDamageType().equals("lava")) {
            return ret;
        }
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return ret;
        }
        this.setSitting(false);
        this.setActivity(1);
        e = par1DamageSource.getEntity();
        if (e != null && e instanceof BetterFireball && this.dragontype == 0) {
            e.setDead();
            return ret;
        }
        if (e != null && e instanceof IceBall && this.dragontype != 0) {
            e.setDead();
            return ret;
        }
        if (e != null && e instanceof WaterBall && this.dragontype != 0) {
            e.setDead();
            return ret;
        }
        if (e != null && e instanceof EntitySmallFireball && this.dragontype == 0) {
            e.setDead();
            return ret;
        }
        if (e != null && e instanceof Dragon) {
            return false;
        }
        if (e != null && e instanceof Spyro) {
            return false;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        this.hurt_timer = 20;
        if (e != null && e instanceof net.minecraft.entity.EntityLivingBase) {
            if (this.isTamed() && e instanceof net.minecraft.entity.player.EntityPlayer) {
                return false;
            }
            this.setAttackTarget((net.minecraft.entity.EntityLivingBase)e);
            this.setTarget(e);
            this.getNavigator().tryMoveToEntityLiving((Entity)((net.minecraft.entity.EntityLivingBase)e), 1.2);
            ret = true;
        }
        return ret;
    }

    public void updateAITasks() {
        net.minecraft.entity.EntityLivingBase e = null;
        super.updateAITasks();
        if (!this.isSitting() && this.getActivity() == 0 && this.getPassengers() == null && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(10) == 1 && (e = this.findSomethingToAttack()) != null) {
            this.setActivity(1);
        }
    }

    public void always_do() {
        net.minecraft.entity.player.EntityPlayer p = null;
        if (this.world.rand.nextInt(250) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(2.0f);
        }
        if (this.isSitting()) {
            return;
        }
        this.owner_flying = 0;
        if (this.isTamed() && this.getOwner() != null && this.getPassengers() == null && !this.isSitting()) {
            p = (net.minecraft.entity.player.EntityPlayer)this.getOwner();
            if (p.capabilities.isFlying) {
                this.owner_flying = 1;
                this.setActivity(1);
            }
        }
        if (this.isTamed() && this.getOwner() != null && !this.isSitting() && this.getDistanceSq((Entity)(p = (net.minecraft.entity.player.EntityPlayer)this.getOwner())) > 400.0) {
            this.setActivity(1);
        }
        if (this.world.rand.nextInt(50) == 1 && !this.isSitting() && !this.target_in_sight && this.getPassengers() == null) {
            if (this.world.rand.nextInt(15) == 1) {
                this.setActivity(1);
            } else {
                this.setActivity(0);
            }
        }
        if (this.world.rand.nextInt(25) == 0 && !this.target_in_sight && this.getPassengers() == null) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 1; i < 11; ++i) {
                int j = i;
                if (j > 4) {
                    j = 4;
                }
                if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) break;
                if (i < 6) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.setActivity(0);
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)(this.ty - 1), (double)this.tz, 1.0);
                if (this.handleLavaMovement()) {
                    this.heal(1.0f);
                    this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("splash")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, this.world.rand.nextFloat() * 0.2f + 0.9f));
                }
            }
        }
    }

    public void fly_with_rider() {
        net.minecraft.entity.EntityLivingBase e = null;
        int freq = 7;
        if (this.isDead()) {
            return;
        }
        if (this.isSitting()) {
            return;
        }
        if (this.world.isRemote) {
            return;
        }
        if (this.world.rand.nextInt(freq) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            if (this.world.rand.nextInt(250) == 0) {
                this.setAttackTarget(null);
            }
            if ((e = this.getAttackTarget()) != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                this.setAttacking(1);
                if (this.getDistanceSq((Entity)e) < (double)((7.0f + e.width / 2.0f) * (7.0f + e.width / 2.0f))) {
                    this.attackEntityAsMob((Entity)e);
                }
                return;
            }
            this.setAttacking(0);
        }
    }

    protected void updateAITick() {
        if (this.getPassengers() != null) {
            return;
        }
        super.updateAITick();
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof LurkingTerror) {
            return false;
        }
        if (par1EntityLiving instanceof EnderReaper) {
            return false;
        }
        if (par1EntityLiving instanceof TerribleTerror) {
            return false;
        }
        if (par1EntityLiving instanceof LeafMonster) {
            return false;
        }
        if (par1EntityLiving instanceof CreepingHorror) {
            return false;
        }
        if (par1EntityLiving instanceof Triffid) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof Mothra) {
            return true;
        }
        if (par1EntityLiving instanceof Kraken) {
            return true;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            return false;
        }
        return false;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(20.0, 20.0, 20.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false)) continue;
            return var4;
        }
        return null;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean getCanSpawnHere() {
        Dragon target = null;
        if (!this.world.isDaytime()) {
            return false;
        }
        target = (Dragon)this.world.findNearestEntityWithinAABB(Dragon.class, this.boundingBox.expand(16.0, 6.0, 16.0), (Entity)this);
        if (target != null) {
            return false;
        }
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID4) {
            return true;
        }
        return !(this.posY < 50.0);
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        super.setPositionAndRotation2(par1, par3, par5, par7, par8, par9);
        this.boatPosRotationIncrements = par9;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
        this.boatYawHead = par7;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        super.setVelocity(par1, par3, par5);
    }

    public void onUpdate() {
        net.minecraft.entity.EntityLivingBase e = null;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.getActivity() == 1) {
            ++this.wing_sound;
            if (this.wing_sound > 20) {
                if (!this.world.isRemote) {
                    this.world.playSoundAtEntity((Entity)this, "orespawn:MothraWings", 0.5f, 1.0f);
                }
                this.wing_sound = 0;
            }
        }
        if (this.isInWater()) {
            this.motionY += 0.07;
        }
        if (this.world.isRemote) {
            return;
        }
        if (this.getActivity() == 0 && this.isTamed() && this.getOwner() != null && !this.isSitting() && this.getDistanceSq((Entity)(e = this.getOwner())) > 144.0) {
            this.setActivity(1);
        }
    }

    private void fly_without_rider() {
        Block bid;
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        boolean do_new = false;
        double ox = 0.0;
        double oy = 0.0;
        double oz = 0.0;
        boolean has_owner = false;
        net.minecraft.entity.EntityLivingBase e = null;
        double speed_factor = 0.5;
        double var1 = 0.0;
        double var3 = 0.0;
        double var5 = 0.0;
        double yoff = 1.25;
        double xzoff = 2.25;
        double gh = 1.25;
        double obstruction_factor = 0.0;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        EntitySmallFireball sf = null;
        BetterFireball bf = null;
        IceBall ib = null;
        WaterBall wb = null;
        boolean toofar = false;
        if (this.currentFlightTarget == null) {
            do_new = true;
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.isSitting()) {
            return;
        }
        if (this.getPassengers() != null) {
            return;
        }
        if (this.unstick_timer > 0) {
            --this.unstick_timer;
        }
        if (this.lastX == (int)this.posX && this.lastZ == (int)this.posZ) {
            ++this.stuck_count;
            if (this.stuck_count > 50) {
                this.stuck_count = 0;
                this.unstick_timer = 100;
                this.target_in_sight = false;
                this.setAttacking(0);
                do_new = true;
            }
        } else {
            this.stuck_count = 0;
            this.lastX = (int)this.posX;
            this.lastZ = (int)this.posZ;
        }
        this.motionY = this.posY < (double)this.currentFlightTarget.getY() + 2.0 ? (this.motionY *= 0.7) : (this.posY > (double)this.currentFlightTarget.getY() - 2.0 ? (this.motionY *= 0.5) : (this.motionY *= 0.61));
        if (this.world.rand.nextInt(300) == 1) {
            do_new = true;
        }
        if (this.isTamed() && this.getOwner() != null) {
            e = this.getOwner();
            has_owner = true;
            ox = e.posX;
            oy = e.posY;
            oz = e.posZ;
            if (this.getDistanceSq((Entity)e) > 144.0) {
                toofar = true;
                this.target_in_sight = false;
                this.setAttacking(0);
                this.flyaway = 0;
                do_new = true;
            }
        }
        if (this.flyaway > 0) {
            --this.flyaway;
        }
        if (!toofar && this.unstick_timer == 0 && this.flyaway == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(9) == 1) {
            e = this.findSomethingToAttack();
            if (e != null) {
                if (this.isTamed() && this.getHealth() / (float)this.mygetMaxHealth() < 0.25f) {
                    this.setActivity(1);
                    this.setAttacking(0);
                    this.target_in_sight = false;
                    do_new = false;
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)(this.posX + (this.posX - e.posX)), (int)(this.posY + 1.0), (int)(this.posZ + (this.posZ - e.posZ)));
                } else {
                    this.setActivity(1);
                    this.setAttacking(1);
                    this.target_in_sight = true;
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                    do_new = false;
                    if (this.getDistanceSq((Entity)e) < (double)((5.0f + e.width / 2.0f) * (5.0f + e.width / 2.0f))) {
                        this.attackEntityAsMob((Entity)e);
                        this.flyaway = 5 + this.world.rand.nextInt(10);
                        do_new = true;
                    } else if (this.getDistanceSq((Entity)e) < 256.0 && !this.isInWater() && this.getDragonFire() >= 1) {
                        float var9;
                        double var7;
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                        if (this.dragontype == 0) {
                            if (this.getDragonFire() == 1) {
                                sf = new EntitySmallFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, e.posX - cx, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - cz);
                                sf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                                sf.setPosition(cx, this.posY + yoff, cz);
                                this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                                this.world.spawnEntity((Entity)sf);
                            } else {
                                bf = new BetterFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, e.posX - cx, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - cz);
                                bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                                bf.setPosition(cx, this.posY + yoff, cz);
                                this.world.playSoundAtEntity((Entity)this, "random.fuse", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                                this.world.spawnEntity((Entity)bf);
                            }
                        } else if (this.getDragonFire() == 1) {
                            wb = new WaterBall(this.world, e.posX - this.posX, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - this.posZ);
                            wb.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
                            var3 = e.posX - wb.posX;
                            var5 = e.posY + 0.25 - wb.posY;
                            var7 = e.posZ - wb.posZ;
                            var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var7 * var7)) * 0.2f;
                            wb.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
                            this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                            this.world.spawnEntity((Entity)wb);
                        } else {
                            ib = new IceBall(this.world, e.posX - this.posX, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - this.posZ);
                            ib.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
                            ib.setSpecial();
                            ib.setIceBall();
                            var3 = e.posX - ib.posX;
                            var5 = e.posY + 0.25 - ib.posY;
                            var7 = e.posZ - ib.posZ;
                            var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var7 * var7)) * 0.2f;
                            ib.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
                            this.world.playSoundAtEntity((Entity)this, "random.fuse", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                            this.world.spawnEntity((Entity)ib);
                        }
                    }
                }
            } else {
                this.target_in_sight = false;
                this.flyaway = 0;
                this.setAttacking(0);
            }
        }
        if (this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.1f) {
            do_new = true;
        }
        if (do_new && !this.target_in_sight || do_new && this.flyaway != 0) {
            bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                int gox = (int)this.posX;
                int goy = (int)this.posY;
                int goz = (int)this.posZ;
                if (has_owner && this.unstick_timer == 0) {
                    gox = (int)ox;
                    goy = (int)oy;
                    goz = (int)oz;
                    if (this.owner_flying == 0) {
                        zdir = this.world.rand.nextInt(10) + 4;
                        xdir = this.world.rand.nextInt(10) + 4;
                    } else {
                        zdir = this.world.rand.nextInt(6);
                        xdir = this.world.rand.nextInt(6);
                    }
                } else {
                    zdir = this.world.rand.nextInt(10) + 16;
                    xdir = this.world.rand.nextInt(10) + 16;
                }
                if (this.world.rand.nextInt(2) == 1) {
                    zdir = -zdir;
                }
                if (this.world.rand.nextInt(2) == 1) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos(gox + xdir, goy + this.world.rand.nextInt(9 + this.owner_flying * 2) - 4, goz + zdir);
                bid = this.world.getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        }
        obstruction_factor = 0.0;
        int dist = 2;
        dist += (int)(velocity * 4.0);
        for (int k = 1; k < dist; ++k) {
            for (int i = 1; i < dist * 2; ++i) {
                double dz;
                double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)).getBlock()(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))));
                if (bid == Blocks.AIR) continue;
                obstruction_factor += 0.05;
            }
        }
        this.motionY += obstruction_factor * 0.05;
        this.posY += obstruction_factor * 0.05;
        speed_factor = 0.5;
        var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        if (this.owner_flying != 0) {
            speed_factor = 1.75;
            if (this.isTamed() && this.getOwner() != null && this.getDistanceSq((Entity)(e = this.getOwner())) > 49.0) {
                speed_factor = 3.5;
            }
        }
        this.motionX += (Math.signum(var1) - this.motionX) * 0.15 * speed_factor;
        this.motionY += (Math.signum(var3) - this.motionY) * 0.21 * speed_factor;
        this.motionZ += (Math.signum(var5) - this.motionZ) * 0.15 * speed_factor;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = (float)(0.75 * speed_factor);
        this.rotationYaw += var8 / 4.0f;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    public void onLivingUpdate() {
        List list = null;
        Entity listEntity = null;
        double d6 = this.rand.nextFloat() * 2.0f - 1.0f;
        double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.95;
        double gh = 1.0;
        double rt = 0.0;
        double pi = 3.1415926545;
        double deltav = 0.0;
        int dist = 2;
        BetterFireball bf = null;
        if (this.getActivity() == 0) {
            super.onLivingUpdate();
        } else if (this.isDead()) {
            super.onLivingUpdate();
            return;
        }
        if (this.isDead()) {
            return;
        }
        if (this.world.isRemote) {
            if (this.boatPosRotationIncrements > 0 && this.getActivity() != 0) {
                double d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                double d10 = net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.boatYaw - (double)this.rotationYaw));
                if (this.getPassengers() != null) {
                    d10 = net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.getPassengers().isEmpty() ? 0.0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw - (double)this.rotationYaw));
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.rotationYawHead = this.rotationYaw;
                --this.boatPosRotationIncrements;
            }
        } else {
            if (this.getActivity() != 0) {
                if (this.fireballticker > 0) {
                    --this.fireballticker;
                }
                if (this.getPassengers() != null) {
                    double rdv;
                    net.minecraft.entity.player.EntityPlayer pp = (net.minecraft.entity.player.EntityPlayer)this.getPassengers();
                    if (this.motionX < -2.0) {
                        this.motionX = -2.0;
                    }
                    if (this.motionX > 2.0) {
                        this.motionX = 2.0;
                    }
                    if (this.motionZ < -2.0) {
                        this.motionZ = -2.0;
                    }
                    if (this.motionZ > 2.0) {
                        this.motionZ = 2.0;
                    }
                    double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    gh = 1.25;
                    Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)).getBlock()this.posZ);
                    if (bid != Blocks.AIR) {
                        this.motionY += 0.03;
                        this.posY += 0.1;
                    } else {
                        this.motionY -= 0.018;
                    }
                    obstruction_factor = 0.0;
                    dist = 3;
                    dist += (int)(velocity * 7.0);
                    for (int k = 1; k < dist; ++k) {
                        for (int i = 1; i < dist * 2; ++i) {
                            double dz;
                            double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                            bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)).getBlock()(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))));
                            if (bid == Blocks.AIR) continue;
                            obstruction_factor += 0.05;
                        }
                    }
                    this.motionY += obstruction_factor * 0.07;
                    this.posY += obstruction_factor * 0.07;
                    if (this.motionY > 2.0) {
                        this.motionY = 2.0;
                    }
                    double d4 = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw;
                    d4 %= 360.0;
                    while (d4 < 0.0) {
                        d4 += 360.0;
                    }
                    double d5 = this.rotationYaw;
                    d5 %= 360.0;
                    while (d5 < 0.0) {
                        d5 += 360.0;
                    }
                    for (relative_g = (d4 - d5) % 180.0; relative_g < 0.0; relative_g += 180.0) {
                    }
                    if (relative_g > 90.0) {
                        relative_g -= 180.0;
                    }
                    if (velocity > 0.01) {
                        d4 = 1.85 - velocity;
                        if ((d4 = Math.abs(d4)) < 0.01) {
                            d4 = 0.01;
                        }
                        if (d4 > 0.9) {
                            d4 = 0.9;
                        }
                        this.rotationYaw = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw + (float)(relative_g * d4);
                    } else {
                        this.rotationYaw = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw;
                    }
                    relative_g = Math.abs(relative_g) * velocity;
                    if (relative_g > 50.0) {
                        relative_g = 0.0;
                    }
                    this.rotationPitch = 2.0f * (float)velocity;
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    double rr = Math.atan2(this.getPassengers().isEmpty() ? 0 : this.getPassengers().get(0).motionZ, this.getPassengers().isEmpty() ? 0 : this.getPassengers().get(0).motionX);
                    double rhm = Math.atan2(this.motionZ, this.motionX);
                    double rhdir = Math.toRadians((this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw + 90.0f) % 360.0f);
                    rt = 0.0;
                    pi = 3.1415926545;
                    deltav = 0.0;
                    float im = pp.moveForward;
                    if (OreSpawnMain.flyup_keystate != 0) {
                        this.motionY += 0.03;
                        this.motionY += velocity * 0.036;
                    }
                    if ((rdv = Math.abs(rhm - rhdir) % (pi * 2.0)) > pi) {
                        rdv -= pi * 2.0;
                    }
                    rdv = Math.abs(rdv);
                    if (Math.abs(newvelocity) < 0.01) {
                        rdv = 0.0;
                    }
                    if (rdv > 1.5) {
                        newvelocity = -newvelocity;
                    }
                    if (Math.abs(im) > 0.001f) {
                        if (im > 0.0f) {
                            deltav = 0.025;
                            if (max_speed > 1.0) {
                                deltav += 0.05;
                            }
                            if (this.deltasmooth < 0.0f) {
                                this.deltasmooth = 0.0f;
                            }
                            this.deltasmooth = (float)((double)this.deltasmooth + deltav / 10.0);
                            if ((double)this.deltasmooth > deltav) {
                                this.deltasmooth = (float)deltav;
                            }
                        } else {
                            max_speed = 0.35;
                            deltav = -0.02;
                            if (this.deltasmooth > 0.0f) {
                                this.deltasmooth = 0.0f;
                            }
                            this.deltasmooth = (float)((double)this.deltasmooth + deltav / 10.0);
                            if ((double)this.deltasmooth < deltav) {
                                this.deltasmooth = (float)deltav;
                            }
                        }
                        if ((newvelocity += (double)this.deltasmooth) >= 0.0) {
                            if (newvelocity > max_speed) {
                                newvelocity = max_speed;
                            }
                            this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                            this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                        } else {
                            if (newvelocity < -max_speed) {
                                newvelocity = -max_speed;
                            }
                            newvelocity = -newvelocity;
                            this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                            this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                        }
                    } else if (newvelocity >= 0.0) {
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                    } else {
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                    }
                    if (this.fireballticker == 0) {
                        double cz;
                        double cx;
                        double xzoff = 4.0;
                        double yoff = -0.25;
                        if (this.getDragonType() == 0) {
                            double d3;
                            double cy;
                            if (pp.moveStrafing > 0.001f) {
                                bf = new BetterFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, 0.0, 0.0, 0.0);
                                bf.setNotMe();
                                bf.setSmall();
                                cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                bf.setPosition(cx, this.posY + yoff, cz);
                                cx = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0f));
                                cz = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0f));
                                cy = -Math.sin(Math.toRadians(pp.rotationPitch));
                                d3 = net.minecraft.util.math.MathHelper.sqrt_double((double)(cx * cx + cy * cy + cz * cz));
                                bf.accelerationX = cx / d3 * 0.15;
                                bf.accelerationY = cy / d3 * 0.15;
                                bf.accelerationZ = cz / d3 * 0.15;
                                bf.motionX = this.motionX;
                                bf.motionY = this.motionY;
                                bf.motionZ = this.motionZ;
                                bf.posX -= this.motionX * 9.0;
                                bf.posY -= this.motionY * 9.0;
                                bf.posZ -= this.motionZ * 9.0;
                                this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                                this.world.spawnEntity((Entity)bf);
                                this.fireballticker = 10;
                            }
                            if (pp.moveStrafing < -0.001f) {
                                bf = new BetterFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, 0.0, 0.0, 0.0);
                                bf.setNotMe();
                                cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                bf.setPosition(cx, this.posY + yoff, cz);
                                cx = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0f));
                                cz = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0f));
                                cy = -Math.sin(Math.toRadians(pp.rotationPitch));
                                d3 = net.minecraft.util.math.MathHelper.sqrt_double((double)(cx * cx + cy * cy + cz * cz));
                                bf.accelerationX = cx / d3 * 0.1;
                                bf.accelerationY = cy / d3 * 0.1;
                                bf.accelerationZ = cz / d3 * 0.1;
                                bf.motionX = this.motionX;
                                bf.motionY = this.motionY;
                                bf.motionZ = this.motionZ;
                                bf.posX -= this.motionX * 9.0;
                                bf.posY -= this.motionY * 9.0;
                                bf.posZ -= this.motionZ * 9.0;
                                this.world.playSoundAtEntity((Entity)this, "random.fuse", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                                this.world.spawnEntity((Entity)bf);
                                this.fireballticker = 20;
                            }
                        } else {
                            float var9;
                            double var77;
                            double var5;
                            double var3;
                            EntityThrowable var2;
                            if (pp.moveStrafing > 0.001f) {
                                cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                var2 = new WaterBall(this.world, cx, this.posY + yoff, cz);
                                var2.setLocationAndAngles(cx, this.posY + yoff, cz, pp.rotationYaw + 90.0f, pp.rotationPitch);
                                var3 = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0f));
                                var5 = -Math.sin(Math.toRadians(pp.rotationPitch));
                                var77 = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0f));
                                var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var77 * var77)) * 0.2f;
                                var2.setThrowableHeading(var3, var5 + (double)var9, var77, 1.4f, 5.0f);
                                var2.posX -= this.motionX * 7.0;
                                var2.posY -= this.motionY * 7.0;
                                var2.posZ -= this.motionZ * 7.0;
                                this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                                this.world.spawnEntity((Entity)var2);
                                this.fireballticker = 5;
                            }
                            if (pp.moveStrafing < -0.001f) {
                                cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                                cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                                var2 = new IceBall(this.world, cx, this.posY + yoff, cz);
                                var2.setLocationAndAngles(cx, this.posY + yoff, cz, pp.rotationYaw + 90.0f, pp.rotationPitch);
                                var2.setSpecial();
                                var2.setIceBall();
                                var3 = Math.cos(Math.toRadians(pp.rotationYaw + 90.0f));
                                var5 = -Math.sin(Math.toRadians(pp.rotationPitch));
                                var77 = Math.sin(Math.toRadians(pp.rotationYaw + 90.0f));
                                var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var77 * var77)) * 0.2f;
                                var2.setThrowableHeading(var3, var5 + (double)var9, var77, 1.4f, 5.0f);
                                var2.posX -= this.motionX * 7.0;
                                var2.posY -= this.motionY * 7.0;
                                var2.posZ -= this.motionZ * 7.0;
                                var2.motionX *= 2.0;
                                var2.motionY *= 2.0;
                                var2.motionZ *= 2.0;
                                this.world.playSoundAtEntity((Entity)this, "fireworks.launch", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                                this.world.spawnEntity((Entity)var2);
                                this.fireballticker = 15;
                            }
                        }
                    }
                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
                    this.motionX *= 0.985;
                    this.motionY *= 0.94;
                    this.motionZ *= 0.985;
                    if (!this.world.isRemote && (list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(2.25, 2.0, 2.25))) != null && !list.isEmpty()) {
                        for (int l = 0; l < list.size(); ++l) {
                            listEntity = (Entity)list.get(l);
                            if (listEntity == this.getPassengers() || listEntity.isDead() || !listEntity.canBePushed()) continue;
                            listEntity.applyEntityCollision((Entity)this);
                        }
                    }
                    this.fly_with_rider();
                    if (this.getPassengers().isDead()) {
                        this.getPassengers() = null;
                    }
                } else {
                    this.fly_without_rider();
                }
            }
            this.always_do();
        }
    }

    public void updateRiderPosition() {
        if (this.getPassengers() != null) {
            float f = 0.65f;
            this.getPassengers().setPosition(this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + this.getMountedYOffset() + this.getPassengers().getYOffset(), this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    protected void playTameEffect(boolean par1) {
        String s = "heart";
        if (!par1) {
            s = "smoke";
        }
        for (int i = 0; i < 20; ++i) {
            double d0 = this.rand.nextGaussian() * 0.08;
            double d1 = this.rand.nextGaussian() * 0.08;
            double d2 = this.rand.nextGaussian() * 0.08;
            this.world.spawnParticle(s, this.posX + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 2.5f), this.posY + 0.5 + (double)this.rand.nextFloat() * 1.5, this.posZ + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 2.5f), d0, d1, d2);
        }
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (!this.isTamed()) {
            if (var2 != null && var2.getItem() == Items.BEEF && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (!this.world.isRemote) {
                    if (this.world.rand.nextInt(5) == 1) {
                        this.setTamed(true);
                        this.func_152115_b(par1EntityPlayer.getUniqueID().toString());
                        this.playTameEffect(true);
                        this.world.setEntityState((Entity)this, (byte)7);
                        this.heal((float)this.mygetMaxHealth() - this.getHealth());
                    } else {
                        this.playTameEffect(false);
                        this.world.setEntityState((Entity)this, (byte)6);
                    }
                }
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
        } else {
            if (!this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
                return false;
            }
            if (var2 == null && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
                if (!this.world.isRemote) {
                    par1EntityPlayer.startRiding((Entity)this);
                    this.setActivity(1);
                    this.setSitting(false);
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.BEEF && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                if ((float)this.mygetMaxHealth() > this.getHealth()) {
                    this.heal((float)this.mygetMaxHealth() - this.getHealth());
                }
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DEADBUSH) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (!this.world.isRemote) {
                    this.setTamed(false);
                    this.func_152115_b("");
                    this.playTameEffect(false);
                    this.world.setEntityState((Entity)this, (byte)6);
                }
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.ICE) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
                if (!this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)6);
                    this.setDragonFire(0);
                    par1EntityPlayer.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString("Dragon fireballs extinguished."));
                }
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.FLINT_AND_STEEL && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
                if (!this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)6);
                    this.setDragonFire(1);
                    par1EntityPlayer.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString("Dragon fireballs lit!"));
                }
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.GUNPOWDER && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer) && this.getDragonFire() > 0) {
                if (!this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)6);
                    this.setDragonFire(2);
                    par1EntityPlayer.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString("Dragon fireballs supercharged!"));
                }
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.SNOWBALL && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                this.dragontype = 1;
                this.setDragonType(this.dragontype);
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.COAL && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                this.dragontype = 0;
                this.setDragonType(this.dragontype);
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.DIAMOND && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer) && !this.world.isRemote) {
                Entity ent = null;
                Spyro d = null;
                ent = Dragon.spawnCreature(this.world, "Baby Dragon", this.posX, this.posY, this.posZ);
                if (ent != null) {
                    d = (Spyro)ent;
                    if (this.isTamed()) {
                        d.setTamed(true);
                        d.func_152115_b(par1EntityPlayer.getUniqueID().toString());
                    }
                    this.setDead();
                }
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (this.isTamed() && var2 != null && var2.getItem() == Items.NAME_TAG && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
                this.setCustomNameTag(var2.getDisplayName());
                if (!par1EntityPlayer.isCreative()) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (!this.isSitting()) {
                    this.setSitting(true);
                    this.setActivity(0);
                } else {
                    this.setSitting(false);
                    this.setActivity(0);
                }
                return true;
            }
        }
        return false;
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.BEEF;
    }

    public int getAttacking() {
        return this.dataManager.get(20);
    }

    public void setAttacking(int par1) {
        if (this.world != null && this.world.isRemote) {
            return;
        }
        this.dataManager.set(20, (Object)((byte)par1));
    }

    public int getActivity() {
        return this.dataManager.get(21);
    }

    public void setActivity(int par1) {
        if (this.world != null && this.world.isRemote) {
            return;
        }
        this.dataManager.set(21, (Object)((byte)par1));
    }

    public int getDragonFire() {
        return this.dataManager.get(24);
    }

    public void setDragonFire(int par1) {
        if (this.world.isRemote) {
            return;
        }
        this.dataManager.set(24, (Object)par1);
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    public void setDragonType(int par1) {
        this.dataManager.set(22, (Object)par1);
    }

    public int getDragonType() {
        return this.dataManager.get(22);
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        if (this.getPassengers() != null) {
            return false;
        }
        return !this.isTamed();
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("DragonAttacking", this.getAttacking());
        par1NBTTagCompound.setInteger("DragonActivity", this.getActivity());
        par1NBTTagCompound.setInteger("DragonFire", this.getDragonFire());
        par1NBTTagCompound.setInteger("DragonType", this.getDragonType());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAttacking(par1NBTTagCompound.getInteger("DragonAttacking"));
        this.setActivity(par1NBTTagCompound.getInteger("DragonActivity"));
        this.setDragonFire(par1NBTTagCompound.getInteger("DragonFire"));
        this.dragontype = par1NBTTagCompound.getInteger("DragonType");
        this.setDragonType(this.dragontype);
    }
}

