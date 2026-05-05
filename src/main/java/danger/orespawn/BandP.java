package danger.orespawn;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BandP extends EntityMob {

    private static final DataParameter<Integer> WHAT = EntityDataManager.createKey(BandP.class, DataSerializers.VARINT);
    
    private int whatset = 0;
    private int whatami = 0;
    // Na 1.12.2, evitamos Arrays de ItemStack e usamos NonNullList para evitar NullPointerExceptions
    public NonNullList<ItemStack> MymainInventory = NonNullList.withSize(100, ItemStack.EMPTY);
    int got_stuff = 0;

    public BandP(World worldIn) {
        super(worldIn);
        this.setSize(0.75f, 1.75f);
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.experienceValue = 1000;
        
        this.tasks.addTask(0, new EntityAIMoveThroughVillage(this, 0.5, false));
        this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 0.5));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveIndoors(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)OreSpawnMain.BandP_stats.attack);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(WHAT, 0);
    }

    @Override
    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return this.got_stuff == 0;
    }

    @Override
    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        super.onUpdate();
        if (!this.world.isRemote && this.whatset == 0) {
            this.whatset = 1;
            this.whatami = this.world.rand.nextInt(2);
            this.setWhat(this.whatami);
        }
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.BandP_stats.health;
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.BandP_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() { 
        return SoundEvents.ENTITY_GENERIC_EXPLODE; // Substitua pelos sons do OreSpawn futuramente
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

    protected Item getDropItem() {
        return Items.EMERALD;
    }

    private ItemStack dropItemRand(Item index, int count) {
        if (index == null) {
            return ItemStack.EMPTY;
        }
        ItemStack is = new ItemStack(index, count, 0);
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), this.posY + 1.0, this.posZ + (double)OreSpawnMain.OreSpawnRand.nextInt(2) - (double)OreSpawnMain.OreSpawnRand.nextInt(2), is);
        this.world.spawnEntity(var3);
        return is;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        int i;
        int var4 = 10 + this.world.rand.nextInt(5);
        for (i = 0; i < var4; ++i) {
            this.dropItemRand(Items.EMERALD, 1);
        }
        if (this.getWhat() == 0) {
            var4 = 2 + this.world.rand.nextInt(3);
            for (i = 0; i < var4; ++i) {
                this.dropItemRand(OreSpawnMain.UraniumNugget, 1);
                this.dropItemRand(OreSpawnMain.TitaniumNugget, 1);
            }
        }
        for (i = 0; i < this.MymainInventory.size(); ++i) {
            ItemStack stack = this.MymainInventory.get(i);
            if (!stack.isEmpty()) {
                ItemStack is = this.dropItemRand(stack.getItem(), stack.getCount());
                if (stack.getCount() == 1 && is != null && !is.isEmpty()) {
                    is.setItemDamage(stack.getItemDamage());
                }
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        return super.attackEntityAsMob(par1Entity);
    }

    @Override
    protected void updateAITasks() {
        EntityLivingBase e;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        
        if (this.world.rand.nextInt(12) == 1 && (e = this.findSomethingToAttack()) != null) {
            this.faceEntity(e, 10.0f, 10.0f);
            if (this.getDistanceSq(e) < 9.0) {
                this.attackEntityAsMob(e);
                
                // Lógica de roubo de itens para a 1.12.2 (NonNullList e ItemStack.EMPTY)
                if (e instanceof EntityPlayer) {
                    EntityPlayer p = (EntityPlayer)e;
                    int k = -1;
                    int kp = -1;
                    
                    for (int i = 0; i < this.MymainInventory.size(); ++i) {
                        if (this.MymainInventory.get(i).isEmpty()) {
                            k = i;
                            break;
                        }
                    }
                    
                    if (k >= 0) {
                        // Tenta roubar armadura primeiro
                        for (int i = p.inventory.armorInventory.size() - 1; i >= 0; --i) {
                            if (!p.inventory.armorInventory.get(i).isEmpty()) {
                                kp = i;
                                break;
                            }
                        }
                        
                        if (kp >= 0) {
                            this.MymainInventory.set(k, p.inventory.armorInventory.get(kp).copy());
                            p.inventory.armorInventory.set(kp, ItemStack.EMPTY);
                            ++this.got_stuff;
                        } else {
                            // Se não tiver armadura, rouba do inventário principal
                            for (int i = p.inventory.mainInventory.size() - 1; i >= 0; --i) {
                                if (!p.inventory.mainInventory.get(i).isEmpty()) {
                                    kp = i;
                                    break;
                                }
                            }
                            if (kp >= 0) {
                                this.MymainInventory.set(k, p.inventory.mainInventory.get(kp).copy());
                                p.inventory.mainInventory.set(kp, ItemStack.EMPTY);
                                ++this.got_stuff;
                            }
                        }
                    }
                }
            } else {
                this.getNavigator().tryMoveToEntityLiving(e, 1.25);
            }
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null || par1EntityLiving == this || !par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (!this.getEntitySenses().canSee(par1EntityLiving)) {
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
        List<EntityLivingBase> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(20.0, 6.0, 20.0));
        for (EntityLivingBase var4 : var5) {
            if (this.isSuitableTarget(var4, false)) {
                return var4;
            }
        }
        return null;
    }

    public int getWhat() {
        return this.dataManager.get(WHAT);
    }

    public void setWhat(int par1) {
        this.dataManager.set(WHAT, par1);
    }

    @Override
    public boolean getCanSpawnHere() {
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    BlockPos pos = new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
                    Block bid = this.world.getBlockState(pos).getBlock();
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    
                    TileEntity tileentity = this.world.getTileEntity(pos);
                    if (tileentity instanceof TileEntityMobSpawner) {
                        TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileentity;
                        ResourceLocation res = spawner.getSpawnerBaseLogic().getEntityId();
                        if (res != null && res.getResourcePath().toLowerCase().contains("criminal")) {
                            return true;
                        }
                    }
                }
            }
        }
        
        if (!this.world.isDaytime()) {
            return false;
        }
        if (this.posY < 100.0) { // O código original checava < 50 e depois < 100. Unifiquei.
            return false;
        }
        
        List<BandP> target = this.world.getEntitiesWithinAABB(BandP.class, this.getEntityBoundingBox().grow(32.0, 12.0, 32.0));
        if (!target.isEmpty() && target.get(0) != this) {
            return false;
        }
        
        List<EntityVillager> target2 = this.world.getEntitiesWithinAABB(EntityVillager.class, this.getEntityBoundingBox().grow(36.0, 12.0, 36.0));
        return !target2.isEmpty();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.got_stuff != 0) {
            par1NBTTagCompound.setTag("Inventory", this.writeToNBT(new NBTTagList()));
        }
        par1NBTTagCompound.setInteger("GotStuff", this.got_stuff);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.got_stuff = par1NBTTagCompound.getInteger("GotStuff");
        if (this.got_stuff != 0) {
            NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory", 10);
            this.readFromNBT(nbttaglist);
        }
    }

    public NBTTagList writeToNBT(NBTTagList par1NBTTagList) {
        for (int i = 0; i < this.MymainInventory.size(); ++i) {
            if (!this.MymainInventory.get(i).isEmpty()) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.MymainInventory.get(i).writeToNBT(nbttagcompound);
                par1NBTTagList.appendTag(nbttagcompound);
            }
        }
        return par1NBTTagList;
    }

    public void readFromNBT(NBTTagList par1NBTTagList) {
        this.MymainInventory = NonNullList.withSize(100, ItemStack.EMPTY);
        for (int i = 0; i < par1NBTTagList.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = par1NBTTagList.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 0xFF;
            ItemStack itemstack = new ItemStack(nbttagcompound);
            if (!itemstack.isEmpty() && j >= 0 && j < this.MymainInventory.size()) {
                this.MymainInventory.set(j, itemstack);
            }
        }
    }
}