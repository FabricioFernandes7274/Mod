package danger.orespawn;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystalTorch extends BlockTorch {

    public BlockCrystalTorch() {
        super();
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        // A direção padrão "UP" já é definida no construtor do BlockTorch nativo
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(4) != 1) {
            return;
        }

        EnumFacing enumfacing = stateIn.getValue(FACING);
        double x = (double) pos.getX() + 0.5D;
        double y = (double) pos.getY() + 0.7D;
        double z = (double) pos.getZ() + 0.5D;
        
        // Offsets originais do OreSpawn
        double offsetVert = 0.213D;
        double offsetHoriz = 0.271D;

        // Velocidades aleatórias mágicas originais preservadas
        double sparkVx = (rand.nextFloat() - rand.nextFloat()) / 8.0D;
        double sparkVy = rand.nextFloat() / 8.0D;
        double sparkVz = (rand.nextFloat() - rand.nextFloat()) / 8.0D;

        double flameVx = (rand.nextFloat() - rand.nextFloat()) / 60.0D;
        double flameVy = rand.nextFloat() / 10.0D;
        double flameVz = (rand.nextFloat() - rand.nextFloat()) / 60.0D;

        // Usamos matemática direcional em vez de IFs longos para a emissão de partículas!
        if (enumfacing.getAxis().isHorizontal()) {
            EnumFacing opposite = enumfacing.getOpposite();
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, 
                x + offsetHoriz * opposite.getFrontOffsetX(), 
                y + offsetVert, 
                z + offsetHoriz * opposite.getFrontOffsetZ(), 
                sparkVx, sparkVy, sparkVz);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, 
                x + offsetHoriz * opposite.getFrontOffsetX(), 
                y + offsetVert, 
                z + offsetHoriz * opposite.getFrontOffsetZ(), 
                flameVx, flameVy, flameVz);
        } else {
            // Partícula para a tocha virada para cima
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x, y, z, sparkVx, sparkVy, sparkVz);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, flameVx, flameVy, flameVz);
        }
    }

    private boolean isCrystalBlock(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();
        return block == OreSpawnMain.CrystalStone || 
               block == OreSpawnMain.CrystalGrass || 
               block == OreSpawnMain.MyCrystalTreeLog || 
               block == OreSpawnMain.CrystalPlanksBlock;
    }

    private boolean canPlaceTorchOnCustom(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos targetPos = pos.offset(facing.getOpposite());
        
        // Checagem customizada: permite grudar diretamente nos blocos de cristal
        if (isCrystalBlock(worldIn, targetPos)) {
            return true;
        }
        
        // Fallback para a lógica baunilha
        IBlockState targetState = worldIn.getBlockState(targetPos);
        if (facing == EnumFacing.UP) {
            return targetState.getBlock().canPlaceTorchOnTop(targetState, worldIn, targetPos);
        } else if (facing != EnumFacing.DOWN) {
            return targetState.getBlockFaceShape(worldIn, targetPos, facing) == net.minecraft.block.state.BlockFaceShape.SOLID;
        }
        
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (EnumFacing enumfacing : FACING.getAllowedValues()) {
            if (this.canPlaceTorchOnCustom(worldIn, pos, enumfacing)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (this.canPlaceTorchOnCustom(worldIn, pos, facing)) {
            return this.getDefaultState().withProperty(FACING, facing);
        }
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (this.canPlaceTorchOnCustom(worldIn, pos, enumfacing)) {
                return this.getDefaultState().withProperty(FACING, enumfacing);
            }
        }
        return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.checkForDrop(worldIn, pos, state);
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canPlaceTorchOnCustom(worldIn, pos, state.getValue(FACING))) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
        return true;
    }
}