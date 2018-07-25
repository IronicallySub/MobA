package me.sub.client.renders;

import me.sub.common.entity.EntityGas;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class RenderGas extends RenderSnowball<EntityGas> {

    public RenderGas(RenderManager renderManagerIn) {
        super(renderManagerIn, null, null);
    }

    @Override
    public void doRender(EntityGas entity, double x, double y, double z, float entityYaw, float partialTicks) {
        World wc = entity.world;

        for (int i = 0; i < 7; ++i) {
            wc.spawnParticle(EnumParticleTypes.CLOUD, entity.posX + (entity.world.rand.nextDouble() - 0.5D) * (double) entity.width, entity.posY + entity.world.rand.nextDouble() * (double) entity.height, entity.posZ + (entity.world.rand.nextDouble() - 0.5D) * (double) entity.width, 0D, 0.0D, 0D);
        }
    }
}
