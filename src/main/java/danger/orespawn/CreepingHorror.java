package danger.orespawn;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class CreepingHorror extends EntityMob {

    public CreepingHorror(World worldIn) {
        super(worldIn);
        this.setSize(0.75F, 0.5F);
        this.experienceValue = 5;
        this.isImmuneToFire = false;
    }

    @Override
    protected void initEntityAI() {
        // Na 1.12.2, a IA deve ser registada aqui
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.35D));
        this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(3, new MyEntityAIWanderALot(this, 10, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawnMain.CreepingHorror_stats.attack);
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.CreepingHorror_stats.health;
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.CreepingHorror_stats.defense;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        
        // Garante que não ultrapassa os limites de velocidade impostos por buffs
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        
        if (this.isNoDespawnRequired()) {
            return;
        }
        
        // Lógica de Despawn Diurno (Morre misteriosamente durante o dia)
        long t = this.world.getWorldTime() % 24000L;
        if (t > 11000L) {
            return; // Está de noite, continua vivo
        }
        
        // Durante o dia tem 1 em 500 de probabilidade por tick de desaparecer
        if (this.world.rand.nextInt(500) == 1) {
            this.setDead();
        }
    }

    // --- Áudio ---

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
        return 0.65F;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }

    // --- Drops ---

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int i = this.world.rand.nextInt(3);
        if (i == 0) {
            this.dropItem(Items.ROTTEN_FLESH, 1);
        } else if (i == 1) {
            this.dropItem(Items.BONE, 1);
        } else {
            this.dropItem(Items.STRING, 1);
        }
    }

    // --- IA de Combate Customizada ---

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();
        
        // Ocasionalmente esquece o alvo que o atacou para focar num novo
        if (this.world.rand.nextInt(200) == 1) {
            this.setAttackTarget(null);
        }
        
        if (this.world.rand.nextInt(5) == 1) {
            EntityLivingBase target = this.findSomethingToAttack();
            if (target != null) {
                this.getNavigator().tryMoveToEntityLiving(target, 1.25D);
                
                // Se estiver a 5 quarteirões ao quadrado de distância e tiver sorte, ataca!
                if (this.getDistanceSq(target) < 5.0D && (this.world.rand.nextInt(12) == 0 || this.world.rand.nextInt(14) == 1)) {
                    this.attackEntityAsMob(target);
                }
            }
        }
    }

    private boolean isSuitableTarget(EntityLivingBase target) {
        if (target == null || target == this || !target.isEntityAlive()) {
            return false;
        }
        if (!this.getEntitySenses().canSee(target)) {
            return false;
        }
        
        // Criaturas amigas/imunes a ele
        if (target instanceof CreepingHorror || target instanceof RockBase || 
            target instanceof EnderReaper || target instanceof LeafMonster || 
            target instanceof Dragon || target instanceof TerribleTerror || 
            target instanceof LurkingTerror || target instanceof PitchBlack || 
            target instanceof Firefly || target instanceof Island || target instanceof IslandToo) {
            return false;
        }
        
        if (target instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) target;
            if (p.isCreative()) {
                return false;
            }
        }
        return true;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        
        // "grow" substitui o "expand" antigo
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0D, 4.0D, 16.0D));
        
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity)) {
                return entity; // Retorna o primeiro que encontrar
            }
        }
        return null;
    }

    // --- Regras de Spawn ---

    @Override
    public boolean getCanSpawnHere() {
        // Verifica as lógicas standard dos monstros (Luz, Dificuldade Pacífica, Bloco sólido)
        if (!super.getCanSpawnHere()) {
            return false;
        }
        if (this.world.isDaytime()) {
            return false;
        }
        // Spawna na Danger Dimension (ID 6) em qualquer altura, ou no Overworld desde que esteja bem no fundo (Y < 15)
        return this.world.provider.getDimension() == OreSpawnMain.DimensionID6 || this.posY <= 15.0D;
    }

    @Override
    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return this.world.isDaytime();
    }
}