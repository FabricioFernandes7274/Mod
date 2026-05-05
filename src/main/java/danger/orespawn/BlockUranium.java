package danger.orespawn;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockUranium extends Block {

    public BlockUranium() {
        super(Material.ROCK);
        this.setHardness(5.0f);
        this.setResistance(5.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        // Gera partículas ao redor do bloco (Fogo e Fumaça)
        for (int i = 0; i < 4; ++i) {
            double x = (double)pos.getX() + rand.nextFloat();
            double y = (double)pos.getY() + rand.nextFloat();
            double z = (double)pos.getZ() + rand.nextFloat();
            
            // Velocidade aleatória para as partículas
            double vx = (rand.nextFloat() - 0.5D) * 0.5D;
            double vy = (rand.nextFloat() - 0.5D) * 0.5D;
            double vz = (rand.nextFloat() - 0.5D) * 0.5D;

            int type = rand.nextInt(3);
            if (type == 0) {
                worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, vx, vy, vz);
            } else if (type == 1) {
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, vx, vy, vz);
            }
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }
}