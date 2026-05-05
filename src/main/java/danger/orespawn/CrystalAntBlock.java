package danger.orespawn;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrystalAntBlock extends Block {

    public CrystalAntBlock() {
        super(Material.GRASS);
        // OBRIGATÓRIO na 1.12.2 para que o bloco atualize e crie as formigas
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            if (worldIn.isRaining()) {
                return;
            }
            
            // Verifica se o bloco em cima é Ar
            if (worldIn.isAirBlock(pos.up())) {
                int howmany = OreSpawnMain.OreSpawnRand.nextInt(6) + 2;
                
                for (int i = 0; i < howmany; ++i) {
                    if (this == OreSpawnMain.MyAntBlock) {
                        if (OreSpawnMain.BlackAntEnable != 0) {
                            spawnCreature(worldIn, new ResourceLocation("orespawn", "Ant"), pos.getX() + 0.5D, pos.getY() + 1.01D, pos.getZ() + 0.5D);
                        }
                    } else if (this == OreSpawnMain.MyRedAntBlock) {
                        if (OreSpawnMain.RedAntEnable != 0) {
                            spawnCreature(worldIn, new ResourceLocation("orespawn", "Red Ant"), pos.getX() + 0.5D, pos.getY() + 1.01D, pos.getZ() + 0.5D);
                        }
                    } else if (this == OreSpawnMain.MyUnstableAntBlock) {
                        if (OreSpawnMain.UnstableAntEnable != 0) {
                            spawnCreature(worldIn, new ResourceLocation("orespawn", "Unstable Ant"), pos.getX() + 0.5D, pos.getY() + 1.01D, pos.getZ() + 0.5D);
                        }
                    } else if (this == OreSpawnMain.TermiteBlock || this == OreSpawnMain.CrystalTermiteBlock) {
                        if (OreSpawnMain.TermiteEnable != 0) {
                            spawnCreature(worldIn, new ResourceLocation("orespawn", "Termite"), pos.getX() + 0.5D, pos.getY() + 1.01D, pos.getZ() + 0.5D);
                        }
                    } else {
                        // Se não for nenhum dos anteriores, é o Rainbow Ant Block
                        if (OreSpawnMain.RainbowAntEnable != 0) {
                            spawnCreature(worldIn, new ResourceLocation("orespawn", "Rainbow Ant"), pos.getX() + 0.5D, pos.getY() + 1.01D, pos.getZ() + 0.5D);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public static Entity spawnCreature(World world, ResourceLocation resLoc, double x, double y, double z) {
        Entity entity = EntityList.createEntityByIDFromName(resLoc, world);
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
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        // Na 1.12.2 isto substitui o renderAsNormalBlock()
        return false;
    }
}