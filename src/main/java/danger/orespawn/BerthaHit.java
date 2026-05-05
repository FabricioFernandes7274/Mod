package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BerthaHit extends EntityThrowable {
    
    private int hit_type = 0;

    public BerthaHit(World worldIn) {
        super(worldIn);
    }

    public BerthaHit(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.setSize(0.33f, 0.33f);
        // Na 1.12.2, o construtor do EntityThrowable já define a posição inicial.
        // Só precisamos atirar na direção que o jogador está olhando!
        this.shoot(throwerIn, throwerIn.rotationPitch, throwerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
    }

    public BerthaHit(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public void setHitType(int i) {
        this.hit_type = i;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (this.isDead) {
            return;
        }
        
        // Recupera quem deu o golpe de espada (o atirador)
        EntityLivingBase thrower = this.getThrower();
        if (thrower == null || !(thrower instanceof EntityPlayer)) {
            this.setDead();
            return;
        }

        EntityPlayer player = (EntityPlayer) thrower;

        if (result.entityHit != null) {
            Entity e = result.entityHit;
            
            // Impede PvP se a regra estiver ativa
            if (OreSpawnMain.big_bertha_pvp == 0 && (e instanceof EntityPlayer || e instanceof Girlfriend || e instanceof Boyfriend)) {
                this.setDead();
                return;
            }
            if (OreSpawnMain.big_bertha_pvp == 0 && e instanceof EntityTameable && ((EntityTameable)e).isTamed()) {
                this.setDead();
                return;
            }

            // Big Bertha Normal (hit_type 0)
            if (this.hit_type == 0 && this.getDistanceSq(thrower) < 81.0 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)OreSpawnMain.bertha_stats.damage);
                e.setFire(10);
                double ks = 2.25;
                double inair = 0.35;
                float f3 = (float)Math.atan2(e.posZ - thrower.posZ, e.posX - thrower.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            
            // Royal Guardian Sword (hit_type 2)
            if (this.hit_type == 2 && this.getDistanceSq(thrower) < 101.0 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)OreSpawnMain.royal_stats.damage);
                double ks = 1.5;
                double inair = 0.25;
                float f3 = (float)Math.atan2(e.posZ - thrower.posZ, e.posX - thrower.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            
            // Slice of Bacon / Hammy (hit_type 3)
            if (this.hit_type == 3 && this.getDistanceSq(thrower) < 64.0 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)OreSpawnMain.hammy_stats.damage);
                double ks = 1.25;
                double inair = 0.65;
                float f3 = (float)Math.atan2(e.posZ - thrower.posZ, e.posX - thrower.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                
                if (!this.world.isRemote) {
                    boolean mobGriefing = this.world.getGameRules().getBoolean("mobGriefing");
                    this.world.newExplosion(null, this.posX, this.posY, this.posZ, 1.5f, true, mobGriefing);
                }
            }
            
        // Explosão no chão para o Hammy
        } else if (!this.world.isRemote && this.hit_type == 3 && this.getDistanceSq(thrower) < 64.0) {
            boolean mobGriefing = this.world.getGameRules().getBoolean("mobGriefing");
            this.world.newExplosion(null, this.posX, this.posY, this.posZ, 2.1f, true, mobGriefing);
        }
        
        this.setDead();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }
}