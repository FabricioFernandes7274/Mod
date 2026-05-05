package danger.orespawn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerCrystalWorkbench extends Container {
    
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public InventoryCraftResult craftResult = new InventoryCraftResult();
    
    private final World world;
    private final BlockPos pos;
    private final EntityPlayer player;

    public ContainerCrystalWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn) {
        this.world = worldIn;
        this.pos = posIn;
        this.player = playerInventory.player;

        // Slot 0: O Resultado do Crafting (O "Output")
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));

        // Slots de 1 a 9: A Grelha de Crafting 3x3
        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 3; ++i1) {
                this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
            }
        }

        // Inventário do Jogador
        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(playerInventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        // Hotbar (Barra Rápida) do Jogador
        for (int l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
        }

        // Força uma atualização para verificar se os itens já lá dentro formam alguma receita
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        // Na 1.12.2, usamos o método nativo "slotChangedCraftingGrid" do Container, 
        // que verifica e atualiza as receitas no lado do servidor e envia os pacotes para o cliente.
        this.slotChangedCraftingGrid(this.world, this.player, this.craftMatrix, this.craftResult);
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        // Na 1.12.2 não precisamos de iterar e dropar item a item. 
        // Este método oficial dropa tudo no chão suavemente quando fechamos a interface.
        if (!this.world.isRemote) {
            this.clearContainer(playerIn, this.world, this.craftMatrix);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        // Garante que o bloco não foi partido e que o jogador está perto suficiente
        if (this.world.getBlockState(this.pos).getBlock() != OreSpawnMain.CrystalWorkbenchBlock) {
            return false;
        } else {
            return playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 0) { // Se clicarmos com SHIFT no Slot do Resultado da receita
                itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);
                
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
                
            } else if (index >= 10 && index < 37) { // Mover do Inventário para a Hotbar
                if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 37 && index < 46) { // Mover da Hotbar para o Inventário
                if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) { // Mover da Grelha para a Mochila
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

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

            // Se for o output, certificamo-nos de que a peça foi corretamente dropada ao jogador
            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        // Equivale ao antigo func_94530_a, impede que o jogo junte itens iguais no slot do resultado
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}