/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSifter
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemSifter(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setMaxDurability(600);
    }

    private void dropItemRand(Item index, int par1, World world, int x, int y, int z) {
        EntityItem var3 = new EntityItem(world, (double)(x + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2)) + 0.5, (double)y + 1.1, (double)(z + OreSpawnMain.OreSpawnRand.nextInt(2) - OreSpawnMain.OreSpawnRand.nextInt(2)) + 0.5, new ItemStack(index, par1, 0));
        world.spawnEntity((Entity)var3);
    }

    public boolean onItemUse(ItemStack par1ItemStack, net.minecraft.entity.player.EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        int i;
        if (par3World.isRemote) {
            return true;
        }
        Block bid = par3World.getBlockState(new BlockPos(par4, par5, par6)).getBlock();
        Block bid2 = par3World.getBlockState(new BlockPos(par4, par5 + 1, par6)).getBlock();
        if (bid2 == Blocks.FLOWING_WATER) {
            bid = Blocks.WATER;
        }
        if (bid2 == Blocks.WATER) {
            bid = Blocks.WATER;
        }
        if (bid == Blocks.WATER) {
            i = par3World.rand.nextInt(160);
            switch (i) {
                case 0: {
                    this.dropItemRand(Items.FISH, 1, par3World, par4, par5, par6);
                    break;
                }
                case 1: {
                    this.dropItemRand(OreSpawnMain.MyGreenFish, 1, par3World, par4, par5, par6);
                    break;
                }
                case 2: {
                    this.dropItemRand(OreSpawnMain.MyBlueFish, 1, par3World, par4, par5, par6);
                    break;
                }
                case 3: {
                    this.dropItemRand(OreSpawnMain.MyPinkFish, 1, par3World, par4, par5, par6);
                    break;
                }
                case 4: {
                    this.dropItemRand(OreSpawnMain.MyRockFish, 1, par3World, par4, par5, par6);
                    break;
                }
                case 5: {
                    this.dropItemRand(OreSpawnMain.MyWoodFish, 1, par3World, par4, par5, par6);
                    break;
                }
                case 6: {
                    this.dropItemRand(OreSpawnMain.MyGreyFish, 1, par3World, par4, par5, par6);
                    break;
                }
                case 7: {
                    this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 8: {
                    this.dropItemRand(Items.IRON_INGOT, 1, par3World, par4, par5, par6);
                    break;
                }
                case 9: {
                    this.dropItemRand(Items.GOLD_NUGGET, 1, par3World, par4, par5, par6);
                    break;
                }
                case 10: {
                    this.dropItemRand(OreSpawnMain.MyItemShoes, 1, par3World, par4, par5, par6);
                    break;
                }
                case 11: {
                    this.dropItemRand(OreSpawnMain.MyItemShoes_1, 1, par3World, par4, par5, par6);
                    break;
                }
                case 12: {
                    this.dropItemRand(OreSpawnMain.MyItemShoes_2, 1, par3World, par4, par5, par6);
                    break;
                }
                case 13: {
                    this.dropItemRand(OreSpawnMain.MyItemShoes_3, 1, par3World, par4, par5, par6);
                    break;
                }
                case 14: {
                    this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 15: {
                    this.dropItemRand(Items.BONE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 16: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.STONE), 1, par3World, par4, par5, par6);
                    break;
                }
                case 17: {
                    this.dropItemRand(Items.BUCKET, 1, par3World, par4, par5, par6);
                    break;
                }
                case 18: {
                    this.dropItemRand(Items.WATER_BUCKET, 1, par3World, par4, par5, par6);
                    break;
                }
                case 19: {
                    if (par3World.rand.nextInt(3) == 1) {
                        this.dropItemRand(Items.EMERALD, 1, par3World, par4, par5, par6);
                        break;
                    }
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                    break;
                }
                case 20: {
                    if (par3World.rand.nextInt(3) == 1) {
                        this.dropItemRand(OreSpawnMain.MyRuby, 1, par3World, par4, par5, par6);
                        break;
                    }
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                    break;
                }
                case 21: {
                    if (par3World.rand.nextInt(3) == 1) {
                        this.dropItemRand(OreSpawnMain.MyAmethyst, 1, par3World, par4, par5, par6);
                        break;
                    }
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                    break;
                }
                case 22: {
                    this.dropItemRand(OreSpawnMain.MyMothScale, 1, par3World, par4, par5, par6);
                    break;
                }
                case 23: {
                    this.dropItemRand(OreSpawnMain.UraniumNugget, 1, par3World, par4, par5, par6);
                    break;
                }
                case 24: {
                    this.dropItemRand(OreSpawnMain.TitaniumNugget, 1, par3World, par4, par5, par6);
                    break;
                }
                case 25: {
                    if (par3World.rand.nextInt(2) == 1) {
                        this.dropItemRand(Items.DIAMOND, 1, par3World, par4, par5, par6);
                        break;
                    }
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                    break;
                }
                case 26: {
                    this.dropItemRand(Items.IRON_INGOT, 1, par3World, par4, par5, par6);
                    break;
                }
                case 27: {
                    this.dropItemRand(Items.GOLD_NUGGET, 1, par3World, par4, par5, par6);
                    break;
                }
                case 28: {
                    this.dropItemRand(Items.REDSTONE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 29: {
                    this.dropItemRand(Items.COAL, 1, par3World, par4, par5, par6);
                    break;
                }
                case 30: {
                    this.dropItemRand(OreSpawnMain.MyItemShoes, 1, par3World, par4, par5, par6);
                    break;
                }
                case 31: {
                    this.dropItemRand(OreSpawnMain.MyItemShoes_1, 1, par3World, par4, par5, par6);
                    break;
                }
                case 32: {
                    this.dropItemRand(OreSpawnMain.MyItemShoes_2, 1, par3World, par4, par5, par6);
                    break;
                }
                case 33: {
                    this.dropItemRand(OreSpawnMain.MyItemShoes_3, 1, par3World, par4, par5, par6);
                    break;
                }
                case 34: {
                    this.dropItemRand(Items.FISH, 1, par3World, par4, par5, par6);
                    break;
                }
                case 35: {
                    this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 36: {
                    this.dropItemRand(Items.BONE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 37: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.STONE), 1, par3World, par4, par5, par6);
                    break;
                }
                case 38: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.STONE_BUTTON), 1, par3World, par4, par5, par6);
                    break;
                }
                case 39: {
                    this.dropItemRand(Items.BUCKET, 1, par3World, par4, par5, par6);
                    break;
                }
                case 40: {
                    this.dropItemRand(Items.WATER_BUCKET, 1, par3World, par4, par5, par6);
                    break;
                }
            }
        }
        if (bid == Blocks.SAND) {
            i = par3World.rand.nextInt(60);
            switch (i) {
                case 0: {
                    this.dropItemRand(Items.IRON_HORSE_ARMOR, 1, par3World, par4, par5, par6);
                    break;
                }
                case 1: {
                    this.dropItemRand((Item)Items.SHEARS, 1, par3World, par4, par5, par6);
                    break;
                }
                case 2: {
                    this.dropItemRand(Items.CARROT_ON_A_STICK, 1, par3World, par4, par5, par6);
                    break;
                }
                case 3: {
                    this.dropItemRand(Items.POISONOUS_POTATO, 1, par3World, par4, par5, par6);
                    break;
                }
                case 4: {
                    this.dropItemRand(Items.ITEM_FRAME, 1, par3World, par4, par5, par6);
                    break;
                }
                case 5: {
                    this.dropItemRand(Items.BONE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 6: {
                    this.dropItemRand(Items.COMPASS, 1, par3World, par4, par5, par6);
                    break;
                }
                case 7: {
                    this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 8: {
                    this.dropItemRand(Items.SADDLE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 9: {
                    this.dropItemRand((Item)Items.IRON_HELMET, 1, par3World, par4, par5, par6);
                    break;
                }
                case 10: {
                    this.dropItemRand((Item)Items.IRON_CHESTPLATE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 11: {
                    this.dropItemRand((Item)Items.IRON_LEGGINGS, 1, par3World, par4, par5, par6);
                    break;
                }
                case 12: {
                    this.dropItemRand((Item)Items.IRON_BOOTS, 1, par3World, par4, par5, par6);
                    break;
                }
                case 13: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.SAND), 1, par3World, par4, par5, par6);
                    break;
                }
            }
        }
        if (bid == Blocks.GRAVEL) {
            i = par3World.rand.nextInt(60);
            switch (i) {
                case 0: {
                    this.dropItemRand(Items.FLINT, 1, par3World, par4, par5, par6);
                    break;
                }
                case 1: {
                    this.dropItemRand(OreSpawnMain.MySalt, 1, par3World, par4, par5, par6);
                    break;
                }
                case 2: {
                    this.dropItemRand(Items.FLINT_AND_STEEL, 1, par3World, par4, par5, par6);
                    break;
                }
                case 3: {
                    this.dropItemRand(Items.SPIDER_EYE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 4: {
                    this.dropItemRand(Items.ITEM_FRAME, 1, par3World, par4, par5, par6);
                    break;
                }
                case 5: {
                    this.dropItemRand(Items.FEATHER, 1, par3World, par4, par5, par6);
                    break;
                }
                case 6: {
                    this.dropItemRand(Items.STRING, 1, par3World, par4, par5, par6);
                    break;
                }
                case 7: {
                    this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 8: {
                    this.dropItemRand(Items.LEAD, 1, par3World, par4, par5, par6);
                    break;
                }
                case 9: {
                    this.dropItemRand(Items.NAME_TAG, 1, par3World, par4, par5, par6);
                    break;
                }
                case 10: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.SAND), 1, par3World, par4, par5, par6);
                    break;
                }
                case 11: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                    break;
                }
            }
        }
        if (bid == Blocks.DIRT) {
            i = par3World.rand.nextInt(60);
            switch (i) {
                case 0: {
                    this.dropItemRand(Items.STRING, 1, par3World, par4, par5, par6);
                    break;
                }
                case 1: {
                    this.dropItemRand(OreSpawnMain.MySalt, 1, par3World, par4, par5, par6);
                    break;
                }
                case 2: {
                    this.dropItemRand((Item)Items.SHEARS, 1, par3World, par4, par5, par6);
                    break;
                }
                case 3: {
                    this.dropItemRand(Items.STICK, 1, par3World, par4, par5, par6);
                    break;
                }
                case 4: {
                    this.dropItemRand(Items.BOWL, 1, par3World, par4, par5, par6);
                    break;
                }
                case 5: {
                    this.dropItemRand(Items.FLOWER_POT, 1, par3World, par4, par5, par6);
                    break;
                }
                case 6: {
                    this.dropItemRand(Items.SIGN, 1, par3World, par4, par5, par6);
                    break;
                }
                case 7: {
                    this.dropItemRand(Items.BRICK, 1, par3World, par4, par5, par6);
                    break;
                }
                case 8: {
                    this.dropItemRand(Items.PAPER, 1, par3World, par4, par5, par6);
                    break;
                }
                case 9: {
                    this.dropItemRand(Items.BONE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 10: {
                    this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                    break;
                }
                case 11: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.SAND), 1, par3World, par4, par5, par6);
                    break;
                }
                case 12: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                    break;
                }
                case 13: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIRT), 1, par3World, par4, par5, par6);
                    break;
                }
            }
        }
        if (bid == Blocks.GRASS) {
            i = par3World.rand.nextInt(60);
            switch (i) {
                case 0: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.YELLOW_FLOWER), 1, par3World, par4, par5, par6);
                    break;
                }
                case 1: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.RED_FLOWER), 1, par3World, par4, par5, par6);
                    break;
                }
                case 2: {
                    this.dropItemRand(Item.getItemFromBlock((Block)OreSpawnMain.MyFlowerPinkBlock), 1, par3World, par4, par5, par6);
                    break;
                }
                case 3: {
                    this.dropItemRand(Item.getItemFromBlock((Block)OreSpawnMain.MyFlowerBlueBlock), 1, par3World, par4, par5, par6);
                    break;
                }
                case 4: {
                    this.dropItemRand(Item.getItemFromBlock((Block)OreSpawnMain.MyFlowerBlackBlock), 1, par3World, par4, par5, par6);
                    break;
                }
                case 5: {
                    this.dropItemRand(Item.getItemFromBlock((Block)OreSpawnMain.MyFlowerScaryBlock), 1, par3World, par4, par5, par6);
                }
                case 6: {
                    this.dropItemRand(Items.WHEAT, 1, par3World, par4, par5, par6);
                    break;
                }
                case 7: {
                    this.dropItemRand(Items.PUMPKIN_SEEDS, 1, par3World, par4, par5, par6);
                    break;
                }
                case 8: {
                    this.dropItemRand(Items.MELON_SEEDS, 1, par3World, par4, par5, par6);
                    break;
                }
                case 9: {
                    this.dropItemRand(Items.CARROT, 1, par3World, par4, par5, par6);
                    break;
                }
                case 10: {
                    this.dropItemRand(Items.POTATO, 1, par3World, par4, par5, par6);
                    break;
                }
                case 11: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DEADBUSH), 1, par3World, par4, par5, par6);
                    break;
                }
                case 12: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                    break;
                }
                case 13: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIRT), 1, par3World, par4, par5, par6);
                    break;
                }
                case 14: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GRASS), 1, par3World, par4, par5, par6);
                    break;
                }
            }
        }
        par1ItemStack.damageItem(1, (net.minecraft.entity.EntityLivingBase)par2EntityPlayer);
        return true;
    }

    public String getMaterialName() {
        return "Unknown";
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

