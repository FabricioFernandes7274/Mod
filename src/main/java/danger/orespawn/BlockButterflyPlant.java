package danger.orespawn;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockButterflyPlant extends BlockCrops {

    public BlockButterflyPlant() {
        super();
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        
        if (worldIn.isRemote) {
            return;
        }
        if (worldIn.isRaining()) {
            return;
        }
        
        int age = this.getAge(state);
        int rate = 7 - age;
        
        if (rate > 1 && OreSpawnMain.OreSpawnRand.nextInt(rate) != 0) {
            return;
        }
        
        Block bid = worldIn.getBlockState(pos.up()).getBlock();
        if (bid == Blocks.AIR && worldIn.isDaytime() && OreSpawnMain.ButterflyEnable != 0) {
            spawnCreature(worldIn, "butterfly", pos.getX() + 0.5, pos.getY() + 1.01, pos.getZ() + 0.5);
        }
    }

    public static Entity spawnCreature(World worldIn, String name, double x, double y, double z) {
        // Na 1.12.2, o ResourceLocation exige que o nome da entidade esteja em letras minúsculas
        Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation("orespawn", name.toLowerCase()), worldIn);
        
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, worldIn.rand.nextFloat() * 360.0f, 0.0f);
            worldIn.spawnEntity(entity);
            if (entity instanceof EntityLiving) {
                ((EntityLiving)entity).playLivingSound();
            }
        }
        return entity;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(5);
    }

    @Override
    protected Item getSeed() {
        return OreSpawnMain.MyButterflySeed;
    }

    @Override
    protected Item getCrop() {
        // Retornar null na 1.12.2 causa Crash. Retornamos a própria semente como "Crop".
        return OreSpawnMain.MyButterflySeed;
    }
}