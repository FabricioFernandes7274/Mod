/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityMobSpawner
//  *  net.minecraft.util.WeightedRandomChestContent
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Trees {
//     public static final WeightedRandomChestContent[] CrystalChestContentsList = new WeightedRandomChestContent[]{new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalTermiteBlock), 0, 1, 5, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalFlowerRedBlock), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalFlowerBlueBlock), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalFlowerGreenBlock), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalFlowerYellowBlock), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalPlanksBlock), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalWorkbenchBlock), 0, 1, 1, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalFurnaceBlock), 0, 1, 1, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.MyTigersEyeBlock), 0, 1, 10, 5), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalStone), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalRat), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalFairy), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalCoal), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalGrass), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalCrystal), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.CrystalTorch), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.MyCrystalLeaves), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.MyCrystalLeaves2), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.MyCrystalLeaves3), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.MyCrystalTreeLog), 0, 1, 10, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)OreSpawnMain.TigersEye), 0, 1, 10, 5), new WeightedRandomChestContent(OreSpawnMain.MyCrystalWoodSword, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalWoodAxe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalWoodShovel, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalWoodPickaxe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalWoodHoe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalPinkSword, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalPinkAxe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalPinkShovel, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalPinkPickaxe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalPinkHoe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyTigersEyeSword, 0, 1, 1, 5), new WeightedRandomChestContent(OreSpawnMain.MyTigersEyeAxe, 0, 1, 1, 5), new WeightedRandomChestContent(OreSpawnMain.MyTigersEyeShovel, 0, 1, 1, 5), new WeightedRandomChestContent(OreSpawnMain.MyTigersEyePickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(OreSpawnMain.MyTigersEyeHoe, 0, 1, 1, 5), new WeightedRandomChestContent(OreSpawnMain.MyCrystalStoneSword, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalStoneAxe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalStoneShovel, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalStonePickaxe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalStoneHoe, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.MyTigersEyeIngot, 0, 1, 5, 5), new WeightedRandomChestContent(OreSpawnMain.MyCrystalPinkIngot, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.MyCrystalApple, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.MyPeacockFeather, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.MyPeacock, 0, 1, 10, 20), new WeightedRandomChestContent(OreSpawnMain.MyRawPeacock, 0, 1, 10, 20), new WeightedRandomChestContent(OreSpawnMain.MyRice, 0, 1, 10, 20), new WeightedRandomChestContent(OreSpawnMain.MyQuinoa, 0, 1, 10, 20), new WeightedRandomChestContent((Item)OreSpawnMain.CrystalPinkHelmet, 0, 1, 1, 10), new WeightedRandomChestContent((Item)OreSpawnMain.CrystalPinkBody, 0, 1, 1, 10), new WeightedRandomChestContent((Item)OreSpawnMain.CrystalPinkLegs, 0, 1, 1, 10), new WeightedRandomChestContent((Item)OreSpawnMain.CrystalPinkBoots, 0, 1, 1, 10), new WeightedRandomChestContent((Item)OreSpawnMain.TigersEyeHelmet, 0, 1, 1, 5), new WeightedRandomChestContent((Item)OreSpawnMain.TigersEyeBody, 0, 1, 1, 5), new WeightedRandomChestContent((Item)OreSpawnMain.TigersEyeLegs, 0, 1, 1, 5), new WeightedRandomChestContent((Item)OreSpawnMain.TigersEyeBoots, 0, 1, 1, 5), new WeightedRandomChestContent((Item)OreSpawnMain.PeacockFeatherHelmet, 0, 1, 1, 10), new WeightedRandomChestContent((Item)OreSpawnMain.PeacockFeatherBody, 0, 1, 1, 10), new WeightedRandomChestContent((Item)OreSpawnMain.PeacockFeatherLegs, 0, 1, 1, 10), new WeightedRandomChestContent((Item)OreSpawnMain.PeacockFeatherBoots, 0, 1, 1, 10), new WeightedRandomChestContent(OreSpawnMain.RotatorEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.VortexEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.PeacockEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.DungeonBeastEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.FairyEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.RatEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.FlounderEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.WhaleEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.IrukandjiEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.SkateEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.UrchinEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.GhostEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.GhostSkellyEgg, 0, 1, 5, 10), new WeightedRandomChestContent(OreSpawnMain.MySkateBow, 0, 1, 1, 2), new WeightedRandomChestContent(OreSpawnMain.MyIrukandjiArrow, 0, 5, 10, 2), new WeightedRandomChestContent(OreSpawnMain.MyIrukandji, 0, 2, 8, 5), new WeightedRandomChestContent(OreSpawnMain.MyUltimateBow, 0, 1, 1, 2), new WeightedRandomChestContent(OreSpawnMain.MyUltimateSword, 0, 1, 1, 2), new WeightedRandomChestContent(Items.IRON_INGOT, 0, 1, 4, 10), new WeightedRandomChestContent(Item.getItemFromBlock((Block)Blocks.LOG), 0, 1, 4, 10), new WeightedRandomChestContent(Items.GOLDEN_APPLE, 0, 1, 5, 2)};

    private void WindTreeBranch(World world, int x, int y, int z, int length, int dirx, int dirz) {
        for (int i = 1; i <= length; ++i) {
            OreSpawnMain.setBlockFast(world, x + i * dirx, y, z + i * dirz, Blocks.LOG, 0, 2);
            if (Blocks.AIR == world.getBlockState(new net.minecraft.util.math.BlockPos(x + i * dirx, y + 1, z + i * dirz)).getBlock()) {
                OreSpawnMain.setBlockFast(world, x + i * dirx, y + 1, z + i * dirz, (Block)Blocks.LEAVES, 0, 2);
            }
            if (i < length / 3 && Blocks.AIR == world.getBlockState(new net.minecraft.util.math.BlockPos(x + i * dirx, y + 2, z + i * dirz)).getBlock()) {
                OreSpawnMain.setBlockFast(world, x + i * dirx, y + 2, z + i * dirz, (Block)Blocks.LEAVES, 0, 2);
            }
            if (i <= length / 3) continue;
            if (Blocks.AIR == world.getBlockState(new net.minecraft.util.math.BlockPos(x + i * dirx + dirz, y, z + i * dirz + dirx)).getBlock()) {
                OreSpawnMain.setBlockFast(world, x + i * dirx + dirz, y, z + i * dirz + dirx, (Block)Blocks.LEAVES, 0, 2);
            }
            if (Blocks.AIR != world.getBlockState(new net.minecraft.util.math.BlockPos(x + i * dirx - dirz, y, z + i * dirz - dirx)).getBlock()) continue;
            OreSpawnMain.setBlockFast(world, x + i * dirx - dirz, y, z + i * dirz - dirx, (Block)Blocks.LEAVES, 0, 2);
        }
        if (Blocks.AIR == world.getBlockState(new BlockPos(x + (length + 1) * dirx, y, z + (length + 1)).getBlock() * dirz)) {
            OreSpawnMain.setBlockFast(world, x + (length + 1) * dirx, y, z + (length + 1) * dirz, (Block)Blocks.LEAVES, 0, 2);
        }
        if (Blocks.AIR == world.getBlockState(new BlockPos(x + (length + 2) * dirx, y, z + (length + 2)).getBlock() * dirz)) {
            OreSpawnMain.setBlockFast(world, x + (length + 2) * dirx, y, z + (length + 2) * dirz, (Block)Blocks.LEAVES, 0, 2);
        }
    }

    public void WindTree(World world, int x, int y, int z, int dir) {
        Block bid;
        if (dir < 0 || dir > 3) {
            return;
        }
        int dirx = 1;
        int dirz = 0;
        if (dir == 1) {
            dirx = -1;
            dirz = 0;
        }
        if (dir == 2) {
            dirx = 0;
            dirz = 1;
        }
        if (dir == 3) {
            dirx = 0;
            dirz = -1;
        }
        if ((bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock()) != Blocks.GRASS && bid != Blocks.DIRT) {
            return;
        }
        int height = world.rand.nextInt(8) + 40;
        int width = world.rand.nextInt(4) + 8;
        for (int j = 0; j < height; ++j) {
            OreSpawnMain.setBlockFast(world, x, j + y, z, Blocks.LOG, 0, 2);
            if (j <= height / 5) continue;
            OreSpawnMain.setBlockFast(world, x + dirx, j + y, z + dirz, (Block)Blocks.LEAVES, 0, 2);
            if (j <= height / 4 || j % 4 != 0) continue;
            this.WindTreeBranch(world, x, j + y, z, height - j, dirx, dirz);
        }
        OreSpawnMain.setBlockFast(world, x, y + height, z, (Block)Blocks.LEAVES, 0, 2);
    }

    private void SkyTreeBranch(World world, int x, int y, int z, int length, int dirx, int dirz) {
        for (int i = 1; i < length; ++i) {
            OreSpawnMain.setBlockFast(world, x + i * dirx, y, z + i * dirz, OreSpawnMain.MySkyTreeLog, 0, 2);
            if (Blocks.AIR == world.getBlockState(new net.minecraft.util.math.BlockPos(x + i * dirx, y + 1, z + i * dirz)).getBlock()) {
                OreSpawnMain.setBlockFast(world, x + i * dirx, y + 1, z + i * dirz, (Block)Blocks.LEAVES, 0, 2);
            }
            if (Blocks.AIR == world.getBlockState(new net.minecraft.util.math.BlockPos(x + i * dirx + dirz, y, z + i * dirz + dirx)).getBlock()) {
                OreSpawnMain.setBlockFast(world, x + i * dirx + dirz, y, z + i * dirz + dirx, (Block)Blocks.LEAVES, 0, 2);
            }
            if (Blocks.AIR != world.getBlockState(new net.minecraft.util.math.BlockPos(x + i * dirx - dirz, y, z + i * dirz - dirx)).getBlock()) continue;
            OreSpawnMain.setBlockFast(world, x + i * dirx - dirz, y, z + i * dirz - dirx, (Block)Blocks.LEAVES, 0, 2);
        }
        if (Blocks.AIR == world.getBlockState(new net.minecraft.util.math.BlockPos(x + length * dirx, y, z + length * dirz)).getBlock()) {
            OreSpawnMain.setBlockFast(world, x + length * dirx, y, z + length * dirz, (Block)Blocks.LEAVES, 0, 2);
        }
    }

    public void SkyTree(World world, int x, int y, int z) {
        Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock();
        if (bid != Blocks.GRASS && bid != Blocks.DIRT) {
            return;
        }
        int height = world.rand.nextInt(15) + 190;
        if (height - y < 20) {
            return;
        }
        int width = world.rand.nextInt(10) + 25;
        for (int j = y; j <= height; ++j) {
            OreSpawnMain.setBlockFast(world, x, j, z, OreSpawnMain.MySkyTreeLog, 0, 2);
        }
        OreSpawnMain.setBlockFast(world, x, height + 1, z, (Block)Blocks.LEAVES, 0, 2);
        this.SkyTreeBranch(world, x, height, z, width, 1, 0);
        this.SkyTreeBranch(world, x, height, z, width, -1, 0);
        this.SkyTreeBranch(world, x, height, z, width, 0, 1);
        this.SkyTreeBranch(world, x, height, z, width, 0, -1);
        height -= 5;
        this.SkyTreeBranch(world, x, height -= world.rand.nextInt(4), z, width /= 3, 1, 0);
        this.SkyTreeBranch(world, x, height, z, width, -1, 0);
        this.SkyTreeBranch(world, x, height, z, width, 0, 1);
        this.SkyTreeBranch(world, x, height, z, width, 0, -1);
    }

    public void DuplicatorTree(World world, int x, int y, int z) {
        int j;
        int i;
        int realy = y;
        Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 1, z)).getBlock();
        if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
            bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 2, z)).getBlock();
            if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
                bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 3, z)).getBlock();
                if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
                    return;
                }
                realy = y - 3;
            } else {
                realy = y - 2;
            }
            return;
        }
        realy = y - 1;
        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, realy + 1, z)).getBlock();
        if (bid != OreSpawnMain.MyDT) {
            OreSpawnMain.setBlockFast(world, x, realy + 1, z, OreSpawnMain.MyDT, 0, 2);
            return;
        }
        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, realy + 2, z)).getBlock();
        if (bid != OreSpawnMain.MyDT) {
            OreSpawnMain.setBlockFast(world, x, realy + 2, z, OreSpawnMain.MyDT, 0, 2);
            return;
        }
        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, realy + 3, z)).getBlock();
        if (bid != OreSpawnMain.MyDT) {
            OreSpawnMain.setBlockFast(world, x, realy + 3, z, OreSpawnMain.MyDT, 0, 2);
            return;
        }
        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, realy + 4, z)).getBlock();
        if (bid != OreSpawnMain.MyAppleLeaves) {
            OreSpawnMain.setBlockFast(world, x, realy + 4, z, OreSpawnMain.MyAppleLeaves, 0, 2);
            return;
        }
        for (i = -1; i <= 1; ++i) {
            for (j = -1; j <= 1; ++j) {
                if (j == 0 && i == 0 || (bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, realy + 3, z + j)).getBlock()) == OreSpawnMain.MyAppleLeaves) continue;
                OreSpawnMain.setBlockFast(world, x + i, realy + 3, z + j, OreSpawnMain.MyAppleLeaves, 0, 2);
                return;
            }
        }
        Block bidm = Blocks.AIR;
        for (int tries = 0; tries < 20 && (bidm == Blocks.AIR || bidm == OreSpawnMain.MyDT); ++tries) {
            i = world.rand.nextInt(5) - 2;
            j = world.rand.nextInt(5) - 2;
            bidm = world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, realy + 1, z + j)).getBlock();
            int meta = world.getBlockMetadata(x + i, realy + 1, z + j);
            if (bidm == Blocks.AIR || bidm == OreSpawnMain.MyDT) continue;
            for (int k = 0; k < 20; ++k) {
                i = world.rand.nextInt(5) - 2;
                bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, realy + 1, z + (j = world.rand.nextInt(5)).getBlock() - 2));
                if (bid != Blocks.AIR) continue;
                world.setBlockState(new net.minecraft.util.math.BlockPos(x + i, realy + 1, z + j), bidm.getStateFromMeta(meta), 2);
                return;
            }
        }
    }

    private void make_leaves(World world, int x, int y, int z) {
        for (int l1 = -3; l1 <= 3; ++l1) {
            for (int l2 = -3; l2 <= 3; ++l2) {
                for (int l3 = 0; l3 <= 2; ++l3) {
                    Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + l1, y + l3, z + l2)).getBlock();
                    if (bid != Blocks.AIR) continue;
                    OreSpawnMain.setBlockFast(world, x + l1, y + l3, z + l2, OreSpawnMain.MyExperienceLeaves, 0, 2);
                }
            }
        }
    }

    private void grow_small_branch(World world, int x, int y, int z, int xdir, int zdir, int xxdir, int zzdir) {
        int n;
        int i2 = 0;
        int k2 = 0;
        int j2 = 0;
        int i = x;
        int j = y;
        int k = z;
        int grow = 4 + world.rand.nextInt(2);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
            this.make_leaves(world, i, j, k);
            ++j;
            i2 = i += xdir;
            k2 = k += zdir;
        }
        grow = 4 + world.rand.nextInt(3);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
            this.make_leaves(world, i, j, k);
            i += xdir;
            k += zdir;
        }
        grow = 4 + world.rand.nextInt(3);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i2, j, k2, Blocks.LOG, 0, 2);
            this.make_leaves(world, i2, j, k2);
            i2 += xxdir;
            k2 += zzdir;
        }
        j2 = --j;
        grow = 3 + world.rand.nextInt(3);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
            this.make_leaves(world, i, j, k);
            i += xdir;
            k += zdir;
            --j;
        }
        grow = 3 + world.rand.nextInt(3);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i2, j2, k2, Blocks.LOG, 0, 2);
            this.make_leaves(world, i2, j2, k2);
            i2 += xxdir;
            k2 += zzdir;
            --j2;
        }
    }

    private void grow_branch(World world, int x, int y, int z, int xdir, int zdir, int xxdir, int zzdir) {
        int n;
        int i2 = 0;
        int k2 = 0;
        int j2 = 0;
        int i = x;
        int j = y;
        int k = z;
        int grow = 5 + world.rand.nextInt(4);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
            this.make_leaves(world, i, j, k);
            ++j;
            i2 = i += xdir;
            k2 = k += zdir;
        }
        grow = 6 + world.rand.nextInt(5);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
            this.make_leaves(world, i, j, k);
            i += xdir;
            k += zdir;
        }
        grow = 6 + world.rand.nextInt(5);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i2, j, k2, Blocks.LOG, 0, 2);
            this.make_leaves(world, i2, j, k2);
            i2 += xxdir;
            k2 += zzdir;
        }
        j2 = --j;
        grow = 4 + world.rand.nextInt(4);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
            this.make_leaves(world, i, j, k);
            i += xdir;
            k += zdir;
            --j;
        }
        grow = 4 + world.rand.nextInt(4);
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i2, j2, k2, Blocks.LOG, 0, 2);
            this.make_leaves(world, i2, j2, k2);
            i2 += xxdir;
            k2 += zzdir;
            --j2;
        }
    }

    public void ExperienceTree(World world, int x, int y, int z) {
        int k;
        int i;
        int j;
        Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock();
        if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
            return;
        }
        for (j = 1; j < 6; ++j) {
            for (i = 0; i < 2; ++i) {
                for (k = 0; k < 2; ++k) {
                    OreSpawnMain.setBlockFast(world, x + i, y + j, z + k, Blocks.LOG, 0, 2);
                }
            }
        }
        this.grow_branch(world, x, y + 6, z, 0, 1, 1, 1);
        this.grow_branch(world, x + 1, y + 6, z, 1, 0, 1, -1);
        this.grow_branch(world, x, y + 6, z + 1, -1, 0, -1, 1);
        this.grow_branch(world, x + 1, y + 6, z + 1, 0, -1, -1, -1);
        for (j = 7; j < 19; ++j) {
            for (i = 0; i < 2; ++i) {
                for (k = 0; k < 2; ++k) {
                    OreSpawnMain.setBlockFast(world, x + i, y + j, z + k, Blocks.LOG, 0, 2);
                }
            }
        }
        this.grow_small_branch(world, x, y + 19, z, 0, 1, -1, 1);
        this.grow_small_branch(world, x + 1, y + 19, z, 1, 0, 1, 1);
        this.grow_small_branch(world, x, y + 19, z + 1, -1, 0, -1, -1);
        this.grow_small_branch(world, x + 1, y + 19, z + 1, 0, -1, 1, -1);
        int grow = 5 + world.rand.nextInt(6);
        for (j = 19; j < 19 + grow; ++j) {
            for (i = 0; i < 2; ++i) {
                for (k = 0; k < 2; ++k) {
                    OreSpawnMain.setBlockFast(world, x + i, y + j, z + k, Blocks.LOG, 0, 2);
                    this.make_leaves(world, x + i, y + j, z + k);
                }
            }
        }
    }

    public void SmallTree(World world, int x, int y, int z) {
        int realy = y;
        Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 1, z)).getBlock();
        if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
            bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 2, z)).getBlock();
            if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
                bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 3, z)).getBlock();
                if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.FARMLAND) {
                    OreSpawnMain.setBlockFast(world, x, y, z, Blocks.AIR, 0, 2);
                    return;
                }
                realy = y - 3;
            } else {
                realy = y - 2;
            }
            return;
        }
        realy = y - 1;
        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, realy + 1, z)).getBlock();
        if (bid == Blocks.AIR) {
            OreSpawnMain.setBlockFast(world, x, realy + 1, z, OreSpawnMain.MySkyTreeLog, 0, 2);
        }
        if (world.rand.nextInt(2) == 1) {
            bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, realy + 2, z)).getBlock();
            if (bid == Blocks.AIR) {
                OreSpawnMain.setBlockFast(world, x, realy + 2, z, OreSpawnMain.MySkyTreeLog, 0, 2);
            }
            if (world.rand.nextInt(2) == 1) {
                bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, realy + 3, z)).getBlock();
                if (bid == Blocks.AIR) {
                    OreSpawnMain.setBlockFast(world, x, realy + 3, z, OreSpawnMain.MySkyTreeLog, 0, 2);
                }
            } else {
                --realy;
            }
        } else {
            realy -= 2;
        }
        if ((bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, realy + 4, z)).getBlock()) == Blocks.AIR) {
            OreSpawnMain.setBlockFast(world, x, realy + 4, z, OreSpawnMain.MyAppleLeaves, 0, 2);
        }
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, realy + 3, z + j)).getBlock();
                if (bid != Blocks.AIR) continue;
                OreSpawnMain.setBlockFast(world, x + i, realy + 3, z + j, OreSpawnMain.MyAppleLeaves, 0, 2);
            }
        }
    }

    public void makeScragglyBranch(World world, int x, int y, int z, int len, int biasx, int biasz) {
        for (int k = 0; k < len; ++k) {
            int iy;
            Block bid;
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2) + biasx;
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2) + biasz;
            if (ix > 1) {
                ix = 1;
            }
            if (ix < -1) {
                ix = -1;
            }
            if (iz > 1) {
                iz = 1;
            }
            if (iz < -1) {
                iz = -1;
            }
            if ((bid = world.getBlockState(new BlockPos(x += ix, y += (iy = world.rand.nextInt(3) > 0 ? 1 : 0), z += iz)).getBlock()) != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }
            OreSpawnMain.setBlockFast(world, x, y, z, Blocks.LOG, 0, 2);
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (world.rand.nextInt(2) != 1 || (bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + m, y, z + n)).getBlock()) != Blocks.AIR) continue;
                    OreSpawnMain.setBlockFast(world, x + m, y, z + n, OreSpawnMain.MyAppleLeaves, 0, 2);
                }
            }
            if (world.rand.nextInt(2) != 1 || (bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y + 1, z)).getBlock()) != Blocks.AIR) continue;
            OreSpawnMain.setBlockFast(world, x, y + 1, z, OreSpawnMain.MyAppleLeaves, 0, 2);
        }
    }

    public void ScragglyTreeWithBranches(World world, int x, int y, int z) {
        Block bid;
        int k;
        int i = 1 + world.rand.nextInt(3);
        int j = i + world.rand.nextInt(12);
        for (k = 0; k < i; ++k) {
            bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y + k, z)).getBlock();
            if (k >= 1 && bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) {
                return;
            }
            OreSpawnMain.setBlockFast(world, x, y + k, z, Blocks.LOG, 0, 2);
        }
        y += i - 1;
        for (k = i; k < j; ++k) {
            int ix = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iz = world.rand.nextInt(2) - world.rand.nextInt(2);
            int iy = world.rand.nextInt(4) > 0 ? 1 : 0;
            bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x += ix, y += iy, z += iz)).getBlock();
            if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawnMain.MyAppleLeaves) break;
            OreSpawnMain.setBlockFast(world, x, y, z, Blocks.LOG, 0, 2);
            if (world.rand.nextInt(4) == 1) {
                this.makeScragglyBranch(world, x, y, z, world.rand.nextInt(1 + j - k), world.rand.nextInt(2) - world.rand.nextInt(2), world.rand.nextInt(2) - world.rand.nextInt(2));
            }
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (world.rand.nextInt(2) != 1 || (bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + m, y, z + n)).getBlock()) != Blocks.AIR) continue;
                    OreSpawnMain.setBlockFast(world, x + m, y, z + n, OreSpawnMain.MyAppleLeaves, 0, 2);
                }
            }
            if (world.rand.nextInt(2) != 1 || (bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y + 1, z)).getBlock()) != Blocks.AIR) continue;
            OreSpawnMain.setBlockFast(world, x, y + 1, z, OreSpawnMain.MyAppleLeaves, 0, 2);
        }
    }

    public void FairyTree(World world, int x, int y, int z) {
        int k;
        int i;
        int j;
        for (j = 1; j < 6; ++j) {
            for (i = 0; i < 2; ++i) {
                for (k = 0; k < 2; ++k) {
                    OreSpawnMain.setBlockFast(world, x + i, y + j, z + k, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                }
            }
        }
        this.grow_crystal_branch(world, x, y + 5, z, 0, 1, 1, 1, -1);
        this.grow_crystal_branch(world, x + 1, y + 5, z, 1, 0, 1, -1, -1);
        this.grow_crystal_branch(world, x, y + 5, z + 1, -1, 0, -1, 1, -1);
        this.grow_crystal_branch(world, x + 1, y + 5, z + 1, 0, -1, -1, -1, -1);
        this.grow_crystal_branch(world, x, y + 6, z, 0, 1, -1, 1, -1);
        this.grow_crystal_branch(world, x + 1, y + 6, z, 1, 0, 1, 1, -1);
        this.grow_crystal_branch(world, x, y + 6, z + 1, -1, 0, -1, -1, -1);
        this.grow_crystal_branch(world, x + 1, y + 6, z + 1, 0, -1, 1, -1, -1);
        int grow = 5 + world.rand.nextInt(5);
        for (j = 6; j < 6 + grow; ++j) {
            for (i = 0; i < 2; ++i) {
                for (k = 0; k < 2; ++k) {
                    OreSpawnMain.setBlockFast(world, x + i, y + j, z + k, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                    this.make_crystal_leaves(world, x + i, y + j, z + k);
                }
            }
        }
        world.setBlockState(new net.minecraft.util.math.BlockPos(x - 1, y + 1, z), Blocks.MOB_SPAWNER.getStateFromMeta(0), 2);
        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new net.minecraft.util.math.BlockPos(new net.minecraft.util.math.BlockPos(x - 1, y + 1, z)));
        if (tileentitymobspawner != null) {
//             tileentitymobspawner.getSpawnerBaseLogic().setEntityName("Fairy");
        }
        world.setBlockState(new net.minecraft.util.math.BlockPos(x + 2, y + 1, z), (Block)Blocks.CHEST.getStateFromMeta(0), 2);
        TileEntityChest chest = (TileEntityChest)world.getTileEntity(new net.minecraft.util.math.BlockPos(new net.minecraft.util.math.BlockPos(x + 2, y + 1, z)));
        if (chest != null) {
//             // TODO: WeightedRandomChestContent removido - usar LootTables
//             // // TODO: WeightedRandomChestContent removido - usar LootTables
//             // // TODO: WeightedRandomChestContent removido - usar LootTables
//             // WeightedRandomChestContent.generateChestContents((Random)world.rand, (WeightedRandomChestContent[])CrystalChestContentsList, (IInventory)chest, (int)(1 + world.rand.nextInt(5)));
        }
    }

    private void make_crystal_leaves(World world, int x, int y, int z) {
        for (int l1 = -2; l1 <= 2; ++l1) {
            for (int l2 = -2; l2 <= 2; ++l2) {
                for (int l3 = 0; l3 <= 1; ++l3) {
                    Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + l1, y + l3, z + l2)).getBlock();
                    if (bid != Blocks.AIR) continue;
                    OreSpawnMain.setBlockFast(world, x + l1, y + l3, z + l2, OreSpawnMain.MyCrystalLeaves3, 0, 2);
                }
            }
        }
    }

    private void make_crystal_castle_leaves(World world, int x, int y, int z) {
        for (int l1 = -1; l1 <= 1; ++l1) {
            for (int l2 = -1; l2 <= 1; ++l2) {
                for (int l3 = 0; l3 <= 1; ++l3) {
                    Block bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + l1, y + l3, z + l2)).getBlock();
                    if (bid != Blocks.AIR) continue;
                    if (l3 != 0) {
                        OreSpawnMain.setBlockFast(world, x + l1, y + l3, z + l2, OreSpawnMain.MyCrystalLeaves3, 0, 2);
                        continue;
                    }
                    OreSpawnMain.setBlockFast(world, x + l1, y + l3, z + l2, OreSpawnMain.MyCrystalLeaves2, 0, 2);
                }
            }
        }
    }

    private void grow_crystal_branch(World world, int x, int y, int z, int xdir, int zdir, int xxdir, int zzdir, int ydir) {
        int n;
        int i2 = 0;
        int k2 = 0;
        int j2 = 0;
        int i = x;
        int j = y;
        int k = z;
        int grow = 4 + world.rand.nextInt(4);
        if (OreSpawnMain.LessLag == 1) {
            --grow;
        }
        if (OreSpawnMain.LessLag == 2) {
            grow -= 2;
        }
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            this.make_crystal_leaves(world, i, j, k);
            ++j;
            i2 = i += xdir;
            k2 = k += zdir;
        }
        grow = 5 + world.rand.nextInt(5);
        if (OreSpawnMain.LessLag == 1) {
            --grow;
        }
        if (OreSpawnMain.LessLag == 2) {
            grow -= 2;
        }
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            this.make_crystal_leaves(world, i, j, k);
            i += xdir;
            k += zdir;
        }
        grow = 5 + world.rand.nextInt(5);
        if (OreSpawnMain.LessLag == 1) {
            --grow;
        }
        if (OreSpawnMain.LessLag == 2) {
            grow -= 2;
        }
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i2, j, k2, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            this.make_crystal_leaves(world, i2, j, k2);
            i2 += xxdir;
            k2 += zzdir;
        }
        j2 = --j;
        grow = 4 + world.rand.nextInt(4);
        if (OreSpawnMain.LessLag == 1) {
            --grow;
        }
        if (OreSpawnMain.LessLag == 2) {
            grow -= 2;
        }
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i, j, k, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            this.make_crystal_leaves(world, i, j, k);
            i += xdir;
            k += zdir;
            j += ydir;
        }
        grow = 4 + world.rand.nextInt(4);
        if (OreSpawnMain.LessLag == 1) {
            --grow;
        }
        if (OreSpawnMain.LessLag == 2) {
            grow -= 2;
        }
        for (n = 0; n < grow; ++n) {
            OreSpawnMain.setBlockFast(world, i2, j2, k2, OreSpawnMain.MyCrystalTreeLog, 0, 2);
            this.make_crystal_leaves(world, i2, j2, k2);
            i2 += xxdir;
            k2 += zzdir;
            j2 += ydir;
        }
    }

    public void addSomething(World world, int x, int y, int z) {
        int i = world.rand.nextInt(3);
        if (i == 1) {
            world.setBlockState(new net.minecraft.util.math.BlockPos(x, y + 1, z), Blocks.MOB_SPAWNER.getStateFromMeta(0), 2);
            TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new net.minecraft.util.math.BlockPos(new net.minecraft.util.math.BlockPos(x, y + 1, z)));
            if (tileentitymobspawner != null) {
//                 tileentitymobspawner.getSpawnerBaseLogic().setEntityName("Fairy");
            }
        }
        if (i == 2) {
            world.setBlockState(new net.minecraft.util.math.BlockPos(x, y + 1, z), (Block)Blocks.CHEST.getStateFromMeta(0), 2);
            TileEntityChest chest = (TileEntityChest)world.getTileEntity(new net.minecraft.util.math.BlockPos(new net.minecraft.util.math.BlockPos(x, y + 1, z)));
            if (chest != null) {
//                 // TODO: WeightedRandomChestContent removido - usar LootTables
//             // // TODO: WeightedRandomChestContent removido - usar LootTables
//             // // TODO: WeightedRandomChestContent removido - usar LootTables
//             // WeightedRandomChestContent.generateChestContents((Random)world.rand, (WeightedRandomChestContent[])CrystalChestContentsList, (IInventory)chest, (int)(1 + world.rand.nextInt(5)));
            }
        }
    }

    public void FairyCastleTree(World world, int x, int y, int z) {
        int nc = 6;
        if (OreSpawnMain.LessLag == 1) {
            --nc;
        }
        if (OreSpawnMain.LessLag == 2) {
            nc -= 2;
        }
        int j = 3 + world.rand.nextInt(3);
        int spread = 0;
        for (int iter = 0; iter < nc; ++iter) {
            int k;
            int i;
            int grow = 4 + world.rand.nextInt(3);
            int width = 1 + world.rand.nextInt(3);
            int randy = world.rand.nextInt(3) - 1;
            for (i = -width; i <= width; ++i) {
                for (k = -width; k <= width; ++k) {
                    OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy, z + k, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                    if (i == -width || i == width || k == -width || k == width) {
                        this.make_crystal_castle_leaves(world, x + i + spread, y + j + randy, z + k);
                    }
                    if (iter != 0 && i == 0 && k == 0) {
                        this.addSomething(world, x + i + spread, y + j + randy, z + k);
                    }
                    if (i == -width && (k == -width || k == width)) {
                        OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy + 1, z + k, OreSpawnMain.CrystalTorch, 0, 2);
                    }
                    if (i != width || k != -width && k != width) continue;
                    OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy + 1, z + k, OreSpawnMain.CrystalTorch, 0, 2);
                }
            }
            if (iter != 0) {
                width = 1 + world.rand.nextInt(3 + iter);
                randy = world.rand.nextInt(3) - 1;
                for (i = -width; i <= width; ++i) {
                    for (k = -width; k <= width; ++k) {
                        OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy, z + k, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                        if (i == -width || i == width || k == -width || k == width) {
                            this.make_crystal_castle_leaves(world, x + i - spread, y + j + randy, z + k);
                        }
                        if (i == 0 && k == 0) {
                            this.addSomething(world, x + i - spread, y + j + randy, z + k);
                        }
                        if (i == -width && (k == -width || k == width)) {
                            OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy + 1, z + k, OreSpawnMain.CrystalTorch, 0, 2);
                        }
                        if (i != width || k != -width && k != width) continue;
                        OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy + 1, z + k, OreSpawnMain.CrystalTorch, 0, 2);
                    }
                }
                width = 1 + world.rand.nextInt(3 + iter);
                randy = world.rand.nextInt(3) - 1;
                for (i = -width; i <= width; ++i) {
                    for (k = -width; k <= width; ++k) {
                        OreSpawnMain.setBlockFast(world, x + i, y + j + randy, z + k + spread, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                        if (i == -width || i == width || k == -width || k == width) {
                            this.make_crystal_castle_leaves(world, x + i, y + j + randy, z + k + spread);
                        }
                        if (i == 0 && k == 0) {
                            this.addSomething(world, x + i, y + j + randy, z + k + spread);
                        }
                        if (i == -width && (k == -width || k == width)) {
                            OreSpawnMain.setBlockFast(world, x + i, y + j + randy + 1, z + k + spread, OreSpawnMain.CrystalTorch, 0, 2);
                        }
                        if (i != width || k != -width && k != width) continue;
                        OreSpawnMain.setBlockFast(world, x + i, y + j + randy + 1, z + k + spread, OreSpawnMain.CrystalTorch, 0, 2);
                    }
                }
                width = 1 + world.rand.nextInt(3 + iter);
                randy = world.rand.nextInt(3) - 1;
                for (i = -width; i <= width; ++i) {
                    for (k = -width; k <= width; ++k) {
                        OreSpawnMain.setBlockFast(world, x + i, y + j + randy, z + k - spread, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                        if (i == -width || i == width || k == -width || k == width) {
                            this.make_crystal_castle_leaves(world, x + i, y + j + randy, z + k - spread);
                        }
                        if (i == 0 && k == 0) {
                            this.addSomething(world, x + i, y + j + randy, z + k - spread);
                        }
                        if (i == -width && (k == -width || k == width)) {
                            OreSpawnMain.setBlockFast(world, x + i, y + j + randy + 1, z + k - spread, OreSpawnMain.CrystalTorch, 0, 2);
                        }
                        if (i != width || k != -width && k != width) continue;
                        OreSpawnMain.setBlockFast(world, x + i, y + j + randy + 1, z + k - spread, OreSpawnMain.CrystalTorch, 0, 2);
                    }
                }
            }
            if (iter >= 2) {
                width = 1 + world.rand.nextInt(3 + iter);
                randy = world.rand.nextInt(3) - 1;
                for (i = -width; i <= width; ++i) {
                    for (k = -width; k <= width; ++k) {
                        OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy, z + k + spread, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                        if (i == -width || i == width || k == -width || k == width) {
                            this.make_crystal_castle_leaves(world, x + i + spread, y + j + randy, z + k + spread);
                        }
                        if (i == 0 && k == 0) {
                            this.addSomething(world, x + i + spread, y + j + randy, z + k + spread);
                        }
                        if (i == -width && (k == -width || k == width)) {
                            OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy + 1, z + k + spread, OreSpawnMain.CrystalTorch, 0, 2);
                        }
                        if (i != width || k != -width && k != width) continue;
                        OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy + 1, z + k + spread, OreSpawnMain.CrystalTorch, 0, 2);
                    }
                }
                width = 1 + world.rand.nextInt(3 + iter);
                randy = world.rand.nextInt(3) - 1;
                for (i = -width; i <= width; ++i) {
                    for (k = -width; k <= width; ++k) {
                        OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy, z + k - spread, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                        if (i == -width || i == width || k == -width || k == width) {
                            this.make_crystal_castle_leaves(world, x + i - spread, y + j + randy, z + k - spread);
                        }
                        if (i == 0 && k == 0) {
                            this.addSomething(world, x + i - spread, y + j + randy, z + k - spread);
                        }
                        if (i == -width && (k == -width || k == width)) {
                            OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy + 1, z + k - spread, OreSpawnMain.CrystalTorch, 0, 2);
                        }
                        if (i != width || k != -width && k != width) continue;
                        OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy + 1, z + k - spread, OreSpawnMain.CrystalTorch, 0, 2);
                    }
                }
                width = 1 + world.rand.nextInt(3 + iter);
                randy = world.rand.nextInt(3) - 1;
                for (i = -width; i <= width; ++i) {
                    for (k = -width; k <= width; ++k) {
                        OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy, z + k + spread, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                        if (i == -width || i == width || k == -width || k == width) {
                            this.make_crystal_castle_leaves(world, x + i - spread, y + j + randy, z + k + spread);
                        }
                        if (i == 0 && k == 0) {
                            this.addSomething(world, x + i - spread, y + j + randy, z + k + spread);
                        }
                        if (i == -width && (k == -width || k == width)) {
                            OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy + 1, z + k + spread, OreSpawnMain.CrystalTorch, 0, 2);
                        }
                        if (i != width || k != -width && k != width) continue;
                        OreSpawnMain.setBlockFast(world, x + i - spread, y + j + randy + 1, z + k + spread, OreSpawnMain.CrystalTorch, 0, 2);
                    }
                }
                width = 1 + world.rand.nextInt(3 + iter);
                randy = world.rand.nextInt(3) - 1;
                for (i = -width; i <= width; ++i) {
                    for (k = -width; k <= width; ++k) {
                        OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy, z + k - spread, OreSpawnMain.MyCrystalTreeLog, 0, 2);
                        if (i == -width || i == width || k == -width || k == width) {
                            this.make_crystal_castle_leaves(world, x + i + spread, y + j + randy, z + k - spread);
                        }
                        if (i == 0 && k == 0) {
                            this.addSomething(world, x + i + spread, y + j + randy, z + k - spread);
                        }
                        if (i == -width && (k == -width || k == width)) {
                            OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy + 1, z + k - spread, OreSpawnMain.CrystalTorch, 0, 2);
                        }
                        if (i != width || k != -width && k != width) continue;
                        OreSpawnMain.setBlockFast(world, x + i + spread, y + j + randy + 1, z + k - spread, OreSpawnMain.CrystalTorch, 0, 2);
                    }
                }
            }
            j += grow;
            if (iter == 0) {
                spread = 3;
            }
            spread += grow;
        }
    }
}

