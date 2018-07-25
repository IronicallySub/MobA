package me.sub.common;


import me.sub.MobA;
import me.sub.common.entity.EntityGas;
import me.sub.common.entity.EntityMobA;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class AObjects {

    public static final EntityEntry MOBA = EntityEntryBuilder.create().entity(EntityMobA.class).id(new ResourceLocation(MobA.MOD_ID, "mob_a"), 0).name("mob_a").tracker(80, 3, false).build();
    public static final EntityEntry MOBA_GAS = EntityEntryBuilder.create().entity(EntityGas.class).id(new ResourceLocation(MobA.MOD_ID, "moba_gas"), 1).name("moba_gas").tracker(80, 3, true).build();


    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> e) {
        IForgeRegistry<EntityEntry> reg = e.getRegistry();
        reg.registerAll(MOBA, MOBA_GAS);
    }

}
