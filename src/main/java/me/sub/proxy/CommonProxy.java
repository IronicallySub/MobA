package me.sub.proxy;

import com.google.common.collect.Lists;
import me.sub.common.EffectFrozen;
import me.sub.common.entity.EntityMobA;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;

public class CommonProxy {

    public void preInit(){}
    public void init(){
    }

    public static void setUpSpawns() {
        EntityRegistry.addSpawn(EntityMobA.class, 100, 3, 7, EnumCreatureType.WATER_CREATURE, Biomes.OCEAN);
        EntityRegistry.addSpawn(EntityMobA.class, 100, 3, 7, EnumCreatureType.WATER_CREATURE, Biomes.DEEP_OCEAN);
    }

    public void postInit() {
        setUpSpawns();
    }


}
