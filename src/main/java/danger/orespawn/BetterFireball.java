package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BetterFireball extends EntityFireball {

    private int notme = 0;
    private boolean small = false;

    public BetterFireball(World worldIn) {
        super(worldIn);
        this.setSize(1.0f, 1.0f);
    }

    public BetterFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        // Na 1.12.2, o construtor "super" já faz toda a matemática de aceleração para nós!
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(1.0f, 1.0f);
        this.explosionPower = 1;
    }

    public void setNotMe() {
        this.notme = 1;
    }

    public void setBig() {
        this.explosionPower = 2; // Substitui o obscuro field_92012_e
    }

    public void setReallyBig() {
        this.explosionPower = 4;
    }

    public void setSmall() {
        this.small = true;
        this.setSize(0.3125f, 0.3125f);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        
        // Despawn de segurança após muito tempo voando (aprox 30 segundos)
        if (this.ticksExisted >= 600) {
            this.setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (this.isDead) {
            return;
        }

        // --- COLISÃO COM ENTIDADES ---
        if (result.entityHit != null) {
            Entity e = result.entityHit;

            // Retornar sem setDead() faz a bola de fogo ATRAVESSAR essas entidades
            if (e instanceof BetterFireball || e instanceof Mothra || e instanceof GodzillaHead) {
                return;
            }

            if (this.notme != 0 && (e instanceof Dragon || e instanceof EntityPlayer)) {
                this.setDead();
                return;
            }

            // O temido dano que corta a vida pela metade para Bosses gigantes
            if (e instanceof EntityLivingBase) {
                EntityLivingBase el = (EntityLivingBase) e;
                boolean isGiant = (el.width * el.height > 30.0f);
                
                if (isGiant && !MyUtils.isRoyalty(el) && !(el instanceof Godzilla) && !(el instanceof GodzillaHead) && !(el instanceof PitchBlack) && !(el instanceof Kraken)) {
                    el.setHealth(el.getHealth() / 2.0f);
                }
            }

            float damage = this.small ? 5.0f : 10.0f;
            e.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), damage);
            e.setFire(5);

        // --- COLISÃO COM BLOCOS ---
        } else if (!this.world.isRemote && result.getBlockPos() != null) {
            BlockPos hitPos = result.getBlockPos();
            if (result.sideHit != null) {
                hitPos = hitPos.offset(result.sideHit);
            }
            if (this.world.isAirBlock(hitPos)) {
                this.world.setBlockState(hitPos, Blocks.FIRE.getDefaultState());
            }
        }

        // --- EXPLOSÃO FINAL ---
        if (!this.world.isRemote) {
            if (!this.small) {
                boolean mobGriefing = this.world.getGameRules().getBoolean("mobGriefing");
                this.world.newExplosion(null, this.posX, this.posY, this.posZ, (float) this.explosionPower, true, mobGriefing);
            }
            this.setDead();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        // O Minecraft já salva o ExplosionPower automaticamente, mas vamos salvar os nossos status adicionais
        compound.setInteger("NotMe", this.notme);
        compound.setBoolean("Small", this.small);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.notme = compound.getInteger("NotMe");
        this.small = compound.getBoolean("Small");
    }
}