/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIAvoidEntity
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMate
 *  net.minecraft.entity.ai.EntityAIMoveIndoors
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;

public class StinkBug extends EntityCreature {

import danger.orespawn.MyEntityAIWanderALot;
import danger.orespawn.OreSpawnMain;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

    public StinkBug(World worldIn) {
        super(worldIn);
        this.setSize(0.55f, 0.55f);
        //this.fireResistance = 10;
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 2;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.5));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, net.minecraft.entity.player.EntityPlayer.class, 4.0f, 1.0, (double)1.4f));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, net.minecraft.entity.player.EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 10, 1.0));
        this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.tasks.addTask(10, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    protected void updateAITick() {
        if (this.isDead()) {
            return;
        }
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        super.updateAITick();
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (this.isDead()) {
            return false;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        if (this.getHealth() <= 0.0f || this.isDead()) {
            AxisAlignedBB bb = new AxisAlignedBB((double)(this.posX - 8.0), (double)(this.posY - 5.0), (double)(this.posZ - 8.0), (double)(this.posX + 8.0), (double)(this.posY + 10.0), (double)(this.posZ + 8.0));
            List var5 = this.world.getEntitiesWithinAABB(net.minecraft.entity.EntityLivingBase.class, bb);
            Iterator var2 = var5.iterator();
            net.minecraft.entity.EntityLivingBase var3 = null;
            while (var2.hasNext()) {
                var3 = (net.minecraft.entity.EntityLivingBase)var2.next();
                if (var3 == null) continue;
                var3.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300, 0));
            }
        }
        return ret;
    }

    public boolean canBreatheUnderwater() {
        return false;
    }

    public int mygetMaxHealth() {
        return 5;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return "orespawn:fart";
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return OreSpawnMain.MyDeadStinkBug;
    }

    public boolean getCanSpawnHere() {
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)).getBlock()this.posZ + k);
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    String s = tileentitymobspawner.getSpawnerBaseLogic().getEntityName();
                    if (s == null || !s.equals("Stink Bug")) continue;
                    return true;
                }
            }
        }
        return !(this.posY < 50.0);
    }

    protected boolean canDespawn() {
        if (this.isChild()) {
            this.enablePersistence();
            return false;
        }
        return !this.isNoDespawnRequired();
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public StinkBug spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return new StinkBug(this.world);
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.FISH;
    }

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == OreSpawnMain.MyCrystalApple;
    }
}


}