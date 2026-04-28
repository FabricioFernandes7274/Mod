/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.EnumPlantType
 *  net.minecraftforge.common.IPlantable
 *  net.minecraftforge.common.util.net.minecraft.util.EnumFacing
 */
package danger.orespawn;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class MyBlockFlower
extends Block
implements IPlantable {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    protected MyBlockFlower(int par1, Material par2Material) {
        super(par2Material);
        //this.setTickRandomly(true);
        float f = 0.2f;
        //this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 3.0f, 0.5f + f);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    protected MyBlockFlower(int par1) {
        this(par1, Material.PLANTS);
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        return super.canPlaceBlockAt(worldIn, par2, par3, par4) && this.canBlockStay(worldIn, par2, par3, par4);
    }

    protected boolean canPlaceBlockOn(Block par1) {
        return par1 == Blocks.GRASS || par1 == Blocks.DIRT || par1 == Blocks.FARMLAND || par1 == OreSpawnMain.CrystalGrass;
    }

    public void onNeighborBlockChange(World worldIn, int par2, int par3, int par4, Block par5) {
        super.onNeighborBlockChange(worldIn, par2, par3, par4, par5);
        this.checkFlowerChange(worldIn, par2, par3, par4);
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        this.checkFlowerChange(worldIn, par2, par3, par4);
    }

    protected final void checkFlowerChange(World worldIn, int par2, int par3, int par4) {
        if (!this.canBlockStay(worldIn, par2, par3, par4)) {
            this.dropBlockAsItem(worldIn, par2, par3, par4, worldIn.getBlockMetadata(par2, par3, par4), 0);
            worldIn.setBlockState(par2, par3, par4, Blocks.AIR, 0, 2);
            return;
        }
        long t = worldIn.getWorldTime();
        if ((t %= 24000L) > 12000L) {
            if (this == OreSpawnMain.MyFlowerPinkBlock) {
                worldIn.setBlockState(par2, par3, par4, OreSpawnMain.MyFlowerBlackBlock);
            }
            if (this == OreSpawnMain.MyFlowerBlueBlock) {
                worldIn.setBlockState(par2, par3, par4, OreSpawnMain.MyFlowerScaryBlock);
            }
        } else {
            if (this == OreSpawnMain.MyFlowerBlackBlock) {
                worldIn.setBlockState(par2, par3, par4, OreSpawnMain.MyFlowerPinkBlock);
            }
            if (this == OreSpawnMain.MyFlowerScaryBlock) {
                worldIn.setBlockState(par2, par3, par4, OreSpawnMain.MyFlowerBlueBlock);
            }
        }
    }

    public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
        return p_149718_1_.getBlockState(new BlockPos(p_149718_2_, p_149718_3_ - 1, p_149718_4_)).getBlock().canSustainPlant((IBlockAccess)p_149718_1_, p_149718_2_, p_149718_3_ - 1, p_149718_4_, net.minecraft.util.EnumFacing.UP, (IPlantable)this);
    }

    import net.minecraft.util.math.AxisAlignedBB;
public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int par2, int par3, int par4) {
        return null;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return 1;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }

    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }
}

