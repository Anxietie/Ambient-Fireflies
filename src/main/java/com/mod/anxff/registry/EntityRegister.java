package com.mod.anxff.registry;

import com.mod.anxff.entity.FireflyEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.mod.anxff.Fireflies.MODID;

public class EntityRegister {
    public static final EntityType<FireflyEntity> FIREFLY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FireflyEntity::new)
            .dimensions(EntityDimensions.fixed(0.2f, 0.2f))
            .trackRangeBlocks(8)
            .build();

    public static void registerEntities() {
        register("firefly", FIREFLY);
        FabricDefaultAttributeRegistry.register(FIREFLY, FireflyEntity.createFireflyAttributes().build());
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(MODID, id), entity);
    }
}
