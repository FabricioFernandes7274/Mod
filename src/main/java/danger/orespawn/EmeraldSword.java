package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class EmeraldSword extends ItemSword {

    private final float attackDamage;

    public EmeraldSword(Item.ToolMaterial material) {
        super(material);
        // No OreSpawn original, a espada de esmeralda dá 15 de dano.
        // Como o ItemSword adiciona o dano do material automaticamente, 
        // ajustamos aqui para garantir o valor desejado.
        this.attackDamage = 15.0F; 
        
        this.maxStackSize = 1;
        this.setMaxDamage(1300); // Durabilidade
        this.setCreativeTab(CreativeTabs.COMBAT);
        
        this.setUnlocalizedName("emerald_sword");
        this.setRegistryName("emerald_sword");
    }

    /**
     * Retorna o nome do material para compatibilidade.
     */
    public String getMaterialName() {
        return "Emerald";
    }

    /**
     * Garante que o item seja reparável com esmeraldas na bigorna.
     */
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == net.minecraft.init.Items.EMERALD || super.getIsRepairable(toRepair, repair);
    }

    /**
     * Método de ataque para garantir que a durabilidade seja reduzida corretamente
     * usando a classe EntityLivingBase da 1.12.2.
     */
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }
}