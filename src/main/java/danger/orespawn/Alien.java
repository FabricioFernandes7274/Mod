package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Alien extends EntityMob {
    
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private double moveSpeed = 0.65;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;
    
    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(Alien.class, DataSerializers.VARINT);

    public Alien(World worldIn) {
        super(worldIn);
        this.setSize(1.1f, 3.25f);
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.getNavigator().setBreakDoors(true);
        this.experienceValue = 100;
        this.isImmuneToFire = false;
        this.jumpMovementFactor = 0.6f;
        this.renderdata = new RenderInfo();
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(2, new MyEntityAIWanderALot(this, 10, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Alien_stats.attack);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, 0);
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

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Alien_stats.health;
    }

    @Override
    protected void jump() {
        super.jump();
        this.motionY += 0.25;
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

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.Alien_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.world.isRemote) {
            float f = 1.7f + Math.abs(this.world.rand.nextFloat() * 0.75f);
            if (this.world.rand.nextInt(20) == 1) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + 1.6, this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYawHead)), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    public int getAlienHealth() {
        return (int)this.getHealth();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // Requer integração com SoundEvent da 1.12.2. Retornando nulo para prevenir crash.
        return null;
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
        return Items.SPIDER_EYE;
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), new ItemStack(index, par1, 0));
        this.world.spawnEntity(var3);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int var4;
        int var5 = 5 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.SPIDER_EYE, 1);
        }
        var5 = 5 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.FLINT, 1);
        }
        this.dropItemRand(Items.MAP, 1);
        this.dropItemRand(Items.CLOCK, 1);
        this.dropItemRand(Items.COMPASS, 1);
    }

    public void initCreature() {
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
                int var2 = 6;
                if (this.world.getDifficulty() == EnumDifficulty.EASY) {
                    var2 = 8;
                } else if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                    var2 = 10;
                } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                    var2 = 12;
                }
                
                if (this.world.rand.nextInt(5) == 1) {
                    ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, var2 * 5, 0));
                }
                
                double ks = 1.1;
                double inair = 0.1;
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
        Entity e;
        boolean ret = false;
        if (par1DamageSource.getDamageType().equals("cactus")) {
            return false;
        }
        if (this.hurt_timer <= 0) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
        }
        if ((e = par1DamageSource.getTrueSource()) != null && e instanceof EntityLivingBase) {
            this.setAttackTarget((EntityLivingBase)e);
            this.getNavigator().tryMoveToEntityLiving(e, 1.2);
            ret = true;
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
                if ((bid == Blocks.TORCH || bid == OreSpawnMain.ExtremeTorch) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.TORCH && bid != OreSpawnMain.ExtremeTorch || (d = dx * dx + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.TORCH || bid == OreSpawnMain.ExtremeTorch) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.TORCH && bid != OreSpawnMain.ExtremeTorch || (d = dy * dy + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.TORCH || bid == OreSpawnMain.ExtremeTorch) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.TORCH && bid != OreSpawnMain.ExtremeTorch || (d = dz * dz + j * j + i * i) >= this.closest) continue;
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
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.world.rand.nextInt(8) == 0) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.faceEntity(e, 10.0f, 10.0f);
                if (this.getDistanceSq(e) < 16.0) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                        this.attackEntityAsMob(e);
                    }
                }
                this.getNavigator().tryMoveToEntityLiving(e, 1.2);
            } else {
                this.setAttacking(0);
            }
        } else if (this.world.rand.nextInt(30) == 0 && OreSpawnMain.PlayNicely == 0) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 2; i < 15 && !this.scan_it((int)this.posX, (int)this.posY, (int)this.posZ, i, i, i); ++i) {
                if (i < 10) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)this.ty, (double)this.tz, 1.0);
                if (this.closest < 27 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    this.world.setBlockState(new BlockPos(this.tx, this.ty, this.tz), Blocks.AIR.getDefaultState());
                }
            }
        }
        if (this.world.rand.nextInt(40) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(1.0f);
        }
    }

    private boolean isSuitableTarget(EntityLivingBase var4, boolean par2) {
        if (var4 == null || var4 == this || !var4.isEntityAlive()) {
            return false;
        }
        if (var4 instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)var4;
            return !p.isCreative() && !p.isSpectator();
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List<EntityLivingBase> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(12.0, 4.0, 12.0));
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
    public boolean getCanSpawnHere() {
        Block bid;
        int i;
        int j;
        int k;
        
        for (k = -3; k < 3; ++k) {
            for (j = -3; j < 3; ++j) {
                for (i = 0; i < 5; ++i) {
                    BlockPos pos = new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    bid = this.world.getBlockState(pos).getBlock();
                    
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    
                    TileEntity tileEntity = this.world.getTileEntity(pos);
                    if (tileEntity instanceof TileEntityMobSpawner) {
                        TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileEntity;
                        ResourceLocation res = spawner.getSpawnerBaseLogic().getEntityId();
                        if (res != null && res.getResourcePath().toLowerCase().contains("alien")) {
                            return true;
                        }
                    }
                }
            }
        }
        
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID4) {
            return true;
        }
        if (this.posY > 50.0) {
            return false;
        }
        
        for (k = -1; k < 2; ++k) {
            for (j = -1; j < 2; ++j) {
                for (i = 1; i < 4; ++i) {
                    bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid != Blocks.AIR) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}