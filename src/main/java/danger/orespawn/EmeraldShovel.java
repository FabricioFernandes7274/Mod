package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class EmeraldShovel extends ItemSpade {

    public EmeraldShovel(Item.ToolMaterial material) {
        // Na 1.12.2, o ItemSpade (ou ItemShovel) recebe o material no construtor
        super(material);
        this.maxStackSize = 1;
        this.setMaxDamage(1300); // Define a durabilidade de 1300 usos
        this.setCreativeTab(CreativeTabs.TOOLS);
        
        // Configurações de registro
        this.setUnlocalizedName("emerald_shovel");
        this.setRegistryName("emerald_shovel");
    }

    // O OreSpawn definia que a pá causava 5 de dano (2.5 corações)
    public String getMaterialName() {
        return "Emerald";
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        // Compatibilidade para reparar com esmeraldas na bigorna
        return repair.getItem() == net.minecraft.init.Items.EMERALD || super.getIsRepairable(toRepair, repair);
    }
}