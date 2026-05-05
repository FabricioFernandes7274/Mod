package danger.orespawn;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CliffRacer extends EntityAnimal {
    
    private BlockPos currentFlightTarget = null;

    public CliffRacer(World worldIn) {
        super(worldIn);
        this.setSize(0.75F, 0.5F);
        this.experienceValue = 5;
        this.isImmuneToFire = false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33D);
        
        // Registar o atributo de dano antes de lhe dar um valor
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int mygetMaxHealth() {
        return 5;
    }

    @Override
    public boolean getCanSpawnHere() {
        // Só faz spawn da camada 50 para cima
        return this.posY >= 50.0D && super.getCanSpawnHere();
    }

    // --- Sistema de Voo Customizado ---

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Abranda a gravidade para criar um efeito de voo natural
        this.motionY *= 0.6D; 
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        // Usa o Raytrace para garantir que não há blocos entre o pássaro e o seu alvo
        return this.world.rayTraceBlocks(
            new Vec3d(this.posX, this.posY + 0.75D, this.posZ), 
            new Vec3d(pX, pY, pZ), 
            false
        ) == null;
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        
        if (this.isDead) return;

        // Se o alvo não existe ou se já estamos perto dele (ou aleatoriamente a cada 300 ticks), procurar um novo.
        if (this.currentFlightTarget == null || this.world.rand.nextInt(300) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 4.0D) {
            int keep_trying = 50;
            
            while (keep_trying > 0) {
                int xdir = this.world.rand.nextInt(10) + 5;
                int zdir = this.world.rand.nextInt(10) + 5;
                
                if (this.world.rand.nextBoolean()) xdir = -xdir;
                if (this.world.rand.nextBoolean()) zdir = -zdir;
                
                int ydir = this.world.rand.nextInt(11) - 5;

                BlockPos target = new BlockPos((int) this.posX + xdir, (int) this.posY + ydir, (int) this.posZ + zdir);
                
                // Se for ar e estiver à vista, marca como o novo destino de voo
                if (this.world.isAirBlock(target) && this.canSeeTarget(target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D)) {
                    this.currentFlightTarget = target;
                    break;
                }
                keep_trying--;
            }
        }

        // Navegar até ao alvo em linha reta (em 3D)
        if (this.currentFlightTarget != null) {
            double dx = (double) this.currentFlightTarget.getX() + 0.4D - this.posX;
            double dy = (double) this.currentFlightTarget.getY() + 0.1D - this.posY;
            double dz = (double) this.currentFlightTarget.getZ() + 0.4D - this.posZ;
            
            this.motionX += (Math.signum(dx) * 0.4D - this.motionX) * 0.3D;
            this.motionY += (Math.signum(dy) * 0.7D - this.motionY) * 0.2D;
            this.motionZ += (Math.signum(dz) * 0.4D - this.motionZ) * 0.3D;
            
            float targetYaw = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
            float yawDiff = MathHelper.wrapDegrees(targetYaw - this.rotationYaw);
            
            this.moveForward = 0.75F;
            this.rotationYaw += yawDiff / 6.0F;
        }
    }

    // --- Imunidade a Queda ---

    @Override
    protected void fall(float distance, float damageMultiplier) {
        // Ignora dano de queda
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
        // Ignora os cálculos de quebra de blocos ao cair (como farmland)
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true; // Pássaros não deveriam ativar pressure plates facilmente
    }

    // --- Sons e Drops ---

    @Override
    protected SoundEvent getAmbientSound() { 
        return SoundEvents.ENTITY_GENERIC_EXPLODE; 
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
        return 0.45F;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        // Movido do antigo getDropItem para suportar drops aleatórios da forma correta na 1.12.2
        int chance = this.world.rand.nextInt(8);
        if (chance == 0) {
            this.dropItem(Items.CHICKEN, 1);
        } else if (chance == 1) {
            this.dropItem(OreSpawnMain.UraniumNugget, 1);
        } else if (chance == 2) {
            this.dropItem(OreSpawnMain.TitaniumNugget, 1);
        }
    }

    // --- Reprodução ---

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null; // O Cliff Racer é passivo mas não se reproduz como uma vaca ou ovelha
    }
}