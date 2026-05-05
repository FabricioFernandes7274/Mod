package danger.orespawn;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class DeadIrukandji extends LaserBall {
    
    private final int my_index = 86;

    // Construtor básico para spawn via registro ou comando
    public DeadIrukandji(World worldIn) {
        super(worldIn);
        this.setIrukandji();
    }

    // Sobrecarga de construtor (comumente usada por projéteis decompilados)
    public DeadIrukandji(World worldIn, int par2) {
        this(worldIn);
    }

    // Construtor usado quando uma entidade (player ou mob) dispara o projétil
    public DeadIrukandji(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        this.setIrukandji();
    }

    // Sobrecarga com index extra, ignorado para manter o padrão setIrukandji()
    public DeadIrukandji(World worldIn, EntityLivingBase shooter, int par3) {
        this(worldIn, shooter);
    }

    // Construtor usado para spawnar em coordenadas específicas
    public DeadIrukandji(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setIrukandji();
    }

    /**
     * Retorna o índice de textura/tipo para renderização.
     */
    public int getIrukandjiIndex() {
        return this.my_index;
    }
}