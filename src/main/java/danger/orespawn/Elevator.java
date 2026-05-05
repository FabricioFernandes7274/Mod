package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Elevator extends EntityLiving {
    // Registros para sincronização Servidor/Cliente
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(Elevator.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> EXPLODING = EntityDataManager.createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(Elevator.class, DataSerializers.VARINT);

    public Elevator(World worldIn) {
        super(worldIn);
        this.setSize(1.25f, 1.0f);
        this.stepHeight = 1.0f;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        // Inicializando os dados que antes estavam comentados
        this.dataManager.register(TIME_SINCE_HIT, 0);
        this.dataManager.register(FORWARD_DIR, 1);
        this.dataManager.register(DAMAGE_TAKEN, 0.0f);
        this.dataManager.register(EXPLODING, 0);
        this.dataManager.register(COLOR, 1);
    }

    // Corrigindo os Getters e Setters que retornavam 0
    public void setDamageTaken(float f) { this.dataManager.set(DAMAGE_TAKEN, f); }
    public float getDamageTaken() { return this.dataManager.get(DAMAGE_TAKEN); }

    public void setTimeSinceHit(int i) { this.dataManager.set(TIME_SINCE_HIT, i); }
    public int getTimeSinceHit() { return this.dataManager.get(TIME_SINCE_HIT); }

    public void setColor(int i) { this.dataManager.set(COLOR, i); }
    public int getColor() { return this.dataManager.get(COLOR); }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) return false;
        
        if (!this.world.isRemote && !this.isDead) {
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + amount * 10.0f);
            
            boolean isCreative = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)source.getTrueSource()).capabilities.isCreativeMode;
            
            if (isCreative || this.getDamageTaken() > 40.0f) {
                if (!isCreative) {
                    this.dropItem(Items.IRON_INGOT, 1); // Substitua pelo item do OreSpawn
                }
                this.setDead();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Lógica de flutuação e partículas simplificada para estabilidade
        if (this.getControllingPassenger() != null) {
            this.fallDistance = 0;
        }
    }

    @Override
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }
}