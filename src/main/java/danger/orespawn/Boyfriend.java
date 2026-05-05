package danger.orespawn;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Boyfriend extends EntityTameable implements IRangedAttackMob {

    // Inventário do Boyfriend (no original ele pode carregar itens para ti)
    public InventoryBasic boyfriendInventory = new InventoryBasic("BoyfriendInventory", false, 9);

    public Boyfriend(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.8F);
        this.setTamed(false);
        this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAISit(this)); // Ele agora obedece ao comando de sentar
        this.tasks.addTask(3, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(4, new EntityAITempt(this, 1.0D, Items.DIAMOND, false));
        this.tasks.addTask(5, new EntityAIAttackRanged(this, 1.0D, 20, 60, 15.0F));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.1D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed()) {
            if (this.isOwner(player)) {
                // Se o player estiver agachado (Sneaking), abre o inventário dele
                if (player.isSneaking()) {
                    if (!this.world.isRemote) {
                        player.displayGUIChest(this.boyfriendInventory);
                    }
                    return true;
                }

                // Lógica de Trocas/Presentes (Onde o código original é gigante)
                if (!itemstack.isEmpty()) {
                    if (this.handleTrades(player, itemstack)) {
                        return true;
                    }
                }

                // Se clicar normal, ele senta ou levanta
                if (!this.world.isRemote) {
                    this.aiSit.setSitting(!this.isSitting());
                    this.isJumping = false;
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                }
                return true;
            }
        } else if (itemstack.getItem() == Items.DIAMOND) {
            // Taming (Domesticar)
            if (!player.capabilities.isCreativeMode) itemstack.shrink(1);
            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamedBy(player);
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    /**
     * Sistema de Trocas adaptado. No original eram centenas de 'if'.
     * Aqui organizei a lógica principal para ser expansível.
     */
    private boolean handleTrades(EntityPlayer player, ItemStack stack) {
        Item item = stack.getItem();
        ItemStack gift = ItemStack.EMPTY;

        // Exemplo de trocas clássicas do Boyfriend no OreSpawn
        if (item == Items.IRON_INGOT) gift = new ItemStack(Items.IRON_SWORD);
        else if (item == Items.GOLD_INGOT) gift = new ItemStack(Items.GOLDEN_APPLE);
        else if (item == Items.EMERALD) gift = new ItemStack(Items.DIAMOND);
        else if (item == Item.getItemFromBlock(Blocks.RED_FLOWER)) gift = new ItemStack(Items.COOKIE, 4);

        if (!gift.isEmpty()) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            this.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            if (!this.world.isRemote) {
                this.entityDropItem(gift, 0.5F);
            }
            return true;
        }
        return false;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        // Lógica de tiro (Flechas ou projéteis customizados do OreSpawn)
        EntityArrow entityarrow = new EntityTippedArrow(this.world, this);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityarrow.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, 1.0F);
        this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityarrow);
    }

    // --- Persistência de Dados (NBT) ---
    // Necessário para o inventário não sumir quando o jogo fecha
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.boyfriendInventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.boyfriendInventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setByte("Slot", (byte)i);
                itemstack.writeToNBT(nbt);
                nbttaglist.appendTag(nbt);
            }
        }
        compound.setTag("Inventory", nbttaglist);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Inventory", 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbt = nbttaglist.getCompoundTagAt(i);
            int j = nbt.getByte("Slot") & 255;
            if (j >= 0 && j < this.boyfriendInventory.getSizeInventory()) {
                this.boyfriendInventory.setInventorySlotContents(j, new ItemStack(nbt));
            }
        }
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {}

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) { return null; }

    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PLAYER_LEVELUP; }
    protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_PLAYER_HURT; }
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PLAYER_DEATH; }
}