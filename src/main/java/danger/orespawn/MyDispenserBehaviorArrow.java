/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.dispenser.BehaviorProjectileDispense
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;


public class MyDispenserBehaviorArrow extends Entity {
final class MyDispenserBehaviorArrow
extends BehaviorProjectileDispense {
    MyDispenserBehaviorArrow() {
    }

    protected IProjectile getProjectileEntity(World worldIn, IPosition par2IPosition) {
        IrukandjiArrow entityarrow = new IrukandjiArrow(worldIn, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        entityarrow.setPickupDelay(1;
        return entityarrow;
    }
}


}