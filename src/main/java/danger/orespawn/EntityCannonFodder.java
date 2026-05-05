package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public abstract class EntityCannonFodder extends EntityTameable {

    // Sincronização moderna na 1.12.2
    private static final DataParameter<Integer> ACTIVATED = EntityDataManager.createKey(EntityCannonFodder.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HAT_COLOR = EntityDataManager.createKey(EntityCannonFodder.class, DataSerializers.VARINT);

    private String name_one = null;
    private String name_two = null;
    private BlockPos patrolPos = BlockPos.ORIGIN;

    public EntityCannonFodder(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ACTIVATED, 0);
        this.dataManager.register(HAT_COLOR, 0);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        // Lógica de Domesticar / Militarizar
        if (!this.isTamed()) {
            int color = 0;
            if (stack.getItem() == Items.CARROT) color = 1;
            else if (stack.getItem() == Items.POTATO) color = 3;
            else if (stack.getItem() == OreSpawnMain.MyQuinoa) color = 2;

            if (color > 0) {
                if (!player.capabilities.isCreativeMode) stack.shrink(1);
                
                if (!this.world.isRemote) {
                    this.setTamedBy(player);
                    this.setHatColor(color);
                    this.setActivated(1);
                    this.navigator.clearPath();
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte)7); // Partículas de coração
                }
                return true;
            }
        } else if (this.isOwner(player)) {
            // Se já é dono, clicar muda para o modo "Sentar/Patrulhar"
            if (!this.world.isRemote) {
                this.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.patrolPos = new BlockPos(this.posX, this.posY, this.posZ);
                this.setActivated(2); // Ativado para combate
            }
            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();

        if (this.getActivated() == 2 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            EntityLivingBase target = this.findSomethingToAttack();
            if (target != null) {
                this.getNavigator().tryMoveToEntityLiving(target, 1.25D);
                
                // Distância de ataque (ajustada para 1.12.2)
                if (this.getDistanceSq(target) < 4.0D && this.ticksExisted % 10 == 0) {
                    this.attackEntityAsMob(target);
                }
            } else if (this.isSitting()) {
                // Volta para o posto de patrulha se estiver sentado e sem alvo
                this.getNavigator().tryMoveToXYZ(patrolPos.getX(), patrolPos.getY(), patrolPos.getZ(), 0.65D);
            }
        }
    }

    private EntityLivingBase findSomethingToAttack() {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0D, 4.0D, 10.0D));
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity)) return entity;
        }
        return null;
    }

    private boolean isSuitableTarget(EntityLivingBase target) {
        if (target == null || target == this || !target.isEntityAlive()) return false;
        if (!this.getEntitySenses().canSee(target)) return false;

        // Ataca monstros
        if (target instanceof EntityMob) return true;

        // Ataca soldados de outras cores (Guerra de Cores do OreSpawn)
        if (target instanceof EntityCannonFodder) {
            EntityCannonFodder other = (EntityCannonFodder) target;
            return other.getHatColor() != 0 && other.getHatColor() != this.getHatColor();
        }

        // Ataca players que não são os donos
        if (target instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) target;
            if (p.capabilities.isCreativeMode) return false;
            return !this.isOwner(p);
        }

        return false;
    }

    // Getters e Setters para sincronização
    public int getHatColor() { return this.dataManager.get(HAT_COLOR); }
    public void setHatColor(int color) { this.dataManager.set(HAT_COLOR, color); }
    public int getActivated() { return this.dataManager.get(ACTIVATED); }
    public void setActivated(int stage) { this.dataManager.set(ACTIVATED, stage); }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("HatColor", this.getHatColor());
        compound.setInteger("IsActivated", this.getActivated());
        compound.setLong("PatrolPos", this.patrolPos.toLong());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setHatColor(compound.getInteger("HatColor"));
        this.setActivated(compound.getInteger("IsActivated"));
        if (compound.hasKey("PatrolPos")) this.patrolPos = BlockPos.fromLong(compound.getLong("PatrolPos"));
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) { return null; }
}