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

public class BlockQuinoa extends BlockReed {
    
    // Caixa de colisão fina (estilo cana-de-açúcar/plantas altas)
    protected static final AxisAlignedBB QUINOA_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 1.0D, 0.8D);

    protected BlockQuinoa() {
        super();
        this.setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return QUINOA_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState stateDown = worldIn.getBlockState(pos.down());
        Block bid = stateDown.getBlock();

        // Pode ser colocada em terra, grama, farmland ou sobre outra planta de Quinoa
        return bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND || 
               bid == OreSpawnMain.MyQuinoaPlant1 || bid == OreSpawnMain.MyQuinoaPlant2 || 
               bid == OreSpawnMain.MyQuinoaPlant3 || bid == OreSpawnMain.MyQuinoaPlant4;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) return;

        int age = state.getValue(AGE);

        // Se a "idade" (tempo de crescimento) chegar ao limite, ela muda para o próximo bloco de estágio
        if (age >= 6) {
            if (this == OreSpawnMain.MyQuinoaPlant1) {
                worldIn.setBlockState(pos, OreSpawnMain.MyQuinoaPlant2.getDefaultState(), 2);
            } else if (this == OreSpawnMain.MyQuinoaPlant2) {
                worldIn.setBlockState(pos, OreSpawnMain.MyQuinoaPlant3.getDefaultState(), 2);
            } else if (this == OreSpawnMain.MyQuinoaPlant3) {
                worldIn.setBlockState(pos, OreSpawnMain.MyQuinoaPlant4.getDefaultState(), 2);
            }
        } else {
            // Caso contrário, apenas incrementa o metadado AGE (0-15)
            worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // No OreSpawn, a Quinoa (item) serve como semente e alimento
        return OreSpawnMain.MyQuinoa;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // Apenas o último estágio dropa a colheita completa (3 a 6 itens)
        if (this == OreSpawnMain.MyQuinoaPlant4) {
            return 3 + random.nextInt(3);
        }
        // Estágios anteriores dropam apenas 1 (a semente)
        return 1;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        // Pick Block (Botão do meio do rato)
        return new ItemStack(OreSpawnMain.MyQuinoa);
    }
}