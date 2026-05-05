package danger.orespawn;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Cephadrome extends EntityCreature {

    // Sistema moderno de sincronização de estado
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(Cephadrome.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> ACTIVITY = EntityDataManager.createKey(Cephadrome.class, DataSerializers.BYTE);

    private RenderInfo renderdata;
    
    // Variáveis que o descompilador perdeu
    private int damage_counter = 100;
    private int updateit = 1;
    private int color = 1;
    private int playing = 0;
    private int hurt_timer = 0;
    private int wasfed;
    private int shouldattack = 0;
    private int wing_sound = 0;
    private int hit_by_player = 0;
    private int badmood = 0;

    public Cephadrome(World worldIn) {
        super(worldIn);
        this.setSize(2.5F, 2.25F);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 200;
        this.isImmuneToFire = false;
        
        this.resetRenderInfo();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        // O Cephadrome viaja longas distâncias, o WanderAvoidWater é ideal
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 1.0D)); 
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 9.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        
        // Entidades "Creature" não atacam por defeito, é preciso registar o atributo
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(70.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, (byte) 0);
        this.dataManager.register(ACTIVITY, (byte) 0);
    }

    private void resetRenderInfo() {
        if (this.renderdata == null) this.renderdata = new RenderInfo();
        this.renderdata.rf1 = 0.0f; this.renderdata.rf2 = 0.0f;
        this.renderdata.rf3 = 0.0f; this.renderdata.rf4 = 0.0f;
        this.renderdata.ri1 = 0; this.renderdata.ri2 = 0;
        this.renderdata.ri3 = 0; this.renderdata.ri4 = 0;
    }

    public int mygetMaxHealth() {
        return 300; // O Cephadrome tem imensa vida!
    }

    @Override
    public int getTotalArmorValue() {
        return 16;
    }

    public RenderInfo getRenderInfo() {
        return this.renderdata;
    }

    // --- Sistema de Montaria Atualizado ---

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        
        // Se a mão não estiver vazia (se calhar usavam um item para montar no mod original)
        if (!this.world.isRemote && !this.isBeingRidden() && itemstack.isEmpty()) {
            player.startRiding(this); // Inicia a montaria na 1.12.2
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean canBeSteered() {
        // Permite que o jogador controle o Cephadrome (requer uso das teclas WASD na IA de movimento)
        return this.getControllingPassenger() instanceof EntityLivingBase;
    }

    @Override
    public Entity getControllingPassenger() {
        // O primeiro passageiro controla a besta
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public double getMountedYOffset() {
        return 2.5D; // Posição do jogador em cima dele
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (this.isPassenger(passenger)) {
            // Posiciona o cavaleiro corretamente
            float f = 0.75F;
            passenger.setPosition(
                this.posX - (double) f * Math.sin(Math.toRadians(this.rotationYaw)), 
                this.posY + this.getMountedYOffset() + passenger.getYOffset(), 
                this.posZ + (double) f * Math.cos(Math.toRadians(this.rotationYaw))
            );
        }
    }

    // --- Inteligência e Ataques ---

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {
            --this.updateit;
            if (this.updateit <= 0) {
                this.updateit = 30;
                if (this.isBeingRidden()) {
                    this.setActivity(1);
                } else {
                    this.setActivity(0);
                }
            }

            // Regeneração lenta (1/100 ticks)
            if (this.world.rand.nextInt(100) == 1 && this.getHealth() < this.mygetMaxHealth()) {
                this.heal(2.0F);
            }
        }
        
        // Se bater asas e tiver a voar (Activity != 1)
        if (this.getActivity() != 1 && this.world.rand.nextInt(6) == 1) {
            // "orespawn:MothraWings" - Pode ser registado futuramente, aqui omitimos
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        
        // Só usa as AIs normais (atacar/andar) se não estiver a ser montado
        if (this.getActivity() == 0) {
            super.updateAITasks();
            
            // Lógica aleatória de procurar o ataque
            if (this.world.rand.nextInt(7) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                EntityLivingBase target = this.getAttackTarget();
                if (target != null && target.isEntityAlive()) {
                    if (this.getDistanceSq(target) < 16.0D) {
                        this.setAttacking(1);
                        this.attackEntityAsMob(target);
                    }
                } else {
                    this.setAttacking(0);
                }
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        return super.attackEntityAsMob(target);
    }

    // --- Drops Reparados ---

    private void dropItemRand(Item item, int amount) {
        ItemStack stack = new ItemStack(item, amount, 0);
        double dx = this.posX + (this.world.rand.nextDouble() * 5.0D) - (this.world.rand.nextDouble() * 5.0D);
        double dy = this.posY + 1.0D;
        double dz = this.posZ + (this.world.rand.nextDouble() * 5.0D) - (this.world.rand.nextDouble() * 5.0D);
        EntityItem entityItem = new EntityItem(this.world, dx, dy, dz, stack);
        this.world.spawnEntity(entityItem);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int i = 4 + this.world.rand.nextInt(6);
        for (int j = 0; j < i; ++j) {
            this.dropItemRand(OreSpawnMain.UraniumNugget, 1);
        }
        // ... (Mais pepitas ou carne, dependendo da config original, deixo este exemplo principal)
    }

    // --- Sincronização de Estados ---

    public final int getAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public final void setAttacking(int par1) {
        this.dataManager.set(ATTACKING, (byte) par1);
    }

    public final int getActivity() {
        return this.dataManager.get(ACTIVITY);
    }

    public final void setActivity(int par1) {
        this.dataManager.set(ACTIVITY, (byte) par1);
    }

    // --- Guardar/Ler do Disco (NBT) ---

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("CephaWasFed", this.wasfed);
        tag.setInteger("CephaHitByPlayer", this.hit_by_player);
        tag.setInteger("CephaBadMood", this.badmood);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.wasfed = tag.getInteger("CephaWasFed");
        this.hit_by_player = tag.getInteger("CephaHitByPlayer");
        this.badmood = tag.getInteger("CephaBadMood");
    }

    // --- Spawner e Sons ---

    @Override
    public boolean getCanSpawnHere() {
        // Corrige o loop maluco que o descompilador fez de verificação de spawners
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    BlockPos pos = new BlockPos((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                    Block bid = this.world.getBlockState(pos).getBlock();
                    
                    if (bid == Blocks.MOB_SPAWNER) {
                        TileEntity te = this.world.getTileEntity(pos);
                        if (te instanceof TileEntityMobSpawner) {
                            ResourceLocation mobName = ((TileEntityMobSpawner) te).getSpawnerBaseLogic().getEntityId();
                            if (mobName != null && mobName.getResourcePath().contains("cephadrome")) {
                                return true; 
                            }
                        }
                    }
                }
            }
        }
        return super.getCanSpawnHere();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_GENERIC_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }

    @Override
    protected float getSoundVolume() { return 1.5F; }

    @Override
    protected float getSoundPitch() { return 1.0F; }

    @Override
    public boolean canBePushed() { return false; } // Não empurres o Cephadrome!
}