package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class DungeonBeast extends EntityMob {
    // Sincronização da animação de ataque
    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(DungeonBeast.class, DataSerializers.VARINT);
    private RenderInfo renderdata = new RenderInfo();

    public DungeonBeast(World worldIn) {
        super(worldIn);
        this.setSize(1.15f, 1.1f);
        this.experienceValue = 60;
        this.isImmuneToFire = false;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        // Nota: MyEntityAIWanderALot deve ser convertido separadamente
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 14, 1.0D));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawnMain.DungeonBeast_stats.attack);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, 0);
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.DungeonBeast_stats.health;
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.DungeonBeast_stats.defense;
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_GENERIC_EXPLODE; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_GENERIC_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }

    @Override
    protected float getSoundVolume() { return 0.8f; }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int i = this.rand.nextInt(4);
        if (i == 1) this.dropItem(OreSpawnMain.MyCrystalPinkIngot, 1);
        else if (i == 2) this.dropItem(OreSpawnMain.MyCrystalApple, 1);
        else if (i == 3) this.dropItem(Item.getItemFromBlock(Blocks.LOG), 1);
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();

        if (this.world.rand.nextInt(8) == 0) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                if (this.getDistanceSq(e) < 8.0D) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(7) == 0 || this.world.rand.nextInt(8) == 1) {
                        this.attackEntityAsMob(e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving(e, 1.2D);
                }
            } else {
                this.setAttacking(0);
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getDamageType().equals("inWall") || source.getDamageType().equals("cactus")) {
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    private boolean isSuitableTarget(EntityLivingBase entity, boolean par2) {
        if (entity == null || entity == this || !entity.isEntityAlive()) return false;
        if (MyUtils.isIgnoreable(entity)) return false;
        if (!this.getEntitySenses().canSee(entity)) return false;
        
        // Blacklist de alvos do DungeonBeast
        if (entity instanceof Rat || entity instanceof DungeonBeast || entity instanceof Rotator ||
            entity instanceof Peacock || entity instanceof Irukandji || entity instanceof Skate ||
            entity instanceof Whale || entity instanceof Flounder) {
            return false;
        }

        if (entity instanceof EntityPlayer) {
            return !((EntityPlayer) entity).isCreative();
        }
        return true;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) return null;
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0D, 3.0D, 16.0D));
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity, false)) return entity;
        }
        return null;
    }

    public final int getAttacking() { return this.dataManager.get(ATTACKING); }
    public final void setAttacking(int par1) { this.dataManager.set(ATTACKING, par1); }

    @Override
    public boolean getCanSpawnHere() {
        // Lógica de verificação de Spawner
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    BlockPos pos = new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    if (this.world.getBlockState(pos).getBlock() == Blocks.MOB_SPAWNER) {
                        TileEntity te = this.world.getTileEntity(pos);
                        if (te instanceof TileEntityMobSpawner) {
                            // Na 1.12.2, o spawner armazena o ID da entidade no MobSpawnerLogic
                            String entityId = ((TileEntityMobSpawner)te).getSpawnerBaseLogic().getCachedEntity().getString("id");
                            if (entityId.contains("dungeon_beast")) return true;
                        }
                    }
                }
            }
        }

        if (!this.isValidLightLevel()) return false;

        // Lógica específica da Dimensão 5 (Utopia/Chunko)
        if (this.world.provider.getDimension() == OreSpawnMain.DimensionID5) {
            if (this.posY > 28.0 || this.posY < 25.0) return false;
            int airBlocks = 0;
            for (int k = -1; k <= 1; ++k) {
                for (int j = -1; j <= 1; ++j) {
                    if (this.world.isAirBlock(new BlockPos((int)this.posX + j, (int)this.posY + 1, (int)this.posZ + k))) {
                        airBlocks++;
                    }
                }
            }
            if (airBlocks < 6) return false;
        }
        return true;
    }
}