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

public class BlockFireflyPlant extends BlockCrops {

    public BlockFireflyPlant() {
        super();
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        // Executa o crescimento normal da planta (super)
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.isRemote) return;

        // Se estiver chovendo, vaga-lumes não saem
        if (worldIn.isRaining()) return;

        int age = this.getAge(state);
        
        // No original, quanto mais crescida, mais chance de spawnar. 
        // 7 é o estágio final (maduro).
        int spawnChance = 7 - age;
        if (spawnChance > 1 && rand.nextInt(spawnChance) != 0) {
            return;
        }

        // Condições de Spawn: Espaço vazio acima, noite e config habilitada
        if (worldIn.isAirBlock(pos.up()) && !worldIn.isDaytime() && OreSpawnMain.FireflyEnable != 0) {
            
            // Quantidade de vaga-lumes (2 a 6)
            int count = 2 + rand.nextInt(5);
            
            for (int i = 0; i < count; ++i) {
                spawnCreature(worldIn, "orespawn:firefly", pos.getX() + 0.5D, pos.getY() + 1.01D, pos.getZ() + 0.5D);
            }
        }
    }

    /**
     * Helper para spawnar entidades pelo nome na 1.12.2
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
        return OreSpawnMain.MyFireflySeed;
    }

    @Override
    protected Item getCrop() {
        // Esta planta geralmente dropa apenas sementes no OreSpawn
        return OreSpawnMain.MyFireflySeed;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // Se estiver madura, dropa mais sementes
        return this.isMaxAge(state) ? 1 + random.nextInt(5) : 1;
    }

    /* * NOTA SOBRE TEXTURAS: 
     * Na 1.12.2, não usamos mais registerTextures ou getIcon no código do Bloco.
     * Você deve criar um arquivo JSON em:
     * assets/orespawn/blockstates/firefly_plant.json
     * E os modelos em assets/orespawn/models/block/firefly_plant_stageX.json
     */
}