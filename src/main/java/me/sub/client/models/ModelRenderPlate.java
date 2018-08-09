package me.sub.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class ModelRenderPlate extends ModelRenderer {

    private boolean isHiding;

    public ModelRenderPlate(ModelBase model, String boxNameIn) {
        super(model, boxNameIn);
    }

    public ModelRenderPlate(ModelBase model) {
        super(model);
    }

    public ModelRenderPlate(ModelBase model, int texOffX, int texOffY) {
        super(model, texOffX, texOffY);
    }

    @Override
    public void render(float scale) {
        GlStateManager.pushMatrix();
        if (isHiding) {
            GlStateManager.translate(0, 0.6, 0);
        }
        super.render(scale);
        GlStateManager.popMatrix();
    }

    public boolean isHiding() {
        return isHiding;
    }

    public void setHiding(boolean hiding) {
        isHiding = hiding;
    }
}
