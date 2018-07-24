package me.sub.client.renders;

import me.sub.client.models.MobA;
import me.sub.common.entity.EntityMobA;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderMobA extends RenderLiving<EntityMobA> {

    private ResourceLocation texture = new ResourceLocation(me.sub.MobA.MOD_ID,"textures/entity/mob_a.png");

    public RenderMobA(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new MobA(), 0);
    }

    @Override
    public void doRender(EntityMobA entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }



    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     *
     * @param entity
     */
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMobA entity) {
        return texture;
    }


    /**
     * Gets the value between start and end according to pct
     */
    private double interpolateValue(double start, double end, double pct) {
        return start + (end - start) * pct;
    }

}
