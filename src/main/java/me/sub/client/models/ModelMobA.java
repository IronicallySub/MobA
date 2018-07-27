package me.sub.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import java.util.Random;

public class ModelMobA extends ModelBase
{
    ModelRenderer head;
    ModelRenderer tongue;
    ModelRenderer mouth_rt;
    ModelRenderer mouth_lt;
    ModelRenderer mouth_rb;
    ModelRenderer mouth_lb;
    ModelRenderer vines;

    public ModelMobA()
    {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 10, 0.0F);
        this.mouth_lt = new ModelRenderer(this, 0, 18);
        this.mouth_lt.mirror = true;
        this.mouth_lt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mouth_lt.addBox(0.0F, -6.0F, -20.0F, 6, 6, 20, 0.0F);
        this.mouth_rt = new ModelRenderer(this, 0, 18);
        this.mouth_rt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mouth_rt.addBox(-6.0F, -6.0F, -20.0F, 6, 6, 20, 0.0F);
        this.mouth_lb = new ModelRenderer(this, 0, 44);
        this.mouth_lb.mirror = true;
        this.mouth_lb.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mouth_lb.addBox(0.0F, 0.0F, -20.0F, 6, 6, 20, 0.0F);
        this.mouth_rb = new ModelRenderer(this, 0, 44);
        this.mouth_rb.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mouth_rb.addBox(-6.0F, 0.0F, -20.0F, 6, 6, 20, 0.0F);
        this.vines = new ModelRenderer(this, 36, 0);
        this.vines.setRotationPoint(0.0F, 0.0F, 10.0F);
        this.vines.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 8, 0.0F);
        this.tongue = new ModelRenderer(this, 0, 70);
        this.tongue.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tongue.addBox(-0.5F, -40.0F, -0.5F, 1, 40, 1, 0.0F);
        setRotateAngle(this.tongue, 1.5707964F, 0.0F, 0.0F);
        this.head.addChild(this.mouth_rt);
        this.head.addChild(this.mouth_rb);
        this.head.addChild(this.mouth_lb);
        this.head.addChild(this.mouth_lt);
        this.head.addChild(this.vines);
        this.head.addChild(this.tongue);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        Random rng = new Random(entityIn.getEntityId());
        float time = entityIn.ticksExisted + limbSwingAmount + rng.nextInt(10000000);
        this.head.rotateAngleX = (headPitch * 0.017453292F);
        this.head.rotateAngleY = (netHeadYaw * 0.017453292F);
        this.head.offsetZ = Math.max(0.0F, (float)Math.sin(time * 0.2D) * 0.7853982F);
        this.mouth_lt.rotateAngleY = Math.min(0.0F, -((float)Math.abs(Math.sin(time * -0.1D)) * 0.7853982F));
        this.mouth_lt.rotateAngleX = Math.min(0.0F, -((float)Math.abs(Math.sin(time * -0.1D)) * 0.3926991F));
        this.mouth_rt.rotateAngleY = Math.max(0.0F, (float)Math.abs(Math.sin(time * 0.1D)) * 0.7853982F);
        this.mouth_rt.rotateAngleX = Math.min(0.0F, -((float)Math.abs(Math.sin(time * 0.1D)) * 0.3926991F));
        this.mouth_lb.rotateAngleY = Math.min(0.0F, -((float)Math.abs(Math.sin(time * 0.1D)) * 0.7853982F));
        this.mouth_lb.rotateAngleX = Math.max(0.0F, (float)Math.abs(Math.sin(time * -0.1D)) * 0.3926991F);
        this.mouth_rb.rotateAngleY = Math.max(0.0F, (float)Math.abs(Math.sin(time * 0.1D)) * 0.7853982F);
        this.mouth_rb.rotateAngleX = Math.max(0.0F, (float)Math.abs(Math.sin(time * 0.1D)) * 0.3926991F);
        this.tongue.offsetZ = Math.max(0.0F, (float) Math.sin(time * 0.4D) * 0.3926991F);
        this.head.render(scale);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX= x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
