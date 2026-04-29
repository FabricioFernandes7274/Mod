/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.effect.EntityLightningBolt
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityChicken
 *  net.minecraft.entity.passive.EntitySquid
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.net.minecraft.util.math.BlockPos
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraft.world.storage.net.minecraft.world.storage.WorldInfo
 */
package danger.orespawn;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Kraken
extends EntityMob {
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private net.minecraft.util.math.BlockPos currentFlightTarget = null;
    private net.minecraft.entity.EntityLivingBase caught = null;
    private int newtarget = 0;
    private int release = 0;
    private int weather_set = 10;
    private int long_enough = 3600;
    private int call_reinforcements = 0;
    private boolean hit_by_player = false;
    private int straight_down = 1;
    private int hurt_timer = 0;

    public Kraken(World worldIn) {
        super(worldIn);
        if (OreSpawnMain.PlayNicely == 0) {
            this.setSize(4.0f, 15.0f);
        } else {
            this.setSize(1.3333334f, 5.0f);
        }
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 500;
        //this.fireResistance = 120;
        this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(1, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.37f);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Kraken_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
//         this.dataManager.register(20, (Object)0);
//         this.dataManager.register(21, (Object)OreSpawnMain.PlayNicely);
        if (this.renderdata == null) {
            this.renderdata = new RenderInfo();
        }
        this.renderdata.rf1 = 0.0f;
        this.renderdata.rf2 = 0.0f;
        this.renderdata.rf3 = 0.0f;
        this.renderdata.rf4 = 0.0f;
        this.renderdata.ri1 = 0;
        this.renderdata.ri2 = 0;
        this.renderdata.ri3 = 0;
        this.renderdata.ri4 = 0;
    }

    public int getPlayNicely() {
        return 0 /* this.dataManager.get(21) */;
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Kraken_stats.health;
    }

    public int getKrakenHealth() {
        return (int)this.getHealth();
    }

    public RenderInfo getRenderInfo() {
        return this.renderdata;
    }

    public void setRenderInfo(RenderInfo r) {
        this.renderdata.rf1 = r.rf1;
        this.renderdata.rf2 = r.rf2;
        this.renderdata.rf3 = r.rf3;
        this.renderdata.rf4 = r.rf4;
        this.renderdata.ri1 = r.ri1;
        this.renderdata.ri2 = r.ri2;
        this.renderdata.ri3 = r.ri3;
        this.renderdata.ri4 = r.ri4;
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Kraken_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.isDead()) {
            return;
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)(this.posY - 10.0), (int)this.posZ);
        } else {
            this.motionY = this.posY < (double)this.currentFlightTarget.getY() ? (this.motionY *= 0.72) : (this.motionY *= 0.5);
        }
        if (this.weather_set > 0 && OreSpawnMain.PlayNicely == 0) {
            --this.weather_set;
            if (this.weather_set == 0 && !this.getEntityWorld().isRemote) {
                net.minecraft.world.storage.WorldInfo worldinfo = this.getEntityWorld().getWorldInfo();
                if (!this.getEntityWorld().isRaining()) {
                    worldinfo.setRainTime(300);
                    worldinfo.setThunderTime(300);
                    worldinfo.setRaining(true);
                    worldinfo.setThundering(true);
                } else {
                    worldinfo.setRainTime(300);
                    worldinfo.setThunderTime(300);
                }
                this.weather_set = 100;
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("LongEnough", this.long_enough);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.long_enough = par1NBTTagCompound.getInteger("LongEnough");
    }

    protected String getLivingSound() {
        if (this.getEntityWorld().rand.nextInt(5) == 0) {
            return "orespawn:kraken_living";
        }
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    protected float getSoundVolume() {
        return 2.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.QUARTZ;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(8) - (double)OreSpawnMain.OreSpawnRand.nextInt(8), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(8) - (double)OreSpawnMain.OreSpawnRand.nextInt(8), is);
        if (var3 != null) {
            this.getEntityWorld().spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(OreSpawnMain.MyKrakenTooth, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int var5 = 120 + this.getEntityWorld().rand.nextInt(160);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.DYE, 1);
        }
        int i = 5 + this.getEntityWorld().rand.nextInt(10);
        block56: for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.getEntityWorld().rand.nextInt(53);
            switch (var3) {
                case 0: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateSword, 1);
                    continue block56;
                }
                case 1: {
                    is = this.dropItemRand(Items.DIAMOND, 1);
                    continue block56;
                }
                case 2: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIAMOND_BLOCK), 1);
                    continue block56;
                }
                case 3: {
                    is = this.dropItemRand(Items.DIAMOND_SWORD, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.LOOTING, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 4: {
                    is = this.dropItemRand(Items.DIAMOND_SHOVEL, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 5: {
                    is = this.dropItemRand(Items.DIAMOND_PICKAXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 6: {
                    is = this.dropItemRand(Items.DIAMOND_AXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 7: {
                    is = this.dropItemRand(Items.DIAMOND_HOE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 8: {
                    is = this.dropItemRand((Item)Items.DIAMOND_HELMET, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(2));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 9: {
                    is = this.dropItemRand((Item)Items.DIAMOND_CHESTPLATE, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 10: {
                    is = this.dropItemRand((Item)Items.DIAMOND_LEGGINGS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 11: {
                    is = this.dropItemRand((Item)Items.DIAMOND_BOOTS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 12: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateBow, 1);
                    continue block56;
                }
                case 13: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateAxe, 1);
                    continue block56;
                }
                case 14: {
                    is = this.dropItemRand(Items.IRON_INGOT, 1);
                    continue block56;
                }
                case 15: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimatePickaxe, 1);
                    continue block56;
                }
                case 16: {
                    is = this.dropItemRand(Items.IRON_SWORD, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.LOOTING, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 17: {
                    is = this.dropItemRand(Items.IRON_SHOVEL, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 18: {
                    is = this.dropItemRand(Items.IRON_PICKAXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 19: {
                    is = this.dropItemRand(Items.IRON_AXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 20: {
                    is = this.dropItemRand(Items.IRON_HOE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 21: {
                    is = this.dropItemRand((Item)Items.IRON_HELMET, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 22: {
                    is = this.dropItemRand((Item)Items.IRON_CHESTPLATE, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 23: {
                    is = this.dropItemRand((Item)Items.IRON_LEGGINGS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 24: {
                    is = this.dropItemRand((Item)Items.IRON_BOOTS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 25: {
                    is = this.dropItemRand(OreSpawnMain.MyUltimateShovel, 1);
                    continue block56;
                }
                case 26: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.IRON_BLOCK), 1);
                    continue block56;
                }
                case 27: {
                    is = this.dropItemRand(Items.GOLD_NUGGET, 1);
                    continue block56;
                }
                case 28: {
                    is = this.dropItemRand(Items.GOLD_INGOT, 1);
                    continue block56;
                }
                case 29: {
                    is = this.dropItemRand(Items.GOLDEN_CARROT, 1);
                    continue block56;
                }
                case 30: {
                    is = this.dropItemRand(Items.GOLDEN_SWORD, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.LOOTING, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 31: {
                    is = this.dropItemRand(Items.GOLDEN_SHOVEL, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 32: {
                    is = this.dropItemRand(Items.GOLDEN_PICKAXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 33: {
                    is = this.dropItemRand(Items.GOLDEN_AXE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 34: {
                    is = this.dropItemRand(Items.GOLDEN_HOE, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 35: {
                    is = this.dropItemRand((Item)Items.GOLDEN_HELMET, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 36: {
                    is = this.dropItemRand((Item)Items.GOLDEN_CHESTPLATE, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 37: {
                    is = this.dropItemRand((Item)Items.GOLDEN_LEGGINGS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 38: {
                    is = this.dropItemRand((Item)Items.GOLDEN_BOOTS, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 39: {
                    this.dropItemRand(Items.GOLDEN_APPLE, 1);
                    continue block56;
                }
                case 40: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GOLD_BLOCK), 1);
                    continue block56;
                }
                case 41: {
                    EntityItem var33 = null;
                    is = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
                    var33 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(3) - (double)OreSpawnMain.OreSpawnRand.nextInt(3), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(3) - (double)OreSpawnMain.OreSpawnRand.nextInt(3), is);
                    if (var33 == null) continue block56;
                    this.getEntityWorld().spawnEntity((Entity)var33);
                    continue block56;
                }
                case 42: {
                    is = this.dropItemRand(OreSpawnMain.MyExperienceSword, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.LOOTING, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 43: {
                    is = this.dropItemRand((Item)OreSpawnMain.ExperienceHelmet, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.RESPIRATION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 44: {
                    is = this.dropItemRand((Item)OreSpawnMain.ExperienceBody, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 45: {
                    is = this.dropItemRand((Item)OreSpawnMain.ExperienceLegs, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 46: {
                    is = this.dropItemRand((Item)OreSpawnMain.ExperienceBoots, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    continue block56;
                }
                case 47: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystSword, 1);
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.LOOTING, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.SHARPNESS, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 48: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystShovel, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 49: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystPickaxe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.FORTUNE, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 50: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystAxe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 51: {
                    is = this.dropItemRand(OreSpawnMain.MyAmethystHoe, 1);
                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantments.UNBREAKING, 2 + this.getEntityWorld().rand.nextInt(4));
                    }
                    if (this.getEntityWorld().rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.getEntityWorld().rand.nextInt(5));
                    continue block56;
                }
                case 52: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)OreSpawnMain.MyBlockAmethystBlock), 1);
                    continue block56;
                }
            }
        }
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        return false;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        if (this.long_enough <= 0) {
            return true;
        }
        if (this.posY > 150.0 && this.getHealth() < (float)(this.mygetMaxHealth() / 2)) {
            return true;
        }
        if (this.posY > 180.0 && this.long_enough <= 0) {
            this.setDead();
            return true;
        }
        return false;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.getEntityWorld().rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int i;
        Block bid;
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        if (this.isDead()) {
            return;
        }
        super.updateAITasks();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.long_enough > 0) {
            --this.long_enough;
        }
//         this.dataManager.set(21, (Object)OreSpawnMain.PlayNicely);
        if (this.getEntityWorld().rand.nextInt(400) == 1 && OreSpawnMain.PlayNicely == 0) {
            this.getEntityWorld().addWeatherEffect((Entity)new EntityLightningBolt(this.getEntityWorld(), this.posX, this.posY - 16.0, this.posZ));
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.newtarget != 0 || this.getEntityWorld().rand.nextInt(250) == 1 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 9.1f) {
            int ground_dist;
            this.newtarget = 0;
            for (ground_dist = 0; ground_dist < 31; ++ground_dist) {
                bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX, (int)this.posY - ground_dist, (int)this.posZ)).getBlock(;
                if (bid == Blocks.AIR) continue;
                this.straight_down = 0;
                break;
            }
            ground_dist = 20 - ground_dist;
            bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.getEntityWorld().rand.nextInt(6) + 12;
                xdir = this.getEntityWorld().rand.nextInt(6) + 12;
                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                    zdir = -zdir;
                }
                if (this.getEntityWorld().rand.nextInt(2) == 0) {
                    xdir = -xdir;
                }
                if (this.straight_down != 0) {
                    xdir = 0;
                    zdir = 0;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + ground_dist + this.getEntityWorld().rand.nextInt(9) - 6, (int)this.posZ + zdir);
                bid = this.getEntityWorld().getBlockState(new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()).getBlock());
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
            if (this.long_enough <= 0 || this.posY < 200.0 && this.getHealth() < (float)(this.mygetMaxHealth() / 4)) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY() + 30, this.currentFlightTarget.getZ());
                if (this.hit_by_player && this.call_reinforcements == 0 && this.getHealth() < (float)(this.mygetMaxHealth() / 8) && this.posY > 130.0) {
                    this.call_reinforcements = 1;
                    for (i = 0; i < 10; ++i) {
                        EntityCreature newent = (EntityCreature)Kraken.spawnCreature(this.getEntityWorld(), "The Kraken", this.posX + (double)this.getEntityWorld().rand.nextInt(10) - (double)this.getEntityWorld().rand.nextInt(10), 170.0, this.posZ + (double)this.getEntityWorld().rand.nextInt(10) - (double)this.getEntityWorld().rand.nextInt(10));
                    }
                }
            }
        } else if (this.caught == null && this.getEntityWorld().rand.nextInt(8) == 1 && OreSpawnMain.PlayNicely == 0) {
            net.minecraft.entity.player.EntityPlayer target = null;
            target = (net.minecraft.entity.player.EntityPlayer)this.getEntityWorld().findNearestEntityWithinAABB(net.minecraft.entity.player.EntityPlayer.class, this.getEntityBoundingBox().expand(25.0, 40.0, 25.0), (Entity)this);
            if (target != null) {
                if (!target.isCreative()) {
                    if (this.getEntitySenses().canSee((Entity)target)) {
                        this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)target.posX, (int)target.posY + 15, (int)target.posZ);
                        this.attackWithSomething((net.minecraft.entity.EntityLivingBase)target);
                    }
                } else {
                    target = null;
                }
            }
            if (target == null && this.getEntityWorld().rand.nextInt(2) == 0) {
                net.minecraft.entity.EntityLivingBase e = null;
                e = this.findSomethingToAttack();
                if (e != null) {
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)e.posY + 15, (int)e.posZ);
                    this.attackWithSomething(e);
                }
            }
        }
        if (this.caught != null) {
            if (!this.caught.isDead()) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, 200, (int)this.posZ);
                if (this.posY > 190.0) {
                    this.release = 1;
                }
                this.caught.motionX = this.motionX;
                this.caught.motionZ = this.motionZ;
                this.caught.motionY = this.motionY;
                this.caught.posX = this.posX;
                if (this.posY - this.caught.posY > 16.0) {
                    this.caught.motionY += 0.25;
                }
                this.caught.posY = this.posY - 15.0;
                this.caught.posZ = this.posZ;
                this.caught.rotationYaw = this.rotationYaw;
                if (this.getEntityWorld().rand.nextInt(50) == 1) {
                    this.attackEntityAsMob((Entity)this.caught);
                }
                if (this.release != 0 || this.getEntityWorld().rand.nextInt(250) == 1) {
                    this.caught = null;
                    this.newtarget = 1;
                    this.release = 0;
                    this.setAttacking(0);
                }
            } else {
                this.caught = null;
                this.newtarget = 1;
                this.release = 0;
                this.setAttacking(0);
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.3 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.3 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.45 - this.motionX) * 0.15;
        this.motionY += (Math.signum(var3) * 0.70999 - this.motionY) * 0.202;
        this.motionZ += (Math.signum(var5) * 0.45 - this.motionZ) * 0.15;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = net.minecraft.util.math.MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.4f;
        if (Math.abs(this.motionX) + Math.abs(this.motionZ) < 0.15) {
            var8 = 0.0f;
        }
        this.rotationYaw += var8 / 5.0f;
        double obstruction_factor = 0.0;
        double dx = 0.0;
        double dz = 0.0;
        int dist = 10;
        for (int k = -20; k < 18; k += 2) {
            for (i = 1; i < dist; i += 2) {
                dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                bid = this.getEntityWorld().getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY + k, (int)(this.posZ + (dz = (double)).getBlock(i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))));
                if (bid == Blocks.AIR) continue;
                obstruction_factor += 0.1;
            }
        }
        this.motionY += obstruction_factor * 0.08;
        this.posY += obstruction_factor * 0.08;
        if (this.posY > 256.0 && !this.isNoDespawnRequired()) {
            this.setDead();
        }
    }

    private void attackWithSomething(net.minecraft.entity.EntityLivingBase par1) {
        if (this.caught != null) {
            return;
        }
        double dist = (this.posX - par1.posX) * (this.posX - par1.posX);
        dist += (this.posZ - par1.posZ) * (this.posZ - par1.posZ);
        if ((dist += (this.posY - par1.posY - 15.0) * (this.posY - par1.posY - 15.0)) < 30.0) {
            this.caught = par1;
            this.release = 0;
            this.setAttacking(1);
        }
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
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par1EntityLiving;
            if (p.isCreative()) {
                return false;
            }
            return !p.capabilities.isFlying;
        }
        if (!par1EntityLiving.onGround && !par1EntityLiving.isInWater()) {
            return false;
        }
        if (par1EntityLiving instanceof EntitySquid) {
            return false;
        }
        if (par1EntityLiving instanceof AttackSquid) {
            return false;
        }
        if (par1EntityLiving instanceof Kraken) {
            return false;
        }
        if (par1EntityLiving instanceof Spyro) {
            return false;
        }
        if (par1EntityLiving instanceof Dragon) {
            Dragon c = (Dragon)par1EntityLiving;
            return c.getPassengers() == null;
        }
        if (par1EntityLiving instanceof Cephadrome) {
            Cephadrome c = (Cephadrome)par1EntityLiving;
            return c.getPassengers() == null;
        }
        if (par1EntityLiving instanceof Leon) {
            Leon c = (Leon)par1EntityLiving;
            return c.getPassengers() == null;
        }
        if (par1EntityLiving instanceof ThePrinceTeen) {
            ThePrinceTeen c = (ThePrinceTeen)par1EntityLiving;
            return c.getPassengers() == null;
        }
        if (par1EntityLiving instanceof ThePrinceAdult) {
            ThePrinceAdult c = (ThePrinceAdult)par1EntityLiving;
            return c.getPassengers() == null;
        }
        if (par1EntityLiving instanceof EntityChicken) {
            return false;
        }
        if (par1EntityLiving instanceof Chipmunk) {
            return false;
        }
        if (par1EntityLiving instanceof StinkBug) {
            return false;
        }
        return !(par1EntityLiving instanceof Mothra);
    }

    private net.minecraft.entity.EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, this.getEntityBoundingBox().expand(20.0, 40.0, 20.0));
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

    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Entity e = par1DamageSource.getEntity();
        boolean ret = false;
        if (this.currentFlightTarget != null && e != null && e instanceof net.minecraft.entity.player.EntityPlayer && this.getHealth() > (float)(this.mygetMaxHealth() / 4)) {
            this.hit_by_player = true;
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)e.posY + 15, (int)e.posZ);
        }
        if (this.hurt_timer > 0) {
            return false;
        }
        this.hurt_timer = 30;
        ret = super.attackEntityFrom(par1DamageSource, par2);
        if (this.getEntityWorld().rand.nextInt(2) == 1) {
            this.release = 1;
        }
        return ret;
    }

    public final int getAttacking() {
        return 0 /* this.dataManager.get(20) */;
    }

    public final void setAttacking(int par1) {
//         this.dataManager.set(20, (Object)((byte)par1));
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        for (int k = -1; k < 2; ++k) {
            for (int j = -1; j < 1; ++j) {
                for (int i = 1; i < 6; ++i) {
                    Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock(;
                    if (bid == Blocks.AIR || bid == Blocks.TALLGRASS) continue;
                    return false;
                }
            }
        }
        return true;
    }
}

