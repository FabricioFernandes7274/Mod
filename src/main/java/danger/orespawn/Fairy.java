/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Fairy
extends EntityAmbientCreature {
    private static final ResourceLocation texture0 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture.png");
    private static final ResourceLocation texture1 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture2.png");
    private static final ResourceLocation texture2 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture3.png");
    private static final ResourceLocation texture3 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture4.png");
    private static final ResourceLocation texture4 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture5.png");
    private static final ResourceLocation texture5 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture6.png");
    private static final ResourceLocation texture6 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture7.png");
    private static final ResourceLocation texture7 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture8.png");
    private static final ResourceLocation texture8 = new net.minecraft.util.ResourceLocation("orespawn", "fairytexture9.png");
    int my_blink = 20 + this.rand.nextInt(20);
    int blinker = 0;
    int myspace = 0;
    public int fairy_type = 0;
    private int force_sync = 10;
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;
    private String myowner = null;
    private GenericTargetSorter TargetSorter = null;

    public Fairy(World worldIn) {
        super(worldIn);
        this.setSize(0.4f, 0.8f);
        if (worldIn != null) {
            this.fairy_type = worldIn.rand.nextInt(9);
        }
        this.getNavigator().setCanSwim(true);
        this.renderDistanceWeight = 3.0;
        this.tasks.addTask(0, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0f));
        this.tasks.addTask(1, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.TargetSorter = new GenericTargetSorter((Entity)this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.1f);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
    }

    public ResourceLocation getTexture(Fairy a) {
        if (this.fairy_type == 8) {
            return texture8;
        }
        if (this.fairy_type == 7) {
            return texture7;
        }
        if (this.fairy_type == 6) {
            return texture6;
        }
        if (this.fairy_type == 5) {
            return texture5;
        }
        if (this.fairy_type == 4) {
            return texture4;
        }
        if (this.fairy_type == 3) {
            return texture3;
        }
        if (this.fairy_type == 2) {
            return texture2;
        }
        if (this.fairy_type == 1) {
            return texture1;
        }
        return texture0;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(20, (Object)this.fairy_type);
    }

    public void setOwner(net.minecraft.entity.EntityLivingBase e) {
        String s;
        net.minecraft.entity.player.EntityPlayer p = null;
        if (e != null && e instanceof net.minecraft.entity.player.EntityPlayer && (s = (p = (net.minecraft.entity.player.EntityPlayer)e).getDisplayName()) != null) {
            this.myowner = s;
        }
    }

    public float getBlink() {
        if (this.blinker < this.my_blink / 2) {
            return 240.0f;
        }
        return 0.0f;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), 2.0f);
        return var4;
    }

    public int getTotalArmorValue() {
        return 4;
    }

    protected float getSoundVolume() {
        return 0.25f;
    }

    protected float getSoundPitch() {
        return 1.7f;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return "orespawn:rat_hit";
    }

    protected String getDeathSound() {
        return "orespawn:big_splat";
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    public int mygetMaxHealth() {
        return 40;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock((Block)OreSpawnMain.CrystalTorch);
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.600000023841;
        ++this.blinker;
        if (this.blinker > this.my_blink) {
            this.blinker = 0;
        }
        --this.force_sync;
        if (this.force_sync < 0) {
            this.force_sync = 10;
            if (this.world.isRemote) {
                this.fairy_type = this.dataManager.get(20);
            } else {
                this.dataManager.set(20, (Object)this.fairy_type);
            }
        }
        long t = this.world.getWorldTime();
        if ((t %= 24000L) < 12000L) {
            return;
        }
        if (this.world.isRemote && this.world.rand.nextInt(5) == 0 && this.getBlink() > 1.0f) {
            this.world.spawnParticle("fireworksSpark", this.posX, this.posY - (double)0.15f, this.posZ, (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 8.0f), (double)(-this.world.rand.nextFloat() / 8.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 8.0f));
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.myowner == null) {
            this.myowner = "null";
        }
        par1NBTTagCompound.setString("MyOwner", this.myowner);
        par1NBTTagCompound.setInteger("FairyType", this.fairy_type);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.myowner = par1NBTTagCompound.getString("MyOwner");
        if (this.myowner != null && this.myowner.equals("null")) {
            this.myowner = null;
        }
        this.fairy_type = par1NBTTagCompound.getInteger("fairyType");
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
        return par1EntityLiving instanceof EntityMob;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(8.0, 8.0, 8.0));
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

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.25), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        net.minecraft.entity.player.EntityPlayer p;
        int keep_trying = 25;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.world.rand.nextInt(200) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 2.5f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                int zdir = this.world.rand.nextInt(8);
                int xdir = this.world.rand.nextInt(8);
                if (this.world.rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }
                if (this.world.rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + this.world.rand.nextInt(5) - 2, (int)this.posZ + zdir);
                bid = this.world.getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        } else if (this.world.rand.nextInt(12) == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            net.minecraft.entity.EntityLivingBase e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                if (this.getDistanceSq((Entity)e) < 6.0) {
                    this.attackEntityAsMob((Entity)e);
                }
            }
        } else if (this.myowner != null && (p = this.world.getPlayerEntityByName(this.myowner)) != null) {
            if (this.getDistanceSq((Entity)p) > 64.0) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)p.posX + this.world.rand.nextInt(3) - this.world.rand.nextInt(3), (int)(p.posY + 1.0), (int)p.posZ + this.world.rand.nextInt(3) - this.world.rand.nextInt(3));
            }
            if (this.getDistanceSq((Entity)p) > 256.0) {
                this.setPosition(p.posX + (double)this.world.rand.nextFloat() - (double)this.world.rand.nextFloat(), p.posY, p.posZ + (double)this.world.rand.nextFloat() - (double)this.world.rand.nextFloat());
            }
        }
        if (this.world.rand.nextInt(250) == 1) {
            this.heal(1.0f);
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.2 - this.motionX) * 0.1;
        this.motionY += (Math.signum(var3) * (double)0.7f - this.motionY) * 0.1;
        this.motionZ += (Math.signum(var5) * 0.2 - this.motionZ) * 0.1;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.2f;
        this.rotationYaw += var8 / 4.0f;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean getCanSpawnHere() {
        int sc = 0;
        for (int k = -1; k <= 1; ++k) {
            for (int j = -1; j <= 1; ++j) {
                Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY, (int)).getBlock()this.posZ + k);
                if (bid != Blocks.AIR) continue;
                ++sc;
            }
        }
        if (sc < 6) {
            return false;
        }
        return !(this.posY < 50.0);
    }

    public void initCreature() {
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return this.myowner == null;
    }
}

