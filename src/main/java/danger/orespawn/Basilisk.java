package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Basilisk extends EntityMob {

    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(Basilisk.class, DataSerializers.VARINT);
    
    private int hurt_timer = 0;

    public Basilisk(World worldIn) {
        super(worldIn);
        this.setSize(1.6f, 3.5f);
        this.experienceValue = 150;
        this.isImmuneToFire = true;
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(2, new MyEntityAIWanderALot(this, 20, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Basilisk_stats.attack);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, 0);
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Basilisk_stats.health;
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.Basilisk_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void jump() {
        this.motionY += 0.25;
        super.jump();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isDead) {
            return;
        }
        if (this.world.rand.nextInt(200) == 0) {
            this.heal(1.0f);
        }
    }

    public int getBasiliskHealth() {
        return (int)this.getHealth();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // Substitua pelo som correto do Orespawn posteriormente
        return SoundEvents.ENTITY_GENERIC_EXPLODE; 
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { 
        return SoundEvents.ENTITY_GENERIC_HURT; 
    }

    @Override
    protected SoundEvent getDeathSound() { 
        return SoundEvents.ENTITY_GENERIC_DEATH; 
    }

    @Override
    protected float getSoundVolume() {
        return 1.0f;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.BEEF;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        if (index == null) return ItemStack.EMPTY;
        ItemStack is = new ItemStack(index, par1, 0);
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), is);
        this.world.spawnEntity(var3);
        return is;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(OreSpawnMain.MyBasiliskScale, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int i = 12 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.EMERALD, 1);
        }
        i = 8 + this.world.rand.nextInt(5);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.CHICKEN, 1);
        }
        i = 3 + this.world.rand.nextInt(5);
        for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(15);
            switch (var3) {
                case 1: {
                    is = this.dropItemRand(Items.EMERALD, 1);
                    break;
                }
                case 2: {
                    is = this.dropItemRand(Item.getItemFromBlock(Blocks.EMERALD_BLOCK), 1);
                    break;
                }
                case 3: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldSword, 1);
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
                    break;
                }
                case 4: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldShovel, 1);
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    break;
                }
                case 5: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldPickaxe, 1);
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
                    break;
                }
                case 6: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldAxe, 1);
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    break;
                }
                case 7: {
                    is = this.dropItemRand(OreSpawnMain.MyEmeraldHoe, 1);
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    break;
                }
                case 8: {
                    is = this.dropItemRand(OreSpawnMain.EmeraldHelmet, 1);
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
                    break;
                }
                case 9: {
                    is = this.dropItemRand(OreSpawnMain.EmeraldBody, 1);
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    break;
                }
                case 10: {
                    is = this.dropItemRand(OreSpawnMain.EmeraldLegs, 1);
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    break;
                }
                case 11: {
                    is = this.dropItemRand(OreSpawnMain.EmeraldBoots, 1);
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    break;
                }
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
                int var2 = 8;
                if (this.world.getDifficulty() == EnumDifficulty.EASY) {
                    var2 = 10;
                } else if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                    var2 = 12;
                } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                    var2 = 14;
                }
                
                if (this.world.rand.nextInt(3) == 0) {
                    ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, var2 * 20, 0));
                }
                
                double ks = 1.5;
                double inair = 0.15;
                float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
                if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                    inair *= 2.0;
                }
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.hurt_timer > 0) {
            return false;
        }
        this.hurt_timer = 30;
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.world.rand.nextInt(5) == 0) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.faceEntity(e, 10.0f, 10.0f);
                if (this.getDistanceSq(e) < (double)((6.0f + e.width / 2.0f) * (6.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(3) == 0 || this.world.rand.nextInt(4) == 1) {
                        this.attackEntityAsMob(e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving(e, 1.25);
                }
                if (e != null) {
                    e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 5));
                }
            } else {
                this.setAttacking(0);
            }
        }
        if (this.world.rand.nextInt(75) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(1.0f);
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null || par1EntityLiving == this || !par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee(par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof Basilisk || par1EntityLiving instanceof LeafMonster) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.isCreative() || p.isSpectator()) {
                return false;
            }
        }
        return true;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List<EntityLivingBase> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(24.0, 7.0, 24.0));
        for (EntityLivingBase var4 : var5) {
            if (this.isSuitableTarget(var4, false)) {
                return var4;
            }
        }
        return null;
    }

    public int getAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public void setAttacking(int par1) {
        this.dataManager.set(ATTACKING, par1);
    }

    @Override
    public boolean getCanSpawnHere() {
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    BlockPos pos = new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    Block bid = this.world.getBlockState(pos).getBlock();
                    
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    
                    TileEntity tileentity = this.world.getTileEntity(pos);
                    if (tileentity instanceof TileEntityMobSpawner) {
                        TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileentity;
                        ResourceLocation res = spawner.getSpawnerBaseLogic().getEntityId();
                        if (res != null && res.getResourcePath().toLowerCase().contains("basilisk")) {
                            return true;
                        }
                    }
                }
            }
        }
        
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.isDaytime()) {
            return false;
        }
        
        for (int k = -1; k < 2; ++k) {
            for (int j = -1; j < 2; ++j) {
                for (int i = 1; i < 5; ++i) {
                    Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid != Blocks.AIR) {
                        return false;
                    }
                }
            }
        }
        
        List<Basilisk> target = this.world.getEntitiesWithinAABB(Basilisk.class, this.getEntityBoundingBox().grow(20.0, 6.0, 20.0));
        return target.isEmpty();
    }
}