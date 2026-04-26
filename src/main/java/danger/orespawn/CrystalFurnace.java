/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.TextureAtlasSprite
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalFurnace
extends BlockContainer {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    private final Random furnaceRand = new Random();
    private final boolean isActive;
    private static boolean keepFurnaceInventory;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite furnaceIconTop;
    @SideOnly(value=Side.CLIENT)
    private TextureAtlasSprite furnaceIconFront;

    protected CrystalFurnace(int par1, boolean par2, float f1, float f2) {
        super(Material.ROCK);
        this.isActive = par2;
        if (!par2) {
            this.setCreativeTab(CreativeTabs.DECORATIONS);
        } else {
            this.setLightLevel(0.6f);
        }
        this.setHardness(f1);
        this.setResistance(f2);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock((Block)OreSpawnMain.CrystalFurnaceBlock);
    }

    @SideOnly(value=Side.CLIENT)
    public Item idPicked(World par1World, int par2, int par3, int par4) {
        return Item.getItemFromBlock((Block)OreSpawnMain.CrystalFurnaceBlock);
    }

    @SideOnly(value=Side.CLIENT)
    public TextureAtlasSprite getIcon(int par1, int par2) {
        return par1 == 1 ? this.furnaceIconTop : (par1 == 0 ? this.furnaceIconTop : (par1 != par2 ? this.blockIcon : this.furnaceIconFront));
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap par1net.minecraft.client.renderer.texture.TextureMap) {
        this.blockIcon = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_side"));
        this.furnaceIconFront = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + (this.isActive ? "_front_off" : "_front_off"));
        this.furnaceIconTop = par1net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5) + "_top"));
    }

    public static void updateFurnaceBlockState(boolean par0, World par1World, int par2, int par3, int par4) {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getTileEntity(new net.minecraft.util.math.BlockPos(par2, par3, par4));
        keepFurnaceInventory = true;
        if (par0) {
            par1World.setBlockState(par2, par3, par4, OreSpawnMain.CrystalFurnaceOnBlock);
        } else {
            par1World.setBlockState(par2, par3, par4, (Block)OreSpawnMain.CrystalFurnaceBlock);
        }
        keepFurnaceInventory = false;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
        if (tileentity != null) {
            tileentity.validate();
            par1World.setTileEntity(par2, par3, par4, tileentity);
        }
    }

    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    private void setDefaultDirection(World par1World, int par2, int par3, int par4) {
        if (!par1World.isRemote) {
            Block l = par1World.getBlock(par2, par3, par4 - 1);
            Block i1 = par1World.getBlock(par2, par3, par4 + 1);
            Block j1 = par1World.getBlock(par2 - 1, par3, par4);
            Block k1 = par1World.getBlock(par2 + 1, par3, par4);
            int b0 = 3;
            if (l.isFullBlock() && !i1.isFullBlock()) {
                b0 = 3;
            }
            if (i1.isFullBlock() && !l.isFullBlock()) {
                b0 = 2;
            }
            if (j1.isFullBlock() && !k1.isFullBlock()) {
                b0 = 5;
            }
            if (k1.isFullBlock() && !j1.isFullBlock()) {
                b0 = 4;
            }
            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, net.minecraft.entity.player.EntityPlayer par5net.minecraft.entity.player.EntityPlayer, int par6, float par7, float par8, float par9) {
        if (par1World.isRemote) {
            return true;
        }
        TileEntityCrystalFurnace tileentitycrystalfurnace = (TileEntityCrystalFurnace)par1World.getTileEntity(new net.minecraft.util.math.BlockPos(par2, par3, par4));
        if (tileentitycrystalfurnace != null) {
            par5net.minecraft.entity.player.EntityPlayer.openGui((Object)OreSpawnMain.instance, 0, par1World, par2, par3, par4);
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (this.isActive) {
            int l = par1World.getBlockMetadata(par2, par3, par4);
            float f = (float)par2 + 0.5f;
            float f1 = (float)par3 + 0.0f + par5Random.nextFloat() * 6.0f / 16.0f;
            float f2 = (float)par4 + 0.5f;
            float f3 = par5Random.nextFloat() * 0.6f - 0.3f;
            float f4 = par5Random.nextFloat() * 0.6f - 0.3f;
            if (l == 4) {
                par1World.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
                par1World.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
            } else if (l == 5) {
                par1World.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
                par1World.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0, 0.0, 0.0);
            } else if (l == 2) {
                par1World.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0, 0.0, 0.0);
                par1World.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0, 0.0, 0.0);
            } else if (l == 3) {
                par1World.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0, 0.0, 0.0);
                par1World.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0, 0.0, 0.0);
            }
        }
    }

    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, net.minecraft.entity.EntityLivingBase par5net.minecraft.entity.EntityLivingBase, ItemStack par6ItemStack) {
        int l = net.minecraft.util.math.MathHelper.floor_double((double)((double)(par5net.minecraft.entity.EntityLivingBase.rotationYaw * 4.0f / 360.0f) + 0.5)) & 3;
        if (l == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }
        if (l == 1) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        }
        if (l == 2) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }
        if (l == 3) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        }
        if (par6ItemStack.hasDisplayName()) {
            ((TileEntityCrystalFurnace)par1World.getTileEntity(new net.minecraft.util.math.BlockPos(par2, par3, par4))).setCustomInventoryName(par6ItemStack.getDisplayName());
        }
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        TileEntityCrystalFurnace tileentitycrystalfurnace;
        if (!keepFurnaceInventory && (tileentitycrystalfurnace = (TileEntityCrystalFurnace)par1World.getTileEntity(new net.minecraft.util.math.BlockPos(par2, par3, par4))) != null) {
            for (int j1 = 0; j1 < tileentitycrystalfurnace.getSizeInventory(); ++j1) {
                ItemStack itemstack = tileentitycrystalfurnace.getStackInSlot(j1);
                if (itemstack == null) continue;
                float f = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
                float f1 = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
                float f2 = this.furnaceRand.nextFloat() * 0.8f + 0.1f;
                while (itemstack.stackSize > 0) {
                    int k1 = this.furnaceRand.nextInt(21) + 10;
                    if (k1 > itemstack.stackSize) {
                        k1 = itemstack.stackSize;
                    }
                    itemstack.stackSize -= k1;
                    EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getMetadata()));
                    if (itemstack.hasTagCompound()) {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                    float f3 = 0.05f;
                    entityitem.motionX = (float)this.furnaceRand.nextGaussian() * f3;
                    entityitem.motionY = (float)this.furnaceRand.nextGaussian() * f3 + 0.2f;
                    entityitem.motionZ = (float)this.furnaceRand.nextGaussian() * f3;
                    par1World.spawnEntity((Entity)entityitem);
                }
            }
            par1World.updateNeighborsAboutBlockChange(par2, par3, par4, par5);
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
        return Container.calcRedstoneFromInventory((IInventory)((IInventory)par1World.getTileEntity(new net.minecraft.util.math.BlockPos(par2, par3, par4))));
    }

    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityCrystalFurnace();
    }
}

