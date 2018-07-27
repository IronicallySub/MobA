package me.sub.proxy;

import me.sub.client.renders.RenderGas;
import me.sub.client.renders.RenderMobA;
import me.sub.common.AObjects;
import me.sub.common.entity.EntityGas;
import me.sub.common.entity.EntityMobA;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
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

    @SubscribeEvent
    public static void freezePlayer(InputUpdateEvent e){
        MovementInput type = e.getMovementInput();
       if(Minecraft.getMinecraft().player.isPotionActive(AObjects.FREEZE)) {
           type.backKeyDown = false;
           type.forwardKeyDown = false;
           type.jump = false;
           type.leftKeyDown = false;
           type.moveForward = 0;
           type.sneak = false;
           type.rightKeyDown = false;
           type.moveStrafe = 0;
       }
    }
}
