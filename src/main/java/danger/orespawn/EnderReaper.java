/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.AttributeModifier
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EntityDamageSourceIndirect
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnderReaper
extends EntityMob {
    private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier attackingSpeedBoostModifier = new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", (double)6.2f, 0).setSaved(false);
    private int teleportDelay;
    private int stareTimer;
    private Entity lastEntityToAttack;

    public EnderReaper(World worldIn) {
        super(worldIn);
        this.setSize(0.7f, 2.9f);
        this.stepHeight = 1.0f;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)OreSpawnMain.EnderReaper_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.37);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.EnderReaper_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(18, (Object)new Byte(0));
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    protected Entity findPlayerToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        net.minecraft.entity.player.EntityPlayer entityplayer = this.getEntityWorld().getClosestVulnerablePlayerToEntity((Entity)this, 81.0);
        if (entityplayer != null) {
            if (this.shouldAttackPlayer(entityplayer)) {
                if (this.stareTimer == 0) {
                    this.getEntityWorld().playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.0f);
                }
                if (this.stareTimer++ == 5) {
                    this.stareTimer = 0;
                }
                this.setScreaming(true);
                return entityplayer;
            }
            this.stareTimer = 0;
            this.setScreaming(false);
        }
        return null;
    }

    private boolean shouldAttackPlayer(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        ItemStack itemstack = par1EntityPlayer.inventory.armorInventory[3];
        if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock((Block)Blocks.PUMPKIN)) {
            return false;
        }
        Vec3d vec3 = par1EntityPlayer.getLook(1.0f).normalize();
        Vec3d vec31 = new Vec3d((double)(this.posX - par1EntityPlayer.posX), (double)(this.getEntityBoundingBox().minY + (double)(this.height / 2.0f) - (par1EntityPlayer.posY + (double)par1EntityPlayer.getEyeHeight())), (double)(this.posZ - par1EntityPlayer.posZ));
        double d0 = vec31.lengthVector();
        double d1 = vec3.dotProduct(vec31 = vec31.normalize());
        return d1 > 1.0 - 0.025 / d0 ? par1EntityPlayer.canEntityBeSeen((Entity)this) : false;
    }

    public void onLivingUpdate() {
        float f;
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0f);
        }
        if (this.lastEntityToAttack != this.getAttackTarget()) {
            IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            attributeinstance.removeModifier(attackingSpeedBoostModifier);
            if (this.getAttackTarget() != null) {
                attributeinstance.applyModifier(attackingSpeedBoostModifier);
            }
        }
        this.lastEntityToAttack = this.getAttackTarget();
        for (int i = 0; i < 2; ++i) {
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25, this.posZ + (this.rand.nextDouble() - 0.5) * (double)this.width, (this.rand.nextDouble() - 0.5) * 2.0, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0);
        }
        if (this.getEntityWorld().isDaytime() && !this.getEntityWorld().isRemote && (f = this.getBrightness(1.0f)) > 0.5f && this.getEntityWorld().canBlockSeeTheSky(net.minecraft.util.math.MathHelper.floor_double((double)this.posX), net.minecraft.util.math.MathHelper.floor_double((double)this.posY), net.minecraft.util.math.MathHelper.floor_double((double)this.posZ)) && this.rand.nextFloat() * 30.0f < (f - 0.4f) * 2.0f) {
            this.getAttackTarget() = null;
            this.setScreaming(false);
            this.teleportRandomly();
        }
        if (this.isWet() || this.isBurning()) {
            this.setScreaming(false);
            this.teleportRandomly();
        }
        this.isJumping = false;
        if (this.getAttackTarget() != null) {
            this.faceEntity(this.getAttackTarget(), 100.0f, 100.0f);
        }
        if (!this.getEntityWorld().isRemote && this.isEntityAlive()) {
            if (this.getAttackTarget() != null) {
                if (this.getAttackTarget() instanceof net.minecraft.entity.player.EntityPlayer && this.shouldAttackPlayer((net.minecraft.entity.player.EntityPlayer)this.getAttackTarget())) {
                    if (this.getAttackTarget().getDistanceSq((Entity)this) < 16.0) {
                        this.teleportRandomly();
                    }
                    this.teleportDelay = 0;
                } else if (this.getAttackTarget().getDistanceSq((Entity)this) > 256.0 && this.teleportDelay++ >= 30 && this.teleportToEntity(this.getAttackTarget())) {
                    this.teleportDelay = 0;
                }
            } else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }
        super.onLivingUpdate();
    }

    protected boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(d0, d1, d2);
    }

    protected boolean teleportToEntity(Entity par1Entity) {
        Vec3d vec3 = new Vec3d((double)(this.posX - par1Entity.posX), (double)(this.getEntityBoundingBox().minY + (double)(this.height / 2.0f) - par1Entity.posY + (double)par1Entity.getEyeHeight()), (double)(this.posZ - par1Entity.posZ));
        vec3 = vec3.normalize();
        double d0 = 16.0;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - vec3.x * d0;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3.y * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - vec3.z * d0;
        return this.teleportTo(d1, d2, d3);
    }

    protected boolean teleportTo(double par1, double par3, double par5) {
        int k;
        int j;
        double d3 = this.posX;
        double d4 = this.posY;
        double d5 = this.posZ;
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        boolean flag = false;
        int i = net.minecraft.util.math.MathHelper.floor_double((double)this.posX);
        if (this.getEntityWorld().blockExists(i, j = net.minecraft.util.math.MathHelper.floor_double((double)this.posY), k = net.minecraft.util.math.MathHelper.floor_double((double)this.posZ))) {
            boolean flag1 = false;
            while (!flag1 && j > 0) {
                Block l = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(i, j - 1, k)).getBlock();
                if (l != Blocks.AIR && l.getMaterial().blocksMovement()) {
                    flag1 = true;
                    continue;
                }
                this.posY -= 1.0;
                --j;
            }
            if (flag1) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.getEntityWorld().getCollidingBoundingBoxes((Entity)this, this.getEntityBoundingBox()).isEmpty() && !this.getEntityWorld().isAnyLiquid(this.getEntityBoundingBox())) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            this.setPosition(d3, d4, d5);
            return false;
        }
        int short1 = 128;
        for (int lx = 0; lx < short1; ++lx) {
            double d6 = (double)lx / ((double)short1 - 1.0);
            float f = (this.rand.nextFloat() - 0.5f) * 0.2f;
            float f1 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            float f2 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5) * (double)this.width * 2.0;
            double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * (double)this.height;
            double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5) * (double)this.width * 2.0;
            this.getEntityWorld().spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, d7, d8, d9, (double)f, (double)f1, (double)f2);
        }
        this.getEntityWorld().playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0f, 1.0f);
        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("mob.endermen.portal")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.0f));
        return true;
    }

    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    protected String getHurtSound() {
        return "mob.endermen.hit";
    }

    protected String getDeathSound() {
        return "mob.endermen.death";
    }

    protected Item getDropItem() {
        return Items.ENDER_EYE;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.EnderReaper_stats.defense;
    }

    protected void dropFewItems(boolean par1, int par2) {
        Item j = this.getDropItem();
        if (j != null) {
            int k = this.rand.nextInt(2 + par2);
            for (int l = 0; l < k; ++l) {
                this.dropItem(j, 1);
            }
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setScreaming(true);
        if (par1DamageSource instanceof EntityDamageSourceIndirect) {
            for (int i = 0; i < 16; ++i) {
                if (!this.teleportRandomly()) continue;
                return true;
            }
            return super.attackEntityFrom(par1DamageSource, par2);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    public boolean getCanSpawnHere() {
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.getEntityWorld().getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Ender Reaper")) continue;
                    return true;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.getEntityWorld().isDaytime()) {
            return false;
        }
        if (this.posY < 30.0) {
            return false;
        }
        EnderReaper target = null;
        target = (EnderReaper)this.getEntityWorld().findNearestEntityWithinAABB(EnderReaper.class, this.getEntityBoundingBox().expand(16.0, 8.0, 16.0), (Entity)this);
        return target == null;
    }

    public boolean isScreaming() {
        return 0 /* this.dataManager.get(18) */ > 0;
    }

    public void setScreaming(boolean par1) {
//         this.dataManager.set(18, (Object)((byte)(par1 ? 1 : 0)));
    }
}

