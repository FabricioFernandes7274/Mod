package danger.orespawn;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDuctTape extends Block {

    // Define os estágios de consumo da fita (0 a 5 fatias)
    public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 5);

    // Caixas de colisão pré-calculadas para cada "mordida"
    protected static final AxisAlignedBB[] DUCT_TAPE_AABB = new AxisAlignedBB[] {
        new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D),
        new AxisAlignedBB(0.1875D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D),
        new AxisAlignedBB(0.3125D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D),
        new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D),
        new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D),
        new AxisAlignedBB(0.6875D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D)
    };

    public BlockDuctTape() {
        super(Material.ANVIL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BITES, 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return DUCT_TAPE_AABB[state.getValue(BITES)];
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        this.useDuctTapeSlice(worldIn, pos, state, playerIn, hand);
        return true;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        this.useDuctTapeSlice(worldIn, pos, worldIn.getBlockState(pos), playerIn, EnumHand.MAIN_HAND);
    }

    private void useDuctTapeSlice(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand) {
        if (worldIn.isRemote) {
            return;
        }

        ItemStack stack = player.getHeldItem(hand);
        
        // Verifica se há apenas UM item na mão e se ele pode sofrer dano (ferramentas/armaduras)
        if (!stack.isEmpty() && stack.getCount() == 1 && stack.isItemStackDamageable()) {
            int maxDamage = stack.getMaxDamage();
            int currentDamage = stack.getItemDamage();

            if (maxDamage > 0 && currentDamage > 0) {
                // Recupera 1/6 da durabilidade máxima por uso da fita
                int repairAmount = maxDamage / 6;
                if (repairAmount < 1) {
                    repairAmount = 1;
                }

                int newDamage = currentDamage - repairAmount;
                if (newDamage < 0) {
                    newDamage = 0;
                }

                stack.setItemDamage(newDamage);

                // Atualiza o estado visual e lógico da fita isolante
                int bites = state.getValue(BITES);
                if (bites < 5) {
                    worldIn.setBlockState(pos, state.withProperty(BITES, bites + 1), 3);
                } else {
                    worldIn.setBlockToAir(pos);
                }
            }
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
    public int quantityDropped(Random random) {
        return 0; // Se quebrar a fita sem o silk touch, não dropa nada (igual ao bolo)
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(OreSpawnMain.MyDuctTapeItem);
    }

    // --- MÉTODOS OBRIGATÓRIOS DO BLOCKSTATE NA 1.12.2 ---

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BITES);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BITES, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BITES);
    }
}