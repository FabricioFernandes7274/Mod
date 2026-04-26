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
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAINearestAttackableTarget
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITempt
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Leon
extends EntityTameable {
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double boatYawHead;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
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
    private float moveSpeed = 0.25f;
    private float deltasmooth = 0.0f;

    public Leon(World par1World) {
        super(par1World);
        this.setSize(3.5f, 8.25f);
        this.getNavigator().setAvoidsWater(true);
        this.experienceValue = 300;
        //this.fireResistance = 10;
        this.isImmuneToFire = false;
        this.setSitting(false);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIFollowOwner(this, 1.1f, 16.0f, 2.0f));
        this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.25, Items.BEEF, false));
        this.tasks.addTask(3, (EntityAIBase)new MyEntityAIWander((EntityCreature)this, 0.75f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 9.0f));
        this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 0, true, false, IMob.targetEntitySelector));
        }
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.getPassengers() = null;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
    }

    public Leon(World par1World, double par2, double par4, double par6) {
        this(par1World);
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
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(55.0);
    }

    public boolean shouldRiderSit() {
        return true;
    }

    public int getTrackingRange() {
        return 64;
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
        this.setActivity(0);
        this.setAttacking(0);
        this.setBeingRidden(0);
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
        return 250;
    }

    public int getLeonHealth() {
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
        return 16;
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return false;
    }

    public String getLivingSound() {
        if (this.isSitting()) {
            return null;
        }
        if (this.getActivity() == 1 && this.getPassengers() == null) {
            return "orespawn:leon_living";
        }
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:leon_hit";
    }

    protected String getDeathSound() {
        return "orespawn:leon_death";
    }

    protected float getSoundVolume() {
        return 1.75f;
    }

    public float getSoundPitch() {
        return 0.85f;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 3.75;
    }

    protected Item getDropItem() {
        return Items.BEEF;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 2.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        int i = 4 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.CHICKEN, 1);
        }
        i = 16 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.FEATHER, 1);
        }
        i = 2 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Item.getItemFromBlock((Block)OreSpawnMain.KrakenRepellent), 1);
        }
        if (this.world.rand.nextInt(5) == 1) {
            this.dropItemRand(OreSpawnMain.MyBattleAxe, 1);
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        double ks = 1.25;
        double inair = 0.15;
        float iskraken = 1.0f;
        if (par1Entity != null && par1Entity instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon)par1Entity;
            DamageSource var21 = null;
            var21 = DamageSource.setExplosionSource(null);
            var21.setExplosion();
            if (this.world.rand.nextInt(6) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, var21, 55.0f);
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, var21, 55.0f);
            }
        } else if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
            if (par1Entity instanceof Kraken) {
                iskraken = 4.0f;
            }
            par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), iskraken * 55.0f);
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
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return ret;
        }
        if (!this.world.isRemote) {
            this.setSitting(false);
        }
        if (!this.world.isRemote) {
            this.setActivity(1);
        }
        if ((e = par1DamageSource.getEntity()) != null && e instanceof Leon) {
            return false;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        this.hurt_timer = 15;
        if (e != null && e instanceof net.minecraft.entity.EntityLivingBase && !this.world.isRemote) {
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
        if (this.world.isRemote) {
            return;
        }
        super.updateAITasks();
        if (this.world.rand.nextInt(200) == 1) {
            this.setAttackTarget(null);
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
            e = this.getAttackTarget();
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                this.setAttacking(1);
                if (this.getDistanceSq((Entity)e) < (double)((9.0f + e.width / 2.0f) * (9.0f + e.width / 2.0f))) {
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
        if (this.world.isRemote) {
            return;
        }
        super.updateAITick();
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        if (OreSpawnMain.PlayNicely != 0) {
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
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof Leon) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
            return !this.isTamed();
        }
        if (!this.isTamed()) {
            if (MyUtils.isAttackableNonMob(par1EntityLiving)) {
                return true;
            }
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
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    Block bid = this.world.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.func_145881_a().getEntityNameToSpawn();
                    if (s == null || !s.equals("Leonopteryx")) continue;
                    return true;
                }
            }
        }
        if (this.world.rand.nextInt(16) != 0) {
            return false;
        }
        Leon target = null;
        if (!this.world.isDaytime()) {
            return false;
        }
        target = (Leon)this.world.findNearestEntityWithinAABB(Leon.class, this.boundingBox.expand(48.0, 16.0, 48.0), (Entity)this);
        if (target != null) {
            return false;
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
        if (this.getActivity() == 0 && this.isTamed() && this.getOwner() != null && !this.isSitting() && (e = this.getOwner()) != null && this.getDistanceSq((Entity)e) > 144.0) {
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
        double gh = 1.25;
        double obstruction_factor = 0.0;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        boolean toofar = false;
        if (this.world.isRemote) {
            return;
        }
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
                this.setActivity(1);
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
        if (!toofar && this.unstick_timer == 0 && this.flyaway == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(8) == 1) {
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
                    if (this.getDistanceSq((Entity)e) < (double)((7.0f + e.width / 2.0f) * (7.0f + e.width / 2.0f))) {
                        this.attackEntityAsMob((Entity)e);
                    }
                }
            } else {
                this.target_in_sight = false;
                this.flyaway = 0;
                this.setAttacking(0);
            }
        }
        if (this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.1f) {
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
                        zdir = this.world.rand.nextInt(12) + 6;
                        xdir = this.world.rand.nextInt(12) + 6;
                    } else {
                        zdir = this.world.rand.nextInt(8);
                        xdir = this.world.rand.nextInt(8);
                    }
                } else {
                    zdir = this.world.rand.nextInt(20) + 6;
                    xdir = this.world.rand.nextInt(20) + 6;
                }
                if (this.world.rand.nextInt(2) == 1) {
                    zdir = -zdir;
                }
                if (this.world.rand.nextInt(2) == 1) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos(gox + xdir, goy + this.world.rand.nextInt(9 + this.owner_flying * 2) - 4, goz + zdir);
                bid = this.world.getBlock(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ());
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
                bid = this.world.getBlock((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))));
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
        this.rotationYaw += var8 / 5.0f;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    public void onLivingUpdate() {
        List list = null;
        Entity listEntity = null;
        double d6 = this.rand.nextFloat() * 2.0f - 1.0f;
        double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 1.15;
        double gh = 1.0;
        double rt = 0.0;
        double pi = 3.1415926545;
        double deltav = 0.0;
        int dist = 2;
        this.always_do();
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
                    d10 = net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)((double)this.getPassengers().rotationYaw - (double)this.rotationYaw));
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.rotationYawHead = this.rotationYaw;
                --this.boatPosRotationIncrements;
            }
        } else {
            if (this.getPassengers() != null) {
                this.setBeingRidden(1);
            } else {
                this.setBeingRidden(0);
            }
            if (this.getActivity() != 0) {
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
                    gh = 1.55;
                    Block bid = this.world.getBlock((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ);
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
                            bid = this.world.getBlock((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))));
                            if (bid == Blocks.AIR) continue;
                            obstruction_factor += 0.05;
                        }
                    }
                    this.motionY += obstruction_factor * 0.07;
                    this.posY += obstruction_factor * 0.07;
                    if (this.motionY > 2.0) {
                        this.motionY = 2.0;
                    }
                    if (this.getPassengers() != null) {
                        double d4 = this.getPassengers().rotationYaw;
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
                            this.rotationYaw = this.getPassengers().rotationYaw + (float)(relative_g * d4);
                        } else {
                            this.rotationYaw = this.getPassengers().rotationYaw;
                        }
                        relative_g = Math.abs(relative_g) * velocity;
                        if (relative_g > 50.0) {
                            relative_g = 0.0;
                        }
                    }
                    this.rotationPitch = 2.0f * (float)velocity;
                    this.setRotation(this.rotationYaw, this.rotationPitch);
                    this.rotationYawHead = this.rotationYaw;
                    double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    double rr = Math.atan2(this.getPassengers().motionZ, this.getPassengers().motionX);
                    double rhm = Math.atan2(this.motionZ, this.motionX);
                    double rhdir = Math.toRadians((this.getPassengers().rotationYaw + 90.0f) % 360.0f);
                    rt = 0.0;
                    pi = 3.1415926545;
                    deltav = 0.0;
                    float im = pp.moveForward;
                    if (OreSpawnMain.flyup_keystate != 0) {
                        this.motionY += 0.035;
                        this.motionY += velocity * 0.038;
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
                            deltav = 0.028;
                            if (max_speed > 1.0) {
                                deltav += 0.06;
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
                    if (this.getPassengers() != null && this.getPassengers().isDead()) {
                        this.getPassengers() = null;
                    }
                } else {
                    this.fly_without_rider();
                }
            }
        }
    }

    public void always_do() {
        net.minecraft.entity.EntityLivingBase e = null;
        net.minecraft.entity.player.EntityPlayer pl = null;
        if (this.world.isRemote) {
            return;
        }
        if (!this.isSitting() && this.getActivity() == 0 && this.getPassengers() == null && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(10) == 1 && (e = this.findSomethingToAttack()) != null) {
            this.setActivity(1);
        }
        if (this.world.rand.nextInt(250) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(2.0f);
        }
        if (this.isSitting()) {
            return;
        }
        this.owner_flying = 0;
        if (this.isTamed() && this.getOwner() != null && this.getPassengers() == null && !this.isSitting()) {
            pl = (net.minecraft.entity.player.EntityPlayer)this.getOwner();
            if (pl.capabilities.isFlying) {
                this.owner_flying = 1;
                this.setActivity(1);
            }
        }
        if (this.isTamed() && this.getOwner() != null && !this.isSitting() && this.getDistanceSq((Entity)(pl = (net.minecraft.entity.player.EntityPlayer)this.getOwner())) > 400.0) {
            this.setActivity(1);
        }
        if (this.world.rand.nextInt(50) == 1 && !this.isSitting() && !this.target_in_sight && this.getPassengers() == null) {
            if (this.world.rand.nextInt(15) == 1) {
                this.setActivity(1);
            } else {
                this.setActivity(0);
            }
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

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1net.minecraft.entity.player.EntityPlayer) {
        ItemStack var2 = par1net.minecraft.entity.player.EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1net.minecraft.entity.player.EntityPlayer.inventory.setInventorySlotContents(par1net.minecraft.entity.player.EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DIAMOND_BLOCK) && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 49.0) {
            if (!this.world.isRemote) {
                this.setTamed(true);
                this.func_152115_b(par1net.minecraft.entity.player.EntityPlayer.getUniqueID().toString());
                this.playTameEffect(true);
                this.world.setEntityState((Entity)this, (byte)7);
                this.heal((float)this.mygetMaxHealth() - this.getHealth());
            }
            if (!par1net.minecraft.entity.player.EntityPlayer.capabilities.isCreativeMode) {
                --var2.stackSize;
                if (var2.stackSize <= 0) {
                    par1net.minecraft.entity.player.EntityPlayer.inventory.setInventorySlotContents(par1net.minecraft.entity.player.EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (!this.isTamed()) {
            if (var2 != null && var2.getItem() == Items.BEEF && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 49.0) {
                if (!this.world.isRemote) {
                    if (this.world.rand.nextInt(3) == 1) {
                        this.setTamed(true);
                        this.func_152115_b(par1net.minecraft.entity.player.EntityPlayer.getUniqueID().toString());
                        this.playTameEffect(true);
                        this.world.setEntityState((Entity)this, (byte)7);
                        this.heal((float)this.mygetMaxHealth() - this.getHealth());
                    } else {
                        this.playTameEffect(false);
                        this.world.setEntityState((Entity)this, (byte)6);
                    }
                }
                if (!par1net.minecraft.entity.player.EntityPlayer.capabilities.isCreativeMode) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1net.minecraft.entity.player.EntityPlayer.inventory.setInventorySlotContents(par1net.minecraft.entity.player.EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
        } else {
            if (!this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1net.minecraft.entity.player.EntityPlayer)) {
                return false;
            }
            if (var2 == null && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 49.0) {
                if (!this.world.isRemote) {
                    par1net.minecraft.entity.player.EntityPlayer.startRiding((Entity)this);
                    this.setActivity(1);
                    this.setSitting(false);
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.BEEF && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 49.0) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                if ((float)this.mygetMaxHealth() > this.getHealth()) {
                    this.heal((float)this.mygetMaxHealth() - this.getHealth());
                }
                if (!par1net.minecraft.entity.player.EntityPlayer.capabilities.isCreativeMode) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1net.minecraft.entity.player.EntityPlayer.inventory.setInventorySlotContents(par1net.minecraft.entity.player.EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DEADBUSH) && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 49.0) {
                if (!this.world.isRemote) {
                    this.setTamed(false);
                    this.func_152115_b("");
                    this.playTameEffect(false);
                    this.world.setEntityState((Entity)this, (byte)6);
                }
                if (!par1net.minecraft.entity.player.EntityPlayer.capabilities.isCreativeMode) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1net.minecraft.entity.player.EntityPlayer.inventory.setInventorySlotContents(par1net.minecraft.entity.player.EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (this.isTamed() && var2 != null && var2.getItem() == Items.NAME_TAG && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 49.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1net.minecraft.entity.player.EntityPlayer)) {
                this.setCustomNameTag(var2.getDisplayName());
                if (!par1net.minecraft.entity.player.EntityPlayer.capabilities.isCreativeMode) {
                    --var2.stackSize;
                    if (var2.stackSize <= 0) {
                        par1net.minecraft.entity.player.EntityPlayer.inventory.setInventorySlotContents(par1net.minecraft.entity.player.EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                return true;
            }
            if (var2 != null && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 49.0 && this.getPassengers() == null) {
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
        this.dataManager.set(20, (Object)par1);
    }

    public int getActivity() {
        return this.dataManager.get(21);
    }

    public void setActivity(int par1) {
        if (this.world != null && this.world.isRemote) {
            return;
        }
        this.dataManager.set(21, (Object)par1);
    }

    public int getBeingRidden() {
        return this.dataManager.get(22);
    }

    public void setBeingRidden(int par1) {
        if (this.world != null && this.world.isRemote) {
            return;
        }
        this.dataManager.set(22, (Object)par1);
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
        par1NBTTagCompound.setInteger("LeonAttacking", this.getAttacking());
        par1NBTTagCompound.setInteger("LeonActivity", this.getActivity());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAttacking(par1NBTTagCompound.getInteger("LeonAttacking"));
        this.setActivity(par1NBTTagCompound.getInteger("LeonActivity"));
    }
}

