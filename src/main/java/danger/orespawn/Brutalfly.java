/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntitySmallFireball
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
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
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
import net.minecraft.world.35f;

    public Brutalfly(World par1World) {
        super(par1World);
        this.setSize(5.0f, 2.0f);
        this.getNavigator().setAvoidsWater(true);
        this.experienceValue = 100;
        this.isImmuneToFire = true;
        //this.fireResistance = 500;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Brutalfly_stats.attack);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Brutalfly_stats.defense;
    }

    public int getBrutalflyHealth() {
        return (int)this.getHealth();
    }

    protected float getSoundVolume() {
        return 1.5f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return "random.explode";
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Brutalfly_stats.health;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
        ++this.wing_sound;
        if (this.wing_sound > 30) {
            if (!this.world.isRemote) {
                this.world.playSoundAtEntity((Entity)this, "orespawn:MothraWings", 1.0f, 1.0f);
            }
            this.wing_sound = 0;
        }
        --this.health_ticker;
        if (this.health_ticker <= 0) {
            if (this.getHealth() < (float)this.mygetMaxHealth()) {
                this.heal(1.0f);
            }
            this.health_ticker = 100;
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 30;
        int shoot = 3;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.lastX == (int)this.posX && this.lastY == (int)this.posY && this.lastZ == (int)this.posZ) {
            ++this.stuck_count;
        } else {
            this.stuck_count = 0;
            this.lastX = (int)this.posX;
            this.lastY = (int)this.posY;
            this.lastZ = (int)this.posZ;
        }
        if (this.world.getDifficulty() == EnumDifficulty.HARD) {
            shoot = 2;
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.stuck_count > 30 || this.world.rand.nextInt(200) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 9.0f) {
            Block bid;
            int down = 0;
            int dist = 20;
            for (int i = -5; i <= 5; i += 5) {
                block1: for (int j = -5; j <= 5; j += 5) {
                    for (int k = 1; k < 20; ++k) {
                        bid = this.world.getBlock((int)this.posX + j, (int)this.posY - k, (int)this.posZ + i);
                        if (bid == Blocks.AIR) continue;
                        if (k >= dist) continue block1;
                        dist = k;
                        continue block1;
                    }
                }
            }
            if (dist > 10) {
                down = dist - 10 + 1;
            }
            bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                xdir = 1;
                zdir = 1;
                if (this.world.rand.nextInt(2) == 0) {
                    xdir = -1;
                }
                if (this.world.rand.nextInt(2) == 0) {
                    zdir = -1;
                }
                int newz = this.rand.nextInt(20) + 8;
                int newx = this.rand.nextInt(20) + 8;
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + (newx *= xdir), (int)this.posY + this.world.rand.nextInt(7) - 1 - down, (int)this.posZ + (newz *= zdir));
                bid = this.world.getBlock(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
            this.stuck_count = 0;
        }
        if (this.world.rand.nextInt(6) == 0) {
            net.minecraft.entity.player.EntityPlayer target = null;
            target = (net.minecraft.entity.player.EntityPlayer)this.world.findNearestEntityWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.boundingBox.expand(30.0, 20.0, 30.0), (Entity)this);
            if (target != null) {
                if (!target.isCreative()) {
                    if (this.getEntitySenses().canSee((Entity)target)) {
                        this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)target.posX, (int)target.posY + 4, (int)target.posZ);
                        if (this.rand.nextInt(shoot) == 0) {
                            this.attackWithSomething((net.minecraft.entity.EntityLivingBase)target);
                        }
                    }
                } else {
                    target = null;
                }
            }
            if (target == null && this.world.rand.nextInt(3) == 0) {
                net.minecraft.entity.EntityLivingBase e = null;
                e = this.findSomethingToAttack();
                if (e != null) {
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)e.posY + 5, (int)e.posZ);
                    if (this.getDistanceSq((Entity)e) > 25.0) {
                        if (this.world.rand.nextInt(shoot) == 0) {
                            this.attackWithSomething(e);
                        }
                    } else {
                        this.attackEntityAsMob((Entity)e);
                    }
                }
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * 0.30001;
        this.motionY += (Math.signum(var3) * 0.7 - this.motionY) * 0.20001;
        this.motionZ += (Math.signum(var5) * 0.5 - this.motionZ) * 0.30001;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 1.0f;
        this.rotationYaw += var8 / 8.0f;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        Entity e = par1DamageSource.getEntity();
        if (e != null && e instanceof Brutalfly) {
            return false;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)e.posY + 2, (int)e.posZ);
        }
        return ret;
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int i;
        int j;
        int k;
        for (k = -2; k <= 2; ++k) {
            for (j = -2; j <= 2; ++j) {
                for (i = 1; i < 4; ++i) {
                    bid = this.world.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Brutalfly")) continue;
                    return true;
                }
            }
        }
        if (this.posY < 70.0) {
            return false;
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.isDaytime()) {
            return false;
        }
        for (k = -4; k < 4; ++k) {
            for (j = -3; j < 3; ++j) {
                for (i = 1; i < 10; ++i) {
                    bid = this.world.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        Brutalfly target = null;
        target = (Brutalfly)this.world.findNearestEntityWithinAABB(Brutalfly.class, this.boundingBox.expand(64.0, 32.0, 64.0), (Entity)this);
        return target == null;
    }

    public void initCreature() {
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(8) - (double)OreSpawnMain.OreSpawnRand.nextInt(8), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(8) - (double)OreSpawnMain.OreSpawnRand.nextInt(8), new ItemStack(index, par1, 0));
        this.world.spawnEntity((Entity)var3);
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        for (int i = 0; i < 20; ++i) {
            float var1 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            float var2 = (this.rand.nextFloat() - 0.5f) * 4.0f;
            float var3 = (this.rand.nextFloat() - 0.5f) * 8.0f;
            this.world.spawnParticle("largeexplode", this.posX + (double)var1, this.posY + 2.0 + (double)var2, this.posZ + (double)var3, 0.0, 0.0, 0.0);
        }
        for (var4 = 0; var4 < 53; ++var4) {
            this.dropItemRand(Items.GOLD_NUGGET, 1);
        }
        for (var4 = 0; var4 < 20; ++var4) {
            Brutalfly.spawnCreature(this.world, "Butterfly", this.posX + 0.5, this.posY + 1.0, this.posZ + 0.5);
        }
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        if (par0World == null) {
            return null;
        }
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    private void attackWithSomething(net.minecraft.entity.EntityLivingBase par1) {
        double xzoff = 2.25;
        double yoff = 0.0;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.world.getDifficulty() == EnumDifficulty.EASY) {
            EntitySmallFireball sf = new EntitySmallFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, par1.posX - cx, par1.posY + 0.55 - (this.posY + yoff), par1.posZ - cz);
            sf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
            sf.setPosition(cx, this.posY + yoff, cz);
            this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity((Entity)sf);
        } else if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
            if (this.world.rand.nextInt(2) == 0) {
                EntitySmallFireball sf = new EntitySmallFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, par1.posX - cx, par1.posY + 0.55 - (this.posY + yoff), par1.posZ - cz);
                sf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                sf.setPosition(cx, this.posY + yoff, cz);
                this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity((Entity)sf);
            } else {
                BetterFireball bf = new BetterFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, par1.posX - cx, par1.posY + 0.55 - (this.posY + yoff), par1.posZ - cz);
                bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                bf.setPosition(cx, this.posY + yoff, cz);
                bf.setNotMe();
                this.world.playSoundAtEntity((Entity)this, "random.fuse", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity((Entity)bf);
            }
        } else {
            BetterFireball bf = new BetterFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, par1.posX - cx, par1.posY + 0.55 - (this.posY + yoff), par1.posZ - cz);
            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
            bf.setPosition(cx, this.posY + yoff, cz);
            bf.setNotMe();
            this.world.playSoundAtEntity((Entity)this, "random.fuse", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity((Entity)bf);
        }
        if (this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(1.0f);
        }
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (par1EntityLiving instanceof Brutalfly) {
            return false;
        }
        if (par1EntityLiving instanceof Mothra) {
            return false;
        }
        if (par1EntityLiving instanceof Vortex) {
            return false;
        }
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.isCreative();
        }
        return false;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(25.0, 20.0, 25.0));
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
}

