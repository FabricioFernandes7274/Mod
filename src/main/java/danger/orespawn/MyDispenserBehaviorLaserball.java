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

final class MyDispenserBehaviorLaserball
extends BehaviorProjectileDispense {
    MyDispenserBehaviorLaserball() {
    }

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
        LaserBall entityarrow = new LaserBall(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        return entityarrow;
    }
}

