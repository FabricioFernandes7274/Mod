package danger.orespawn;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class CrystalGrass extends Block {

    protected CrystalGrass(float hardness, float resistance) {
        super(Material.GRASS);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        // Permite que flores e mudas cresçam neste bloco
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        // Mantendo a lógica original baseada na dimensão, 
        // embora na 1.12.2 o ideal seja que um bloco seja ou não opaco fixamente.
        return OreSpawnMain.current_dimension == OreSpawnMain.DimensionID5;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        // Substitui o renderAsNormalBlock
        return OreSpawnMain.current_dimension == OreSpawnMain.DimensionID5;
    }
}