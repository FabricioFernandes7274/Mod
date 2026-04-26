/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Rotator
extends EntityMob {
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int busy_fighting = 0;
    private int was_spawnered = 0;

    public Rotator(World par1World) {
        super(par1World);
        this.setSize(1.0f, 2.0f);
        this.experienceValue = 35;
        this.isImmuneToFire = true;
        //this.fireResistance = 25;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Rotator_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
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
        if (this.busy_fighting != 0) {
            return false;
        }
        return this.was_spawnered == 0;
    }

    protected float getSoundVolume() {
        return 0.75f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected String getLivingSound() {
        return "vortexlive";
    }

    protected String getHurtSound() {
        return "orespawn:glasshit";
    }

    protected String getDeathSound() {
        return "orespawn:glassdead";
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Rotator_stats.health;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        net.minecraft.entity.EntityLivingBase e = null;
        super.onUpdate();
        this.motionY *= 0.6;
        if (this.world.isRemote && this.world.rand.nextInt(10) == 1) {
            this.world.spawnParticle("fireworksSpark", this.posX, this.posY + (double)1.4f, this.posZ, (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 4.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 4.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 4.0f));
        }
        this.busy_fighting = 0;
        e = this.findSomethingToAttack();
        if (e != null) {
            double a = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
            this.world.spawnParticle("fireworksSpark", this.posX, this.posY + (double)1.4f, this.posZ, Math.cos(a), (e.posY - this.posY) / 10.0, Math.sin(a));
            this.busy_fighting = 1;
        }
        if (this.isNoDespawnRequired()) {
            return;
        }
        if (this.busy_fighting != 0) {
            return;
        }
        if (this.was_spawnered != 0) {
            return;
        }
        long t = this.world.getWorldTime();
        if ((t %= 24000L) < 12000L && this.world.rand.nextInt(400) == 1) {
            this.setDead();
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        net.minecraft.entity.EntityLivingBase e = null;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.rand.nextInt(300) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.1f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.rand.nextInt(10) + 8;
                xdir = this.rand.nextInt(10) + 8;
                if (this.rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }
                if (this.rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(6) - 3, (int)this.posZ + zdir);
                bid = this.world.getBlock(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        } else if (this.rand.nextInt(9) == 2 && (e = this.findSomethingToAttack()) != null) {
            double a = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)(e.posX + 2.5 * Math.cos(a += 1.5707963267948966)), (int)e.posY, (int)(e.posZ + 2.5 * Math.sin(a)));
            if (this.getDistanceSq((Entity)e) < 9.0) {
                this.attackEntityAsMob((Entity)e);
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.4 - this.motionX) * 0.2;
        this.motionY += (Math.signum(var3) * (double)0.7f - this.motionY) * 0.20000000149011612;
        this.motionZ += (Math.signum(var5) * 0.4 - this.motionZ) * 0.2;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.75f;
        this.rotationYaw += var8 / 4.0f;
    }

    protected boolean canTriggerWalking() {
        return true;
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
        if (e != null && e instanceof EntityArrow) {
            return false;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)e.posY, (int)e.posZ);
        }
        return ret;
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
                    String s = tileentitymobspawner.func_145881_a().getEntityNameToSpawn();
                    if (s == null || !s.equals("Rotator")) continue;
                    this.was_spawnered = 1;
                    return true;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        for (k = -1; k <= 1; ++k) {
            for (j = -1; j <= 1; ++j) {
                for (i = 1; i < 3; ++i) {
                    bid = this.world.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        long t = this.world.getWorldTime();
        return (t %= 24000L) >= 12000L;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Rotator_stats.defense;
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
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
        }
        if (par1EntityLiving instanceof Termite) {
            return false;
        }
        if (par1EntityLiving instanceof Vortex) {
            return false;
        }
        if (par1EntityLiving instanceof Rotator) {
            return false;
        }
        if (par1EntityLiving instanceof DungeonBeast) {
            return false;
        }
        if (par1EntityLiving instanceof Peacock) {
            return false;
        }
        if (par1EntityLiving instanceof CrystalCow) {
            return false;
        }
        if (par1EntityLiving instanceof Irukandji) {
            return false;
        }
        if (par1EntityLiving instanceof Skate) {
            return false;
        }
        if (par1EntityLiving instanceof Whale) {
            return false;
        }
        if (par1EntityLiving instanceof Flounder) {
            return false;
        }
        if (par1EntityLiving instanceof Urchin) {
            return false;
        }
        if (par1EntityLiving instanceof TerribleTerror) {
            return false;
        }
        if (par1EntityLiving instanceof LurkingTerror) {
            return false;
        }
        if (par1EntityLiving instanceof CloudShark) {
            return false;
        }
        if (par1EntityLiving instanceof Mothra) {
            return false;
        }
        if (par1EntityLiving instanceof Bee) {
            return false;
        }
        return !(par1EntityLiving instanceof Mantis);
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(12.0, 10.0, 12.0));
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

    protected Item getDropItem() {
        int i = this.world.rand.nextInt(4);
        if (i == 0) {
            return OreSpawnMain.MyCrystalPinkIngot;
        }
        if (i == 1) {
            return OreSpawnMain.MyTigersEyeIngot;
        }
        if (i == 2) {
            return Item.getItemFromBlock((Block)OreSpawnMain.CrystalCoal);
        }
        if (i == 3) {
            return Items.IRON_INGOT;
        }
        return null;
    }
}

