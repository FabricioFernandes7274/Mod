package danger.orespawn;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class Acid extends LaserBall {
    
    private int my_index = 85;

    public Acid(World worldIn) {
        super(worldIn);
        this.setAcid();
    }

    public Acid(World worldIn, int par2) {
        super(worldIn);
        this.setAcid();
    }

    public Acid(World worldIn, EntityLivingBase par2EntityLiving) {
        super(worldIn, par2EntityLiving);
        this.setAcid();
    }

    public Acid(World worldIn, EntityLivingBase par2EntityLiving, int par3) {
        super(worldIn, par2EntityLiving);
        this.setAcid();
    }

    public Acid(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
        this.setAcid();
    }

    public int getAcidIndex() {
        return this.my_index;
    }
}