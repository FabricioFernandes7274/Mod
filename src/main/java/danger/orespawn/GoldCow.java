/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.passive.EntityCow
 *  net.minecraft.init.Items
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class GoldCow
extends RedCow {
    public GoldCow(World world) {
        super(world);
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
        for (int var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Items.APPLE, 1);
        }
        this.dropItem(Items.GOLDEN_APPLE, 1);
        super.dropFewItems(par1, par2);
    }

    @Override
    public EntityCow createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    @Override
    public GoldCow spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return new GoldCow(this.getEntityWorld());
    }
}

