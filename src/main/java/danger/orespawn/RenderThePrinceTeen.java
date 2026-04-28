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

public class RenderThePrinceTeen
extends RenderLiving {
    protected ModelThePrinceTeen model = (ModelThePrinceTeen)this.mainModel;
    private float scale = 1.0f;
    private static final ResourceLocation texture = new net.minecraft.util.ResourceLocation("orespawn", "PrinceTeentexture.png");

    public RenderThePrinceTeen(net.minecraft.client.renderer.entity.RenderManager renderManager, ModelThePrinceTeen par1ModelBase, float par2, float par3) {
        super(renderManager, (ModelBase)par1ModelBase, par2 * par3);
        this.scale = par3;
    }

    public void renderThePrinceTeen(ThePrinceTeen par1EntityThePrinceTeen, double par2, double par4, double par6, float par8, float par9) {
        super.doRender((EntityLiving)par1EntityThePrinceTeen, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLivingBase par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderThePrinceTeen((ThePrinceTeen)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderThePrinceTeen((ThePrinceTeen)par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(ThePrinceTeen par1Entity, float par2) {
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
    }

    protected void preRenderCallback(net.minecraft.entity.EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((ThePrinceTeen)par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}

