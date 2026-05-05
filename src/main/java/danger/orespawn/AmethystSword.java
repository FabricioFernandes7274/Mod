package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AmethystSword extends ItemSword {

    public AmethystSword(Item.ToolMaterial material) {
        // Construtor limpo exigindo apenas o material da lâmina!
        super(material);
        
        // maxStackSize = 1 já é o padrão estabelecido pelo ItemSword.
        this.setMaxDamage(2000); // O nosso confiável substituto para setMaxDurability.
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public String getMaterialName() {
        return "Amethyst";
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        // Na 1.12.2, ambos os parâmetros são EntityLivingBase. 
        // O atacante é quem recebe o dano na durabilidade do item.
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 3500;
    }
}