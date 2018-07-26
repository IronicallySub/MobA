package me.sub.common;


import me.sub.MobA;
import me.sub.common.entity.EntityGas;
import me.sub.common.entity.EntityMobA;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class AObjects {

    public static final EffectFrozen FREEZE = new EffectFrozen();

    public static final EntityEntry MOBA = EntityEntryBuilder.create().entity(EntityMobA.class).egg(0,7).id(new ResourceLocation(MobA.MOD_ID, "mob_a"), 0).name("mob_a").tracker(80, 3, false).build();
    public static final EntityEntry MOBA_GAS = EntityEntryBuilder.create().entity(EntityGas.class).id(new ResourceLocation(MobA.MOD_ID, "moba_gas"), 1).name("moba_gas").tracker(80, 3, true).build();

    public static SoundEvent BITE = setUpSound("bite");

    private static SoundEvent setUpSound(String soundName) {
        return new SoundEvent(new ResourceLocation(MobA.MOD_ID, soundName)).setRegistryName(soundName);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> e) {
        IForgeRegistry<EntityEntry> reg = e.getRegistry();
        reg.registerAll(MOBA, MOBA_GAS);
    }

    @SubscribeEvent
    public static void addEntities(RegistryEvent.Register<Potion> e) {
        e.getRegistry().registerAll(FREEZE);
    }


    @SubscribeEvent
    public static void addSounds(RegistryEvent.Register<SoundEvent> e) {
        e.getRegistry().register(BITE);
    }

}
