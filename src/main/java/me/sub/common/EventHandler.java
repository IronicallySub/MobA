package me.sub.common;

import me.sub.client.renders.item.RenderCaliSheild;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void removePotion(LivingEvent.LivingUpdateEvent e) {
        if (!(e.getEntityLiving() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) e.getEntityLiving();
        if (player.ticksExisted % 200 == 0) {
            if (player.isPotionActive(AObjects.FREEZE)) {
                player.removePotionEffect(AObjects.FREEZE);
            }
        }
    }

    @SubscribeEvent
    public static void renderSheild(ModelBakeEvent e) {
        AObjects.SHEILD.setTileEntityItemStackRenderer(new RenderCaliSheild());
    }

}
