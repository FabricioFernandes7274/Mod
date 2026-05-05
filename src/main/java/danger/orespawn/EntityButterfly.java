package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class EntityButterfly extends EntityAmbientCreature {

    // Sincronização do tipo de borboleta entre servidor e cliente
    private static final DataParameter<Integer> BUTTERFLY_TYPE = EntityDataManager.createKey(EntityButterfly.class, DataSerializers.VARINT);
    
    private BlockPos currentFlightTarget;
    private int force_sync = 25;

    public EntityButterfly(World worldIn) {
        super(worldIn);
        this.setSize(0.4f, 0.4f);
        // Garante que borboletas não "pousem" na água e fiquem presas
        if (this.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) {
            ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        // Inicializa com um tipo aleatório (0 a 3)
        this.dataManager.register(BUTTERFLY_TYPE, this.rand.nextInt(4));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
        // Registra dano de ataque para a variante agressiva
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        
        // Simula a física de "flutuar" reduzindo a queda vertical
        this.motionY *= 0.6000000238418579D;

        // Sincronização periódica (opcional, mas mantida do original)
        if (!this.world.isRemote) {
            if (--this.force_sync <= 0) {
                this.force_sync = 25;
                // O servidor apenas garante que o valor está setado
            }
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();

        // Lógica de Voo
        if (this.currentFlightTarget == null || this.world.rand.nextInt(30) == 0 || 
            this.currentFlightTarget.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0D) {
            
            this.currentFlightTarget = new BlockPos(
                (int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
                (int)this.posY + this.rand.nextInt(6) - 2,
                (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7)
            );
        }

        // Variante Agressiva (V-Butterfly) na dimensão Utopia/Chaos
        if (this.getButterflyType() == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            EntityLivingBase target = this.findSomethingToAttack();
            if (target != null) {
                this.currentFlightTarget = new BlockPos((int)target.posX, (int)target.posY + 1, (int)target.posZ);
                if (this.getDistanceSq(target) < 2.0D) {
                    this.attackEntityAsMob(target);
                }
            }
        }

        // Movimentação em direção ao alvo de voo
        double dx = (double)this.currentFlightTarget.getX() + 0.5D - this.posX;
        double dy = (double)this.currentFlightTarget.getY() + 0.1D - this.posY;
        double dz = (double)this.currentFlightTarget.getZ() + 0.5D - this.posZ;

        this.motionX += (Math.signum(dx) * 0.5D - this.motionX) * 0.1D;
        this.motionY += (Math.signum(dy) * 0.7D - this.motionY) * 0.1D;
        this.motionZ += (Math.signum(dz) * 0.5D - this.motionZ) * 0.1D;

        float angle = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float wrap = MathHelper.wrapDegrees(angle - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += wrap;
    }

    private EntityLivingBase findSomethingToAttack() {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
        for (EntityLivingBase entity : list) {
            if (entity instanceof EntityPlayer) {
                if (!((EntityPlayer)entity).isCreative()) return entity;
            } else if (entity instanceof EntityHorse) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player instanceof EntityPlayerMP && !this.world.isRemote) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.isEmpty()) {
                // Teletransporte para a Dimensão 6 (Utopia)
                int targetDim = (player.dimension != 6) ? 6 : 0;
                WorldServer worldServer = player.getServer().getWorld(targetDim);
                player.changeDimension(targetDim, new OreSpawnTeleporter(worldServer));
                return true;
            }
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D && this.world.provider.getDimension() != 6) return false;
        if (!this.world.isDaytime()) return false;
        
        BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        return this.world.getBlockState(pos).getBlock() == Blocks.AIR && super.getCanSpawnHere();
    }

    // Getters e Setters para o DataManager
    public int getButterflyType() { return this.dataManager.get(BUTTERFLY_TYPE); }
    public void setButterflyType(int type) { this.dataManager.set(BUTTERFLY_TYPE, type); }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("ButterflyType", this.getButterflyType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setButterflyType(compound.getInteger("ButterflyType"));
    }

    // Sons e Queda
    @Override protected SoundEvent getAmbientSound() { return null; }
    @Override protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }
    @Override protected void updateFallState(double y, boolean onGroundIn) {}
    @Override public void fall(float distance, float damageMultiplier) {}
}