package danger.orespawn;

import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockRice extends BlockCrops {

    public BlockRice() {
        super();
    }

    @Override
    protected Item getSeed() {
        return OreSpawnMain.MyRice;
    }

    @Override
    protected Item getCrop() {
        return OreSpawnMain.MyRice;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        if (this.isMaxAge(state)) {
            return 2 + random.nextInt(4);
        }
        return 1;
    }
}