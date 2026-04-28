/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.world.World;

public class RubyBird
extends Cockateil {
    public RubyBird(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.birdtype = 5;
        this.setBirdType(this.birdtype);
        this.setFlyUp();
    }

    @Override
    protected String getLivingSound() {
        if (this.getEntityWorld().isDaytime() && !this.getEntityWorld().isRaining()) {
            return "orespawn:rubybird";
        }
        return null;
    }

    @Override
    public boolean getCanSpawnHere() {
        return true;
    }
}

