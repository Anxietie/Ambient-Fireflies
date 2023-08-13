package com.mod.anxff.registry;

import com.mod.anxff.item.EntityBottleItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.mod.anxff.Fireflies.MODID;

public class ItemRegister {
    public static final Item FIREFLY_BOTTLE = new EntityBottleItem(EntityRegister.FIREFLY, SoundRegister.FIREFLY_RELEASE, new Item.Settings().maxCount(1));
    public static final Item FIREFLY_SPAWN_EGG = new SpawnEggItem(EntityRegister.FIREFLY, 0x000000, 0xFFFF00, new Item.Settings());

    public static void registerItems() {
        register("firefly_bottle", FIREFLY_BOTTLE);
        register("firefly_spawn_egg", FIREFLY_SPAWN_EGG);
    }

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.add(FIREFLY_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.addAfter(Items.GLASS_BOTTLE, FIREFLY_BOTTLE));
    }

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MODID, id), item);
    }
}
