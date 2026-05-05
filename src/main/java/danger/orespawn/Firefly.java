package danger.orespawn;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class Firefly extends EntityAmbientCreature {

    private int my_blink;
    private int blinker = 0;
    private BlockPos currentFlightTarget = null;

    public Firefly(World worldIn) {
        super(worldIn);
        this.setSize(0.4F, 0.8F);
        
        // Define o tempo do piscar único de cada vagalume
        this.my_blink = 20 + this.rand.nextInt(20);
        
        // Evita afogamentos simples
        if (this.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) {
            ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
        
        // AmbientCreature não ataca, mas registrar previne crashes com código antigo
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
    }

    /**
     * Retorna a intensidade do brilho para o Renderer usar no glow.
     */
    public float getBlink() {
        if (this.blinker < this.my_blink / 2) {
            return 240.0F; // Acesso para brilhar no escuro (FullBright)
        }
        return 0.0F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        
        // Física flutuante de inseto
        this.motionY *= 0.6000000238418579D;
        
        // Controle de brilho
        this.blinker++;
        if (this.blinker > this.my_blink) {
            this.blinker = 0;
        }

        if (this.isNoDespawnRequired()) {
            return;
        }

        if (!this.world.isRemote) {
            long time = this.world.getWorldTime() % 24000L;
            
            // Se for de dia (tempo menor que 11000 ou maior que 23000), os vagalumes começam a morrer
            if (time < 11000L || time > 23000L) {
                if (this.rand.nextInt(500) == 0) {
                    this.setDead();
                }
            }
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();

        // Escolha de alvo para voo
        if (this.currentFlightTarget == null || this.world.rand.nextInt(40) == 0 || 
            this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 2.0D) {
            
            int keep_trying = 25;
            while (keep_trying-- > 0) {
                BlockPos target = new BlockPos(
                        this.posX + this.rand.nextInt(8) - 4, 
                        this.posY + this.rand.nextInt(6) - 2, 
                        this.posZ + this.rand.nextInt(8) - 4
                );
                
                if (this.world.isAirBlock(target)) {
                    this.currentFlightTarget = target;
                    break;
                }
            }
        }

        // Caso ainda seja nulo, evita crash
        if (this.currentFlightTarget == null) return;

        // Voo até o alvo
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

    @Override
    public boolean getCanSpawnHere() {
        // Permitido spawnar na Chaos Dimension a qualquer momento
        if (this.world.provider.getDimension() != OreSpawnMain.DimensionID4) {
            if (this.world.isDaytime()) return false;
            if (this.posY < 50.0D) return false;
        }

        BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        if (!this.world.isAirBlock(pos)) return false;

        // Limita para que no máximo 10 vagalumes nasçam aglomerados (evita lag)
        List<Firefly> buddies = this.world.getEntitiesWithinAABB(Firefly.class, this.getEntityBoundingBox().grow(20.0D, 8.0D, 20.0D));
        if (buddies.size() > 10) {
            return false;
        }

        return super.getCanSpawnHere();
    }

    @Override
    protected boolean canDespawn() {
        if (!this.world.isDaytime()) {
            return false; // Não some à noite a menos que fique muito longe
        }
        return !this.isNoDespawnRequired();
    }

    @Override
    protected Item getDropItem() {
        // Vagalumes dropam a tocha que clareia o dobro que uma tocha normal!
        return Item.getItemFromBlock(OreSpawnMain.ExtremeTorch);
    }

    // Comportamentos físicos e ignorar placas de pressão
    @Override public boolean canBePushed() { return true; }
    @Override protected boolean canTriggerWalking() { return false; }
    @Override public boolean doesEntityNotTriggerPressurePlate() { return true; }
    @Override protected void updateFallState(double y, boolean onGroundIn) {}
    @Override public void fall(float distance, float damageMultiplier) {}

    // Sons (Vagalumes são silenciosos)
    @Override protected float getSoundVolume() { return 0.0F; }
    @Override protected float getSoundPitch() { return 1.0F; }
    @Override protected SoundEvent getAmbientSound() { return null; }
    @Override protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }
}