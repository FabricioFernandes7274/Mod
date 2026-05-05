package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class EmeraldHoe extends ItemHoe {

    public EmeraldHoe(Item.ToolMaterial material) {
        super(material);
        this.maxStackSize = 1;
        // No OreSpawn, a durabilidade é alta para ferramentas de esmeralda
        this.setMaxDamage(1300); 
        this.setCreativeTab(CreativeTabs.TOOLS);
        
        // Nomes para registro e tradução
        this.setUnlocalizedName("emerald_hoe");
        this.setRegistryName("emerald_hoe");
    }

    // O OreSpawn definia um dano de 5 para a enxada de esmeralda
    // Para manter isso na 1.12.2, teríamos que mexer em AttributeModifiers,
    // mas o método abaixo é a forma simplificada de manter o espírito do mod:
    public String getMaterialName() {
        return "Emerald";
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        // Permite reparar a enxada usando esmeraldas na bigorna
        return repair.getItem() == net.minecraft.init.Items.EMERALD || super.getIsRepairable(toRepair, repair);
    }
}