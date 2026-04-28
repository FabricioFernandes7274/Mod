/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.item.ItemStack
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class EntityRainbowAnt
extends EntityAnt {
    public EntityRainbowAnt(World par1World) {
        super(par1World);
        this.setSize(0.1f, 0.1f);
        this.experienceValue = 0;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, (EntityAIBase)new EntityAIPanic((EntityCreature)this, (double)1.4f));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 9, 1.0));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
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
        if (par1EntityPlayer.dimension != OreSpawnMain.DimensionID3) {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, OreSpawnMain.DimensionID3, (Teleporter)new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawnMain.DimensionID3), OreSpawnMain.DimensionID3, this.world));
        } else {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, 0, (Teleporter)new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0), 0, this.world));
        }
        return true;
    }
}

