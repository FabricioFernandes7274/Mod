/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.world.EnumDifficulty;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityButterfly
extends EntityAmbientCreature {
    private static final ResourceLocation texture1 = new net.minecraft.util.ResourceLocation("orespawn", "butterfly.png");
    private static final ResourceLocation texture2 = new net.minecraft.util.ResourceLocation("orespawn", "butterfly2.png");
    private static final ResourceLocation texture3 = new net.minecraft.util.ResourceLocation("orespawn", "butterfly3.png");
    private static final ResourceLocation texture4 = new net.minecraft.util.ResourceLocation("orespawn", "butterfly4.png");
    private static final ResourceLocation texture5 = new net.minecraft.util.ResourceLocation("orespawn", "eyemoth.png");
    private static final ResourceLocation texture6 = new net.minecraft.util.ResourceLocation("orespawn", "lunamoth.png");
    private static final ResourceLocation texture7 = new net.minecraft.util.ResourceLocation("orespawn", "darkmoth.png");
    private static final ResourceLocation texture8 = new net.minecraft.util.ResourceLocation("orespawn", "firemoth.png");
    private static final ResourceLocation texture9 = new net.minecraft.util.ResourceLocation("orespawn", "vbutterfly1.png");
    public int butterfly_type = OreSpawnMain.OreSpawnRand.nextInt(4);
    private int attack_delay = 0;
    private GenericTargetSorter TargetSorter = null;
    private int force_sync = 25;
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;

    public EntityButterfly(World worldIn) {
        super(worldIn);
        this.setSize(0.4f, 0.4f);
        this.getNavigator().setCanSwim(true);
        this.TargetSorter = new GenericTargetSorter((Entity)this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.1f);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    public ResourceLocation getTexture(EntityButterfly a) {
        if (a instanceof Mothra) {
            return texture5;
        }
        if (a instanceof EntityLunaMoth) {
            if (((EntityLunaMoth)a).moth_type == 1) {
                return texture5;
            }
            if (((EntityLunaMoth)a).moth_type == 2) {
                return texture7;
            }
            if (((EntityLunaMoth)a).moth_type == 3) {
                return texture8;
            }
            return texture6;
        }
        if (this.butterfly_type == 1) {
            if (this.getEntityWorld().provider.getDimension() == OreSpawnMain.DimensionID4) {
                return texture9;
            }
            return texture2;
        }
        if (this.butterfly_type == 2) {
            return texture3;
        }
        if (this.butterfly_type == 3) {
            return texture4;
        }
        return texture1;
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)this.butterfly_type);
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    protected float getSoundVolume() {
        return 0.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    public int mygetMaxHealth() {
        return 2;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    protected void updateAITasks() {
        int keep_trying = 25;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.rand.nextInt(100) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
                bid = this.getEntityWorld().getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                --keep_trying;
            }
        } else if (this.rand.nextInt(10) == 0 && this.getEntityWorld().provider.getDimension() == OreSpawnMain.DimensionID4 && this.butterfly_type == 1 && this.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL) {
            net.minecraft.entity.EntityLivingBase e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                if (this.getDistanceSq((Entity)e) < 6.0) {
                    this.attackEntityAsMob((Entity)e);
                }
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * (double)0.1f;
        this.motionY += (Math.signum(var3) * (double)0.7f - this.motionY) * (double)0.1f;
        this.motionZ += (Math.signum(var5) * 0.5 - this.motionZ) * (double)0.1f;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.5f;
        this.rotationYaw += var8;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (OreSpawnMain.OreSpawnRand.nextInt(2) != 0) {
            return false;
        }
        if (this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), 1.0f);
        return var4;
    }

    private boolean isSuitableTarget(net.minecraft.entity.EntityLivingBase par1EntityLiving, boolean par2) {
        if (this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            return !p.isCreative();
        }
        return par1EntityLiving instanceof EntityHorse;
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(8.0, 5.0, 8.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        net.minecraft.entity.EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (net.minecraft.entity.EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false)) continue;
            return var4;
        }
        return null;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= (double)0.6f;
        --this.force_sync;
        if (this.force_sync < 0) {
            this.force_sync = 25;
            if (this.getEntityWorld().isRemote) {
                this.butterfly_type = 0 /* this.dataManager.get(20) */;
            } else {
//                 this.dataManager.set(20, (Object)this.butterfly_type);
            }
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        if (par1EntityPlayer == null) {
            return false;
        }
        if (!(par1EntityPlayer instanceof net.minecraft.entity.player.EntityPlayerMP)) {
            return false;
        }
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getCount() <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (var2 != null) {
            return false;
        }
        if (par1EntityPlayer.dimension != OreSpawnMain.DimensionID6) {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, OreSpawnMain.DimensionID6, (Teleporter)new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawnMain.DimensionID6), OreSpawnMain.DimensionID6, this.getEntityWorld()));
        } else {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, 0, (Teleporter)new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0), 0, this.getEntityWorld()));
        }
        return true;
    }

    public boolean getCanSpawnHere() {
        Block bid;
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.getEntityWorld().getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Butterfly")) continue;
                    this.butterfly_type = 1;
                    return true;
                }
            }
        }
        bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock(;
        if (bid != Blocks.AIR) {
            return false;
        }
        if (!this.getEntityWorld().isDaytime()) {
            return false;
        }
        if (this.getEntityWorld().provider.getDimension() == OreSpawnMain.DimensionID4) {
            return true;
        }
        return !(this.posY < 50.0);
    }

    public void initCreature() {
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("ButterflyType", this.butterfly_type);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.butterfly_type = par1NBTTagCompound.getInteger("ButterflyType");
    }
}

