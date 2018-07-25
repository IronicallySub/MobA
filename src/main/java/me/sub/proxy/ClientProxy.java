package me.sub.proxy;

import me.sub.client.renders.RenderGas;
import me.sub.client.renders.RenderMobA;
import me.sub.common.entity.EntityGas;
import me.sub.common.entity.EntityMobA;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(){
        super.preInit();
        RenderingRegistry.registerEntityRenderingHandler(EntityMobA.class, RenderMobA::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGas.class, RenderGas::new);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void postInit() {
        super.postInit();
    }
}
