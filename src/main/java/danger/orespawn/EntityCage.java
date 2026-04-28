/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.boss.net.minecraft.entity.boss.dragon.EntityDragonPart
 *  net.minecraft.entity.boss.EntityWither
 *  net.minecraft.entity.monster.EntityBlaze
 *  net.minecraft.entity.monster.EntityCaveSpider
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntityGhast
 *  net.minecraft.entity.monster.EntityIronGolem
 *  net.minecraft.entity.monster.EntityMagmaCube
 *  net.minecraft.entity.monster.EntityPigZombie
 *  net.minecraft.entity.monster.EntitySilverfish
 *  net.minecraft.entity.monster.EntitySkeleton
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.monster.EntitySnowman
 *  net.minecraft.entity.monster.EntitySpider
 *  net.minecraft.entity.monster.EntityWitch
 *  net.minecraft.entity.monster.EntityZombie
 *  net.minecraft.entity.passive.EntityBat
 *  net.minecraft.entity.passive.EntityChicken
 *  net.minecraft.entity.passive.EntityCow
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.passive.EntityMooshroom
 *  net.minecraft.entity.passive.EntityOcelot
 *  net.minecraft.entity.passive.EntityPig
 *  net.minecraft.entity.passive.EntitySheep
 *  net.minecraft.entity.passive.EntitySquid
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.passive.EntityWolf
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.net.minecraft.entity.boss.dragon.EntityDragonPart;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityCage extends Entity {
    public int my_index = 160;
    private World throwerWorld = null;
    private net.minecraft.entity.player.EntityPlayer thrower = null;

    public EntityCage(World worldIn) {
        super(worldIn);
        this.throwerWorld = worldIn;
    }

    public EntityCage(World worldIn, int i) {
        super(worldIn);
        this.throwerWorld = worldIn;
        this.my_index = i;
    }

    public EntityCage(World worldIn, net.minecraft.entity.player.EntityPlayer par2EntityLiving, int i) {
        super(worldIn, (net.minecraft.entity.EntityLivingBase)par2EntityLiving);
        this.throwerWorld = worldIn;
        this.thrower = par2EntityLiving;
        this.my_index = i;
        if (this.thrower.world != null) {
            this.throwerWorld = this.thrower.world;
        }
    }

    public int getCageIndex() {
        return this.my_index;
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (par1RayTraceResult.entityHit != null && this.rand.nextInt(10) >= 2) {
            Girlfriend gf;
            if (this.throwerWorld != null) {
                for (int var3 = 0; var3 < 4; ++var3) {
                    this.throwerWorld.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, par1RayTraceResult.entityHit.posX, par1RayTraceResult.entityHit.posY + 0.25, par1RayTraceResult.entityHit.posZ, 0.0, 0.0, 0.0);
                    this.throwerWorld.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, par1RayTraceResult.entityHit.posX, par1RayTraceResult.entityHit.posY + 0.25, par1RayTraceResult.entityHit.posZ, 0.0, 0.0, 0.0);
                    this.throwerWorld.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, par1RayTraceResult.entityHit.posX, par1RayTraceResult.entityHit.posY + 0.25, par1RayTraceResult.entityHit.posZ, 0.0, 0.0, 0.0);
                }
                if (this.thrower != null) {
                    this.throwerWorld.playSound(null, (Entity)this.thrower.posX, (Entity)this.thrower.posY, (Entity)this.thrower.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.5f);
                }
            }
            if (par1RayTraceResult.entityHit instanceof net.minecraft.entity.player.EntityPlayer) {
                if (!this.getEntityWorld().isRemote) {
                    this.dropItem(OreSpawnMain.CageEmpty, 1);
                    this.setDead();
                }
                return;
            }
            if (par1RayTraceResult.entityHit instanceof SpiderDriver) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSpiderDriver, 1);
            } else if (par1RayTraceResult.entityHit instanceof EntitySpider) {
                if (par1RayTraceResult.entityHit instanceof EntityCaveSpider) {
                    par1RayTraceResult.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedCaveSpider, 1);
                } else {
                    par1RayTraceResult.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedSpider, 1);
                }
            }
            if (par1RayTraceResult.entityHit instanceof Crab) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCrab, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityBat) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBat, 2);
            }
            if (par1RayTraceResult.entityHit instanceof EntityPig) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedPig, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntitySquid) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSquid, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityChicken) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedChicken, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityCreeper) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCreeper, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityHorse) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedHorse, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntitySkeleton) {
                EntitySkeleton sk = (EntitySkeleton)par1RayTraceResult.entityHit;
                if (sk.getSkeletonType() != 0) {
                    this.dropItem(OreSpawnMain.CagedWitherSkeleton, 1);
                } else {
                    this.dropItem(OreSpawnMain.CagedSkeleton, 1);
                }
                par1RayTraceResult.entityHit.setDead();
            }
            if (par1RayTraceResult.entityHit instanceof EntityZombie) {
                if (par1RayTraceResult.entityHit instanceof EntityPigZombie) {
                    par1RayTraceResult.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedZombiePigman, 1);
                } else {
                    par1RayTraceResult.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedZombie, 1);
                }
            }
            if (par1RayTraceResult.entityHit instanceof EntitySlime) {
                if (par1RayTraceResult.entityHit instanceof EntityMagmaCube) {
                    par1RayTraceResult.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedMagmaCube, 1);
                } else {
                    par1RayTraceResult.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedSlime, 1);
                }
            }
            if (par1RayTraceResult.entityHit instanceof EntityGhast) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGhast, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityEnderman) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEnderman, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntitySilverfish) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSilverfish, 2);
            }
            if (par1RayTraceResult.entityHit instanceof EntityWitch) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWitch, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntitySheep) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSheep, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityWolf) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWolf, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityOcelot) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedOcelot, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityBlaze) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBlaze, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Girlfriend && !(gf = (Girlfriend)par1RayTraceResult.entityHit).isTamed()) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGirlfriend, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Boyfriend && !(gf = (Boyfriend)par1RayTraceResult.entityHit).isTamed()) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBoyfriend, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityDragon) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                EntityDragon dr = (EntityDragon)par1RayTraceResult.entityHit;
                dr.setDead();
                this.dropItem(OreSpawnMain.CagedEnderDragon, 1);
            }
            if (par1RayTraceResult.entityHit instanceof net.minecraft.entity.boss.dragon.EntityDragonPart) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                net.minecraft.entity.boss.dragon.EntityDragonPart dp = (net.minecraft.entity.boss.dragon.EntityDragonPart)par1RayTraceResult.entityHit;
                EntityDragon dr = (EntityDragon)dp.entityDragonObj;
                dr.setDead();
                this.dropItem(OreSpawnMain.CagedEnderDragon, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntitySnowman) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSnowGolem, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityIronGolem) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedIronGolem, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EntityWither) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWitherBoss, 1);
            }
            if (par1RayTraceResult.entityHit instanceof CrystalCow) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCrystalCow, 1);
                if (!this.getEntityWorld().isRemote) {
                    this.setDead();
                }
                return;
            }
            if (par1RayTraceResult.entityHit instanceof EnchantedCow) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEnchantedCow, 1);
                if (!this.getEntityWorld().isRemote) {
                    this.setDead();
                }
                return;
            }
            if (par1RayTraceResult.entityHit instanceof GoldCow) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGoldCow, 1);
                if (!this.getEntityWorld().isRemote) {
                    this.setDead();
                }
                return;
            }
            if (par1RayTraceResult.entityHit instanceof RedCow) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedRedCow, 1);
                if (!this.getEntityWorld().isRemote) {
                    this.setDead();
                }
                return;
            }
            if (par1RayTraceResult.entityHit instanceof EntityCow) {
                if (par1RayTraceResult.entityHit instanceof EntityMooshroom) {
                    par1RayTraceResult.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedMooshroom, 1);
                } else {
                    par1RayTraceResult.entityHit.setDead();
                    this.dropItem(OreSpawnMain.CagedCow, 1);
                }
                if (!this.getEntityWorld().isRemote) {
                    this.setDead();
                }
                return;
            }
            if (par1RayTraceResult.entityHit instanceof EntityVillager) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedVillager, 1);
                if (!this.getEntityWorld().isRemote) {
                    this.setDead();
                }
                return;
            }
            if (par1RayTraceResult.entityHit instanceof Mothra) {
                if (this.rand.nextInt(10) < 4) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedMOTHRA, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Alosaurus) {
                if (this.rand.nextInt(10) < 4) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedAlo, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Cryolophosaurus) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCryo, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Camarasaurus) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCama, 1);
            }
            if (par1RayTraceResult.entityHit instanceof VelocityRaptor) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedVelo, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Hydrolisc) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedHydro, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Basilisk) {
                if (this.rand.nextInt(10) < 6) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBasil, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Dragonfly) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedDragonfly, 2);
            }
            if (par1RayTraceResult.entityHit instanceof EmperorScorpion) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEmperorScorpion, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Cephadrome) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCephadrome, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Dragon) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedDragon, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Scorpion) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedScorpion, 1);
            }
            if (par1RayTraceResult.entityHit instanceof CaveFisher) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCaveFisher, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Spyro) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSpyro, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Baryonyx) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBaryonyx, 1);
            }
            if (par1RayTraceResult.entityHit instanceof GammaMetroid) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGammaMetroid, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Cockateil) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCockateil, 4);
            }
            if (par1RayTraceResult.entityHit instanceof AttackSquid) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedAttackSquid, 6);
            }
            if (par1RayTraceResult.entityHit instanceof Kyuubi) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedKyuubi, 1);
            }
            if (par1RayTraceResult.entityHit instanceof WaterDragon) {
                if (this.rand.nextInt(10) < 6) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWaterDragon, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Kraken) {
                if (this.rand.nextInt(100) < 95) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedKraken, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Lizard) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLizard, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Alien) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedAlien, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Bee) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBee, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Firefly) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedFirefly, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Chipmunk) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedChipmunk, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Gazelle) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGazelle, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Ostrich) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedOstrich, 1);
            }
            if (par1RayTraceResult.entityHit instanceof TrooperBug) {
                if (this.rand.nextInt(10) < 6) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedTrooper, 1);
            }
            if (par1RayTraceResult.entityHit instanceof SpitBug) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSpit, 1);
            }
            if (par1RayTraceResult.entityHit instanceof StinkBug) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedStink, 1);
            }
            if (par1RayTraceResult.entityHit instanceof CreepingHorror) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCreepingHorror, 1);
            }
            if (par1RayTraceResult.entityHit instanceof TerribleTerror) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedTerribleTerror, 1);
            }
            if (par1RayTraceResult.entityHit instanceof CliffRacer) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCliffRacer, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Triffid) {
                if (this.rand.nextInt(10) < 6) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedTriffid, 1);
            }
            if (par1RayTraceResult.entityHit instanceof PitchBlack) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedPitchBlack, 1);
            }
            if (par1RayTraceResult.entityHit instanceof LurkingTerror) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLurkingTerror, 1);
            }
            if (par1RayTraceResult.entityHit instanceof WormSmall) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSmallWorm, 1);
            }
            if (par1RayTraceResult.entityHit instanceof WormMedium) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedMediumWorm, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Cassowary) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCassowary, 1);
            }
            if (par1RayTraceResult.entityHit instanceof CloudShark) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCloudShark, 1);
            }
            if (par1RayTraceResult.entityHit instanceof GoldFish) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedGoldFish, 1);
            }
            if (par1RayTraceResult.entityHit instanceof LeafMonster) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLeafMonster, 1);
            }
            if (par1RayTraceResult.entityHit instanceof WormLarge) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLargeWorm, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EnderKnight) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEnderKnight, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EnderReaper) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEnderReaper, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Beaver) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBeaver, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Urchin) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedUrchin, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Flounder) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedFlounder, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Skate) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSkate, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Rotator) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedRotator, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Peacock) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedPeacock, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Fairy) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedFairy, 1);
            }
            if (par1RayTraceResult.entityHit instanceof DungeonBeast) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedDungeonBeast, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Vortex) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedVortex, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Rat) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedRat, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Whale) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedWhale, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Irukandji) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedIrukandji, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Stinky) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedStinky, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Mantis) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedMantis, 1);
            }
            if (par1RayTraceResult.entityHit instanceof TRex) {
                if (this.rand.nextInt(10) < 4) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedTRex, 1);
            }
            if (par1RayTraceResult.entityHit instanceof HerculesBeetle) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedHercules, 1);
            }
            if (par1RayTraceResult.entityHit instanceof EasterBunny) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedEasterBunny, 1);
            }
            if (par1RayTraceResult.entityHit instanceof CaterKiller) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCaterKiller, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Molenoid) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedMolenoid, 1);
            }
            if (par1RayTraceResult.entityHit instanceof SeaMonster) {
                if (this.rand.nextInt(10) < 3) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSeaMonster, 1);
            }
            if (par1RayTraceResult.entityHit instanceof SeaViper) {
                if (this.rand.nextInt(10) < 4) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedSeaViper, 1);
            }
            if (par1RayTraceResult.entityHit instanceof RubberDucky) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedRubberDucky, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Leon) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedLeon, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Hammerhead) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedHammerhead, 1);
            }
            if (par1RayTraceResult.entityHit instanceof BandP) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCriminal, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Cricket) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedCricket, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Frog) {
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedFrog, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Brutalfly) {
                if (this.rand.nextInt(10) < 5) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedBrutalfly, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Nastysaurus) {
                if (this.rand.nextInt(10) < 7) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedNastysaurus, 1);
            }
            if (par1RayTraceResult.entityHit instanceof Pointysaurus) {
                if (this.rand.nextInt(10) < 2) {
                    if (!this.getEntityWorld().isRemote) {
                        this.dropItem(OreSpawnMain.CageEmpty, 1);
                        this.setDead();
                    }
                    return;
                }
                par1RayTraceResult.entityHit.setDead();
                this.dropItem(OreSpawnMain.CagedPointysaurus, 1);
            }
        } else if (!this.getEntityWorld().isRemote) {
            this.dropItem(OreSpawnMain.CageEmpty, 1);
        }
        if (!this.getEntityWorld().isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.my_rotation += 20.0f;
        while (this.my_rotation > 360.0f) {
            this.my_rotation -= 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
    }
}


}