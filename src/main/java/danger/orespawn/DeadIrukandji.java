/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.world.World;

public class DeadIrukandji
extends LaserBall {
    private int my_index = 86;

    public DeadIrukandji(World worldIn) {
        super(worldIn);
        super.setIrukandji();
    }

    public DeadIrukandji(World worldIn, int par2) {
        super(worldIn);
        super.setIrukandji();
    }

    public DeadIrukandji(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(worldIn, par2EntityLiving);
        super.setIrukandji();
    }

    public DeadIrukandji(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(worldIn, par2EntityLiving);
        super.setIrukandji();
    }

    public DeadIrukandji(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
        super.setIrukandji();
    }

    public int getIrukandjiIndex() {
        return this.my_index;
    }
}

