package danger.orespawn;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockExtremeTorch extends BlockTorch {

    public BlockExtremeTorch() {
        super();
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        // Pega a direção para qual a tocha está virada
        EnumFacing enumfacing = stateIn.getValue(FACING);
        double x = (double) pos.getX() + 0.5D;
        double y = (double) pos.getY() + 0.7D;
        double z = (double) pos.getZ() + 0.5D;
        double yOffset = 0.22D;
        double offset = 0.27D;

        // Se a tocha estiver na parede, ajusta a posição da partícula
        if (enumfacing.getAxis().isHorizontal()) {
            EnumFacing oposto = enumfacing.getOpposite();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + offset * (double) oposto.getFrontOffsetX(), y + yOffset, z + offset * (double) oposto.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, x + offset * (double) oposto.getFrontOffsetX(), y + yOffset, z + offset * (double) oposto.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, x + offset * (double) oposto.getFrontOffsetX(), y + yOffset, z + offset * (double) oposto.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
        } else {
            // Se estiver no chão
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        // Armadilha do Cephadrome: Checa se o bloco debaixo é o bloco "Eye of Ender" do OreSpawn
        if (worldIn.getBlockState(pos.down()).getBlock() == OreSpawnMain.MyEyeOfEnderBlock) {
            boolean found = false;
            BlockPos spawnPos = null;

            // Tenta achar um lugar válido para spawnar em até 100 tentativas (removido o label bugado "block0")
            for (int tries = 0; tries < 100 && !found; ++tries) {
                int xOffset = worldIn.rand.nextInt(2) == 0 ? 4 + worldIn.rand.nextInt(3) - worldIn.rand.nextInt(3) : -4 + worldIn.rand.nextInt(3) - worldIn.rand.nextInt(3);
                int zOffset = worldIn.rand.nextInt(2) == 0 ? 4 + worldIn.rand.nextInt(3) - worldIn.rand.nextInt(3) : -4 + worldIn.rand.nextInt(3) - worldIn.rand.nextInt(3);
                
                int targetX = pos.getX() + xOffset;
                int targetZ = pos.getZ() + zOffset;

                // Checa uma variação de altura de Y-2 a Y+2
                for (int targetY = pos.getY() - 2; targetY <= pos.getY() + 2; ++targetY) {
                    BlockPos checkPos = new BlockPos(targetX, targetY, targetZ);
                    
                    // Condição de spawn: chão sólido e 2 blocos de ar acima
                    if (worldIn.getBlockState(checkPos.down()).getMaterial().isSolid() && 
                        worldIn.isAirBlock(checkPos) && 
                        worldIn.isAirBlock(checkPos.up())) {
                        
                        found = true;
                        spawnPos = checkPos;
                        break; // Sai do loop interno
                    }
                }
            }

            if (found && spawnPos != null) {
                if (!worldIn.isRemote) {
                    // Na 1.12.2, os nomes das entidades devem ter o namespace do mod (ex: "orespawn:cephadrome")
                    spawnCreature(worldIn, "orespawn:cephadrome", spawnPos.getX() + 0.5D, spawnPos.getY() + 0.01D, spawnPos.getZ() + 0.5D);
                } else {
                    for (int i = 0; i < 16; ++i) {
                        double px = pos.getX() + worldIn.rand.nextFloat() - worldIn.rand.nextFloat();
                        double py = pos.getY() + worldIn.rand.nextFloat();
                        double pz = pos.getZ() + worldIn.rand.nextFloat() - worldIn.rand.nextFloat();
                        
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, 0.0D, 0.0D, 0.0D);
                        worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, px, py, pz, 0.0D, 0.0D, 0.0D);
                        worldIn.spawnParticle(EnumParticleTypes.REDSTONE, px, py, pz, 0.0D, 0.0D, 0.0D);
                    }
                }

                // Som da explosão da tocha corrigido para a 1.12.2
                worldIn.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0F, worldIn.rand.nextFloat() * 0.2F + 0.9F);
                worldIn.setBlockToAir(pos); // Destrói a tocha
            }
        }
        
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    public static Entity spawnCreature(World world, String entityName, double x, double y, double z) {
        Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(entityName), world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(entity);
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).playLivingSound();
            }
        }
        return entity;
    }
}