package danger.orespawn;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Brutalfly extends EntityMob {
    
    public BlockPos currentFlightTarget;
    
    // Variáveis que o descompilador "esqueceu" de declarar
    private int wing_sound = 0;
    private int health_ticker = 100;
    private int stuck_count = 0;
    private int lastX = 0;
    private int lastY = 0;
    private int lastZ = 0;

    public Brutalfly(World worldIn) {
        super(worldIn);
        this.setSize(5.0f, 2.0f);
        this.experienceValue = 100;
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double) OreSpawnMain.Brutalfly_stats.attack);
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int getTotalArmorValue() {
        return OreSpawnMain.Brutalfly_stats.defense;
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.Brutalfly_stats.health;
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
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_GENERIC_EXPLODE; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_GENERIC_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_GENERIC_DEATH; }

    @Override
    public boolean canBePushed() {
        return true;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
        
        // Efeito de som das asas batendo
        ++this.wing_sound;
        if (this.wing_sound > 30) {
            if (!this.world.isRemote) {
                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            }
            this.wing_sound = 0;
        }
        
        // Regeneração lenta de vida
        --this.health_ticker;
        if (this.health_ticker <= 0) {
            if (this.getHealth() < (float) this.mygetMaxHealth()) {
                this.heal(1.0f);
            }
            this.health_ticker = 100;
        }
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false) == null;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();

        // Verificação para ver se o mob está preso numa parede
        if (this.lastX == (int) this.posX && this.lastY == (int) this.posY && this.lastZ == (int) this.posZ) {
            ++this.stuck_count;
        } else {
            this.stuck_count = 0;
            this.lastX = (int) this.posX;
            this.lastY = (int) this.posY;
            this.lastZ = (int) this.posZ;
        }

        int shootChance = this.world.getDifficulty() == EnumDifficulty.HARD ? 2 : 3;

        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ);
        }

        // Lógica de "Vaguear" (Wandering) do voo
        if (this.stuck_count > 30 || this.world.rand.nextInt(200) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 9.0D) {
            int down = 0;
            int dist = 20;
            for (int i = -5; i <= 5; i += 5) {
                for (int j = -5; j <= 5; j += 5) {
                    for (int k = 1; k < 20; ++k) {
                        Block bid = this.world.getBlockState(new BlockPos((int) this.posX + j, (int) this.posY - k, (int) this.posZ + i)).getBlock();
                        if (bid != Blocks.AIR) {
                            if (k < dist) dist = k;
                            break;
                        }
                    }
                }
            }
            
            if (dist > 10) down = dist - 10 + 1;

            int keep_trying = 30;
            while (keep_trying != 0) {
                int xdir = this.world.rand.nextInt(2) == 0 ? -1 : 1;
                int zdir = this.world.rand.nextInt(2) == 0 ? -1 : 1;
                
                int newx = (this.world.rand.nextInt(20) + 8) * xdir;
                int newz = (this.world.rand.nextInt(20) + 8) * zdir;
                
                BlockPos potentialTarget = new BlockPos((int) this.posX + newx, (int) this.posY + this.world.rand.nextInt(7) - 1 - down, (int) this.posZ + newz);
                
                Block bid = this.world.getBlockState(potentialTarget).getBlock();
                if (bid == Blocks.AIR && this.canSeeTarget(potentialTarget.getX(), potentialTarget.getY(), potentialTarget.getZ())) {
                    this.currentFlightTarget = potentialTarget;
                    break;
                }
                --keep_trying;
            }
            this.stuck_count = 0;
        }

        // Lógica de Procurar Alvo e Atacar
        if (this.world.rand.nextInt(6) == 0) {
            EntityPlayer target = this.world.getClosestPlayerToEntity(this, 30.0D);
            if (target != null && !target.isCreative() && this.getEntitySenses().canSee(target)) {
                this.currentFlightTarget = new BlockPos((int) target.posX, (int) target.posY + 4, (int) target.posZ);
                if (this.world.rand.nextInt(shootChance) == 0) {
                    this.attackWithSomething(target);
                }
            } else {
                EntityLivingBase mobTarget = this.findSomethingToAttack();
                if (mobTarget != null) {
                    this.currentFlightTarget = new BlockPos((int) mobTarget.posX, (int) mobTarget.posY + 5, (int) mobTarget.posZ);
                    if (this.getDistanceSq(mobTarget) > 25.0D) {
                        if (this.world.rand.nextInt(shootChance) == 0) {
                            this.attackWithSomething(mobTarget);
                        }
                    } else {
                        this.attackEntityAsMob(mobTarget);
                    }
                }
            }
        }

        // Move a entidade suavemente na direção do FlightTarget
        double var1 = (double) this.currentFlightTarget.getX() + 0.5D - this.posX;
        double var3 = (double) this.currentFlightTarget.getY() + 0.1D - this.posY;
        double var5 = (double) this.currentFlightTarget.getZ() + 0.5D - this.posZ;
        
        this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.3D;
        this.motionY += (Math.signum(var3) * 0.7D - this.motionY) * 0.2D;
        this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.3D;
        
        float var7 = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0f;
        float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
        this.moveForward = 1.0f;
        this.rotationYaw += var8 / 8.0f;
    }

    @Override
	public void fall(float distance, float damageMultiplier) {}

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}

    @Override
    public boolean doesEntityNotTriggerPressurePlate() { return true; }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity e = source.getTrueSource();
        // Não sofre dano de outras Brutalflys
        if (e instanceof Brutalfly) return false;
        
        boolean ret = super.attackEntityFrom(source, amount);
        
        // Se atacado, vira-se para o atacante
        if (e != null && ret) {
            this.currentFlightTarget = new BlockPos((int) e.posX, (int) e.posY + 2, (int) e.posZ);
        }
        return ret;
    }

    @Override
    public boolean getCanSpawnHere() {
        // Lógica de spawn aprimorada e reparada para 1.12.2
        for (int k = -2; k <= 2; ++k) {
            for (int j = -2; j <= 2; ++j) {
                for (int i = 1; i < 4; ++i) {
                    BlockPos checkPos = new BlockPos((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k);
                    Block bid = this.world.getBlockState(checkPos).getBlock();
                    
                    if (bid == Blocks.MOB_SPAWNER) {
                        TileEntity te = this.world.getTileEntity(checkPos);
                        if (te instanceof TileEntityMobSpawner) {
                            ResourceLocation mobName = ((TileEntityMobSpawner) te).getSpawnerBaseLogic().getEntityId();
                            if (mobName != null && mobName.getResourcePath().contains("brutalfly")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        if (this.posY < 70.0D || !this.isValidLightLevel() || this.world.isDaytime()) return false;
        
        // Não spawna se houver blocos diretamente em cima (precisa de espaço)
        for (int k = -4; k < 4; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 1; i < 10; ++i) {
                    if (this.world.getBlockState(new BlockPos((int) this.posX + j, (int) this.posY + i, (int) this.posZ + k)).getBlock() != Blocks.AIR) {
                        return false;
                    }
                }
            }
        }
        
        // Limita a quantidade na mesma área
        List<Brutalfly> targets = this.world.getEntitiesWithinAABB(Brutalfly.class, this.getEntityBoundingBox().expand(64.0D, 32.0D, 64.0D));
        return targets.isEmpty();
    }

    private void dropItemRand(Item index, int amount) {
        EntityItem item = new EntityItem(this.world, this.posX + (this.world.rand.nextInt(8) - this.world.rand.nextInt(8)), this.posY + 1.0D, this.posZ + (this.world.rand.nextInt(8) - this.world.rand.nextInt(8)), new ItemStack(index, amount));
        this.world.spawnEntity(item);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        for (int i = 0; i < 20; ++i) {
            double var1 = (this.world.rand.nextFloat() - 0.5D) * 8.0D;
            double var2 = (this.world.rand.nextFloat() - 0.5D) * 4.0D;
            double var3 = (this.world.rand.nextFloat() - 0.5D) * 8.0D;
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + var1, this.posY + 2.0D + var2, this.posZ + var3, 0.0D, 0.0D, 0.0D);
        }
        for (int i = 0; i < 53; ++i) {
            this.dropItemRand(Items.GOLD_NUGGET, 1);
        }
        for (int i = 0; i < 20; ++i) {
            spawnCreature(this.world, "orespawn:butterfly", this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D);
        }
    }

    public static Entity spawnCreature(World world, String name, double x, double y, double z) {
        if (world == null) return null;
        Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(name), world);
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0f, 0.0f);
            world.spawnEntity(entity);
            if (entity instanceof EntityLiving) ((EntityLiving) entity).playLivingSound();
        }
        return entity;
    }

    private void attackWithSomething(EntityLivingBase target) {
        double xzoff = 2.25D;
        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
        
        // No OreSpawn original, ele disparava EntitySmallFireball ou BetterFireball dependendo da dificuldade
        if (this.world.getDifficulty() == EnumDifficulty.EASY || this.world.rand.nextInt(2) == 0) {
            double dX = target.posX - cx;
            double dY = (target.posY + 0.55D) - this.posY;
            double dZ = target.posZ - cz;
            
            EntitySmallFireball sf = new EntitySmallFireball(this.world, this, dX, dY, dZ);
            sf.setLocationAndAngles(cx, this.posY, cz, this.rotationYaw, 0.0f);
            this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity(sf);
        } else {
            // Se tiveres a classe BetterFireball pronta, deves usar aqui, caso contrário, usa a fireball normal
            double dX = target.posX - cx;
            double dY = (target.posY + 0.55D) - this.posY;
            double dZ = target.posZ - cz;
            
            EntitySmallFireball bf = new EntitySmallFireball(this.world, this, dX, dY, dZ);
            bf.setLocationAndAngles(cx, this.posY, cz, this.rotationYaw, 0.0f);
            this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
            this.world.spawnEntity(bf);
        }
    }

    private boolean isSuitableTarget(EntityLivingBase target, boolean ignoreSight) {
        if (target == null || target == this || !target.isEntityAlive()) return false;
        if (target instanceof Brutalfly || target.getClass().getSimpleName().equals("Mothra") || target.getClass().getSimpleName().equals("Vortex")) return false;
        
        if (!this.getEntitySenses().canSee(target)) return false;
        if (target instanceof EntityMob) return true;
        
        if (target instanceof EntityPlayer) {
            return !((EntityPlayer) target).isCreative();
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) return null;
        
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(25.0D, 20.0D, 25.0D));
        for (EntityLivingBase entity : list) {
            if (this.isSuitableTarget(entity, false)) {
                return entity; // Retorna o primeiro alvo válido encontrado
            }
        }
        return null;
    }
}