package danger.orespawn;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTitanium extends Block {

    public BlockTitanium() {
        super(Material.ROCK);
        this.setHardness(5.0f);
        this.setResistance(5.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        // Se o OreSpawnMain tiver uma config para desativar partículas, você checaria aqui
        // No original ele apenas spawnava se encontrasse uma face livre (ar)

        for (int l = 0; l < 4; ++l) {
            double x = (double)pos.getX() + rand.nextFloat();
            double y = (double)pos.getY() + rand.nextFloat();
            double z = (double)pos.getZ() + rand.nextFloat();
            double speedX = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double speedY = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double speedZ = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            
            // Escolhe aleatoriamente entre fumaça e fogo (conforme o original)
            int type = rand.nextInt(3);
            if (type == 0) {
                worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, speedX, speedY, speedZ);
            } else if (type == 1) {
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, speedX, speedY, speedZ);
            }
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true; // Blocos de titânio costumam ser sólidos
    }
}