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
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class MoleDirtBlock
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public MoleDirtBlock(int i) {
        super(Material.GROUND);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        //this.setTickRandomly(true);
    }

    public void updateTick(World worldIn, int par2, int par3, int par4, Random par5Random) {
        if (worldIn.isRemote) {
            return;
        }
        worldIn.setBlockState(par2, par3, par4, Blocks.AIR, 0, 2);
    }

    import net.minecraft.util.math.AxisAlignedBB;
public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int par2, int par3, int par4) {
        float f = 0.125f;
        return new AxisAlignedBB((double)par2, (double)par3, (double)par4, (double)(par2 + 1), (double)((float)(par3 + 1) - f), (double)(par4 + 1));
    }

    public void onEntityCollidedWithBlock(World worldIn, int par2, int par3, int par4, Entity par5Entity) {
        if (par5Entity != null) {
            par5Entity.motionX *= 0.3;
            par5Entity.motionZ *= 0.3;
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

