/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.BlockCrops
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.item.Item
 *  net.minecraft.util.TextureAtlasSprite
 */
package danger.orespawn;
import java.util.Random;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStrawberry
extends BlockCrops {
    private TextureAtlasSprite[] iconArray;

    public BlockStrawberry(int par1) {
    }

    public TextureAtlasSprite getIcon(int par1, int par2) {
        if (par2 < 7) {
            if (par2 >= 6) {
                par2 = 4;
            }
            return this.iconArray[par2 >> 1];
        }
        return this.iconArray[3];
    }

    public int quantityDropped(Random par1Random) {
        return 1 + par1Random.nextInt(5);
    }

    protected Item getSeed() {
        return OreSpawnMain.MyStrawberrySeed;
    }

    protected Item getCrop() {
        return OreSpawnMain.MyStrawberry;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap net.minecraft.client.renderer.texture.TextureMap) {
        this.iconArray = new TextureAtlasSprite[4];
        for (int i = 0; i < this.iconArray.length; ++i) {
            this.iconArray[i] = net.minecraft.client.renderer.texture.TextureMap.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:strawberry_" + i));
        }
    }
}

