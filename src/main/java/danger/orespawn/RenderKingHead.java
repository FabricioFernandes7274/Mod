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
 */
package danger.orespawn;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderKingHead
extends RenderLiving {
    public RenderKingHead(net.minecraft.client.renderer.entity.RenderManager renderManager, ModelTheKing par1ModelBase, float par2, float par3) {
        super(renderManager, (ModelBase)par1ModelBase, par2 * par3);
    }

    public void renderKingHead(KingHead par1EntityKingHead, double par2, double par4, double par6, float par8, float par9) {
    }

    public void doRender(EntityLivingBase par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
    }

    protected void preRenderScale(KingHead par1Entity, float par2) {
    }

    protected void preRenderCallback(net.minecraft.entity.EntityLivingBase par1EntityLiving, float par2) {
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}

