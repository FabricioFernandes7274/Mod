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
 *  net.minecraft.entity.ai.EntityAIMoveThroughVillage
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Rat extends EntityMob {

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
    private String myowner = null;

    public Rat(World worldIn) {
        super(worldIn);
        this.setSize(0.25f, 0.5f);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 5;
        //this.fireResistance = 10;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, (double)1.35f));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.tasks.addTask(3, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 10, 1.0));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 8.0f));
        this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.TargetSorter = new GenericTargetSorter((Entity)this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Rat_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)0);
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return this.myowner == null;
    }

    public final int getAttacking() {
        return this.dataManager.get(20);
    }

    public final void setAttacking(int par1) {
        this.dataManager = new net.minecraft.util.math.BlockPos(20, (Object)((byte)par1));
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Rat_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Rat_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
        this.posY += 0.25;
    }

    protected String getLivingSound() {
        return "orespawn:ratlive";
    }

    protected String getHurtSound() {
        return "orespawn:rathit";
    }

    protected String getDeathSound() {
        return "orespawn:ratdead";
    }

    protected float getSoundVolume() {
        return 0.45f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.ROTTEN_FLESH;
    }

    public void initCreature() {
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    protected void updateAITasks() {
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        if (this.world.rand.nextInt(5) == 1) {
            net.minecraft.entity.EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.setAttacking(1);
                this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.25);
                if (this.getDistanceSq((Entity)e) < 4.0 && (this.rand.nextInt(8) == 0 || this.rand.nextInt(7) == 1)) {
                    this.attackEntityAsMob((Entity)e);
                }
            } else {
                net.minecraft.entity.player.EntityPlayer p;
                this.setAttacking(0);
                if (this.myowner != null && (p = this.world.getPlayerEntityByName(this.myowner)) != null) {
                    if (this.getDistanceSq((Entity)p) > 64.0) {
                        this.getNavigator().tryMoveToEntityLiving((Entity)p, 1.75);
                    }
                    if (this.getDistanceSq((Entity)p) > 256.0) {
                        this.setPosition(p.posX + (double)this.world.rand.nextFloat() - (double)this.world.rand.nextFloat(), p.posY, p.posZ + (double)this.world.rand.nextFloat() - (double)this.world.rand.nextFloat());
                    }
                }
            }
        }
        if (this.world.rand.nextInt(250) == 1) {
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
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
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
        if (par1EntityLiving instanceof Rat) {
            return false;
        }
        if (par1EntityLiving instanceof Ghost) {
            return false;
        }
        if (par1EntityLiving instanceof GhostSkelly) {
            return false;
        }
        if (par1EntityLiving instanceof DungeonBeast) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.isCreative()) {
                return false;
            }
            if (this.myowner != null) {
                if (this.myowner.equals(p.getUniqueID().toString())) {
                    return false;
                }
                if (OreSpawnMain.RatPlayerFriendly != 0) {
                    return false;
                }
            }
        }
        if (this.myowner != null && par1EntityLiving instanceof EntityTameable) {
            EntityTameable e = (EntityTameable)par1EntityLiving;
            if (OreSpawnMain.RatPetFriendly != 0 && e.isTamed()) {
                return false;
            }
            if (e.getUniqueID() != null && this.myowner.equals(e.getUniqueID())) {
                return false;
            }
        }
        return true;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(9.0, 2.0, 9.0));
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

    public void setOwner(net.minecraft.entity.EntityLivingBase e) {
        String s;
        net.minecraft.entity.player.EntityPlayer p = null;
        if (e != null && e instanceof net.minecraft.entity.player.EntityPlayer && (s = (p = (net.minecraft.entity.player.EntityPlayer)e).getUniqueID().toString()) != null) {
            this.myowner = s;
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.myowner == null) {
            this.myowner = "null";
        }
        par1NBTTagCompound.setString("MyOwner", this.myowner);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.myowner = par1NBTTagCompound.getString("MyOwner");
        if (this.myowner != null && this.myowner.equals("null")) {
            this.myowner = null;
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return ret;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        return ret;
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int j;
        int k;
        int sc = 0;
        for (k = -2; k < 2; ++k) {
            for (j = -2; j < 2; ++j) {
                for (int i = 0; i < 5; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)).getBlock()this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Rat")) continue;
                    return true;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID5) {
            if (this.posY > 50.0) {
                return false;
            }
            for (k = -1; k <= 1; ++k) {
                for (j = -1; j <= 1; ++j) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + 1, (int)).getBlock()this.posZ + k);
                    if (bid != Blocks.AIR) continue;
                    ++sc;
                }
            }
            if (sc < 4) {
                return false;
            }
        }
        return this.findBuddies() <= 8;
    }

    private int findBuddies() {
        List var5 = this.world.getEntitiesWithinAABB(Rat.class, this.getEntityBoundingBox().expand(20.0, 10.0, 20.0));
        return var5.size();
    }
}


}