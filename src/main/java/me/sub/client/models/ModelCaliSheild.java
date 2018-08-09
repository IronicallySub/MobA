package me.sub.client.models;

import me.sub.Con17MobA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ModelCaliSheild extends ModelShield {

    public ModelRenderer plate;
    private ResourceLocation texture = new ResourceLocation(Con17MobA.MOD_ID, "textures/entity/mob_d.png");

    public ModelCaliSheild() {
        this.plate = new ModelRenderer(this, 16, 33);
        this.plate.addBox(-5.0F, 6.0F, 4.0F, 10, 18, 2, 0.0F);
        setRotateAngle(this.plate, 0.5235988F, 0, 0.0F);
    }

    @Override
    public void render() {
        GlStateManager.pushMatrix();
        super.render();

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GlStateManager.translate(-2, 0, 1);
        plate.render(0.0625F);
        GlStateManager.popMatrix();
    }


    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
