/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIAvoidEntity
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveIndoors
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITempt
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntitySmallFireball
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
import net.minecraft.world.EnumDifficulty;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Spyro extends EntityMob {
    public net.minecraft.util.math.BlockPos currentFlightTarget;
    private int activity = 0;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
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
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Spyro(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.5f);
        this.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3f);
        //this.fireResistance = 1000;
        this.isImmuneToFire = true;
        ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.setSitting(false);
        this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityMob.class, 8.0f, (double)0.3f, (double)0.4f));
        this.tasks.addTask(3, (EntityAIBase)new MyEntityAIFollowOwner(this, 1.15f, 12.0f, 2.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.25, Items.BEEF, false));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.5));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, (EntityAIBase)new MyEntityAIWander((EntityCreature)this, 0.75f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.tasks.addTask(9, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.experienceValue = 35;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.activity = 1;
//         this.dataManager.register(21, (Object)this.activity);
//         this.dataManager.register(20, (Object)1);
        this.setSitting(false);
        this.setTamed(false);
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("SpyroActivity", 0 /* this.dataManager.get(21) */);
        par1NBTTagCompound.setInteger("SpyroFire", 0 /* this.dataManager.get(20) */);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.activity = par1NBTTagCompound.getInteger("SpyroActivity");
//         this.dataManager.set(21, (Object)this.activity);
//         this.dataManager.set(20, (Object)par1NBTTagCompound.getInteger("SpyroFire"));
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
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dx * dx + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dy * dy + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        return found != 0;
    }

    public int getActivity() {
        int i = 0 /* this.dataManager.get(21) */;
        return i;
    }

    public void setActivity(int par1) {
        this.activity = par1;
//         this.dataManager.set(21, (Object)this.activity);
    }

    public int getSpyroFire() {
        return 0 /* this.dataManager.get(20) */;
    }

    public void setSpyroFire(int par1) {
//         this.dataManager.set(20, (Object)par1);
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public int mygetMaxHealth() {
        return 200;
    }

    public int getSpyroHealth() {
        return (int)this.getHealth();
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getCount() <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (var2 != null && var2.getItem() == Items.BEEF && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            if (!this.isTamed()) {
                if (!this.getEntityWorld().isRemote) {
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        this.setTamed(true);
                        this.func_152115_b(par1EntityPlayer.getUniqueID().toString());
                        this.playTameEffect(true);
                        this.getEntityWorld().setEntityState((Entity)this, (byte)7);
                        this.heal((float)this.mygetMaxHealth() - this.getHealth());
                    } else {
                        this.playTameEffect(false);
                        this.getEntityWorld().setEntityState((Entity)this, (byte)6);
                    }
                }
            } else if (this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
                if (this.getEntityWorld().isRemote) {
                    this.playTameEffect(true);
                    this.getEntityWorld().setEntityState((Entity)this, (byte)7);
                }
                if ((float)this.mygetMaxHealth() > this.getHealth()) {
                    this.heal((float)this.mygetMaxHealth() - this.getHealth());
                }
            }
            if (!par1EntityPlayer.isCreative()) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DEADBUSH) && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
            if (!this.getEntityWorld().isRemote) {
                this.setTamed(false);
                this.setHealth(this.mygetMaxHealth());
                this.func_152115_b("");
                this.playTameEffect(false);
                this.getEntityWorld().setEntityState((Entity)this, (byte)6);
            }
            if (!par1EntityPlayer.isCreative()) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.ICE) && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
            if (!this.getEntityWorld().isRemote) {
                this.playTameEffect(true);
                this.getEntityWorld().setEntityState((Entity)this, (byte)6);
                this.setSpyroFire(0);
                par1EntityPlayer.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString("Baby Dragon fireballs extinguished."));
            }
            if (!par1EntityPlayer.isCreative()) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Items.DIAMOND && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer) && !this.getEntityWorld().isRemote) {
            Entity ent = null;
            Dragon d = null;
            ent = Spyro.spawnCreature(this.getEntityWorld(), "Dragon", this.posX, this.posY, this.posZ);
            if (ent != null) {
                d = (Dragon)ent;
                if (this.isTamed()) {
                    d.setTamed(true);
                    d.func_152115_b(par1EntityPlayer.getUniqueID().toString());
                }
                this.setDead();
            }
            if (!par1EntityPlayer.isCreative()) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Items.FLINT_AND_STEEL && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
            if (!this.getEntityWorld().isRemote) {
                this.playTameEffect(true);
                this.getEntityWorld().setEntityState((Entity)this, (byte)6);
                this.setSpyroFire(1);
                par1EntityPlayer.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString("Baby Dragon fireballs lit!"));
            }
            if (!par1EntityPlayer.isCreative()) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Items.NAME_TAG && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
            this.setCustomNameTag(var2.getDisplayName());
            if (!par1EntityPlayer.isCreative()) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
            if (!this.isSitting()) {
                this.setSitting(true);
            } else {
                this.setSitting(false);
            }
            return true;
        }
        return super.interact(par1EntityPlayer);
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.BEEF;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return !this.isTamed();
    }

    public int getTotalArmorValue() {
        return 5;
    }

    protected String getLivingSound() {
        if (this.isSitting()) {
            return null;
        }
        if (this.getActivity() != 2) {
            return null;
        }
        return "orespawn:roar";
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    protected float getSoundVolume() {
        return 0.4f;
    }

    protected Item getDropItem() {
        return Items.BEEF;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var3 = 0;
        if (this.isTamed()) {
            var3 = this.getEntityWorld().rand.nextInt(4);
            ++var3;
            for (int var4 = 0; var4 < var3; ++var4) {
                this.dropItem(Items.BEEF, 1);
            }
        }
    }

    protected float getSoundPitch() {
        return this.isChild() ? (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.1f + 1.5f : (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.1f + 1.0f;
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

    public boolean getCanSpawnHere() {
        if (!this.getEntityWorld().isDaytime()) {
            return false;
        }
        return !(this.posY < 50.0);
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    public float getAttackStrength(Entity par1Entity) {
        return 4.0f;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        float var2 = this.getAttackStrength(par1Entity);
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), var2);
        return var4;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (!par1DamageSource.getDamageType().equals("cactus")) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
        }
        return ret;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.getEntityWorld().rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    public void onUpdate() {
        net.minecraft.entity.EntityLivingBase e;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
        if (this.isInWater()) {
            this.motionY += 0.07;
        }
        if (this.getEntityWorld().isRemote) {
            return;
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.getEntityWorld().rand.nextInt(100000) == 1 && !this.isNoDespawnRequired()) {
            Entity ent = null;
            Dragon d = null;
            ent = Spyro.spawnCreature(this.getEntityWorld(), "Dragon", this.posX, this.posY, this.posZ);
            if (ent != null) {
                d = (Dragon)ent;
                if (this.isTamed()) {
                    d.setTamed(true);
                    d.func_152115_b(this.getUniqueID());
                }
                this.setDead();
                return;
            }
        }
        if (this.activity == 2) {
            this.motionY = this.posY < (double)this.currentFlightTarget.getY() + 2.0 ? (this.motionY *= 0.7) : (this.posY > (double)this.currentFlightTarget.getY() - 2.0 ? (this.motionY *= 0.5) : (this.motionY *= 0.61));
        }
        if (this.activity == 1 && this.isTamed() && this.getOwner() != null && this.getDistanceSq((Entity)(e = this.getOwner())) > 256.0) {
            this.setActivity(2);
        }
        this.do_movement();
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected void updateAITick() {
        boolean fly = false;
        if (this.activity == 1) {
            super.updateAITick();
        }
        if (this.isDead()) {
            return;
        }
        if (this.getEntityWorld().rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        if (this.getEntityWorld().isRemote) {
            return;
        }
        if (!this.isSitting()) {
            if (this.activity == 0) {
                this.activity = 1;
            }
            if (this.getEntityWorld().rand.nextInt(20) == 0) {
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
                    this.setActivity(1);
                    this.getNavigator().tryMoveToXYZ((double)this.tx, (double)(this.ty - 1), (double)this.tz, 1.0);
                    if (this.handleLavaMovement()) {
                        this.heal(1.0f);
                        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("splash")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, this.getEntityWorld().rand.nextFloat() * 0.2f + 0.9f));
                    }
                }
            }
            if (this.getEntityWorld().rand.nextInt(100) == 1 && !this.target_in_sight) {
                this.activity = 1;
                if (this.getEntityWorld().rand.nextInt(8) == 1) {
                    this.activity = 2;
                }
                this.setActivity(this.activity);
            }
            this.owner_flying = 0;
            if (this.isTamed() && this.getOwner() != null) {
                net.minecraft.entity.player.EntityPlayer e = (net.minecraft.entity.player.EntityPlayer)this.getOwner();
                if (e.capabilities.isFlying) {
                    fly = true;
                }
                if (fly) {
                    this.owner_flying = 1;
                    this.setActivity(2);
                }
            }
        }
    }

    protected void updateAITasks() {
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.getEntityWorld().rand.nextInt(100) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(1.0f);
        }
    }

    private void do_movement() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        boolean do_new = false;
        double ox = 0.0;
        double oy = 0.0;
        double oz = 0.0;
        boolean has_owner = false;
        net.minecraft.entity.EntityLivingBase e = null;
        if (this.currentFlightTarget == null) {
            do_new = true;
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.isSitting()) {
            return;
        }
        if (this.activity == 1) {
            return;
        }
        if (this.getActivity() == 2 && this.getEntityWorld().rand.nextInt(300) == 0) {
            do_new = true;
        }
        if (this.isTamed() && this.getOwner() != null) {
            e = this.getOwner();
            has_owner = true;
            ox = e.posX;
            oy = e.posY;
            oz = e.posZ;
            if (this.getDistanceSq((Entity)e) > 100.0) {
                do_new = true;
            }
            if (this.owner_flying != 0 && this.getDistanceSq((Entity)e) > 36.0) {
                do_new = true;
            }
        }
        if (this.getEntityWorld().rand.nextInt(6) == 1 && this.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL) {
            e = this.findSomethingToAttack();
            if (e != null) {
                if (this.isTamed() && this.getHealth() / (float)this.mygetMaxHealth() < 0.25f) {
                    this.setActivity(2);
                    this.target_in_sight = false;
                    do_new = false;
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)(this.posX + (this.posX - e.posX)), (int)(this.posY + 1.0), (int)(this.posZ + (this.posZ - e.posZ)));
                } else {
                    this.setActivity(2);
                    this.target_in_sight = true;
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.25);
                    do_new = false;
                    if (this.getDistanceSq((Entity)e) < (double)((3.0f + e.width / 2.0f) * (3.0f + e.width / 2.0f))) {
                        this.attackEntityAsMob((Entity)e);
                    } else if (this.getDistanceSq((Entity)e) < 64.0 && !this.isInWater() && (this.getSpyroFire() == 1 && this.getEntityWorld().rand.nextInt(10) == 0 || this.getEntityWorld().rand.nextInt(15) == 1)) {
                        EntitySmallFireball var2 = new EntitySmallFireball(this.getEntityWorld(), (net.minecraft.entity.EntityLivingBase)this, e.posX - this.posX, e.posY + 0.25 - (this.posY + 1.25), e.posZ - this.posZ);
                        var2.setLocationAndAngles(this.posX, this.posY + 1.25, this.posZ, this.rotationYaw, this.rotationPitch);
                        this.getEntityWorld().playSound(null, (Entity)this.posX, (Entity)this.posY, (Entity)this.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.HOSTILE, 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                        this.getEntityWorld().spawnEntity((Entity)var2);
                    }
                }
            } else {
                this.target_in_sight = false;
            }
        }
        if (this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.1f && this.getActivity() != 3) {
            do_new = true;
        }
        if (do_new && !this.target_in_sight) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                int gox = (int)this.posX;
                int goy = (int)this.posY;
                int goz = (int)this.posZ;
                if (has_owner) {
                    gox = (int)ox;
                    goy = (int)oy;
                    goz = (int)oz;
                    if (this.owner_flying == 0) {
                        zdir = this.getEntityWorld().rand.nextInt(4) + 6;
                        xdir = this.getEntityWorld().rand.nextInt(4) + 6;
                    } else {
                        zdir = this.getEntityWorld().rand.nextInt(6);
                        xdir = this.getEntityWorld().rand.nextInt(6);
                    }
                } else {
                    zdir = this.getEntityWorld().rand.nextInt(5) + 6;
                    xdir = this.getEntityWorld().rand.nextInt(5) + 6;
                }
                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }
                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos(gox + xdir, goy + this.getEntityWorld().rand.nextInt(9 + this.owner_flying * 2) - 4, goz + zdir);
                bid = this.getEntityWorld().getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        }
        double speed_factor = 0.5;
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        if (this.owner_flying != 0) {
            speed_factor = 1.75;
            if (this.isTamed() && this.getOwner() != null && this.getDistanceSq((Entity)(e = this.getOwner())) > 49.0) {
                speed_factor = 3.5;
            }
        }
        this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * 0.15 * speed_factor;
        this.motionY += (Math.signum(var3) * 0.7 - this.motionY) * 0.21 * speed_factor;
        this.motionZ += (Math.signum(var5) * 0.5 - this.motionZ) * 0.15 * speed_factor;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = (float)(0.75 * speed_factor);
        this.rotationYaw += var8 / 3.0f;
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
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
        if (par1EntityLiving instanceof Spyro) {
            return false;
        }
        if (par1EntityLiving instanceof Mothra) {
            return true;
        }
        return par1EntityLiving instanceof EntityMob;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(12.0, 6.0, 12.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false) || !this.canSeeTarget(var4.posX, var4.posY, var4.posZ)) continue;
            return var4;
        }
        return null;
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
}


}