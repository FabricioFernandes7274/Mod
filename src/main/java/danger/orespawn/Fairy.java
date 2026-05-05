package danger.orespawn;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class Fairy extends EntityAmbientCreature {

    // Gerenciador de sincronização para as 9 cores de fadas
    private static final DataParameter<Integer> FAIRY_TYPE = EntityDataManager.createKey(Fairy.class, DataSerializers.VARINT);

    private int my_blink;
    private int blinker = 0;
    private BlockPos currentFlightTarget = null;
    private String myowner = null; // Mantido como String para compatibilidade com o OreSpawn original

    public Fairy(World worldIn) {
        super(worldIn);
        this.setSize(0.4F, 0.8F);
        
        // Define o tempo de piscar (partículas) aleatório
        this.my_blink = 20 + this.rand.nextInt(20);
        
        if (this.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) {
            ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        }

        // Se estiver no servidor, escolhe uma cor aleatória
        if (!worldIn.isRemote) {
            this.setFairyType(this.rand.nextInt(9));
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(1, new EntityAILookIdle(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FAIRY_TYPE, 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D); // Fadas são bem resistentes!
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
        
        // Criaturas de ambiente não tem dano por padrão, precisamos registrar
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        
        // Física de flutuação
        this.motionY *= 0.6000000238418579D;
        
        this.blinker++;
        if (this.blinker > this.my_blink) {
            this.blinker = 0;
        }

        // Partículas brilhantes à noite
        long time = this.world.getWorldTime() % 24000L;
        if (time >= 12000L) {
            if (this.world.isRemote && this.rand.nextInt(5) == 0 && this.getBlink() > 1.0F) {
                this.world.spawnParticle(
                        EnumParticleTypes.FIREWORKS_SPARK, 
                        this.posX, this.posY - 0.15D, this.posZ, 
                        (this.rand.nextFloat() - this.rand.nextFloat()) / 8.0D, 
                        -this.rand.nextFloat() / 8.0D, 
                        (this.rand.nextFloat() - this.rand.nextFloat()) / 8.0D
                );
            }
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();

        // 1. Lógica de cura passiva
        if (this.ticksExisted % 250 == 0) this.heal(1.0F);

        // Define um alvo de voo se não tiver nenhum
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos(this.posX, this.posY, this.posZ);
        }

        // 2. Protege o jogador atacando monstros
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.rand.nextInt(12) == 0) {
            EntityLivingBase target = this.findSomethingToAttack();
            if (target != null) {
                this.currentFlightTarget = new BlockPos(target.posX, target.posY + 1.0D, target.posZ);
                if (this.getDistanceSq(target) < 6.0D) {
                    this.attackEntityAsMob(target);
                }
            }
        } 
        // 3. Segue o Dono
        else if (this.myowner != null) {
            EntityPlayer player = this.world.getPlayerEntityByName(this.myowner);
            if (player != null) {
                double dist = this.getDistanceSq(player);
                if (dist > 256.0D) {
                    // Teleporta se estiver muito longe (mais de 16 blocos)
                    this.setPosition(
                        player.posX + this.rand.nextFloat() - this.rand.nextFloat(), 
                        player.posY, 
                        player.posZ + this.rand.nextFloat() - this.rand.nextFloat()
                    );
                } else if (dist > 64.0D) {
                    // Voa na direção do dono
                    this.currentFlightTarget = new BlockPos(
                        player.posX + this.rand.nextInt(3) - this.rand.nextInt(3),
                        player.posY + 1.0D,
                        player.posZ + this.rand.nextInt(3) - this.rand.nextInt(3)
                    );
                }
            }
        }

        // 4. Voo Aleatório (Passear)
        if (this.rand.nextInt(200) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 2.5D) {
            int keep_trying = 25;
            while (keep_trying-- > 0) {
                int xdir = this.rand.nextInt(8) * (this.rand.nextBoolean() ? 1 : -1);
                int zdir = this.rand.nextInt(8) * (this.rand.nextBoolean() ? 1 : -1);
                int ydir = this.rand.nextInt(5) - 2;

                BlockPos nextTarget = new BlockPos(this.posX + xdir, this.posY + ydir, this.posZ + zdir);
                if (this.world.isAirBlock(nextTarget) && this.canSeeTarget(nextTarget)) {
                    this.currentFlightTarget = nextTarget;
                    break;
                }
            }
        }

        // 5. Aplica a física de movimento na direção do BlockPos selecionado
        double dx = (double)this.currentFlightTarget.getX() + 0.5D - this.posX;
        double dy = (double)this.currentFlightTarget.getY() + 0.1D - this.posY;
        double dz = (double)this.currentFlightTarget.getZ() + 0.5D - this.posZ;

        this.motionX += (Math.signum(dx) * 0.2D - this.motionX) * 0.1D;
        this.motionY += (Math.signum(dy) * 0.7D - this.motionY) * 0.1D;
        this.motionZ += (Math.signum(dz) * 0.2D - this.motionZ) * 0.1D;

        float angle = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float wrap = MathHelper.wrapDegrees(angle - this.rotationYaw);
        this.moveForward = 0.2F;
        this.rotationYaw += wrap / 4.0F;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) return null;
        
        List<EntityMob> list = this.world.getEntitiesWithinAABB(EntityMob.class, this.getEntityBoundingBox().grow(8.0D));
        for (EntityMob mob : list) {
            if (this.getEntitySenses().canSee(mob) && mob.isEntityAlive()) {
                return mob;
            }
        }
        return null;
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) return false;
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
    }

    public boolean canSeeTarget(BlockPos pos) {
        return this.world.rayTraceBlocks(
                new Vec3d(this.posX, this.posY + 0.25D, this.posZ), 
                new Vec3d(pos.getX(), pos.getY(), pos.getZ()), 
                false) == null;
    }

    public void setOwner(EntityPlayer player) {
        if (player != null) {
            this.myowner = player.getName(); // Na 1.12.2, getDisplayName() retorna ITextComponent. getName() retorna a String.
        }
    }

    public float getBlink() {
        return this.blinker < this.my_blink / 2 ? 240.0F : 0.0F;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D) return false;

        // Checa se tem espaço aberto para a fada nascer
        int airBlocks = 0;
        BlockPos pos = new BlockPos(this);
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (this.world.isAirBlock(pos.add(x, 0, z))) {
                    airBlocks++;
                }
            }
        }
        return airBlocks >= 6 && super.getCanSpawnHere();
    }

    @Override
    protected boolean canDespawn() {
        // Fadas com dono NUNCA dão despawn.
        if (this.isNoDespawnRequired()) return false;
        return this.myowner == null;
    }

    // --- Sincronização e NBT ---
    public int getFairyType() { return this.dataManager.get(FAIRY_TYPE); }
    public void setFairyType(int type) { this.dataManager.set(FAIRY_TYPE, type); }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setString("MyOwner", this.myowner == null ? "null" : this.myowner);
        compound.setInteger("FairyType", this.getFairyType()); // Nome corrigido
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.myowner = compound.getString("MyOwner");
        if (this.myowner.equals("null") || this.myowner.isEmpty()) {
            this.myowner = null;
        }
        // Lê pelo mesmo nome exato que foi salvo (corrigindo bug do original)
        if (compound.hasKey("FairyType")) {
            this.setFairyType(compound.getInteger("FairyType"));
        } else if (compound.hasKey("fairyType")) {
            // Suporte legado se você estiver importando mapa antigo
            this.setFairyType(compound.getInteger("fairyType"));
        }
    }

    // --- Configurações Físicas e Sons ---
    @Override public int getTotalArmorValue() { return 4; }
    @Override public boolean canBePushed() { return true; }
    @Override protected boolean canTriggerWalking() { return false; }
    @Override public boolean doesEntityNotTriggerPressurePlate() { return true; }
    @Override protected void updateFallState(double y, boolean onGroundIn) {}
    @Override public void fall(float distance, float damageMultiplier) {}

    @Override protected float getSoundVolume() { return 0.25F; }
    @Override protected float getSoundPitch() { return 1.7F; }
    @Override protected SoundEvent getAmbientSound() { return null; } // Retirei a explosão bizarra do decompilador
    @Override protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }

    @Override
    protected Item getDropItem() {
        // Precisa que você crie o ItemBlock para a CrystalTorch no OreSpawnMain
        return Item.getItemFromBlock(OreSpawnMain.CrystalTorch);
    }
}