package danger.orespawn;

import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRadish extends BlockCrops {

    public BlockRadish() {
        super();
    }

    @Override
    protected Item getSeed() {
        // O semente e o fruto do rabanete no OreSpawn são o mesmo item
        return OreSpawnMain.MyRadish;
    }

    @Override
    protected Item getCrop() {
        return OreSpawnMain.MyRadish;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // Se estiver totalmente crescido (age 7), dropa entre 2 e 5 rabanetes
        if (this.isMaxAge(state)) {
            return 2 + random.nextInt(4);
        }
        // Se quebrado antes, dropa 1 semente (o próprio rabanete)
        return 1;
    }
}