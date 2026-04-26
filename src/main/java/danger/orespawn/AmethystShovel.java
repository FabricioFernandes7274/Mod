/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemSpade
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AmethystShovel
extends ItemSpade {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public AmethystShovel(int par1, Item.ToolMaterial par2) {
        super(par2);
        this.maxStackSize = 1;
        this.setMaxDurability(2000);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public int getDamageVsEntity(Entity par1Entity) {
        return 5;
    }

    public String getMaterialName() {
        return "Amethyst";
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5));
    }
}

