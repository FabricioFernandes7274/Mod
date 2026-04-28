/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityFishHook
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemFishFood$FishType
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.stats.StatList
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.WeightedRandom
 *  net.minecraft.util.WeightedRandomFishable
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import danger.orespawn.OreSpawnMain;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;

public class UltimateFishHook
extends EntityFishHook {
    private static final List JUNK = Arrays.asList(new WeightedRandomFishable(new ItemStack((Item)Items.LEATHER_BOOTS), 10).setMaxDamagePercent(0.9f), new WeightedRandomFishable(new ItemStack(Items.LEATHER), 10), new WeightedRandomFishable(new ItemStack(Items.BONE), 10), new WeightedRandomFishable(new ItemStack((Item)Items.POTIONITEM), 10), new WeightedRandomFishable(new ItemStack(Items.STRING), 5), new WeightedRandomFishable(new ItemStack((Item)Items.FISHING_ROD), 2).setMaxDamagePercent(0.9f), new WeightedRandomFishable(new ItemStack(Items.BOWL), 10), new WeightedRandomFishable(new ItemStack(Items.STICK), 5), new WeightedRandomFishable(new ItemStack(Items.DYE, 10, 0), 1), new WeightedRandomFishable(new ItemStack((Block)Blocks.TRIPWIRE_HOOK), 10), new WeightedRandomFishable(new ItemStack(Items.ROTTEN_FLESH), 10));
    private static final List VALUABLES = Arrays.asList(new WeightedRandomFishable(new ItemStack(Blocks.WATERLILY), 1), new WeightedRandomFishable(new ItemStack(Items.NAME_TAG), 1), new WeightedRandomFishable(new ItemStack(Items.SADDLE), 1), new WeightedRandomFishable(new ItemStack((Item)Items.BOW), 1).setMaxDamagePercent(0.25f).setEnchantable(), new WeightedRandomFishable(new ItemStack((Item)Items.FISHING_ROD), 1).setMaxDamagePercent(0.25f).setEnchantable(), new WeightedRandomFishable(new ItemStack(Items.BOOK), 1).setEnchantable());
    private static final List FISH = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.COD.getItemDamage()), 60), new WeightedRandomFishable(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.SALMON.getItemDamage()), 25), new WeightedRandomFishable(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.CLOWNFISH.getItemDamage()), 2), new WeightedRandomFishable(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.PUFFERFISH.getItemDamage()), 13));
    private static final List orespawn_lava_fish = Arrays.asList(new WeightedRandomFishable(new ItemStack(OreSpawnMain.MySunspotUrchin), 25), new WeightedRandomFishable(new ItemStack(OreSpawnMain.MyLavaEel), 10), new WeightedRandomFishable(new ItemStack(OreSpawnMain.MySunFish), 15), new WeightedRandomFishable(new ItemStack(OreSpawnMain.MySparkFish), 10), new WeightedRandomFishable(new ItemStack(OreSpawnMain.MyFireFish), 15));
    private static final List orespawn_fish = Arrays.asList(new WeightedRandomFishable(new ItemStack(OreSpawnMain.MyBlueFish), 25), new WeightedRandomFishable(new ItemStack(OreSpawnMain.MyPinkFish), 10), new WeightedRandomFishable(new ItemStack(OreSpawnMain.MyRockFish), 15), new WeightedRandomFishable(new ItemStack(OreSpawnMain.MyWoodFish), 10), new WeightedRandomFishable(new ItemStack(OreSpawnMain.MyGreyFish), 15));
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private Block inTile;
    private boolean inGround;
    private int ticksInGround;
    private int ticksInAir;
    private int fish_on_hook;
    private int fish_wait_time;
    private int ticks_catchable;
    private float fish_direction;
    public Entity caughtEntity;
    private int fishPosRotationIncrements;
    private double fishX;
    private double fishY;
    private double fishZ;
    private double fishYaw;
    private double fishPitch;
    @SideOnly(value=Side.CLIENT)
    private double clientMotionX;
    @SideOnly(value=Side.CLIENT)
    private double clientMotionY;
    @SideOnly(value=Side.CLIENT)
    private double clientMotionZ;
    private int fishing_in_lava = 0;

    public UltimateFishHook(World worldIn) {
        super(worldIn);
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
        //this.fireResistance = 3000;
        this.isImmuneToFire = true;
    }

    @SideOnly(value=Side.CLIENT)
    public UltimateFishHook(World UltimateFishHook(World worldIn, net.minecraft.entity.player.EntityPlayer par2EntityPlayer) {
        super(worldIn);
        this.ignoreFrustumCheck = true;
        this.angler = par2EntityPlayer;
        this.angler.fishEntity = this;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(par2EntityPlayer.posX, par2EntityPlayer.posY + 1.62 - (double)par2EntityPlayer.yOffset, par2EntityPlayer.posZ, par2EntityPlayer.rotationYaw, par2EntityPlayer.rotationPitch);
        this.posX -= (double)(net.minecraft.util.math.MathHelper.cos((float)(this.rotationYaw / 180.0f * (float)Math.PI)) * 0.16f);
        this.posY -= (double)0.1f;
        this.posZ -= (double)(net.minecraft.util.math.MathHelper.sin((float)(this.rotationYaw / 180.0f * (float)Math.PI)) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        float f = 0.4f;
        this.motionX = -net.minecraft.util.math.MathHelper.sin((float)(this.rotationYaw / 180.0f * (float)Math.PI)) * net.minecraft.util.math.MathHelper.cos((float)(this.rotationPitch / 180.0f * (float)Math.PI)) * f;
        this.motionZ = net.minecraft.util.math.MathHelper.cos((float)(this.rotationYaw / 180.0f * (float)Math.PI)) * net.minecraft.util.math.MathHelper.cos((float)(this.rotationPitch / 180.0f * (float)Math.PI)) * f;
        this.motionY = -net.minecraft.util.math.MathHelper.sin((float)(this.rotationPitch / 180.0f * (float)Math.PI)) * f;
        this.handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
        //this.fireResistance = 3000;
        this.isImmuneToFire = true;
    }

    protected void entityInit() {
        //this.fireResistance = 3000;
        this.isImmuneToFire = true;
    }

    public void handleHookCasting(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_) {
        float f2 = net.minecraft.util.math.MathHelper.sqrt_double((double)(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_));
        p_146035_1_ /= (double)f2;
        p_146035_3_ /= (double)f2;
        p_146035_5_ /= (double)f2;
        p_146035_1_ += this.rand.nextGaussian() * (double)0.0075f * (double)p_146035_8_;
        p_146035_3_ += this.rand.nextGaussian() * (double)0.0075f * (double)p_146035_8_;
        p_146035_5_ += this.rand.nextGaussian() * (double)0.0075f * (double)p_146035_8_;
        this.motionX = p_146035_1_ *= (double)p_146035_7_;
        this.motionY = p_146035_3_ *= (double)p_146035_7_;
        this.motionZ = p_146035_5_ *= (double)p_146035_7_;
        float f3 = net.minecraft.util.math.MathHelper.sqrt_double((double)(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_));
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0 / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_146035_3_, f3) * 180.0 / Math.PI);
        this.ticksInGround = 0;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean isInRangeToRenderDist(double par1) {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        return par1 < (d1 *= 64.0) * d1;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.fishX = par1;
        this.fishY = par3;
        this.fishZ = par5;
        this.fishYaw = par7;
        this.fishPitch = par8;
        this.fishPosRotationIncrements = par9;
        this.motionX = this.clientMotionX;
        this.motionY = this.clientMotionY;
        this.motionZ = this.clientMotionZ;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        this.clientMotionX = this.motionX = par1;
        this.clientMotionY = this.motionY = par3;
        this.clientMotionZ = this.motionZ = par5;
    }

    public void onUpdate() {
        if (this.fishPosRotationIncrements > 0) {
            double d7 = this.posX + (this.fishX - this.posX) / (double)this.fishPosRotationIncrements;
            double d8 = this.posY + (this.fishY - this.posY) / (double)this.fishPosRotationIncrements;
            double d9 = this.posZ + (this.fishZ - this.posZ) / (double)this.fishPosRotationIncrements;
            double d1 = net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.fishYaw - (double)this.rotationYaw));
            this.rotationYaw = (float)((double)this.rotationYaw + d1 / (double)this.fishPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.fishPitch - (double)this.rotationPitch) / (double)this.fishPosRotationIncrements);
            --this.fishPosRotationIncrements;
            this.setPosition(d7, d8, d9);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        } else {
            double d2;
            if (!this.world.isRemote) {
                ItemStack itemstack = this.angler.getHeldItemMainhand();
                if (this.angler.isDead() || !this.angler.isEntityAlive() || itemstack == null || itemstack.getItem() != OreSpawnMain.MyUltimateFishingRod || this.getDistanceSq((Entity)this.angler) > 1024.0) {
                    this.setDead();
                    this.angler.fishEntity = null;
                    return;
                }
                if (this.caughtEntity != null) {
                    if (!this.caughtEntity.isDead()) {
                        this.posX = this.caughtEntity.posX;
                        this.posY = this.caughtEntity.getEntityBoundingBox().minY + (double)this.caughtEntity.height * 0.8;
                        this.posZ = this.caughtEntity.posZ;
                        return;
                    }
                    this.caughtEntity = null;
                }
            }
            if (this.ticksCatchable > 0) {
                --this.ticksCatchable;
            }
            if (this.inGround) {
                if (this.world.getBlockState(new net.minecraft.util.math.BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
                    ++this.ticksInGround;
                    if (this.ticksInGround == 1200) {
                        this.setDead();
                    }
                    return;
                }
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            } else {
                ++this.ticksInAir;
            }
            Vec3d vec31 = new Vec3d((double)this.posX, (double)this.posY, (double)this.posZ);
            Vec3d vec3 = new Vec3d((double)(this.posX + this.motionX), (double)(this.posY + this.motionY), (double)(this.posZ + this.motionZ));
            RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec31, vec3);
            vec31 = new Vec3d((double)this.posX, (double)this.posY, (double)this.posZ);
            vec3 = new Vec3d((double)(this.posX + this.motionX), (double)(this.posY + this.motionY), (double)(this.posZ + this.motionZ));
            if (movingobjectposition != null) {
                vec3 = new Vec3d((double)movingobjectposition.hitVec.x, (double)movingobjectposition.hitVec.y, (double)movingobjectposition.hitVec.z);
            }
            Entity entity = null;
            List list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double d0 = 0.0;
            for (int i = 0; i < list.size(); ++i) {
                float f;
                AxisAlignedBB axisalignedbb;
                RayTraceResult movingobjectposition1;
                Entity entity1 = (Entity)list.get(i);
                if (!entity1.canBeCollidedWith() || entity1 == this.angler && this.ticksInAir < 5 || (movingobjectposition1 = (axisalignedbb = entity1.getEntityBoundingBox().expand((double)(f = 0.3f), (double)f, (double)f)).calculateIntercept(vec31, vec3)) == null || !((d2 = vec31.distanceTo(movingobjectposition1.hitVec)) < d0) && d0 != 0.0) continue;
                entity = entity1;
                d0 = d2;
            }
            if (entity != null) {
                movingobjectposition = new RayTraceResult(entity);
            }
            if (movingobjectposition != null) {
                if (movingobjectposition.entityHit != null) {
                    if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.angler), 0.0f)) {
                        this.caughtEntity = movingobjectposition.entityHit;
                    }
                } else {
                    this.inGround = true;
                }
            }
            if (!this.inGround) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                float f5 = net.minecraft.util.math.MathHelper.sqrt_double((double)(this.motionX * this.motionX + this.motionZ * this.motionZ));
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
                this.rotationPitch = (float)(Math.atan2(this.motionY, f5) * 180.0 / Math.PI);
                while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                    this.prevRotationPitch -= 360.0f;
                }
                while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                    this.prevRotationPitch += 360.0f;
                }
                while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                    this.prevRotationYaw -= 360.0f;
                }
                while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                    this.prevRotationYaw += 360.0f;
                }
                this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
                this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
                float f6 = 0.92f;
                if (this.onGround || this.collidedHorizontally) {
                    f6 = 0.5f;
                }
                int b0 = 5;
                double d10 = 0.0;
                for (int j = 0; j < b0; ++j) {
                    double d3 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(j + 0) / (double)b0 - 0.125 + 0.125;
                    double d4 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(j + 1) / (double)b0 - 0.125 + 0.125;
                    AxisAlignedBB axisalignedbb1 = new AxisAlignedBB((double)this.getEntityBoundingBox().minX, (double)d3, (double)this.getEntityBoundingBox().minZ, (double)this.getEntityBoundingBox().maxX, (double)d4, (double)this.getEntityBoundingBox().maxZ);
                    if (this.world.isAABBInMaterial(axisalignedbb1, Material.WATER)) {
                        d10 += 1.0 / (double)b0;
                    }
                    if (!this.world.isAABBInMaterial(axisalignedbb1, Material.LAVA)) continue;
                    d10 += 1.0 / (double)b0;
                }
                if (!this.world.isRemote && d10 > 0.0) {
                    WorldServer worldserver = (WorldServer)this.world;
                    int k = 1;
                    if (this.rand.nextFloat() < 0.25f && this.world.isRainingAt(net.minecraft.util.math.MathHelper.floor_double((double)this.posX), net.minecraft.util.math.MathHelper.floor_double((double)this.posY) + 1, net.minecraft.util.math.MathHelper.floor_double((double)this.posZ))) {
                        k = 2;
                    }
                    if (this.rand.nextFloat() < 0.5f && !this.world.canBlockSeeTheSky(net.minecraft.util.math.MathHelper.floor_double((double)this.posX), net.minecraft.util.math.MathHelper.floor_double((double)this.posY) + 1, net.minecraft.util.math.MathHelper.floor_double((double)this.posZ))) {
                        --k;
                    }
                    if (this.fish_on_hook > 0) {
                        --this.fish_on_hook;
                        if (this.fish_on_hook <= 0) {
                            this.fish_wait_time = 0;
                            this.ticks_catchable = 0;
                        }
                    } else if (this.ticks_catchable > 0) {
                        this.ticks_catchable -= k;
                        if (this.ticks_catchable <= 0) {
                            this.motionY -= (double)0.2f;
                            this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.splash")), net.minecraft.util.SoundCategory.NEUTRAL, 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f));
                            float f1 = net.minecraft.util.math.MathHelper.floor_double((double)this.getEntityBoundingBox().minY);
                            worldserver.func_147487_a("bubble", this.posX, (double)(f1 + 1.0f), this.posZ, (int)(1.0f + this.width * 20.0f), (double)this.width, 0.0, (double)this.width, (double)0.2f);
                            worldserver.func_147487_a("wake", this.posX, (double)(f1 + 1.0f), this.posZ, (int)(1.0f + this.width * 20.0f), (double)this.width, 0.0, (double)this.width, (double)0.2f);
                            this.fish_on_hook = net.minecraft.util.math.MathHelper.getRandomIntegerInRange((Random)this.rand, (int)10, (int)30);
                        } else {
                            this.fish_direction = (float)((double)this.fish_direction + this.rand.nextGaussian() * 4.0);
                            float f1 = this.fish_direction * ((float)Math.PI / 180);
                            float f7 = net.minecraft.util.math.MathHelper.sin((float)f1);
                            float f2 = net.minecraft.util.math.MathHelper.cos((float)f1);
                            double d11 = this.posX + (double)(f7 * (float)this.ticks_catchable * 0.1f);
                            double d5 = (float)net.minecraft.util.math.MathHelper.floor_double((double)this.getEntityBoundingBox().minY) + 1.0f;
                            double d6 = this.posZ + (double)(f2 * (float)this.ticks_catchable * 0.1f);
                            if (this.rand.nextFloat() < 0.15f) {
                                worldserver.func_147487_a("bubble", d11, d5 - (double)0.1f, d6, 1, (double)f7, 0.1, (double)f2, 0.0);
                            }
                            float f3 = f7 * 0.04f;
                            float f4 = f2 * 0.04f;
                            worldserver.func_147487_a("wake", d11, d5, d6, 0, (double)f4, 0.01, (double)(-f3), 1.0);
                            worldserver.func_147487_a("wake", d11, d5, d6, 0, (double)(-f4), 0.01, (double)f3, 1.0);
                        }
                    } else if (this.fish_wait_time > 0) {
                        this.fish_wait_time -= k;
                        float f1 = 0.15f;
                        if (this.fish_wait_time < 20) {
                            f1 = (float)((double)f1 + (double)(20 - this.fish_wait_time) * 0.05);
                        } else if (this.fish_wait_time < 40) {
                            f1 = (float)((double)f1 + (double)(40 - this.fish_wait_time) * 0.02);
                        } else if (this.fish_wait_time < 60) {
                            f1 = (float)((double)f1 + (double)(60 - this.fish_wait_time) * 0.01);
                        }
                        if (this.rand.nextFloat() < f1) {
                            float f7 = net.minecraft.util.math.MathHelper.randomFloatClamp((Random)this.rand, (float)0.0f, (float)360.0f) * ((float)Math.PI / 180);
                            float f2 = net.minecraft.util.math.MathHelper.randomFloatClamp((Random)this.rand, (float)25.0f, (float)60.0f);
                            double d11 = this.posX + (double)(net.minecraft.util.math.MathHelper.sin((float)f7) * f2 * 0.1f);
                            double d5 = (float)net.minecraft.util.math.MathHelper.floor_double((double)this.getEntityBoundingBox().minY) + 1.0f;
                            double d6 = this.posZ + (double)(net.minecraft.util.math.MathHelper.cos((float)f7) * f2 * 0.1f);
                            worldserver.func_147487_a("splash", d11, d5, d6, 2 + this.rand.nextInt(2), (double)0.1f, 0.0, (double)0.1f, 0.0);
                        }
                        if (this.fish_wait_time <= 0) {
                            this.fish_direction = net.minecraft.util.math.MathHelper.randomFloatClamp((Random)this.rand, (float)0.0f, (float)360.0f);
                            this.ticks_catchable = net.minecraft.util.math.MathHelper.getRandomIntegerInRange((Random)this.rand, (int)100, (int)200);
                        }
                    } else {
                        this.fish_wait_time = net.minecraft.util.math.MathHelper.getRandomIntegerInRange((Random)this.rand, (int)50, (int)300);
                        this.fish_wait_time -= EnchantmentHelper.func_151387_h((net.minecraft.entity.EntityLivingBase)this.angler) * 20 * 5;
                    }
                    if (this.fish_on_hook > 0) {
                        this.motionY -= (double)(this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2;
                    }
                }
                d2 = d10 * 2.0 - 1.0;
                this.motionY += (double)0.04f * d2;
                if (d10 > 0.0) {
                    f6 = (float)((double)f6 * 0.9);
                    this.motionY *= 0.8;
                }
                this.motionX *= (double)f6;
                this.motionY *= (double)f6;
                this.motionZ *= (double)f6;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.xTile);
        par1NBTTagCompound.setShort("yTile", (short)this.yTile);
        par1NBTTagCompound.setShort("zTile", (short)this.zTile);
        par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock((Block)this.inTile));
        par1NBTTagCompound.setByte("shake", (byte)this.ticksCatchable);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.xTile = par1NBTTagCompound.getShort("xTile");
        this.yTile = par1NBTTagCompound.getShort("yTile");
        this.zTile = par1NBTTagCompound.getShort("zTile");
        this.inTile = Block.getBlockById((int)(par1NBTTagCompound.getByte("inTile") & 0xFF));
        this.ticksCatchable = par1NBTTagCompound.getByte("shake") & 0xFF;
        this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
    }

    @SideOnly(value=Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }

    public int handleHookRetraction() {
        if (this.world.isRemote) {
            return 0;
        }
        int b0 = 0;
        if (this.caughtEntity != null) {
            double d0 = this.angler.posX - this.posX;
            double d2 = this.angler.posY - this.posY;
            double d4 = this.angler.posZ - this.posZ;
            double d6 = net.minecraft.util.math.MathHelper.sqrt_double((double)(d0 * d0 + d2 * d2 + d4 * d4));
            double d8 = 0.1;
            this.caughtEntity.motionX += d0 * d8;
            this.caughtEntity.motionY += d2 * d8 + (double)net.minecraft.util.math.MathHelper.sqrt_double((double)d6) * 0.08;
            this.caughtEntity.motionZ += d4 * d8;
            b0 = 3;
        } else if (this.fish_on_hook > 0) {
            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + 1.25, this.posZ, this.func_146033_f());
            double d1 = this.angler.posX - this.posX;
            double d3 = this.angler.posY - this.posY;
            double d5 = this.angler.posZ - this.posZ;
            double d7 = net.minecraft.util.math.MathHelper.sqrt_double((double)(d1 * d1 + d3 * d3 + d5 * d5));
            double d9 = 0.1;
            entityitem.motionX = d1 * d9;
            entityitem.motionY = d3 * d9 + (double)net.minecraft.util.math.MathHelper.sqrt_double((double)d7) * 0.08;
            entityitem.motionZ = d5 * d9;
            entityitem.fireResistance = 3000;
            this.world.spawnEntity((Entity)entityitem);
            this.angler.world.spawnEntity((Entity)new EntityXPOrb(this.angler.world, this.angler.posX, this.angler.posY + 0.5, this.angler.posZ + 0.5, this.rand.nextInt(6) + 1));
            b0 = 1;
        }
        if (this.inGround) {
            b0 = 2;
        }
        this.setDead();
        this.angler.fishEntity = null;
        return b0;
    }

    private ItemStack func_146033_f() {
        float f = this.world.rand.nextFloat();
        int i = EnchantmentHelper.func_151386_g((net.minecraft.entity.EntityLivingBase)this.angler);
        int j = EnchantmentHelper.func_151387_h((net.minecraft.entity.EntityLivingBase)this.angler);
        float f1 = 0.1f - (float)i * 0.025f - (float)j * 0.01f;
        float f2 = 0.05f + (float)i * 0.01f - (float)j * 0.01f;
        f1 = net.minecraft.util.math.MathHelper.clamp_float((float)f1, (float)0.0f, (float)1.0f);
        f2 = net.minecraft.util.math.MathHelper.clamp_float((float)f2, (float)0.0f, (float)1.0f);
        Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock(;
        if (this.handleLavaMovement() || bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) {
            this.angler.addStat(StatList.fishCaughtStat, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (Collection)orespawn_lava_fish)).getItemStack(this.rand);
        }
        if (f < f1) {
            this.angler.addStat(StatList.field_151183_A, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (Collection)JUNK)).getItemStack(this.rand);
        }
        if ((f -= f1) < f2) {
            this.angler.addStat(StatList.field_151184_B, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (Collection)VALUABLES)).getItemStack(this.rand);
        }
        float f3 = this.world.rand.nextFloat();
        this.angler.addStat(StatList.fishCaughtStat, 1);
        if (f3 < 0.5f) {
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (Collection)FISH)).getItemStack(this.rand);
        }
        return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (Collection)orespawn_fish)).getItemStack(this.rand);
    }

    public void setDead() {
        super.setDead();
        if (this.angler != null) {
            this.angler.fishEntity = null;
        }
    }
}

