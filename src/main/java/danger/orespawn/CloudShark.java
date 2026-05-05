package danger.orespawn;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class CloudShark extends EntityMob {
    
    private BlockPos currentFlightTarget = null;

    public CloudShark(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 0.75F);
        this.experienceValue = 5;
        this.isImmuneToFire = false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawnMain.CloudShark_stats.attack);
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), f);
    }

    @Override
    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return !this.world.isDaytime();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.CloudShark_stats.health;
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.CloudShark_stats.defense;
    }

    @Override
    public boolean getCanSpawnHere() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    // --- Sistema de Voo e Movimentação ---

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6D; // Abranda a gravidade para flutuar
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(
            new Vec3d(this.posX, this.posY + 0.75D, this.posZ), 
            new Vec3d(pX, pY, pZ), 
            false
        ) == null;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();
        
        int keep_trying = 50;
        int updown = 0;
        
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos(this);
        }
        
        // Mantém o tubarão entre as camadas 120 e 140
        if (this.posY < 120.0D) updown = 2;
        if (this.posY > 140.0D) updown = -2;
        
        if (this.world.rand.nextInt(300) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 4.0D) {
            while (keep_trying > 0) {
                int xdir = this.world.rand.nextInt(10) + 8;
                int zdir = this.world.rand.nextInt(10) + 8;
                
                if (this.world.rand.nextBoolean()) xdir = -xdir;
                if (this.world.rand.nextBoolean()) zdir = -zdir;
                
                int ydir = this.world.rand.nextInt(5) - 2 + updown;
                
                BlockPos target = new BlockPos((int) this.posX + xdir, (int) this.posY + ydir, (int) this.posZ + zdir);
                
                if (this.world.isAirBlock(target) && this.canSeeTarget(target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D)) {
                    this.currentFlightTarget = target;
                    break;
                }
                keep_trying--;
            }
        }
        
        // Lógica de Caça
        if (this.world.rand.nextInt(9) == 2) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.currentFlightTarget = new BlockPos(e);
                if (this.getDistanceSq(e) < 9.0D) {
                    this.attackEntityAsMob(e);
                }
            }
        }
        
        // Voo fluído na direção do alvo
        if (this.currentFlightTarget != null) {
            double dx = (double) this.currentFlightTarget.getX() + 0.5D - this.posX;
            double dy = (double) this.currentFlightTarget.getY() + 0.1D - this.posY;
            double dz = (double) this.currentFlightTarget.getZ() + 0.5D - this.posZ;
            
            this.motionX += (Math.signum(dx) * 0.5D - this.motionX) * 0.3D;
            this.motionY += (Math.signum(dy) * 0.7D - this.motionY) * 0.2D;
            this.motionZ += (Math.signum(dz) * 0.5D - this.motionZ) * 0.3D;
            
            float targetYaw = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
            float yawDiff = MathHelper.wrapDegrees(targetYaw - this.rotationYaw);
            
            this.moveForward = 1.0F;
            this.rotationYaw += yawDiff / 4.0F;
        }
    }

    // --- Lógica de Combate ---

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = super.attackEntityFrom(source, amount);
        Entity e = source.getTrueSource();
        if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget = new BlockPos(e); // Foca-se em quem o atacou
        }
        return ret;
    }

    private boolean isSuitableTarget(EntityLivingBase target) {
        if (target == null || target == this || !target.isEntityAlive()) return false;
        if (!this.getEntitySenses().canSee(target)) return false;
        
        // O Cloud Shark ignora estas criaturas:
        if (target instanceof RockBase || target instanceof EntityAnt || target instanceof CliffRacer) {
            return false;
        }
        
        // O Cloud Shark foca as suas atenções nestas presas:
        if (target instanceof EntityButterfly || target instanceof Cockateil || target instanceof EntityMosquito || target instanceof Firefly || target instanceof GoldFish) {
            return true;
        }
        
        if (target instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) target;
            if (!p.isCreative()) {
                return true;
            }
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        // Na 1.12.2 o antigo "expand" para alargar caixas bounding 3D chama-se "grow"
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(12.0D, 10.0D, 12.0D));
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity)) {
                return entity;
            }
        }
        return null;
    }

    // --- Imunidades ---

    @Override
    protected void fall(float distance, float damageMultiplier) { }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) { }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    // --- Sons e Drops ---

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
    protected float getSoundVolume() {
        return 0.25F;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int chance = this.world.rand.nextInt(3);
        if (chance == 0) {
            this.dropItem(Items.PAPER, 1);
        } else if (chance == 1) {
            this.dropItem(Items.STRING, 1);
        } else if (chance == 2) {
            this.dropItem(Items.BONE, 1);
        }
    }
}