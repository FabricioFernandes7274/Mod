/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
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
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.effect.EntityLightningBolt
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.AxisAlignedBB;

public class TheQueen extends EntityMob {
    public net.minecraft.util.math.BlockPos currentFlightTarget;
    private int attack_level = 0;
    private int attdam = 0;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import danger.orespawn.BetterFireball;
// import danger.orespawn.GenericTargetSorter;
import danger.orespawn.Ghost;
import danger.orespawn.GhostSkelly;
import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.PurplePower;
import danger.orespawn.QueenHead;
import danger.orespawn.TheKing;
import danger.orespawn.ThunderBolt;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
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
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
    private int mood = 0;
    private int always_mad = 0;

    public TheQueen(World worldIn) {
        super(worldIn);
        if (OreSpawnMain.PlayNicely == 0) {
            this.setSize(22.0f, 24.0f);
        } else {
            this.setSize(5.5f, 6.0f);
        }
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 25000;
        this.isImmuneToFire = true;
        //this.fireResistance = 5000;
        this.noClip = true;
//         this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderDistanceWeight = 12.0;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.62f);
        this.attdam = OreSpawnMain.TheQueen_stats.attack;
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.attdam);
    }

    protected void entityInit() {
        int i = 0;
        super.entityInit();
//         this.dataManager.register(20, (Object)i);
//         this.dataManager.register(21, (Object)OreSpawnMain.PlayNicely);
//         this.dataManager.register(22, (Object)this.mood);
//         this.dataManager.register(23, (Object)this.attack_level);
    }

    public int getPlayNicely() {
        return 0 /* this.dataManager.get(21) */;
    }

    public int getIsHappy() {
        return 0 /* this.dataManager.get(22) */;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean isInRangeToRenderDist(double par1) {
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean isInRangeToRenderVec3D(Vec3d par1Vec3) {
        return true;
    }

    protected boolean canDespawn() {
        return false;
    }

    public int getAttacking() {
        return 0 /* this.dataManager.get(20) */;
    }

    public void setAttacking(int par1) {
//         this.dataManager.set(20, (Object)par1);
    }

    public int getPower() {
        return 0 /* this.dataManager.get(23) */;
    }

    public void setPower(int par1) {
//         this.dataManager.set(23, (Object)par1);
    }

    protected float getSoundVolume() {
        return 1.35f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE; }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.TheQueen_stats.health;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock((Block)Blocks.YELLOW_FLOWER);
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(20) - (double)OreSpawnMain.OreSpawnRand.nextInt(20), this.posY + 12.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(20) - (double)OreSpawnMain.OreSpawnRand.nextInt(20), new ItemStack(index, par1, 0));
        this.getEntityWorld().spawnEntity((Entity)var3);
    }

    protected void dropFewItems(boolean par1, int par2) {
        this.dropItemRand(OreSpawnMain.MyRoyal, 1);
        this.dropItemRand(OreSpawnMain.ThePrinceEgg, 1);
        TheQueen.spawnCreature(this.getEntityWorld(), "The Princess", this.posX, this.posY + 10.0, this.posZ);
        for (int i = 0; i < 56; ++i) {
            this.dropItemRand(OreSpawnMain.MyQueenScale, 1);
            this.dropItemRand(Items.BEEF, 1);
            this.dropItemRand(Items.BONE, 1);
            this.dropItemRand(Items.ROTTEN_FLESH, 1);
        }
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public boolean isHappy() {
        return this.getIsHappy() == 0;
    }

    public void onUpdate() {
        super.onUpdate();
        ++this.wing_sound;
        if (this.wing_sound > 30) {
            if (!this.getEntityWorld().isRemote) {
                this.getEntityWorld().playSound(null, this.posX, this.posY, this.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 1.75f, 0.75f);
            }
            this.wing_sound = 0;
        }
        this.noClip = true;
        this.motionY *= 0.6;
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() * 3 / 4)) {
            this.attdam = OreSpawnMain.TheQueen_stats.attack * 20;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 2)) {
            this.attdam = OreSpawnMain.TheQueen_stats.attack * 100;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 3)) {
            this.attdam = OreSpawnMain.TheQueen_stats.attack * 500;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 4)) {
            this.attdam = OreSpawnMain.TheQueen_stats.attack * 1000;
        }
        if (this.getEntityWorld().isRemote && this.getPower() > 800) {
            float f = 7.0f;
            if (this.getEntityWorld().rand.nextInt(4) == 1) {
                for (int i = 0; i < 10; ++i) {
                    this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + 14.0, this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)), (this.getEntityWorld().rand.nextGaussian() - this.getEntityWorld().rand.nextGaussian()) / 5.0 + this.motionX * 3.0, (this.getEntityWorld().rand.nextGaussian() - this.getEntityWorld().rand.nextGaussian()) / 5.0, (this.getEntityWorld().rand.nextGaussian() - this.getEntityWorld().rand.nextGaussian()) / 5.0 + this.motionZ * 3.0);
                }
            }
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4;
        if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase && !this.getEntityWorld().isRemote) {
            net.minecraft.entity.EntityLivingBase e = (net.minecraft.entity.EntityLivingBase)par1Entity;
            if (!e.isDead) {
                if (this.ev == e) {
                    if (this.evh < e.getHealth()) {
                        e.setHealth(this.evh);
                    }
                } else {
                    this.ev = e;
                }
                if (e.width * e.height > 30.0f) {
                    e.setHealth(e.getHealth() * 3.0f / 4.0f);
                    e.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)this.attdam);
                }
                this.evh = e.getHealth();
                if (this.evh <= 0.0f) {
                    this.ev.setDead();
                }
            } else {
                this.ev = null;
                this.evh = 0.0f;
            }
        }
        if (par1Entity != null && par1Entity instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon)par1Entity;
            DamageSource var21 = null;
            var21 = DamageSource.setExplosionSource(null);
            var21.setExplosion();
            if (this.getEntityWorld().rand.nextInt(6) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, var21, (float)this.attdam);
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, var21, (float)this.attdam);
            }
        }
        if (var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)this.attdam)) {
            double ks = 2.75;
            double inair = 0.2;
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            inair += (double)(this.getEntityWorld().rand.nextFloat() * 0.25f);
            if (par1Entity.isDead || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                inair *= 1.5;
            }
            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return var4;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.getEntityWorld().rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 8.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    private boolean tooFarFromHome() {
        float d1 = (float)(this.posX - (double)this.homex);
        float d2 = (float)(this.posZ - (double)this.homez);
        return (d1 = (float)Math.sqrt(d1 * d1 + d2 * d2)) > 120.0f;
    }

    protected void updateAITasks() {
        Block bid;
        int k;
        int i;
        int j;
        int xdir = 1;
        int zdir = 1;
        int attrand = 5;
        boolean updown = false;
        int which = 0;
        net.minecraft.entity.EntityLivingBase e = null;
        net.minecraft.entity.EntityLivingBase f = null;
        double rr = 0.0;
        double rhdir = 0.0;
        double rdd = 0.0;
        double pi = 3.1415926545;
        double var1 = 0.0;
        double var3 = 0.0;
        double var5 = 0.0;
        float var7 = 0.0f;
        float var8 = 0.0f;
        EntityLiving newent = null;
        double xzoff = 8.0;
        double yoff = 14.0;
        List kinglist = null;
        Iterator var2 = null;
        TheKing var4 = null;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.ev != null) {
            if (this.getDistanceSq((Entity)this.ev) < 2000.0 && !this.ev.isDead) {
                if (this.evh < this.ev.getHealth()) {
                    this.ev.setHealth(this.evh);
                } else {
                    this.evh = this.ev.getHealth();
                }
                if (this.evh <= 0.0f) {
                    this.ev.setDead();
                }
            } else {
                this.ev = null;
                this.evh = 0.0f;
            }
        }
        if (this.attack_level > 1000) {
            if (this.mood == 1) {
                j = 15;
                if (this.player_hit_count < 10) {
                    j = 45;
                }
                for (i = 0; i < j; ++i) {
                    Entity ppwr = TheQueen.spawnCreature(this.getEntityWorld(), "PurplePower", this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)));
                    if (ppwr == null) continue;
                    ppwr.motionX = this.motionX * 3.0;
                    ppwr.motionZ = this.motionZ * 3.0;
                }
            } else {
                int m;
                if (this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    block1: for (m = 0; m < 25; ++m) {
                        i = this.getEntityWorld().rand.nextInt(25) - this.getEntityWorld().rand.nextInt(25);
                        k = this.getEntityWorld().rand.nextInt(25) - this.getEntityWorld().rand.nextInt(25);
                        for (j = -20; j < 20; ++j) {
                            bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k)).getBlock(;
                            if (bid == Blocks.GRASS) {
                                if (this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k)).getBlock( != Blocks.AIR) continue block1;
                                which = this.getEntityWorld().rand.nextInt(8);
                                if (which == 0) {
                                    this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), (Block.getDefaultState())Blocks.RED_FLOWER);
                                }
                                if (which == 1) {
                                    this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), (Block.getDefaultState())Blocks.YELLOW_FLOWER);
                                }
                                if (which == 2) {
                                    this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), OreSpawnMain.MyFlowerBlueBlock.getDefaultState());
                                }
                                if (which == 3) {
                                    this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), OreSpawnMain.MyFlowerPinkBlock.getDefaultState());
                                }
                                if (which == 4) {
                                    this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), OreSpawnMain.CrystalFlowerRedBlock.getDefaultState());
                                }
                                if (which == 5) {
                                    this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), OreSpawnMain.CrystalFlowerGreenBlock.getDefaultState());
                                }
                                if (which == 6) {
                                    this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), OreSpawnMain.CrystalFlowerBlueBlock.getDefaultState());
                                }
                                if (which != 7) continue block1;
                                this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), OreSpawnMain.CrystalFlowerYellowBlock.getDefaultState());
                                continue block1;
                            }
                            if (bid == Blocks.DIRT && this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k)).getBlock( == Blocks.AIR) {
                                this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k), (Block.getDefaultState())Blocks.GRASS);
                                continue block1;
                            }
                            if (bid == Blocks.STONE && this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k)).getBlock( == Blocks.AIR) {
                                this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), Blocks.DIRT.getDefaultState());
                                continue block1;
                            }
                            if (bid == Blocks.SAND && this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k)).getBlock( == Blocks.AIR) {
                                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                                    this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k), Blocks.CACTUS.getDefaultState());
                                    continue block1;
                                }
                                this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k), Blocks.DIRT.getDefaultState());
                                continue block1;
                            }
                            if (bid == Blocks.LAVA && this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k)).getBlock( == Blocks.AIR) {
                                this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k), Blocks.WATER.getDefaultState());
                                continue block1;
                            }
                            if (bid == Blocks.FLOWING_LAVA && this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k)).getBlock( == Blocks.AIR) {
                                this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k), (Block.getDefaultState())Blocks.FLOWING_WATER);
                                continue block1;
                            }
                            if (bid == Blocks.AIR && j > 0) continue block1;
                        }
                    }
                }
                for (m = 0; m < 10; ++m) {
                    i = this.getEntityWorld().rand.nextInt(15) - this.getEntityWorld().rand.nextInt(15);
                    k = this.getEntityWorld().rand.nextInt(15) - this.getEntityWorld().rand.nextInt(15);
                    j = this.getEntityWorld().rand.nextInt(20);
                    bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k)).getBlock(;
                    if (bid != Blocks.AIR) continue;
                    newent = this.getEntityWorld().rand.nextInt(2) == 0 ? (EntityLiving)TheQueen.spawnCreature(this.getEntityWorld(), "Butterfly", this.posX + (double)i, this.posY + (double)j, this.posZ + (double)k) : (EntityLiving)TheQueen.spawnCreature(this.getEntityWorld(), "Bird", this.posX + (double)i, this.posY + (double)j, this.posZ + (double)k);
                }
            }
            this.attack_level = 1;
        }
        if (this.attack_level > 1) {
            --this.attack_level;
        }
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.homex == 0 && this.homez == 0 || this.guard_mode == 0) {
            this.homex = (int)this.posX;
            this.homez = (int)this.posZ;
        }
        if (this.getHealth() > (float)(this.mygetMaxHealth() - 2) && this.getEntityWorld().rand.nextInt(500) == 1) {
            this.mood = 0;
        }
        if (this.always_mad != 0) {
            this.mood = 1;
        }
        if (this.mood == 0) {
            this.attack_level += 10;
        }
        ++this.ticker;
        if (this.ticker > 30000) {
            this.ticker = 0;
        }
        if (this.ticker % 60 == 0) {
            this.stream_count = 10;
        }
        if (this.ticker % 70 == 0) {
            this.stream_count_l = 6;
        }
        if (this.ticker % 10 == 0) {
//             this.dataManager.set(21, (Object)OreSpawnMain.PlayNicely);
//             this.dataManager.set(22, (Object)this.mood);
            this.setPower(this.attack_level);
        }
        if (this.backoff_timer > 0) {
            --this.backoff_timer;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 2)) {
            attrand = 3;
        }
        this.noClip = true;
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.tooFarFromHome() || this.getEntityWorld().rand.nextInt(200) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 9.1f) {
            zdir = this.getEntityWorld().rand.nextInt(120);
            xdir = this.getEntityWorld().rand.nextInt(120);
            if (this.getEntityWorld().rand.nextInt(2) == 0) {
                zdir = -zdir;
            }
            if (this.getEntityWorld().rand.nextInt(2) == 0) {
                xdir = -xdir;
            }
            int dist = 0;
            for (i = -5; i <= 5; i += 5) {
                block5: for (j = -5; j <= 5; j += 5) {
                    bid = this.getEntityWorld().getBlockState(new BlockPos(this.homex + j, (int)this.posY, this.homez + i)).getBlock();
                    if (bid != Blocks.AIR) {
                        for (k = 1; k < 20; ++k) {
                            bid = this.getEntityWorld().getBlockState(new BlockPos(this.homex + j, (int)this.posY + k, this.homez + i)).getBlock();
                            ++dist;
                            if (bid == Blocks.AIR) continue block5;
                        }
                        continue;
                    }
                    for (k = 1; k < 20; ++k) {
                        bid = this.getEntityWorld().getBlockState(new BlockPos(this.homex + j, (int)this.posY - k, this.homez + i)).getBlock();
                        --dist;
                        if (bid != Blocks.AIR) continue block5;
                    }
                }
            }
            if ((int)(this.posY + (double)(dist = dist / 9 + 2)) > 230) {
                dist = 230 - (int)this.posY;
            }
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos(this.homex + xdir, (int)(this.posY + (double)dist), this.homez + zdir);
            if (this.mood == 0 && (kinglist = this.getEntityWorld().getEntitiesWithinAABB(TheKing.class, this.getEntityBoundingBox().expand(64.0, 32.0, 64.0))) != null) {
//                 Collections.sort(kinglist, this.TargetSorter);
                var2 = kinglist.iterator();
                if (var2.hasNext()) {
                    var4 = null;
                    var4 = (TheKing)((Object)var2.next());
                    this.guard_mode = 0;
                    zdir = this.getEntityWorld().rand.nextInt(16);
                    xdir = this.getEntityWorld().rand.nextInt(16);
                    if (this.getEntityWorld().rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)var4.posX + xdir, (int)(var4.posY + (double)(this.getEntityWorld().rand.nextInt(8) - this.getEntityWorld().rand.nextInt(8))), (int)var4.posZ + zdir);
                }
            }
        } else if (this.getEntityWorld().rand.nextInt(attrand) == 0) {
            float d1;
            e = this.rt;
            if (OreSpawnMain.PlayNicely != 0 || this.isHappy()) {
                e = null;
            }
            if (e != null && (e instanceof TheQueen || e instanceof QueenHead)) {
                this.rt = null;
                e = null;
            }
            if (e != null) {
                d1 = (float)(e.posX - (double)this.homex);
                float d2 = (float)(e.posZ - (double)this.homez);
                d1 = (float)Math.sqrt(d1 * d1 + d2 * d2);
                if (e.isDead || this.getEntityWorld().rand.nextInt(450) == 1 || d1 > 128.0f && this.guard_mode == 1) {
                    e = null;
                    this.rt = null;
                }
                if (e != null && !this.MyCanSee(e)) {
                    e = null;
                }
            }
            f = this.findSomethingToAttack();
            if (this.head_found == 0 && this.mood == 1) {
                newent = (EntityLiving)TheQueen.spawnCreature(this.getEntityWorld(), "QueenHead", this.posX, this.posY + 20.0, this.posZ);
            }
            if (e == null) {
                e = f;
            }
            if (e != null) {
                d1 = e.width * e.height;
                if (this.attack_level < 1000) {
                    this.attack_level += 15;
                    if (this.getHealth() < (float)(this.mygetMaxHealth() / 2)) {
                        this.attack_level += 15;
                    }
                    if (d1 > 50.0f) {
                        this.attack_level += 15;
                    }
                    if (d1 > 100.0f) {
                        this.attack_level += 15;
                    }
                    if (d1 > 200.0f) {
                        this.attack_level += 25;
                    }
                }
                this.setAttacking(1);
                if (this.backoff_timer == 0) {
                    int dist = (int)(e.posY + (double)(e.height / 2.0f) + 1.0);
                    if (dist > 230) {
                        dist = 230;
                    }
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, dist, (int)e.posZ);
                    if (this.getEntityWorld().rand.nextInt(50) == 1) {
                        this.backoff_timer = 90 + this.getEntityWorld().rand.nextInt(90);
                    }
                } else if (this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 9.1f) {
                    zdir = this.getEntityWorld().rand.nextInt(20) + 30;
                    xdir = this.getEntityWorld().rand.nextInt(20) + 30;
                    if (this.getEntityWorld().rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }
                    int dist = 0;
                    for (i = -5; i <= 5; i += 5) {
                        block9: for (j = -5; j <= 5; j += 5) {
                            bid = this.getEntityWorld().getBlockState(new BlockPos((int)e.posX + j, (int)this.posY, (int)e.posZ + i)).getBlock(;
                            if (bid != Blocks.AIR) {
                                for (k = 1; k < 20; ++k) {
                                    bid = this.getEntityWorld().getBlockState(new BlockPos((int)e.posX + j, (int)this.posY + k, (int)e.posZ + i)).getBlock(;
                                    ++dist;
                                    if (bid == Blocks.AIR) continue block9;
                                }
                                continue;
                            }
                            for (k = 1; k < 20; ++k) {
                                bid = this.getEntityWorld().getBlockState(new BlockPos((int)e.posX + j, (int)this.posY - k, (int)e.posZ + i)).getBlock(;
                                --dist;
                                if (bid != Blocks.AIR) continue block9;
                            }
                        }
                    }
                    if ((int)(this.posY + (double)(dist = dist / 9 + 2)) > 230) {
                        dist = 230 - (int)this.posY;
                    }
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX + xdir, (int)(this.posY + (double)dist), (int)e.posZ + zdir);
                }
                if (this.getDistanceSq((Entity)e) < 900.0) {
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        this.doJumpDamage(this.posX, this.posY, this.posZ, 15.0, OreSpawnMain.TheQueen_stats.attack / 4, 0);
                    }
                    this.attackEntityAsMob((Entity)e);
                }
                double dx = this.posX + 20.0 * Math.sin(Math.toRadians(this.rotationYaw));
                double dz = this.posZ - 20.0 * Math.cos(Math.toRadians(this.rotationYaw));
                if (this.getEntityWorld().rand.nextInt(3) == 1) {
                    this.doJumpDamage(dx, this.posY + 10.0, dz, 15.0, OreSpawnMain.TheQueen_stats.attack / 2, 1);
                }
                if (this.getHorizontalDistanceSqToEntity((Entity)e) > 900.0) {
                    which = this.getEntityWorld().rand.nextInt(2);
                    if (which == 0) {
                        if (this.stream_count > 0) {
                            this.setAttacking(1);
                            rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                            rhdir = Math.toRadians((this.rotationYaw + 90.0f) % 360.0f);
                            rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                            if (rdd > pi) {
                                rdd -= pi * 2.0;
                            }
                            if ((rdd = Math.abs(rdd)) < 0.5) {
                                this.firecanon(e);
                            }
                        }
                    } else if (this.stream_count_l > 0) {
                        this.setAttacking(1);
                        rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                        rhdir = Math.toRadians((this.rotationYaw + 90.0f) % 360.0f);
                        rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                        if (rdd > pi) {
                            rdd -= pi * 2.0;
                        }
                        if ((rdd = Math.abs(rdd)) < 0.5) {
                            this.firecanonl(e);
                        }
                    }
                }
            } else {
                this.setAttacking(0);
                this.stream_count = 10;
                this.stream_count_l = 6;
            }
        }
        var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.65 - this.motionX) * 0.35;
        this.motionY += (Math.signum(var3) * 0.69999 - this.motionY) * 0.3;
        this.motionZ += (Math.signum(var5) * 0.65 - this.motionZ) * 0.35;
        var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.75f;
        this.rotationYaw += var8 / 8.0f;
        if (this.getEntityWorld().rand.nextInt(32) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(5.0f);
            if (this.player_hit_count < 10) {
                this.heal(50.0f);
            }
        }
        if (this.player_hit_count < 10 && this.getHealth() < 2000.0f) {
            this.heal(2000.0f - this.getHealth());
        }
    }

    private double getHorizontalDistanceSqToEntity(Entity e) {
        double d1 = e.posZ - this.posZ;
        double d2 = e.posX - this.posX;
        return d1 * d1 + d2 * d2;
    }

    private void firecanon(net.minecraft.entity.EntityLivingBase e) {
        double yoff = 14.0;
        double xzoff = 32.0;
        BetterFireball bf = null;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.stream_count > 0) {
            bf = new BetterFireball(this.getEntityWorld(), (net.minecraft.entity.EntityLivingBase)this, e.posX - cx, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - cz);
            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
            bf.setPosition(cx, this.posY + yoff, cz);
            bf.setReallyBig();
            this.getEntityWorld().playSound(null, this.posX, this.posY, this.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.HOSTILE, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            this.getEntityWorld().spawnEntity((Entity)bf);
            for (int i = 0; i < 6; ++i) {
                float r1 = 5.0f * (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat());
                float r2 = 3.0f * (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat());
                float r3 = 5.0f * (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat());
                bf = new BetterFireball(this.getEntityWorld(), (net.minecraft.entity.EntityLivingBase)this, e.posX - cx + (double)r1, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff) + (double)r2, e.posZ - cz + (double)r3);
                bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                bf.setPosition(cx, this.posY + yoff, cz);
                bf.setBig();
                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                    bf.setSmall();
                }
                this.getEntityWorld().playSound(null, this.posX, this.posY, this.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.HOSTILE, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.getEntityWorld().spawnEntity((Entity)bf);
            }
            --this.stream_count;
        }
    }

    private void firecanonl(net.minecraft.entity.EntityLivingBase e) {
        double yoff = 14.0;
        double xzoff = 32.0;
        double var3 = 0.0;
        double var5 = 0.0;
        double var7 = 0.0;
        float var9 = 0.0f;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.stream_count_l > 0) {
            this.getEntityWorld().playSound(null, this.posX, this.posY, this.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.HOSTILE, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            for (int i = 0; i < 3; ++i) {
                ThunderBolt lb = new ThunderBolt(this.getEntityWorld(), cx, this.posY + yoff, cz);
                lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0f, 0.0f);
                var3 = e.posX - lb.posX;
                var5 = e.posY + 0.25 - lb.posY;
                var7 = e.posZ - lb.posZ;
                var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var7 * var7)) * 0.2f;
                lb.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 4.0f);
                lb.motionX *= 3.0;
                lb.motionY *= 3.0;
                lb.motionZ *= 3.0;
                this.getEntityWorld().spawnEntity((Entity)lb);
            }
            --this.stream_count_l;
        }
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

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        float dm = par2;
        if (this.hurt_timer > 0) {
            return false;
        }
        if (dm > 750.0f) {
            dm = 750.0f;
        }
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return false;
        }
        this.mood = 1;
        if (par1DamageSource.isExplosion()) {
            float s = this.getHealth();
            if ((s += par2 / 2.0f) > this.getMaxHealth()) {
                s = this.getMaxHealth();
            }
            this.setHealth(s);
            return false;
        }
        Entity e = par1DamageSource.getTrueSource();
        if (e != null && e instanceof net.minecraft.entity.EntityLivingBase) {
            if (e instanceof PurplePower) {
                return false;
            }
            float s = e.height * e.width;
            if (e instanceof EntityMob && s < 3.0f) {
                e.setDead();
                return false;
            }
        }
        if (!par1DamageSource.getDamageType().equals("cactus")) {
            this.hurt_timer = 20;
            ret = super.attackEntityFrom(par1DamageSource, dm);
            if (e != null && e instanceof net.minecraft.entity.player.EntityPlayer) {
                ++this.player_hit_count;
            }
            if (e != null && e instanceof net.minecraft.entity.EntityLivingBase && this.currentFlightTarget != null && !MyUtils.isRoyalty(e)) {
                this.rt = (net.minecraft.entity.EntityLivingBase)e;
                int dist = (int)e.posY;
                if (dist > 230) {
                    dist = 230;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, dist, (int)e.posZ);
            }
        }
        return ret;
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public int getTotalArmorValue() {
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() * 2 / 3)) {
            return OreSpawnMain.TheQueen_stats.defense + 2;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 2)) {
            return OreSpawnMain.TheQueen_stats.defense + 3;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 3)) {
            return OreSpawnMain.TheQueen_stats.defense + 5;
        }
        return OreSpawnMain.TheQueen_stats.defense;
    }

    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
    }

    public void initCreature() {
    }

    public boolean MyCanSee(net.minecraft.entity.EntityLivingBase e) {
        double xzoff = 10.0;
        int nblks = 20;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        float startx = (float)cx;
        float starty = (float)(this.posY + 14.0);
        float startz = (float)cz;
        float dx = (float)((e.posX - (double)startx) / 20.0);
        float dy = (float)((e.posY + (double)(e.height / 2.0f) - (double)starty) / 20.0);
        float dz = (float)((e.posZ - (double)startz) / 20.0);
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
            Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)(startx += dx), (int)(starty += dy), (int)(startz += dz)).getBlock();
            if (bid == Blocks.AIR) continue;
            return false;
        }
        return true;
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
        if (par1EntityLiving instanceof QueenHead) {
            this.head_found = 1;
            return false;
        }
        if (MyUtils.isRoyalty((Entity)par1EntityLiving)) {
            return false;
        }
        float d1 = (float)(par1EntityLiving.posX - (double)this.homex);
        float d2 = (float)(par1EntityLiving.posZ - (double)this.homez);
        if ((d1 = (float)Math.sqrt(d1 * d1 + d2 * d2)) > 144.0f) {
            return false;
        }
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.isCreative();
        }
        if (par1EntityLiving instanceof EntityHorse) {
            return true;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof EntityDragon) {
            return true;
        }
        return MyUtils.isAttackableNonMob(par1EntityLiving);
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0 || this.isHappy()) {
            this.head_found = 1;
            return null;
        }
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(80.0, 60.0, 80.0));
//         Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        net.minecraft.entity.EntityLivingBase ret = null;
        this.head_found = 0;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (this.isSuitableTarget(var4, false) && ret == null) {
                ret = var4;
            }
            if (ret == null || this.head_found == 0) continue;
            break;
        }
        return ret;
    }

    public void setGuardMode(int i) {
        this.guard_mode = i;
    }

    public void setBadMood(int i) {
        this.always_mad = i;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("KingHomeX", this.homex);
        par1NBTTagCompound.setInteger("KingHomeZ", this.homez);
        par1NBTTagCompound.setInteger("GuardMode", this.guard_mode);
        par1NBTTagCompound.setInteger("PlayerHits", this.player_hit_count);
        par1NBTTagCompound.setInteger("MeanMode", this.always_mad);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.homex = par1NBTTagCompound.getInteger("KingHomeX");
        this.homez = par1NBTTagCompound.getInteger("KingHomeZ");
        this.guard_mode = par1NBTTagCompound.getInteger("GuardMode");
        this.player_hit_count = par1NBTTagCompound.getInteger("PlayerHits");
        this.always_mad = par1NBTTagCompound.getInteger("MeanMode");
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

    private net.minecraft.entity.EntityLivingBase doJumpDamage(double X, double Y, double Z, double dist, double damage, int knock) {
        AxisAlignedBB bb = new AxisAlignedBB((double)(X - dist), (double)(Y - 10.0), (double)(Z - dist), (double)(X + dist), (double)(Y + 10.0), (double)(Z + dist));
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, bb);
//         Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (var4 == null || var4 == this || !var4.isEntityAlive() || MyUtils.isRoyalty((Entity)var4) || var4 instanceof Ghost || var4 instanceof GhostSkelly) continue;
            DamageSource var21 = null;
            var21 = DamageSource.setExplosionSource(null);
            var21.setExplosion();
            var4.attackEntityFrom(var21, (float)damage / 2.0f);
            var4.attackEntityFrom(DamageSource.FALL, (float)damage / 2.0f);
            this.getEntityWorld().playSound(null, var4.posX, var4.posY, var4.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.HOSTILE, 0.65f, 1.0f + (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.5f);
            if (knock == 0) continue;
            double ks = 2.75;
            double inair = 0.65;
            float f3 = (float)Math.atan2(var4.posZ - this.posZ, var4.posX - this.posX);
            var4.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return null;
    }
}


}