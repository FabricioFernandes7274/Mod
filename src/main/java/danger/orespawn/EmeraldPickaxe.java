package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EmeraldPickaxe extends ItemPickaxe {

    public EmeraldPickaxe(Item.ToolMaterial material) {
        // Na 1.12.2, passamos o material para o super
        super(material);
        this.maxStackSize = 1;
        this.setMaxDamage(1300); // Define a durabilidade
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setUnlocalizedName("emerald_pickaxe");
        this.setRegistryName("emerald_pickaxe");
    }

    /**
     * Chamado a cada tick enquanto o item está no inventário.
     * Corrigido para aplicar Silk Touch de forma segura.
     */
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) { // Apenas no lado do servidor
            // Verifica se já tem Toque de Seda para não ficar reaplicando à toa
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) <= 0) {
                stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
            }
        }
    }

    /**
     * Chamado quando o item é criado ou craftado. 
     * Adiciona o encantamento imediatamente.
     */
    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) <= 0) {
            stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
        }
    }

    public String getMaterialName() {
        return "Emerald";
    }

    // O OreSpawn usava um dano base de 10 para a picareta também
    @Override
    public boolean hitEntity(ItemStack stack, net.minecraft.entity.EntityLivingBase target, net.minecraft.entity.EntityLivingBase attacker) {
        stack.damageItem(1, attacker); // Gasta durabilidade ao bater
        return true;
    }
}