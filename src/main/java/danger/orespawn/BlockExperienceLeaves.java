package danger.orespawn;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockExperienceLeaves extends BlockLeaves {

    public BlockExperienceLeaves() {
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR; // Não dropa nada naturalmente, como no original
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            long time = worldIn.getWorldTime() % 24000L;
            
            // Só funciona no meio da noite (14000 a 22000)
            if (time >= 14000L && time <= 22000L) {
                
                // Chance de dropar um frasco de XP dois blocos ACIMA
                if (rand.nextInt(65) == 1 && worldIn.isAirBlock(pos.up())) {
                    spawnAsEntity(worldIn, pos.up(2), new ItemStack(Items.EXPERIENCE_BOTTLE));
                }
                
                // Chance de atirar um frasco de XP para BAIXO
                if (rand.nextInt(75) == 1 && worldIn.isAirBlock(pos.down())) {
                    EntityExpBottle expBottle = new EntityExpBottle(worldIn, pos.getX() + 0.5D, pos.getY() - 0.5D, pos.getZ() + 0.5D);
                    
                    // Cálculo de direção aleatória preservado do mod original
                    double dirX = (rand.nextFloat() - rand.nextFloat()) / 2.0F;
                    double dirY = -0.1D;
                    double dirZ = (rand.nextFloat() - rand.nextFloat()) / 2.0F;
                    
                    // O método antigo setThrowableHeading agora é shoot
                    expBottle.shoot(dirX, dirY, dirZ, 0.4F, 5.0F);
                    worldIn.spawnEntity(expBottle);
                }
            }
            
            // Permite que o BlockLeaves nativo lide com o decaimento padrão da folha (se a árvore for quebrada)
            super.updateTick(worldIn, pos, state, rand);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        long time = worldIn.getWorldTime() % 24000L;
        
        // As partículas começam um pouco antes e terminam um pouco depois (13000 a 23000)
        if (time < 13000L || time > 23000L) {
            return;
        }
        
        int rate = 0;
        if (time < 14000L) {
            rate = (14000 - (int) time) / 2;
        } else if (time > 22000L) {
            rate = ((int) time - 22000) / 2;
        }

        double x = pos.getX() + 0.5D;
        double y = pos.getY();
        double z = pos.getZ() + 0.5D;

        // Partículas brilhantes acima da folha
        if (rand.nextInt(200 + rate) == 1 && worldIn.isAirBlock(pos.up())) {
            for (int i = 0; i < 10; ++i) {
                worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, 
                    pos.getX() + rand.nextDouble(), 
                    y + 1.25D, 
                    pos.getZ() + rand.nextDouble(), 
                    rand.nextGaussian(), Math.abs(rand.nextGaussian()), rand.nextGaussian());
            }
        }
        
        // Partículas brilhantes abaixo da folha (chuva mágica)
        if (rand.nextInt(40 + rate) == 1 && worldIn.isAirBlock(pos.down())) {
            for (int i = 0; i < 4; ++i) {
                worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, 
                    pos.getX() + rand.nextDouble(), 
                    y - 0.25D, 
                    pos.getZ() + rand.nextDouble(), 
                    rand.nextFloat() - rand.nextFloat(), 
                    -Math.abs(rand.nextFloat()), 
                    rand.nextFloat() - rand.nextFloat());
            }
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
        return BlockPlanks.EnumType.OAK; // Retorno padrão exigido
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