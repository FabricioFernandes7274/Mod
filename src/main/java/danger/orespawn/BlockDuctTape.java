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
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.TextureAtlasSprite
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraft.world.World;

public class BlockDuctTape
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite DuctTapeTopIcon;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite DuctTapeBottomIcon;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite innerIcon;

    protected BlockDuctTape(int par1) {
        super(Material.ANVIL);
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
        return par1 == 1 ? this.DuctTapeTopIcon : (par1 == 0 ? this.DuctTapeBottomIcon : (par2 > 0 && par1 == 4 ? this.innerIcon : this.blockIcon));
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap par1IconRegister) {
        this.blockIcon = par1IconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_side"));
        this.innerIcon = par1IconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_inner"));
        this.DuctTapeTopIcon = par1IconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_top"));
        this.DuctTapeBottomIcon = par1IconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_bottom"));
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean onBlockActivated(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        this.eatDuctTapeSlice(worldIn, par2, par3, par4, par5EntityPlayer);
        return true;
    }

    public void onBlockClicked(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer) {
        this.eatDuctTapeSlice(worldIn, par2, par3, par4, par5EntityPlayer);
    }

    private void eatDuctTapeSlice(World worldIn, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5EntityPlayer) {
        ItemStack var2;
        if (par5EntityPlayer != null && (var2 = par5EntityPlayer.inventory.getCurrentItem()) != null && var2.stackSize == 1) {
            int cd = var2.getMaxDurability();
            int fd = 0;
            if (cd > 0) {
                if ((cd /= 6) < 1) {
                    cd = 1;
                }
                if ((fd = var2.getMetadata()) > 0) {
                    fd = fd > cd ? (fd -= cd) : 0;
                    var2.setMetadata(fd);
                    int l = worldIn.getBlockMetadata(par2, par3, par4) + 1;
                    if (l >= 6) {
                        worldIn.setBlockToAir(par2, par3, par4);
                    } else {
                        worldIn// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //// TODO: setBlockMetadataWithNotify removido na 1.12.2 //.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
                    }
                }
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
        return worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock().getMaterial().isSolid();
    }

    public int quantityDropped(Random par1Random) {
        return 0;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @SideOnly(value=Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return OreSpawnMain.MyDuctTapeItem;
    }
}

