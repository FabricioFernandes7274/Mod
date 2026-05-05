package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class AmethystPickaxe extends ItemPickaxe {

    public AmethystPickaxe(Item.ToolMaterial material) {
        // Removemos o ID numérico, passamos apenas o material tático!
        super(material);
        
        // maxStackSize = 1 já é o padrão da classe base.
        this.setMaxDamage(2000); // Antigo setMaxDurability
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    // Mantido para compatibilidade interna do seu mod, caso precise.
    public String getMaterialName() {
        return "Amethyst";
    }
}