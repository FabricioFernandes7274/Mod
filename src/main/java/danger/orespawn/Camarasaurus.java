package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
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

public class Camarasaurus extends EntityTameable {
    
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Camarasaurus(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 1.2F);
        this.experienceValue = 5;
        // Permite nadar (substitui o antigo setCanSwim)
        this.getNavigator().setCanSwim(true);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAISit(this)); // Obrigatório para Tameables
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowOwner(this, 2.0D, 10.0F, 2.0F));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityMob.class, 8.0F, 1.0D, 1.4D));
        this.tasks.addTask(5, new EntityAITempt(this, 1.2D, Items.APPLE, false));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(9, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    public int mygetMaxHealth() {
        return 20;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D) {
            return false;
        }
        return this.world.isDaytime() && super.getCanSpawnHere();
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        int i = MathHelper.ceil(distance - 3.0F);
        if (i > 0) {
            if (i > 3) {
                this.playSound(SoundEvents.ENTITY_GENERIC_BIG_FALL, 1.0F, 1.0F);
            } else {
                this.playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1.0F, 1.0F);
            }
            if (i > 2) {
                i = 2; // Limite de dano de queda para proteger a criatura
            }
            this.attackEntityFrom(DamageSource.FALL, (float) i);
        }
    }

    /**
     * Lógica simplificada de varredura (Scan) para encontrar folhas
     */
    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;
        BlockPos center = new BlockPos(x, y, z);
        
        for (int i = -dx; i <= dx; i++) {
            for (int j = -dy; j <= dy; j++) {
                for (int k = -dz; k <= dz; k++) {
                    // Verifica apenas as bordas da área da caixa atual para otimização
                    if (Math.abs(i) == dx || Math.abs(j) == dy || Math.abs(k) == dz) {
                        BlockPos checkPos = center.add(i, j, k);
                        Block bid = this.world.getBlockState(checkPos).getBlock();
                        
                        if (bid == Blocks.LEAVES || bid == Blocks.LEAVES2 || bid == Blocks.VINE || 
                            bid == Blocks.TALLGRASS || bid == Blocks.CACTUS || bid == Blocks.DOUBLE_PLANT) {
                            
                            int d = i * i + j * j + k * k; // Distância quadrada
                            if (d < this.closest) {
                                this.closest = d;
                                this.tx = checkPos.getX();
                                this.ty = checkPos.getY();
                                this.tz = checkPos.getZ();
                                found++;
                            }
                        }
                    }
                }
            }
        }
        return found != 0;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;

        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        
        super.updateAITasks();
        
        // IA para comer vegetação próxima e se curar
        if (!this.isSitting() && OreSpawnMain.PlayNicely == 0) {
            if ((this.world.rand.nextInt(20) == 0 && this.getHealth() < this.mygetMaxHealth()) || this.world.rand.nextInt(250) == 0) {
                this.closest = 99999;
                this.tx = 0;
                this.ty = 0;
                this.tz = 0;
                boolean foundFood = false;

                for (int i = 1; i < 11; ++i) {
                    int j = Math.min(i, 2);
                    if (this.scan_it((int) this.posX, (int) this.posY + 1, (int) this.posZ, i, j, i)) {
                        foundFood = true;
                        break;
                    }
                    if (i >= 6) ++i; // Pula iterações maiores para poupar processamento
                }

                if (foundFood && this.closest < 99999) {
                    this.getNavigator().tryMoveToXYZ((double) this.tx, (double) this.ty, (double) this.tz, 1.0D);
                    
                    if (this.closest < 12) { // Se estiver muito perto da planta
                        if (this.world.getGameRules().getBoolean("mobGriefing")) {
                            this.world.setBlockToAir(new BlockPos(this.tx, this.ty, this.tz));
                        }
                        this.heal(1.0F);
                        this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, this.world.rand.nextFloat() * 0.2F + 0.9F);
                    }
                }
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed()) {
            if (this.isOwner(player)) {
                // Sentar ou Levantar (Ignora se estiver a curar/reproduzir)
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
                    this.setTamedBy(player); // Subtitui o antigo func_152115_b
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
    public boolean isBreedingItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new Camarasaurus(this.world);
    }

    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.RED_FLOWER);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        if (this.isTamed()) {
            int amount = 2 + this.world.rand.nextInt(5);
            for (int i = 0; i < amount; ++i) {
                this.dropItem(Item.getItemFromBlock(Blocks.RED_FLOWER), 1);
            }
        }
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
}