/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.init.Blocks
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


public class EntityThrownRock extends Entity {
    public EntityThrownRock(World worldIn) {
        super(worldIn);
    }

    public EntityThrownRock(World worldIn, int par2) {
        super(worldIn);
    }

    public EntityThrownRock(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving) {
        super(worldIn, par2EntityLiving);
    }

    public EntityThrownRock(World worldIn, net.minecraft.entity.EntityLivingBase par2EntityLiving, int par3) {
        super(worldIn, par2EntityLiving);
        this.rock_type = par3;
    }

    public EntityThrownRock(World worldIn, double par2, double par4, double par6) {
        super(worldIn, par2, par4, par6);
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)0);
    }

    public int getRockType() {
        return 0 /* this.dataManager.get(20) */;
    }

    public void setRockType(int par1) {
        if (this.world == null) {
            return;
        }
        if (this.getEntityWorld().isRemote) {
            return;
        }
        this.rock_type = par1;
        this.dataManager = new net.minecraft.util.math.BlockPos(20, (Object)par1);
    }

    protected void onImpact(RayTraceResult par1RayTraceResult) {
        if (this.isDead) {
            return;
        }
        if (this.getEntityWorld().isRemote) {
            return;
        }
        if (par1RayTraceResult.entityHit != null && null != null) {
            float f3;
            double inair;
            double ks;
            Entity e = par1RayTraceResult.entityHit;
            if (this.rock_type == 1 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 2.0f);
                ks = 0.1;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.rock_type == 2 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 5.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.rock_type == 3 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 5.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                e.setFire(20);
            }
            if (this.rock_type == 4 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 5.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
                }
            }
            if (this.rock_type == 5 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 10.0f);
                ks = 0.1;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 0));
                }
            }
            if (this.rock_type == 6 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 20.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
            }
            if (this.rock_type == 7 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 40.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.rock_type == 8 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 40.0f);
                ks = 0.5;
                inair = 0.055;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                this.getEntityWorld().newExplosion((Entity)null, e.posX, e.posY + 0.25, e.posZ, 2.1f, true, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
            }
            if (this.rock_type == 9 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 150.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                e.setFire(50);
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
            }
            if (this.rock_type == 10 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 150.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 0));
                }
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
            }
            if (this.rock_type == 11 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 150.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0));
                }
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
            }
            if (this.rock_type == 12 && e != null) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((net.minecraft.entity.player.EntityPlayer)((net.minecraft.entity.player.EntityPlayer)null)), 250.0f);
                ks = 0.2;
                inair = 0.025;
                f3 = (float)Math.atan2(e.posZ - null.posZ, e.posX - null.posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (e instanceof net.minecraft.entity.EntityLivingBase) {
                    ((net.minecraft.entity.EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                }
                this.getEntityWorld().newExplosion((Entity)null, e.posX, e.posY + 0.25, e.posZ, 5.1f, true, this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing"));
            }
        } else if (this.rock_type != 0) {
            int played = 0;
            int x = par1RayTraceResult.getBlockPos().getX();
            int y = par1RayTraceResult.getBlockPos().getY();
            int z = par1RayTraceResult.getBlockPos().getZ();
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    for (int k = -1; k <= 1; ++k) {
                        Block bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + k)).getBlock();
                        if (bid != Blocks.GLASS && bid != Blocks.GLASS_PANE && bid != Blocks.GLASS) continue;
                        if (!this.getEntityWorld().isRemote) {
                            this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + k), Blocks.AIR.getDefaultState());
                        }
                        if (played != 0) continue;
                        this.getEntityWorld().playSoundEffect((double)x, (double)y, (double)z, "orespawn:glassdead", 1.0f, 1.0f);
                        ++played;
                    }
                }
            }
            if (!this.getEntityWorld().isRemote) {
                if (this.rock_type == 1) {
                    this.dropItem(OreSpawnMain.MySmallRock, 1);
                }
                if (this.rock_type == 2) {
                    this.dropItem(OreSpawnMain.MyRock, 1);
                }
                if (this.rock_type == 3) {
                    this.dropItem(OreSpawnMain.MyRedRock, 1);
                }
                if (this.rock_type == 4) {
                    this.dropItem(OreSpawnMain.MyGreenRock, 1);
                }
                if (this.rock_type == 5) {
                    this.dropItem(OreSpawnMain.MyBlueRock, 1);
                }
                if (this.rock_type == 6) {
                    this.dropItem(OreSpawnMain.MyPurpleRock, 1);
                }
                if (this.rock_type == 7) {
                    this.dropItem(OreSpawnMain.MySpikeyRock, 1);
                }
                if (this.rock_type == 8) {
                    this.dropItem(OreSpawnMain.MyTNTRock, 1);
                }
                if (this.rock_type == 9) {
                    this.dropItem(OreSpawnMain.MyCrystalRedRock, 1);
                }
                if (this.rock_type == 10) {
                    this.dropItem(OreSpawnMain.MyCrystalGreenRock, 1);
                }
                if (this.rock_type == 11) {
                    this.dropItem(OreSpawnMain.MyCrystalBlueRock, 1);
                }
                if (this.rock_type == 12) {
                    this.dropItem(OreSpawnMain.MyCrystalTNTRock, 1);
                }
            }
        }
        this.setDead();
    }

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
        if (bid == Blocks.WATER && this.motionY < (double)-0.15f && this.motionY > (double)-0.55f && (float)(this.motionX * this.motionX + this.motionZ * this.motionZ) > 0.5f) {
            this.motionY = -(this.motionY * 3.0 / 4.0);
            this.motionX = this.motionX * 3.0 / 4.0;
            this.motionZ = this.motionZ * 3.0 / 4.0;
        }
    }
}


}