package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Baryonyx extends EntityAnimal {

    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Baryonyx(World worldIn) {
        super(worldIn);
        this.setSize(1.5f, 2.8f);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityMob.class, 8.0f, 1.0, 1.4));
        this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0f));
        this.tasks.addTask(6, new MyEntityAIWander(this, 1.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        return this.findBuddies() <= 8 && super.getCanSpawnHere();
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    public int mygetMaxHealth() {
        return 40;
    }

    @Override
    protected SoundEvent getAmbientSound() { 
        return SoundEvents.ENTITY_GENERIC_EXPLODE; // Pode ser alterado depois para o som original
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
        return 0.4f;
    }

    protected Item getDropItem() {
        return Items.BEEF;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.world.rand.nextInt(5) + 2;
        for (int var4 = 0; var4 < var3; ++var4) {
            this.dropItem(Items.BEEF, 1);
        }
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
                if (bid == Blocks.GRASS && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                bid = this.world.getBlockState(new BlockPos(x - dx, y + i, z + j)).getBlock();
                if (bid == Blocks.GRASS && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x - dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new BlockPos(x + i, y + dy, z + j)).getBlock();
                if (bid == Blocks.GRASS && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                bid = this.world.getBlockState(new BlockPos(x + i, y - dy, z + j)).getBlock();
                if (bid == Blocks.GRASS && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y - dy;
                    this.tz = z + j;
                    ++found;
                }
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dy; j <= dy; ++j) {
                bid = this.world.getBlockState(new BlockPos(x + i, y + j, z + dz)).getBlock();
                if (bid == Blocks.GRASS && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                bid = this.world.getBlockState(new BlockPos(x + i, y + j, z - dz)).getBlock();
                if (bid == Blocks.GRASS && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z - dz;
                    ++found;
                }
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
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        if (this.world.rand.nextInt(60) == 0 && OreSpawnMain.PlayNicely == 0) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 1; i < 11; ++i) {
                int j = i;
                if (j > 2) {
                    j = 2;
                }
                if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) break;
                if (i < 6) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)this.ty, (double)this.tz, 1.0);
                if (this.closest < 12) {
                    if (this.world.getGameRules().getBoolean("mobGriefing")) {
                        this.world.setBlockState(new BlockPos(this.tx, this.ty, this.tz), Blocks.DIRT.getDefaultState());
                    }
                    this.heal(1.0f);
                    this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, this.world.rand.nextFloat() * 0.2f + 0.9f);
                }
            }
        }
    }

    @Override
    protected boolean canDespawn() {
        if (this.isChild()) {
            this.enablePersistence();
            return false;
        }
        return !this.isNoDespawnRequired();
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public Baryonyx spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return new Baryonyx(this.world);
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && !par1ItemStack.isEmpty() && par1ItemStack.getItem() == Items.APPLE;
    }

    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack != null && !par1ItemStack.isEmpty() && par1ItemStack.getItem() == OreSpawnMain.MyCrystalApple;
    }

    private int findBuddies() {
        List<Baryonyx> var5 = this.world.getEntitiesWithinAABB(Baryonyx.class, this.getEntityBoundingBox().grow(20.0, 10.0, 20.0));
        return var5.size();
    }
}