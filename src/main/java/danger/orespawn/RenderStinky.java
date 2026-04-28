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

public class RenderStinky
extends RenderLiving {
    protected ModelStinky model = (ModelStinky)this.mainModel;
    private float scale = 1.0f;
    private static final ResourceLocation texture1 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture1.png");
    private static final ResourceLocation texture2 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture2.png");
    private static final ResourceLocation texture3 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture3.png");
    private static final ResourceLocation texture4 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture4.png");
    private static final ResourceLocation texture5 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture5.png");
    private static final ResourceLocation texture6 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture6.png");
    private static final ResourceLocation texture7 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture7.png");
    private static final ResourceLocation texture8 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture8.png");
    private static final ResourceLocation texture9 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture9.png");
    private static final ResourceLocation texture10 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture10.png");
    private static final ResourceLocation texture11 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture11.png");
    private static final ResourceLocation texture12 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture12.png");
    private static final ResourceLocation texture13 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture13.png");
    private static final ResourceLocation texture14 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture14.png");
    private static final ResourceLocation texture15 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture15.png");
    private static final ResourceLocation texture16 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture16.png");
    private static final ResourceLocation texture17 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture17.png");
    private static final ResourceLocation texture18 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture18.png");
    private static final ResourceLocation texture19 = new net.minecraft.util.ResourceLocation("orespawn", "Stinkytexture19.png");

    public RenderStinky(net.minecraft.client.renderer.entity.RenderManager renderManager, ModelStinky par1ModelBase, float par2, float par3) {
        super(renderManager, (ModelBase)par1ModelBase, par2 * par3);
        this.scale = par3;
    }

    public void renderStinky(Stinky par1EntityStinky, double par2, double par4, double par6, float par8, float par9) {
        super.doRender((EntityLiving)par1EntityStinky, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLivingBase par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderStinky((Stinky)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderStinky((Stinky)par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(Stinky par1Entity, float par2) {
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
    }

    protected void preRenderCallback(net.minecraft.entity.EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((Stinky)par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Stinky s = (Stinky)entity;
        int i = s.getSkin();
        if (i == 1) {
            return texture2;
        }
        if (i == 2) {
            return texture3;
        }
        if (i == 3) {
            return texture4;
        }
        if (i == 4) {
            return texture5;
        }
        if (i == 5) {
            return texture6;
        }
        if (i == 6) {
            return texture7;
        }
        if (i == 7) {
            return texture8;
        }
        if (i == 8) {
            return texture9;
        }
        if (i == 9) {
            return texture10;
        }
        if (i == 10) {
            return texture11;
        }
        if (i == 11) {
            return texture12;
        }
        if (i == 12) {
            return texture13;
        }
        if (i == 13) {
            return texture14;
        }
        if (i == 14) {
            return texture15;
        }
        if (i == 15) {
            return texture16;
        }
        if (i == 16) {
            return texture17;
        }
        if (i == 17) {
            return texture18;
        }
        if (i == 18) {
            return texture19;
        }
        return texture1;
    }
}

