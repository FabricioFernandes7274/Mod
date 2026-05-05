package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class AmethystAxe extends ItemAxe {

    public AmethystAxe(Item.ToolMaterial material) {
        // Na 1.12.2, o construtor do ItemAxe exige (Material, Dano de Ataque, Velocidade de Ataque).
        // Passamos 12.0F para o dano (seu weaponDamage antigo) e -3.0F para o cooldown padrão de machados pesados.
        super(material, 12.0F, -3.0F);
        
        // Na 1.12.2, o limite de pack de ferramentas já é 1 por padrão no Item.java.
        this.setMaxDamage(2000); // Antigo setMaxDurability
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public String getMaterialName() {
        return "Amethyst";
    }
}