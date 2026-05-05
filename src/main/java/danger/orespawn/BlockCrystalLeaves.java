package danger.orespawn;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystalLeaves extends BlockLeaves {

    public BlockCrystalLeaves() {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setTickRandomly(true);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, 0));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        
        if (rand.nextInt(100) == 1) {
            drops.add(new ItemStack(OreSpawnMain.MyCrystalApple));
        }
        
        if (rand.nextInt(50) == 1) {
            if (this == OreSpawnMain.MyCrystalLeaves) {
                drops.add(new ItemStack(OreSpawnMain.MyCrystalPlant));
            } else if (this == OreSpawnMain.MyCrystalLeaves2) {
                drops.add(new ItemStack(OreSpawnMain.MyCrystalPlant2));
            } else if (this == OreSpawnMain.MyCrystalLeaves3) {
                drops.add(new ItemStack(OreSpawnMain.MyCrystalPlant3));
            }
        }
    }

    @Override
    public int quantityDropped(Random random) {
        return 0; // Folhas geralmente dropam a si mesmas apenas com Silk Touch
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            int chance = 20;
            
            if (worldIn.provider.getDimension() == OreSpawnMain.DimensionID4) {
                chance = 100;
            }
            
            BlockPos downPos = pos.down();
            
            // Lógica de "soltar" itens da árvore (maçãs e plantas de cristal)
            if (worldIn.isAirBlock(downPos) && rand.nextInt(chance) == 3) {
                if (rand.nextInt(100) == 1) {
                    spawnAsEntity(worldIn, downPos, new ItemStack(OreSpawnMain.MyCrystalApple));
                }
                if (rand.nextInt(50) == 1) {
                    if (this == OreSpawnMain.MyCrystalLeaves) {
                        spawnAsEntity(worldIn, downPos, new ItemStack(OreSpawnMain.MyCrystalPlant));
                    } else if (this == OreSpawnMain.MyCrystalLeaves2) {
                        spawnAsEntity(worldIn, downPos, new ItemStack(OreSpawnMain.MyCrystalPlant2));
                    } else if (this == OreSpawnMain.MyCrystalLeaves3) {
                        spawnAsEntity(worldIn, downPos, new ItemStack(OreSpawnMain.MyCrystalPlant3));
                    }
                }
            }

            // O `super.updateTick` lida com a verificação de decaimento padrão das folhas
            super.updateTick(worldIn, pos, state, rand);
        }
    }

    // --- RENDERIZAÇÃO E GRÁFICOS ---

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !Blocks.LEAVES.getDefaultState().isOpaqueCube();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return !this.isOpaqueCube(blockState) || super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return Blocks.LEAVES.getDefaultState().isOpaqueCube() ? BlockRenderLayer.SOLID : BlockRenderLayer.CUTOUT_MIPPED;
    }

    // --- MÉTODOS OBRIGATÓRIOS DO BLOCKLEAVES NA 1.12.2 ---

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK; // Valor padrão exigido pelo BlockLeaves
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                   .withProperty(DECAYABLE, (meta & 4) == 0)
                   .withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!state.getValue(DECAYABLE)) {
            i |= 4;
        }
        if (state.getValue(CHECK_DECAY)) {
            i |= 8;
        }
        return i;
    }
    
    @Override
    public List<ItemStack> onSheared(World world, BlockPos pos, int fortune) {
        return java.util.Arrays.asList(new ItemStack(this, 1, 0));
    }
    
    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }
}