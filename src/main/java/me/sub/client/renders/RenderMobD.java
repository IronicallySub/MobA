package me.sub.client.renders;

import me.sub.Con17MobA;
import me.sub.client.models.ModelMobD;
import me.sub.common.entity.EntityMobD;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderMobD extends RenderLiving<EntityMobD> {

    private ResourceLocation texture = new ResourceLocation(Con17MobA.MOD_ID, "textures/entity/mob_d.png");

    public RenderMobD(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelMobD(), 0);
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
