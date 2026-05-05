package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
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
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Bee extends EntityMob {

    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(Bee.class, DataSerializers.VARINT);

    private BlockPos currentFlightTarget = null;
    private int stuck_count = 0;
    private int lastX = 0;
    private int lastZ = 0;
    private Entity rt = null;

    public Bee(World worldIn) {
        super(worldIn);
        this.setSize(1.5f, 2.5f);
        this.experienceValue = 25;
        this.isImmuneToFire = false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.Bee_stats.attack);
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

    public final int getAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public final void setAttacking(int par1) {
        this.dataManager.set(ATTACKING, par1);
    }

    @Override
    protected float getSoundVolume() {
        return 0.25f;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0f;
    }

    @Override
    protected SoundEvent getAmbientSound() { 
        return SoundEvents.ENTITY_GENERIC_EXPLODE; // Substituir pelo som correto do OreSpawn depois
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
    public boolean canBePushed() {
        return true;
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Bee_stats.health;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), new ItemStack(index, par1, 0));
        this.world.spawnEntity(var3);
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int i;
        int var4 = 2 + this.world.rand.nextInt(10);
        for (i = 0; i < var4; ++i) {
            this.dropItemRand(Items.GOLD_NUGGET, 1);
        }
        var4 = 2 + this.world.rand.nextInt(10);
        for (i = 0; i < var4; ++i) {
            this.dropItemRand(OreSpawnMain.MyButterCandy, 1);
        }
        var4 = 2 + this.world.rand.nextInt(10);
        for (i = 0; i < var4; ++i) {
            this.dropItemRand(Item.getItemFromBlock(Blocks.YELLOW_FLOWER), 1);
        }
        var4 = 2 + this.world.rand.nextInt(10);
        for (i = 0; i < var4; ++i) {
            this.dropItemRand(Items.SUGAR, 1);
        }
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
        if (this.isInWater() && this.world.rand.nextInt(4) == 1) {
            this.attackEntityAsMob(this);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)OreSpawnMain.Bee_stats.attack);
        if (this.world.rand.nextInt(3) == 1 && par1Entity instanceof EntityLivingBase) {
            ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 50, 0));
        }
        return var4;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        RayTraceResult result = this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false);
        return result == null || result.typeOfHit == RayTraceResult.Type.MISS;
    }

    @Override
    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        
        if (this.lastX == (int)this.posX && this.lastZ == (int)this.posZ) {
            ++this.stuck_count;
        } else {
            this.stuck_count = 0;
            this.lastX = (int)this.posX;
            this.lastZ = (int)this.posZ;
        }
        
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        
        if (this.stuck_count > 50 || this.world.rand.nextInt(300) == 0 || this.currentFlightTarget.distanceSqToCenter(this.posX, this.posY, this.posZ) < 2.1) {
            Block bid = Blocks.STONE;
            this.stuck_count = 0;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.world.rand.nextInt(9) + 4;
                xdir = this.world.rand.nextInt(9) + 4;
                if (this.world.rand.nextInt(2) == 0) zdir = -zdir;
                if (this.world.rand.nextInt(2) == 0) xdir = -xdir;
                
                this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.world.rand.nextInt(6) - 3, (int)this.posZ + zdir);
                bid = this.world.getBlockState(this.currentFlightTarget).getBlock();
                
                if (bid == Blocks.AIR && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        } else if (this.world.rand.nextInt(15) == 0) {
            EntityLivingBase e = null;
            if (this.rt instanceof EntityLivingBase) {
                e = (EntityLivingBase)this.rt;
            }
            if (e != null && e.isDead) {
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                this.setAttacking(1);
                this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY + 1, (int)e.posZ);
                if (this.getDistanceSq(e) < 16.0) {
                    this.attackEntityAsMob(e);
                }
            } else {
                this.setAttacking(0);
            }
        }
        
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * 0.30000000149011613;
        this.motionY += (Math.signum(var3) * 0.7D - this.motionY) * 0.20000000149011612;
        this.motionZ += (Math.signum(var5) * 0.5 - this.motionZ) * 0.30000000149011613;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0f;
        float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
        this.moveForward = 1.0f;
        this.rotationYaw += var8 / 4.0f;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = super.attackEntityFrom(par1DamageSource, par2);
        Entity e = par1DamageSource.getTrueSource();
        if (e != null && e instanceof EntityLivingBase && this.currentFlightTarget != null) {
            this.rt = e;
            this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY, (int)e.posZ);
        }
        return ret;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID4) {
            return true;
        }
        for (int k = -2; k < 2; ++k) {
            for (int j = -2; j < 2; ++j) {
                for (int i = 0; i < 5; ++i) {
                    BlockPos pos = new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    Block bid = this.world.getBlockState(pos).getBlock();
                    
                    if (bid == Blocks.MOB_SPAWNER) {
                        TileEntity tileentity = this.world.getTileEntity(pos);
                        if (tileentity instanceof TileEntityMobSpawner) {
                            TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileentity;
                            ResourceLocation res = spawner.getSpawnerBaseLogic().getEntityId();
                            if (res != null && res.getResourcePath().toLowerCase().contains("bee")) {
                                return true;
                            }
                        }
                    }
                }
            }
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
        
        if (this.posY < 50.0) {
            return false;
        }
        return this.world.isDaytime();
    }

    public void initCreature() {
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null || par1EntityLiving == this || !par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (!this.getEntitySenses().canSee(par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving.isInWater()) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            return !p.isCreative() && !p.isSpectator();
        }
        if (par1EntityLiving instanceof EntityVillager || par1EntityLiving instanceof Girlfriend || par1EntityLiving instanceof Boyfriend) {
            return true;
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        List<EntityLivingBase> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0, 6.0, 10.0));
        for (EntityLivingBase var4 : var5) {
            if (this.isSuitableTarget(var4, false)) {
                return var4;
            }
        }
        return null;
    }
}