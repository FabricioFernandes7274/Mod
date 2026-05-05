package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIPanic;
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
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class Gazelle extends EntityTameable {

    public Gazelle(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.8F);
        this.experienceValue = 5;
        
        if (this.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) {
            ((net.minecraft.pathfinding.PathNavigateGround) this.getNavigator()).setCanSwim(true);
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, this.aiSit); // Já incluso na EntityTameable
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowOwner(this, 2.0D, 10.0F, 2.0F));
        // Foge de monstros
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityMob.class, 8.0F, 1.0D, 1.7D));
        this.tasks.addTask(5, new EntityAITempt(this, 1.2D, Items.APPLE, false));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.5D));
        // Foge de jogadores se não for domesticada
        this.tasks.addTask(7, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 12.0F, 1.0D, 2.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(9, new EntityAIMoveIndoors(this));
        this.tasks.addTask(10, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(11, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D); // Mais rápida que a média
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        // Lógica de "comer colheitas"
        if (!this.world.isRemote && !this.isSitting() && OreSpawnMain.PlayNicely == 0) {
            if ((this.getHealth() < this.getMaxHealth() && this.rand.nextInt(30) == 0) || this.rand.nextInt(750) == 0) {
                this.eatCropsToHeal();
            } 
            // Se não tentar comer, procura um amigo para andar perto
            else if (this.rand.nextInt(250) == 0) {
                Gazelle buddy = this.findBuddy();
                if (buddy != null) {
                    this.getNavigator().tryMoveToEntityLiving(buddy, 1.0D);
                }
            }
        }
    }

    /**
     * Substitui o scan_it(). Procura Morangos, Batatas ou Cenouras para comer e se curar.
     */
    private void eatCropsToHeal() {
        BlockPos myPos = new BlockPos(this);
        int radius = 5;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos targetPos = myPos.add(x, y, z);
                    Block block = this.world.getBlockState(targetPos).getBlock();

                    if (block == OreSpawnMain.MyStrawberryPlant || block == Blocks.POTATOES || block == Blocks.CARROTS || block == Blocks.TALLGRASS || block == Blocks.DOUBLE_PLANT) {
                        
                        if (this.getDistanceSqToCenter(targetPos) > 4.0D) {
                            this.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0D);
                            return; 
                        } else {
                            if (this.world.getGameRules().getBoolean("mobGriefing")) {
                                // Se for grama alta ela quebra, se for plantação ela substitui por AR.
                                this.world.setBlockToAir(targetPos);
                            }
                            this.heal(1.0F);
                            this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, this.rand.nextFloat() * 0.2F + 0.9F);
                            return;
                        }
                    }
                }
            }
        }
    }

    private Gazelle findBuddy() {
        List<Gazelle> list = this.world.getEntitiesWithinAABB(Gazelle.class, this.getEntityBoundingBox().grow(16.0D, 6.0D, 16.0D));
        for (Gazelle buddy : list) {
            if (buddy != this && buddy.isEntityAlive()) {
                return buddy; // Retorna o primeiro amigo que achar
            }
        }
        return null;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!stack.isEmpty()) {
            // TENTATIVA DE DOMESTICAR COM MAÇÃS
            if (stack.getItem() == Items.APPLE && !this.isTamed()) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);

                if (!this.world.isRemote) {
                    if (this.rand.nextInt(2) == 0) {
                        this.setTamedBy(player);
                        this.navigator.clearPath();
                        this.setAttackTarget(null);
                        this.aiSit.setSitting(true);
                        this.heal(this.getMaxHealth());
                        this.playTameEffect(true);
                        this.world.setEntityState(this, (byte) 7);
                    } else {
                        this.playTameEffect(false);
                        this.world.setEntityState(this, (byte) 6);
                    }
                }
                return true;
            }

            // DESDOMESTICAR (Mata seca = DeadBush)
            if (stack.getItem() == Item.getItemFromBlock(Blocks.DEADBUSH) && this.isTamed() && this.isOwner(player)) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                if (!this.world.isRemote) {
                    this.setTamed(false);
                    this.setOwnerId(null);
                    this.aiSit.setSitting(false);
                    this.playTameEffect(false);
                }
                return true;
            }

            // NOMEAR
            if (stack.getItem() == Items.NAME_TAG && this.isTamed() && this.isOwner(player)) {
                if (stack.hasDisplayName()) {
                    this.setCustomNameTag(stack.getDisplayName());
                    if (!player.capabilities.isCreativeMode) stack.shrink(1);
                    return true;
                }
            }
            
            // ACASALAR (Crystal Apple)
            if (this.isBreedingItem(stack) && this.isTamed() && this.isOwner(player)) {
                return super.processInteract(player, hand);
            }
        }

        // SENTAR/LEVANTAR
        if (this.isTamed() && this.isOwner(player) && stack.isEmpty()) {
            if (!this.world.isRemote) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
            }
            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        // Reduz o dano máximo tomado se for domesticada (Proteção do dono)
        if (this.isTamed() && amount > 10.0F) {
            amount = 10.0F;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        // Reduz dano de queda
        float fallDamage = MathHelper.ceil(distance - 3.0F);
        if (fallDamage > 0.0F) {
            if (fallDamage > 3.0F) {
                this.playSound(SoundEvents.ENTITY_GENERIC_BIG_FALL, 1.0F, 1.0F);
            } else {
                this.playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1.0F, 1.0F);
            }
            
            if (fallDamage > 2.0F) fallDamage = 2.0F;
            this.attackEntityFrom(DamageSource.FALL, fallDamage);
        }
    }

    // --- Reprodução ---
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        Gazelle baby = new Gazelle(this.world);
        UUID ownerId = this.getOwnerId();
        if (ownerId != null) {
            baby.setOwnerId(ownerId);
            baby.setTamed(true);
        }
        return baby;
    }

    // --- Spawns ---
    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D || this.posY > 100.0D) return false;
        
        BlockPos pos = new BlockPos(this.posX, this.posY - 1, this.posZ);
        Block block = this.world.getBlockState(pos).getBlock();
        
        return block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.TALLGRASS;
    }

    @Override
    protected boolean canDespawn() {
        return false; // Gazelas nunca dão despawn no código original do OreSpawn
    }

    // --- Drops e Sons ---
    @Override
    protected Item getDropItem() {
        return Items.BEEF; // Curiosamente dropam carne de vaca
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        if (this.isTamed()) {
            // Dropa Papoulas vermelhas se for domesticada (Por que? Ninguém sabe)
            int count = 2 + this.rand.nextInt(5 + lootingModifier);
            for (int i = 0; i < count; ++i) {
                this.dropItem(Item.getItemFromBlock(Blocks.RED_FLOWER), 1);
            }
        } else {
            super.dropFewItems(wasRecentlyHit, lootingModifier);
        }
    }

    @Override protected float getSoundVolume() { return 0.4F; }
    @Override protected SoundEvent getAmbientSound() { return null; }
    @Override protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }
    
    @Override
    protected float getSoundPitch() {
        float pitch = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
        return this.isChild() ? pitch + 1.5F : pitch + 1.0F;
    }
}