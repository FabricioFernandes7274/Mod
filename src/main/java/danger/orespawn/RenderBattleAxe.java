/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType
 *  net.minecraftforge.client.net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType$ItemRenderType
 *  net.minecraftforge.client.net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType$ItemRendererHelper
 *  org.lwjgl.opengl.GL11
 */
package danger.orespawn;
import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraftforge.fml.client.FMLClientHandler;

public class RenderBattleAxe
implements net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType {
    protected ModelBattleAxe modelBattleAxe = new ModelBattleAxe();
    private static final ResourceLocation texture = new net.minecraft.util.ResourceLocation("orespawn", "BattleAxetexture.png");

    public boolean handleRenderType(ItemStack item, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType type) {
        switch (type) {
            case THIRD_PERSON_RIGHT_HAND: {
                return true;
            }
            case FIRST_PERSON_RIGHT_HAND: {
                return true;
            }
        }
        return false;
    }

    public boolean shouldUseRenderHelper(net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType type, ItemStack item, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType type, ItemStack item, Object ... data) {
        switch (type) {
            case THIRD_PERSON_RIGHT_HAND: {
                this.renderSwordF5(3.0f, -8.0f, -2.0f, 0.35f);
                break;
            }
            case FIRST_PERSON_RIGHT_HAND: {
                this.renderSword(-2.0f, -4.0f, -6.0f, 0.35f);
                break;
            }
        }
    }

    private void renderSword(float x, float y, float z, float scale) {
        GL11.glPushMatrix();
        GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)190.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)15.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        FMLClientHandler.instance().getClient().textureManager.bindTexture(texture);
        this.modelBattleAxe.render();
        GL11.glPopMatrix();
    }

    private void renderSwordF5(float x, float y, float z, float scale) {
        GL11.glPushMatrix();
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.25f, (float)0.0f);
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        FMLClientHandler.instance().getClient().textureManager.bindTexture(texture);
        this.modelBattleAxe.render();
        GL11.glPopMatrix();
    }
}

