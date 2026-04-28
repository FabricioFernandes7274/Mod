/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.ItemStack
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.List;

public class EntityAnt extends EntityCreature {

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
    private static final ResourceLocation texture1 = new net.minecraft.util.ResourceLocation("orespawn", "ant.png");
    private static final ResourceLocation texture2 = new net.minecraft.util.ResourceLocation("orespawn", "red_ant.png");
    private static final ResourceLocation texture3 = new net.minecraft.util.ResourceLocation("orespawn", "rainbow_ant.png");
    private static final ResourceLocation texture4 = new net.minecraft.util.ResourceLocation("orespawn", "unstableant.png");
    private static final ResourceLocation texture5 = new net.minecraft.util.ResourceLocation("orespawn", "termite.png");

    public EntityAnt(World worldIn) {
        super(worldIn);
        this.setSize(0.1f, 0.1f);
        this.experienceValue = 0;
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(0, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.4));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 9, 1.0));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    public ResourceLocation getTexture(EntityAnt a) {
        if (a instanceof EntityRedAnt) {
            return texture2;
        }
        if (a instanceof EntityRainbowAnt) {
            return texture3;
        }
        if (a instanceof EntityUnstableAnt) {
            return texture4;
        }
        if (a instanceof Termite) {
            return texture5;
        }
        return texture1;
    }

    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        super.onUpdate();
    }

    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        if (par1EntityPlayer == null) {
            return false;
        }
        if (!(par1EntityPlayer instanceof net.minecraft.entity.player.EntityPlayerMP)) {
            return false;
        }
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getCount() <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (var2 != null) {
            return false;
        }
        if (par1EntityPlayer.dimension != OreSpawnMain.DimensionID) {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, OreSpawnMain.DimensionID, (Teleporter)new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawnMain.DimensionID), OreSpawnMain.DimensionID, this.world));
        } else {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, 0, (Teleporter)new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0), 0, this.world));
        }
        return true;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public int mygetMaxHealth() {
        return 1;
    }

    protected String getLivingSound() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected String getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 0.0f;
    }

    protected void playStepSound(int par1, int par2, int par3, int par4) {
    }

    protected void dropFewItems(boolean par1, int par2) {
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        return this.findBuddies() <= 4;
    }

    private int findBuddies() {
        List var5 = this.world.getEntitiesWithinAABB(EntityAnt.class, this.getEntityBoundingBox().expand(20.0, 10.0, 20.0));
        return var5.size();
    }

    public void updateAITick() {
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        super.updateAITick();
    }
}


}