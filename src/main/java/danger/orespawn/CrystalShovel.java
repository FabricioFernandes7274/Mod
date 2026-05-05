package danger.orespawn;

import java.util.Set;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class CrystalShovel extends ItemSpade {

    // Lista de blocos extras onde a pá é eficiente (além dos padrões do Minecraft)
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(
        Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, 
        Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, 
        Blocks.SNOW_LAYER, Blocks.SOUL_SAND, OreSpawnMain.CrystalGrass
    );

    public CrystalShovel(ToolMaterial material) {
        // Na 1.12.2: material, e os blocos efetivos
        super(material);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public boolean canHarvestBlock(net.minecraft.block.state.IBlockState state) {
        Block block = state.getBlock();
        // Mantendo a lógica de colheita de neve, mas permitindo o padrão da pá
        if (block == Blocks.SNOW_LAYER || block == Blocks.SNOW) {
            return true;
        }
        return super.canHarvestBlock(state);
    }
    
    // Se quiseres garantir que ela seja ultra eficiente no CrystalGrass especificamente:
    @Override
    public float getDestroySpeed(ItemStack stack, net.minecraft.block.state.IBlockState state) {
        if (state.getBlock() == OreSpawnMain.CrystalGrass) {
            return this.efficiency;
        }
        return super.getDestroySpeed(stack, state);
    }
}