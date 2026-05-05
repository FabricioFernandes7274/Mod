package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class AmethystShovel extends ItemSpade {

    public AmethystShovel(Item.ToolMaterial material) {
        // Construtor moderno da 1.12.2, exigindo apenas o material da ferramenta
        super(material);
        
        // maxStackSize = 1 já é o padrão estabelecido pela classe base
        this.setMaxDamage(2000); // O substituto tático do setMaxDurability
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    // Mantido intacto caso o seu esquadrão (outras partes do mod) precise dessa informação
    public String getMaterialName() {
        return "Amethyst";
    }
}