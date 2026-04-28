/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.net.minecraft.entity.ai.EntityAIAttackMelee
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAINearestAttackableTarget
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.ItemStack
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRedAnt
extends EntityAnt {
    int attack_delay = 20;

    public EntityRedAnt(World par1World) {
        super(par1World);
        this.setSize(0.2f, 0.2f);
        this.moveSpeed = 0.2f;
        this.experienceValue = 1;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, (EntityAIBase)new EntityAIPanic((EntityCreature)this, (double)1.4f));
        this.tasks.addTask(1, (EntityAIBase)new net.minecraft.entity.ai.EntityAIAttackMelee((EntityCreature)this, net.minecraft.entity.player.EntityPlayer.class, 1.0, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 10, 1.0));
        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, net.minecraft.entity.player.EntityPlayer.class, 4, true));
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
    }

    @Override
    public int mygetMaxHealth() {
        return 2;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (OreSpawnMain.OreSpawnRand.nextInt(15) != 0) {
            return false;
        }
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), 1.0f);
        return var4;
    }

    @Override
    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        if (par1EntityPlayer == null) {
            return false;
        }
        if (!(par1EntityPlayer instanceof net.minecraft.entity.player.EntityPlayerMP)) {
            return false;
        }
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.stackSize <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (var2 != null) {
            return false;
        }
        if (par1EntityPlayer.dimension != OreSpawnMain.DimensionID2) {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, OreSpawnMain.DimensionID2, (Teleporter)new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawnMain.DimensionID2), OreSpawnMain.DimensionID2, this.world));
        } else {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, 0, (Teleporter)new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0), 0, this.world));
        }
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.isDead()) {
            return;
        }
        if (this.attack_delay > 0) {
            --this.attack_delay;
        }
        if (this.attack_delay > 0) {
            return;
        }
        this.attack_delay = 20;
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return;
        }
        if (OreSpawnMain.PlayNicely != 0) {
            return;
        }
        net.minecraft.entity.player.EntityPlayer e = this.world.getClosestVulnerablePlayerToEntity((Entity)this, 1.5);
        if (e != null) {
            this.attackEntityAsMob((Entity)e);
        }
    }
}

