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

public class BlockLettuce extends BlockReed {
    
    // Caixa de colisão da alface (baseada no setBlockBounds original)
    protected static final AxisAlignedBB LETTUCE_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D);

    protected BlockLettuce() {
        super();
        this.setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return LETTUCE_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        // Checa o bloco abaixo
        IBlockState stateDown = worldIn.getBlockState(pos.down());
        Block bid = stateDown.getBlock();

        // Pode ser colocada em terra, grama, farmlands ou em cima de outra "fase" da alface
        return bid == OreSpawnMain.MyLettucePlant1 || 
               bid == OreSpawnMain.MyLettucePlant2 || 
               bid == OreSpawnMain.MyLettucePlant3 || 
               bid == OreSpawnMain.MyLettucePlant4 || 
               bid == Blocks.GRASS || 
               bid == Blocks.DIRT || 
               bid == Blocks.FARMLAND;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) return;

        // O sistema de Reed usa o meta para o tempo de crescimento (0-15)
        int age = state.getValue(AGE);

        if (age >= 4) {
            // Se atingiu o "estágio 4" de tempo, ela evolui para o PRÓXIMO BLOCO
            if (this == OreSpawnMain.MyLettucePlant1) {
                worldIn.setBlockState(pos, OreSpawnMain.MyLettucePlant2.getDefaultState(), 2);
            } else if (this == OreSpawnMain.MyLettucePlant2) {
                worldIn.setBlockState(pos, OreSpawnMain.MyLettucePlant3.getDefaultState(), 2);
            } else if (this == OreSpawnMain.MyLettucePlant3) {
                worldIn.setBlockState(pos, OreSpawnMain.MyLettucePlant4.getDefaultState(), 2);
            }
        } else {
            // Caso contrário, apenas incrementa a idade interna (meta)
            worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // Só dropa a Alface (item comestível)
        return OreSpawnMain.MyLettuce;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // Se for o último estágio (Plant 4), dropa 2 a 4 alfaces
        if (this == OreSpawnMain.MyLettucePlant4) {
            return 2 + random.nextInt(3);
        }
        // Se quebrar antes, dropa 1 (as sementes no OreSpawn costumam ser a própria alface ou o item MyLettuce)
        return 1;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        // O "Pick Block" (botão do meio do mouse) retorna o item base
        return new ItemStack(OreSpawnMain.MyLettuce);
    }

    // Nota: O método neighborChanged não é estritamente necessário aqui porque o BlockReed 
    // já lida com a queda se o bloco de baixo sumir.
}