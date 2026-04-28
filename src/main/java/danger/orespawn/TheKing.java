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
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.AxisAlignedBB;

public class TheKing extends EntityMob {
    private int attack_level = 0;
    private int attdam = 0;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import danger.orespawn.BetterFireball;
import danger.orespawn.Boyfriend;
import danger.orespawn.GenericTargetSorter;
import danger.orespawn.Ghost;
import danger.orespawn.GhostSkelly;
import danger.orespawn.Girlfriend;
import danger.orespawn.Godzilla;
import danger.orespawn.GodzillaHead;
import danger.orespawn.IceBall;
import danger.orespawn.KingHead;
import danger.orespawn.Kraken;
import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.PitchBlack;
import danger.orespawn.PurplePower;
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
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.net.minecraft.util.text.TextComponentString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.net.minecraft.util.text.ITextComponent;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
    private int hurt_timer = 0;
    private int homex = 0;
    private int homez = 0;
    private int stream_count = 0;
    private int stream_count_l = 0;
    private int stream_count_i = 0;
    private int ticker = 0;
    private int player_hit_count = 0;
    private int backoff_timer = 0;
    private int guard_mode = 0;
    private volatile int head_found = 0;
    private int wing_sound = 0;
    private int large_unknown_detected = 0;
    private int isEnd = 0;
    private int endCounter = 0;

