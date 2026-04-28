/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemTool
 */
package danger.orespawn;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalShovel
extends ItemTool {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    public static final Set blocksEffectiveAgainst = Sets.newHashSet((Object[])new Block[]{Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.CLAY, Blocks.FARMLAND, Blocks.MYCELIUM, OreSpawnMain.CrystalGrass});

    public CrystalShovel(int par1, Item.ToolMaterial par2) {
        super(1.0f, par2, blocksEffectiveAgainst);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public boolean canHarvestBlock(Block par1Block) {
        return par1Block == Blocks.SNOW;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

