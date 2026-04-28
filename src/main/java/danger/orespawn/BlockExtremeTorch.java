/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.BlockTorch
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockExtremeTorch
extends BlockTorch {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public BlockExtremeTorch(int par1) {
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        int var6 = worldIn.getBlockMetadata(par2, par3, par4);
        double var7 = (float)par2 + 0.5f;
        double var9 = (float)par3 + 0.7f;
        double var11 = (float)par4 + 0.5f;
        double var13 = 0.213;
        double var15 = 0.271;
        if (var6 == 1) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, var7 - var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 2) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, var7 + var15, var9 + var13, var11, 0.0, 0.0, 0.0);
        } else if (var6 == 3) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, var7, var9 + var13, var11 - var15, 0.0, 0.0, 0.0);
        } else if (var6 == 4) {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, var7, var9 + var13, var11 + var15, 0.0, 0.0, 0.0);
        } else {
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, var7, var9, var11, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var7, var9, var11, 0.0, 0.0, 0.0);
            worldIn.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, var7, var9, var11, 0.0, 0.0, 0.0);
        }
        this.onBlockPlacedBy(worldIn, par2, par3, par4, null, null);
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        return super.canPlaceBlockAt(worldIn, par2, par3, par4);
    }

    public void onBlockPlacedBy(World world, int par2, int par3, int par4, net.minecraft.entity.EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
        int x = par2;
        int y = par3;
        int z = par4;
        boolean found = false;
        if (world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 1, z)).getBlock() == OreSpawnMain.MyEyeOfEnderBlock) {
            block0: for (int tries = 0; tries < 100 && !found; ++tries) {
                x = world.rand.nextInt(2) == 0 ? par2 + 4 + world.rand.nextInt(3) - world.rand.nextInt(3) : par2 - 4 + world.rand.nextInt(3) - world.rand.nextInt(3);
                z = world.rand.nextInt(2) == 0 ? par4 + 4 + world.rand.nextInt(3) - world.rand.nextInt(3) : par4 - 4 + world.rand.nextInt(3) - world.rand.nextInt(3);
                for (y = par3 - 2; y <= par3 + 2; ++y) {
                    if (!world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 1, z)).getBlock().getMaterial().isSolid() || world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock() != Blocks.AIR || world.getBlockState(new net.minecraft.util.math.BlockPos(x, y + 1, z)).getBlock() != Blocks.AIR) continue;
                    found = true;
                    continue block0;
                }
            }
            if (found) {
                if (!world.isRemote) {
                    Entity ent = null;
                    ent = BlockExtremeTorch.spawnCreature(world, "Cephadrome", (double)x + 0.5, (double)y + 0.01, (double)z + 0.5);
                } else {
                    for (int var3 = 0; var3 < 16; ++var3) {
                        world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (double)((float)par2 + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)par3 + world.rand.nextFloat()), (double)((float)par4 + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                        world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (double)((float)par2 + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)par3 + world.rand.nextFloat()), (double)((float)par4 + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                        world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, (double)((float)par2 + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)par3 + world.rand.nextFloat()), (double)((float)par4 + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                    }
                }
                if (par5EntityLiving != null) {
                    par5EntityLiving.world.playSoundAtEntity((Entity)par5EntityLiving, "random.explode", 1.0f, world.rand.nextFloat() * 0.2f + 0.9f);
                } else {
                    world.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation((double)par2, (double)par3, (double)par4, "random.explode", 1.0f, world.rand.nextFloat() * 0.2f + 0.9f, false);
                }
                world.setBlockState(par2, par3, par4, Blocks.AIR);
            }
        }
        super.onBlockPlacedBy(world, par2, par3, par4, par5EntityLiving, par6ItemStack);
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

