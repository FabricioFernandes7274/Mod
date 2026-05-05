package danger.orespawn;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Cockateil extends EntityAnimal {
    
    // Modernização do DataManager (Sincroniza as 6 cores do pássaro com o ecrã do jogador)
    private static final DataParameter<Integer> BIRD_TYPE = EntityDataManager.createKey(Cockateil.class, DataSerializers.VARINT);
    
    private BlockPos currentFlightTarget = null;
    private boolean killedByPlayer = false;
    
    private static final ResourceLocation TEXTURE_1 = new ResourceLocation("orespawn", "Bird1.png");
    private static final ResourceLocation TEXTURE_2 = new ResourceLocation("orespawn", "Bird2.png");
    private static final ResourceLocation TEXTURE_3 = new ResourceLocation("orespawn", "Bird3.png");
    private static final ResourceLocation TEXTURE_4 = new ResourceLocation("orespawn", "Bird4.png");
    private static final ResourceLocation TEXTURE_5 = new ResourceLocation("orespawn", "Bird5.png");
    private static final ResourceLocation TEXTURE_6 = new ResourceLocation("orespawn", "Bird6.png"); // O Ruby Bird!
    
    private int stuckCount = 0;
    private int lastX = 0;
    private int lastZ = 0;
    private int flyUp = 0;

    public Cockateil(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 2;
        this.isImmuneToFire = false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        // Atribui uma cor aleatória de 0 a 5 ao nascer
        this.dataManager.register(BIRD_TYPE, this.world.rand.nextInt(6));
    }

    public ResourceLocation getTexture() {
        int birdType = this.getBirdType();
        switch (birdType) {
            case 0: return TEXTURE_1;
            case 1: return TEXTURE_2;
            case 2: return TEXTURE_3;
            case 3: return TEXTURE_4;
            case 4: return TEXTURE_5;
            case 5: return TEXTURE_6;
            default: return TEXTURE_1;
        }
    }

    public int getBirdType() {
        return this.dataManager.get(BIRD_TYPE);
    }

    public void setBirdType(int type) {
        this.dataManager.set(BIRD_TYPE, type);
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int mygetMaxHealth() {
        return 2; // É muito frágil
    }

    @Override
    public boolean getCanSpawnHere() {
        // Permite spawns de dia na Terra ou a qualquer momento na Utopia (DimensionID4 no original)
        if (!this.world.isDaytime() && this.world.provider.getDimension() != OreSpawnMain.DimensionID4) {
            return false;
        }
        return this.posY >= 50.0D && super.getCanSpawnHere();
    }

    // --- Sistema de Combate ---

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity e = source.getTrueSource();
        if (e instanceof EntityPlayer) {
            this.killedByPlayer = true;
            this.setFlyUp(); // Assusta o pássaro, que vai tentar fugir para o céu
        }
        return super.attackEntityFrom(source, amount);
    }

    public void setFlyUp() {
        this.flyUp = 2; // Isto vai alterar a matemática do TargetFlight para o atirar no ar
    }

    // --- IA de Voo Customizada ---

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos(this);
        } else {
            // Se estiver abaixo do alvo, a gravidade é 0.7 (sobe devagar). Se estiver acima, é 0.5 (desce normalmente).
            if (this.posY < (double) this.currentFlightTarget.getY()) {
                this.motionY *= 0.7D;
            } else {
                this.motionY *= 0.5D;
            }
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(
            new Vec3d(this.posX, this.posY + 0.75D, this.posZ), 
            new Vec3d(pX, pY, pZ), 
            false
        ) == null;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();

        int stayUp = 0;
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID4) {
            stayUp = 2; // Na Utopia, eles voam mais alto
        }

        // Lógica de desprendimento. Se o pássaro bate numa parede e não sai do sítio
        if (this.lastX == (int) this.posX && this.lastZ == (int) this.posZ) {
            this.stuckCount++;
        } else {
            this.stuckCount = 0;
            this.lastX = (int) this.posX;
            this.lastZ = (int) this.posZ;
        }

        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos(this);
        }

        if (this.stuckCount > 40 || this.world.rand.nextInt(250) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 4.1D) {
            this.stuckCount = 0;
            int keepTrying = 35;

            while (keepTrying > 0) {
                // Cálculo mágico do criador do mod que afasta ou aproxima o pássaro baseado no "susto" (flyUp)
                int xdir = this.world.rand.nextInt(8) + 5 - (this.flyUp * 2);
                int zdir = this.world.rand.nextInt(8) + 5 - (this.flyUp * 2);
                
                if (this.world.rand.nextBoolean()) xdir = -xdir;
                if (this.world.rand.nextBoolean()) zdir = -zdir;
                
                int ydir = this.world.rand.nextInt(9 + stayUp) - 5 + this.flyUp;

                BlockPos target = new BlockPos((int) this.posX + xdir, (int) this.posY + ydir, (int) this.posZ + zdir);

                if (this.world.isAirBlock(target) && this.canSeeTarget(target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D)) {
                    this.currentFlightTarget = target;
                    break;
                }
                keepTrying--;
            }
        }

        if (this.currentFlightTarget != null) {
            double dx = (double) this.currentFlightTarget.getX() + 0.3D - this.posX;
            double dy = (double) this.currentFlightTarget.getY() + 0.1D - this.posY;
            double dz = (double) this.currentFlightTarget.getZ() + 0.3D - this.posZ;
            
            this.motionX += (Math.signum(dx) * 0.3D - this.motionX) * 0.25D;
            this.motionY += (Math.signum(dy) * 0.699999D - this.motionY) * 0.200000001D;
            this.motionZ += (Math.signum(dz) * 0.3D - this.motionZ) * 0.25D;
            
            float targetYaw = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
            float yawDiff = MathHelper.wrapDegrees(targetYaw - this.rotationYaw);
            
            this.moveForward = 0.8F;
            this.rotationYaw += yawDiff / 3.0F;
        }
    }

    // --- Imunidade de Queda ---

    @Override
    protected void fall(float distance, float damageMultiplier) { }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) { }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true; 
    }

    // --- Drops, NBT e Áudio ---

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int birdType = this.getBirdType();
        
        // O clássico "Ruby Bird": se o pássaro for vermelho (5) e morto por um jogador, tem 33% chance de dar um Rubi!
        if (birdType == 5 && this.killedByPlayer && this.world.rand.nextInt(3) == 1) {
            this.dropItem(OreSpawnMain.MyRuby, 1);
            return;
        }
        
        this.dropItem(Items.FEATHER, 1); // Caso contrário, dá uma pena.
    }

    @Override
    protected SoundEvent getAmbientSound() { 
        if (this.world.isDaytime() && !this.world.isRaining()) {
            return new SoundEvent(new ResourceLocation("orespawn", "birds"));
        }
        return null;
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
        return 0.55F;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("BirdType", this.getBirdType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.setBirdType(tag.getInteger("BirdType"));
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }
}