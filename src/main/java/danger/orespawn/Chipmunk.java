package danger.orespawn;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Chipmunk extends EntityTameable {

    public Chipmunk(World worldIn) {
        super(worldIn);
        this.setSize(0.35F, 0.35F);
        this.experienceValue = 5;
        this.getNavigator().setCanSwim(true);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAISit(this)); // Obrigatório para pets
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowOwner(this, 2.0D, 10.0F, 2.0F));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityMob.class, 8.0F, 1.0D, 1.6D));
        this.tasks.addTask(5, new EntityAITempt(this, 1.2D, Items.APPLE, false));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(7, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0F, 1.0D, 1.4D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.38D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    public int mygetMaxHealth() {
        return 5;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D) {
            return false;
        }
        return this.findBuddies() <= 2 && super.getCanSpawnHere();
    }

    private int findBuddies() {
        List<Chipmunk> list = this.world.getEntitiesWithinAABB(Chipmunk.class, this.getEntityBoundingBox().expand(20.0D, 10.0D, 20.0D));
        return list.size();
    }

    @Override
    protected void fall(float distance, float damageMultiplier) {
        int i = MathHelper.ceil(distance - 3.0F);
        if (i > 0) {
            if (i > 3) {
                this.playSound(SoundEvents.ENTITY_GENERIC_BIG_FALL, 1.0F, 1.0F);
            } else {
                this.playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1.0F, 1.0F);
            }
            if (i > 2) {
                i = 2; // Limite de dano de queda
            }
            this.attackEntityFrom(DamageSource.FALL, (float) i);
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;

        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        if (this.world.rand.nextInt(250) == 0 && this.getHealth() < this.mygetMaxHealth()) {
            this.heal(1.0F);
        }
        
        // Destruição de blocos de terra (Griefing do esquilo)
        if (!this.world.isRemote && this.world.rand.nextInt(600) == 1) {
            BlockPos posDown = new BlockPos((int) this.posX, (int) this.posY - 1, (int) this.posZ);
            Block bid = this.world.getBlockState(posDown).getBlock();
            
            if ((bid == Blocks.DIRT || bid == Blocks.FARMLAND) && this.world.getGameRules().getBoolean("mobGriefing")) {
                this.world.setBlockToAir(posDown);
            }
        }
        super.updateAITasks();
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed()) {
            if (this.isOwner(player)) {
                // "Desdomesticar" o esquilo usando um DeadBush
                if (!itemstack.isEmpty() && itemstack.getItem() == Item.getItemFromBlock(Blocks.DEADBUSH)) {
                    if (!this.world.isRemote) {
                        this.setTamedBy(null);
                        this.setTamed(false);
                        this.playTameEffect(false);
                        this.world.setEntityState(this, (byte) 6);
                    }
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    return true;
                }
                
                // Ignorar as maçãs caso ele seja clicado para evitar que coma maçãs enquanto está a tentar sentar-se.
                if (!itemstack.isEmpty() && (itemstack.getItem() == Items.APPLE || itemstack.getItem() == OreSpawnMain.MyCrystalApple)) {
                    return super.processInteract(player, hand);
                }

                if (!this.world.isRemote) {
                    this.aiSit.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.navigator.clearPath();
                }
                return true;
            }
        } else if (!itemstack.isEmpty() && itemstack.getItem() == Items.APPLE && player.getDistanceSq(this) < 16.0D) {
            // Taming (Domesticar com maçã)
            if (!player.capabilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            if (!this.world.isRemote) {
                if (this.rand.nextInt(2) == 0) {
                    this.setTamedBy(player);
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte) 7);
                    this.heal(this.mygetMaxHealth() - this.getHealth());
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte) 6);
                }
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
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

    @Override
    protected float getSoundPitch() {
        return this.isChild() ? (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.1F + 1.5F : (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.1F + 1.0F;
    }

    @Override
    protected Item getDropItem() {
        return Items.WHEAT;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        if (this.isTamed()) {
            int var3 = 2 + this.world.rand.nextInt(5);
            for (int var4 = 0; var4 < var3; ++var4) {
                this.dropItem(Item.getItemFromBlock(Blocks.RED_FLOWER), 1);
            }
        } else {
            // Drop normal
            int j = this.world.rand.nextInt(3) + this.world.rand.nextInt(1 + lootingModifier);
            for (int k = 0; k < j; ++k) {
                this.dropItem(Items.WHEAT, 1);
            }
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new Chipmunk(this.world);
    }
}