/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.MathHelper
 */
package danger.orespawn;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelIsland
extends ModelBase {
    private float wingspeed = 1.0f;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;

    public ModelIsland(float f) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Shape1 = new ModelRenderer((ModelBase)this, 0, 0);
        this.Shape1.addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.Shape1.setRotationPoint(0.0f, 16.0f, 0.0f);
        this.Shape1.setTextureSize(64, 32);
        this.Shape1.mirror = true;
        this.setRotation(this.Shape1, 0.0f, 0.0f, 0.0f);
        this.Shape2 = new ModelRenderer((ModelBase)this, 32, 0);
        this.Shape2.addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.Shape2.setRotationPoint(0.0f, 16.0f, 0.0f);
        this.Shape2.setTextureSize(64, 32);
        this.Shape2.mirror = true;
        this.setRotation(this.Shape2, 0.7853982f, 0.7853982f, 0.7853982f);
        this.Shape3 = new ModelRenderer((ModelBase)this, 32, 16);
        this.Shape3.addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.Shape3.setRotationPoint(0.0f, 16.0f, 0.0f);
        this.Shape3.setTextureSize(64, 32);
        this.Shape3.mirror = true;
        this.setRotation(this.Shape3, 0.7853982f, 0.7853982f, 0.7853982f);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        float newangle = 0.0f;
        this.Shape1.rotateAngleX = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.05f * this.wingspeed)) * (float)Math.PI;
        this.Shape1.rotateAngleY = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.051f * this.wingspeed)) * (float)Math.PI;
        this.Shape1.rotateAngleZ = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.052f * this.wingspeed)) * (float)Math.PI;
        this.Shape2.rotateAngleX = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.053f * this.wingspeed)) * (float)Math.PI;
        this.Shape2.rotateAngleY = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.054f * this.wingspeed)) * (float)Math.PI;
        this.Shape2.rotateAngleZ = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.055f * this.wingspeed)) * (float)Math.PI;
        this.Shape3.rotateAngleX = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.056f * this.wingspeed)) * (float)Math.PI;
        this.Shape3.rotateAngleY = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.057f * this.wingspeed)) * (float)Math.PI;
        this.Shape3.rotateAngleZ = newangle = net.minecraft.util.math.MathHelper.cos((float)(f2 * 0.058f * this.wingspeed)) * (float)Math.PI;
        this.Shape1.render(f5);
        this.Shape2.render(f5);
        this.Shape3.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
    }
}

