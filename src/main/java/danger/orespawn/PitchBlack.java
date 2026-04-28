/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveThroughVillage
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
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
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.world.World;

public class PitchBlack
extends EntityMob {
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private float MyMoveSpeed = 0.2f;
    private int damage_ticker = 0;
    private int wing_sound = 0;

    public PitchBlack(World worldIn) {
        super(worldIn);
        this.setSize(2.0f, 3.0f);
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 200;
        this.isImmuneToFire = false;
        //this.fireResistance = 25;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 10.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.MyMoveSpeed = 0.2f;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(this.MyMoveSpeed + 0.1f * this.getPitchBlackScale()));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)(this.getPitchBlackScale() * (float)OreSpawnMain.PitchBlack_stats.attack));
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)0);
//         this.dataManager.register(21, (Object)0);
//         this.dataManager.register(22, (Object)0);
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
        float t = 0.5f;
        if (this.world != null) {
            if (this.world.rand.nextInt(4) == 1) {
                t = 1.0f;
            }
            if (this.world.rand.nextInt(8) == 2) {
                t = 2.0f;
            }
            if (this.world.rand.nextInt(32) == 3) {
                t = 3.0f;
            }
            if (this.world.rand.nextInt(64) == 4) {
                t = 4.0f;
            }
        } else {
            if (OreSpawnMain.OreSpawnRand.nextInt(4) == 1) {
                t = 1.0f;
            }
            if (OreSpawnMain.OreSpawnRand.nextInt(8) == 2) {
                t = 2.0f;
            }
            if (OreSpawnMain.OreSpawnRand.nextInt(32) == 3) {
                t = 3.0f;
            }
            if (OreSpawnMain.OreSpawnRand.nextInt(64) == 4) {
                t = 4.0f;
            }
        }
        if (OreSpawnMain.NightmareSize == 1) {
            t = 0.5f;
        }
        if (OreSpawnMain.NightmareSize == 2) {
            t = 1.0f;
        }
        if (OreSpawnMain.NightmareSize == 3) {
            t = 2.0f;
        }
        if (OreSpawnMain.NightmareSize == 4) {
            t = 3.0f;
        }
        if (OreSpawnMain.NightmareSize == 5) {
            t = 4.0f;
        }
        this.setPitchBlackScale(t);
        this.experienceValue = (int)(100.0f * t);
        //this.fireResistance = (int)(25.0f * t);
        this.setSize(2.5f * this.getPitchBlackScale(), 3.5f * this.getPitchBlackScale());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setPitchBlackScale(par1NBTTagCompound.getFloat("Fscale"));
        this.setSize(2.5f * this.getPitchBlackScale(), 3.5f * this.getPitchBlackScale());
        this.experienceValue = (int)(100.0f * this.getPitchBlackScale());
        //this.fireResistance = (int)(25.0f * this.getPitchBlackScale());
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setFloat("Fscale", this.getPitchBlackScale());
    }

    public final int getAttacking() {
        return 0 /* this.dataManager.get(20) */;
    }

    public final void setAttacking(int par1) {
//         this.dataManager.set(20, (Object)((byte)par1));
    }

    public final int getActivity() {
        return 0 /* this.dataManager.get(21) */;
    }

    public final void setActivity(int par1) {
//         this.dataManager.set(21, (Object)((byte)par1));
    }

    public float getPitchBlackScale() {
        int i = 0 /* this.dataManager.get(22) */;
        float f = i;
        return f / 10.0f;
    }

    public void setPitchBlackScale(float par1) {
        float f = par1 * 10.0001f;
        int i = (int)f;
//         this.dataManager.set(22, (Object)i);
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.PitchBlack_stats.defense + (int)(2.0f * this.getPitchBlackScale());
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

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return this.world.isDaytime();
    }

    protected float getSoundVolume() {
        return 0.75f;
    }

    protected float getSoundPitch() {
        return 1.0f - 0.7f * (4.0f / this.getPitchBlackScale());
    }

    protected String getLivingSound() {
        if (this.world.rand.nextInt(5) != 2) {
            return null;
        }
        return "orespawn:pitchblack_living";
    }

    protected String getHurtSound() {
        return "orespawn:pitchblack_hit";
    }

    protected String getDeathSound() {
        return "orespawn:pitchblack_dead";
    }

    public int mygetMaxHealth() {
        return (int)((float)OreSpawnMain.PitchBlack_stats.health * this.getPitchBlackScale());
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        this.MyMoveSpeed = 0.2f;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(this.MyMoveSpeed + 0.1f * this.getPitchBlackScale()));
        super.onUpdate();
        this.setSize(2.5f * this.getPitchBlackScale(), 3.5f * this.getPitchBlackScale());
        ++this.wing_sound;
        if (this.wing_sound > 20) {
            if (!this.world.isRemote) {
                this.world.playSound(null, (Entity)this.posX, (Entity)this.posY, (Entity)this.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
            this.wing_sound = 0;
        }
        this.motionY *= 0.6;
        if (!this.world.isRemote && this.world.rand.nextInt(250) == 1) {
            this.heal(1.0f + this.getPitchBlackScale());
            if (this.world.rand.nextInt(5) == 0) {
                Block bid = Blocks.AIR;
                if (this.posY > 10.0) {
                    for (int i = 0; i < 10 && (bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY - i, (int)this.posZ)).getBlock() == Blocks.AIR; ++i) {
                    }
                } else {
                    bid = Blocks.STONE;
                }
                if (bid != Blocks.AIR) {
                    Entity e = null;
                    e = this.findSomethingToAttack();
                    if (e == null) {
                        this.setActivity(0);
                    }
                }
            } else {
                this.setActivity(1);
                this.getNavigator().setPath(null, 0.0);
            }
        }
        if (this.getActivity() == 0 && this.world.rand.nextInt(10) == 1) {
            Entity e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                this.setActivity(1);
                this.getNavigator().setPath(null, 0.0);
            }
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4 = false;
        if (par1Entity != null && par1Entity instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon)par1Entity;
            DamageSource var21 = null;
            var21 = DamageSource.setExplosionSource(null);
            var21.setExplosion();
            if (this.world.rand.nextInt(8) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, var21, (float)OreSpawnMain.PitchBlack_stats.attack * this.getPitchBlackScale());
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, var21, (float)OreSpawnMain.PitchBlack_stats.attack * this.getPitchBlackScale());
            }
            var4 = true;
        } else {
            var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)OreSpawnMain.PitchBlack_stats.attack * this.getPitchBlackScale());
            if (var4 && par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
                double ks = 1.15 * (double)this.getPitchBlackScale();
                double inair = 0.08 * (double)this.getPitchBlackScale();
                float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
                if (par1Entity.isDead() || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                    inair *= 2.0;
                }
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }
        return var4;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        if (this.damage_ticker > 0) {
            --this.damage_ticker;
        }
        if (this.getActivity() == 0) {
            super.updateAITasks();
            return;
        }
        if (this.isDead()) {
            return;
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.getActivity() == 0) {
            return;
        }
        if (this.rand.nextInt(150) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.1f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying > 0) {
                zdir = this.rand.nextInt(20) + 5 * (int)this.getPitchBlackScale();
                xdir = this.rand.nextInt(20) + 5 * (int)this.getPitchBlackScale();
                if (this.rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }
                if (this.rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(11) - 5, (int)this.posZ + zdir);
                bid = this.world.getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        } else if (this.rand.nextInt(8) == 0) {
            Entity e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                double d1 = 5.0 + (double)(e.width / 2.0f);
                d1 += (double)this.getPitchBlackScale();
                d1 *= d1;
                this.setAttacking(1);
                if (e instanceof EntityDragon && d1 < 100.0) {
                    d1 = 100.0;
                }
                if (e instanceof Godzilla && d1 < 100.0) {
                    d1 = 100.0;
                }
                if (e instanceof GodzillaHead && d1 < 100.0) {
                    d1 = 100.0;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + 2.0), (int)e.posZ);
                if (this.getDistanceSq(e) < d1) {
                    this.attackEntityAsMob(e);
                }
            } else {
                this.setAttacking(0);
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.4 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.4 - this.posZ;
        double myspeed = 0.5f + this.getPitchBlackScale() / 10.0f;
        this.motionX += (Math.signum(var1) * myspeed - this.motionX) * 0.33;
        this.motionY += (Math.signum(var3) * (double)0.7f - this.motionY) * 0.20000000149011612;
        this.motionZ += (Math.signum(var5) * myspeed - this.motionZ) * 0.33;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.1f + (float)myspeed;
        this.rotationYaw += var8 / 5.0f;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (this.damage_ticker > 0) {
            return ret;
        }
        this.damage_ticker = 20;
        ret = super.attackEntityFrom(par1DamageSource, par2);
        Entity e = par1DamageSource.getEntity();
        if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + 2.0), (int)e.posZ);
        }
        this.setActivity(1);
        this.getNavigator().setPath(null, 0.0);
        return ret;
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int i;
        int j;
        int k;
        for (k = -3; k < 3; ++k) {
            for (j = -3; j < 3; ++j) {
                for (i = 0; i < 5; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Nightmare")) continue;
                    Float t = Float.valueOf(this.getPitchBlackScale());
                    if (t.floatValue() > 1.0f) {
                        t = Float.valueOf(1.0f);
                    }
                    this.setPitchBlackScale(t.floatValue());
                    return true;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.isDaytime()) {
            return false;
        }
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID6) {
            PitchBlack target = null;
            target = (PitchBlack)this.world.findNearestEntityWithinAABB(PitchBlack.class, this.getEntityBoundingBox().expand(16.0, 16.0, 16.0), (Entity)this);
            if (target != null) {
                return false;
            }
        }
        if (this.getPitchBlackScale() < 1.1f) {
            return true;
        }
        int ix = 1;
        if (this.getPitchBlackScale() > 3.1f) {
            ix = 2;
        }
        int iy = ix * 3;
        for (k = -ix; k <= ix; ++k) {
            for (j = -ix; j <= ix; ++j) {
                for (i = 1; i <= iy; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!(par1EntityLiving instanceof net.minecraft.entity.EntityLivingBase)) {
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
        if (par1EntityLiving instanceof PitchBlack) {
            return false;
        }
        if (par1EntityLiving instanceof EnderReaper) {
            return false;
        }
        if (par1EntityLiving instanceof LeafMonster) {
            return false;
        }
        if (par1EntityLiving instanceof TerribleTerror) {
            return false;
        }
        if (par1EntityLiving instanceof LurkingTerror) {
            return false;
        }
        if (par1EntityLiving instanceof CreepingHorror) {
            return false;
        }
        if (par1EntityLiving instanceof Island) {
            return false;
        }
        if (par1EntityLiving instanceof IslandToo) {
            return false;
        }
        if (par1EntityLiving instanceof Triffid) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.isCreative()) {
                return false;
            }
        }
        return true;
    }

    private Entity findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        double d1 = 16.0 + (double)(this.getPitchBlackScale() * 6.0f);
        double d2 = 10.0 + (double)(this.getPitchBlackScale() * 4.0f);
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(d1, d2, d1));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        net.minecraft.entity.EntityLivingBase var3 = null;
        while (var2.hasNext()) {
            var3 = (net.minecraft.entity.EntityLivingBase)var2.next();
            if (!this.isSuitableTarget(var3, false)) continue;
            return var3;
        }
        return null;
    }

    protected Item getDropItem() {
        return OreSpawnMain.MyNightmareScale;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)((float)OreSpawnMain.OreSpawnRand.nextInt(5) * this.getPitchBlackScale()) - (double)((float)OreSpawnMain.OreSpawnRand.nextInt(5) * this.getPitchBlackScale()), this.posY + 1.0, this.posZ + (double)((float)OreSpawnMain.OreSpawnRand.nextInt(5) * this.getPitchBlackScale()) - (double)((float)OreSpawnMain.OreSpawnRand.nextInt(5) * this.getPitchBlackScale()), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        int i = 3 + this.world.rand.nextInt(2 + (int)(5.0f * this.getPitchBlackScale()));
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.ROTTEN_FLESH, 1);
            int j = this.world.rand.nextInt(10);
            if (j == 0) {
                this.dropItemRand(Items.FEATHER, 1);
            }
            if (j == 1) {
                this.dropItemRand(Items.STRING, 1);
            }
            if (j == 2) {
                this.dropItemRand(Items.FLINT, 1);
            }
            if (j != 3) continue;
            this.dropItemRand(Items.BEEF, 1);
        }
        this.dropItemRand(OreSpawnMain.MyNightmareScale, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        i = 2 + (int)this.getPitchBlackScale() + this.world.rand.nextInt(2 + (int)(5.0f * this.getPitchBlackScale()));
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(OreSpawnMain.ZooKeeper, 1);
        }
    }
}

