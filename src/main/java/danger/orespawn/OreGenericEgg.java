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
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreGenericEgg
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public OreGenericEgg(int oldid) {
        super(Material.GROUND);
        this.setHardness(0.5f);
        this.setResistance(1.0f);
        this.setStepSound(net.minecraft.block.SoundType.GROUND);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void dropBlockAsItemWithChance(World worldIn, int par2, int par3, int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(worldIn, par2, par3, par4, par5, par6, par7);
        int j1 = 5 + worldIn.rand.nextInt(3) + worldIn.rand.nextInt(3);
        if (worldIn.rand.nextInt(2) == 1) {
            this.dropXpOnBlockBreak(worldIn, par2, par3, par4, j1);
        }
    }

    public boolean isOpaqueCube() {
        return true;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

