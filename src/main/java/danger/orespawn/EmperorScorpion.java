package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import java.util.List;

public class EmperorScorpion extends EntityMob {
    
    // Sincronização da animação de ataque (antigo ID 20)
    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(EmperorScorpion.class, DataSerializers.VARINT);
    private int hurt_timer = 0;

    public EmperorScorpion(World worldIn) {
        super(worldIn);
        this.setSize(3.5f, 3.0f);
        this.experienceValue = 200;
        this.isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 0.9D, false));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D)); // Substituído WanderALot por padrão
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.2D, false));
        
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(400.0D); // Valor padrão OreSpawn
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D); // Dano pesado
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(15.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.hurt_timer > 0) --this.hurt_timer;
        
        // Lógica de Cura Passiva
        if (!this.world.isRemote && this.ticksExisted % 100 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(2.0f);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof EntityLivingBase) {
                int duration = 6;
                if (this.world.getDifficulty() == EnumDifficulty.NORMAL) duration = 10;
                else if (this.world.getDifficulty() == EnumDifficulty.HARD) duration = 12;

                if (this.rand.nextInt(3) == 1) {
                    ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, duration * 20, 0));
                }
                
                // Efeito de Knockback customizado (o escorpião joga pra longe)
                double angle = Math.atan2(entityIn.posZ - this.posZ, entityIn.posX - this.posX);
                entityIn.addVelocity(Math.cos(angle) * 2.0, 0.4, Math.sin(angle) * 2.0);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        // Drop simplificado para não depender de outras classes por enquanto
        this.dropItem(Items.BEEF, 4 + this.rand.nextInt(8));
        this.dropItem(Item.getItemFromBlock(Blocks.OBSIDIAN), 4 + this.rand.nextInt(5));
        
        if (this.rand.nextInt(10) == 0) {
            this.dropItem(Items.DIAMOND, 1);
        }
    }

    // Gerenciamento do estado de ataque para animação
    public int getAttacking() { return this.dataManager.get(ATTACKING); }
    public void setAttacking(int val) { this.dataManager.set(ATTACKING, val); }

    @Override
    protected net.minecraft.util.SoundEvent getAmbientSound() { return SoundEvents.ENTITY_SPIDER_AMBIENT; }
    @Override
    protected net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_SPIDER_HURT; }
    @Override
    protected net.minecraft.util.SoundEvent getDeathSound() { return SoundEvents.ENTITY_SPIDER_DEATH; }
}