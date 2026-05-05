package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class BigHammer extends ItemSword {

    public BigHammer(Item.ToolMaterial material) {
        super(material);
        this.setMaxDamage(9000);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public String getMaterialName() {
        return "AMETHYST";
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (target != null && !target.world.isRemote) {
            // Lança o alvo para cima ao ser atingido
            target.addVelocity(0.0D, (double)Math.abs(target.world.rand.nextFloat() * 2.0F / 3.0F), 0.0D);
            target.velocityChanged = true; // Garante que o cliente seja notificado do empurrão
        }
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 3000;
    }
}