/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityCaveSpider
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySpider
 *  net.minecraft.entity.monster.EntityZombie
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AttackSquid
extends EntityMob {
    private GenericTargetSorter TargetSorter = null;
    private net.minecraft.entity.EntityLivingBase buddy = null;
    private float moveSpeed = 0.25f;
    private int wasshot = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public AttackSquid(World par1World) {
        super(par1World);
        this.setSize(1.0f, 1.25f);
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 15;
        //this.fireResistance = 3;
        this.isImmuneToFire = false;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.AttackSquid_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)0);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void setWasShot() {
        this.wasshot = 250;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.AttackSquid_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.AttackSquid_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public int getAttackStrength(Entity par1Entity) {
        int var2 = 2;
        return var2;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:squid_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:squid_death";
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
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
        ItemStack is = null;
        int var4 = this.world.rand.nextInt(50);
        switch (var4) {
            case 0: {
                is = this.dropItemRand(Items.GOLD_NUGGET, 1);
                break;
            }
            case 1: {
                is = this.dropItemRand(Items.GOLD_INGOT, 1);
                break;
            }
            case 2: {
                is = this.dropItemRand(Items.GOLDEN_CARROT, 1);
                break;
            }
            case 3: {
                is = this.dropItemRand(Items.GOLDEN_SWORD, 1);
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
                is = this.dropItemRand(Items.GOLDEN_SHOVEL, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 5: {
                is = this.dropItemRand(Items.GOLDEN_PICKAXE, 1);
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
                is = this.dropItemRand(Items.GOLDEN_AXE, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 7: {
                is = this.dropItemRand(Items.GOLDEN_HOE, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 8: {
                is = this.dropItemRand((Item)Items.GOLDEN_HELMET, 1);
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
                is = this.dropItemRand((Item)Items.GOLDEN_CHESTPLATE, 1);
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
                is = this.dropItemRand((Item)Items.GOLDEN_LEGGINGS, 1);
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
                is = this.dropItemRand((Item)Items.GOLDEN_BOOTS, 1);
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                break;
            }
            case 12: {
                this.dropItemRand(Items.GOLDEN_APPLE, 1);
                break;
            }
            case 13: {
                this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GOLD_BLOCK), 1);
                break;
            }
            case 14: {
                EntityItem var3 = null;
                is = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
                var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(3) - (double)OreSpawnMain.OreSpawnRand.nextInt(3), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(3) - (double)OreSpawnMain.OreSpawnRand.nextInt(3), is);
                if (var3 == null) break;
                this.world.spawnEntity((Entity)var3);
                break;
            }
            case 15: 
            case 16: 
            case 17: {
                this.dropItemRand(Items.DYE, 1);
                break;
            }
        }
        int i = 1 + this.world.rand.nextInt(3);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.FISH, 1);
        }
    }

    public void initCreature() {
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1net.minecraft.entity.player.EntityPlayer) {
        return false;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        return super.attackEntityAsMob(par1Entity);
    }

    protected void fall(float par1) {
        if (this.wasshot != 0) {
            return;
        }
        super.fall(par1);
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (this.isDead()) {
            return false;
        }
        Entity e = par1DamageSource.getEntity();
        if (e != null && e instanceof AttackSquid) {
            return false;
        }
        if (e != null && e instanceof WaterBall) {
            return false;
        }
        if (e != null && e instanceof WaterDragon) {
            return false;
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
            ret = true;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        if ((this.getHealth() <= 0.0f || this.isDead()) && this.world.provider.getDimension() != OreSpawnMain.DimensionID5 && !this.world.isRemote && e != null && e instanceof net.minecraft.entity.player.EntityPlayer && this.world.rand.nextInt(15) == 1 && OreSpawnMain.KrakenEnable != 0 && this.wasshot == 0) {
            int j = 1 + this.world.rand.nextInt(3);
            for (int i = 0; i < j; ++i) {
                EntityCreature newent = (EntityCreature)AttackSquid.spawnCreature(this.world, "The Kraken", this.posX + (double)this.world.rand.nextInt(4) - (double)this.world.rand.nextInt(4), 170.0, this.posZ + (double)this.world.rand.nextInt(4) - (double)this.world.rand.nextInt(4));
            }
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
        if (this.wasshot > 0) {
            --this.wasshot;
            if (this.wasshot == 0) {
                this.setDead();
                return;
            }
        }
        if (!this.isInWater() && this.world.rand.nextInt(10) == 0) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 1; i < 12; ++i) {
                int j = i;
                if (j > 5) {
                    j = 5;
                }
                if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) break;
                if (i < 5) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)(this.ty - 1), (double)this.tz, 1.33);
            } else {
                if (this.world.rand.nextInt(25) == 1) {
                    this.heal(-1.0f);
                }
                if (this.getHealth() <= 0.0f) {
                    this.setDead();
                    return;
                }
            }
        }
        if (this.world.rand.nextInt(10) == 1) {
            net.minecraft.entity.EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                if (this.getDistanceSq((Entity)e) < 9.0) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                        this.attackEntityAsMob((Entity)e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.2);
                    this.watercanon(e);
                }
            } else {
                if (this.buddy != null) {
                    this.getNavigator().tryMoveToEntityLiving((Entity)this.buddy, 1.0);
                }
                this.setAttacking(0);
            }
        }
    }

    private void watercanon(net.minecraft.entity.EntityLivingBase e) {
        double yoff = 1.0;
        double xzoff = 1.2;
        if (this.world.rand.nextInt(5) == 1) {
            if (this.rand.nextInt(3) == 1) {
                InkSack var2 = new InkSack(this.world, e.posX - this.posX, e.posY + 0.75 - (this.posY + yoff), e.posZ - this.posZ);
                var2.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
                double var3 = e.posX - this.posX;
                double var5 = e.posY + 0.25 - var2.posY;
                double var7 = e.posZ - this.posZ;
                float var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var7 * var7)) * 0.2f;
                var2.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
                this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity((Entity)var2);
            } else {
                WaterBall var2 = new WaterBall(this.world, e.posX - this.posX, e.posY + 0.75 - (this.posY + yoff), e.posZ - this.posZ);
                var2.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
                double var3 = e.posX - this.posX;
                double var5 = e.posY + 0.25 - var2.posY;
                double var7 = e.posZ - this.posZ;
                float var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var7 * var7)) * 0.2f;
                var2.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
                this.world.playSoundAtEntity((Entity)this, "random.bow", 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity((Entity)var2);
            }
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
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.capabilities.isCreativeMode;
        }
        if (par1EntityLiving instanceof Girlfriend) {
            return true;
        }
        if (par1EntityLiving instanceof Boyfriend) {
            return true;
        }
        if (par1EntityLiving instanceof EntityZombie) {
            return true;
        }
        if (par1EntityLiving instanceof EntityVillager) {
            return true;
        }
        if (par1EntityLiving instanceof EntitySpider) {
            return true;
        }
        if (par1EntityLiving instanceof EntityCaveSpider) {
            return true;
        }
        if (par1EntityLiving instanceof Ghost) {
            return false;
        }
        if (par1EntityLiving instanceof GhostSkelly) {
            return false;
        }
        if (par1EntityLiving instanceof Lizard) {
            return true;
        }
        if (par1EntityLiving instanceof AttackSquid) {
            if (this.world.rand.nextInt(5) == 1) {
                this.buddy = par1EntityLiving;
            }
            return false;
        }
        return this.wasshot != 0;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(10.0, 4.0, 10.0));
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

    public final int getAttacking() {
        return this.dataManager.get(20);
    }

    public final void setAttacking(int par1) {
        this.dataManager = new net.minecraft.util.math.BlockPos(20, (Object)((byte)par1));
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("WasShot", this.wasshot);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.wasshot = par1NBTTagCompound.getInteger("WasShot");
    }

    public boolean getCanSpawnHere() {
        super.getCanSpawnHere();
        if (this.posY < 50.0) {
            return false;
        }
        return this.world.isDaytime();
    }
}

