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
import net.minecraft.init.Items;
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

public class BlockAppleLeaves extends BlockLeaves {

    public BlockAppleLeaves() {
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
        // A lógica de drop principal das folhas é tratada via getDrops na 1.12.2,
        // mas a chance de cair maçãs mantivemos no updateTick ou aqui
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        
        if (rand.nextInt(25) == 1) {
            drops.add(new ItemStack(Items.APPLE));
        }
        if (rand.nextInt(500) == 2) {
            drops.add(new ItemStack(Items.GOLDEN_APPLE, 1, 0));
        }
        if (rand.nextInt(1000) == 3) {
            drops.add(new ItemStack(Items.GOLDEN_APPLE, 1, 1));
        }
        if (rand.nextInt(10000) == 4) {
            drops.add(new ItemStack(OreSpawnMain.MagicApple));
        }
    }

    @Override
    public int quantityDropped(Random random) {
        return 0; // Folhas geralmente não dropam a si mesmas
    }
    
    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            int chance = 20;
            
            // Dropa maçãs periodicamente
            if (worldIn.provider.getDimension() == OreSpawnMain.DimensionID4) {
                chance = 100;
            }
            
            BlockPos downPos = pos.down();
            if (worldIn.isAirBlock(downPos) && rand.nextInt(chance) == 3) {
                 if (rand.nextInt(25) == 1) {
                    spawnAsEntity(worldIn, downPos, new ItemStack(Items.APPLE));
                } else if (rand.nextInt(500) == 2) {
                    spawnAsEntity(worldIn, downPos, new ItemStack(Items.GOLDEN_APPLE, 1, 0));
                } else if (rand.nextInt(1000) == 3) {
                    spawnAsEntity(worldIn, downPos, new ItemStack(Items.GOLDEN_APPLE, 1, 1));
                } else if (rand.nextInt(10000) == 4) {
                    spawnAsEntity(worldIn, downPos, new ItemStack(OreSpawnMain.MagicApple));
                }
            }

            // Transformação em folhas assustadoras à noite na dimensão Utopia
            long t = worldIn.getWorldTime() % 24000L;
            if (t > 12000L && worldIn.provider.getDimension() == OreSpawnMain.DimensionID4) {
                worldIn.setBlockState(pos, OreSpawnMain.MyScaryLeaves.getDefaultState(), 3);
            }
            
            // Tratamento padrão de decaimento de folhas (se não estiver conectada a madeira)
            super.updateTick(worldIn, pos, state, rand);
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        // FastGraphicsLeaves é uma config antiga, geralmente delegamos pro cliente agora
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
        // Folhas geralmente usam CUTOUT_MIPPED para permitir transparência
        return Blocks.LEAVES.getDefaultState().isOpaqueCube() ? BlockRenderLayer.SOLID : BlockRenderLayer.CUTOUT_MIPPED;
    }

    // --- MÉTODOS OBRIGATÓRIOS DO BLOCKLEAVES NA 1.12.2 ---

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK; // Retorno padrão exigido
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
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
}