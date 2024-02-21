package com.mod.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.mod.AmbientFireflies.MODID;

public class PotionRegister {
	public static final Potion GLOW_POTION = new Potion(new StatusEffectInstance(StatusEffects.GLOWING, 2400));
	public static final Potion LONG_GLOW_POTION = new Potion(new StatusEffectInstance(StatusEffects.GLOWING, 4800));

	public static void registerPotions() {
		registerPotion("glow_potion", GLOW_POTION);
		registerPotion("long_glow_potion", LONG_GLOW_POTION);
		registerRecipe(Potions.AWKWARD, ItemRegister.GLOW_POWDER, GLOW_POTION);
		registerRecipe(GLOW_POTION, Items.REDSTONE, LONG_GLOW_POTION);
	}

	public static Potion registerPotion(String id, Potion potion) {
		return Registry.register(Registries.POTION, new Identifier(MODID, id), potion);
	}

	public static void registerRecipe(Potion input, Item item, Potion output) {
		BrewingRecipeRegistry.registerPotionRecipe(input, item, output);
	}
}
