package danger.orespawn;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class DispenserBehaviorOreSpawnEgg extends BehaviorDefaultDispenseItem {

    public DispenserBehaviorOreSpawnEgg() {
    }

    @Override
    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        // Na 1.12.2 pegamos a direção através do BlockState
        EnumFacing enumfacing = source.getBlockState().getValue(BlockDispenser.FACING);
        
        double x = source.getX() + (double)enumfacing.getFrontOffsetX();
        double y = (double)source.getBlockPos().getY() + 0.2D;
        double z = source.getZ() + (double)enumfacing.getFrontOffsetZ();
        
        Item item = stack.getItem();
        
        if (item instanceof ItemSpawnEgg) {
            ItemSpawnEgg ise = (ItemSpawnEgg)item;
            
            // Chama o método de spawn customizado do OreSpawn
            // Certifique-se de que ItemSpawnEgg.spawn_something existe e está atualizado
            Entity entity = ItemSpawnEgg.spawn_something(ise.my_id, source.getWorld(), x, y, z);
            
            if (entity instanceof EntityLiving && stack.hasDisplayName()) {
                ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
            }
        }

        // Remove 1 item do stack
        stack.shrink(1);
        return stack;
    }
}