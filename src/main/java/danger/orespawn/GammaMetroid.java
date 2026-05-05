package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class GammaMetroid extends EntityTameable {

    public GammaMetroid(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.5F);
        this.experienceValue = 20;
        this.isImmuneToFire = true; // Substitui o fireResistance = 1000 da 1.7.10
        
        if (this.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) {
            ((net.minecraft.pathfinding.PathNavigateGround) this.getNavigator()).setCanSwim(true);
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        // A IA de sentar (Sitting) é ativada automaticamente pelo EntityTameable na 1.12.2
        this.tasks.addTask(1, this.aiSit); 
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.2D, Items.IRON_INGOT, false));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.25D, false)); // Substitui a IA manual de ataque
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 2.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        
        // Target Tasks (Quem ele ataca)
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(OreSpawnMain.GammaMetroid_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        
        // Tameables não têm dano por padrão, deve ser registrado
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawnMain.GammaMetroid_stats.attack);
        
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(OreSpawnMain.GammaMetroid_stats.defense);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        
        // Inteligência para Comer Pedra e se curar
        if (!this.world.isRemote && !this.isSitting() && OreSpawnMain.PlayNicely == 0) {
            if ((this.getHealth() < this.getMaxHealth() && this.rand.nextInt(20) == 0) || this.rand.nextInt(100) == 0) {
                this.eatStoneToHeal();
            }
        }
    }

    /**
     * Substitui o pesadelo de performance "scan_it". Procura pedra em volta e come.
     */
    private void eatStoneToHeal() {
        BlockPos myPos = new BlockPos(this);
        int radius = 4;
        
        for (int x = -radius; x <= radius; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos targetPos = myPos.add(x, y, z);
                    
                    if (this.world.getBlockState(targetPos).getBlock() == Blocks.STONE) {
                        // Navega até a pedra (se estiver meio longe) ou come instantaneamente se estiver perto
                        if (this.getDistanceSqToCenter(targetPos) > 4.0D) {
                            this.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0D);
                            return; // Retorna para andar até a pedra
                        } else {
                            if (this.world.getGameRules().getBoolean("mobGriefing")) {
                                this.world.destroyBlock(targetPos, false); // Come a pedra, não dropa item
                            }
                            this.heal(1.0F);
                            this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, this.rand.nextFloat() * 0.2F + 1.5F);
                            return; // Comeu
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!stack.isEmpty()) {
            // TENTATIVA DE DOMESTICAR COM FERRO
            if (stack.getItem() == Items.IRON_INGOT && !this.isTamed()) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                
                if (!this.world.isRemote) {
                    if (this.rand.nextInt(3) == 0) {
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
                    // Como setTamed(false) não apaga o dono na 1.12, o limpamos manualmente:
                    this.setOwnerId(null); 
                    this.aiSit.setSitting(false);
                    this.playTameEffect(false);
                }
                return true;
            }

            // MUDAR NOME
            if (stack.getItem() == Items.NAME_TAG && this.isTamed() && this.isOwner(player)) {
                if (stack.hasDisplayName()) {
                    this.setCustomNameTag(stack.getDisplayName());
                    if (!player.capabilities.isCreativeMode) stack.shrink(1);
                    return true;
                }
            }
            
            // ACASALAR (Crystal Apple)
            if (this.isBreedingItem(stack) && this.isTamed() && this.isOwner(player)) {
                // Deixa o Tameable padrão resolver o acasalamento (consumir item, corações)
                return super.processInteract(player, hand);
            }
        }

        // COMANDO DE SENTAR/LEVANTAR
        if (this.isTamed() && this.isOwner(player) && stack.isEmpty()) {
            if (!this.world.isRemote) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
            }
            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
    }

    // --- Reprodução e Filhotes ---
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        GammaMetroid baby = new GammaMetroid(this.world);
        UUID ownerId = this.getOwnerId();
        if (ownerId != null) {
            baby.setOwnerId(ownerId);
            baby.setTamed(true);
        }
        return baby;
    }

    // --- Regras de Spawn ---
    @Override
    public boolean getCanSpawnHere() {
        if (this.posY > 50.0D && this.world.provider.getDimension() != OreSpawnMain.DimensionID4) return false;

        // Metroids gostam de nascer no escuro total (cavernas)
        BlockPos pos = new BlockPos(this);
        int lightLevel = this.world.getLightFor(EnumSkyBlock.BLOCK, pos);
        
        if (this.world.isThundering()) {
            lightLevel -= this.world.getSkylightSubtracted();
        }
        if (lightLevel > this.rand.nextInt(8)) return false;

        return super.getCanSpawnHere();
    }

    @Override
    protected boolean canDespawn() {
        if (this.isChild() || this.isTamed()) return false;
        return !this.isNoDespawnRequired();
    }

    // --- Drops e Sons ---
    @Override
    protected Item getDropItem() {
        return Items.IRON_INGOT;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int nuggets = 5 + this.rand.nextInt(10 + lootingModifier);
        for (int i = 0; i < nuggets; ++i) this.dropItem(Items.GOLD_NUGGET, 1);

        int ingots = 6 + this.rand.nextInt(10 + lootingModifier);
        for (int i = 0; i < ingots; ++i) this.dropItem(Items.IRON_INGOT, 1);
    }

    @Override protected SoundEvent getAmbientSound() { return this.rand.nextInt(5) == 0 ? null /* "orespawn:wtf_living" */ : null; }
    @Override protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }
    @Override protected float getSoundVolume() { return 1.5F; }
}