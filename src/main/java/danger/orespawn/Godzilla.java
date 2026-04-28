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
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.effect.EntityLightningBolt
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySkeleton
 *  net.minecraft.entity.monster.EntitySpider
 *  net.minecraft.entity.monster.EntityZombie
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;

public class Godzilla extends EntityMob {

import danger.orespawn.BetterFireball;
import danger.orespawn.GenericTargetSorter;
import danger.orespawn.Ghost;
import danger.orespawn.GhostSkelly;
import danger.orespawn.GodzillaHead;
import danger.orespawn.Kraken;
import danger.orespawn.MyEntityAIWanderALot;
import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.PitchBlack;
import danger.orespawn.RenderInfo;
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
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
    private int hurt_timer = 0;
    private int jumped = 0;
    private int jump_timer = 0;
    private int ticker = 0;
    private RenderInfo renderdata = new RenderInfo();
    private int stream_count = 8;
    private MyEntityAIWanderALot wander = null;
    private int head_found = 0;
    private int large_unknown_detected = 0;

    public Godzilla(World worldIn) {
        super(worldIn);
        if (OreSpawnMain.PlayNicely == 0) {
            this.setSize(9.9f, 25.0f);
        } else {
            this.setSize(2.475f, 6.25f);
        }
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 10000;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.wander = new MyEntityAIWanderALot((EntityCreature)this, 15, 1.0);
        this.tasks.addTask(2, (EntityAIBase)this.wander);
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 50.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        //this.fireResistance = 10000;
        this.isImmuneToFire = true;
        this.renderDistanceWeight = 12.0;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Godzilla_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)0);
