
package me.sub.client.renders.item;

import me.sub.common.AObjects;
import me.sub.common.entity.EntityRock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;

public class RenderRock extends RenderSnowball<EntityRock> {

    public RenderRock(RenderManager renderManagerIn) {
        super(renderManagerIn, AObjects.ROCK_ITEM, Minecraft.getMinecraft().getRenderItem());
    }

    @Override
    public void doRender(EntityRock entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableFog();
        GlStateManager.disableLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.enableFog();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {

    }
}
