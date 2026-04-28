/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.BlockWorkbench
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.TextureAtlasSprite
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraft.world.World;

public class CrystalWorkbench
extends BlockWorkbench {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite workbenchIconTop;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite workbenchIconFront;

    protected CrystalWorkbench(int par1, float f1, float f2) {
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setHardness(f1);
        this.setResistance(f2);
    }

    public boolean onBlockActivated(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if (worldIn.isRemote) {
            return true;
        }
        par5EntityPlayer.openGui((Object)OreSpawnMain.instance, 1, worldIn, par2, par3, par4);
        return true;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public TextureAtlasSprite getIcon(int par1, int par2) {
        return par1 == 1 ? this.workbenchIconTop : (par1 == 0 ? this.blockIcon : (par1 != 2 && par1 != 4 ? this.blockIcon : this.workbenchIconFront));
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap par1net.minecraft.client.renderer.texture.TextureMap) {
        this.blockIcon = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_side"));
        this.workbenchIconTop = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_top"));
        this.workbenchIconFront = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_bottom"));
    }
}

