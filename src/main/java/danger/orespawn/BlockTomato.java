package danger.orespawn;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTomato extends BlockReed {
    
    // Bounding box para o tomate (baseada no original de 0.375f)
    protected static final AxisAlignedBB TOMATO_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

    protected BlockTomato() {
        super();
        this.setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return TOMATO_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState groundState = worldIn.getBlockState(pos.down());
        Block ground = groundState.getBlock();
        
        // Pode ser plantado em terra, grama, farmland ou em cima de outra fase da planta
        return ground == Blocks.GRASS || ground == Blocks.DIRT || ground == Blocks.FARMLAND || 
               ground == OreSpawnMain.MyTomatoPlant1 || ground == OreSpawnMain.MyTomatoPlant2 || 
               ground == OreSpawnMain.MyTomatoPlant3 || ground == OreSpawnMain.MyTomatoPlant4;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) return;

        // O BlockReed usa a propriedade AGE (0-15) para o tempo de crescimento
        int age = state.getValue(AGE);

        if (age >= 7) {
            // Se atingiu o tempo de crescer, evoluímos para o próximo bloco (estágio)
            if (this == OreSpawnMain.MyTomatoPlant1) {
                worldIn.setBlockState(pos, OreSpawnMain.MyTomatoPlant2.getDefaultState(), 2);
            } else if (this == OreSpawnMain.MyTomatoPlant2) {
                worldIn.setBlockState(pos, OreSpawnMain.MyTomatoPlant3.getDefaultState(), 2);
            } else if (this == OreSpawnMain.MyTomatoPlant3) {
                worldIn.setBlockState(pos, OreSpawnMain.MyTomatoPlant4.getDefaultState(), 2);
            }
        } else {
            // Apenas aumenta a "idade" interna do bloco atual
            worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return OreSpawnMain.MyTomato;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // Só dropa tomates extras se for o último estágio (Plant 4)
        if (this == OreSpawnMain.MyTomatoPlant4) {
            return 2 + random.nextInt(4);
        }
        return 1;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(OreSpawnMain.MyTomato);
    }
}