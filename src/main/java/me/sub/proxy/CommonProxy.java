package me.sub.proxy;

import com.google.common.collect.Lists;
import me.sub.common.entity.EntityMobA;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;

public class CommonProxy {

    public void preInit(){}
    public void init(){}

    public static void setUpSpawns() {
        Collection<Biome> biomes = ForgeRegistries.BIOMES.getValuesCollection();
        ArrayList<Biome> spawn = Lists.newArrayList();
        spawn.addAll(biomes);

        for (Biome biome : spawn) {
            if (biome != null && biome.getRegistryName().toString().contains("ocean")) {
                EntityRegistry.addSpawn(EntityMobA.class, 100, 3, 7, EnumCreatureType.WATER_CREATURE, biome);
            }
        }
    }

    public void postInit() {
        setUpSpawns();
    }
}
