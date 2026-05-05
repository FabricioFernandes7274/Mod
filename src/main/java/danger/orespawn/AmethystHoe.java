package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

public class AmethystHoe extends ItemHoe {

    public AmethystHoe(Item.ToolMaterial material) {
        // Removemos o antigo ID numérico. Na 1.12.2, passamos apenas o material.
        super(material);
        
        // O maxStackSize = 1 já é o comportamento padrão da classe ItemHoe
        this.setMaxDamage(2000); // Antigo setMaxDurability
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    // Mantido caso você tenha outras partes do mod que chamem esse método especificamente
    public String getMaterialName() {
        return "Amethyst";
    }
}