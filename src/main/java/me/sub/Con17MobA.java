package me.sub;

import me.sub.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Con17MobA.MOD_ID, name = Con17MobA.MOD_NAME, version = Con17MobA.VERSION)
public class Con17MobA {

    public static final String MOD_ID = "con_mob_a";
    public static final String MOD_NAME = "Con17MobA";
    public static final String VERSION = "1.0-SNAPSHOT";

    @SidedProxy(clientSide = "me.sub.proxy.ClientProxy", serverSide = "me.sub.proxy.CommonProxy")
    private static CommonProxy proxy;

    @Mod.Instance(MOD_ID)
    public static Con17MobA INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
