package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Crab extends EntityMob {
    
    // Parâmetros sincronizados de Dados
    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(Crab.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> CRAB_SCALE = EntityDataManager.createKey(Crab.class, DataSerializers.VARINT);
    
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;
    private int hurt_timer = 0;

    public Crab(World worldIn) {
        super(worldIn);
        this.setSize(1.25F, 2.5F);
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 150;
        this.isImmuneToFire = false;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        // Mantém-se perto de água mas vaga pelo mundo
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 1.0D)); 
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D * this.getCrabScale());
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double) ((float) OreSpawnMain.Crab_stats.attack * this.getCrabScale()));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, 0);
        this.dataManager.register(CRAB_SCALE, 0); // 0 indica que ainda não tem scale definido
        
        float t = 0.25F;
        if (this.world != null) {
            if (this.world.rand.nextInt(4) == 1) t = 0.5F;
            if (this.world.rand.nextInt(8) == 2) t = 1.0F;
        } else {
            if (OreSpawnMain.OreSpawnRand.nextInt(4) == 1) t = 0.5F;
            if (OreSpawnMain.OreSpawnRand.nextInt(8) == 2) t = 1.0F;
        }
        
        this.setCrabScale(t);
        this.experienceValue = (int) (400.0F * t);
        this.setSize(3.75F * this.getCrabScale(), 3.5F * this.getCrabScale());
    }

    public float getCrabScale() {
        int i = this.dataManager.get(CRAB_SCALE);
        if (i == 0) return 0.25F; // Scale por defeito se falhar
        return (float) i / 100.0F;
    }

    public void setCrabScale(float scale) {
        int i = (int) (scale * 100.0F);
        this.dataManager.set(CRAB_SCALE, i);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("Fscale")) {
            this.setCrabScale(compound.getFloat("Fscale"));
        }
        this.setSize(3.75F * this.getCrabScale(), 3.5F * this.getCrabScale());
        this.experienceValue = (int) (400.0F * this.getCrabScale());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setFloat("Fscale", this.getCrabScale());
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.isInWater() ? 0.95D : 0.25D * this.getCrabScale());
        super.onUpdate();
        this.setSize(3.75F * this.getCrabScale(), 3.5F * this.getCrabScale());
    }

    public int mygetMaxHealth() {
        return (int) ((float) OreSpawnMain.Crab_stats.health * this.getCrabScale());
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.Crab_stats.defense + (int) (2.0F * this.getCrabScale());
    }

    public int getCrabHealth() {
        return (int) this.getHealth();
    }

    @Override
    protected SoundEvent getAmbientSound() { 
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
        return 0.75F;
    }

    @Override
    protected float getSoundPitch() {
        return 2.0F - 0.3F * (1.0F / this.getCrabScale());
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int var5 = 4 + this.world.rand.nextInt(8);
        var5 = (int) ((float) var5 * this.getCrabScale());
        if (var5 < 1) var5 = 1;
        
        for (int var4 = 0; var4 < var5; ++var4) {
            this.dropItem(OreSpawnMain.MyRawCrabMeat, 1);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) OreSpawnMain.Crab_stats.attack * this.getCrabScale());
        if (flag && target instanceof EntityLivingBase) {
            double ks = 1.15D * (double) this.getCrabScale();
            double inair = 0.48D * (double) this.getCrabScale();
            
            float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
            
            if (target.isDead || target instanceof EntityPlayer) {
                inair *= 2.0D;
            }
            target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = false;
        if (source == DamageSource.CACTUS) {
            return false;
        }
        
        Entity e = source.getTrueSource();
        if (this.hurt_timer <= 0) {
            ret = super.attackEntityFrom(source, amount);
            this.hurt_timer = 8;
        }
        
        if (e instanceof EntityLiving) {
            if (e instanceof Crab) {
                return false;
            }
            this.setAttackTarget((EntityLivingBase) e);
            this.getNavigator().tryMoveToEntityLiving(e, 1.2D);
        }
        return ret;
    }

    // Procura por água por perto
    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;
        
        Iterable<BlockPos> blocks = BlockPos.getAllInBox(new BlockPos(x - dx, y - dy, z - dz), new BlockPos(x + dx, y + dy, z + dz));
        
        for (BlockPos pos : blocks) {
            Block bid = this.world.getBlockState(pos).getBlock();
            if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
                int d = (int) this.getDistanceSqToCenter(pos);
                if (d < this.closest) {
                    this.closest = d;
                    this.tx = pos.getX();
                    this.ty = pos.getY();
                    this.tz = pos.getZ();
                    found++;
                }
            }
        }
        return found != 0;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();
        
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        
        if (!this.isInWater() && this.world.rand.nextInt(25) == 0) {
            this.closest = 99999;
            this.tx = 0;
            this.ty = 0;
            this.tz = 0;
            
            for (int i = 1; i < 12; ++i) {
                int j = i > 10 ? 10 : i;
                if (this.scan_it((int) this.posX, (int) this.posY - 1, (int) this.posZ, i, j, i)) break;
            }
            
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double) this.tx, (double) (this.ty - 1), (double) this.tz, 1.33D);
            } else {
                if (this.world.rand.nextInt(100) == 1) {
                    this.heal(-1.0F * this.getCrabScale()); // Ele seca e leva dano fora de água
                }
            }
        }
        
        if (this.world.rand.nextInt(5) == 1) {
            EntityLivingBase e = this.getAttackTarget();
            if (this.world.rand.nextInt(100) == 1) {
                this.setAttackTarget(null);
            }
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            
            if (e != null) {
                this.faceEntity(e, 10.0F, 10.0F);
                if (this.getDistanceSq(e) < (double) ((6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F) * this.getCrabScale())) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                        this.attackEntityAsMob(e);
                        if (!this.world.isRemote) {
                            this.world.playSound(null, e.posX, e.posY, e.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.75F, 1.5F);
                        }
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving(e, 1.0D);
                }
            } else {
                this.setAttacking(0);
            }
        }
        
        // Regenera vida quando está na água
        if (this.world.rand.nextInt(120) == 1 && this.isInWater() && this.getHealth() < (float) this.mygetMaxHealth()) {
            this.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bobber.splash")), 1.5F, this.world.rand.nextFloat() * 0.2F + 0.9F);
            this.heal(4.0F * this.getCrabScale());
        }
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean par2) {
        if (target == null || target == this || !target.isEntityAlive() || target instanceof Crab) {
            return false;
        }
        if (!this.getEntitySenses().canSee(target)) {
            return false;
        }
        if (target instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) target;
            return !p.isCreative();
        }
        if (target instanceof EntityMob || target instanceof Lizard || target instanceof RubberDucky || target instanceof EntityVillager || target instanceof Girlfriend || target instanceof Boyfriend) {
            return true;
        }
        return MyUtils.isAttackableNonMob(target);
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) return null;
        
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0D, 6.0D, 16.0D));
        
        EntityLivingBase e = this.getAttackTarget();
        if (e != null && e.isEntityAlive()) {
            return e;
        }
        this.setAttackTarget(null);
        
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity, false)) {
                return entity;
            }
        }
        return null;
    }

    public final int getAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public final void setAttacking(int attacking) {
        this.dataManager.set(ATTACKING, attacking);
    }

    private int findBuddies() {
        List<Crab> list = this.world.getEntitiesWithinAABB(Crab.class, this.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        return list.size();
    }

    @Override
    public boolean getCanSpawnHere() {
        // Verifica primeiro se está a ser gerado por um Spawner específico
        BlockPos pos = new BlockPos(this);
        Iterable<BlockPos> box = BlockPos.getAllInBox(pos.add(-3, 0, -3), pos.add(3, 4, 3));
        
        for (BlockPos checkPos : box) {
            if (this.world.getBlockState(checkPos).getBlock() == Blocks.MOB_SPAWNER) {
                TileEntity te = this.world.getTileEntity(checkPos);
                if (te instanceof TileEntityMobSpawner) {
                    MobSpawnerBaseLogic logic = ((TileEntityMobSpawner) te).getSpawnerBaseLogic();
                    ResourceLocation mobId = logic.getEntityId();
                    if (mobId != null && mobId.getPath().equals("Crab")) {
                        this.setCrabScale(0.35F);
                        return true;
                    }
                }
            }
        }
        
        if (this.posY < 50.0D || !this.world.isDaytime()) {
            return false;
        }
        
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID5) {
            if (this.world.rand.nextInt(40) != 1) return false;
            if (this.findBuddies() > 3) return false;
        }
        
        return super.getCanSpawnHere();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
}