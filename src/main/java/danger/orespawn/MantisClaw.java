/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MantisClaw
extends ItemSword {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int weaponDamage;
    private final Item.ToolMaterial toolMaterial;

    public MantisClaw(int par1, Item.ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        this.toolMaterial = par2EnumToolMaterial;
        this.weaponDamage = 10;
        this.maxStackSize = 1;
        this.setMaxDurability(1000);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public String getMaterialName() {
        return "AMETHYST";
    }

    public boolean hitEntity(ItemStack par1ItemStack, net.minecraft.entity.EntityLivingBase par2EntityLiving, net.minecraft.entity.EntityLivingBase par3EntityLiving) {
        int var2 = 5;
        if (par2EntityLiving != null && par3EntityLiving != null && !par2EntityLiving.world.isRemote) {
            par2EntityLiving.heal(-1.0f);
            par3EntityLiving.heal(1.0f);
        }
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 3000;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

