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

public class RenderLeon
extends RenderLiving {
    protected ModelLeon model = (ModelLeon)this.mainModel;
    private float scale = 1.0f;
    private static final ResourceLocation texture = new net.minecraft.util.ResourceLocation("orespawn", "Leon.png");

    public RenderLeon(ModelLeon par1ModelBase, float par2, float par3) {
        super((ModelBase)par1ModelBase, par2 * par3);
        this.scale = par3;
    }

    public void renderLeon(Leon par1EntityLeon, double par2, double par4, double par6, float par8, float par9) {
        super.doRender((EntityLiving)par1EntityLeon, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderLeon((Leon)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderLeon((Leon)par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(Leon par1Entity, float par2) {
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
    }

    protected void preRenderCallback(net.minecraft.entity.EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((Leon)par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}

