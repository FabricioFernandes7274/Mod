package danger.orespawn;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreeperRepellent extends BlockTorch {

    public CreeperRepellent() {
        super();
        this.setCreativeTab(CreativeTabs.REDSTONE);
        this.setTickRandomly(true);
    }

    @Override
    public int tickRate(World worldIn) {
        return 10;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            this.findSomethingToRepell(worldIn, pos);
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
        }
    }

    private void findSomethingToRepell(World worldIn, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        AxisAlignedBB bb = new AxisAlignedBB(x - 20.0D, y - 10.0D, z - 20.0D, x + 20.0D, y + 10.0D, z + 20.0D);
        List<EntityLivingBase> list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        
        for (EntityLivingBase entity : list) {
            if (entity instanceof EntityCreeper || entity instanceof EntityAnt || entity instanceof PurplePower) {
                
                // Se for o PurplePower, verificar o tipo
                if (entity instanceof PurplePower && ((PurplePower) entity).getPurpleType() == 10) {
                    continue; 
                }

                double dx = entity.posX - (double) x;
                double dy = entity.posY - (double) y;
                double dz = entity.posZ - (double) z;
                
                double distanceSq = dx * dx + dy * dy + dz * dz;
                double distance = Math.sqrt(distanceSq);
                
                double repelForce = 20.0D - distance;
                if (repelForce > 20.0D) repelForce = 20.0D;
                if (repelForce < 0.0D) repelForce = 0.0D;
                
                repelForce *= 0.4D; // Diminui a força para que a entidade não voe para o espaço

                // Calcula o ângulo em que deve ser empurrado
                double angle = Math.atan2(dx, dz);
                
                entity.motionX += repelForce * Math.sin(angle);
                entity.motionZ += repelForce * Math.cos(angle);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        EnumFacing enumfacing = stateIn.getValue(FACING);
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;
        double d3 = 0.22D;
        double d4 = 0.27D;

        if (enumfacing.getAxis().isHorizontal()) {
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * (double) enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * (double) enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d4 * (double) enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
        } else {
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}