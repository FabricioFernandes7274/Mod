package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class Dragonfly extends EntityAnimal {
    private BlockPos currentFlightTarget = null;

    public Dragonfly(World worldIn) {
        super(worldIn);
        this.setSize(1.5f, 0.5f);
        // Na 1.12.2, o navigator é configurado de forma diferente, mas animais voadores 
        // geralmente ignoram o pathfinding padrão em favor da lógica manual no updateAITasks.
        this.experienceValue = 5;
        this.isImmuneToFire = false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    @Override
    protected float getSoundVolume() {
        return 0.25f;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GENERIC_EXPLODE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Simula a levitação diminuindo a queda vertical
        this.motionY *= 0.6D;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.25D, this.posZ), 
                new Vec3d(pX, pY, pZ), false) == null;
    }

    @Override
    protected void updateAITasks() {
        int xdir, zdir;
        int keep_trying = 50;

        if (this.isDead) return;

        super.updateAITasks();

        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }

        // Lógica de novo alvo de voo aleatório
        if (this.world.rand.nextInt(300) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 2.1D) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.world.rand.nextInt(5) + 5;
                xdir = this.world.rand.nextInt(5) + 5;
                if (this.world.rand.nextInt(2) == 0) zdir = -zdir;
                if (this.world.rand.nextInt(2) == 0) xdir = -xdir;

                this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.world.rand.nextInt(5) - 2, (int)this.posZ + zdir);
                bid = this.world.getBlockState(this.currentFlightTarget).getBlock();
                
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        } 
        // Lógica de busca de presas
        else if (this.world.rand.nextInt(12) == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0D), (int)e.posZ);
                if (this.getDistanceSq(e) < 6.0D) {
                    this.attackEntityAsMob(e);
                }
            }
        }

        // Movimentação em direção ao alvo
        double var1 = (double)this.currentFlightTarget.getX() + 0.5D - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1D - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5D - this.posZ;

        this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.3D;
        this.motionY += (Math.signum(var3) * 0.7D - this.motionY) * 0.2D;
        this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.3D;

        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
        float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
        this.moveForward = 1.0F;
        this.rotationYaw += var8 / 4.0F;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        // Dragonfly não sofre dano de queda
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, BlockPos pos) {
        // Ignora processamento de queda
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D) return false;
        return this.world.isDaytime() && super.getCanSpawnHere();
    }

    private boolean isSuitableTarget(EntityLivingBase entity, boolean par2) {
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) return false;
        if (entity == null || entity == this || !entity.isEntityAlive()) return false;
        if (!this.getEntitySenses().canSee(entity)) return false;

        // Lista de presas originais do OreSpawn
        if (entity instanceof EntityAnt || entity instanceof EntityButterfly || 
            entity instanceof Cockateil || entity instanceof EntityMosquito || 
            entity instanceof Firefly) {
            return true;
        }

        return entity instanceof EntityHorse && OreSpawnMain.DragonflyHorseFriendly == 0;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) return null;

        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, 
                this.getEntityBoundingBox().grow(10.0D, 6.0D, 10.0D));
        
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity, false)) {
                return entity;
            }
        }
        return null;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int i = this.rand.nextInt(6);
        Item item = null;
        
        if (i == 0) item = Items.GOLD_NUGGET;
        else if (i == 1) item = OreSpawnMain.UraniumNugget;
        else if (i == 2) item = OreSpawnMain.TitaniumNugget;

        if (item != null) {
            this.dropItem(item, 1);
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }
}