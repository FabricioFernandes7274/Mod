/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockDispenser
 *  net.minecraft.dispenser.BehaviorDefaultDispenseItem
 *  net.minecraft.dispenser.IBlockSource
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 */
package danger.orespawn;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

final class DispenserBehaviorOreSpawnEgg
extends BehaviorDefaultDispenseItem {
    DispenserBehaviorOreSpawnEgg() {
    }

    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        EnumFacing enumfacing = BlockDispenser.getFacingDirection((int)par1IBlockSource.getBlockMetadata());
        double d0 = par1IBlockSource.getX() + (double)enumfacing.getFrontOffsetX() * 2.0;
        double d1 = (float)par1IBlockSource.getYInt() + 0.2f;
        double d2 = par1IBlockSource.getZ() + (double)enumfacing.getFrontOffsetZ() * 2.0;
        Item it = par2ItemStack.getItem();
        if (it instanceof ItemSpawnEgg) {
            ItemSpawnEgg ise = (ItemSpawnEgg)it;
            Entity entity = ItemSpawnEgg.spawn_something(ise.my_id, par1IBlockSource.getWorld(), (int)d0, (int)d1, (int)d2);
            if (entity instanceof net.minecraft.entity.EntityLivingBase && par2ItemStack.hasDisplayName()) {
                ((EntityLiving)entity).setCustomNameTag(par2ItemStack.getDisplayName());
            }
        }
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
}

