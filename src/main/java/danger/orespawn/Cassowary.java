package danger.orespawn;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Cassowary extends EntityAnimal {

    public Cassowary(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 1.2F);
        this.experienceValue = 5;
        // Permite que o mob nade para não se afogar
        this.getNavigator().setCanSwim(true);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0D));
        // Foge de monstros
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityMob.class, 8.0F, 1.0D, 1.4D));
        // Foge de jogadores
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0F, 1.0D, 1.4D));
        this.tasks.addTask(4, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        
        // Entidades passivas não têm dano de ataque por padrão, é preciso registar o atributo primeiro
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    }

    @Override
    public boolean getCanSpawnHere() {
        // Só spawna de dia
        return this.world.isDaytime() && super.getCanSpawnHere();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Removemos o setBaseValue redundante que havia no código descompilado do onUpdate
    }

    // --- Sistema de Reprodução (Breeding) ---
    
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        // Usa maçã de cristal para reprodução (conforme o código original)
        return !stack.isEmpty() && stack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new Cassowary(this.world);
    }

    // --- Sistema de Drops ---

    @Override
    protected Item getDropItem() {
        return Items.CHICKEN;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        // Dropa de 2 a 4 frangos crus (mais bónus de pilhagem/looting)
        int count = 2 + this.world.rand.nextInt(3) + this.world.rand.nextInt(lootingModifier + 1);
        for (int i = 0; i < count; ++i) {
            this.dropItem(Items.CHICKEN, 1);
        }
    }

    // --- Sistema de Despawn ---

    @Override
    protected boolean canDespawn() {
        if (this.isChild()) {
            this.enablePersistence(); // Impede as crias de desaparecerem
            return false;
        }
        return !this.isNoDespawnRequired();
    }

    // --- Sistema de Sons ---

    @Override
    protected SoundEvent getAmbientSound() { 
        // O decompilador apontava para o som de explosão, o que é estranho para uma ave!
        // Mantive a lógica da explosão como estava no original, mas podes alterar para SoundEvents.ENTITY_CHICKEN_AMBIENT
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
        return 0.4F;
    }
}