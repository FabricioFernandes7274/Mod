package danger.orespawn;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class Flounder extends EntityAnimal {

    public Flounder(World worldIn) {
        super(worldIn);
        this.setSize(0.55F, 0.25F); // Peixe pequeno e achatado
        this.experienceValue = 5;
        
        // Garante que ele possa tentar andar na terra se for jogado pra fora d'água
        if (this.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) {
            ((net.minecraft.pathfinding.PathNavigateGround) this.getNavigator()).setCanSwim(true);
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0D));
        // O peixe foge de jogadores que chegam muito perto
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0F, 1.0D, 1.4D));
        this.tasks.addTask(4, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.isDead) return;

        // Se estiver FORA d'água, ele tenta desesperadamente encontrar água
        if (!this.isInWater() && this.world.rand.nextInt(20) == 0) {
            BlockPos waterPos = this.findWater();
            
            if (waterPos != null) {
                // Tenta se arrastar de volta para a água
                this.getNavigator().tryMoveToXYZ(waterPos.getX(), waterPos.getY(), waterPos.getZ(), 1.0D);
            } else {
                // Sofre dano de asfixia em vez do "heal(-1)" bizarro do original
                if (this.world.rand.nextInt(25) == 0) {
                    this.attackEntityFrom(DamageSource.DROWN, 1.0F);
                }
            }
        }
        
        // Se estiver NA água, ocasionalmente se cura e faz barulho de splash
        if (this.isInWater() && this.world.rand.nextInt(50) == 0) {
            this.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1.0F, this.rand.nextFloat() * 0.2F + 0.9F);
            this.heal(1.0F);
        }
    }

    /**
     * Substitui o 'scan_it' bizarro original. Procura blocos de água em um raio de 8 blocos.
     */
    private BlockPos findWater() {
        BlockPos myPos = new BlockPos(this);
        int radius = 8;
        
        // Busca otimizada ao redor do peixe
        for (int x = -radius; x <= radius; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos targetPos = myPos.add(x, y, z);
                    // Na 1.12.2 a melhor forma de achar água é pela classe Material
                    if (this.world.getBlockState(targetPos).getMaterial() == Material.WATER) {
                        return targetPos;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D) return false;
        if (!this.world.isDaytime()) return false;
        if (this.rand.nextInt(20) != 0) return false; // Reduz a frequência de spawn

        // Limite de População (Agrupamento)
        List<Flounder> buddies = this.world.getEntitiesWithinAABB(Flounder.class, this.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D));
        return buddies.size() <= 10 && super.getCanSpawnHere();
    }

    @Override
    protected boolean canDespawn() {
        // Bebês não devem dar despawn para não arruinar fazendas de peixe
        if (this.isChild()) {
            return false;
        }
        return !this.isNoDespawnRequired();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        // No OreSpawn original, peixes se reproduzem com a Maçã de Cristal
        return !stack.isEmpty() && stack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    @Override
    public Flounder createChild(EntityAgeable ageable) {
        return new Flounder(this.world);
    }

    // --- Drops ---
    @Override
    protected Item getDropItem() {
        return Items.FISH;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int count = 1 + this.rand.nextInt(2 + lootingModifier);
        for (int i = 0; i < count; ++i) {
            this.dropItem(Items.FISH, 1);
        }
    }

    // --- Sons ---
    @Override protected float getSoundVolume() { return 0.4F; }
    @Override protected SoundEvent getAmbientSound() { return null; } // Retirei a explosão
    @Override protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }
}