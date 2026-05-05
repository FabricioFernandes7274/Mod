package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class EnderReaper extends EntityMob {

    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier ATTACKING_SPEED_BOOST = (new AttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 6.2D, 0)).setSaved(false);
    
    private static final DataParameter<Boolean> SCREAMING = EntityDataManager.createKey(EnderReaper.class, DataSerializers.BOOLEAN);

    private int teleportDelay;
    private int stareTimer;
    private EntityLivingBase lastEntityToAttack;

    public EnderReaper(World worldIn) {
        super(worldIn);
        this.setSize(0.7f, 2.9f); // Ligeiramente mais largo que o Knight
        this.stepHeight = 1.0f;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        // Stats vindas do OreSpawnMain
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D); 
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.37D); // Mais rápido que o Knight
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(81.0D); // Alcance superior
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SCREAMING, Boolean.valueOf(false));
    }

    @Override
    public void onLivingUpdate() {
        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.PORTAL, 
                    this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, 
                    this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, 
                    this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 
                    (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }

        if (this.isWet() || this.isBurning()) {
            this.setScreaming(false);
            this.teleportRandomly();
        }

        if (this.getAttackTarget() != this.lastEntityToAttack) {
            IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
            if (this.getAttackTarget() != null) {
                iattributeinstance.applyModifier(ATTACKING_SPEED_BOOST);
            }
        }
        this.lastEntityToAttack = this.getAttackTarget();

        if (!this.world.isRemote && this.isEntityAlive()) {
            EntityPlayer player = this.world.getClosestVulnerablePlayerToEntity(this, 81.0D);
            if (player != null && this.shouldAttackPlayer(player)) {
                if (this.stareTimer == 0) {
                    this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERMEN_STARE, SoundCategory.HOSTILE, 1.0F, 1.0F);
                }
                if (this.stareTimer++ == 5) {
                    this.stareTimer = 0;
                    this.setScreaming(true);
                    this.setAttackTarget(player);
                }
            }

            if (this.getAttackTarget() != null) {
                double distSq = this.getDistanceSq(this.getAttackTarget());
                if (distSq < 16.0D) {
                    this.teleportRandomly();
                } else if (distSq > 256.0D && this.teleportDelay++ >= 30) {
                    this.teleportToEntity(this.getAttackTarget());
                    this.teleportDelay = 0;
                }
            }
        }
        super.onLivingUpdate();
    }

    private boolean shouldAttackPlayer(EntityPlayer player) {
        ItemStack itemstack = player.inventory.armorInventory.get(3);
        if (!itemstack.isEmpty() && itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN)) {
            return false;
        } else {
            Vec3d vec3d = player.getLook(1.0F).normalize();
            Vec3d vec3d1 = new Vec3d(this.posX - player.posX, this.getEntityBoundingBox().minY + (double)this.getEyeHeight() - (player.posY + (double)player.getEyeHeight()), this.posZ - player.posZ);
            double d0 = vec3d1.lengthVector();
            vec3d1 = vec3d1.normalize();
            double d1 = vec3d.dotProduct(vec3d1);
            return d1 > 1.0D - 0.025D / d0 ? player.canEntityBeSeen(this) : false;
        }
    }

    protected boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.attemptTeleport(d0, d1, d2);
    }

    protected boolean teleportToEntity(Entity target) {
        Vec3d vec3d = new Vec3d(this.posX - target.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - target.posY + (double)target.getEyeHeight(), this.posZ - target.posZ);
        vec3d = vec3d.normalize();
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.y * 16.0D;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
        return this.attemptTeleport(d1, d2, d3);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source instanceof EntityDamageSourceIndirect) {
            for (int i = 0; i < 64; ++i) {
                if (this.teleportRandomly()) return true;
            }
            return false;
        }
        this.setScreaming(true);
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean getCanSpawnHere() {
        // Checagem de proximidade (Ender Reaper não gosta de vizinhos)
        EnderReaper nearby = this.world.findNearestEntityWithinAABB(EnderReaper.class, this.getEntityBoundingBox().grow(16.0, 8.0, 16.0), this);
        if (nearby != null) return false;

        if (this.posY < 30.0 || this.world.isDaytime() || !this.isValidLightLevel()) return false;
        
        return super.getCanSpawnHere();
    }

    @Override
    protected Item getDropItem() { return Items.ENDER_EYE; }

    @Override
    public int getTotalArmorValue() { return 12; } // Valor base de armadura

    public boolean isScreaming() { return this.dataManager.get(SCREAMING); }
    public void setScreaming(boolean screaming) { this.dataManager.set(SCREAMING, screaming); }

    @Override
    protected net.minecraft.util.SoundEvent getAmbientSound() { return SoundEvents.ENTITY_ENDERMEN_AMBIENT; }
    @Override
    protected net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_ENDERMEN_HURT; }
    @Override
    protected net.minecraft.util.SoundEvent getDeathSound() { return SoundEvents.ENTITY_ENDERMEN_DEATH; }
}