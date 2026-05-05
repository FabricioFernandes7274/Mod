package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class EmeraldAxe extends ItemAxe {

    // No OreSpawn original, o Machado de Esmeralda tem status bem altos
    public EmeraldAxe(Item.ToolMaterial material) {
        // O super do ItemAxe na 1.12.2 pede o material, o dano e a velocidade
        // Valores aproximados do OreSpawn: Dano 10, Velocidade -3.0F
        super(material, 10.0F, -3.0F); 
        
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.TOOLS);
        // Define o nome interno (importante para a textura)
        this.setUnlocalizedName("emerald_axe");
        this.setRegistryName("emerald_axe");
    }

    // Mantém o nome do material para compatibilidade com outros mods (como Tinkers)
    public String getToolMaterialName() {
        return "EMERALD";
    }

    @Override
    public int getItemEnchantability() {
        return 15; // Valor padrão para ferramentas de esmeralda/diamante
    }

    // Se quiser que ele seja reparável com esmeraldas:
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == net.minecraft.init.Items.EMERALD || super.getIsRepairable(toRepair, repair);
    }
}