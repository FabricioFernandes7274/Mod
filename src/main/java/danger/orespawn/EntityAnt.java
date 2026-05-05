package danger.orespawn;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class EntityAnt extends EntityCreature {

    public EntityAnt(World worldIn) {
        super(worldIn);
        this.setSize(0.1f, 0.1f);
        this.experienceValue = 0;
        
        // Configuração de navegação
        if (this.getNavigator() instanceof PathNavigateGround) {
            ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        }
    }

    @Override
    protected void initEntityAI() {
        // AI de natação é essencial para não afogar
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        // Substitua 'MyEntityAIWanderALot' pela sua classe customizada ou EntityAIWander
        this.tasks.addTask(2, new net.minecraft.entity.ai.EntityAIWanderAvoidWater(this, 1.0D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player instanceof EntityPlayerMP && !this.world.isRemote) {
            ItemStack itemstack = player.getHeldItem(hand);

            // A formiga só transporta se a mão estiver vazia
            if (itemstack.isEmpty()) {
                int targetDimension = (player.dimension != OreSpawnMain.DimensionID) ? OreSpawnMain.DimensionID : 0;
                
                // Lógica de Teletransporte da 1.12.2
                WorldServer worldServer = player.getServer().getWorld(targetDimension);
                
                // É necessário uma implementação de Teleporter (OreSpawnTeleporter)
                player.changeDimension(targetDimension, new OreSpawnTeleporter(worldServer));
                return true;
            }
        }
        return super.processInteract(player, hand);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Mantém a velocidade constante (redundante, mas presente no original)
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D) {
            return false;
        }
        // Limita a quantidade de formigas para evitar lag
        List<EntityAnt> buddies = this.world.getEntitiesWithinAABB(EntityAnt.class, this.getEntityBoundingBox().grow(20.0D, 10.0D, 20.0D));
        return buddies.size() <= 4 && super.getCanSpawnHere();
    }

    // Sons (Volume 0 conforme original)
    @Override
    protected SoundEvent getAmbientSound() { return null; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }
    @Override
    protected float getSoundVolume() { return 0.0f; }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }
}