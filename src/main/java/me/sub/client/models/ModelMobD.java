package me.sub.client.models;

import me.sub.common.entity.EntityMobD;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelMobD extends ModelBase {
    private final ModelRenderer body_hidden;
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer crown;
    public ModelRenderer[] plate = new ModelRenderer[4];

    public ModelMobD() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -6.0F, -4.0F, 8, 8, 8, -0.25F);
        this.crown = new ModelRenderer(this, 0, 16);
        this.crown.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.crown.addBox(-4.0F, -7.0F, -4.0F, 8, 9, 8, 0.0F);
        this.body = new ModelRenderer(this, 0, 33);
        this.body.addBox(-2.0F, 2.0F, -2.0F, 4, 20, 4, 0.0F);

        this.body_hidden = new ModelRenderer(this, 0, 0);
        this.body_hidden.addBox(0, 0, 0, 0, 0, 0, 0.0F);


        for (int i = 0; i < 4; i++) {
            this.plate[i] = new ModelRenderPlate(this, 16, 33);
            this.plate[i].addBox(-5.0F, 6.0F, 4.0F, 10, 18, 2, 0.0F);
            setRotateAngle(this.plate[i], 0.5235988F, getAngle(i), 0.0F);
        }
        this.head.addChild(this.crown);
        for (int i = 0; i < 4; i++) {
            this.body_hidden.addChild(this.plate[i]);
        }
    }

    private float getAngle(int i) {
        float a = 0.0F;
        switch (i) {
            case 0:
            default:
                break;
            case 1:
                a = (float) Math.toRadians(90.0D);
                break;
            case 2:
                a = (float) Math.toRadians(180.0D);
                break;
            case 3:
                a = (float) Math.toRadians(270.0D);
        }
        return a;
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        if (entityIn instanceof EntityMobD) {
            EntityMobD entityMobD = (EntityMobD) entityIn;
            float offsetValue;

            if (entityMobD.isHiding()) {
                offsetValue = -0.07F;
                System.out.println("sdfsdfsdfd");
                body_hidden.rotateAngleY = entityIn.world.getTotalWorldTime();
            } else {
                offsetValue = -0.09F;
            }

            GlStateManager.translate(0.0F, -0.6F, 0.0F);
            float f = ageInTicks * 3.1415927F * -0.1F;
            this.head.rotateAngleX = (headPitch * 0.017453292F);
            this.head.rotateAngleY = (netHeadYaw * 0.017453292F);
            for (int i = 0; i < 4; i++) {
                this.plate[i].rotationPointY = MathHelper.cos((i * 8.5F + ageInTicks) * 0.5F);
                this.plate[i].rotationPointX = (MathHelper.cos(f) * 2.0F);
                this.plate[i].rotationPointZ = (MathHelper.sin(f) * 2.0F);
            }
            this.body.rotationPointY = (2.0F + MathHelper.cos(ageInTicks) * 0.25F);
            this.body.rotationPointX = (MathHelper.cos(f) * 2.0F);
            this.body.rotationPointZ = (-MathHelper.sin(f) * 2.0F);

            for (int i = 0; i < 4; i++) {
                ModelRenderPlate plate = (ModelRenderPlate) this.plate[i];
                plate.setHiding(entityMobD.isHiding());
            }

            GlStateManager.pushMatrix();
            float offset = MathHelper.cos(entityIn.ticksExisted * 0.1F) * offsetValue;
            GlStateManager.translate(0, -offset, 0);

            GlStateManager.pushMatrix();
            if (entityMobD.isHiding()) {
                GlStateManager.translate(0, 1.5, 0);
            }

            body.isHidden = entityMobD.isHiding();

            this.head.render(scale);
            GlStateManager.popMatrix();

            GlStateManager.popMatrix();

            this.body.render(scale);
            
            this.body_hidden.render(scale);
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getTicksElytraFlying() > 4;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        if (flag) {
            this.head.rotateAngleX = -((float) Math.PI / 4F);
        } else {
            this.head.rotateAngleX = headPitch * 0.017453292F;
        }

        if (entityIn.isSneaking()) {
            this.head.rotationPointY = 1.0F;
        } else {
            this.head.rotationPointY = 0.0F;
        }

    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

