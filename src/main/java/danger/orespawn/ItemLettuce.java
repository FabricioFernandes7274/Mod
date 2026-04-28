/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.item.ItemSeedFood
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSeedFood;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLettuce
extends ItemSeedFood {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public ItemLettuce(int par1, int par2, float par3, Block par4, Block par5) {
        super(par2, par3, par4, par5);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

