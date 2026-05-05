package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCage extends EntityThrowable {

    public int my_index = 160;
    private float my_rotation = 0.0F;

    // Construtores obrigatórios para EntityThrowable
    public EntityCage(World worldIn) {
        super(worldIn);
    }

    public EntityCage(World worldIn, EntityLivingBase throwerIn, int index) {
        super(worldIn, throwerIn);
        this.my_index = index;
    }

    public EntityCage(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null && this.rand.nextInt(10) >= 2) {
                Entity hit = result.entityHit;
                boolean captured = false;

                // Efeitos visuais e sonoros no impacto bem-sucedido
                this.spawnImpactParticles(hit);
                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1.0F, 1.5F);

                // Bloqueia captura de players
                if (hit instanceof EntityPlayer) {
                    this.dropCageItem(OreSpawnMain.CageEmpty, 1);
                } 
                // Lógica de Captura (Exemplos das principais categorias)
                else if (hit instanceof EntitySpider) {
                    this.dropCageItem(hit instanceof EntityCaveSpider ? OreSpawnMain.CagedCaveSpider : OreSpawnMain.CagedSpider, 1);
                    captured = true;
                }
                else if (hit instanceof EntityPig) {
                    this.dropCageItem(OreSpawnMain.CagedPig, 1);
                    captured = true;
                }
                else if (hit instanceof EntityCreeper) {
                    this.dropCageItem(OreSpawnMain.CagedCreeper, 1);
                    captured = true;
                }
                else if (hit instanceof EntitySkeleton) {
                    // Na 1.12.2, Wither Skeleton é uma classe separada (EntityWitherSkeleton)
                    if (hit instanceof EntityWitherSkeleton) {
                        this.dropCageItem(OreSpawnMain.CagedWitherSkeleton, 1);
                    } else {
                        this.dropCageItem(OreSpawnMain.CagedSkeleton, 1);
                    }
                    captured = true;
                }
                else if (hit instanceof EntityZombie) {
                    this.dropCageItem(hit instanceof EntityPigZombie ? OreSpawnMain.CagedZombiePigman, 1 : OreSpawnMain.CagedZombie, 1);
                    captured = true;
                }
                // Exemplo de Boss (Ender Dragon)
                else if (hit instanceof EntityDragon || hit instanceof net.minecraft.entity.boss.EntityDragonPart) {
                    if (this.rand.nextInt(10) >= 5) {
                        EntityDragon dragon = (hit instanceof EntityDragon) ? (EntityDragon)hit : (EntityDragon)((net.minecraft.entity.boss.EntityDragonPart)hit).entityDragonObj;
                        dragon.setDead();
                        this.dropCageItem(OreSpawnMain.CagedEnderDragon, 1);
                        captured = true;
                    } else {
                        this.dropCageItem(OreSpawnMain.CageEmpty, 1);
                    }
                }
                // --- AQUI VOCÊ ADICIONA OS OUTROS MOBS DO ORESPAWN (Kraken, T-Rex, etc) ---
                // Exemplo:
                /*
                else if (hit instanceof TRex) {
                    if (this.rand.nextInt(10) >= 4) {
                        this.dropCageItem(OreSpawnMain.CagedTRex, 1);
                        captured = true;
                    } else { this.dropCageItem(OreSpawnMain.CageEmpty, 1); }
                }
                */

                if (captured) {
                    hit.setDead();
                }
            } else {
                // Se errar o alvo ou falhar na chance aleatória
                this.dropCageItem(OreSpawnMain.CageEmpty, 1);
            }

            // Remove a entidade da jaula voadora do mundo
            this.setDead();
        }
    }

    private void dropCageItem(Item item, int count) {
        if (!this.world.isRemote) {
            this.dropItem(item, count);
        }
    }

    private void spawnImpactParticles(Entity hit) {
        for (int i = 0; i < 4; ++i) {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, hit.posX, hit.posY + 0.25D, hit.posZ, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, hit.posX, hit.posY + 0.25D, hit.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Mantém a rotação visual da jaula enquanto voa
        this.my_rotation += 20.0F;
        if (this.my_rotation > 360.0F) {
            this.my_rotation -= 360.0F;
        }
        this.rotationPitch = this.my_rotation;
        this.prevRotationPitch = this.my_rotation;
    }
}