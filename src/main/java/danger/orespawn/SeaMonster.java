/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
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
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.DamageSource
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
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class SeaMonster
extends EntityMob {
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private float moveSpeed = 0.25f;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public SeaMonster(World worldIn) {
        super(worldIn);
        this.setSize(1.25f, 2.5f);
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 150;
        //this.fireResistance = 30;
        this.isImmuneToFire = false;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 10.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.SeaMonster_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)0);
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
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.SeaMonster_stats.health;
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
        return OreSpawnMain.SeaMonster_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.isInWater() ? 0.55f : 0.25f);
    }

    public int getSeaMonsterHealth() {
        return (int)this.getHealth();
    }

    protected String getLivingSound() {
        if (this.getEntityWorld().rand.nextInt(3) == 0) {
            return "orespawn:seamonster_living";
        }
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

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
        var3 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), is);
        if (var3 != null) {
            this.getEntityWorld().spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(OreSpawnMain.SeaMonsterScale, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int var5 = 9 + this.getEntityWorld().rand.nextInt(6);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.FISH, 1);
        }
        var4 = this.getEntityWorld().rand.nextInt(20);
        switch (var4) {
            case 1: {
                is = this.dropItemRand(Items.IRON_INGOT, 1);
                break;
            }
            case 3: {
                is = this.dropItemRand(Items.IRON_SWORD, 1);
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.LOOTING, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                break;
            }
            case 4: {
                is = this.dropItemRand(Items.IRON_SHOVEL, 1);
                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                }
                if (this.getEntityWorld().rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                break;
            }
            case 5: {
                is = this.dropItemRand(Items.IRON_PICKAXE, 1);
                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                break;
            }
            case 6: {
                is = this.dropItemRand(Items.IRON_AXE, 1);
                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                }
                if (this.getEntityWorld().rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                break;
            }
            case 7: {
                is = this.dropItemRand(Items.IRON_HOE, 1);
                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                }
                if (this.getEntityWorld().rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                break;
            }
            case 8: {
                is = this.dropItemRand((Item)Items.IRON_HELMET, 1);
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(2));
                }
                if (this.getEntityWorld().rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                break;
            }
            case 9: {
                is = this.dropItemRand((Item)Items.IRON_CHESTPLATE, 1);
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                break;
            }
            case 10: {
                is = this.dropItemRand((Item)Items.IRON_LEGGINGS, 1);
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                break;
            }
            case 11: {
                is = this.dropItemRand((Item)Items.IRON_BOOTS, 1);
                if (this.getEntityWorld().rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                }
                if (this.getEntityWorld().rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                break;
            }
            case 13: {
                this.dropItemRand(Item.getItemFromBlock((Block)Blocks.IRON_BLOCK), 1);
                break;
            }
        }
    }

    public void initCreature() {
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
                double ks = 0.6;
                double inair = 0.1;
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
        if (this.hurt_timer <= 0) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
            this.hurt_timer = 8;
        }
        if (e != null && e instanceof EntityLiving) {
            if (e instanceof SeaMonster) {
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
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + dx, y + i, z + j)).getBlock();
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dx * dx + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dy * dy + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dz * dz + j * j + i * i) >= this.closest) continue;
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
        if (!this.isInWater() && this.getEntityWorld().rand.nextInt(25) == 0) {
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
                if (this.getEntityWorld().rand.nextInt(40) == 1) {
                    this.heal(-1.0f);
                }
                if (this.getHealth() <= 0.0f) {
                    this.setDead();
                    return;
                }
            }
        }
        if (this.getEntityWorld().rand.nextInt(5) == 1) {
            net.minecraft.entity.EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getDistanceSq((Entity)e) < (double)((4.0f + e.width / 2.0f) * (4.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    if (this.getEntityWorld().rand.nextInt(4) == 0 || this.getEntityWorld().rand.nextInt(5) == 1) {
                        this.attackEntityAsMob((Entity)e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.0);
                }
            } else {
                this.setAttacking(0);
            }
        }
        if (this.getEntityWorld().rand.nextInt(120) == 1 && this.isInWater() && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("splash")), net.minecraft.util.SoundCategory.NEUTRAL, 1.5f, this.getEntityWorld().rand.nextFloat() * 0.2f + 0.9f));
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
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.isCreative();
        }
        if (par1EntityLiving instanceof SeaMonster) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        return MyUtils.isAttackableNonMob(par1EntityLiving);
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(16.0, 4.0, 16.0));
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
        return 0 /* this.dataManager.get(20) */;
    }

    public final void setAttacking(int par1) {
//         this.dataManager.set(20, (Object)((byte)par1));
    }

    public boolean getCanSpawnHere() {
        SeaMonster target = null;
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.getEntityWorld().getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Sea Monster")) continue;
                    return true;
                }
            }
        }
        if (this.posY < 50.0) {
            return false;
        }
        if (this.getEntityWorld().isDaytime()) {
            return false;
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        target = (SeaMonster)this.getEntityWorld().findNearestEntityWithinAABB(SeaMonster.class, this.getEntityBoundingBox().expand(16.0, 5.0, 16.0), (Entity)this);
        return target == null;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }
}

