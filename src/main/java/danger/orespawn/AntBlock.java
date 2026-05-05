package danger.orespawn;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
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

public class AntBlock extends BlockGrass {

    public AntBlock() {
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            if (worldIn.isRaining()) {
                return;
            }
            
            Block bid = worldIn.getBlockState(pos.up()).getBlock();
            if (bid == Blocks.AIR) {
                int howmany = OreSpawnMain.OreSpawnRand.nextInt(6) + 2;
                for (int i = 0; i < howmany; ++i) {
                    if (this == OreSpawnMain.MyAntBlock) {
                        if (OreSpawnMain.BlackAntEnable != 0) {
                            spawnCreature(worldIn, "ant", pos.getX() + 0.5, pos.getY() + 1.01, pos.getZ() + 0.5);
                        }
                    } else if (this == OreSpawnMain.MyRedAntBlock) {
                        if (OreSpawnMain.RedAntEnable != 0) {
                            spawnCreature(worldIn, "red_ant", pos.getX() + 0.5, pos.getY() + 1.01, pos.getZ() + 0.5);
                        }
                    } else if (this == OreSpawnMain.MyUnstableAntBlock) {
                        if (OreSpawnMain.UnstableAntEnable != 0) {
                            spawnCreature(worldIn, "unstable_ant", pos.getX() + 0.5, pos.getY() + 1.01, pos.getZ() + 0.5);
                        }
                    } else if (this == OreSpawnMain.TermiteBlock) {
                        if (OreSpawnMain.TermiteEnable != 0) {
                            spawnCreature(worldIn, "termite", pos.getX() + 0.5, pos.getY() + 1.01, pos.getZ() + 0.5);
                        }
                    } else {
                        if (OreSpawnMain.RainbowAntEnable != 0) {
                            spawnCreature(worldIn, "rainbow_ant", pos.getX() + 0.5, pos.getY() + 1.01, pos.getZ() + 0.5);
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

    public static Entity spawnCreature(World worldIn, String name, double x, double y, double z) {
        // Na 1.12.2, entidades precisam de um ResourceLocation válido (letras minúsculas e sem espaços).
        Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation("orespawn", name), worldIn);
        
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, worldIn.rand.nextFloat() * 360.0f, 0.0f);
            worldIn.spawnEntity(entity);
            if (entity instanceof EntityLiving) {
                ((EntityLiving)entity).playLivingSound();
            }
        }
        return entity;
    }
}