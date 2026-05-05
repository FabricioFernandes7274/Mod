package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Cricket extends EntityCreature {

    // Sincroniza a ação de cantar com o cliente (para a animação)
    private static final DataParameter<Integer> SINGING = EntityDataManager.createKey(Cricket.class, DataSerializers.VARINT);

    private int singing = 0;
    private int jumpcount = 0;

    public Cricket(World worldIn) {
        super(worldIn);
        this.setSize(0.1F, 0.1F);
        this.experienceValue = 1;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 8, 1.0D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        
        // EntityCreature não tem ataque por defeito, logo temos de registar e dar valor nulo
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SINGING, 0);
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int getSinging() {
        return this.dataManager.get(SINGING);
    }

    public void setSinging(int value) {
        this.dataManager.set(SINGING, value);
    }

    // --- Mecânica de Salto ---

    private void jumpAround() {
        this.motionY += 0.55D + Math.abs(this.world.rand.nextFloat() * 0.35F);
        this.posY += 0.25D;
        
        float f = 0.3F + Math.abs(this.world.rand.nextFloat() * 0.25F);
        float d = (float) (this.world.rand.nextFloat() * Math.PI * 2.0D);
        
        this.motionX += f * Math.sin(d);
        this.motionZ += f * Math.cos(d);
        this.isAirBorne = true;
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
        
        if (!this.world.isRemote) {
            if (this.singing != 0) {
                --this.singing;
                if (this.singing <= 0) {
                    this.setSinging(0);
                }
            }
            if (this.jumpcount > 0) {
                --this.jumpcount;
            }
            if (this.jumpcount == 0 && this.world.rand.nextInt(50) == 1) {
                this.jumpAround();
                this.jumpcount = 50;
            }
        }
    }

    public int mygetMaxHealth() {
        return 3;
    }

    // --- Áudio ---

    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.world.isRemote) {
            // O grilo só faz barulho metade das vezes
            if (this.world.rand.nextInt(2) == 0) {
                return null;
            }
            // Inicia o timer de canto e atualiza para a renderização
            this.singing = 40;
            this.setSinging(this.singing);
        }
        return new SoundEvent(new ResourceLocation("orespawn", "cricket"));
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
        return 0.7F;
    }

    // Um grilo é demasiado pequeno para fazer sons de passos
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) { }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        // Não dropa nada
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    // --- Imunidade a Quedas ---

    @Override
    protected void fall(float distance, float damageMultiplier) { }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, BlockPos pos) { }

    // --- Controlo de Spawns ---

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 30.0D) {
            return false;
        }
        // Evita gerar se já houver demasiados grilos perto (mais de 5 num raio)
        return this.findBuddies() <= 5 && super.getCanSpawnHere();
    }

    private int findBuddies() {
        List<Cricket> list = this.world.getEntitiesWithinAABB(Cricket.class, this.getEntityBoundingBox().grow(20.0D, 10.0D, 20.0D));
        return list.size();
    }
}