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

public class BlockMosquitoPlant extends BlockCrops {

    public BlockMosquitoPlant() {
        super();
        // Na 1.12.2, o setTickRandomly já é true por padrão em BlockCrops
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        // Crescimento padrão da planta
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.isRemote) return;

        // Verifica se mosquitos estão habilitados na config
        if (OreSpawnMain.MosquitoEnable == 0) return;

        int age = this.getAge(state);
        
        // Lógica de chance baseada no estágio (quanto mais madura, maior a chance)
        // O meta 7 é o estágio final (maduro)
        int spawnChance = 7 - age;
        if (spawnChance > 1 && rand.nextInt(spawnChance) != 0) {
            return;
        }

        // Verifica se há espaço livre acima para o mosquito "decolar"
        if (worldIn.isAirBlock(pos.up())) {
            
            // Quantidade de mosquitos (2 a 6)
            int howMany = 2 + rand.nextInt(5);
            
            for (int i = 0; i < howMany; ++i) {
                spawnCreature(worldIn, "orespawn:mosquito", pos.getX() + 0.5D, pos.getY() + 1.01D, pos.getZ() + 0.5D);
            }
        }
    }

    /**
     * Método auxiliar para spawnar a entidade pelo ID de registro
     */
    public static Entity spawnCreature(World world, String name, double x, double y, double z) {
        Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(name), world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(entity);
            
            // Tenta tocar o som ambiente da entidade ao nascer
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).playLivingSound();
            }
        }
        return entity;
    }

    @Override
    protected Item getSeed() {
        // Retorna o item da semente definido no seu Mod principal
        return OreSpawnMain.MyMosquitoSeed;
    }

    @Override
    protected Item getCrop() {
        // Plantas de mosquito geralmente não dropam "frutos", apenas sementes
        return null;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // Dropa de 1 a 5 sementes ao ser colhida/quebrada
        return 1 + random.nextInt(5);
    }

    /* * Lembrete: Removido getIcon e registerTextures.
     * Na 1.12.2, a renderização é controlada pelo arquivo:
     * assets/orespawn/blockstates/mosquito_plant.json
     */
}