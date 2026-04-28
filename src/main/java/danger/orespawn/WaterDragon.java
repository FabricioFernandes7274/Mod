/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
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
 *  net.minecraft.entity.projectile.EntitySmallFireball
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
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
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WaterDragon
extends EntityTameable {
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int stream_count = 0;
    private int hurt_timer = 0;
    private float moveSpeed = 0.25f;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public WaterDragon(World worldIn) {
        super(worldIn);
        this.setSize(1.25f, 1.9f);
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 100;
        //this.fireResistance = 3;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIFollowOwner(this, 2.0f, 10.0f, 2.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, (double)1.2f, Items.FISH, false));
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
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.WaterDragon_stats.attack);
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

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (super.interact(par1EntityPlayer)) {
            return true;
        }
        if (var2 != null && var2.getItem() == Items.FISH && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
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

    protected boolean canDespawn() {
        if (this.isChild()) {
            this.enablePersistence();
            return false;
        }
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return !this.isTamed();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.WaterDragon_stats.health;
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
        return OreSpawnMain.WaterDragon_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.moveSpeed = this.isInWater() ? 0.55f : 0.25f;
    }

    public int getWaterDragonHealth() {
        return (int)this.getHealth();
    }

    public int getAttackStrength(Entity par1Entity) {
        int var2 = 4;
        if (this.world.getDifficulty() == EnumDifficulty.EASY) {
            var2 = 6;
            if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                var2 = 8;
            } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                var2 = 10;
            }
        }
        return var2;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:waterdragon_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:waterdragon_death";
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.FISH;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(OreSpawnMain.MyWaterDragonScale, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int var5 = 9 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.FISH, 1);
        }
        var4 = this.world.rand.nextInt(20);
        switch (var4) {
            case 0: {
                is = this.dropItemRand(OreSpawnMain.MyUltimateAxe, 1);
                break;
            }
            case 1: {
                is = this.dropItemRand(Items.IRON_INGOT, 1);
                break;
            }
            case 2: {
                is = this.dropItemRand(OreSpawnMain.MyUltimatePickaxe, 1);
                break;
            }
            case 3: {
                is = this.dropItemRand(Items.IRON_SWORD, 1);
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 4: {
                is = this.dropItemRand(Items.IRON_SHOVEL, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 5: {
                is = this.dropItemRand(Items.IRON_PICKAXE, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 6: {
                is = this.dropItemRand(Items.IRON_AXE, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 7: {
                is = this.dropItemRand(Items.IRON_HOE, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 8: {
                is = this.dropItemRand((Item)Items.IRON_HELMET, 1);
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 9: {
                is = this.dropItemRand((Item)Items.IRON_CHESTPLATE, 1);
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                break;
            }
            case 10: {
                is = this.dropItemRand((Item)Items.IRON_LEGGINGS, 1);
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                break;
            }
            case 11: {
                is = this.dropItemRand((Item)Items.IRON_BOOTS, 1);
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                break;
            }
            case 12: {
                is = this.dropItemRand(OreSpawnMain.MyUltimateShovel, 1);
                break;
            }
            case 13: {
                this.dropItemRand(Item.getItemFromBlock((Block)Blocks.IRON_BLOCK), 1);
                break;
            }
            case 14: {
                break;
            }
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)OreSpawnMain.WaterDragon_stats.attack);
        if (var4) {
            if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
                double ks = 1.1;
                double inair = 0.14;
                float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
                if (par1Entity.isDead() || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                    inair *= 2.0;
                }
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            return true;
        }
        return false;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (par1DamageSource.getDamageType().equals("cactus")) {
            return false;
        }
        Entity e = par1DamageSource.getEntity();
        if (e != null && e instanceof WaterDragon) {
            return false;
        }
        if (e != null && e instanceof AttackSquid) {
            return false;
        }
        if (e != null && e instanceof WaterBall) {
            return false;
        }
        if (this.hurt_timer <= 0) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
            this.hurt_timer = 10;
        }
        if (e != null && e instanceof EntityLiving) {
            if (e instanceof AttackSquid) {
                return false;
            }
            if (e instanceof WaterDragon) {
                return false;
            }
            this.setAttackTarget((net.minecraft.entity.EntityLivingBase)((EntityLiving)e));
            this.setTarget(e);
            this.getNavigator().tryMoveToEntityLiving((Entity)((EntityLiving)e), 1.2);
        }
        return ret;
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
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dx * dx + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dy * dy + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        return found != 0;
    }

    protected void updateAITasks() {
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (!this.isInWater() && this.world.rand.nextInt(25) == 0 && !this.isSitting()) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 1; i < 12; ++i) {
                int j = i;
                if (j > 10) {
                    j = 10;
                }
                if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) break;
                if (i < 5) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)(this.ty - 1), (double)this.tz, 1.33);
            } else {
                if (this.world.rand.nextInt(50) == 1) {
                    this.heal(-1.0f);
                }
                if (this.getHealth() <= 0.0f) {
                    this.setDead();
                    return;
                }
            }
        }
        if (this.world.rand.nextInt(200) == 0) {
            this.setAttackTarget(null);
        }
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(5) == 1) {
            net.minecraft.entity.EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getDistanceSq((Entity)e) < (double)((4.0f + e.width / 2.0f) * (4.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                        this.attackEntityAsMob((Entity)e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.0);
                    this.watercanon(e);
                }
            } else {
                this.setAttacking(0);
            }
        }
        if (this.world.rand.nextInt(100) == 1 && this.isInWater() && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("splash")), net.minecraft.util.SoundCategory.NEUTRAL, 1.5f, this.world.rand.nextFloat() * 0.2f + 0.9f));
            this.heal(1.0f);
        }
    }

    private void watercanon(net.minecraft.entity.EntityLivingBase e) {
        double yoff = 1.75;
        double xzoff = 1.5;
        if (this.stream_count > 0) {
            WaterBall var2;
            this.setAttacking(2);
            if (this.rand.nextInt(15) == 1) {
                var2 = new EntitySmallFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, e.posX - this.posX, e.posY + 0.75 - (this.posY + yoff), e.posZ - this.posZ);
                var2.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYawHead)), this.rotationYaw, this.rotationPitch);
                this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity((Entity)var2);
            }
            var2 = new WaterBall(this.world, e.posX - this.posX, e.posY + 0.75 - (this.posY + yoff), e.posZ - this.posZ);
            var2.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
            double var3 = e.posX - var2.posX;
            double var5 = e.posY + 0.25 - var2.posY;
            double var7 = e.posZ - var2.posZ;
            float var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var7 * var7)) * 0.2f;
            var2.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
            this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity((Entity)var2);
            --this.stream_count;
        } else {
            this.setAttacking(0);
        }
        if (this.stream_count <= 0 && this.rand.nextInt(4) == 1) {
            this.stream_count = 8;
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
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof WaterDragon) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (this.isTamed()) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.isCreative();
        }
        return MyUtils.isAttackableNonMob(par1EntityLiving);
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        if (this.isChild()) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(14.0, 4.0, 14.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        net.minecraft.entity.EntityLivingBase e = this.getAttackTarget();
        if (e != null && e.isEntityAlive()) {
            return e;
        }
        this.setAttackTarget(null);
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false)) continue;
            return var4;
        }
        return null;
    }

    public int getAttacking() {
        return this.dataManager.get(20);
    }

    public void setAttacking(int par1) {
        this.dataManager.set(20, (Object)((byte)par1));
    }

    public boolean getCanSpawnHere() {
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)).getBlock()this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Water Dragon")) continue;
                    return true;
                }
            }
        }
        WaterDragon target = null;
        if (this.posY < 50.0) {
            return false;
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        target = (WaterDragon)this.world.findNearestEntityWithinAABB(WaterDragon.class, this.getEntityBoundingBox().expand(16.0, 5.0, 16.0), (Entity)this);
        return target == null;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public WaterDragon spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        WaterDragon w = new WaterDragon(this.world);
        if (this.isTamed()) {
            this.func_152115_b(this.getUniqueID());
            w.setTamed(true);
        }
        return w;
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.FISH;
    }

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }
}

