package me.sub.client.renders;

import lucraft.mods.lucraftcore.util.helper.LCRenderHelper;
import me.sub.Con17MobA;
import me.sub.client.models.ModelMobD;
import me.sub.common.entity.EntityMobD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Random;

public class RenderMobD extends RenderLiving<EntityMobD> {

    private ResourceLocation texture = new ResourceLocation(Con17MobA.MOD_ID, "textures/entity/mob_d.png");

    public RenderMobD(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelMobD(), 0);
    }

    @Override
    public void doRender(EntityMobD entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        int scale = entity.getSpecialTime() / 5;
        
        
        if(entity.isAttackActive()){

            GlStateManager.pushMatrix();
            Minecraft mc = Minecraft.getMinecraft();
            Random rand = new Random(2);
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            float f = 0.2F;
            LCRenderHelper.setLightmapTextureCoords(240, 240);

            for (int j = 0; j < 2; j++) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y + 1, z);
                LCRenderHelper.setupRenderLightning();
                GlStateManager.translate(0, 0.5F, 0);
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.rotate((mc.player.ticksExisted + LCRenderHelper.renderTick) / 2F, 0, 1, 0);

                Vec3d color = new Vec3d(0.39F, 0.36F, 0);;
                for (int i = 0; i < 30; i++) {
                    GlStateManager.rotate((mc.player.ticksExisted + LCRenderHelper.renderTick) * i / 70F, 1, 1, 0);
                    LCRenderHelper.drawGlowingLine(new Vec3d((-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f), new Vec3d((-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f), 0.1F, color, 0);
                }

                GlStateManager.popMatrix();
                LCRenderHelper.drawGlowingLine(new Vec3d(-0.05F, 0, 0), new Vec3d(0.05F, 0, 0), 0.9F, color);
                LCRenderHelper.finishRenderLightning();
            }

            GlStateManager.popMatrix();


        }

    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     *
     * @param entity
     */
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMobD entity) {
        return texture;
    }
}
