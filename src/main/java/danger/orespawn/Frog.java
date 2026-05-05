package danger.orespawn;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class Frog extends EntityMob {

    // Sincroniza o estado de "cantando" para animações (inflar o papo)
    private static final DataParameter<Integer> SINGING = EntityDataManager.createKey(Frog.class, DataSerializers.VARINT);

    private int singing_timer = 0;
    private int jumpcount = 0;

    public Frog(World worldIn) {
        super(worldIn);
        this.setSize(0.75F, 0.75F);
        this.experienceValue = 5;
        
        // Sapos obviamente não têm medo de água
        if (this.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) {
            ((net.minecraft.pathfinding.PathNavigateGround) this.getNavigator()).setCanSwim(true);
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        // Define o dano (3) direto aqui, diferente do original que colocava 0
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D); 
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SINGING, 0);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    // Pulo customizado do Sapo
    private void jumpAround() {
        this.motionY += 0.75D + Math.abs(this.rand.nextFloat() * 0.55D);
        this.posY += 0.35D;
        float f = 0.7F + Math.abs(this.rand.nextFloat() * 0.75F);
        float d = (float) Math.toRadians(this.rotationYaw);
        
        // Matemática de movimentação direcional da 1.12.2
        this.motionX -= (double) (f * MathHelper.sin(d));
        this.motionZ += (double) (f * MathHelper.cos(d));
        this.isAirBorne = true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {
            // Gerenciador do canto do sapo
            if (this.singing_timer > 0) {
                this.singing_timer--;
                if (this.singing_timer <= 0) {
                    this.setSinging(0);
                }
            }

            // Gerenciador de pulos aleatórios (ocioso)
            if (this.jumpcount > 0) {
                this.jumpcount--;
            }
            if (this.jumpcount == 0 && this.rand.nextInt(70) == 1) {
                this.jumpAround();
                this.jumpcount = 50;
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        // O EASTER EGG DO PRÍNCIPE/PRINCESA SAPO
        if (player.isSneaking() && stack.isEmpty()) {
            if (!this.world.isRemote) {
                this.setDead();
                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0F, this.world.rand.nextFloat() * 0.2F + 0.9F);
                
                // 50% de chance para Boyfriend, 50% para Girlfriend
                if (this.rand.nextInt(2) == 0) {
                    Boyfriend bf = new Boyfriend(this.world);
                    bf.setLocationAndAngles(this.posX, this.posY + 0.01D, this.posZ, this.rotationYaw, 0.0F);
                    bf.setPrince(1 + this.world.rand.nextInt(2)); // Transforma em Príncipe
                    this.world.spawnEntity(bf);
                } else {
                    Girlfriend gf = new Girlfriend(this.world);
                    gf.setLocationAndAngles(this.posX, this.posY + 0.01D, this.posZ, this.rotationYaw, 0.0F);
                    gf.setPrincess(1 + this.world.rand.nextInt(2)); // Transforma em Princesa
                    this.world.spawnEntity(gf);
                }
            } else {
                // Efeitos visuais da transformação rodam no cliente
                for (int i = 0; i < 16; ++i) {
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat(), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat(), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
                    this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + this.rand.nextFloat(), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
                }
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();

        // Inteligência Artificial de Caçar Insetos
        if (this.rand.nextInt(12) == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && OreSpawnMain.PlayNicely == 0) {
            EntityLivingBase bug = this.findBugToEat();
            if (bug != null) {
                this.getNavigator().tryMoveToEntityLiving(bug, 1.25D);
                if (this.getDistanceSq(bug) < 6.0D) {
                    // Causa dano no inseto
                    if (bug.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F)) {
                        if (bug.isDead) this.heal(1.0F); // Recupera vida ao comer o inseto
                    }
                }
            }
        }
    }

    private EntityLivingBase findBugToEat() {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8.0D, 3.0D, 8.0D));
        for (EntityLivingBase entity : list) {
            if (entity.isEntityAlive() && this.getEntitySenses().canSee(entity)) {
                // Checa se a classe do alvo faz parte do cardápio do sapo
                if (entity instanceof EntityAnt || 
                    entity instanceof EntityButterfly || 
                    entity instanceof EntityMosquito || 
                    entity instanceof Firefly || 
                    // Se você não tiver essas duas classes abaixo criadas ainda, você pode comentá-las temporariamente
                    entity instanceof Cricket || 
                    entity instanceof WormSmall) {
                    return entity;
                }
            }
        }
        return null;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean hurt = super.attackEntityFrom(source, amount);
        // Se tomar dano, ele pula assustado
        if (hurt && !this.world.isRemote && this.jumpcount <= 0) {
            this.jumpAround();
            this.jumpcount = 25;
        }
        return hurt;
    }

    // Limitador de Spawn
    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D) return false;
        if (!this.world.isDaytime()) return false;
        
        // Spawn reduzido na dimensão dos monstros (ID 5)
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID5 && this.rand.nextInt(20) != 1) {
            return false;
        }

        List<Frog> buddies = this.world.getEntitiesWithinAABB(Frog.class, this.getEntityBoundingBox().grow(20.0D, 8.0D, 20.0D));
        return buddies.size() <= 5 && super.getCanSpawnHere();
    }

    // Gets e Sets Sincronizados
    public int getSinging() { return this.dataManager.get(SINGING); }
    public void setSinging(int value) { this.dataManager.set(SINGING, value); }

    // Drops
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        // Sapo dropa 4 Slimeballs (referência clássica do mod)
        for (int i = 0; i < 4; ++i) {
            this.dropItem(Items.SLIME_BALL, 1);
        }
    }

    // Comportamentos Padrão
    @Override protected boolean canTriggerWalking() { return true; }
    @Override protected void fall(float distance, float damageMultiplier) {}
    @Override protected void updateFallState(double y, boolean onGroundIn) {}
    @Override protected boolean canDespawn() { return !this.isNoDespawnRequired(); }

    // Sons
    @Override protected float getSoundVolume() { return 0.7F; }
    @Override protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }

    @Override
    protected SoundEvent getAmbientSound() {
        // Lógica original de cantar
        if (!this.world.isRemote && this.rand.nextInt(2) != 0) {
            this.singing_timer = 35;
            this.setSinging(this.singing_timer);
        }
        return null; // Caso você tenha o SoundEvent do sapo, adicione-o aqui. No original retornava a string "orespawn:frog".
    }
}