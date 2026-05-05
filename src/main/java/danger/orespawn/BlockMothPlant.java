package danger.orespawn;

import java.util.Random;
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

public class BlockMothPlant extends BlockCrops {

    public BlockMothPlant() {
        super();
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        // Deixa a planta crescer naturalmente
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.isRemote) return;

        // Se estiver chovendo, mariposas não aparecem
        if (worldIn.isRaining()) return;

        // Verifica se estão habilitadas
        if (OreSpawnMain.MothEnable == 0) return;

        int age = this.getAge(state);
        
        // Lógica de chance baseada na idade (0-7)
        int rate = 7 - age;
        if (rate > 1 && rand.nextInt(rate) != 0) {
            return;
        }

        // Condições: Ar acima, noite e Mariposas ligadas
        if (worldIn.isAirBlock(pos.up()) && !worldIn.isDaytime()) {
            // Diferente dos mosquitos, as mariposas costumam spawnar sozinhas ou em grupos menores
            spawnCreature(worldIn, "orespawn:moth", pos.getX() + 0.5D, pos.getY() + 1.01D, pos.getZ() + 0.5D);
        }
    }

    /**
     * Auxiliar para spawnar entidades
     */
    public static Entity spawnCreature(World world, String name, double x, double y, double z) {
        Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(name), world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(entity);
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).playLivingSound();
            }
        }
        return entity;
    }

    @Override
    protected Item getSeed() {
        return OreSpawnMain.MyMothSeed;
    }

    @Override
    protected Item getCrop() {
        // Geralmente retorna null se você só quer que drope sementes
        return null;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // Dropa de 1 a 5 sementes (ajuste conforme o equilíbrio original)
        return 1 + random.nextInt(5);
    }
}