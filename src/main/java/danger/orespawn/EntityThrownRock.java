package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class EntityThrownRock extends EntityThrowable {
    
    // Configuração correta do DataManager para a versão 1.12.2
    private static final DataParameter<Integer> ROCK_TYPE = EntityDataManager.createKey(EntityThrownRock.class, DataSerializers.VARINT);
    
    public int rock_type = 0;
    private float my_rotation = 0.0f;
    private int myage = 0;

    public EntityThrownRock(World worldIn) {
        super(worldIn);
    }

    public EntityThrownRock(World worldIn, int par2) {
        super(worldIn);
    }

    public EntityThrownRock(World worldIn, EntityLivingBase par2EntityLiving) {
        super(worldIn, par2EntityLiving);
    }

    public EntityThrownRock(World worldIn, EntityLivingBase par2EntityLiving, int par3) {
        super(worldIn, par2EntityLiving);
        this.rock_type = par3;
        this.setRockType(par3);
    }

    public EntityThrownRock(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ROCK_TYPE, 0);
    }

    public int getRockType() {
        return this.dataManager.get(ROCK_TYPE);
    }

    public void setRockType(int par1) {
        this.rock_type = par1;
        this.dataManager.set(ROCK_TYPE, par1);
    }

    @Override
    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (this.isDead || this.getEntityWorld().isRemote) {
            return;
        }
        
        if (par1RayTraceResult.entityHit != null) {
            float f3;
            double inair;
            double ks;
            Entity e = par1RayTraceResult.entityHit;
            
            // Corrige a fonte de dano para um projétil arremessado
            DamageSource source = DamageSource.causeThrownDamage(this, this.getThrower());

            if (this.rock_type == 1) {
                e.attackEntityFrom(source, 2.0f);
                ks = 0.1;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX); // Nulls corrigidos para this
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            else if (this.rock_type == 2) {
                e.attackEntityFrom(source, 5.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            else if (this.rock_type == 3) {
                e.attackEntityFrom(source, 5.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                e.setFire(20);
            }
            else if (this.rock_type == 4) {
                e.attackEntityFrom(source, 5.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof EntityLivingBase) {
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
                }
            }
            else if (this.rock_type == 5) {
                e.attackEntityFrom(source, 10.0f);
                ks = 0.1;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof EntityLivingBase) {
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 0));
                }
            }
            else if (this.rock_type == 6) {
                e.attackEntityFrom(source, 20.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof EntityLivingBase) {
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
            }
            else if (this.rock_type == 7) {
                e.attackEntityFrom(source, 40.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            else if (this.rock_type == 8) {
                e.attackEntityFrom(source, 40.0f);
                ks = 0.5;
                inair = 0.055;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                boolean griefing = this.getEntityWorld().getGameRules().getBoolean("mobGriefing");
                this.getEntityWorld().newExplosion(this, e.posX, e.posY + 0.25, e.posZ, 2.1f, true, griefing);
            }
            else if (this.rock_type == 9) {
                e.attackEntityFrom(source, 150.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                e.setFire(50);
                if (e instanceof EntityLivingBase) {
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
            }
            else if (this.rock_type == 10) {
                e.attackEntityFrom(source, 150.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof EntityLivingBase) {
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 0));
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
            }
            else if (this.rock_type == 11) {
                e.attackEntityFrom(source, 150.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof EntityLivingBase) {
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0));
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
            }
            else if (this.rock_type == 12) {
                e.attackEntityFrom(source, 250.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                if (e.isDead) inair *= 2.0;
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof EntityLivingBase) {
                    ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
                boolean griefing = this.getEntityWorld().getGameRules().getBoolean("mobGriefing");
                this.getEntityWorld().newExplosion(this, e.posX, e.posY + 0.25, e.posZ, 5.1f, true, griefing);
            }
            
        } else if (this.rock_type != 0 && par1RayTraceResult.getBlockPos() != null) {
            int played = 0;
            int x = par1RayTraceResult.getBlockPos().getX();
            int y = par1RayTraceResult.getBlockPos().getY();
            int z = par1RayTraceResult.getBlockPos().getZ();
            
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    for (int k = -1; k <= 1; ++k) {
                        Block bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + k)).getBlock();
                        if (bid != Blocks.GLASS && bid != Blocks.GLASS_PANE && bid != Blocks.STAINED_GLASS && bid != Blocks.STAINED_GLASS_PANE) continue;
                        
                        if (!this.getEntityWorld().isRemote) {
                            this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + k), Blocks.AIR.getDefaultState());
                        }
                        if (played == 0) {
                            this.getEntityWorld().playSound(null, x, y, z, net.minecraft.init.SoundEvents.BLOCK_GLASS_BREAK, net.minecraft.util.SoundCategory.BLOCKS, 1.0f, 1.0f);
                            ++played;
                        }
                    }
                }
            }
            
            if (!this.getEntityWorld().isRemote) {
                if (this.rock_type == 1) this.dropItem(OreSpawnMain.MySmallRock, 1);
                if (this.rock_type == 2) this.dropItem(OreSpawnMain.MyRock, 1);
                if (this.rock_type == 3) this.dropItem(OreSpawnMain.MyRedRock, 1);
                if (this.rock_type == 4) this.dropItem(OreSpawnMain.MyGreenRock, 1);
                if (this.rock_type == 5) this.dropItem(OreSpawnMain.MyBlueRock, 1);
                if (this.rock_type == 6) this.dropItem(OreSpawnMain.MyPurpleRock, 1);
                if (this.rock_type == 7) this.dropItem(OreSpawnMain.MySpikeyRock, 1);
                if (this.rock_type == 8) this.dropItem(OreSpawnMain.MyTNTRock, 1);
                if (this.rock_type == 9) this.dropItem(OreSpawnMain.MyCrystalRedRock, 1);
                if (this.rock_type == 10) this.dropItem(OreSpawnMain.MyCrystalGreenRock, 1);
                if (this.rock_type == 11) this.dropItem(OreSpawnMain.MyCrystalBlueRock, 1);
                if (this.rock_type == 12) this.dropItem(OreSpawnMain.MyCrystalTNTRock, 1);
            }
        }
        this.setDead();
    }

    @Override
    public void onUpdate() {
        int x = (int)this.posX;
        int y = (int)this.posY;
        int z = (int)this.posZ;
        super.onUpdate();
        this.my_rotation += 30.0f;
        this.my_rotation %= 360.0f;
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
        ++this.myage;
        
        if (this.myage > 1000) {
            this.setDead();
        }
        if (this.getEntityWorld().isRemote) {
            this.rock_type = this.getRockType();
        } else {
            this.setRockType(this.rock_type);
        }
        
        Block bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock();
        if (bid == Blocks.WATER && this.motionY < -0.15f && this.motionY > -0.55f && (this.motionX * this.motionX + this.motionZ * this.motionZ) > 0.5f) {
            this.motionY = -(this.motionY * 3.0 / 4.0);
            this.motionX = this.motionX * 3.0 / 4.0;
            this.motionZ = this.motionZ * 3.0 / 4.0;
        }
    }
}