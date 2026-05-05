package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityLunaMoth extends EntityButterfly {

    public int moth_type = this.rand.nextInt(4);
    private BlockPos currentFlightTarget = null;

    public EntityLunaMoth(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.5f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Mantém a física de flutuação da borboleta
        this.motionY *= 0.6D;
    }

    /**
     * Busca por fontes de luz próximas (Tochas)
     * Substitui o método scan_it complexo por uma busca radial mais eficiente.
     */
    private BlockPos findLightSource() {
        BlockPos myPos = new BlockPos(this.posX, this.posY, this.posZ);
        int radius = 10;
        
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos targetPos = myPos.add(x, y, z);
                    Block block = this.world.getBlockState(targetPos).getBlock();
                    
                    if (block == Blocks.TORCH || block == OreSpawnMain.ExtremeTorch) {
                        return targetPos;
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;

        // Lógica de Voo Aleatório (Padrão Butterfly)
        if (this.currentFlightTarget == null || this.world.rand.nextInt(100) == 0 || 
            this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 4.0D) {
            
            // Se estiver de noite, tenta buscar uma tocha antes de voar aleatoriamente
            if (!this.world.isDaytime() && this.world.rand.nextInt(5) == 0) {
                BlockPos light = findLightSource();
                if (light != null) {
                    this.currentFlightTarget = light.up(); // Voa um bloco acima da tocha
                }
            }

            // Se não achou luz, voa para um lugar aleatório
            if (this.currentFlightTarget == null || this.world.rand.nextInt(50) == 0) {
                this.currentFlightTarget = new BlockPos(
                    (int)this.posX + this.rand.nextInt(10) - 5,
                    (int)this.posY + this.rand.nextInt(6) - 2,
                    (int)this.posZ + this.rand.nextInt(10) - 5
                );
            }
        }

        // Movimentação física em direção ao alvo
        double dx = (double)this.currentFlightTarget.getX() + 0.5D - this.posX;
        double dy = (double)this.currentFlightTarget.getY() + 0.1D - this.posY;
        double dz = (double)this.currentFlightTarget.getZ() + 0.5D - this.posZ;

        this.motionX += (Math.signum(dx) * 0.5D - this.motionX) * 0.1D;
        this.motionY += (Math.signum(dy) * 0.68D - this.motionY) * 0.1D;
        this.motionZ += (Math.signum(dz) * 0.5D - this.motionZ) * 0.1D;

        // Rotação visual
        float angle = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
        float wrap = net.minecraft.util.math.MathHelper.wrapDegrees(angle - this.rotationYaw);
        this.moveForward = 0.75F;
        this.rotationYaw += wrap;
    }

    @Override
    public boolean getCanSpawnHere() {
        // Mariposas só nascem à noite ou na dimensão Utopia
        if (this.world.isDaytime() && this.world.provider.getDimension() != 6) {
            return false;
        }
        
        BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        return this.world.getBlockState(pos).getBlock() == Blocks.AIR && this.posY >= 50.0D;
    }

    // Métodos herdados que não fazem nada (padrão Butterfly)
    @Override protected void updateFallState(double y, boolean onGroundIn) {}
    @Override public void fall(float distance, float damageMultiplier) {}
}