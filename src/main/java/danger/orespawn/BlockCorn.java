package danger.orespawn;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCorn extends BlockReed {

    public BlockCorn() {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
        this.disableStats();
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        Block bid = worldIn.getBlockState(pos.down()).getBlock();
        if (bid == Blocks.AIR) {
            return false;
        }
        return bid == OreSpawnMain.MyCornPlant1 || bid == OreSpawnMain.MyCornPlant2 || 
               bid == OreSpawnMain.MyCornPlant3 || bid == OreSpawnMain.MyCornPlant4 || 
               bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) {
            return;
        }

        // Apenas as plantas 1 e 2 continuam crescendo ativamente
        if (this != OreSpawnMain.MyCornPlant1 && this != OreSpawnMain.MyCornPlant2) {
            return;
        }

        int age = state.getValue(AGE);
        
        // Mantendo o comportamento original do mod onde a altura alvo era recalculada devido à limitação de metadados
        int myMaxHeight = 4 + OreSpawnMain.OreSpawnRand.nextInt(4);
        int currentHeight = 1;
        boolean dontGrow = false;

        if (worldIn.isAirBlock(pos.up())) {
            // Calcula a altura atual do pé de milho verificando os blocos abaixo
            for (int i = 1; i < 10; ++i) {
                Block bid = worldIn.getBlockState(pos.down(i)).getBlock();
                if (bid == OreSpawnMain.MyCornPlant1 || bid == OreSpawnMain.MyCornPlant2 || 
                    bid == OreSpawnMain.MyCornPlant3 || bid == OreSpawnMain.MyCornPlant4) {
                    
                    currentHeight++;
                    // Se encontrar partes maduras na base, interrompe o crescimento
                    if (bid == OreSpawnMain.MyCornPlant3 || bid == OreSpawnMain.MyCornPlant4) {
                        dontGrow = true;
                    }
                } else {
                    break; // Acabou o pé de milho
                }
            }

            if (dontGrow) {
                myMaxHeight = currentHeight;
            }

            // Lógica de crescimento baseada na idade (AGE) e altura alvo
            if (age >= 6 - (myMaxHeight / 3)) {
                if (currentHeight < myMaxHeight) {
                    // Cresce um bloco para cima
                    worldIn.setBlockState(pos.up(), OreSpawnMain.MyCornPlant1.getDefaultState(), 2);
                    worldIn.setBlockState(pos, OreSpawnMain.MyCornPlant2.getDefaultState(), 2);
                } else {
                    // Atingiu altura máxima, inicia a maturação da espiga
                    for (int i = 1; i < myMaxHeight - 1; ++i) {
                        Block bid = worldIn.getBlockState(pos.down(i)).getBlock();
                        if (bid == OreSpawnMain.MyCornPlant2) {
                            worldIn.setBlockState(pos.down(i), OreSpawnMain.MyCornPlant3.getDefaultState(), 2);
                        } else if (bid == OreSpawnMain.MyCornPlant3) {
                            worldIn.setBlockState(pos.down(i), OreSpawnMain.MyCornPlant4.getDefaultState(), 2);
                        }
                    }
                    worldIn.setBlockState(pos, state.withProperty(AGE, 0), 2);
                }
            } else {
                // Incrementa a "idade" do milho antes de crescer
                // Evitamos crash checando se age < 15, pois o PropertyInteger vai de 0 a 15
                if (age < 15) {
                    worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return OreSpawnMain.MyCornCob;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        // Função chamada quando o jogador clica com o botão do meio no bloco (Pick Block)
        return new ItemStack(OreSpawnMain.MyCornCob);
    }

    @Override
    public int quantityDropped(Random random) {
        // Apenas a planta madura final dropa milho em abundância
        if (this == OreSpawnMain.MyCornPlant4) {
            return 1 + random.nextInt(2);
        }
        return 0; 
    }
}