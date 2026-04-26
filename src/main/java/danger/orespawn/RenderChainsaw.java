/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.//IItemRenderer removido na 1.12.2 - Object
 *  net.minecraftforge.client.//IItemRenderer removido na 1.12.2 - Object$ItemRenderType
 *  net.minecraftforge.client.//IItemRenderer removido na 1.12.2 - Object$ItemRendererHelper
 *  org.lwjgl.opengl.GL11
 */
package danger.orespawn;
import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.//IItemRenderer removido na 1.12.2 - Object;
import net.minecraftforge.fml.client.FMLClientHandler;

public class RenderChainsaw
implements //IItemRenderer removido na 1.12.2 - Object {
    protected ModelChainsaw modelChainsaw = new ModelChainsaw();
    private static final ResourceLocation texture = new net.minecraft.util.ResourceLocation("orespawn", "Chainsawtexture.png");

    public boolean handleRenderType(ItemStack item, //IItemRenderer removido na 1.12.2 - Object.ItemRenderType type) {
        switch (type) {
            case net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND: {
                return true;
            }
            case net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND: {
                return true;
            }
        }
        return false;
    }

    public boolean shouldUseRenderHelper(//IItemRenderer removido na 1.12.2 - Object.ItemRenderType type, ItemStack item, //IItemRenderer removido na 1.12.2 - Object.ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(//IItemRenderer removido na 1.12.2 - Object.ItemRenderType type, ItemStack item, Object ... data) {
        switch (type) {
            case net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND: {
                this.renderSwordF5(-3.0f, -3.0f, -2.0f, 0.25f);
                break;
            }
            case net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND: {
                this.renderSword(-10.0f, 1.0f, -4.0f, 0.25f);
                break;
            }
        }
    }

    private void renderSword(float x, float y, float z, float scale) {
        GL11.glPushMatrix();
        GL11.glRotatef((float)150.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        FMLClientHandler.instance().getClient().textureManager.bindTexture(texture);
        this.modelChainsaw.render();
        GL11.glPopMatrix();
    }

    private void renderSwordF5(float x, float y, float z, float scale) {
        GL11.glPushMatrix();
        GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glRotatef((float)-20.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)-20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        FMLClientHandler.instance().getClient().textureManager.bindTexture(texture);
        this.modelChainsaw.render();
        GL11.glPopMatrix();
    }
}

