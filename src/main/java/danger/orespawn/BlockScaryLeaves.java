package danger.orespawn;

import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockScaryLeaves extends BlockLeaves {

    public BlockScaryLeaves() {
        super();
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // Lógica de drop original: Cherry dropa Cherry, Peach dropa Peach
        if (this == OreSpawnMain.MyCherryLeaves) {
            return OreSpawnMain.MyCherry;
        }
        if (this == OreSpawnMain.MyPeachLeaves) {
            return OreSpawnMain.MyPeach;
        }
        // Se não for nenhum desses, dropa a muda (sapling) padrão do Minecraft
        return Item.getItemFromBlock(Blocks.SAPLING);
    }

    @Override
    protected int getSaplingDropChance(IBlockState state) {
        return 20; // 5% de chance de dropar muda
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        // Na 1.12.2, as folhas não são opacas se o gráfico estiver no "Fancy"
        return !Blocks.LEAVES.isOpaqueCube(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        // Isso permite a transparência (recorte) das folhas
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        // Permite coletar as folhas com tesoura
        return NonNullList.withSize(1, new ItemStack(this));
    }

    @Override
    public EnumType getWoodType(int meta) {
        // Obrigatório por estender BlockLeaves, mas como o OreSpawn usa blocos separados,
        // podemos retornar OAK (carvalho) como padrão.
        return EnumType.OAK;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, net.minecraft.util.EnumFacing side) {
        // Melhora a performance escondendo faces internas se os gráficos forem "Fast"
        return Blocks.LEAVES.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}