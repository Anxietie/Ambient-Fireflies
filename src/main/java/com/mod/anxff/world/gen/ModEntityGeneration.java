package com.mod.anxff.world.gen;

import com.mod.anxff.entity.FireflyEntity;
import com.mod.anxff.registry.EntityRegister;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntityGeneration {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                BiomeKeys.PLAINS,
                BiomeKeys.FOREST,
                BiomeKeys.BEACH,
                BiomeKeys.BIRCH_FOREST,
                BiomeKeys.DARK_FOREST,
                BiomeKeys.CHERRY_GROVE,
                BiomeKeys.TAIGA
        ), SpawnGroup.CREATURE, EntityRegister.FIREFLY, 8, 2, 5);
        SpawnRestriction.register(EntityRegister.FIREFLY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FireflyEntity::canSpawn);
    }
}
