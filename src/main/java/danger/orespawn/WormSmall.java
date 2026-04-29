/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class WormSmall
extends EntityMob {
    public int upcount = 50;
    public int downcount = 0;

    public WormSmall(World worldIn) {
        super(worldIn);
        this.setSize(0.25f, 1.0f);
        ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.experienceValue = 0;
        this.noClip = true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.1f);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.WormSmall_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
    }

    protected boolean canDespawn() {
        return false;
    }

    protected float getSoundVolume() {
        return 0.5f;
    }

    protected float getSoundPitch() {
        return 1.5f;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE; }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.WormSmall_stats.health;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.WormSmall_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        net.minecraft.entity.player.EntityPlayer target = null;
        super.onLivingUpdate();
        target = (net.minecraft.entity.player.EntityPlayer)this.getEntityWorld().findNearestEntityWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.getEntityBoundingBox().expand(8.0, 8.0, 8.0), (Entity)this);
        if (target != null || OreSpawnMain.PlayNicely != 0) {
            if (this.upcount > 0) {
                Block bid;
                --this.upcount;
                if (this.upcount == 0) {
                    this.downcount = 100 + this.getEntityWorld().rand.nextInt(150);
                }
                if (target != null) {
                    this.pointAtEntity((net.minecraft.entity.EntityLivingBase)target);
                }
                if ((bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX, (int)(this.posY + 0.25), (int)this.posZ)).getBlock() == Blocks.TALLGRASS) {
                    bid = Blocks.AIR;
                }
                if (bid != Blocks.AIR) {
                    if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.STONE) {
                        this.setDead();
                    }
                    this.motionY += (double)0.15f;
                    this.posY += (double)0.1f;
                }
            } else {
                if (this.downcount > 0) {
                    --this.downcount;
                } else {
                    this.upcount = 25 + this.getEntityWorld().rand.nextInt(50);
                }
                Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX, (int)this.posY + 2, (int)this.posZ)).getBlock(;
                if (bid == Blocks.TALLGRASS) {
                    bid = Blocks.AIR;
                }
                if (bid != Blocks.AIR) {
                    if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.STONE) {
                        this.setDead();
                    }
                    this.motionY += (double)0.2f;
                    this.posY += (double)0.05f;
                }
            }
        } else {
            this.upcount = this.getEntityWorld().rand.nextInt(50);
            this.downcount = 0;
            Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX, (int)this.posY + 2, (int)this.posZ)).getBlock(;
            if (bid == Blocks.TALLGRASS) {
                bid = Blocks.AIR;
            }
            if (bid != Blocks.AIR) {
                if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.STONE) {
                    this.setDead();
                }
                this.motionY += (double)0.1f;
                this.posY += (double)0.05f;
            }
        }
        this.motionY -= 0.01;
        this.motionX = 0.0;
        this.motionZ = 0.0;
        this.moveForward = 0.0f;
    }

    public void onUpdate() {
        if (this.isNoDespawnRequired()) {
            this.noClip = false;
        }
        super.onUpdate();
        this.motionY *= 0.75;
    }

    public void pointAtEntity(net.minecraft.entity.EntityLivingBase e) {
        float f2;
        double d1 = e.posX - this.posX;
        double d2 = e.posZ - this.posZ;
        float d = (float)Math.atan2(d2, d1);
        this.rotationYaw = this.rotationYawHead = (f2 = (float)((double)d * 180.0 / Math.PI) - 90.0f);
    }

    protected void updateAITasks() {
        int bid = 0;
        net.minecraft.entity.player.EntityPlayer target = null;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (OreSpawnMain.PlayNicely != 0) {
            return;
        }
        target = (net.minecraft.entity.player.EntityPlayer)this.getEntityWorld().findNearestEntityWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.getEntityBoundingBox().expand(1.5, 4.0, 1.5), (Entity)this);
        if (target != null && target.isCreative()) {
            target = null;
        }
        if (target != null) {
            this.pointAtEntity((net.minecraft.entity.EntityLivingBase)target);
            if (this.upcount > 0 && this.getEntityWorld().rand.nextInt(15) == 1 && !target.isCreative()) {
                ItemStack boots;
                super.attackEntityAsMob((Entity)target);
                if (this.getEntityWorld().rand.nextInt(6) == 1 && (boots = target.getEquipmentInSlot(1)) != null) {
                    target.setCurrentItemOrArmor(1, null);
                    bid = boots.getMaxDurability() - boots.getMetadata();
                    bid = bid > 20 ? (bid /= 20) : 1;
                    boots.damageItem(bid, (net.minecraft.entity.EntityLivingBase)this);
                    EntityItem var3 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(5) - (double)OreSpawnMain.OreSpawnRand.nextInt(5), this.posY + 3.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(5) - (double)OreSpawnMain.OreSpawnRand.nextInt(5), boots);
                    this.getEntityWorld().spawnEntity((Entity)var3);
                }
            }
        }
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
        return !this.getEntityWorld().isDaytime();
    }

    public void initCreature() {
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return ret;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        return ret;
    }

    protected Item getDropItem() {
        return null;
    }
}

