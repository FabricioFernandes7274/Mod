package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AttackSquid extends EntityMob {
    
    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(AttackSquid.class, DataSerializers.VARINT);
    
    private EntityLivingBase buddy = null;
    private int wasshot = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public AttackSquid(World worldIn) {
        super(worldIn);
        this.setSize(1.0f, 1.25f);
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 15;
        this.isImmuneToFire = false;
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 1.0));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.AttackSquid_stats.attack);
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

    public void setWasShot() {
        this.wasshot = 250;
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.AttackSquid_stats.health;
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.AttackSquid_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public int getAttackStrength(Entity par1Entity) {
        return 2;
    }

    @Override
    protected net.minecraft.util.SoundEvent getAmbientSound() { 
        return SoundEvents.ENTITY_GENERIC_EXPLODE; 
    }

    @Override
    protected net.minecraft.util.SoundEvent getHurtSound(DamageSource damageSourceIn) { 
        return SoundEvents.ENTITY_GENERIC_HURT; 
    }

    @Override
    protected net.minecraft.util.SoundEvent getDeathSound() { 
        return SoundEvents.ENTITY_GENERIC_DEATH; 
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = EntityList.createEntityByIDFromName(new ResourceLocation("orespawn", par1), par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
        }
        return var8;
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
        return Items.FISH;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        ItemStack is = new ItemStack(index, par1, 0);
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), is);
        this.world.spawnEntity(var3);
        return is;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        ItemStack is = null;
        int var4 = this.world.rand.nextInt(50);
        switch (var4) {
            case 0: is = this.dropItemRand(Items.GOLD_NUGGET, 1); break;
            case 1: is = this.dropItemRand(Items.GOLD_INGOT, 1); break;
            case 2: is = this.dropItemRand(Items.GOLDEN_CARROT, 1); break;
            case 3: {
                is = this.dropItemRand(Items.GOLDEN_SWORD, 1);
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 4: {
                is = this.dropItemRand(Items.GOLDEN_SHOVEL, 1);
                if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 5: {
                is = this.dropItemRand(Items.GOLDEN_PICKAXE, 1);
                if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 6: {
                is = this.dropItemRand(Items.GOLDEN_AXE, 1);
                if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                break;
            }
            case 7: {
                is = this.dropItemRand(Items.GOLDEN_HOE, 1);
                if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                break;
            }
            case 8: {
                is = this.dropItemRand(Items.GOLDEN_HELMET, 1);
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
                is = this.dropItemRand(Items.GOLDEN_CHESTPLATE, 1);
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                break;
            }
            case 10: {
                is = this.dropItemRand(Items.GOLDEN_LEGGINGS, 1);
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                break;
            }
            case 11: {
                is = this.dropItemRand(Items.GOLDEN_BOOTS, 1);
                if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
                if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                break;
            }
            case 12: this.dropItemRand(Items.GOLDEN_APPLE, 1); break;
            case 13: this.dropItemRand(Item.getItemFromBlock(Blocks.GOLD_BLOCK), 1); break;
            case 14: {
                is = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
                EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(3) - (double)OreSpawnMain.OreSpawnRand.nextInt(3), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(3) - (double)OreSpawnMain.OreSpawnRand.nextInt(3), is);
                this.world.spawnEntity(var3);
                break;
            }
            case 15: 
            case 16: 
            case 17: this.dropItemRand(Items.DYE, 1); break;
        }
        
        int i = 1 + this.world.rand.nextInt(3);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.FISH, 1);
        }
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        if (this.wasshot != 0) {
            return;
        }
        super.fall(distance, damageMultiplier);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (this.isDead) {
            return false;
        }
        Entity e = par1DamageSource.getTrueSource();
        if (e != null && (e instanceof AttackSquid || e instanceof WaterBall || e instanceof WaterDragon)) {
            return false;
        }
        if (e != null && e instanceof EntityLivingBase) {
            this.setAttackTarget((EntityLivingBase)e);
            this.getNavigator().tryMoveToEntityLiving(e, 1.2);
            ret = true;
        }
        
        ret = super.attackEntityFrom(par1DamageSource, par2);
        
        if ((this.getHealth() <= 0.0f || this.isDead) && this.world.provider.getDimension() != OreSpawnMain.DimensionID5 && !this.world.isRemote && e != null && e instanceof EntityPlayer && this.world.rand.nextInt(15) == 1 && OreSpawnMain.KrakenEnable != 0 && this.wasshot == 0) {
            int j = 1 + this.world.rand.nextInt(3);
            for (int i = 0; i < j; ++i) {
                AttackSquid.spawnCreature(this.world, "the_kraken", this.posX + (double)this.world.rand.nextInt(4) - (double)this.world.rand.nextInt(4), 170.0, this.posZ + (double)this.world.rand.nextInt(4) - (double)this.world.rand.nextInt(4));
            }
        }
        return ret;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int d;
        Block bid;
        int j;
        int i;
        int found = 0;
        for (i = -dy; i <= dy; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dx * dx + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x - dx;
                this.ty = y + i;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new BlockPos(x + i, y + dy, z + j)).getBlock();
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dy * dy + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y - dy;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dy; j <= dy; ++j) {
                bid = this.world.getBlockState(new BlockPos(x + i, y + j, z + dz)).getBlock();
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        return found != 0;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.wasshot > 0) {
            --this.wasshot;
            if (this.wasshot == 0) {
                this.setDead();
                return;
            }
        }
        if (!this.isInWater() && this.world.rand.nextInt(10) == 0) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 1; i < 12; ++i) {
                int j = i;
                if (j > 5) j = 5;
                if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) break;
                if (i < 5) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)(this.ty - 1), (double)this.tz, 1.33);
            } else {
                if (this.world.rand.nextInt(25) == 1) {
                    this.heal(-1.0f);
                }
                if (this.getHealth() <= 0.0f) {
                    this.setDead();
                    return;
                }
            }
        }
        if (this.world.rand.nextInt(10) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                if (this.getDistanceSq(e) < 9.0) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                        this.attackEntityAsMob(e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving(e, 1.2);
                    this.watercanon(e);
                }
            } else {
                if (this.buddy != null) {
                    this.getNavigator().tryMoveToEntityLiving(this.buddy, 1.0);
                }
                this.setAttacking(0);
            }
        }
    }

    private void watercanon(EntityLivingBase e) {
        double yoff = 1.0;
        double xzoff = 1.2;
        if (this.world.rand.nextInt(5) == 1) {
            if (this.world.rand.nextInt(3) == 1) {
                InkSack var2 = new InkSack(this.world, e.posX - this.posX, e.posY + 0.75 - (this.posY + yoff), e.posZ - this.posZ);
                var2.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
                double var3 = e.posX - this.posX;
                double var5 = e.posY + 0.25 - var2.posY;
                double var7 = e.posZ - this.posZ;
                float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2f;
                var2.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity(var2);
            } else {
                WaterBall var2 = new WaterBall(this.world, e.posX - this.posX, e.posY + 0.75 - (this.posY + yoff), e.posZ - this.posZ);
                var2.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
                double var3 = e.posX - this.posX;
                double var5 = e.posY + 0.25 - var2.posY;
                double var7 = e.posZ - this.posZ;
                float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2f;
                var2.setThrowableHeading(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                this.world.spawnEntity(var2);
            }
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null || par1EntityLiving == this || !par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (!this.getEntitySenses().canSee(par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            return !p.isCreative() && !p.isSpectator();
        }
        if (par1EntityLiving instanceof Girlfriend || par1EntityLiving instanceof Boyfriend || par1EntityLiving instanceof EntityZombie || par1EntityLiving instanceof EntityVillager || par1EntityLiving instanceof EntitySpider || par1EntityLiving instanceof EntityCaveSpider || par1EntityLiving instanceof Lizard) {
            return true;
        }
        if (par1EntityLiving instanceof Ghost || par1EntityLiving instanceof GhostSkelly) {
            return false;
        }
        if (par1EntityLiving instanceof AttackSquid) {
            if (this.world.rand.nextInt(5) == 1) {
                this.buddy = par1EntityLiving;
            }
            return false;
        }
        return this.wasshot != 0;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List<EntityLivingBase> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0, 4.0, 10.0));
        EntityLivingBase e = this.getAttackTarget();
        if (e != null && e.isEntityAlive()) {
            return e;
        }
        this.setAttackTarget(null);
        for (EntityLivingBase var4 : var5) {
            if (this.isSuitableTarget(var4, false)) {
                return var4;
            }
        }
        return null;
    }

    public final int getAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public final void setAttacking(int par1) {
        this.dataManager.set(ATTACKING, par1);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("WasShot", this.wasshot);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.wasshot = par1NBTTagCompound.getInteger("WasShot");
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        return this.world.isDaytime() && super.getCanSpawnHere();
    }
}