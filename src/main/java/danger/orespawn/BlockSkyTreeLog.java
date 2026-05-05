package danger.orespawn;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSkyTreeLog extends Block {

    public BlockSkyTreeLog() {
        super(Material.WOOD);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!worldIn.isRemote) {
            // Inicia a destruição em cadeia
            // var7 no original era o raio de procura (3)
            this.breakRecursor(worldIn, pos, pos, 0);
        }
    }

    /**
     * Função recursiva que limpa os troncos da árvore.
     * @param world O mundo
     * @param currentPos A posição que está a ser verificada agora
     * @param originPos A posição original onde o jogador bateu
     * @param recursion Contador para evitar crashes (limite de profundidade)
     */
    private void breakRecursor(World world, BlockPos currentPos, BlockPos originPos, int recursion) {
        int radius = 3;
        // Limite de segurança para evitar StackOverflow em árvores infinitas
        if (recursion > 150) return; 

        // Procura num cubo de 3x3x3 ao redor do bloco atual
        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dy = -radius; dy <= radius; ++dy) {
                for (int dz = -radius; dz <= radius; ++dz) {
                    BlockPos targetPos = currentPos.add(dx, dy, dz);
                    
                    // Verifica se o bloco no alvo é este mesmo tipo de tronco
                    if (world.getBlockState(targetPos).getBlock() == this) {
                        // Não quebra o bloco se for a origem (já foi quebrado)
                        if (targetPos.equals(originPos) && recursion == 0) continue;

                        // Dropa o item, quebra o bloco e chama a função novamente para o vizinho
                        this.dropBlockAsItem(world, targetPos, world.getBlockState(targetPos), 0);
                        world.setBlockState(targetPos, Blocks.AIR.getDefaultState(), 2);
                        
                        this.breakRecursor(world, targetPos, originPos, recursion + 1);
                    }
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }
}