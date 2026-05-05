package danger.orespawn;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CaveFisher extends EntityMob {

    // Sistema de Metadados da 1.12.2 (Substitui o antigo dataManager.register(20, 0))
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(CaveFisher.class, DataSerializers.BYTE);
    
    private RenderInfo renderdata;
    private float moveSpeed = 0.25f;

    public CaveFisher(World worldIn) {
        super(worldIn);
        this.setSize(1.35f, 0.75f);
        this.experienceValue = 10;
        this.isImmuneToFire = false;
        
        // O RenderInfo do OreSpawn (usado para animações de pernas/corpo no renderizador)
        this.renderdata = new RenderInfo();
        this.resetRenderInfo();
    }

    @Override
    protected void initEntityAI() {
        // Se ainda tiveres a classe MyEntityAIWanderALot, podes usá-la, mas o EntityAIWanderAvoidWater é o padrão otimizado da 1.12.2
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 1.0D)); 
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawnMain.CaveFisher_stats.attack);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, (byte) 0);
    }

    private void resetRenderInfo() {
        if (this.renderdata == null) this.renderdata = new RenderInfo();
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
        return OreSpawnMain.CaveFisher_stats.health;
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
        return OreSpawnMain.CaveFisher_stats.defense;
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() { 
        return SoundEvents.ENTITY_GENERIC_EXPLODE; // Mantido o som peculiar original do OreSpawn
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
        return 1.5f;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0f;
    }

    @Override
    protected Item getDropItem() {
        // Fallback genérico, os drops reais são geridos em dropFewItems
        return Items.GOLD_NUGGET; 
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        // A forma correta de dropar itens com chances na 1.12.2
        int chance = this.world.rand.nextInt(6);
        if (chance == 0) {
            this.dropItem(Items.GOLD_NUGGET, 1);
        } else if (chance == 1) {
            this.dropItem(OreSpawnMain.UraniumNugget, 1);
        } else if (chance == 2) {
            this.dropItem(OreSpawnMain.TitaniumNugget, 1);
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();
        
        // IA Customizada de ataque à queima-roupa do OreSpawn
        if (this.world.rand.nextInt(8) == 0) {
            EntityLivingBase target = this.findSomethingToAttack();
            if (target != null) {
                if (this.getDistanceSq(target) < 8.0D) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(7) == 0 || this.world.rand.nextInt(8) == 1) {
                        this.attackEntityAsMob(target);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving(target, 1.2D);
                }
            } else {
                this.setAttacking(0);
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        // Imunidade a Cactos do OreSpawn original
        if (source == DamageSource.CACTUS) {
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    private boolean isSuitableTarget(EntityLivingBase target) {
        if (target == null || target == this || !target.isEntityAlive()) return false;
        if (MyUtils.isIgnoreable(target)) return false;
        if (!this.getEntitySenses().canSee(target)) return false;
        
        if (target instanceof CaveFisher || target.getClass().getSimpleName().equals("EnderReaper") || target.getClass().getSimpleName().equals("EnderKnight")) {
            return false;
        }
        if (target instanceof EntityMob) return false;
        
        if (target instanceof EntityPlayer) {
            if (((EntityPlayer) target).isCreative()) {
                return false;
            }
        }
        return true;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) {
            return null;
        }
        
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0D, 3.0D, 10.0D));
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity)) {
                return entity; // Retorna o primeiro alvo válido encontrado nas redondezas
            }
        }
        return null;
    }

    public final int getAttacking() {
        return this.dataManager.get(ATTACKING);
    }

    public final void setAttacking(int state) {
        this.dataManager.set(ATTACKING, (byte) state);
    }

    @Override
    public boolean getCanSpawnHere() {
        // Correção massiva do sistema de Spawners estragado pelo decompilador
        for (int k = -2; k < 2; ++k) {
            for (int j = -2; j < 2; ++j) {
                for (int i = 0; i < 5; ++i) {
                    BlockPos pos = new BlockPos((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                    Block bid = this.world.getBlockState(pos).getBlock();
                    
                    if (bid == Blocks.MOB_SPAWNER) {
                        TileEntity te = this.world.getTileEntity(pos);
                        if (te instanceof TileEntityMobSpawner) {
                            ResourceLocation mobName = ((TileEntityMobSpawner) te).getSpawnerBaseLogic().getEntityId();
                            if (mobName != null && mobName.getResourcePath().contains("cavefisher")) {
                                return true; // Permite o spawn se houver um spawner de CaveFisher nas proximidades
                            }
                        }
                    }
                }
            }
        }
        
        // Verifica luz e camada limite (abaixo do Y 50)
        if (!this.isValidLightLevel()) {
            return false;
        }
        return this.posY <= 50.0D && super.getCanSpawnHere();
    }
}