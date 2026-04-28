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
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.world.World
 *  net.minecraftforge.common.util.net.minecraft.util.EnumFacing
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreBasicStone
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public OreBasicStone(int par1, float f1, float f2) {
        super(Material.ROCK);
        this.setHardness(f1);
        this.setResistance(f2);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        //this.setTickRandomly(false);
    }

    public void onBlockDestroyedByPlayer(World worldIn, int par2, int par3, int par4, int par5) {
        int i;
        int num;
        if (!worldIn.isRemote && this == OreSpawnMain.CrystalRat) {
            num = 1 + worldIn.rand.nextInt(10);
            for (i = 0; i < num; ++i) {
                OreBasicStone.spawnCreature(worldIn, 0, "Rat", (double)par2 + 0.5 + (double)(worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2, (double)par3 + 0.01, (double)par4 + 0.5 + (double)(worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2);
            }
        }
        if (!worldIn.isRemote && this == OreSpawnMain.CrystalFairy) {
            num = 1 + worldIn.rand.nextInt(6);
            for (i = 0; i < num; ++i) {
                OreBasicStone.spawnCreature(worldIn, 0, "Fairy", (double)par2 + 0.5 + (double)(worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2, (double)par3 + 0.01, (double)par4 + 0.5 + (double)(worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2);
            }
        }
        if (!worldIn.isRemote && this == OreSpawnMain.RedAntTroll) {
            num = 15 + worldIn.rand.nextInt(6);
            for (i = 0; i < num; ++i) {
                OreBasicStone.spawnCreature(worldIn, 0, "Red Ant", (double)par2 + 0.5 + (double)(worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2, (double)par3 + 0.01, (double)par4 + 0.5 + (double)(worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2);
            }
        }
        if (!worldIn.isRemote && this == OreSpawnMain.TermiteTroll) {
            num = 15 + worldIn.rand.nextInt(6);
            for (i = 0; i < num; ++i) {
                OreBasicStone.spawnCreature(worldIn, 0, "Termite", (double)par2 + 0.5 + (double)(worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2, (double)par3 + 0.01, (double)par4 + 0.5 + (double)(worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2);
            }
        }
        super.onBlockDestroyedByPlayer(worldIn, par2, par3, par4, par5);
    }

    public boolean isBlockSolidOnSide(World world, int x, int y, int z, net.minecraft.util.EnumFacing side) {
        return true;
    }

    public boolean isOpaqueCube() {
        return OreSpawnMain.current_dimension == OreSpawnMain.DimensionID5;
    }

    public boolean renderAsNormalBlock() {
        return OreSpawnMain.current_dimension == OreSpawnMain.DimensionID5;
    }

    public static Entity spawnCreature(World par0World, int par1, String name, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = name == null ? EntityList.createEntityByID((int)par1, (World)par0World) : EntityList.createEntityByIDFromName((String)name, (World)par0World);
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

