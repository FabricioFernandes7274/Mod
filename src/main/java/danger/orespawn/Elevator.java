/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.entity.net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.net.minecraft.network.play.client.CPacketPlayer$C05PacketPlayerLook
 *  net.minecraft.network.play.client.net.minecraft.network.play.client.CPacketInput
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.entity.net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.net.minecraft.network.play.client.CPacketInput;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SideOnly;

public class Elevator
extends EntityLiving {
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private int damage_counter = 100;
    private int exploding = 0;
    private int color = 1;
    private int playing = 0;
    private static final ResourceLocation texture1 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator1.png");
    private static final ResourceLocation texture2 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator2.png");
    private static final ResourceLocation texture3 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator3.png");
    private static final ResourceLocation texture4 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator4.png");
    private static final ResourceLocation texture5 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator5.png");
    private static final ResourceLocation texture6 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator6.png");
    private static final ResourceLocation texture7 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator7.png");
    private static final ResourceLocation texture8 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator8.png");
    private static final ResourceLocation texture9 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator9.png");
    private static final ResourceLocation texture10 = new net.minecraft.util.ResourceLocation("orespawn", "Elevator10.png");

    public Elevator(World par1World) {
        super(par1World);
        this.setSize(1.25f, 1.0f);
        this.getPassengers() = null;
    }

    public Elevator(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    public ResourceLocation getTexture() {
        switch (this.getColor()) {
            case 1: {
                return texture1;
            }
            case 2: {
                return texture2;
            }
            case 3: {
                return texture3;
            }
            case 4: {
                return texture4;
            }
            case 5: {
                return texture5;
            }
            case 6: {
                return texture6;
            }
            case 7: {
                return texture7;
            }
            case 8: {
                return texture8;
            }
            case 9: {
                return texture9;
            }
            case 10: {
                return texture10;
            }
        }
        return texture1;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)1.33f);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    protected boolean canDespawn() {
        return false;
    }

    public boolean shouldRiderSit() {
        return false;
    }

    public int getTrackingRange() {
        return 128;
    }

    public int getUpdateFrequency() {
        return 10;
    }

    public boolean sendsVelocityUpdates() {
        return true;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(22, (Object)new Integer(0));
        this.dataManager.register(23, (Object)new Integer(1));
        this.dataManager.register(24, (Object)new Float(0.0f));
        this.dataManager.register(20, (Object)new Integer(0));
        this.dataManager.register(21, (Object)new Integer(0));
        this.enablePersistence();
    }

    public boolean canBePushed() {
        return true;
    }

    public double getMountedYOffset() {
        return 0.5;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean p = par1DamageSource.getEntity() instanceof net.minecraft.entity.player.EntityPlayer;
        if (this.getPassengers() != null && !p) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return false;
        }
        if (!this.world.isRemote && !this.isDead()) {
            boolean flag;
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + par2 * 10.0f);
            this.setBeenAttacked();
            boolean bl = flag = par1DamageSource.getEntity() instanceof net.minecraft.entity.player.EntityPlayer && ((net.minecraft.entity.player.EntityPlayer)par1DamageSource.getEntity()).isCreative();
            if (flag || this.getDamageTaken() > 40.0f) {
                if (this.getPassengers() != null) {
                    this.getPassengers().startRiding((Entity)this);
                }
                if (!flag) {
                    this.dropItem(OreSpawnMain.MyElevator, 1);
                }
                this.setDead();
            }
            return true;
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }

    public boolean canBeCollidedWith() {
        return !this.isDead();
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.boatPosRotationIncrements = this.getPassengers() != null ? par9 + 8 : 6;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        this.velocityX = this.motionX = par1;
        this.velocityY = this.motionY = par3;
        this.velocityZ = this.motionZ = par5;
    }

    public void onLivingpdate() {
        if (this.getPassengers() == null) {
            super.onLivingUpdate();
        }
        this.setFire(0);
    }

    public void onUpdate() {
        int k;
        int i;
        Block bid;
        double d5;
        double d4;
        List list = null;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d6 = this.rand.nextFloat() * 2.0f - 1.0f;
        double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.85;
        double gh = 0.75;
        int dist = 2;
        if (this.isDead()) {
            return;
        }
        this.isAirBorne = true;
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (velocity > 0.15 && this.getPassengers() != null) {
            d4 = Math.cos(Math.toRadians(this.rotationYaw + 270.0f));
            d5 = Math.sin(Math.toRadians(this.rotationYaw + 270.0f));
            bid = Blocks.AIR;
            for (i = 1; i < 10 && (bid = this.world.getBlock((int)this.posX, (int)this.posY - i, (int)this.posZ)) == Blocks.AIR; ++i) {
            }
            int j = 0;
            while ((double)j < 1.0 + velocity * 10.0) {
                double d9;
                double d8;
                d6 = this.rand.nextFloat() * 2.0f - 1.0f;
                d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    d8 = this.posX - d4 * d6 * 0.8 + d5 * d7;
                    d9 = this.posZ - d5 * d6 * 0.8 - d4 * d7;
                    if (this.rand.nextBoolean()) {
                        this.world.spawnParticle("smoke", d8, this.posY - 0.25, d9, this.motionX, this.motionY, this.motionZ);
                    } else {
                        this.world.spawnParticle("reddust", d8, this.posY - 0.25, d9, this.motionX, this.motionY, this.motionZ);
                    }
                } else {
                    d8 = this.posX + d4 + d5 * d6 * 0.7;
                    d9 = this.posZ + d5 - d4 * d6 * 0.7;
                    if (this.rand.nextBoolean()) {
                        this.world.spawnParticle("smoke", d8, this.posY - 0.225, d9, this.motionX, this.motionY, this.motionZ);
                    } else {
                        this.world.spawnParticle("reddust", d8, this.posY - 0.225, d9, this.motionX, this.motionY, this.motionZ);
                    }
                }
                if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
                    for (k = 0; k < 5; ++k) {
                        this.world.spawnParticle("splash", this.posX + (double)this.rand.nextFloat(), this.posY - (double)i + 1.25, this.posZ + (double)this.rand.nextFloat(), this.motionX / 2.0, this.motionY + velocity, this.motionZ / 2.0);
                    }
                }
                ++j;
            }
        }
        if (this.playing > 0) {
            --this.playing;
        }
        if (this.getPassengers() != null && this.playing == 0 && this.world.rand.nextInt(80) == 1) {
            this.world.playSoundAtEntity(this.getPassengers(), "orespawn:hover", 0.45f, 1.0f);
            this.playing = 55;
        }
        if (!this.world.isRemote) {
            if (this.exploding > 0) {
                --this.exploding;
            }
            if (this.exploding == 0 && velocity > 0.65 && this.world.rand.nextInt(20000) == 1) {
                this.exploding = 45;
                this.playing = 50;
            }
            this.setExploding(this.exploding);
        } else {
            this.exploding = this.getExploding();
        }
        if (this.getExploding() > 0 && this.getPassengers() != null) {
            if (this.world.rand.nextInt(10) == 1) {
                this.world.playSoundAtEntity(this.getPassengers(), "random.explode", 0.55f, 0.75f + this.world.rand.nextFloat());
            }
            for (i = 0; i < 15; ++i) {
                this.world.spawnParticle("explode", (double)((int)(this.posX + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0f))), (double)((int)(this.posY + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0f))), (double)((int)(this.posZ + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0f))), this.motionX, 0.0, this.motionZ);
                this.world.spawnParticle("largeexplode", (double)((int)(this.posX + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0f))), (double)((int)(this.posY + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0f))), (double)((int)(this.posZ + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0f))), this.motionX, 0.0, this.motionZ);
                this.world.spawnParticle("smoke", (double)((int)(this.posX + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0f))), (double)((int)(this.posY + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0f))), (double)((int)(this.posZ + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0f))), this.motionX, 0.0, this.motionZ);
                this.world.spawnParticle("largesmoke", (double)((int)(this.posX + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0f))), (double)((int)(this.posY + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0f))), (double)((int)(this.posZ + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0f))), this.motionX, 0.0, this.motionZ);
            }
        }
        if (this.world.isRemote) {
            if (this.getPassengers() == null) {
                bid = this.world.getBlock((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ);
                if (bid != Blocks.AIR) {
                    this.motionY += 0.06;
                    this.posY += 0.07;
                    this.boatY += 0.07;
                } else {
                    this.motionY -= 0.003;
                }
            } else if (this.getPassengers() instanceof net.minecraft.client.entity.EntityPlayerSP) {
                net.minecraft.client.entity.EntityPlayerSP pp = (net.minecraft.client.entity.EntityPlayerSP)this.getPassengers();
                pp.connection.addToSendQueue((Packet)new net.minecraft.network.play.client.CPacketPlayer.PositionRotation(pp.rotationYaw, pp.rotationPitch, pp.onGround));
                pp.connection.addToSendQueue((Packet)new net.minecraft.network.play.client.CPacketInput(pp.moveStrafing, pp.moveForward, pp.movementInput.jump, pp.movementInput.sneak));
            }
            if (this.boatPosRotationIncrements > 0) {
                d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                double d10 = net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.boatYaw - (double)this.rotationYaw));
                if (this.getPassengers() != null) {
                    d10 = net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.getPassengers().isEmpty() ? 0.0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw - (double)this.rotationYaw));
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                --this.boatPosRotationIncrements;
            } else {
                d4 = this.posX + this.motionX;
                d5 = this.posY + this.motionY;
                double d11 = this.posZ + this.motionZ;
                this.setPosition(d4, d5, d11);
                this.motionX *= 0.99;
                this.motionY *= 0.95;
                this.motionZ *= 0.99;
            }
        } else {
            if (this.getPassengers() != null) {
                gh = 1.25;
            }
            if ((bid = this.world.getBlock((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)) != Blocks.AIR) {
                this.motionY += 0.06;
                this.posY += 0.1;
                if (bid == Blocks.TALLGRASS && this.getPassengers() != null && this.world.rand.nextInt(200) == 1 && this.world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.world.setBlock((int)this.posX, (int)(this.posY - gh), (int)this.posZ, Blocks.AIR);
                }
                if (bid == Blocks.GRASS && this.getPassengers() != null && this.world.rand.nextInt(200) == 1 && this.world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.world.setBlock((int)this.posX, (int)(this.posY - gh), (int)this.posZ, Blocks.DIRT);
                }
            } else {
                this.motionY -= 0.01;
            }
            if (this.getPassengers() != null) {
                double rdv;
                net.minecraft.entity.player.EntityPlayer pp = (net.minecraft.entity.player.EntityPlayer)this.getPassengers();
                obstruction_factor = 0.0;
                dist = 3;
                dist += (int)(velocity * 8.0);
                for (k = 1; k < dist; ++k) {
                    for (i = 1; i < dist * 2; ++i) {
                        double dz;
                        double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                        bid = this.world.getBlock((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))));
                        if (bid == Blocks.AIR) continue;
                        obstruction_factor += 0.05;
                    }
                }
                this.motionY += obstruction_factor * 0.11;
                this.posY += obstruction_factor * 0.11;
                d4 = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw;
                d4 %= 360.0;
                while (d4 < 0.0) {
                    d4 += 360.0;
                }
                d5 = this.rotationYaw;
                d5 %= 360.0;
                while (d5 < 0.0) {
                    d5 += 360.0;
                }
                for (relative_g = (d4 - d5) % 180.0; relative_g < 0.0; relative_g += 180.0) {
                }
                if (relative_g > 90.0) {
                    relative_g -= 180.0;
                }
                if (velocity > 0.01) {
                    d4 = 1.85 - velocity;
                    if ((d4 = Math.abs(d4)) < 0.01) {
                        d4 = 0.01;
                    }
                    if (d4 > 0.9) {
                        d4 = 0.9;
                    }
                    this.rotationYaw = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw + (float)(relative_g * d4);
                } else {
                    this.rotationYaw = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw;
                }
                relative_g = Math.abs(relative_g) * velocity;
                if (relative_g > 50.0) {
                    relative_g = 0.0;
                }
                this.rotationPitch = 10.0f * (float)velocity;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                if (this.exploding != 0 && (newvelocity -= 0.05) < 0.0) {
                    newvelocity = 0.0;
                }
                double rr = Math.atan2(this.getPassengers().isEmpty() ? 0 : this.getPassengers().get(0).motionZ, this.getPassengers().isEmpty() ? 0 : this.getPassengers().get(0).motionX);
                double rhm = Math.atan2(this.motionZ, this.motionX);
                double rhdir = Math.toRadians((this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw + 90.0f) % 360.0f);
                double rt = 0.0;
                double pi = 3.1415926545;
                double deltav = 0.0;
                float im = pp.moveForward;
                if (OreSpawnMain.flyup_keystate != 0) {
                    max_speed += 1.0;
                }
                if ((rdv = Math.abs(rhm - rhdir) % (pi * 2.0)) > pi) {
                    rdv -= pi * 2.0;
                }
                rdv = Math.abs(rdv);
                if (Math.abs(newvelocity) < 0.01) {
                    rdv = 0.0;
                }
                if (rdv > 1.5) {
                    newvelocity = -newvelocity;
                }
                if (Math.abs(im) > 0.001f) {
                    if (im > 0.0f) {
                        deltav = 0.025;
                        if (max_speed > 1.0) {
                            deltav += 0.15;
                        }
                    } else {
                        max_speed = 0.35;
                        deltav = -0.02;
                    }
                    if ((newvelocity += deltav) >= 0.0) {
                        if (newvelocity > max_speed) {
                            newvelocity = max_speed;
                        }
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                    } else {
                        if (newvelocity < -max_speed) {
                            newvelocity = -max_speed;
                        }
                        newvelocity = -newvelocity;
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                    }
                } else if (newvelocity >= 0.0) {
                    this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                    this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                } else {
                    this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                    this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                }
            } else if (this.getPassengers() == null) {
                this.motionX = 0.0;
                this.motionZ = 0.0;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.collidedHorizontally && velocity > 0.75) {
                this.setDead();
                int p = this.world.rand.nextInt(10);
                for (k = 0; k < 6 + p; ++k) {
                    this.dropItem(Items.STICK, 1);
                }
                for (k = 0; k < 2; ++k) {
                    this.dropItem(Items.DIAMOND, 1);
                }
            } else {
                this.motionX *= 0.98;
                this.motionY *= 0.94;
                this.motionZ *= 0.98;
            }
            if ((list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.boundingBox.expand(0.25, 0.0, 0.25))) != null && !list.isEmpty()) {
                for (int l = 0; l < list.size(); ++l) {
                    Entity entity = (Entity)list.get(l);
                    if (entity == this.getPassengers() || !entity.canBePushed() || entity instanceof Girlfriend || entity instanceof Boyfriend) continue;
                    entity.applyEntityCollision((Entity)this);
                }
            }
            if (this.getPassengers() != null && this.getPassengers().isDead()) {
                this.getPassengers() = null;
            }
        }
    }

    public void updateRiderPosition() {
        if (this.getPassengers() != null) {
            this.getPassengers().setPosition(this.posX, this.posY + this.getMountedYOffset() + this.getPassengers().getYOffset(), this.posZ);
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("HoverColor", this.getColor());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.color = par1NBTTagCompound.getInteger("HoverColor");
        if (this.color < 1) {
            this.color = 1;
        }
        if (this.color > 10) {
            this.color = 10;
        }
        this.setColor(this.color);
    }

    public float getShadowSize() {
        return 0.25f;
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (var2 != null && var2.getItem() == OreSpawnMain.MyUltimateSword && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            if (!this.world.isRemote) {
                ++this.color;
                if (this.color > 10) {
                    this.color = 1;
                }
                this.setColor(this.color);
            }
            return true;
        }
        if (this.getPassengers() != null && this.getPassengers() instanceof net.minecraft.entity.player.EntityPlayer && this.getPassengers() != par1EntityPlayer) {
            return true;
        }
        if (!this.world.isRemote) {
            par1EntityPlayer.startRiding((Entity)this);
        }
        return true;
    }

    public void setDamageTaken(float f) {
        this.dataManager.set(24, (Object)Float.valueOf(f));
    }

    public float getDamageTaken() {
        return this.dataManager.get(24);
    }

    public void setTimeSinceHit(int par1) {
        this.dataManager.set(22, (Object)par1);
    }

    public int getTimeSinceHit() {
        return this.dataManager.get(22);
    }

    public void setForwardDirection(int par1) {
        this.dataManager.set(23, (Object)par1);
    }

    public int getForwardDirection() {
        return this.dataManager.get(23);
    }

    public void setExploding(int par1) {
        this.dataManager.set(20, (Object)par1);
    }

    public int getExploding() {
        return this.dataManager.get(20);
    }

    public void setColor(int par1) {
        this.dataManager.set(21, (Object)par1);
    }

    public int getColor() {
        return this.dataManager.get(21);
    }
}

