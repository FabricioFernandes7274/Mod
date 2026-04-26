/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.net.minecraft.inventory.IContainerListener
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.net.minecraft.inventory.SlotFurnaceFuel
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.FurnaceRecipes
 */
package danger.orespawn;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCrystalFurnace
extends Container {
    private TileEntityCrystalFurnace furnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public ContainerCrystalFurnace(InventoryPlayer par1InventoryPlayer, TileEntityCrystalFurnace par2TileEntityCrystalFurnace) {
        int i;
        this.furnace = par2TileEntityCrystalFurnace;
        this.addSlotToContainer(new Slot((IInventory)par2TileEntityCrystalFurnace, 0, 56, 17));
        this.addSlotToContainer(new Slot((IInventory)par2TileEntityCrystalFurnace, 1, 56, 53));
        this.addSlotToContainer((Slot)new net.minecraft.inventory.SlotFurnaceFuel(par1InventoryPlayer.player, (IInventory)par2TileEntityCrystalFurnace, 2, 116, 35));
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    public void onCraftGuiOpened(net.minecraft.inventory.IContainerListener par1net.minecraft.inventory.IContainerListener) {
        super.onCraftGuiOpened(par1net.minecraft.inventory.IContainerListener);
        par1net.minecraft.inventory.IContainerListener.sendProgressBarUpdate((Container)this, 0, this.furnace.furnaceCookTime);
        par1net.minecraft.inventory.IContainerListener.sendProgressBarUpdate((Container)this, 1, this.furnace.furnaceBurnTime);
        par1net.minecraft.inventory.IContainerListener.sendProgressBarUpdate((Container)this, 2, this.furnace.currentItemBurnTime);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i) {
            net.minecraft.inventory.IContainerListener icrafting = (net.minecraft.inventory.IContainerListener)this.listeners.get(i);
            if (this.lastCookTime != this.furnace.furnaceCookTime) {
                icrafting.sendProgressBarUpdate((Container)this, 0, this.furnace.furnaceCookTime);
            }
            if (this.lastBurnTime != this.furnace.furnaceBurnTime) {
                icrafting.sendProgressBarUpdate((Container)this, 1, this.furnace.furnaceBurnTime);
            }
            if (this.lastItemBurnTime == this.furnace.currentItemBurnTime) continue;
            icrafting.sendProgressBarUpdate((Container)this, 2, this.furnace.currentItemBurnTime);
        }
        this.lastCookTime = this.furnace.furnaceCookTime;
        this.lastBurnTime = this.furnace.furnaceBurnTime;
        this.lastItemBurnTime = this.furnace.currentItemBurnTime;
    }

    @SideOnly(value=Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            this.furnace.furnaceCookTime = par2;
        }
        if (par1 == 1) {
            this.furnace.furnaceBurnTime = par2;
        }
        if (par1 == 2) {
            this.furnace.currentItemBurnTime = par2;
        }
    }

    public boolean canInteractWith(net.minecraft.entity.player.EntityPlayer par1net.minecraft.entity.player.EntityPlayer) {
        return this.furnace.isUseableByPlayer(par1net.minecraft.entity.player.EntityPlayer);
    }

    public ItemStack transferStackInSlot(net.minecraft.entity.player.EntityPlayer par1net.minecraft.entity.player.EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (par2 == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (par2 != 1 && par2 != 0 ? (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null ? !this.mergeItemStack(itemstack1, 0, 1, false) : (TileEntityCrystalFurnace.isItemFuel(itemstack1) ? !this.mergeItemStack(itemstack1, 1, 2, false) : (par2 >= 3 && par2 < 30 ? !this.mergeItemStack(itemstack1, 30, 39, false) : par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)))) : !this.mergeItemStack(itemstack1, 3, 39, false)) {
                return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(par1net.minecraft.entity.player.EntityPlayer, itemstack1);
        }
        return itemstack;
    }
}

