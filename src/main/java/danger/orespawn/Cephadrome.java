/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
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
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
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
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Cephadrome
extends EntityCreature {
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double boatYawHead;
    private int damage_counter = 100;
    private int updateit = 1;
    private int color = 1;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private int wasfed;
    private int shouldattack = 0;
    private int wing_sound = 0;
    private int hit_by_player = 0;
    private int badmood = 0;
    private float moveSpeed = 0.25f;

    public Cephadrome(World worldIn) {
        super(worldIn);
        this.setSize(2.5f, 2.25f);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 200;
        //this.fireResistance = 100;
        this.isImmuneToFire = false;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot(this, 16, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 9.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.getPassengers() = null;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
    }

    public Cephadrome(World worldIn, double par2, double par4, double par6) {
        this(worldIn);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(70.0);
    }

    public boolean shouldRiderSit() {
        return true;
    }

    public int getTrackingRange() {
        return 128;
    }

    public int getUpdateFrequency() {
        return 10;
    }

    public boolean sendsVelocityUpdates() {
        return true;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)0);
        this.dataManager.register(21, (Object)0);
        this.setActivity(0);
        this.setAttacking(0);
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

    public int mygetMaxHealth() {
        return 300;
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
        return 16;
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.1;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public String getLivingSound() {
        if (this.getActivity() != 1 && this.rand.nextInt(6) == 1) {
            return "orespawn:MothraWings";
        }
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:alo_death";
    }

    protected float getSoundVolume() {
        return 1.5f;
    }

    public float getSoundPitch() {
        return 1.0f;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 2.5;
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
        int i = 4 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(OreSpawnMain.UraniumNugget, 1);
        }
        i = 4 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(OreSpawnMain.TitaniumNugget, 1);
        }
        i = 1 + this.world.rand.nextInt(5);
        block17: for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(20);
            switch (var3) {
                case 0: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRubySword, 1);
                    continue block17;
                }
                case 1: {
                    ItemStack is = this.dropItemRand(Items.DIAMOND, 1);
                    continue block17;
                }
                case 2: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyThunderStaff, 1);
                    continue block17;
                }
                case 3: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRubySword, 1);
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
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRubyShovel, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 5: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRubyPickaxe, 1);
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
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRubyAxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 7: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRubyHoe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 8: {
                    ItemStack is = this.dropItemRand((Item)OreSpawnMain.RubyHelmet, 1);
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
                    ItemStack is = this.dropItemRand((Item)OreSpawnMain.RubyBody, 1);
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
                    ItemStack is = this.dropItemRand((Item)OreSpawnMain.RubyLegs, 1);
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
                    ItemStack is = this.dropItemRand((Item)OreSpawnMain.RubyBoots, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block17;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    continue block17;
                }
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 17: {
                    ItemStack is = this.dropItemRand(OreSpawnMain.MyRuby, 1);
                    continue block17;
                }
            }
        }
    }

    public int getCephadromeHealth() {
        return (int)this.getHealth();
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        double ks = 2.5;
        double inair = 0.35;
        float iskraken = 1.0f;
        boolean ret = false;
        if (par1Entity != null && par1Entity instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon)par1Entity;
            DamageSource var21 = null;
            var21 = DamageSource.setExplosionSource(null);
            var21.setExplosion();
            if (this.world.rand.nextInt(6) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, var21, 70.0f);
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, var21, 70.0f);
            }
            ret = true;
        } else if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
            if (par1Entity instanceof Kraken) {
                iskraken = 1.5f;
            }
            ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), iskraken * 70.0f);
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            if (par1Entity.isDead() || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                inair *= 2.0;
            }
            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return ret;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (this.hurt_timer > 0) {
            return false;
        }
        if (!par1DamageSource.getDamageType().equals("cactus")) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
            this.hurt_timer = 25;
            Entity e = par1DamageSource.getEntity();
            if (e != null && e instanceof net.minecraft.entity.EntityLivingBase) {
                this.setAttackTarget((net.minecraft.entity.EntityLivingBase)e);
                this.setTarget(e);
                this.getNavigator().tryMoveToEntityLiving((Entity)((net.minecraft.entity.EntityLivingBase)e), 1.2);
                ret = true;
            }
            if (e != null && e instanceof net.minecraft.entity.player.EntityPlayer && this.getHealth() < this.getMaxHealth() * 9.0f / 10.0f) {
                this.hit_by_player = 1;
            }
        }
        return ret;
    }

    public double getHorizontalDistanceSqToEntity(Entity par1Entity) {
        double d0 = this.posX - par1Entity.posX;
        double d2 = this.posZ - par1Entity.posZ;
        return d0 * d0 + d2 * d2;
    }

    public void updateAITasks() {
        net.minecraft.entity.EntityLivingBase e = null;
        double maxdist = 10.0;
        if (this.isDead()) {
            return;
        }
        if (this.updateit > 0) {
            --this.updateit;
        }
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.updateit <= 0 && !this.world.isRemote) {
            this.updateit = 30;
            if (this.getPassengers() != null) {
                this.setActivity(1);
            } else {
                this.setActivity(0);
            }
        }
        if (this.world.rand.nextInt(100) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(2.0f);
        }
        if (this.getActivity() == 0) {
            super.updateAITasks();
        }
        if (this.world.rand.nextInt(7) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            e = this.getAttackTarget();
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                if (this.getActivity() == 0) {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.7);
                    maxdist = 6.0;
                }
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                this.setAttacking(1);
                if (this.getDistanceSq((Entity)e) < (maxdist + (double)(e.width / 2.0f)) * (maxdist + (double)(e.width / 2.0f))) {
                    this.attackEntityAsMob((Entity)e);
                } else if (e instanceof Kraken && this.getHorizontalDistanceSqToEntity((Entity)e) < (maxdist + (double)(e.width / 2.0f)) * (maxdist + (double)(e.width / 2.0f))) {
                    this.attackEntityAsMob((Entity)e);
                }
            } else if (this.getAttacking() != 0) {
                this.setAttacking(0);
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
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof Cephadrome) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof Mothra) {
            return true;
        }
        if (par1EntityLiving instanceof Leon) {
            EntityTameable et = (EntityTameable)par1EntityLiving;
            return !et.isTamed();
        }
        if (par1EntityLiving instanceof GammaMetroid) {
            EntityTameable et = (EntityTameable)par1EntityLiving;
            return !et.isTamed();
        }
        if (par1EntityLiving instanceof WaterDragon) {
            EntityTameable et = (EntityTameable)par1EntityLiving;
            return !et.isTamed();
        }
        if (par1EntityLiving instanceof EntityDragon) {
            return true;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.isCreative()) {
                return false;
            }
            if (this.hit_by_player != 0) {
                return true;
            }
            if (this.badmood != 0) {
                return true;
            }
            if (this.shouldattack > 0) {
                this.shouldattack = 0;
                return true;
            }
            return false;
        }
        return false;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(16.0, 20.0, 16.0));
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
                    if (s == null || !s.equals("Cephadrome")) continue;
                    this.badmood = 1;
                    return true;
                }
            }
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        if (this.posY < 50.0) {
            return false;
        }
        for (k = -2; k < 2; ++k) {
            for (j = -2; j < 2; ++j) {
                for (i = 1; i < 5; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)).getBlock()this.posZ + k);
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        Cephadrome target = null;
        target = (Cephadrome)this.world.findNearestEntityWithinAABB(Cephadrome.class, this.boundingBox.expand(16.0, 6.0, 16.0), (Entity)this);
        return target == null;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        super.setPositionAndRotation2(par1, par3, par5, par7, par8, par9);
        this.boatPosRotationIncrements = par9;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
        this.boatYawHead = par7;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        super.setVelocity(par1, par3, par5);
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
        if (this.getActivity() == 1) {
            ++this.wing_sound;
            if (this.wing_sound > 22) {
                if (!this.world.isRemote) {
                    this.world.playSoundAtEntity((Entity)this, "orespawn:MothraWings", 0.5f, 1.0f);
                }
                this.wing_sound = 0;
            }
        }
        if (OreSpawnMain.PlayNicely == 0) {
            this.wasfed = 1;
        }
    }

    public void onLivingUpdate() {
        List list = null;
        Entity listEntity = null;
        double d6 = this.rand.nextFloat() * 2.0f - 1.0f;
        double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 1.15;
        double gh = 1.0;
        double rt = 0.0;
        double pi = 3.1415926545;
        double deltav = 0.0;
        int dist = 2;
        if (this.getActivity() == 0) {
            super.onLivingUpdate();
        } else if (this.isDead()) {
            super.onLivingUpdate();
            return;
        }
        if (this.isDead()) {
            return;
        }
        if (this.world.isRemote) {
            if (this.boatPosRotationIncrements > 0 && this.getActivity() != 0) {
                double d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                double d10 = net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.boatYaw - (double)this.rotationYaw));
                if (this.getPassengers() != null) {
                    d10 = net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.getPassengers().isEmpty() ? 0.0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw - (double)this.rotationYaw));
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                --this.boatPosRotationIncrements;
            }
        } else if (this.getActivity() != 0) {
            if (this.getPassengers() != null) {
                double rdv;
                net.minecraft.entity.player.EntityPlayer pp = (net.minecraft.entity.player.EntityPlayer)this.getPassengers();
                if (this.motionX < -2.0) {
                    this.motionX = -2.0;
                }
                if (this.motionX > 2.0) {
                    this.motionX = 2.0;
                }
                if (this.motionZ < -2.0) {
                    this.motionZ = -2.0;
                }
                if (this.motionZ > 2.0) {
                    this.motionZ = 2.0;
                }
                double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                gh = 1.55;
                Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)).getBlock()this.posZ);
                if (bid != Blocks.AIR) {
                    this.motionY += 0.07;
                    this.posY += 0.1;
                } else {
                    this.motionY -= 0.018;
                }
                obstruction_factor = 0.0;
                dist = 2;
                dist += (int)(velocity * 6.0);
                for (int k = 1; k < dist; ++k) {
                    for (int i = 1; i < dist * 2; ++i) {
                        double dz;
                        double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                        bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)).getBlock()(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))));
                        if (bid == Blocks.AIR) continue;
                        obstruction_factor += 0.04;
                    }
                }
                this.motionY += obstruction_factor * 0.09;
                this.posY += obstruction_factor * 0.09;
                if (this.motionY > 2.0) {
                    this.motionY = 2.0;
                }
                double d4 = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw;
                d4 %= 360.0;
                while (d4 < 0.0) {
                    d4 += 360.0;
                }
                double d5 = this.rotationYaw;
                d5 %= 360.0;
                while (d5 < 0.0) {
                    d5 += 360.0;
                }
                for (relative_g = (d4 - d5) % 180.0; relative_g < 0.0; relative_g += 180.0) {
                }
                if (relative_g > 90.0) {
                    relative_g -= 180.0;
                }
                if (velocity > 0.1) {
                    d4 = 1.5 - velocity;
                    if ((d4 = Math.abs(d4)) < 0.01) {
                        d4 = 0.01;
                    }
                    if (d4 > 0.9) {
                        d4 = 0.9;
                    }
                    this.rotationYaw = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw + (float)(relative_g * d4);
                } else {
                    this.rotationYaw = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw;
                }
                relative_g = Math.abs(relative_g) * velocity;
                if (relative_g > 50.0) {
                    relative_g = 0.0;
                }
                this.rotationPitch = this.motionY > 0.0 ? 360.0f - 2.0f * (float)velocity : 2.0f * (float)velocity;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                double rr = Math.atan2(this.getPassengers().isEmpty() ? 0 : this.getPassengers().get(0).motionZ, this.getPassengers().isEmpty() ? 0 : this.getPassengers().get(0).motionX);
                double rhm = Math.atan2(this.motionZ, this.motionX);
                double rhdir = Math.toRadians((this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw + 90.0f) % 360.0f);
                rt = 0.0;
                pi = 3.1415926545;
                deltav = 0.0;
                double im = pp.moveForward;
                if (OreSpawnMain.flyup_keystate != 0) {
                    this.motionY += 0.04;
                    this.motionY += velocity * 0.05;
                }
                if ((rdv = Math.abs(rhm - rhdir) % (pi * 2.0)) > pi) {
                    rdv -= pi * 2.0;
                }
                rdv = Math.abs(rdv);
                if (Math.abs(newvelocity) < 0.01) {
                    rdv = 0.0;
                }
                if (rdv > 1.5) {
                    newvelocity = -newvelocity;
                }
                if (Math.abs(im) > (double)0.001f) {
                    if (im > 0.0) {
                        deltav = 0.03;
                        if (max_speed > 0.85) {
                            deltav += 0.05;
                        }
                    } else {
                        max_speed = 0.35;
                        deltav = -0.03;
                    }
                    if ((newvelocity += deltav) >= 0.0) {
                        if (newvelocity > max_speed) {
                            newvelocity = max_speed;
                        }
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                    } else {
                        if (newvelocity < -max_speed) {
                            newvelocity = -max_speed;
                        }
                        newvelocity = -newvelocity;
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                    }
                } else if (newvelocity >= 0.0) {
                    this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                    this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                } else {
                    this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                    this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.985;
            this.motionY *= 0.94;
            this.motionZ *= 0.985;
            if (!this.world.isRemote && (list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(2.25, 2.0, 2.25))) != null && !list.isEmpty()) {
                for (int l = 0; l < list.size(); ++l) {
                    listEntity = (Entity)list.get(l);
                    if (listEntity == this.getPassengers() || listEntity.isDead() || !listEntity.canBePushed()) continue;
                    listEntity.applyEntityCollision((Entity)this);
                }
            }
            if (this.getPassengers() != null && this.getPassengers().isDead()) {
                this.getPassengers() = null;
            }
        }
        if (this.getActivity() == 1) {
            this.updateAITasks();
        }
    }

    public void updateRiderPosition() {
        if (this.getPassengers() != null) {
            float f = 0.75f;
            this.getPassengers().setPosition(this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + this.getMountedYOffset() + this.getPassengers().getYOffset(), this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    protected void playTameEffect(boolean par1) {
        String s = "heart";
        if (!par1) {
            s = "smoke";
        }
        for (int i = 0; i < 20; ++i) {
            double d0 = this.rand.nextGaussian() * 0.08;
            double d1 = this.rand.nextGaussian() * 0.08;
            double d2 = this.rand.nextGaussian() * 0.08;
            this.world.spawnParticle(s, this.posX + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 2.5f), this.posY + 0.5 + (double)this.rand.nextFloat() * 1.5, this.posZ + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 2.5f), d0, d1, d2);
        }
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (var2 != null && (var2.getItem() == Items.BEEF || var2.getItem() == Items.CHICKEN || var2.getItem() == Items.PORKCHOP) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
            if (!this.world.isRemote) {
                this.heal((float)this.mygetMaxHealth() - this.getHealth());
            }
            this.wasfed = 1;
            this.shouldattack = 0;
            this.playTameEffect(true);
            if (!par1EntityPlayer.isCreative()) {
                --var2.stackSize;
                if (var2.stackSize <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    var2 = null;
                }
            }
        } else {
            if (this.getPassengers() != null && this.getPassengers() instanceof net.minecraft.entity.player.EntityPlayer && this.getPassengers() != par1EntityPlayer) {
                return true;
            }
            if (var2 == null && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && !this.world.isRemote) {
                if (this.wasfed == 0) {
                    this.getNavigator().tryMoveToEntityLiving((Entity)par1EntityPlayer, 1.2);
                    this.shouldattack = 1;
                    return false;
                }
                par1EntityPlayer.startRiding((Entity)this);
                this.wasfed = 0;
            }
            return true;
        }
        return false;
    }

    public int getAttacking() {
        return this.dataManager.get(20);
    }

    public void setAttacking(int par1) {
        if (this.world != null && this.world.isRemote) {
            return;
        }
        this.dataManager.set(20, (Object)((byte)par1));
    }

    public int getActivity() {
        return this.dataManager.get(21);
    }

    public void setActivity(int par1) {
        if (this.world != null && this.world.isRemote) {
            return;
        }
        this.dataManager = new net.minecraft.util.math.BlockPos(21, (Object)((byte)par1));
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return this.getPassengers() == null;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CephaWasFed", this.wasfed);
        par1NBTTagCompound.setInteger("CephaAttacking", this.getAttacking());
        par1NBTTagCompound.setInteger("CephaActivity", this.getActivity());
        par1NBTTagCompound.setInteger("CephaHitByPlayer", this.hit_by_player);
        par1NBTTagCompound.setInteger("CephaBadMood", this.badmood);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.wasfed = par1NBTTagCompound.getInteger("CephaWasFed");
        this.hit_by_player = par1NBTTagCompound.getInteger("CephaHitByPlayer");
        this.badmood = par1NBTTagCompound.getInteger("CephaBadMood");
        this.setAttacking(par1NBTTagCompound.getInteger("CephaAttacking"));
        this.setActivity(par1NBTTagCompound.getInteger("CephaActivity"));
    }
}

