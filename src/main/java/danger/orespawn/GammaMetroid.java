/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  net.minecraft.entity.ai.EntityAIMate
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITempt
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.EnumSkyBlock
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GammaMetroid extends EntityMob {

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
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public GammaMetroid(World worldIn) {
        super(worldIn);
        this.setSize(1.5f, 1.5f);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 20;
        //this.fireResistance = 1000;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIFollowOwner(this, 2.0f, 10.0f, 2.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, (double)1.2f, Items.IRON_INGOT, false));
        this.tasks.addTask(4, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.GammaMetroid_stats.attack);
    }

    protected boolean canDespawn() {
        if (this.isChild()) {
            this.enablePersistence();
            return false;
        }
        if (this.isTamed()) {
            return false;
        }
        return !this.isNoDespawnRequired();
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)OreSpawnMain.GammaMetroid_stats.attack);
        return var4;
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (super.interact(par1EntityPlayer)) {
            return true;
        }
        if (var2 != null && var2.getItem() == Items.IRON_INGOT && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
            if (!this.isTamed()) {
                if (!this.world.isRemote) {
                    if (this.rand.nextInt(3) == 0) {
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
            } else if (this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                if ((float)this.mygetMaxHealth() > this.getHealth()) {
                    this.heal((float)this.mygetMaxHealth() - this.getHealth());
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
        if (this.isTamed() && var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DEADBUSH) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
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
        if (this.isTamed() && var2 != null && var2.getItem() == Items.NAME_TAG && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer)) {
            this.setCustomNameTag(var2.getDisplayName());
            if (!par1EntityPlayer.isCreative()) {
                --var2.stackSize;
                if (var2.stackSize <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && this.getGameProfile((net.minecraft.entity.EntityLivingBase)par1EntityPlayer) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
            if (!this.isSitting()) {
                this.setSitting(true);
            } else {
                this.setSitting(false);
            }
            return true;
        }
        return false;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.GammaMetroid_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.GammaMetroid_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        if (this.world.rand.nextInt(5) == 1) {
            return "orespawn:wtf_living";
        }
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:duck_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    protected float getSoundVolume() {
        return 1.5f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.IRON_INGOT;
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), new ItemStack(index, par1, 0));
        this.world.spawnEntity((Entity)var3);
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        int i = 5 + OreSpawnMain.OreSpawnRand.nextInt(10);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.GOLD_NUGGET, 1);
        }
        i = 6 + OreSpawnMain.OreSpawnRand.nextInt(10);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.IRON_INGOT, 1);
        }
    }

    protected void updateAITasks() {
        net.minecraft.entity.EntityLivingBase e;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(5) == 0 && (e = this.findSomethingToAttack()) != null) {
            this.faceEntity((Entity)e, 10.0f, 10.0f);
            if (this.getDistanceSq((Entity)e) <= 9.0) {
                if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                    this.attackEntityAsMob((Entity)e);
                }
            } else {
                this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.25);
            }
        }
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
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof GammaMetroid) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return false;
        }
        if (this.isTamed()) {
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
        if (this.isChild()) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(10.0, 3.0, 10.0));
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

    protected boolean isValidLightLevel() {
        int k;
        int j;
        int i = net.minecraft.util.math.MathHelper.floor_double((double)this.posX);
        if (this.world.getSavedLightValue(EnumSkyBlock.Sky, i, j = net.minecraft.util.math.MathHelper.floor_double((double)this.boundingBox.minY), k = net.minecraft.util.math.MathHelper.floor_double((double)this.posZ)) > this.rand.nextInt(32)) {
            return false;
        }
        int l = this.world.getBlockLightValue(i, j, k);
        if (this.world.isThundering()) {
            int i1 = this.world.skylightSubtracted;
            this.world.skylightSubtracted = 10;
            l = this.world.getBlockLightValue(i, j, k);
            this.world.skylightSubtracted = i1;
        }
        return l <= this.rand.nextInt(8);
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int i;
        int j;
        int k;
        for (k = -3; k < 3; ++k) {
            for (j = -3; j < 3; ++j) {
                for (i = 0; i < 5; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)).getBlock()this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("WTF?")) continue;
                    return true;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID4) {
            return true;
        }
        if (this.posY > 50.0) {
            return false;
        }
        for (k = -1; k < 1; ++k) {
            for (j = -1; j < 1; ++j) {
                for (i = 1; i < 4; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)).getBlock()this.posZ + k);
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
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
                if (bid == Blocks.STONE && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.STONE || (d = dx * dx + j * j + i * i) >= this.closest) continue;
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
                if (bid == Blocks.STONE && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.STONE || (d = dy * dy + j * j + i * i) >= this.closest) continue;
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
                if (bid == Blocks.STONE && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.STONE || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        return found != 0;
    }

    protected void updateAITick() {
        if (this.isDead()) {
            return;
        }
        super.updateAITick();
        if ((this.world.rand.nextInt(20) == 0 && this.getHealth() < (float)this.mygetMaxHealth() || this.world.rand.nextInt(100) == 0) && OreSpawnMain.PlayNicely == 0 && !this.isSitting()) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 1; i < 6; ++i) {
                int j = i;
                if (j > 2) {
                    j = 2;
                }
                if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) break;
                if (i < 4) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)this.ty, (double)this.tz, 1.0);
                if (this.closest < 12) {
                    if (this.world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                        this.world.setBlockState(new net.minecraft.util.math.BlockPos(this.tx, this.ty, this.tz), Blocks.AIR.getDefaultState(), 2);
                    }
                    this.heal(1.0f);
                    this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.burp")), net.minecraft.util.SoundCategory.NEUTRAL, 0.5f, this.world.rand.nextFloat() * 0.2f + 1.5f));
                }
            }
        }
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public GammaMetroid spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        GammaMetroid w = new GammaMetroid(this.world);
        if (this.isTamed()) {
            this.func_152115_b(this.getUniqueID());
            w.setTamed(true);
        }
        return w;
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.IRON_INGOT;
    }

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == OreSpawnMain.MyCrystalApple;
    }
}


}