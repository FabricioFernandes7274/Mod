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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;


public class MyDispenserBehaviorWDCharge extends EntityMob {
final class MyDispenserBehaviorWDCharge
extends BehaviorProjectileDispense {
    MyDispenserBehaviorWDCharge() {
    }

    protected IProjectile getProjectileEntity(World worldIn, IPosition par2IPosition) {
        WaterBall entityarrow = new WaterBall(worldIn, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        return entityarrow;
    }
}


}