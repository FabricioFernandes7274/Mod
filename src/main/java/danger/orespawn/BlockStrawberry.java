package danger.orespawn;

import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockStrawberry extends BlockCrops {

    public BlockStrawberry() {
        super();
    }

    @Override
    protected Item getSeed() {
        return OreSpawnMain.MyStrawberrySeed;
    }

    @Override
    protected Item getCrop() {
        return OreSpawnMain.MyStrawberry;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // Ao colher morangos, o OreSpawn dropava entre 1 e 5
        if (this.isMaxAge(state)) {
            return 1 + random.nextInt(5);
        }
        // Dropa a semente se quebrado jovem
        return 1;
    }
}