//         this.dataManager.register(21, (Object)OreSpawnMain.PlayNicely);
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

    public int getPlayNicely() {
        return 0 /* this.dataManager.get(21) */;
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
        return OreSpawnMain.PlayNicely != 0;
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Godzilla_stats.health;
    }

    public int getTotalArmorValue() {
        if (this.large_unknown_detected != 0) {
            return 25;
        }
        return OreSpawnMain.Godzilla_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        double xzoff = 0.0;
        double myoff = 20.0;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
        if (this.isAirBorne) {
            this.getNavigator().setPath(null, 0.0);
        }
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    protected String getLivingSound() {
        if (this.getEntityWorld().rand.nextInt(5) == 0) {
            return "orespawn:godzilla_living";
        }
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:alo_hurt";
    }

    protected String getDeathSound() {
        return "orespawn:godzilla_death";
    }

    protected float getSoundVolume() {
        return 1.65f;
    }

    protected float getSoundPitch() {
        return 1.1f;
    }

    protected Item getDropItem() {
        return null;
    }

    protected void jump() {
        while (this.rotationYaw < 0.0f) {
            this.rotationYaw += 360.0f;
        }
        while (this.rotationYawHead < 0.0f) {
            this.rotationYawHead += 360.0f;
        }
        while (this.rotationYaw > 360.0f) {
            this.rotationYaw -= 360.0f;
        }
        while (this.rotationYawHead > 360.0f) {
            this.rotationYawHead -= 360.0f;
        }
        this.motionY += (double)0.45f;
        this.posY += 0.5;
        float f = 0.2f + Math.abs(this.getEntityWorld().rand.nextFloat() * 0.45f);
        this.motionX += (double)f * Math.cos(Math.toRadians(this.rotationYawHead + 90.0f));
        this.motionZ += (double)f * Math.sin(Math.toRadians(this.rotationYawHead + 90.0f));
        this.isAirBorne = true;
        this.getNavigator().setPath(null, 0.0);
    }

    protected void jumpAtEntity(net.minecraft.entity.EntityLivingBase e) {
        float f2;
        this.motionY += 1.25;
        this.posY += (double)1.55f;
        double d1 = e.posX - this.posX;
        double d2 = e.posZ - this.posZ;
        float d = (float)Math.atan2(d2, d1);
        this.rotationYaw = f2 = (float)((double)d * 180.0 / Math.PI) - 90.0f;
        d1 = Math.sqrt(d1 * d1 + d2 * d2);
        this.motionX += d1 * 0.05 * Math.cos(d);
        this.motionZ += d1 * 0.05 * Math.sin(d);
        this.isAirBorne = true;
        this.getNavigator().setPath(null, 0.0);
    }

    private double getHorizontalDistanceSqToEntity(Entity e) {
        double d1 = e.posZ - this.posZ;
        double d2 = e.posX - this.posX;
        return d1 * d1 + d2 * d2;
    }

    public double MygetDistanceSqToEntity(Entity par1Entity) {
        double d0 = this.posX - par1Entity.posX;
        double d1 = par1Entity.posY - this.posY;
        double d2 = this.posZ - par1Entity.posZ;
        if (d1 > 0.0 && d1 < 20.0) {
            d1 = 0.0;
        }
        if (d1 > 20.0) {
            d1 -= 10.0;
        }
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    protected void updateAITasks() {
        Block bid;
        int j;
        int i;
        net.minecraft.entity.EntityLivingBase e = null;
        int xzrange = 9;
        if (this.isDead()) {
            return;
        }
        if (this.getEntityWorld().isRemote) {
            return;
        }
//         this.dataManager.set(21, (Object)OreSpawnMain.PlayNicely);
        super.updateAITasks();
        ++this.ticker;
        if (this.ticker > 30000) {
            this.ticker = 0;
        }
        if (this.ticker % 100 == 0) {
            this.stream_count = 8;
        }
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.jump_timer > 0) {
            --this.jump_timer;
        }
        OreSpawnMain.godzilla_has_spawned = 1;
        if (this.getEntityWorld().rand.nextInt(200) == 0) {
            this.setAttackTarget(null);
        }
        if (OreSpawnMain.PlayNicely == 0) {
            if (this.motionY < -0.95) {
                this.jumped = 1;
            }
            if (this.motionY < -1.5) {
                this.jumped = 2;
            }
            if (this.jumped != 0 && this.motionY > -0.1) {
                double df = 1.0;
                if (this.jumped == 2) {
                    df = 1.5;
                }
                this.doJumpDamage(this.posX, this.posY, this.posZ, 10.0, (double)OreSpawnMain.Godzilla_stats.attack * df, 0);
                this.doJumpDamage(this.posX, this.posY, this.posZ, 15.0, (double)(OreSpawnMain.Godzilla_stats.attack / 2) * df, 0);
                this.doJumpDamage(this.posX, this.posY, this.posZ, 25.0, (double)(OreSpawnMain.Godzilla_stats.attack / 4) * df, 0);
                this.jumped = 0;
            }
        }
        xzrange = 12;
        if (this.getAttacking() != 0) {
            xzrange = 16;
        }
        int k = -3 + this.ticker % 30;
        if (OreSpawnMain.PlayNicely == 0) {
            for (i = -xzrange; i <= xzrange; ++i) {
                for (j = -xzrange; j <= xzrange; ++j) {
                    bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j)).getBlock(;
                    if (this.isCrushable(bid)) {
                        this.getEntityWorld().setBlock((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j, Blocks.AIR);
                        if (this.getEntityWorld().rand.nextInt(15) != 1) continue;
                        this.dropItemRand(Item.getItemFromBlock((Block)bid), 1);
                        continue;
                    }
                    if (bid == Blocks.GRASS && this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                        this.getEntityWorld().setBlock((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j, Blocks.DIRT);
                    }
                    if (bid != Blocks.FARMLAND || !this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) continue;
                    this.getEntityWorld().setBlock((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j, Blocks.DIRT);
                }
            }
        }
        double dx = this.posX + 16.0 * Math.sin(Math.toRadians(this.rotationYawHead));
        double dz = this.posZ - 16.0 * Math.cos(Math.toRadians(this.rotationYawHead));
        k = -3 + this.ticker % 12;
        if (OreSpawnMain.PlayNicely == 0) {
            for (i = -xzrange; i <= xzrange; ++i) {
                for (j = -xzrange; j <= xzrange; ++j) {
                    bid = this.getEntityWorld().getBlockState(new BlockPos((int)dx + i, (int)this.posY + k, (int)dz + j)).getBlock(;
                    if (this.isCrushable(bid)) {
                        this.getEntityWorld().setBlock((int)dx + i, (int)this.posY + k, (int)dz + j, Blocks.AIR);
                        if (this.getEntityWorld().rand.nextInt(15) != 1) continue;
                        this.dropItemRandAt(Item.getItemFromBlock((Block)bid), 1, dx, dz);
                        continue;
                    }
                    if (bid == Blocks.GRASS && this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                        this.getEntityWorld().setBlock((int)dx + i, (int)this.posY + k, (int)dz + j, Blocks.DIRT);
                    }
                    if (bid != Blocks.FARMLAND || !this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) continue;
                    this.getEntityWorld().setBlock((int)dx + i, (int)this.posY + k, (int)dz + j, Blocks.DIRT);
                }
            }
        }
        if (OreSpawnMain.PlayNicely == 0 && k == 0) {
            this.doJumpDamage(dx, this.posY, dz, 15.0, OreSpawnMain.Godzilla_stats.attack / 2, 1);
        }
        if (this.getEntityWorld().rand.nextInt(5 - this.large_unknown_detected) == 1) {
            e = this.getAttackTarget();
            if (OreSpawnMain.PlayNicely != 0) {
                e = null;
            }
            if (e != null) {
                if (!e.isEntityAlive()) {
                    this.setAttackTarget(null);
                    e = null;
                } else if (e instanceof Godzilla || e instanceof GodzillaHead) {
                    this.setAttackTarget(null);
                    e = null;
                }
            }
            if (e == null) {
                e = this.findSomethingToAttack();
                if (this.head_found == 0) {
                    EntityLiving newent = (EntityLiving)Godzilla.spawnCreature(this.getEntityWorld(), "MobzillaHead", this.posX, this.posY + 20.0, this.posZ);
                }
            }
            if (e != null) {
                this.wander.setBusy(1);
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getEntityWorld().rand.nextInt(65) == 1 && this.MygetDistanceSqToEntity((Entity)e) > 300.0) {
                    this.doLightningAttack(e);
                } else if (this.getEntityWorld().rand.nextInt(20 - this.large_unknown_detected * 5) == 1 && this.jump_timer == 0) {
                    this.jumpAtEntity(e);
                    this.jump_timer = 30;
                } else if (this.MygetDistanceSqToEntity((Entity)e) < (double)(300.0f + e.width / 2.0f * (e.width / 2.0f))) {
                    this.setAttacking(1);
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.0);
                    if (this.getEntityWorld().rand.nextInt(4 - this.large_unknown_detected) == 0 || this.getEntityWorld().rand.nextInt(3 - this.large_unknown_detected) == 1) {
                        this.attackEntityAsMob((Entity)e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.0);
                    if (this.getHorizontalDistanceSqToEntity((Entity)e) > 625.0) {
                        if (this.stream_count > 0) {
                            this.setAttacking(1);
                            double rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                            double rhdir = Math.toRadians((this.rotationYawHead + 90.0f) % 360.0f);
                            double pi = 3.1415926545;
                            double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                            if (rdd > pi) {
                                rdd -= pi * 2.0;
                            }
                            if ((rdd = Math.abs(rdd)) < 0.5) {
                                this.firecanon(e);
                            }
                        } else {
                            this.setAttacking(0);
                        }
                    } else {
                        this.setAttacking(0);
                    }
                }
            } else {
                this.setAttacking(0);
                this.wander.setBusy(0);
                this.stream_count = 8;
            }
        }
        if (this.getEntityWorld().rand.nextInt(35) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(5.0f);
        }
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
        }
        return var8;
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
        if (par1EntityLiving instanceof Godzilla) {
            return false;
        }
        if (par1EntityLiving instanceof GodzillaHead) {
            return false;
        }
        if (par1EntityLiving instanceof EntityCreeper) {
            return false;
        }
        if (par1EntityLiving instanceof EntityZombie) {
            return false;
        }
        if (par1EntityLiving instanceof EntitySpider) {
            return false;
        }
        if (par1EntityLiving instanceof EntitySkeleton) {
            return false;
        }
        if (par1EntityLiving instanceof Ghost) {
            return false;
        }
        if (par1EntityLiving instanceof GhostSkelly) {
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

    private boolean isVillagerTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
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
        return par1EntityLiving instanceof EntityVillager;
    }

    private net.minecraft.entity.EntityLivingBase doJumpDamage(double X, double Y, double Z, double dist, double damage, int knock) {
        AxisAlignedBB bb = new AxisAlignedBB((double)(X - dist), (double)(Y - 10.0), (double)(Z - dist), (double)(X + dist), (double)(Y + 10.0), (double)(Z + dist));
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, bb);
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (var4 == null || var4 == this || !var4.isEntityAlive() || var4 instanceof Godzilla || var4 instanceof GodzillaHead || var4 instanceof Ghost || var4 instanceof GhostSkelly) continue;
            DamageSource var21 = null;
            var21 = DamageSource.setExplosionSource(null);
            var21.setExplosion();
            var4.attackEntityFrom(var21, (float)damage / 2.0f);
            var4.attackEntityFrom(DamageSource.FALL, (float)damage / 2.0f);
            this.getEntityWorld().playSoundAtEntity((Entity)var4, "random.explode", 0.85f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5f);
            if (knock == 0) continue;
            double ks = 3.5;
            double inair = 0.75;
            float f3 = (float)Math.atan2(var4.posZ - this.posZ, var4.posX - this.posX);
            var4.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return null;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            this.head_found = 1;
            return null;
        }
        List var5 = null;
        Iterator var2 = null;
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        net.minecraft.entity.EntityLivingBase ret = null;
        boolean vf = false;
        var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(64.0, 40.0, 64.0));
        if (var5 == null) {
            return null;
        }
        Collections.sort(var5, this.TargetSorter);
        var2 = var5.iterator();
        this.head_found = 0;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (var4 instanceof GodzillaHead) {
                this.head_found = 1;
            }
            if (!vf && this.isVillagerTarget(var4, false)) {
                ret = var4;
                vf = true;
            }
            if (ret != null || vf || !this.isSuitableTarget(var4, false)) continue;
            ret = var4;
        }
        return ret;
    }

    public boolean getCanSpawnHere() {
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.getEntityWorld().isDaytime()) {
            return false;
        }
        if (this.posY < 50.0) {
            return false;
        }
        if (OreSpawnMain.godzilla_has_spawned != 0) {
            return false;
        }
        if (this.getEntityWorld().rand.nextInt(40) != 1) {
            return false;
        }
        for (int k = -8; k <= 8; ++k) {
            for (int j = -8; j <= 8; ++j) {
                for (int i = 5; i < 15; ++i) {
                    Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        Godzilla target = null;
        target = (Godzilla)this.getEntityWorld().findNearestEntityWithinAABB(Godzilla.class, this.getEntityBoundingBox().expand(64.0, 16.0, 64.0), (Entity)this);
        if (target != null) {
            return false;
        }
        if (!this.getEntityWorld().isRemote) {
            OreSpawnMain.godzilla_has_spawned = 1;
        }
        return true;
    }

    public final int getAttacking() {
        return 0 /* this.dataManager.get(20) */;
    }

    public final void setAttacking(int par1) {
//         this.dataManager.set(20, (Object)((byte)par1));
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(10) - (double)OreSpawnMain.OreSpawnRand.nextInt(10), this.posY + 4.0 + (double)this.getEntityWorld().rand.nextInt(10), this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(10) - (double)OreSpawnMain.OreSpawnRand.nextInt(10), is);
        if (var3 != null) {
            this.getEntityWorld().spawnEntity((Entity)var3);
        }
        return is;
    }

    private ItemStack dropItemRandAt(Item index, int par1, double dx, double dz) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.getEntityWorld(), dx + (double)OreSpawnMain.OreSpawnRand.nextInt(10) - (double)OreSpawnMain.OreSpawnRand.nextInt(10), this.posY + 4.0 + (double)this.getEntityWorld().rand.nextInt(6), dz + (double)OreSpawnMain.OreSpawnRand.nextInt(10) - (double)OreSpawnMain.OreSpawnRand.nextInt(10), is);
        if (var3 != null) {
            this.getEntityWorld().spawnEntity((Entity)var3);
        }
        return is;
    }

    private boolean isCrushable(Block bid) {
        if (bid == null) {
            return false;
        }
        if (!this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            return false;
        }
        if (bid == Blocks.GRASS) {
            return false;
        }
        if (bid == Blocks.DIRT) {
            return false;
        }
        if (bid == Blocks.STONE) {
            return false;
        }
        if (bid == Blocks.FARMLAND) {
            return false;
        }
        if (bid == Blocks.WATER) {
            return false;
        }
        if (bid == Blocks.FLOWING_WATER) {
            return false;
        }
        if (bid == Blocks.LAVA) {
            return false;
        }
        if (bid == Blocks.FLOWING_LAVA) {
            return false;
        }
        if (bid == Blocks.BEDROCK) {
            return false;
        }
        if (bid == Blocks.OBSIDIAN) {
            return false;
        }
        if (bid == Blocks.SAND) {
            return false;
        }
        if (bid == Blocks.GRAVEL) {
            return false;
        }
        if (bid == Blocks.IRON_BLOCK) {
            return false;
        }
        if (bid == Blocks.DIAMOND_BLOCK) {
            return false;
        }
        if (bid == Blocks.EMERALD_BLOCK) {
            return false;
        }
        if (bid == Blocks.GOLD_BLOCK) {
            return false;
        }
        if (bid == Blocks.NETHERRACK) {
            return false;
        }
        if (bid == Blocks.END_STONE) {
            return false;
        }
        if (bid == OreSpawnMain.MyBlockAmethystBlock) {
            return false;
        }
        if (bid == OreSpawnMain.MyBlockRubyBlock) {
            return false;
        }
        if (bid == OreSpawnMain.MyBlockUraniumBlock) {
            return false;
        }
        if (bid == OreSpawnMain.MyBlockTitaniumBlock) {
            return false;
        }
        if (bid == OreSpawnMain.CrystalStone) {
            return false;
        }
        return bid != OreSpawnMain.CrystalGrass;
    }

    private void firecanon(net.minecraft.entity.EntityLivingBase e) {
        double yoff = 19.0;
        double xzoff = 22.0;
        BetterFireball bf = null;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.stream_count > 0) {
            bf = new BetterFireball(this.getEntityWorld(), (net.minecraft.entity.EntityLivingBase)this, e.posX - cx, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - cz);
            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
            bf.setPosition(cx, this.posY + yoff, cz);
            bf.setBig();
            this.getEntityWorld().playSoundAtEntity((Entity)this, "random.fuse", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            this.getEntityWorld().spawnEntity((Entity)bf);
            for (int i = 0; i < 5; ++i) {
                float r1 = 5.0f * (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat());
                float r2 = 3.0f * (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat());
                float r3 = 5.0f * (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat());
                bf = new BetterFireball(this.getEntityWorld(), (net.minecraft.entity.EntityLivingBase)this, e.posX - cx + (double)r1, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff) + (double)r2, e.posZ - cz + (double)r3);
                bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                bf.setPosition(cx, this.posY + yoff, cz);
                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                    bf.setSmall();
                }
                this.getEntityWorld().playSoundAtEntity((Entity)this, "random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.getEntityWorld().spawnEntity((Entity)bf);
            }
            --this.stream_count;
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        float s;
        if (!(par1Entity == null || !(par1Entity instanceof net.minecraft.entity.EntityLivingBase) || !((s = par1Entity.height * par1Entity.width) > 30.0f) || MyUtils.isRoyalty(par1Entity) || par1Entity instanceof Godzilla || par1Entity instanceof GodzillaHead || par1Entity instanceof PitchBlack || par1Entity instanceof Kraken)) {
            net.minecraft.entity.EntityLivingBase e = (net.minecraft.entity.EntityLivingBase)par1Entity;
            e.setHealth(e.getHealth() / 2.0f);
            e.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)OreSpawnMain.Godzilla_stats.attack * 10.0f);
            this.large_unknown_detected = 1;
        }
        if (par1Entity != null && par1Entity instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon)par1Entity;
            DamageSource var21 = null;
            var21 = DamageSource.setExplosionSource(null);
            var21.setExplosion();
            if (this.getEntityWorld().rand.nextInt(6) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, var21, (float)OreSpawnMain.Godzilla_stats.attack / 2.0f);
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, var21, (float)OreSpawnMain.Godzilla_stats.attack / 2.0f);
            }
        }
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
                double ks = 3.2;
                double inair = 0.3;
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
        Entity e;
        boolean ret = false;
        float dm = par2;
        float s = 0.0f;
        if (this.hurt_timer > 0) {
            return false;
        }
        if (dm > 750.0f) {
            dm = 750.0f;
        }
        if ((e = par1DamageSource.getEntity()) != null && e instanceof net.minecraft.entity.EntityLivingBase) {
            net.minecraft.entity.EntityLivingBase enl = (net.minecraft.entity.EntityLivingBase)e;
            s = enl.height * enl.width;
            if (!(!(s > 30.0f) || MyUtils.isRoyalty((Entity)enl) || enl instanceof Godzilla || enl instanceof GodzillaHead || enl instanceof PitchBlack || enl instanceof Kraken)) {
                dm /= 10.0f;
                this.hurt_timer = 50;
                this.large_unknown_detected = 1;
            }
        }
        if (!par1DamageSource.getDamageType().equals("cactus")) {
            ret = super.attackEntityFrom(par1DamageSource, dm);
            this.hurt_timer = 20;
            e = par1DamageSource.getEntity();
            if (e != null && e instanceof net.minecraft.entity.EntityLivingBase && !(e instanceof GodzillaHead) && !(e instanceof Godzilla)) {
                this.setAttackTarget((net.minecraft.entity.EntityLivingBase)e);
                this.setTarget(e);
                this.getNavigator().tryMoveToEntityLiving((Entity)((net.minecraft.entity.EntityLivingBase)e), 1.2);
            }
        }
        return ret;
    }

    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
    }

    private void doLightningAttack(net.minecraft.entity.EntityLivingBase e) {
        if (e == null) {
            return;
        }
        float var2 = 100.0f;
        e.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), var2);
        e.setFire(5);
        for (int var3 = 0; var3 < 20; ++var3) {
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, e.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), e.posY + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), e.posZ + (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, e.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), e.posY + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), e.posZ + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, e.posX, e.posY, e.posZ, this.getEntityWorld().rand.nextGaussian(), this.getEntityWorld().rand.nextGaussian(), this.getEntityWorld().rand.nextGaussian());
        }
        this.getEntityWorld().playSoundAtEntity((Entity)e, "random.explode", 0.5f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5f);
        if (!this.getEntityWorld().isRemote) {
            this.getEntityWorld().createExplosion((Entity)this, e.posX, e.posY, e.posZ, 3.0f, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
        }
        this.getEntityWorld().addWeatherEffect((Entity)new EntityLightningBolt(this.getEntityWorld(), e.posX, e.posY + 1.0, e.posZ));
        this.getEntityWorld().addWeatherEffect((Entity)new EntityLightningBolt(this.getEntityWorld(), this.posX, this.posY + 15.0, this.posZ));
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int var5 = 50 + this.getEntityWorld().rand.nextInt(30);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(OreSpawnMain.MyGodzillaScale, 1);
        }
        var5 = 100 + this.getEntityWorld().rand.nextInt(160);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.BEEF, 1);
        }
        var5 = 50 + this.getEntityWorld().rand.nextInt(60);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.BONE, 1);
        }
        int i = 25 + this.getEntityWorld().rand.nextInt(15);
        block80: for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.getEntityWorld().rand.nextInt(80);
            switch (var3) {
                case 0: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateSword, 1);
                    continue block80;
                }
                case 1: {
                    is = this.dropItemRand(Items.DIAMOND, 1);
                    continue block80;
                }
                case 2: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIAMOND_BLOCK), 1);
                    continue block80;
                }
                case 3: {
                    is = this.dropItemRand(Items.DIAMOND_SWORD, 1);
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
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 4: {
                    is = this.dropItemRand(Items.DIAMOND_SHOVEL, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 5: {
                    is = this.dropItemRand(Items.DIAMOND_PICKAXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 6: {
                    is = this.dropItemRand(Items.DIAMOND_AXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 7: {
                    is = this.dropItemRand(Items.DIAMOND_HOE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 8: {
                    is = this.dropItemRand((Item)Items.DIAMOND_HELMET, 1);
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
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 9: {
                    is = this.dropItemRand((Item)Items.DIAMOND_CHESTPLATE, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 10: {
                    is = this.dropItemRand((Item)Items.DIAMOND_LEGGINGS, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 11: {
                    is = this.dropItemRand((Item)Items.DIAMOND_BOOTS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 12: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateBow, 1);
                    continue block80;
                }
                case 13: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateAxe, 1);
                    continue block80;
                }
                case 14: {
                    is = this.dropItemRand(Items.IRON_INGOT, 1);
                    continue block80;
                }
                case 15: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimatePickaxe, 1);
                    continue block80;
                }
                case 16: {
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
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 17: {
                    is = this.dropItemRand(Items.IRON_SHOVEL, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 18: {
                    is = this.dropItemRand(Items.IRON_PICKAXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 19: {
                    is = this.dropItemRand(Items.IRON_AXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 20: {
                    is = this.dropItemRand(Items.IRON_HOE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 21: {
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
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 22: {
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 23: {
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 24: {
                    is = this.dropItemRand((Item)Items.IRON_BOOTS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 25: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateShovel, 1);
                    continue block80;
                }
                case 26: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.IRON_BLOCK), 1);
                    continue block80;
                }
                case 27: {
                    is = this.dropItemRand(Items.GOLD_NUGGET, 1);
                    continue block80;
                }
                case 28: {
                    is = this.dropItemRand(Items.GOLD_INGOT, 1);
                    continue block80;
                }
                case 29: {
                    is = this.dropItemRand(Items.GOLDEN_CARROT, 1);
                    continue block80;
                }
                case 30: {
                    is = this.dropItemRand(Items.GOLDEN_SWORD, 1);
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
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 31: {
                    is = this.dropItemRand(Items.GOLDEN_SHOVEL, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 32: {
                    is = this.dropItemRand(Items.GOLDEN_PICKAXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 33: {
                    is = this.dropItemRand(Items.GOLDEN_AXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 34: {
                    is = this.dropItemRand(Items.GOLDEN_HOE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 35: {
                    is = this.dropItemRand((Item)Items.GOLDEN_HELMET, 1);
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
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 36: {
                    is = this.dropItemRand((Item)Items.GOLDEN_CHESTPLATE, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 37: {
                    is = this.dropItemRand((Item)Items.GOLDEN_LEGGINGS, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 38: {
                    is = this.dropItemRand((Item)Items.GOLDEN_BOOTS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 39: {
                    this.dropItemRand(Items.GOLDEN_APPLE, 1);
                    continue block80;
                }
                case 40: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GOLD_BLOCK), 1);
                    continue block80;
                }
                case 41: {
                    EntityItem var33 = null;
                    is = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
                    var33 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(3) - (double)OreSpawnMain.OreSpawnRand.nextInt(3), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(3) - (double)OreSpawnMain.OreSpawnRand.nextInt(3), is);
                    if (var33 == null) continue block80;
                    this.getEntityWorld().spawnEntity((Entity)var33);
                    continue block80;
                }
                case 42: {
                    is = this.dropItemRand(OreSpawnMain.MyExperienceSword, 1);
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
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 43: {
                    is = this.dropItemRand((Item)OreSpawnMain.ExperienceHelmet, 1);
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
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 44: {
                    is = this.dropItemRand((Item)OreSpawnMain.ExperienceBody, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 45: {
                    is = this.dropItemRand((Item)OreSpawnMain.ExperienceLegs, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 46: {
                    is = this.dropItemRand((Item)OreSpawnMain.ExperienceBoots, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 47: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystSword, 1);
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
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 48: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystShovel, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 49: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystPickaxe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 50: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystAxe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 51: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystHoe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 52: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)OreSpawnMain.MyBlockAmethystBlock), 1);
                    continue block80;
                }
                case 53: {
                    is = this.dropItemRand((Item)OreSpawnMain.AmethystHelmet, 1);
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
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 54: {
                    is = this.dropItemRand((Item)OreSpawnMain.AmethystBody, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 55: {
                    is = this.dropItemRand((Item)OreSpawnMain.AmethystLegs, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 56: {
                    is = this.dropItemRand((Item)OreSpawnMain.AmethystBoots, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 57: {
                    is = this.dropItemRand((Item)OreSpawnMain.RubyHelmet, 1);
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
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 58: {
                    is = this.dropItemRand((Item)OreSpawnMain.RubyBody, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 59: {
                    is = this.dropItemRand((Item)OreSpawnMain.RubyLegs, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 60: {
                    is = this.dropItemRand((Item)OreSpawnMain.RubyBoots, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 61: {
                    is = this.dropItemRand(OreSpawnMain.MyRubySword, 1);
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
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 62: {
                    is = this.dropItemRand(OreSpawnMain.MyRubyShovel, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 63: {
                    is = this.dropItemRand(OreSpawnMain.MyRubyPickaxe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 64: {
                    is = this.dropItemRand(OreSpawnMain.MyRubyAxe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 65: {
                    is = this.dropItemRand(OreSpawnMain.MyRubyHoe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 66: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)OreSpawnMain.MyBlockRubyBlock), 1);
                    continue block80;
                }
                case 67: {
                    is = this.dropItemRand((Item)OreSpawnMain.UltimateHelmet, 1);
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
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 68: {
                    is = this.dropItemRand((Item)OreSpawnMain.UltimateBody, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 69: {
                    is = this.dropItemRand((Item)OreSpawnMain.UltimateLegs, 1);
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
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 70: {
                    is = this.dropItemRand((Item)OreSpawnMain.UltimateBoots, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block80;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block80;
                }
                case 71: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateShovel, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 73: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimatePickaxe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 74: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateAxe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
                case 75: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateHoe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block80;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block80;
                }
            }
        }
    }
}


}