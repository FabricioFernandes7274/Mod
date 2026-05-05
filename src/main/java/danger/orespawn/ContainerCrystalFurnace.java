package danger.orespawn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCrystalFurnace extends Container {
    
    private final TileEntityCrystalFurnace furnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public ContainerCrystalFurnace(InventoryPlayer playerInventory, TileEntityCrystalFurnace furnaceInventory) {
        this.furnace = furnaceInventory;
        
        // Slot 0: Input (O que vai ser assado)
        this.addSlotToContainer(new Slot(furnaceInventory, 0, 56, 17));
        
        // Slot 1: Combustível (Usa SlotFurnaceFuel nativo para validar que é carvão/madeira etc)
        this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 56, 53));
        
        // Slot 2: Resultado (Usa SlotFurnaceOutput nativo para não deixar o jogador meter itens aqui à mão)
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 116, 35));

        // Slots de inventário do Jogador
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Barra de acesso rápido do Jogador (Hotbar)
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, this.furnace.furnaceCookTime);
        listener.sendWindowProperty(this, 1, this.furnace.furnaceBurnTime);
        listener.sendWindowProperty(this, 2, this.furnace.currentItemBurnTime);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        
        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener listener = this.listeners.get(i);
            
            if (this.lastCookTime != this.furnace.furnaceCookTime) {
                listener.sendWindowProperty(this, 0, this.furnace.furnaceCookTime);
            }
            if (this.lastBurnTime != this.furnace.furnaceBurnTime) {
                listener.sendWindowProperty(this, 1, this.furnace.furnaceBurnTime);
            }
            if (this.lastItemBurnTime != this.furnace.currentItemBurnTime) {
                listener.sendWindowProperty(this, 2, this.furnace.currentItemBurnTime);
            }
        }
        
        this.lastCookTime = this.furnace.furnaceCookTime;
        this.lastBurnTime = this.furnace.furnaceBurnTime;
        this.lastItemBurnTime = this.furnace.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            this.furnace.furnaceCookTime = data;
        } else if (id == 1) {
            this.furnace.furnaceBurnTime = data;
        } else if (id == 2) {
            this.furnace.currentItemBurnTime = data;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.furnace.isUsableByPlayer(playerIn);
    }

    // A lógica complexa do Shift-Click (Transferência Rápida)
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            // Se for o slot de Output (Resultado), põe no inventário do jogador
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }
            // Se NÃO for da fornalha (foi clicado no inventário do jogador)
            else if (index != 1 && index != 0) {
                // Tenta pôr no slot de assar (se tiver receita)
                if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty()) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // Tenta pôr no slot de combustível (se arder)
                else if (TileEntityCrystalFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // Arrumar entre a mochila e o cinto
                else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            }
            // Se foi clicado na Entrada ou Combustível, joga para o inventário do jogador
            else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}