    public TheKing(World worldIn) {
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
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderDistanceWeight = 12.0;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.62f);
        this.attdam = OreSpawnMain.TheKing_stats.attack;
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.attdam);
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)0);
//         this.dataManager.register(21, (Object)OreSpawnMain.PlayNicely);
//         this.dataManager.register(22, (Object)this.isEnd);
    }

    public int getPlayNicely() {
        return 0 /* this.dataManager.get(21) */;
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

    public final int getAttacking() {
        return 0 /* this.dataManager.get(20) */;
    }

    public final void setAttacking(int par1) {
//         this.dataManager.set(20, (Object)((byte)par1));
    }

    protected float getSoundVolume() {
        return 1.35f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected String getLivingSound() {
        return "orespawn:king_living";
    }

    protected String getHurtSound() {
        return "orespawn:king_hit";
    }

    protected String getDeathSound() {
        return "orespawn:trex_death";
    }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.TheKing_stats.health;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock((Block)Blocks.YELLOW_FLOWER);
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(20) - (double)OreSpawnMain.OreSpawnRand.nextInt(20), this.posY + 12.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(20) - (double)OreSpawnMain.OreSpawnRand.nextInt(20), new ItemStack(index, par1, 0));
        this.world.spawnEntity((Entity)var3);
    }

    protected void dropFewItems(boolean par1, int par2) {
        int k;
        Item it = null;
        Block bl = null;
        TheKing.spawnCreature(this.world, "The Prince", this.posX, this.posY + 10.0, this.posZ);
        this.dropItemRand((Item)OreSpawnMain.RoyalBody, 1);
        this.dropItemRand((Item)OreSpawnMain.RoyalHelmet, 1);
        this.dropItemRand((Item)OreSpawnMain.RoyalLegs, 1);
        this.dropItemRand((Item)OreSpawnMain.RoyalBoots, 1);
        this.dropItemRand(OreSpawnMain.MyRoyal, 1);
        Iterator ilist = Item.REGISTRY.iterator();
        int icount = 0;
        while (ilist.hasNext()) {
            it = (Item)ilist.next();
            if (it == null) continue;
            ++icount;
        }
        int j = 0;
        while (j < 150) {
            ilist = Item.REGISTRY.iterator();
            for (k = 1 + this.world.rand.nextInt(icount); k > 0 && ilist.hasNext(); --k) {
                it = (Item)ilist.next();
            }
            if (it == null) continue;
            ++j;
            this.dropItemRand(it, 1);
        }
        Iterator blist = Block.REGISTRY.iterator();
        int bcount = 0;
        while (blist.hasNext()) {
            bl = (Block)blist.next();
            if (bl == null) continue;
            ++bcount;
        }
        j = 0;
        while (j < 150) {
            blist = Block.REGISTRY.iterator();
            for (k = 1 + this.world.rand.nextInt(bcount); k > 0 && blist.hasNext(); --k) {
                bl = (Block)blist.next();
            }
            if (bl == null) continue;
            ++j;
            this.dropItemRand(Item.getItemFromBlock((Block)bl), 1);
        }
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        ++this.wing_sound;
        if (this.wing_sound > 30) {
            if (!this.world.isRemote) {
                this.world.playSound(null, (Entity)this.posX, (Entity)this.posY, (Entity)this.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 1.75f, 0.75f);
            }
            this.wing_sound = 0;
        }
        this.noClip = true;
        this.motionY *= 0.6;
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() * 2 / 3)) {
            this.attdam = OreSpawnMain.TheKing_stats.attack * 2;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 2)) {
            this.attdam = OreSpawnMain.TheKing_stats.attack * 4;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 4)) {
            this.attdam = OreSpawnMain.TheKing_stats.attack * 8;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 8)) {
            this.attdam = OreSpawnMain.TheKing_stats.attack * 16;
        }
        if (this.world.isRemote) {
            float f = 7.0f;
            this.isEnd = 0 /* this.dataManager.get(22) */;
            if (this.isEnd != 0 && this.world.rand.nextInt(3) == 1) {
                for (int i = 0; i < 10; ++i) {
                    this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + 14.0, this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)), (this.world.rand.nextGaussian() - this.world.rand.nextGaussian()) / 4.0 + this.motionX * 6.0, (this.world.rand.nextGaussian() - this.world.rand.nextGaussian()) / 4.0, (this.world.rand.nextGaussian() - this.world.rand.nextGaussian()) / 4.0 + this.motionZ * 6.0);
                }
            }
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4;
        float s;
        if (!(par1Entity == null || !(par1Entity instanceof net.minecraft.entity.EntityLivingBase) || !((s = par1Entity.height * par1Entity.width) > 30.0f) || MyUtils.isRoyalty(par1Entity) || par1Entity instanceof Godzilla || par1Entity instanceof GodzillaHead || par1Entity instanceof PitchBlack || par1Entity instanceof Kraken)) {
            net.minecraft.entity.EntityLivingBase e = (net.minecraft.entity.EntityLivingBase)par1Entity;
            e.setHealth(e.getHealth() / 2.0f);
            e.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)this.attdam * 10.0f);
            this.large_unknown_detected = 1;
        }
        if (par1Entity != null && par1Entity instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon)par1Entity;
            DamageSource var21 = null;
            var21 = DamageSource.setExplosionSource(null);
            var21.setExplosion();
            if (this.world.rand.nextInt(6) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, var21, (float)this.attdam);
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, var21, (float)this.attdam);
            }
        }
        if (var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)this.attdam)) {
            double ks = 3.3;
            double inair = 0.25;
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            inair += (double)(this.world.rand.nextFloat() * 0.25f);
            if (par1Entity.isDead() || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                inair *= 1.5;
            }
            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return var4;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 8.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    private boolean tooFarFromHome() {
        float d1 = (float)(this.posX - (double)this.homex);
        float d2 = (float)(this.posZ - (double)this.homez);
        return (d1 = (float)Math.sqrt(d1 * d1 + d2 * d2)) > 120.0f;
    }

    private void msgToPlayers(String s) {
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.getEntityBoundingBox().expand(80.0, 64.0, 80.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.player.EntityPlayer var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.player.EntityPlayer)var3;
            var4.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString(s));
        }
    }

    private net.minecraft.entity.player.EntityPlayer findNearestPlayer() {
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.getEntityBoundingBox().expand(80.0, 64.0, 80.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.player.EntityPlayer var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            if (var3 instanceof net.minecraft.entity.player.EntityPlayer) {
                var4 = (net.minecraft.entity.player.EntityPlayer)var3;
            }
            if (var4 == null) continue;
            break;
        }
        return var4;
    }

    protected void updateAITasks() {
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
        net.minecraft.entity.player.EntityPlayer p = null;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
//         this.dataManager.set(22, (Object)this.isEnd);
        this.dataManager = new net.minecraft.util.math.BlockPos(21, (Object)OreSpawnMain.PlayNicely);
        if (this.isEnd == 1) {
            ++this.endCounter;
            this.noClip = true;
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.hurt_timer = 10;
            if (this.isDead()) {
                return;
            }
            p = this.findNearestPlayer();
            if (p != null) {
                float f2;
                this.faceEntity((Entity)p, 10.0f, 10.0f);
                p.motionX = 0.0;
                p.motionY = 0.0;
                p.motionZ = 0.0;
                double dd0 = this.posX - p.posX;
                double dd1 = this.posZ - p.posZ;
                p.rotationYaw = f2 = (float)(Math.atan2(dd1, dd0) * 180.0 / Math.PI) - 90.0f;
                p.setHealth(1.0f);
            }
            if (this.endCounter == 10) {
                this.msgToPlayers("The King: Enough of this charade. I am done. You have shown me what I wanted to know.");
                return;
            }
            if (this.endCounter == 80) {
                this.msgToPlayers("The King: That's right my little pet. It has all been a game. You never killed me. You can't.");
                return;
            }
            if (this.endCounter == 160) {
                this.msgToPlayers("The King: I am the one. The only. The many. I exist within both space and time. Everywhere and always.");
                return;
            }
            if (this.endCounter == 240) {
                this.msgToPlayers("The King: I used you to learn your ways, and I have reached my conclusion on your species.");
                return;
            }
            if (this.endCounter == 300) {
                this.msgToPlayers("The King: You have 10 seconds to run...");
                return;
            }
            if (this.endCounter == 320) {
                this.msgToPlayers("9.");
                return;
            }
            if (this.endCounter == 340) {
                this.msgToPlayers("8.");
                return;
            }
            if (this.endCounter == 360) {
                this.msgToPlayers("7.");
                return;
            }
            if (this.endCounter == 380) {
                this.msgToPlayers("6.");
                return;
            }
            if (this.endCounter == 400) {
                this.msgToPlayers("5.");
                return;
            }
            if (this.endCounter == 420) {
                this.msgToPlayers("4.");
                return;
            }
            if (this.endCounter == 440) {
                this.msgToPlayers("3.");
                return;
            }
            if (this.endCounter == 460) {
                this.msgToPlayers("2.");
                return;
            }
            if (this.endCounter == 480) {
                this.msgToPlayers("1.");
                return;
            }
            if (this.endCounter == 500) {
                this.msgToPlayers("The King: Prepare to die!");
                this.isEnd = 2;
                return;
            }
            return;
        }
        if (this.isEnd == 2) {
            this.hurt_timer = 10;
            this.player_hit_count = 0;
            this.stream_count = 10;
            this.stream_count_l = 10;
            this.stream_count_i = 10;
            attrand = 3;
            this.guard_mode = 0;
            this.large_unknown_detected = 1;
            if (this.backoff_timer > 0) {
                --this.backoff_timer;
            }
        }
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.homex == 0 && this.homez == 0 || this.guard_mode == 0) {
            this.homex = (int)this.posX;
            this.homez = (int)this.posZ;
        }
        ++this.ticker;
        if (this.ticker > 30000) {
            this.ticker = 0;
        }
        if (this.ticker % 80 == 0) {
            this.stream_count = 10;
        }
        if (this.ticker % 90 == 0) {
            this.stream_count_l = 5;
        }
        if (this.ticker % 70 == 0) {
            this.stream_count_i = 8;
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
        if (this.tooFarFromHome() || this.world.rand.nextInt(200) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 9.1f) {
            zdir = this.world.rand.nextInt(120);
            xdir = this.world.rand.nextInt(120);
            if (this.world.rand.nextInt(2) == 0) {
                zdir = -zdir;
            }
            if (this.world.rand.nextInt(2) == 0) {
                xdir = -xdir;
            }
            int dist = 0;
            for (int i = -5; i <= 5; i += 5) {
                block1: for (int j = -5; j <= 5; j += 5) {
                    int k;
                    Block bid = this.world.getBlockState(new BlockPos(this.homex + j, (int)this.posY, this.homez + i)).getBlock();
                    if (bid != Blocks.AIR) {
                        for (k = 1; k < 20; ++k) {
                            bid = this.world.getBlockState(new BlockPos(this.homex + j, (int)this.posY + k, this.homez + i)).getBlock();
                            ++dist;
                            if (bid == Blocks.AIR) continue block1;
                        }
                        continue;
                    }
                    for (k = 1; k < 20; ++k) {
                        bid = this.world.getBlockState(new BlockPos(this.homex + j, (int)this.posY - k, this.homez + i)).getBlock();
                        --dist;
                        if (bid != Blocks.AIR) continue block1;
                    }
                }
            }
            if ((int)(this.posY + (double)(dist = dist / 9 + 2)) > 230) {
                dist = 230 - (int)this.posY;
            }
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos(this.homex + xdir, (int)(this.posY + (double)dist), this.homez + zdir);
        } else if (this.world.rand.nextInt(attrand) == 0) {
            e = this.rt;
            if (OreSpawnMain.PlayNicely != 0) {
                e = null;
            }
            if (e != null && (e instanceof TheKing || e instanceof KingHead)) {
                this.rt = null;
                e = null;
            }
            if (e != null) {
                float d1 = (float)(e.posX - (double)this.homex);
                float d2 = (float)(e.posZ - (double)this.homez);
                d1 = (float)Math.sqrt(d1 * d1 + d2 * d2);
                if (e.isDead() || this.world.rand.nextInt(250) == 1 || d1 > 128.0f && this.guard_mode == 1) {
                    e = null;
                    this.rt = null;
                }
                if (e != null && !this.MyCanSee(e)) {
                    e = null;
                }
            }
            f = this.findSomethingToAttack();
            if (this.head_found == 0) {
                EntityLiving entityLiving = (EntityLiving)TheKing.spawnCreature(this.world, "KingHead", this.posX, this.posY + 20.0, this.posZ);
            }
            if (e == null) {
                e = f;
            }
            if (e != null) {
                this.setAttacking(1);
                if (this.backoff_timer == 0) {
                    int dist = (int)(e.posY + (double)(e.height / 2.0f) + 1.0);
                    if (dist > 230) {
                        dist = 230;
                    }
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, dist, (int)e.posZ);
                    if (this.world.rand.nextInt(70) == 1) {
                        this.backoff_timer = 80 + this.world.rand.nextInt(80);
                    }
                } else if (this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 9.1f) {
                    zdir = this.world.rand.nextInt(20) + 30;
                    xdir = this.world.rand.nextInt(20) + 30;
                    if (this.world.rand.nextInt(2) == 0) {
                        zdir = -zdir;
                    }
                    if (this.world.rand.nextInt(2) == 0) {
                        xdir = -xdir;
                    }
                    int dist = 0;
                    for (int i = -5; i <= 5; i += 5) {
                        block5: for (int j = -5; j <= 5; j += 5) {
                            int k;
                            Block bid = this.world.getBlockState(new BlockPos((int)e.posX + j, (int)this.posY, (int)e.posZ + i)).getBlock(;
                            if (bid != Blocks.AIR) {
                                for (k = 1; k < 20; ++k) {
                                    bid = this.world.getBlockState(new BlockPos((int)e.posX + j, (int)this.posY + k, (int)e.posZ + i)).getBlock(;
                                    ++dist;
                                    if (bid == Blocks.AIR) continue block5;
                                }
                                continue;
                            }
                            for (k = 1; k < 20; ++k) {
                                bid = this.world.getBlockState(new BlockPos((int)e.posX + j, (int)this.posY - k, (int)e.posZ + i)).getBlock(;
                                --dist;
                                if (bid != Blocks.AIR) continue block5;
                            }
                        }
                    }
                    if ((int)(this.posY + (double)(dist = dist / 9 + 2)) > 230) {
                        dist = 230 - (int)this.posY;
                    }
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX + xdir, (int)(this.posY + (double)dist), (int)e.posZ + zdir);
                }
                if (this.getDistanceSq((Entity)e) < 900.0) {
                    if (this.world.rand.nextInt(2) == 1) {
                        this.doJumpDamage(this.posX, this.posY, this.posZ, 15.0, OreSpawnMain.TheKing_stats.attack / 4, 0);
                    }
                    this.attackEntityAsMob((Entity)e);
                }
                double dx = this.posX + 20.0 * Math.sin(Math.toRadians(this.rotationYawHead));
                double dz = this.posZ - 20.0 * Math.cos(Math.toRadians(this.rotationYawHead));
                if (this.world.rand.nextInt(3) == 1) {
                    this.doJumpDamage(dx, this.posY + 10.0, dz, 15.0, OreSpawnMain.TheKing_stats.attack / 2, 1);
                }
                if (this.getHorizontalDistanceSqToEntity((Entity)e) > 900.0) {
                    which = this.world.rand.nextInt(3);
                    if (which == 0) {
                        if (this.stream_count > 0) {
                            this.setAttacking(1);
                            rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                            rhdir = Math.toRadians((this.rotationYawHead + 90.0f) % 360.0f);
                            rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                            if (rdd > pi) {
                                rdd -= pi * 2.0;
                            }
                            if ((rdd = Math.abs(rdd)) < 0.5) {
                                this.firecanon(e);
                            }
                        }
                    } else if (which == 1) {
                        if (this.stream_count_l > 0) {
                            this.setAttacking(1);
                            rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                            rhdir = Math.toRadians((this.rotationYawHead + 90.0f) % 360.0f);
                            rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                            if (rdd > pi) {
                                rdd -= pi * 2.0;
                            }
                            if ((rdd = Math.abs(rdd)) < 0.5) {
                                this.firecanonl(e);
                            }
                        }
                    } else if (this.stream_count_i > 0) {
                        this.setAttacking(1);
                        rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                        rhdir = Math.toRadians((this.rotationYawHead + 90.0f) % 360.0f);
                        rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                        if (rdd > pi) {
                            rdd -= pi * 2.0;
                        }
                        if ((rdd = Math.abs(rdd)) < 0.5) {
                            this.firecanoni(e);
                        }
                    }
                }
            } else {
                this.setAttacking(0);
                this.stream_count = 10;
                this.stream_count_l = 5;
                this.stream_count_i = 8;
            }
        }
        if (this.getAttacking() != 0 && this.isEnd == 2) {
            double xzoff = 10.0;
            double yoff = 14.0;
            Entity ppwr = TheKing.spawnCreature(this.world, "PurplePower", this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)));
            if (ppwr != null) {
                PurplePower pwr = (PurplePower)ppwr;
                pwr.motionX = this.motionX * 3.0;
                pwr.motionZ = this.motionZ * 3.0;
                pwr.setPurpleType(10);
            }
        }
        var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.7 - this.motionX) * 0.35;
        this.motionY += (Math.signum(var3) * 0.69999 - this.motionY) * 0.3;
        this.motionZ += (Math.signum(var5) * 0.7 - this.motionZ) * 0.35;
        var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 1.0f;
        this.rotationYaw += var8 / 8.0f;
        if (this.world.rand.nextInt(30) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(5.0f);
            if (this.large_unknown_detected != 0) {
                this.heal(200.0f);
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
            bf = new BetterFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, e.posX - cx, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - cz);
            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
            bf.setPosition(cx, this.posY + yoff, cz);
            bf.setReallyBig();
            this.world.playSoundAtEntity((Entity)this, "random.fuse", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity((Entity)bf);
            for (int i = 0; i < 6; ++i) {
                float r1 = 5.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                float r2 = 3.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                float r3 = 5.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                bf = new BetterFireball(this.world, (net.minecraft.entity.EntityLivingBase)this, e.posX - cx + (double)r1, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff) + (double)r2, e.posZ - cz + (double)r3);
                bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                bf.setPosition(cx, this.posY + yoff, cz);
                bf.setBig();
                if (this.world.rand.nextInt(2) == 1) {
                    bf.setSmall();
                }
                this.world.playSoundAtEntity((Entity)this, "random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity((Entity)bf);
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
            this.world.playSoundAtEntity((Entity)this, "random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            for (int i = 0; i < 3; ++i) {
                float r1 = 5.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                float r2 = 3.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                float r3 = 5.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                ThunderBolt lb = new ThunderBolt(this.world, cx, this.posY + yoff, cz);
                lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0f, 0.0f);
                var3 = e.posX - lb.posX;
                var5 = e.posY + 0.25 - lb.posY;
                var7 = e.posZ - lb.posZ;
                var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var7 * var7)) * 0.2f;
                lb.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 4.0f);
                lb.motionX *= 3.0;
                lb.motionY *= 3.0;
                lb.motionZ *= 3.0;
                this.world.spawnEntity((Entity)lb);
            }
            --this.stream_count_l;
        }
    }

    private void firecanoni(net.minecraft.entity.EntityLivingBase e) {
        double yoff = 14.0;
        double xzoff = 32.0;
        double var3 = 0.0;
        double var5 = 0.0;
        double var7 = 0.0;
        float var9 = 0.0f;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        if (this.stream_count_i > 0) {
            this.world.playSoundAtEntity((Entity)this, "random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            for (int i = 0; i < 5; ++i) {
                float r1 = 5.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                float r2 = 3.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                float r3 = 5.0f * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
                IceBall lb = new IceBall(this.world, cx, this.posY + yoff, cz);
                lb.setIceMaker(1);
                lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0f, 0.0f);
                var3 = e.posX - lb.posX;
                var5 = e.posY + 0.25 - lb.posY;
                var7 = e.posZ - lb.posZ;
                var9 = net.minecraft.util.math.MathHelper.sqrt_double((double)(var3 * var3 + var7 * var7)) * 0.2f;
                lb.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 4.0f);
                lb.motionX *= 3.0;
                lb.motionY *= 3.0;
                lb.motionZ *= 3.0;
                this.world.spawnEntity((Entity)lb);
            }
            --this.stream_count_i;
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
        Entity e = par1DamageSource.getEntity();
        if (e != null && e instanceof net.minecraft.entity.EntityLivingBase) {
            net.minecraft.entity.EntityLivingBase enl = (net.minecraft.entity.EntityLivingBase)e;
            float s = enl.height * enl.width;
            if (!(!(s > 30.0f) || MyUtils.isRoyalty((Entity)enl) || enl instanceof Godzilla || enl instanceof GodzillaHead || enl instanceof PitchBlack || enl instanceof Kraken)) {
                dm /= 10.0f;
                this.hurt_timer = 50;
                this.large_unknown_detected = 1;
            }
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
        if (this.large_unknown_detected != 0) {
            return 25;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() * 2 / 3)) {
            return OreSpawnMain.TheKing_stats.defense + 1;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 2)) {
            return OreSpawnMain.TheKing_stats.defense + 2;
        }
        if (this.player_hit_count < 10 && this.getHealth() < (float)(this.mygetMaxHealth() / 4)) {
            return OreSpawnMain.TheKing_stats.defense + 3;
        }
        return OreSpawnMain.TheKing_stats.defense;
    }

    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
    }

    public void initCreature() {
    }

    public boolean MyCanSee(net.minecraft.entity.EntityLivingBase e) {
        double xzoff = 22.0;
        int nblks = 20;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        float startx = (float)cx;
        float starty = (float)(this.posY + (double)(this.height * 7.0f / 8.0f));
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
            Block bid = this.world.getBlockState(new BlockPos((int)(startx += dx), (int)(starty += dy), (int)(startz += dz)).getBlock();
            if (bid == Blocks.FLOWING_WATER || bid == Blocks.WATER || bid == Blocks.LEAVES || bid == Blocks.VINE || bid == Blocks.AIR) continue;
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
        if (par1EntityLiving instanceof KingHead) {
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
        if (this.isEnd == 2) {
            if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
                net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
                return !p.isCreative();
            }
            if (par1EntityLiving instanceof Girlfriend) {
                return true;
            }
            if (par1EntityLiving instanceof Boyfriend) {
                return true;
            }
            if (par1EntityLiving instanceof EntityVillager) {
                return true;
            }
        }
        if (!this.MyCanSee(par1EntityLiving)) {
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
        if (OreSpawnMain.PlayNicely != 0) {
            this.head_found = 1;
            return null;
        }
        if (this.isEnd == 2) {
            List var5p = this.world.getEntitiesWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.getEntityBoundingBox().expand(80.0, 64.0, 80.0));
            Collections.sort(var5p, this.TargetSorter);
            Iterator var2p = var5p.iterator();
            Entity var3p = null;
            net.minecraft.entity.EntityLivingBase var4p = null;
            Object retp = null;
            this.head_found = 1;
            while (var2p.hasNext()) {
                var3p = (Entity)var2p.next();
                var4p = (net.minecraft.entity.EntityLivingBase)var3p;
                if (!this.isSuitableTarget(var4p, false)) continue;
                return var4p;
            }
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(80.0, 64.0, 80.0));
        Collections.sort(var5, this.TargetSorter);
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

    public void setFree() {
        this.isEnd = 1;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("KingHomeX", this.homex);
        par1NBTTagCompound.setInteger("KingHomeZ", this.homez);
        par1NBTTagCompound.setInteger("GuardMode", this.guard_mode);
        par1NBTTagCompound.setInteger("PlayerHits", this.player_hit_count);
        par1NBTTagCompound.setInteger("IsEnd", this.isEnd);
        par1NBTTagCompound.setInteger("EndCounter", this.endCounter);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.homex = par1NBTTagCompound.getInteger("KingHomeX");
        this.homez = par1NBTTagCompound.getInteger("KingHomeZ");
        this.guard_mode = par1NBTTagCompound.getInteger("GuardMode");
        this.player_hit_count = par1NBTTagCompound.getInteger("PlayerHits");
        this.isEnd = par1NBTTagCompound.getInteger("IsEnd");
        this.endCounter = par1NBTTagCompound.getInteger("EndCounter");
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
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, bb);
        Collections.sort(var5, this.TargetSorter);
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
            this.world.playSoundAtEntity((Entity)var4, "random.explode", 0.65f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5f);
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