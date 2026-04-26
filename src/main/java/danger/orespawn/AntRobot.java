/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AntRobot
extends EntityLiving {
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.3f;
    private RenderSpiderRobotInfo renderdata = new RenderSpiderRobotInfo();
    private int didonce = 0;
    private int rideTicker = 0;
    private int owned = 0;

    public AntRobot(World par1World) {
        super(par1World);
        this.setSize(2.75f, 1.25f);
        this.getPassengers() = null;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(1, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 12.0f));
        this.tasks.addTask(2, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.isImmuneToFire = true;
        this.experienceValue = OreSpawnMain.AntRobot_stats.health / 2;
    }

    public AntRobot(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.yOffset, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)OreSpawnMain.AntRobot_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.AntRobot_stats.attack);
    }

    protected boolean canDespawn() {
        return false;
    }

    public void setOwned() {
        this.owned = 1;
    }

    public int getOwned() {
        return this.owned;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.AntRobot_stats.defense;
    }

    protected void updateAITasks() {
        net.minecraft.entity.EntityLivingBase e = null;
        if (this.isDead()) {
            return;
        }
        if (this.getPassengers() != null) {
            return;
        }
        super.updateAITasks();
        if (this.owned == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            if (this.world.rand.nextInt(20) == 0) {
                this.feetFindSomethingToHit();
            }
            if (this.world.rand.nextInt(150) == 0) {
                this.setAttackTarget(null);
            }
            if ((e = this.getAttackTarget()) != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack(2.0f, false);
            }
            if (e != null) {
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getDistanceSq((Entity)e) > 16.0) {
                    double d1 = e.posZ - this.posZ;
                    double d2 = e.posX - this.posX;
                    double dd = Math.atan2(d1, d2);
                    this.goThisWay(0.2 * Math.cos(dd), 0.2 * Math.sin(dd));
                }
            } else {
                this.setAttacking(0);
            }
            if (e != null && this.world.rand.nextInt(15) == 0) {
                e = this.getAttackTarget();
                if (e == null) {
                    e = this.findSomethingToAttack(2.0f, true);
                }
                if (e != null) {
                    if (this.getDistanceSq((Entity)e) < (double)((6.0f + e.width / 2.0f) * (6.0f + e.width / 2.0f))) {
                        this.setAttacking(1);
                        this.attackEntityAsMob((Entity)e);
                    } else {
                        this.setAttacking(0);
                    }
                } else {
                    this.setAttacking(0);
                }
            }
        }
    }

    protected void updateAITick() {
        if (this.getPassengers() != null) {
            return;
        }
        super.updateAITick();
    }

    private void initLegData() {
        if (this.renderdata == null) {
            this.renderdata = new RenderSpiderRobotInfo();
        }
        for (int i = 0; i < 6; ++i) {
            this.renderdata.ycurrentangle[i] = 0.0f;
            this.renderdata.ywantedangle[i] = 0.0f;
            this.renderdata.ydisplayangle[i] = 0.0f;
            this.renderdata.yvelocity[i] = 0.0f;
            this.renderdata.ymid[i] = 0.0f;
            this.renderdata.yoff[i] = 0.0f;
            this.renderdata.yrange[i] = 0.0f;
            this.renderdata.udcurrentangle[i] = 0.0f;
            this.renderdata.udwantedangle[i] = 0.0f;
            this.renderdata.uddisplayangle[i] = 0.0f;
            this.renderdata.udvelocity[i] = 0.0f;
            this.renderdata.p1xangle[i] = 0.7853981633974483;
            this.renderdata.p2xangle[i] = 0.0;
            this.renderdata.p3xangle[i] = -0.7853981633974483;
            this.renderdata.pxvelocity[i] = 0.0f;
            this.renderdata.foot_xpos[i] = (float)this.posX;
            this.renderdata.foot_ypos[i] = (float)this.posY;
            this.renderdata.foot_zpos[i] = (float)this.posZ;
            this.renderdata.realposx[i] = 0.0f;
            this.renderdata.realposy[i] = 0.0f;
            this.renderdata.realposz[i] = 0.0f;
            this.renderdata.legoff[i] = 0.0f;
            this.renderdata.footup[i] = 1;
            this.renderdata.uppoint[i] = 0.0f;
            this.renderdata.footingticker[i] = 0;
            this.renderdata.gpcounter = 0;
            if (i == 0) {
                this.renderdata.legoff[i] = 0.75f;
                this.renderdata.ymid[i] = 0.0f;
                this.renderdata.yrange[i] = 0.2617994f;
                this.renderdata.pairedwith[i] = 1;
                this.renderdata.yoff[i] = -0.75f;
            }
            if (i == 1) {
                this.renderdata.legoff[i] = 0.75f;
                this.renderdata.ymid[i] = (float)Math.PI;
                this.renderdata.yrange[i] = -0.2617994f;
                this.renderdata.pairedwith[i] = 0;
                this.renderdata.yoff[i] = -0.75f;
            }
            if (i == 2) {
                this.renderdata.legoff[i] = 1.0f;
                this.renderdata.ymid[i] = -0.7853982f;
                this.renderdata.yrange[i] = 0.2617994f;
                this.renderdata.pairedwith[i] = 3;
                this.renderdata.yoff[i] = -0.75f;
            }
            if (i == 3) {
                this.renderdata.legoff[i] = 1.0f;
                this.renderdata.ymid[i] = 3.9269907f;
                this.renderdata.yrange[i] = -0.2617994f;
                this.renderdata.pairedwith[i] = 2;
                this.renderdata.yoff[i] = -0.75f;
            }
            if (i == 4) {
                this.renderdata.legoff[i] = 1.15f;
                this.renderdata.ymid[i] = 0.7853982f;
                this.renderdata.yrange[i] = 0.2617994f;
                this.renderdata.pairedwith[i] = 5;
                this.renderdata.yoff[i] = -0.75f;
            }
            if (i != 5) continue;
            this.renderdata.legoff[i] = 1.15f;
            this.renderdata.ymid[i] = 2.3561945f;
            this.renderdata.yrange[i] = -0.2617994f;
            this.renderdata.pairedwith[i] = 4;
            this.renderdata.yoff[i] = -0.75f;
        }
    }

    private float getNewVelocity(float v, float diff, float curval) {
        float tv = v;
        if ((tv *= 18.0f) < 2.0f) {
            tv = 2.0f;
        }
        if (tv > 8.0f) {
            tv = 8.0f;
        }
        if (diff > 0.0f) {
            if ((double)diff < Math.PI / 360 * (double)tv) {
                curval = 0.0f;
            } else {
                curval = (float)((double)curval + 0.004363323129985824 * (double)tv);
                if ((double)diff < 0.06981317007977318 * (double)tv) {
                    curval = (float)(Math.PI / 180 * (double)tv);
                }
                if ((double)diff < Math.PI / 90 * (double)tv) {
                    curval = (float)(Math.PI / 360 * (double)tv);
                }
                if ((double)curval > 0.06981317007977318 * (double)tv) {
                    curval = (float)(0.06981317007977318 * (double)tv);
                }
            }
        } else if ((double)diff > -Math.PI / 360 * (double)tv) {
            curval = 0.0f;
        } else {
            curval = (float)((double)curval - 0.004363323129985824 * (double)tv);
            if ((double)diff > -0.06981317007977318 * (double)tv) {
                curval = -((float)(Math.PI / 180 * (double)tv));
            }
            if ((double)diff > -Math.PI / 90 * (double)tv) {
                curval = -((float)(Math.PI / 360 * (double)tv));
            }
            if ((double)curval < -0.06981317007977318 * (double)tv) {
                curval = -((float)(0.06981317007977318 * (double)tv));
            }
        }
        return curval;
    }

    public void updateLegs() {
        if (!this.world.isRemote) {
            return;
        }
        this.rotationYaw %= 360.0f;
        while (this.rotationYaw < 0.0f) {
            this.rotationYaw += 360.0f;
        }
        ++this.renderdata.gpcounter;
        if (this.didonce == 0) {
            this.didonce = 1;
            this.initLegData();
        }
        float d1 = (float)(this.prevPosX - this.posX);
        float d2 = (float)(this.prevPosY - this.posY);
        float d3 = (float)(this.prevPosZ - this.posZ);
        float realv = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
        int i = 0;
        for (i = 0; i < 6; ++i) {
            double rdv;
            int fcount = 0;
            int n = i;
            this.renderdata.footingticker[n] = this.renderdata.footingticker[n] + 1;
            this.renderdata.realposx[i] = (float)(this.posX - (double)this.renderdata.legoff[i] * Math.sin(Math.toRadians(net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i]));
            this.renderdata.realposz[i] = (float)(this.posZ + (double)this.renderdata.legoff[i] * Math.cos(Math.toRadians(net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i]));
            this.renderdata.realposy[i] = (float)this.posY + this.renderdata.yoff[i];
            int it = this.renderdata.footingticker[i] + this.renderdata.footingticker[this.renderdata.pairedwith[i]];
            if (it > 50 && this.renderdata.footingticker[i] > this.renderdata.footingticker[this.renderdata.pairedwith[i]]) {
                this.renderdata.footingticker[i] = 0;
            }
            d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
            d2 = this.renderdata.realposy[i] - this.renderdata.foot_ypos[i];
            d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
            float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            dd *= 16.0f;
            float da = (float)(Math.abs((double)this.renderdata.ycurrentangle[i] - (Math.toRadians(net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)this.rotationYaw)) + (double)this.renderdata.ymid[i])) % (Math.PI * 2));
            if ((double)da > Math.PI) {
                da = (float)((double)da - Math.PI * 2);
            }
            if ((double)da < -Math.PI) {
                da = (float)((double)da + Math.PI * 2);
            }
            da = Math.abs(da);
            if (dd > 144.0f || dd < 22.0f || da > Math.abs(this.renderdata.yrange[i]) * 8.0f / 6.0f || (double)Math.abs(this.renderdata.udcurrentangle[i]) > 1.25 || this.renderdata.footingticker[i] == 0) {
                this.findNewFooting(i);
                d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
                d2 = this.renderdata.realposy[i] - this.renderdata.foot_ypos[i];
                d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
                dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                dd *= 16.0f;
            }
            float c1 = (float)(49.0 * Math.cos(this.renderdata.p2xangle[i] - this.renderdata.p1xangle[i]));
            float c2 = 49.0f;
            float c3 = (float)(49.0 * Math.cos(this.renderdata.p2xangle[i] - this.renderdata.p3xangle[i]));
            float cc = c1 + c2 + c3;
            float diff = cc - dd;
            this.renderdata.pxvelocity[i] = this.getNewVelocity(realv, (float)((double)diff * Math.PI / 360.0), this.renderdata.pxvelocity[i]);
            if (this.renderdata.pxvelocity[i] == 0.0f || Math.abs(diff) < 8.0f) {
                ++fcount;
            }
            int n2 = i;
            this.renderdata.p1xangle[n2] = this.renderdata.p1xangle[n2] + (double)this.renderdata.pxvelocity[i];
            this.renderdata.p2xangle[i] = 0.0;
            this.renderdata.p3xangle[i] = -this.renderdata.p1xangle[i];
            dd = this.renderdata.uppoint[i] != 0.0f ? (float)Math.atan2(dd, (double)(this.renderdata.realposy[i] - this.renderdata.uppoint[i]) * 16.0) : (float)Math.atan2(dd, (double)(this.renderdata.realposy[i] - this.renderdata.foot_ypos[i]) * 16.0);
            this.renderdata.udwantedangle[i] = (float)((double)dd - 1.5707963267948966);
            while ((double)this.renderdata.udwantedangle[i] > Math.PI) {
                int n3 = i;
                this.renderdata.udwantedangle[n3] = (float)((double)this.renderdata.udwantedangle[n3] - Math.PI * 2);
            }
            while ((double)this.renderdata.udwantedangle[i] < -Math.PI) {
                int n4 = i;
                this.renderdata.udwantedangle[n4] = (float)((double)this.renderdata.udwantedangle[n4] + Math.PI * 2);
            }
            double rhm = this.renderdata.udwantedangle[i];
            double rhdir = this.renderdata.udcurrentangle[i];
            for (rdv = (rhm - rhdir) % (Math.PI * 2); rdv > Math.PI; rdv -= Math.PI * 2) {
            }
            while (rdv < -Math.PI) {
                rdv += Math.PI * 2;
            }
            diff = (float)rdv;
            this.renderdata.udvelocity[i] = this.getNewVelocity(realv * 2.0f, diff, this.renderdata.udvelocity[i]);
            if (this.renderdata.udvelocity[i] == 0.0f || (double)Math.abs(diff) < Math.PI / 90) {
                this.renderdata.uppoint[i] = 0.0f;
                ++fcount;
            }
            rhdir += (double)this.renderdata.udvelocity[i];
            while (rhdir > Math.PI) {
                rhdir -= Math.PI * 2;
            }
            while (rhdir < -Math.PI) {
                rhdir += Math.PI * 2;
            }
            this.renderdata.uddisplayangle[i] = dd = (this.renderdata.udcurrentangle[i] = (float)rhdir);
            d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
            d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
            dd = (float)Math.atan2(d3, d1);
            this.renderdata.ywantedangle[i] = dd;
            rhm = this.renderdata.ywantedangle[i];
            rdv = (rhm - (rhdir = (double)this.renderdata.ycurrentangle[i])) % (Math.PI * 2);
            if (rdv > Math.PI) {
                rdv -= Math.PI * 2;
            }
            if (rdv < -Math.PI) {
                rdv += Math.PI * 2;
            }
            diff = (float)rdv;
            this.renderdata.yvelocity[i] = this.getNewVelocity(realv, diff, this.renderdata.yvelocity[i]);
            if (this.renderdata.yvelocity[i] == 0.0f || (double)Math.abs(diff) < Math.PI / 90) {
                ++fcount;
            }
            int n5 = i;
            this.renderdata.ycurrentangle[n5] = this.renderdata.ycurrentangle[n5] + this.renderdata.yvelocity[i];
            while ((double)this.renderdata.ycurrentangle[i] > Math.PI) {
                int n6 = i;
                this.renderdata.ycurrentangle[n6] = (float)((double)this.renderdata.ycurrentangle[n6] - Math.PI * 2);
            }
            while ((double)this.renderdata.ycurrentangle[i] < -Math.PI) {
                int n7 = i;
                this.renderdata.ycurrentangle[n7] = (float)((double)this.renderdata.ycurrentangle[n7] + Math.PI * 2);
            }
            dd = (float)((double)this.renderdata.ycurrentangle[i] - Math.toRadians(net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)this.rotationYaw)) - 1.5707963267948966);
            while ((double)dd > Math.PI) {
                dd = (float)((double)dd - Math.PI * 2);
            }
            while ((double)dd < -Math.PI) {
                dd = (float)((double)dd + Math.PI * 2);
            }
            this.renderdata.ydisplayangle[i] = dd;
            if (fcount != 3) continue;
            this.renderdata.footup[i] = 0;
        }
    }

    private void findNewFooting(int i) {
        float dd;
        float d2;
        float fy;
        float fz;
        float fx;
        float f = 9.0f;
        boolean found = false;
        float range = 0.0f;
        double rhdir = Math.toRadians((this.rotationYaw + 90.0f) % 360.0f);
        double pi = 3.1415926545;
        this.renderdata.footingticker[i] = 0;
        float d1 = (float)(this.posX - this.prevPosX);
        float d3 = (float)(this.posZ - this.prevPosZ);
        double rhm = Math.atan2(d3, d1);
        double velocity = Math.sqrt(d1 * d1 + d3 * d3);
        double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
        if (rdv > pi) {
            rdv -= pi * 2.0;
        }
        rdv = Math.abs(rdv);
        if (Math.abs(velocity) < 0.01) {
            rdv = 0.0;
        }
        range = this.renderdata.yrange[i];
        range *= 0.8f;
        if (Math.abs((this.prevRotationYaw - this.rotationYaw) % 360.0f) > 0.75f) {
            range = 0.0f;
        }
        if (i >= 4) {
            f = 4.0f;
        }
        if (rdv > 1.5) {
            range = -range;
            f = 4.0f;
            if (i >= 4) {
                f = 9.0f;
            }
        }
        if (i == 0 || i == 1) {
            f = 6.0f;
        }
        float deffx = fx = (float)((double)this.renderdata.realposx[i] - (double)(f / 2.0f) * Math.sin(Math.toRadians(net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i]));
        float deffz = fz = (float)((double)this.renderdata.realposz[i] + (double)(f / 2.0f) * Math.cos(Math.toRadians(net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i]));
        float deffy = fy = this.renderdata.realposy[i] - 1.0f;
        float oldf = f;
        int span = 1;
        while (!found && f > 2.5f) {
            fx = (float)((double)this.renderdata.realposx[i] - (double)f * Math.sin(Math.toRadians(net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i] - (double)range));
            fz = (float)((double)this.renderdata.realposz[i] + (double)f * Math.cos(Math.toRadians(net.minecraft.util.math.MathHelper.wrapAngleTo180_double((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i] - (double)range));
            fy = this.renderdata.realposy[i];
            for (int j = 8; !found && j > -9; --j) {
                block2: for (int m = -span; !found && m <= span; ++m) {
                    for (int n = -span; !found && n <= span; ++n) {
                        Block blk = this.world.getBlock((int)fx + m, (int)fy + j, (int)fz + n);
                        if (blk == Blocks.AIR || !this.world.getBlock((int)fx + m, (int)fy + j, (int)fz + n).getMaterial().isSolid()) continue;
                        d1 = this.renderdata.realposx[i] - (fx + (float)m);
                        d2 = this.renderdata.realposy[i] - (fy + (float)j + 1.0f);
                        d3 = this.renderdata.realposz[i] - (fz + (float)n);
                        dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                        if ((dd *= 16.0f) > 144.0f) continue;
                        fy += (float)(j + 1);
                        fx += (float)m;
                        fz += (float)n;
                        found = true;
                        continue block2;
                    }
                }
            }
            if (!((f -= 1.0f) < 2.5f) || range == 0.0f) continue;
            range = 0.0f;
            span = 3;
            f = oldf;
        }
        if (!found) {
            fx = deffx;
            fy = deffy;
            fz = deffz;
        }
        float sfx = this.renderdata.foot_xpos[i];
        float sfy = this.renderdata.foot_ypos[i];
        float sfz = this.renderdata.foot_zpos[i];
        this.renderdata.foot_xpos[i] = fx;
        this.renderdata.foot_ypos[i] = fy;
        this.renderdata.foot_zpos[i] = fz;
        if (this.renderdata.footup[i] == 0) {
            this.renderdata.footup[i] = 1;
            d1 = sfx - fx;
            d2 = sfy - fy;
            d3 = sfz - fz;
            dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            dd *= 16.0f;
            d1 = (sfy + fy) / 2.0f;
            if (dd > 3.0f) {
                d1 += 0.3f;
            }
            if (dd > 24.0f) {
                d1 += 0.6f;
            }
            if (dd > 50.0f) {
                d1 += 0.6f;
            }
            this.renderdata.uppoint[i] = d1;
        }
    }

    public boolean shouldRiderSit() {
        return true;
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

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.enablePersistence();
        this.initLegData();
        int i = 0;
        this.dataManager.register(20, (Object)i);
    }

    public RenderSpiderRobotInfo getRenderSpiderRobotInfo() {
        return this.renderdata;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 0.55 + Math.cos((float)this.rideTicker * 0.19f) * 0.02;
    }

    public void updateRiderPosition() {
        if (this.getPassengers() != null) {
            float f = -1.25f;
            f = (float)((double)f + Math.cos((float)this.rideTicker * 0.33f) * 0.05);
            this.getPassengers().setPosition(this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + this.getMountedYOffset() + this.getPassengers().getYOffset(), this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("cactus")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("inFire")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("onFire")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("magic")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("starve")) {
            return false;
        }
        Entity e = par1DamageSource.getEntity();
        if (e != null && e instanceof EntityLiving) {
            this.setAttackTarget((net.minecraft.entity.EntityLivingBase)((EntityLiving)e));
            this.faceEntity(e, 20.0f, 20.0f);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean canBeCollidedWith() {
        return !this.isDead();
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.boatPosRotationIncrements = this.getPassengers() != null ? par9 + 8 : par9 + 6;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        if (this.getPassengers() == null) {
            super.setVelocity(par1, par3, par5);
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.setFire(0);
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && !this.world.isRemote && this.getPassengers() != null && this.world.rand.nextInt(50) == 0) {
            this.feetFindSomethingToHit();
        }
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && !this.world.isRemote && this.getPassengers() != null && this.world.rand.nextInt(9) == 0) {
            net.minecraft.entity.EntityLivingBase e = null;
            e = this.findSomethingToAttack(1.0f, true);
            if (e != null) {
                if (this.getDistanceSq((Entity)e) < (double)((6.0f + e.width / 2.0f) * (6.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    this.attackEntityAsMob((Entity)e);
                }
            } else {
                this.setAttacking(0);
            }
        }
        float f = 4.0f;
        float dx = (float)((double)f * Math.cos(Math.toRadians(this.rotationYaw - 80.0f)));
        float dz = (float)((double)f * Math.sin(Math.toRadians(this.rotationYaw - 80.0f)));
        float dx2 = (float)((double)f * Math.cos(Math.toRadians(this.rotationYaw - 90.0f)));
        float dz2 = (float)((double)f * Math.sin(Math.toRadians(this.rotationYaw - 90.0f)));
        if (this.world.rand.nextInt(18) == 0) {
            this.world.spawnParticle("flame", this.posX + (double)dx, this.posY + 0.5, this.posZ + (double)dz, (double)(dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0f), (double)(dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
        if (this.world.rand.nextInt(7) == 0) {
            this.world.spawnParticle("smoke", this.posX + (double)dx, this.posY + 0.5, this.posZ + (double)dz, (double)(dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0f), (double)(dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
        if (this.world.rand.nextInt(16) == 0) {
            this.world.spawnParticle("fireworksSpark", this.posX + (double)dx, this.posY + 0.5, this.posZ + (double)dz, (double)(dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 5.0f), (double)(dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
        dx = (float)((double)f * Math.cos(Math.toRadians(this.rotationYaw - 100.0f)));
        dz = (float)((double)f * Math.sin(Math.toRadians(this.rotationYaw - 100.0f)));
        if (this.world.rand.nextInt(18) == 0) {
            this.world.spawnParticle("flame", this.posX + (double)dx, this.posY + 0.5, this.posZ + (double)dz, (double)(dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0f), (double)(dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
        if (this.world.rand.nextInt(7) == 0) {
            this.world.spawnParticle("smoke", this.posX + (double)dx, this.posY + 0.5, this.posZ + (double)dz, (double)(dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0f), (double)(dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
        if (this.world.rand.nextInt(16) == 0) {
            this.world.spawnParticle("fireworksSpark", this.posX + (double)dx, this.posY + 0.5, this.posZ + (double)dz, (double)(dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 5.0f), (double)(dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
    }

    public void onLivingUpdate() {
        Object list = null;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d6 = this.rand.nextFloat() * 2.0f - 1.0f;
        double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.3;
        double gh = 1.75;
        int dist = 2;
        if (this.isDead()) {
            return;
        }
        if (this.getPassengers() == null) {
            super.onLivingUpdate();
        }
        if (this.motionY > (double)0.85f) {
            this.motionY = 0.85f;
        }
        if (this.motionY < (double)-0.85f) {
            this.motionY = -0.85f;
        }
        if (this.motionX < -1.25) {
            this.motionX = -1.25;
        }
        if (this.motionX > 1.25) {
            this.motionX = 1.25;
        }
        if (this.motionZ < -1.25) {
            this.motionZ = -1.25;
        }
        if (this.motionZ > 1.25) {
            this.motionZ = 1.25;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.rideTicker += this.world.rand.nextInt(3);
        if (this.playing > 0) {
            --this.playing;
        }
        if (this.getPassengers() != null && this.playing == 0 && this.world.rand.nextInt(80) == 1) {
            this.world.playSoundAtEntity((Entity)this, "orespawn:robotspider", 0.35f, 1.0f);
            this.playing = 125;
        }
        if (this.world.isRemote) {
            if (this.getPassengers() == null) {
                Block bid = this.world.getBlock((int)this.posX, (int)((float)this.posY - (float)gh + 1.0f), (int)this.posZ);
                if (bid == Blocks.AIR) {
                    bid = this.world.getBlock((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ);
                }
                if (bid != Blocks.AIR && bid != Blocks.WATER && bid != Blocks.FLOWING_WATER && bid != Blocks.LAVA && bid != Blocks.FLOWING_LAVA) {
                    this.motionY += 0.12;
                    this.posY += 0.12;
                    this.boatY += 0.12;
                } else {
                    this.motionY -= 0.002;
                }
            }
            if (this.boatPosRotationIncrements > 0) {
                double d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
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
                double d4 = this.posX + this.motionX;
                double d5 = this.posY + this.motionY;
                double d11 = this.posZ + this.motionZ;
                this.setPosition(d4, d5, d11);
                this.motionX *= 0.99;
                this.motionY *= 0.95;
                this.motionZ *= 0.99;
            }
            this.updateLegs();
        } else {
            Block bid;
            if (this.getPassengers() != null) {
                gh = 2.25;
                bid = this.world.getBlock((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ);
                if (bid != Blocks.AIR && bid != Blocks.WATER && bid != Blocks.FLOWING_WATER && bid != Blocks.LAVA && bid != Blocks.FLOWING_LAVA) {
                    this.motionY += 0.06;
                    this.posY += 0.03;
                } else {
                    this.motionY -= 0.02;
                }
            } else {
                bid = this.world.getBlock((int)this.posX, (int)((float)this.posY - (float)gh + 1.0f), (int)this.posZ);
                if (bid == Blocks.AIR) {
                    bid = this.world.getBlock((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ);
                }
                if (bid != Blocks.AIR && bid != Blocks.WATER && bid != Blocks.FLOWING_WATER && bid != Blocks.LAVA && bid != Blocks.FLOWING_LAVA) {
                    this.motionY += 0.15;
                    this.posY += 0.15;
                    this.boatY += 0.15;
                } else {
                    this.motionY -= 0.002;
                }
            }
            if (this.getPassengers() != null) {
                net.minecraft.entity.player.EntityPlayer pp = (net.minecraft.entity.player.EntityPlayer)this.getPassengers();
                obstruction_factor = 0.0;
                dist = 3;
                dist += (int)(velocity * 6.0);
                for (int k = 1; k < dist; ++k) {
                    for (int i = 1; i < dist * 2; ++i) {
                        for (int j = -90; j <= 90; j += 30) {
                            double dz;
                            double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f + (float)j));
                            bid = this.world.getBlock((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f + (float)j)))));
                            if (bid == Blocks.AIR || bid == Blocks.WATER || bid == Blocks.FLOWING_WATER || bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) continue;
                            obstruction_factor += 0.02;
                        }
                    }
                }
                this.motionY += obstruction_factor * 0.05;
                this.posY += obstruction_factor * 0.05;
                double d4 = this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw;
                d4 %= 360.0;
                while (d4 < 0.0) {
                    d4 += 360.0;
                }
                double d5 = this.rotationYaw;
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
                this.rotationPitch = 0.0f;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                double rr = Math.atan2(this.getPassengers().isEmpty() ? 0 : this.getPassengers().get(0).motionZ, this.getPassengers().isEmpty() ? 0 : this.getPassengers().get(0).motionX);
                double rhm = Math.atan2(this.motionZ, this.motionX);
                double rhdir = Math.toRadians((this.getPassengers().isEmpty() ? 0 : ((net.minecraft.entity.Entity)this.getPassengers().get(0)).rotationYaw + 90.0f) % 360.0f);
                double rt = 0.0;
                double pi = 3.1415926545;
                double deltav = 0.0;
                float im = pp.moveForward;
                double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
                if (rdv > pi) {
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
                        deltav = 0.05;
                    } else {
                        max_speed = 0.25;
                        deltav = -0.05;
                    }
                    newvelocity += deltav;
                    if (newvelocity >= 0.0) {
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
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.98;
                this.motionY *= 0.98;
                this.motionZ *= 0.98;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8;
            this.motionY *= 0.98;
            this.motionZ *= 0.8;
            if (this.getPassengers() != null && this.getPassengers().isDead()) {
                this.getPassengers() = null;
            }
        }
    }

    public void goThisWay(double mx, double mz) {
        this.motionX = mx;
        this.motionZ = mz;
    }

    public boolean isAIEnabled() {
        return this.getPassengers() == null;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("AntRobotOwned", this.owned);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.owned = par1NBTTagCompound.getInteger("AntRobotOwned");
    }

    public float getShadowSize() {
        return 0.95f;
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1net.minecraft.entity.player.EntityPlayer) {
        ItemStack var2 = par1net.minecraft.entity.player.EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1net.minecraft.entity.player.EntityPlayer.inventory.setInventorySlotContents(par1net.minecraft.entity.player.EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (this.owned == 0) {
            return true;
        }
        if (var2 != null && var2.getItem() == Items.IRON_INGOT && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
            if (!this.world.isRemote) {
                float f = this.getMaxHealth() - this.getHealth();
                if (f > 100.0f) {
                    f = 100.0f;
                }
                if (f > 0.0f) {
                    this.heal(f);
                }
            }
            if (!par1net.minecraft.entity.player.EntityPlayer.capabilities.isCreativeMode) {
                --var2.stackSize;
                if (var2.stackSize <= 0) {
                    par1net.minecraft.entity.player.EntityPlayer.inventory.setInventorySlotContents(par1net.minecraft.entity.player.EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.getPassengers() != null && this.getPassengers() instanceof net.minecraft.entity.player.EntityPlayer && this.getPassengers() != par1net.minecraft.entity.player.EntityPlayer) {
            return true;
        }
        if (!this.world.isRemote && this.getPassengers() == null && par1net.minecraft.entity.player.EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            par1net.minecraft.entity.player.EntityPlayer.startRiding((Entity)this);
            this.world.playSoundAtEntity((Entity)this, "orespawn:robotspidermount", 0.45f, 1.0f);
        }
        return true;
    }

    private void feetFindSomethingToHit() {
        if (OreSpawnMain.PlayNicely != 0) {
            return;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(10.0, 8.0, 10.0));
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (!this.feetisSuitableTarget(var4, false)) continue;
            this.feetattackEntityAsMob((Entity)var4);
        }
    }

    private boolean feetisSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (par1EntityLiving instanceof AntRobot) {
            return false;
        }
        if (par1EntityLiving == this.getPassengers()) {
            return false;
        }
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        float d1 = (float)(par1EntityLiving.posX - this.posX);
        float d2 = (float)(par1EntityLiving.posY - this.posY);
        float d3 = (float)(par1EntityLiving.posZ - this.posZ);
        float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
        if (dd > 9.0f) {
            return false;
        }
        if (dd < 6.0f) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.capabilities.isCreativeMode;
        }
        return true;
    }

    public boolean feetattackEntityAsMob(Entity par1Entity) {
        boolean ret = false;
        if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
            double ks = 0.6;
            double inair = 0.1;
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)OreSpawnMain.AntRobot_stats.attack / 10.0f);
            if (par1Entity.isDead() || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                inair *= 2.0;
            }
            if (ret) {
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }
        return ret;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack(float distmul, boolean dircheck) {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.boundingBox.expand(12.0 * (double)distmul, 12.0, 12.0 * (double)distmul));
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, dircheck)) continue;
            return var4;
        }
        return null;
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (par1EntityLiving instanceof AntRobot) {
            return false;
        }
        if (par1EntityLiving == this.getPassengers()) {
            return false;
        }
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par2) {
            double rr = Math.atan2(par1EntityLiving.posZ - this.posZ, par1EntityLiving.posX - this.posX);
            double rhdir = Math.toRadians((this.rotationYaw + 90.0f) % 360.0f);
            double pi = 3.1415926545;
            double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
            if (rdd > pi) {
                rdd -= pi * 2.0;
            }
            rdd = Math.abs(rdd);
            if (this.getDistanceSq((Entity)par1EntityLiving) < 36.0) {
                return true;
            }
            if (rdd > 0.75) {
                return false;
            }
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.capabilities.isCreativeMode;
        }
        return true;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean ret = false;
        if (par1Entity != null && par1Entity instanceof net.minecraft.entity.EntityLivingBase) {
            double ks = 0.7;
            double inair = 0.1;
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), (float)OreSpawnMain.AntRobot_stats.attack);
            if (par1Entity.isDead() || par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
                inair *= 2.0;
            }
            if (ret) {
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }
        return ret;
    }

    public int getAttacking() {
        return this.dataManager.get(20);
    }

    public void setAttacking(int par1) {
        this.dataManager.set(20, (Object)par1);
    }

    protected Item getDropItem() {
        return null;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        ItemStack is = null;
        int i = 7 + this.world.rand.nextInt(7);
        block13: for (int var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(12);
            switch (var3) {
                case 0: {
                    is = this.dropItemRand(Items.REDSTONE, 1);
                    continue block13;
                }
                case 1: {
                    is = this.dropItemRand(Items.REPEATER, 1);
                    continue block13;
                }
                case 2: {
                    is = this.dropItemRand(Items.COMPARATOR, 1);
                    continue block13;
                }
                case 3: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.REDSTONE_BLOCK), 1);
                    continue block13;
                }
                case 4: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DISPENSER), 1);
                    continue block13;
                }
                case 5: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.STICKY_PISTON), 1);
                    continue block13;
                }
                case 6: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.PISTON), 1);
                    continue block13;
                }
                case 7: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.LEVER), 1);
                    continue block13;
                }
                case 8: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.REDSTONE_BLOCK), 1);
                    continue block13;
                }
                case 9: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), 1);
                    continue block13;
                }
                case 10: {
                    is = this.dropItemRand(Items.IRON_INGOT, 1);
                    continue block13;
                }
            }
        }
    }
}

