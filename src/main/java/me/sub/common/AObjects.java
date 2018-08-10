package me.sub.common;

import me.sub.Con17MobA;
import me.sub.common.entity.*;
import me.sub.common.items.*;
import me.sub.common.potions.EffectFrozen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class AObjects {

    public static final EntityEntry MOBD_BALL = EntityEntryBuilder.create().entity(EntityRock.class).id(new ResourceLocation(Con17MobA.MOD_ID, "moba_rock"), 3).name("moba_rock").tracker(80, 3, true).build();
    public static final Item PLATE = new ItemPlatePart();
    public static final Item ROCK_ITEM = new ItemRock();
    public static final Item CHARGE = new ItemCharge();

    public static final EffectFrozen FREEZE = new EffectFrozen();

    public static final EntityEntry MOBA = EntityEntryBuilder.create().entity(EntityMobA.class).egg(2, 7).id(new ResourceLocation(Con17MobA.MOD_ID, "mob_a"), 0).name("mob_a").tracker(80, 3, false).build();
    public static final EntityEntry MOBA_GAS = EntityEntryBuilder.create().entity(EntityGas.class).id(new ResourceLocation(Con17MobA.MOD_ID, "moba_gas"), 1).name("moba_gas").tracker(80, 3, true).build();
    public static final EntityEntry MOBD = EntityEntryBuilder.create().entity(EntityMobD.class).egg(7, 78).id(new ResourceLocation(Con17MobA.MOD_ID, "mob_d"), 2).name("mob_d").tracker(80, 3, false).build();
  

    public static ModDamageSources BITE_SOURCE = new ModDamageSources(" was bitten to death....");

    public static Item.ToolMaterial TONGUE_M = EnumHelper.addToolMaterial("TONGUE", 3, 1561, 8.0F, 5.0F, 100);

    public static final Item TONGUE = new ItemTongue();
    public static final Item TONGUE_SWORD = new ItemTougueSword(TONGUE_M);
    public static final Item SHEILD = new ItemSheila();

    public static ModDamageSources TOO_HOT = new ModDamageSources(" held a hot item too long...");
    public static ModDamageSources ROCK = new ModDamageSources(" got rocked to death...");

    public static SoundEvent BITE = setUpSound("bite");
    public static SoundEvent BURP = setUpSound("burp");

    private static SoundEvent setUpSound(String soundName) {
        return new SoundEvent(new ResourceLocation(Con17MobA.MOD_ID, soundName)).setRegistryName(soundName);
    }

    @SubscribeEvent
    public static void addEntities(RegistryEvent.Register<EntityEntry> e) {
        IForgeRegistry<EntityEntry> reg = e.getRegistry();
        reg.registerAll(MOBA, MOBA_GAS, MOBD, MOBD_BALL);
    }

    @SubscribeEvent
    public static void addItems(RegistryEvent.Register<Item> e) {
        IForgeRegistry<Item> reg = e.getRegistry();
        reg.registerAll(TONGUE, TONGUE_SWORD, SHEILD, PLATE, ROCK_ITEM, CHARGE);
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
        ModelLoader.setCustomModelResourceLocation(PLATE, 0, new ModelResourceLocation(PLATE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ROCK_ITEM, 0, new ModelResourceLocation(ROCK_ITEM.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(CHARGE, 0, new ModelResourceLocation(CHARGE.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void startHiding(LivingAttackEvent e) {
        if (e.getEntityLiving() instanceof EntityMobD) {
            EntityMobD mobD = (EntityMobD) e.getEntityLiving();
            if (!mobD.isHiding()) {
                if (e.getSource().getTrueSource() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) e.getSource().getTrueSource();
                    if (player.world.rand.nextInt(20) < 12 && !player.getHeldItemMainhand().isEmpty()) {
                        player.sendStatusMessage(new TextComponentString(TextFormatting.RED + TextFormatting.BOLD.toString() + "You item is getting too hot!"), true);
                        player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 100);
                    }
                }
            } else {
                if (e.getSource().getTrueSource() != null) {
                    e.getSource().getTrueSource().attackEntityFrom(AObjects.TOO_HOT, 2);
                    e.getSource().getTrueSource().setFire(1);
                }
                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void cancelDamage(LivingHurtEvent e) {
        if (e.getEntityLiving() instanceof EntityMobD) {
            EntityMobD mobD = (EntityMobD) e.getEntityLiving();
            if (mobD.world.rand.nextBoolean()) {
                mobD.setHiding(true);
                e.setCanceled(true);
            }
        }
    }

}
