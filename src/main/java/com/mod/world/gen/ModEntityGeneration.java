package com.mod.world.gen;

import com.mod.entity.FireflyEntity;
import com.mod.registry.EntityRegister;
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
				BiomeKeys.FLOWER_FOREST,
				BiomeKeys.SAVANNA,
				BiomeKeys.JUNGLE,
				BiomeKeys.BAMBOO_JUNGLE,
				BiomeKeys.SPARSE_JUNGLE,
				BiomeKeys.CHERRY_GROVE,
				BiomeKeys.TAIGA,
				BiomeKeys.SWAMP,
				BiomeKeys.MANGROVE_SWAMP
		), SpawnGroup.CREATURE, EntityRegister.FIREFLY, 10, 2, 5);
		SpawnRestriction.register(EntityRegister.FIREFLY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FireflyEntity::canSpawn);
	}
}
