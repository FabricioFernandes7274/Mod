/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.entity.RenderLiving
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package danger.orespawn;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderPitchBlack
extends RenderLiving {
    protected ModelPitchBlack model = (ModelPitchBlack)this.mainModel;
    private float scale = 1.0f;
    private static final ResourceLocation texture = new net.minecraft.util.ResourceLocation("orespawn", "PitchBlacktexture.png");

    public RenderPitchBlack(ModelPitchBlack par1ModelBase, float par2, float par3) {
        super((ModelBase)par1ModelBase, par2 * par3);
        this.scale = par3;
    }

    public void renderPitchBlack(PitchBlack par1EntityPitchBlack, double par2, double par4, double par6, float par8, float par9) {
        super.doRender((EntityLiving)par1EntityPitchBlack, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderPitchBlack((PitchBlack)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderPitchBlack((PitchBlack)par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(PitchBlack par1Entity, float par2) {
        float pscale = par1Entity.getPitchBlackScale();
        GL11.glScalef((float)pscale, (float)pscale, (float)pscale);
    }

    protected void preRenderCallback(net.minecraft.entity.EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((PitchBlack)par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}

