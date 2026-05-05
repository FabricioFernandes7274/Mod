package danger.orespawn;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPizza extends Block {

    // Define as fatias comidas (0 a 6). 6 fatias comidas = pizza some.
    public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);
    
    // Caixas de colisão baseadas na quantidade de fatias restantes
    protected static final AxisAlignedBB[] PIZZA_AABB = new AxisAlignedBB[] {
        new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D), // 0 fatias comidas
        new AxisAlignedBB(0.1875D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D), // 1
        new AxisAlignedBB(0.3125D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D), // 2
        new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D), // 3
        new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D), // 4
        new AxisAlignedBB(0.6875D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D), // 5
        new AxisAlignedBB(0.8125D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D)  // 6
    };

    protected BlockPizza() {
        super(Material.CAKE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BITES, 0));
        this.setTickRandomly(true);
        this.setSoundType(SoundType.CLOTH); // Pizza é "macia" como o bolo
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return PIZZA_AABB[state.getValue(BITES)];
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            return this.eatPizzaSlice(worldIn, pos, state, playerIn);
        }
        return true;
    }

    private boolean eatPizzaSlice(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!player.canEat(false)) {
            return false;
        } else {
            // Adiciona 4 de comida (2 pernis) e 0.2 de saturação
            player.getFoodStats().addStats(4, 0.2F);
            int i = state.getValue(BITES);

            if (i < 6) {
                worldIn.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
            } else {
                worldIn.setBlockToAir(pos);
            }
            return true;
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return OreSpawnMain.MyPizzaItem;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(OreSpawnMain.MyPizzaItem);
    }

    // --- Sistema de Estados (Meta 1.12.2) ---

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BITES, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BITES);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BITES);
    }
}