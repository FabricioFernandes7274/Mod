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
 *  net.minecraft.entity.ai.EntityAIMoveThroughVillage
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
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
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
import net.minecraft.world.35f;
    int foundmob = 0;
    int ticker = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public CaterKiller(World par1World) {
        super(par1World);
        if (OreSpawnMain.PlayNicely == 0) {
            this.setSize(2.9f, 4.6f);
        } else {
            this.setSize(1.45f, 2.3f);
        }
        this.getNavigator().setAvoidsWater(true);
        this.experienceValue = 200;
        //this.fireResistance = 100;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.CaterKiller_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)0);
        this.dataManager.register(21, (Object)OreSpawnMain.PlayNicely);
    }

    public int getPlayNicely() {
        return this.dataManager.get(21);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Entity e = null;
        Boolean ret = super.attackEntityFrom(par1DamageSource, par2);
        e = par1DamageSource.getEntity();
        if (e != null && e instanceof EntityLiving) {
            this.setAttackTarget((net.minecraft.entity.EntityLivingBase)((EntityLiving)e));
        }
        return ret;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.CaterKiller_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.CaterKiller_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        if (this.rand.nextInt(3) == 0) {
            return "orespawn:caterkiller_living";
        }
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:caterkiller_hit";
    }

    protected String getDeathSound() {
        return "orespawn:caterkiller_death";
    }

    protected float getSoundVolume() {
        return 1.5f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.BEEF;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(5) - (double)OreSpawnMain.OreSpawnRand.nextInt(5), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(5) - (double)OreSpawnMain.OreSpawnRand.nextInt(5), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(OreSpawnMain.CaterKillerJaw, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        for (var4 = 0; var4 < 10; ++var4) {
            this.dropItemRand(Items.LEATHER, 1);
        }
        for (var4 = 0; var4 < 6; ++var4) {
            this.dropItemRand(Items.BEEF, 1);
        }
        int i = 1 + this.world.rand.nextInt(5);
        block17: for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(20);
            switch (var3) {
                case 0: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateSword, 1);
                    continue block17;
                }
                case 1: {
                    is = this.dropItemRand(OreSpawnMain.MyRuby, 1);
                    continue block17;
                }
                case 2: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIAMOND_BLOCK), 1);
                    continue block17;
                }
                case 3: {
                    is = this.dropItemRand(OreSpawnMain.MyRubySword, 1);
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
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 4: {
                    is = this.dropItemRand(OreSpawnMain.MyRubyShovel, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 5: {
                    is = this.dropItemRand(OreSpawnMain.MyRubyPickaxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 6: {
                    is = this.dropItemRand(OreSpawnMain.MyRubyAxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 7: {
                    is = this.dropItemRand(OreSpawnMain.MyRubyHoe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 8: {
                    is = this.dropItemRand((Item)OreSpawnMain.RubyHelmet, 1);
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
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 9: {
                    is = this.dropItemRand((Item)OreSpawnMain.RubyBody, 1);
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
                    if (this.world.rand.nextInt(2) != 1) continue block17;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    continue block17;
                }
                case 10: {
                    is = this.dropItemRand((Item)OreSpawnMain.RubyLegs, 1);
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
                    if (this.world.rand.nextInt(2) != 1) continue block17;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    continue block17;
                }
                case 11: {
                    is = this.dropItemRand((Item)OreSpawnMain.RubyBoots, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block17;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    continue block17;
                }
                case 12: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateBow, 1);
                    continue block17;
                }
            }
        }
        for (var4 = 0; var4 < 25; ++var4) {
            CaterKiller.spawnCreature(this.world, "Butterfly", this.posX, this.posY + 1.0, this.posZ);
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

    public void initCreature() {
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
                double ks = 1.2;
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

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int d;
        Block bid;
        int j;
        int i;
        int found = 0;
        for (i = -dy; i <= dy; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + dx, y + i, z + j)).getBlock();
                if ((bid == Blocks.LEAVES || bid == Blocks.VINE || bid == Blocks.LOG || bid == OreSpawnMain.MyDT || bid == Blocks.LOG2 || bid == Blocks.LEAVES2 || bid == OreSpawnMain.MyAppleLeaves || bid == OreSpawnMain.MyExperienceLeaves || bid == OreSpawnMain.MyScaryLeaves || bid == OreSpawnMain.MyPeachLeaves || bid == OreSpawnMain.MyCherryLeaves) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.LEAVES && bid != Blocks.VINE && bid != Blocks.LOG && bid != OreSpawnMain.MyDT && bid != Blocks.LOG2 && bid != Blocks.LEAVES2 && bid != OreSpawnMain.MyAppleLeaves && bid != OreSpawnMain.MyExperienceLeaves && bid != OreSpawnMain.MyScaryLeaves && bid != OreSpawnMain.MyPeachLeaves && bid != OreSpawnMain.MyCherryLeaves || (d = dx * dx + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.LEAVES || bid == Blocks.VINE || bid == Blocks.LOG || bid == OreSpawnMain.MyDT || bid == Blocks.LOG2 || bid == Blocks.LEAVES2 || bid == OreSpawnMain.MyAppleLeaves || bid == OreSpawnMain.MyExperienceLeaves || bid == OreSpawnMain.MyScaryLeaves || bid == OreSpawnMain.MyPeachLeaves || bid == OreSpawnMain.MyCherryLeaves) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.LEAVES && bid != Blocks.VINE && bid != Blocks.LOG && bid != OreSpawnMain.MyDT && bid != Blocks.LOG2 && bid != Blocks.LEAVES2 && bid != OreSpawnMain.MyAppleLeaves && bid != OreSpawnMain.MyExperienceLeaves && bid != OreSpawnMain.MyScaryLeaves && bid != OreSpawnMain.MyPeachLeaves && bid != OreSpawnMain.MyCherryLeaves || (d = dy * dy + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.LEAVES || bid == Blocks.VINE || bid == Blocks.LOG || bid == OreSpawnMain.MyDT || bid == Blocks.LOG2 || bid == Blocks.LEAVES2 || bid == OreSpawnMain.MyAppleLeaves || bid == OreSpawnMain.MyExperienceLeaves || bid == OreSpawnMain.MyScaryLeaves || bid == OreSpawnMain.MyPeachLeaves || bid == OreSpawnMain.MyCherryLeaves) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.LEAVES && bid != Blocks.VINE && bid != Blocks.LOG && bid != OreSpawnMain.MyDT && bid != Blocks.LOG2 && bid != Blocks.LEAVES2 && bid != OreSpawnMain.MyAppleLeaves && bid != OreSpawnMain.MyExperienceLeaves && bid != OreSpawnMain.MyScaryLeaves && bid != OreSpawnMain.MyPeachLeaves && bid != OreSpawnMain.MyCherryLeaves || (d = dz * dz + j * j + i * i) >= this.closest) continue;
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
        int j;
        int i;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        this.dataManager = new net.minecraft.util.math.BlockPos(21, (Object)OreSpawnMain.PlayNicely);
        if (this.getHealth() + 1.0f < this.getMaxHealth()) {
            ++this.ticker;
            if (this.ticker > 2400) {
                CaterKiller.spawnCreature(this.world, "Brutalfly", this.posX, this.posY + 4.0, this.posZ);
                this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.explode")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, this.world.rand.nextFloat() * 0.2f + 0.9f));
                for (int i2 = 0; i2 < 10; ++i2) {
                    CaterKiller.spawnCreature(this.world, "Butterfly", this.posX, this.posY + 1.0 + (double)this.world.rand.nextInt(4), this.posZ);
                }
                this.setDead();
                return;
            }
        }
        if (this.isInWeb) {
            for (i = -2; i <= 2; ++i) {
                for (j = -1; j < 5; ++j) {
                    for (int k = -2; k <= 2; ++k) {
                        if (this.world.getBlock((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k) != Blocks.WEB) continue;
                        this.world.setBlock((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k, Blocks.AIR);
                        this.world// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //.setBlockMetadataWithNotify((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k, 0, 3);
                    }
                }
            }
            this.isInWeb = false;
        }
        if (this.world.rand.nextInt(4) == 0) {
            net.minecraft.entity.EntityLivingBase e = this.getAttackTarget();
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (this.world.rand.nextInt(200) == 0) {
                this.setAttackTarget(null);
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                this.foundmob = 1;
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getDistanceSq((Entity)e) < (double)((5.0f + e.width / 2.0f) * (5.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(3) == 0 || this.world.rand.nextInt(4) == 1) {
                        this.attackEntityAsMob((Entity)e);
                    }
                } else {
                    this.setAttacking(0);
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.25);
                    if (this.world.rand.nextInt(4) == 0) {
                        double dx = e.posX;
                        double dz = e.posZ;
                        dx += (double)(this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0;
                        dz += (double)(this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0;
                        for (i = 2; i > -2; --i) {
                            if (this.world.getBlock((int)dx, (int)e.posY + i + 1, (int)dz) != Blocks.AIR || this.world.getBlock((int)dx, (int)e.posY + i, (int)dz) == Blocks.AIR) continue;
                            this.world.setBlock((int)dx, (int)e.posY + i + 1, (int)dz, Blocks.WEB);
                            break;
                        }
                    }
                }
            } else {
                this.setAttacking(0);
                this.foundmob = 0;
            }
        }
        if ((this.world.rand.nextInt(8) == 0 && this.getHealth() < (float)this.mygetMaxHealth() || this.world.rand.nextInt(30) == 0) && OreSpawnMain.PlayNicely == 0) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (i = 1; i < 13; ++i) {
                j = i;
                if (j > 9) {
                    j = 9;
                }
                if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) break;
                if (i < 9) continue;
                ++i;
            }
            if (this.closest < 99999) {
                if (this.foundmob == 0) {
                    this.getNavigator().tryMoveToXYZ((double)this.tx, (double)this.ty, (double)this.tz, 1.0);
                }
                if (this.closest < 81) {
                    if (this.world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                        this.world.setBlockState(new net.minecraft.util.math.BlockPos(this.tx, this.ty, this.tz), Blocks.AIR.getDefaultState(), 2);
                    }
                    this.heal(2.0f);
                    if (this.world.rand.nextInt(20) == 1) {
                        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.burp")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, this.world.rand.nextFloat() * 0.2f + 0.9f));
                    }
                }
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
        if (!this.MyCanSee(par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.isCreative();
        }
        if (par1EntityLiving instanceof CaterKiller) {
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
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(20.0, 8.0, 20.0));
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
        int i;
        int j;
        int k;
        for (k = -3; k < 3; ++k) {
            for (j = -3; j < 3; ++j) {
                for (i = 0; i < 5; ++i) {
                    bid = this.world.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("CaterKiller")) continue;
                    return true;
                }
            }
        }
        if (this.posY < 50.0) {
            return false;
        }
        if (this.world.rand.nextInt(10) != 0) {
            return false;
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        for (k = -1; k < 2; ++k) {
            for (j = -1; j < 2; ++j) {
                for (i = 1; i < 5; ++i) {
                    bid = this.world.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (bid == Blocks.AIR || bid == Blocks.LEAVES || bid == Blocks.LEAVES2 || bid == Blocks.LOG || bid == Blocks.LOG2) continue;
                    return false;
                }
            }
        }
        CaterKiller target = null;
        target = (CaterKiller)this.world.findNearestEntityWithinAABB(CaterKiller.class, this.boundingBox.expand(48.0, 16.0, 48.0), (Entity)this);
        return target == null;
    }

    public boolean MyCanSee(net.minecraft.entity.EntityLivingBase e) {
        double xzoff = 2.5;
        int nblks = 10;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        float startx = (float)cx;
        float starty = (float)(this.posY + 3.0);
        float startz = (float)cz;
        float dx = (float)((e.posX - (double)startx) / 10.0);
        float dy = (float)((e.posY + (double)(e.height / 2.0f) - (double)starty) / 10.0);
        float dz = (float)((e.posZ - (double)startz) / 10.0);
        if ((double)Math.abs(dx) > 1.0) {
            dy /= Math.abs(dx);
            dz /= Math.abs(dx);
            nblks = (int)((float)nblks * Math.abs(dx));
            if (dx > 1.0f) {
                dx = 1.0f;
            }
            if (dx < -1.0f) {
                dx = -1.0f;
            }
        }
        if ((double)Math.abs(dy) > 1.0) {
            dx /= Math.abs(dy);
            dz /= Math.abs(dy);
            nblks = (int)((float)nblks * Math.abs(dy));
            if (dy > 1.0f) {
                dy = 1.0f;
            }
            if (dy < -1.0f) {
                dy = -1.0f;
            }
        }
        if ((double)Math.abs(dz) > 1.0) {
            dy /= Math.abs(dz);
            dx /= Math.abs(dz);
            nblks = (int)((float)nblks * Math.abs(dz));
            if (dz > 1.0f) {
                dz = 1.0f;
            }
            if (dz < -1.0f) {
                dz = -1.0f;
            }
        }
        for (int i = 0; i < nblks; ++i) {
            Block bid = this.world.getBlock((int)(startx += dx), (int)(starty += dy), (int)(startz += dz));
            if (bid == Blocks.AIR || bid == Blocks.WEB || bid == Blocks.TALLGRASS || bid == Blocks.LEAVES) continue;
            return false;
        }
        return true;
    }
}

