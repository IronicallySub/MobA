
package me.sub.client.renders.item;

import java.util.Random;

import lucraft.mods.lucraftcore.util.helper.LCRenderHelper;
import me.sub.common.AObjects;
import me.sub.common.entity.EntityRock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;

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

        for (int i = 0; i < 7; ++i) {
            entity.world.spawnParticle(EnumParticleTypes.FLAME, entity.posX + (entity.world.rand.nextDouble() - 0.5D) * (double) entity.width, entity.posY + entity.world.rand.nextDouble() * (double) entity.height, entity.posZ + (entity.world.rand.nextDouble() - 0.5D) * (double) entity.width, 0D, 0.0D, 0D);
        }
        
        Minecraft.getMinecraft().getRenderItem().renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.enableFog();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {

    }
}
