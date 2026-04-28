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
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.DamageSource
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
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Urchin
extends EntityMob {
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private float moveSpeed = 0.3f;
    private int was_spawnered = 0;

    public Urchin(World par1World) {
        super(par1World);
        this.setSize(1.35f, 2.1f);
        this.getNavigator().setAvoidsWater(true);
        this.experienceValue = 20;
        //this.fireResistance = 1000;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 14, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Urchin_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)0);
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

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return this.was_spawnered == 0;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
        if (this.isNoDespawnRequired()) {
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

    public int mygetMaxHealth() {
        return OreSpawnMain.Urchin_stats.health;
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
        return OreSpawnMain.Urchin_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.world.rand.nextInt(3) == 1) {
            this.world.spawnParticle("flame", this.posX, this.posY + 0.75, this.posZ, 0.0, (double)(this.world.rand.nextFloat() / 10.0f), 0.0);
            if (this.isInWater() && this.world.rand.nextInt(5) == 1) {
                this.attackEntityAsMob((Entity)this);
                this.world.spawnParticle("smoke", this.posX, this.posY + 1.75, this.posZ, 0.0, (double)(this.world.rand.nextFloat() / 10.0f), 0.0);
                this.world.spawnParticle("largesmoke", this.posX, this.posY + 1.75, this.posZ, 0.0, (double)(this.world.rand.nextFloat() / 10.0f), 0.0);
                this.world.spawnParticle("smoke", this.posX, this.posY + 2.0, this.posZ, 0.0, (double)(this.world.rand.nextFloat() / 10.0f), 0.0);
                this.world.spawnParticle("largesmoke", this.posX, this.posY + 2.0, this.posZ, 0.0, (double)(this.world.rand.nextFloat() / 10.0f), 0.0);
            }
        }
    }

    protected String getLivingSound() {
        return "orespawn:kyuubi_living";
    }

    protected String getHurtSound() {
        return "orespawn:glasshit";
    }

    protected String getDeathSound() {
        return "orespawn:glassdead";
    }

    protected float getSoundVolume() {
        return 1.1f;
    }

    protected float getSoundPitch() {
        return 1.25f;
    }

    protected Item getDropItem() {
        int i = this.world.rand.nextInt(3);
        if (i == 1) {
            return OreSpawnMain.MyCrystalPinkIngot;
        }
        if (i == 2) {
            return OreSpawnMain.MyCrystalApple;
        }
        return null;
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        par1Entity.setFire(5);
        return super.attackEntityAsMob(par1Entity);
    }

    protected void updateAITasks() {
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.world.rand.nextInt(8) == 0) {
            net.minecraft.entity.EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                if (this.getDistanceSq((Entity)e) < 8.0) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(7) == 0 || this.world.rand.nextInt(8) == 1) {
                        this.attackEntityAsMob((Entity)e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.2);
                }
            } else {
                this.setAttacking(0);
            }
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (!par1DamageSource.getDamageType().equals("cactus")) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
        }
        return ret;
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
        if (par1EntityLiving instanceof Vortex) {
            return false;
        }
        if (par1EntityLiving instanceof Rotator) {
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
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.isCreative()) {
                return false;
            }
        }
        return true;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(16.0, 3.0, 16.0));
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

    public final int getAttacking() {
        return this.dataManager.get(20);
    }

    public final void setAttacking(int par1) {
        this.dataManager.set(20, (Object)((byte)par1));
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int j;
        int k;
        int sc = 0;
        for (k = -2; k <= 2; ++k) {
            for (j = -2; j <= 2; ++j) {
                for (int i = 1; i < 4; ++i) {
                    bid = this.world.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Crystal Urchin")) continue;
                    this.was_spawnered = 1;
                    return true;
                }
            }
        }
        for (k = -1; k <= 1; ++k) {
            for (j = -1; j <= 1; ++j) {
                bid = this.world.getBlock((int)this.posX + j, (int)this.posY + 1, (int)this.posZ + k);
                if (bid != Blocks.AIR) continue;
                ++sc;
            }
        }
        if (sc < 6) {
            return false;
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        long t = this.world.getWorldTime();
        return (t %= 24000L) >= 13000L;
    }
}

