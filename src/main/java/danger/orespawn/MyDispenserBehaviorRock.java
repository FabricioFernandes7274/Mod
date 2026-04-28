/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockDispenser
 *  net.minecraft.dispenser.BehaviorProjectileDispense
 *  net.minecraft.dispenser.IBlockSource
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


public class MyDispenserBehaviorRock extends EntityMob {
final class MyDispenserBehaviorRock
extends BehaviorProjectileDispense {
    MyDispenserBehaviorRock() {
    }

    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        World world = par1IBlockSource.getWorld();
        IPosition iposition = BlockDispenser.getIPositionFromBlockSource((IBlockSource)par1IBlockSource);
        EnumFacing enumfacing = BlockDispenser.getFacingDirection((int)par1IBlockSource.getBlockMetadata());
        IProjectile iprojectile = this.getProjectileEntity(world, iposition);
        iprojectile.setThrowableHeading((double)enumfacing.getFrontOffsetX(), (double)((float)enumfacing.getFrontOffsetY() + 0.1f), (double)enumfacing.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
        EntityThrownRock r = (EntityThrownRock)iprojectile;
        if (par2ItemStack.getItem() == OreSpawnMain.MySmallRock) {
            r.setRockType(1);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyRock) {
            r.setRockType(2);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyRedRock) {
            r.setRockType(3);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyGreenRock) {
            r.setRockType(4);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyBlueRock) {
            r.setRockType(5);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyPurpleRock) {
            r.setRockType(6);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MySpikeyRock) {
            r.setRockType(7);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyTNTRock) {
            r.setRockType(8);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyCrystalRedRock) {
            r.setRockType(9);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyCrystalGreenRock) {
            r.setRockType(10);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyCrystalBlueRock) {
            r.setRockType(11);
        }
        if (par2ItemStack.getItem() == OreSpawnMain.MyCrystalTNTRock) {
            r.setRockType(12);
        }
        world.spawnEntity((Entity)iprojectile);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }

    protected IProjectile getProjectileEntity(World worldIn, IPosition par2IPosition) {
        EntityThrownRock entityarrow = new EntityThrownRock(worldIn, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        return entityarrow;
    }
}


}