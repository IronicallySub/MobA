package me.sub.proxy;

import me.sub.common.entity.EntityMobA;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {

    public void preInit(){}
    public void init(){
    }

    public static void setUpSpawns() {
        Biome sds;
        EntityRegistry.addSpawn(EntityMobA.class, 100, 3, 7, EnumCreatureType.WATER_CREATURE, Biomes.OCEAN);
        EntityRegistry.addSpawn(EntityMobA.class, 10, 4, 4, EnumCreatureType.WATER_CREATURE, Biomes.DEEP_OCEAN);
    }

    public void postInit() {
        setUpSpawns();
    }


}
