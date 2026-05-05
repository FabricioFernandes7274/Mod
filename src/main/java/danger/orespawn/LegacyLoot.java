package danger.orespawn;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.Random;

public class LegacyLoot {
    public Item item;
    public int meta;
    public int min;
    public int max;
    public int weight;

    public LegacyLoot(Item item, int meta, int min, int max, int weight) {
        this.item = item;
        this.meta = meta;
        this.min = min;
        this.max = max;
        this.weight = weight;
    }

    // Método que enche o baú aleatoriamente baseado nos "pesos" e "chances" dos itens
    public static void generateChestContents(Random rand, LegacyLoot[] list, IInventory chest, int count) {
        if (chest == null || list == null || list.length == 0) return;
        
        int totalWeight = 0;
        for (LegacyLoot loot : list) {
            totalWeight += loot.weight;
        }

        if (totalWeight <= 0) return;

        for (int i = 0; i < count; i++) {
            int roll = rand.nextInt(totalWeight);
            int currentWeight = 0;
            LegacyLoot picked = null;
            
            // Sorteia o item com base na raridade (weight)
            for (LegacyLoot loot : list) {
                currentWeight += loot.weight;
                if (roll < currentWeight) {
                    picked = loot;
                    break;
                }
            }
            
            if (picked != null && picked.item != null) {
                // Calcula a quantidade aleatória
                int amount = picked.min + (picked.min < picked.max ? rand.nextInt(picked.max - picked.min + 1) : 0);
                ItemStack stack = new ItemStack(picked.item, amount, picked.meta);
                
                // Tenta colocar o item em um slot vazio do baú
                for (int attempts = 0; attempts < 10; attempts++) {
                    int slot = rand.nextInt(chest.getSizeInventory());
                    if (chest.getStackInSlot(slot).isEmpty()) {
                        chest.setInventorySlotContents(slot, stack);
                        break;
                    }
                }
            }
        }
    }
}