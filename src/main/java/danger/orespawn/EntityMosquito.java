package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMosquito extends EntityAmbientCreature {

    private BlockPos currentFlightTarget;

    public EntityMosquito(World worldIn) {
        super(worldIn);
        this.setSize(0.2F, 0.2F);
        // Evita que o mosquito morra afogado facilmente
        if (this.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) {
            ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
        
        // AmbientCreature não ataca, mas o OreSpawn costumava registrar para evitar Crash
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Física de flutuação de inseto (reduz a queda)
        this.motionY *= 0.6D;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();

        // Escolha de um novo alvo de voo
        if (this.currentFlightTarget == null || this.world.rand.nextInt(20) == 0 || 
            this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 3.0D) {
            
            // 25% de chance de focar na cabeça de um jogador próximo
            if (this.world.rand.nextInt(4) == 0) {
                EntityPlayer target = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 10.0D, false);
                if (target != null) {
                    // Voa 2 blocos acima dos pés do jogador (perto do rosto)
                    this.currentFlightTarget = new BlockPos(target.posX, target.posY + 2.0D, target.posZ);
                } else {
                    this.pickRandomFlightTarget();
                }
            } else {
                this.pickRandomFlightTarget();
            }
        }

        // Movimentação em direção ao BlockPos
        double dx = (double)this.currentFlightTarget.getX() + 0.5D - this.posX;
        double dy = (double)this.currentFlightTarget.getY() + 0.1D - this.posY;
        double dz = (double)this.currentFlightTarget.getZ() + 0.5D - this.posZ;

        this.motionX += (Math.signum(dx) * 0.5D - this.motionX) * 0.1D;
        this.motionY += (Math.signum(dy) * 0.7D - this.motionY) * 0.1D;
        this.motionZ += (Math.signum(dz) * 0.5D - this.motionZ) * 0.1D;

        float angle = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float wrap = MathHelper.wrapDegrees(angle - this.rotationYaw);
        this.moveForward = 0.3F;
        this.rotationYaw += wrap;
    }

    /**
     * Tenta encontrar um bloco de AR próximo para voar.
     */
    private void pickRandomFlightTarget() {
        int keep_trying = 50;
        Block block = Blocks.STONE;
        
        while (block != Blocks.AIR && keep_trying > 0) {
            this.currentFlightTarget = new BlockPos(
                (int)this.posX + this.rand.nextInt(12) - 6,
                (int)this.posY + this.rand.nextInt(6) - 2,
                (int)this.posZ + this.rand.nextInt(12) - 6
            );
            block = this.world.getBlockState(this.currentFlightTarget).getBlock();
            keep_trying--;
        }
    }

    // Comportamentos de Voo e Placa de Pressão
    @Override protected boolean canTriggerWalking() { return false; }
    @Override public boolean doesEntityNotTriggerPressurePlate() { return true; }
    @Override protected void updateFallState(double y, boolean onGroundIn) {}
    @Override public void fall(float distance, float damageMultiplier) {}
    @Override protected boolean canDespawn() { return !this.isNoDespawnRequired(); }

    @Override
    public boolean getCanSpawnHere() {
        // Mosquitos do OreSpawn podiam nascer em qualquer lugar
        return true;
    }

    // Sons e Sons do OreSpawn
    @Override protected float getSoundVolume() { return 0.4F; }
    @Override protected float getSoundPitch() { return 1.5F; }
    @Override protected SoundEvent getHurtSound(DamageSource ds) { return SoundEvents.ENTITY_GENERIC_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }
    @Override protected SoundEvent getAmbientSound() { 
        // O original tocava "EXPLODE" como teste, 
        // Você pode trocar por um som customizado de zumbido no futuro.
        return null; 
    }
}