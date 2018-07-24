package me.sub;

import me.sub.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = MobA.MOD_ID, name = MobA.MOD_NAME, version = MobA.VERSION)
public class MobA {

    public static final String MOD_ID = "mob_a";
    public static final String MOD_NAME = "MobA";
    public static final String VERSION = "1.0-SNAPSHOT";

    @SidedProxy(clientSide = "me.sub.proxy.ClientProxy", serverSide = "me.sub.proxy.CommonProxy")
    private static CommonProxy proxy;

    @Mod.Instance(MOD_ID)
    public static MobA INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
