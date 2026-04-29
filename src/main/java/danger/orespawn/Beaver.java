/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIAvoidEntity
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMate
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Beaver extends EntityAnimal {
    private int buddy = 0;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
    private GenericTargetSorter TargetSorter = null;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Beaver(World worldIn) {
        super(worldIn);
        this.setSize(0.6f, 0.8f);
        this.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2f);
        //this.fireResistance = 100;
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 5;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityMob.class, 8.0f, 1.0, 1.5));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.5));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, net.minecraft.entity.player.EntityPlayer.class, 8.0f, 1.0, 1.5));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 10, 1.0));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
    }

    public boolean isWood(Block bid) {
        if (bid == Blocks.LOG || bid == OreSpawnMain.MyDT || bid == OreSpawnMain.MySkyTreeLog) {
            return true;
        }
        return bid == net.minecraft.init.Blocks.FENCE || bid == net.minecraft.init.Blocks.FENCE_GATE || bid == Blocks.STANDING_SIGN;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int d;
        Block bid;
        int j;
        int i;
        int found = 0;
        for (i = -dy; i <= dy; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + dx, y + i, z + j)).getBlock();
                if (this.isWood(bid) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if (!this.isWood(bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) || (d = dx * dx + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x - dx;
                this.ty = y + i;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + dy, z + j)).getBlock();
                if (this.isWood(bid) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if (!this.isWood(bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) || (d = dy * dy + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y - dy;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dy; j <= dy; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + dz)).getBlock();
                if (this.isWood(bid) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if (!this.isWood(bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        return found != 0;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.getEntityWorld(), this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), this.posY + 4.0 + (double)this.getEntityWorld().rand.nextInt(4), this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(4) - (double)OreSpawnMain.OreSpawnRand.nextInt(4), is);
        if (var3 != null) {
            this.getEntityWorld().spawnEntity((Entity)var3);
        }
        return is;
    }

    public void breakRecursor(World world, int x, int y, int z, int xf, int yf, int zf, int recursion) {
        int var7 = 1;
        if (recursion > 200) {
            return;
        }
        for (int var9 = -var7; var9 <= var7; ++var9) {
            for (int var10 = -var7; var10 <= var7; ++var10) {
                for (int var11 = -var7; var11 <= var7; ++var11) {
                    Block var12;
                    if (var9 == 0 && var10 == 0 && var11 == 0 || x + var9 == xf && y + var10 == yf && z + var11 == zf || recursion > 0 && x + var9 >= xf - var7 && x + var9 <= xf + var7 && y + var10 >= yf - var7 && y + var10 <= yf + var7 && z + var11 >= zf - var7 && z + var11 <= zf + var7 || !this.isWood(var12 = world.getBlockState(new net.minecraft.util.math.BlockPos(x + var9, y + var10, z + var11)).getBlock())) continue;
                    world.setBlockState(new net.minecraft.util.math.BlockPos(x + var9, y + var10, z + var11), Blocks.AIR.getStateFromMeta(0), 2);
                    this.dropItemRand(Item.getItemFromBlock((Block)var12), 1);
                    this.breakRecursor(world, x + var9, y + var10, z + var11, x, y, z, recursion + 1);
                }
            }
        }
    }

    protected void updateAITick() {
        Beaver buddy;
        if (this.isDead()) {
            return;
        }
        if (this.getEntityWorld().rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        if ((this.getEntityWorld().rand.nextInt(30) == 0 && this.getBeaverHealth() < this.mygetMaxHealth() || this.getEntityWorld().rand.nextInt(350) == 1) && OreSpawnMain.PlayNicely == 0) {
            int i;
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (i = 1; i < 11; ++i) {
                int j = i;
                if (j > 2) {
                    j = 2;
                }
                if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) break;
                if (i < 6) continue;
                ++i;
            }
            i = 0;
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)this.ty, (double)this.tz, 1.0);
                if (this.closest < 12) {
                    if (this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                        this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos(new net.minecraft.util.math.BlockPos(this.tx, this.ty, this.tz)), Blocks.AIR.getDefaultState().getStateFromMeta(2);
                        this.breakRecursor(this.getEntityWorld()), this.tx, this.ty, this.tz, this.tx, this.ty, this.tz, i);
                    }
                    this.heal(1.0f);
                    this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("orespawn:chainsaw")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, this.getEntityWorld().rand.nextFloat() * 0.2f + 0.9f));
                }
            }
        }
        if (this.getEntityWorld().rand.nextInt(200) == 1 && (buddy = this.findBuddy()) != null) {
            this.getNavigator().tryMoveToXYZ(buddy.posX, buddy.posY, buddy.posZ, 0.5);
        }
        super.updateAITick();
    }

    private Beaver findBuddy() {
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(Beaver.class, this.getEntityBoundingBox().expand(16.0, 6.0, 16.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        Beaver var4 = null;
        if (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (Beaver)var3;
            return var4;
        }
        return null;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public int mygetMaxHealth() {
        return 15;
    }

    public int getBeaverHealth() {
        return (int)this.getHealth();
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE; }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_HURT; }

    protected net.minecraft.util.SoundEvent getDeathSound() { return net.minecraft.init.SoundEvents.ENTITY_GENERIC_DEATH; }

    protected float getSoundVolume() {
        return 0.4f;
    }

    protected Item getDropItem() {
        return Items.PORKCHOP;
    }

    protected float getSoundPitch() {
        return this.isChild() ? (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.1f + 1.5f : (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.1f + 1.0f;
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        if (this.posY > 100.0) {
            return false;
        }
        Block bid = this.getEntityWorld().getBlockState(new BlockPos((int)this.posX, (int)this.posY - 1, (int)this.posZ)).getBlock(;
        return bid == Blocks.DIRT || bid == Blocks.GRASS || bid == Blocks.TALLGRASS || bid == Blocks.LEAVES;
    }

    protected boolean canDespawn() {
        return false;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public Beaver spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return new Beaver(this.getEntityWorld());
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.APPLE;
    }

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == OreSpawnMain.MyCrystalApple;
    }
}


}