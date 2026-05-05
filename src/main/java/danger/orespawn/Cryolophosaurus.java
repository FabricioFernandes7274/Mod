package danger.orespawn;

import java.util.List;

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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Cryolophosaurus extends EntityMob {

    public Cryolophosaurus(World worldIn) {
        super(worldIn);
        this.setSize(0.75F, 0.75F);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 10;
        this.isImmuneToFire = false;
    }

    @Override
    protected void initEntityAI() {
        // Tarefas de IA modernizadas para o local correto
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
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawnMain.Cryolophosaurus_stats.attack);
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Cryolophosaurus_stats.health;
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.Cryolophosaurus_stats.defense;
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    // --- Áudio ---

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.world.rand.nextInt(6) == 0) {
            return new SoundEvent(new ResourceLocation("orespawn", "cryo_living"));
        }
        return null;
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
        return 0.75F;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }

    // --- Drops ---

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int i = this.world.rand.nextInt(10);
        if (i == 0) {
            this.dropItem(Items.CHICKEN, 1);
        } else if (i == 1) {
            this.dropItem(OreSpawnMain.UraniumNugget, 1);
        } else if (i == 2) {
            this.dropItem(OreSpawnMain.TitaniumNugget, 1);
        }
    }

    // --- Combate Customizado ---

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();
        
        // Esquece ocasionalmente o alvo e foca noutra coisa
        if (this.world.rand.nextInt(200) == 1) {
            this.setAttackTarget(null);
        }
        
        // Sistema manual de ataque
        if (this.world.rand.nextInt(5) == 1) {
            EntityLivingBase target = this.findSomethingToAttack();
            if (target != null) {
                this.getNavigator().tryMoveToEntityLiving(target, 1.25D);
                
                // Distância de ataque e chance aleatória
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
        
        // Criaturas que ele NÃO ataca (Tem respeito aos maiores predadores e ignora insetos chatos/fantasmas)
        if (target instanceof Alosaurus || target instanceof TRex || target instanceof Cryolophosaurus || 
            target instanceof Ghost || target instanceof GhostSkelly || target instanceof CaveFisher || 
            target instanceof GammaMetroid || target instanceof EntityButterfly || target instanceof Firefly || 
            target instanceof EntityMosquito || target instanceof RockBase) {
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
        
        // Substituído expand por grow
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(9.0D, 2.0D, 9.0D));
        
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity)) {
                return entity;
            }
        }
        return null;
    }

    // --- Regras de Spawn ---

    @Override
    public boolean getCanSpawnHere() {
        // super.getCanSpawnHere() já cuida do 'isValidLightLevel' na 1.12.2
        if (!super.getCanSpawnHere()) {
            return false;
        }
        // Aparece durante a noite OU nas profundezas
        return !this.world.isDaytime() || this.posY <= 50.0D;
    }
}