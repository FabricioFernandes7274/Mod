/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.TextureAtlasSprite
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraft.world.World;

public class BlockPizza
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite pizzaTopIcon;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite pizzaBottomIcon;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite innerIcon;

    protected BlockPizza(int par1) {
        super(Material.CAKE);
        //this.setTickRandomly(true);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float f = 0.0625f;
        float f1 = (float)(1 + l * 2) / 16.0f;
        float f2 = 0.25f;
        //this.setBlockBounds(f1, 0.0f, f, 1.0f - f, f2, 1.0f - f);
    }

    public void setBlockBoundsForItemRender() {
        float f = 0.0625f;
        float f1 = 0.25f;
        //this.setBlockBounds(f, 0.0f, f, 1.0f - f, f1, 1.0f - f);
    }

    import net.minecraft.util.math.AxisAlignedBB;
public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int par2, int par3, int par4) {
        int l = worldIn.getBlockMetadata(par2, par3, par4);
        float f = 0.0625f;
        float f1 = (float)(1 + l * 2) / 16.0f;
        float f2 = 0.25f;
        return new AxisAlignedBB((double)((float)par2 + f1), (double)par3, (double)((float)par4 + f), (double)((float)(par2 + 1) - f), (double)((float)par3 + f2 - f), (double)((float)(par4 + 1) - f));
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    import net.minecraft.util.math.AxisAlignedBB;
public AxisAlignedBB getSelectedBoundingBoxFromPool(World worldIn, int par2, int par3, int par4) {
        int l = worldIn.getBlockMetadata(par2, par3, par4);
        float f = 0.0625f;
        float f1 = (float)(1 + l * 2) / 16.0f;
        float f2 = 0.25f;
        return new AxisAlignedBB((double)((float)par2 + f1), (double)par3, (double)((float)par4 + f), (double)((float)(par2 + 1) - f), (double)((float)par3 + f2), (double)((float)(par4 + 1) - f));
    }

    @SideOnly(value=Side.CLIENT)
    public TextureAtlasSprite getIcon(int par1, int par2) {
        return par1 == 1 ? this.pizzaTopIcon : (par1 == 0 ? this.pizzaBottomIcon : (par2 > 0 && par1 == 4 ? this.innerIcon : this.blockIcon));
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap par1net.minecraft.client.renderer.texture.TextureMap) {
        this.blockIcon = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_side"));
        this.innerIcon = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_inner"));
        this.pizzaTopIcon = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_top"));
        this.pizzaBottomIcon = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_bottom"));
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean onBlockActivated(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        this.eatPizzaSlice(worldIn, par2, par3, par4, par5EntityPlayer);
        return true;
    }

    public void onBlockClicked(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer) {
        this.eatPizzaSlice(worldIn, par2, par3, par4, par5EntityPlayer);
    }

    private void eatPizzaSlice(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer) {
        if (par5EntityPlayer.canEat(false)) {
            par5EntityPlayer.getFoodStats().addStats(4, 0.2f);
            int l = worldIn.getBlockMetadata(par2, par3, par4) + 1;
            if (l >= 6) {
                worldIn.setBlockToAir(par2, par3, par4);
            } else {
                worldIn// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
            }
        }
    }

    public boolean canPlaceBlockAt(World worldIn, int par2, int par3, int par4) {
        return !super.canPlaceBlockAt(worldIn, par2, par3, par4) ? false : this.canBlockStay(worldIn, par2, par3, par4);
    }

    public void onNeighborBlockChange(World worldIn, int par2, int par3, int par4, int par5) {
        if (!this.canBlockStay(worldIn, par2, par3, par4)) {
            worldIn.setBlockToAir(par2, par3, par4);
        }
    }

    public boolean canBlockStay(World worldIn, int par2, int par3, int par4) {
        return worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock().isNormalCube();
    }

    public int quantityDropped(Random par1Random) {
        return 0;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return OreSpawnMain.MyPizzaItem;
    }
}

