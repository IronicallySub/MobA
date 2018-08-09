package me.sub.common;

import me.sub.Con17MobA;
import me.sub.common.entity.EntityGas;
import me.sub.common.entity.EntityMobA;
import me.sub.common.entity.EntityMobD;
import me.sub.common.items.ItemSheila;
import me.sub.common.items.ItemTongue;
import me.sub.common.items.ItemTougueSword;
import me.sub.common.potions.EffectFrozen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class AObjects {

    public static final EffectFrozen FREEZE = new EffectFrozen();

    public static final EntityEntry MOBA = EntityEntryBuilder.create().entity(EntityMobA.class).egg(0, 7).id(new ResourceLocation(Con17MobA.MOD_ID, "mob_a"), 0).name("mob_a").tracker(80, 3, false).build();
    public static final EntityEntry MOBA_GAS = EntityEntryBuilder.create().entity(EntityGas.class).id(new ResourceLocation(Con17MobA.MOD_ID, "moba_gas"), 1).name("moba_gas").tracker(80, 3, true).build();
    public static final EntityEntry MOBD = EntityEntryBuilder.create().entity(EntityMobD.class).egg(0, 78).id(new ResourceLocation(Con17MobA.MOD_ID, "mob_d"), 2).name("mob_d").tracker(80, 3, false).build();


    public static Item.ToolMaterial TONGUE_M = EnumHelper.addToolMaterial("TONGUE", 3, 1561, 8.0F, 5.0F, 100);


    public static final Item TONGUE = new ItemTongue();
    public static final Item TONGUE_SWORD = new ItemTougueSword(TONGUE_M);
    public static final Item SHEILD = new ItemSheila();

    public static SoundEvent BITE = setUpSound("bite");
    public static SoundEvent BURP = setUpSound("burp");

    private static SoundEvent setUpSound(String soundName) {
        return new SoundEvent(new ResourceLocation(Con17MobA.MOD_ID, soundName)).setRegistryName(soundName);
    }

    @SubscribeEvent
    public static void addEntities(RegistryEvent.Register<EntityEntry> e) {
        IForgeRegistry<EntityEntry> reg = e.getRegistry();
        reg.registerAll(MOBA, MOBA_GAS, MOBD);
    }

    @SubscribeEvent
    public static void addItems(RegistryEvent.Register<Item> e) {
        IForgeRegistry<Item> reg = e.getRegistry();
        reg.registerAll(TONGUE, TONGUE_SWORD, SHEILD);
    }

    @SubscribeEvent
    public static void addPotions(RegistryEvent.Register<Potion> e) {
        e.getRegistry().registerAll(FREEZE);
    }


    @SubscribeEvent
    public static void addSounds(RegistryEvent.Register<SoundEvent> e) {
        e.getRegistry().registerAll(BITE, BURP);
    }

    @SubscribeEvent
    public static void addModels(ModelBakeEvent e) {
        ModelLoader.setCustomModelResourceLocation(TONGUE, 0, new ModelResourceLocation(TONGUE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(TONGUE_SWORD, 0, new ModelResourceLocation(TONGUE_SWORD.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(SHEILD, 0, new ModelResourceLocation(SHEILD.getRegistryName(), "inventory"));

    }

}
