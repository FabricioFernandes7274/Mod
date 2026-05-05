package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Beaver extends EntityAnimal {
    
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Beaver(World worldIn) {
        super(worldIn);
        this.setSize(0.6f, 0.8f);
        this.experienceValue = 5;
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityMob.class, 8.0f, 1.0D, 1.5D));
        this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
        this.tasks.addTask(5, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0f, 1.0D, 1.5D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, new MyEntityAIWanderALot(this, 10, 1.0));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    public boolean isWood(Block bid) {
        // Na 1.12.2, usar instanceof abrange todas as variações de madeiras e cercas!
        if (bid == OreSpawnMain.MyDT || bid == OreSpawnMain.MySkyTreeLog) return true;
        return bid instanceof BlockLog || bid instanceof BlockFence || bid instanceof BlockFenceGate || bid == Blocks.STANDING_SIGN || bid == Blocks.WALL_SIGN;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int d;
        Block bid;
        int found = 0;
        
        for (int i = -dy; i <= dy; ++i) {
            for (int j = -dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();
                if (this.isWood(bid) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                bid = this.world.getBlockState(new BlockPos(x - dx, y + i, z + j)).getBlock();
                if (this.isWood(bid) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x - dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
            }
        }
        for (int i = -dx; i <= dx; ++i) {
            for (int j = -dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new BlockPos(x + i, y + dy, z + j)).getBlock();
                if (this.isWood(bid) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                bid = this.world.getBlockState(new BlockPos(x + i, y - dy, z + j)).getBlock();
                if (this.isWood(bid) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y - dy;
                    this.tz = z + j;
                    ++found;
                }
            }
        }
        for (int i = -dx; i <= dx; ++i) {
            for (int j = -dy; j <= dy; ++j) {
                bid = this.world.getBlockState(new BlockPos(x + i, y + j, z + dz)).getBlock();
                if (this.isWood(bid) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                bid = this.world.getBlockState(new BlockPos(x + i, y + j, z - dz)).getBlock();
                if (this.isWood(bid) && (d = dz * dz + j * j + i * i) < this.closest) {
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

    private ItemStack dropItemRand(Item index, int par1) {
        if (index == null) return ItemStack.EMPTY;
        ItemStack is = new ItemStack(index, par1, 0);
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 4.0 + (double)this.world.rand.nextInt(4), this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), is);
        this.world.spawnEntity(var3);
        return is;
    }

    public void breakRecursor(World worldIn, int x, int y, int z, int xf, int yf, int zf, int recursion) {
        int var7 = 1;
        if (recursion > 200) {
            return;
        }
        for (int var9 = -var7; var9 <= var7; ++var9) {
            for (int var10 = -var7; var10 <= var7; ++var10) {
                for (int var11 = -var7; var11 <= var7; ++var11) {
                    BlockPos pos = new BlockPos(x + var9, y + var10, z + var11);
                    Block var12 = worldIn.getBlockState(pos).getBlock();
                    
                    if (var9 == 0 && var10 == 0 && var11 == 0 || x + var9 == xf && y + var10 == yf && z + var11 == zf || recursion > 0 && x + var9 >= xf - var7 && x + var9 <= xf + var7 && y + var10 >= yf - var7 && y + var10 <= yf + var7 && z + var11 >= zf - var7 && z + var11 <= zf + var7 || !this.isWood(var12)) continue;
                    
                    worldIn.setBlockToAir(pos);
                    this.dropItemRand(Item.getItemFromBlock(var12), 1);
                    this.breakRecursor(worldIn, x + var9, y + var10, z + var11, x, y, z, recursion + 1);
                }
            }
        }
    }

    @Override
    protected void updateAITick() {
        if (this.isDead) {
            return;
        }
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        if ((this.world.rand.nextInt(30) == 0 && this.getBeaverHealth() < this.mygetMaxHealth() || this.world.rand.nextInt(350) == 1) && OreSpawnMain.PlayNicely == 0) {
            int i;
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (i = 1; i < 11; ++i) {
                int j = i;
                if (j > 2) j = 2;
                if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) break;
                if (i < 6) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)this.ty, (double)this.tz, 1.0);
                if (this.closest < 12) {
                    if (this.world.getGameRules().getBoolean("mobGriefing")) {
                        this.world.setBlockToAir(new BlockPos(this.tx, this.ty, this.tz));
                        this.breakRecursor(this.world, this.tx, this.ty, this.tz, this.tx, this.ty, this.tz, 0);
                    }
                    this.heal(1.0f);
                    
                    SoundEvent chainsaw = SoundEvent.REGISTRY.getObject(new ResourceLocation("orespawn", "chainsaw"));
                    if (chainsaw != null) {
                        this.playSound(chainsaw, 1.0f, this.world.rand.nextFloat() * 0.2f + 0.9f);
                    }
                }
            }
        }
        Beaver buddy;
        if (this.world.rand.nextInt(200) == 1 && (buddy = this.findBuddy()) != null) {
            this.getNavigator().tryMoveToXYZ(buddy.posX, buddy.posY, buddy.posZ, 0.5);
        }
        super.updateAITick();
    }

    private Beaver findBuddy() {
        List<Beaver> var5 = this.world.getEntitiesWithinAABB(Beaver.class, this.getEntityBoundingBox().grow(16.0, 6.0, 16.0));
        for (Beaver var4 : var5) {
            if (var4 != this) {
                return var4;
            }
        }
        return null;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public int mygetMaxHealth() {
        return 15;
    }

    public int getBeaverHealth() {
        return (int)this.getHealth();
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
        return 0.4f;
    }

    protected Item getDropItem() {
        return Items.PORKCHOP;
    }

    @Override
    protected float getSoundPitch() {
        return this.isChild() ? (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.1f + 1.5f : (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.1f + 1.0f;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0 || this.posY > 100.0) {
            return false;
        }
        Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY - 1, (int)this.posZ)).getBlock();
        return bid == Blocks.DIRT || bid == Blocks.GRASS || bid == Blocks.TALLGRASS || bid == Blocks.LEAVES;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public Beaver spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return new Beaver(this.world);
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && !par1ItemStack.isEmpty() && par1ItemStack.getItem() == Items.APPLE;
    }

    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack != null && !par1ItemStack.isEmpty() && par1ItemStack.getItem() == OreSpawnMain.MyCrystalApple;
    }
}