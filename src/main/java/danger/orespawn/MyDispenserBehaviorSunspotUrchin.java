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


public class MyDispenserBehaviorSunspotUrchin extends EntityMob {
final class MyDispenserBehaviorSunspotUrchin
extends BehaviorProjectileDispense {
    MyDispenserBehaviorSunspotUrchin() {
    }

    protected IProjectile getProjectileEntity(World worldIn, IPosition par2IPosition) {
        SunspotUrchin entityarrow = new SunspotUrchin(worldIn, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        return entityarrow;
    }
}


}