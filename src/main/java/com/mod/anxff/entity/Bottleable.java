package com.mod.anxff.entity;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Optional;

public interface Bottleable {
    boolean isFromBottle();

    void setFromBottle(boolean fromBottle);

    void copyDataToStack(ItemStack itemStack);

    void copyDataFromNbt(NbtCompound nbt);

    ItemStack getBottleItem();

    SoundEvent getBottledSound();

    @Deprecated
    static void copyDataToStack(MobEntity entity, ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (entity.hasCustomName()) {
            stack.setCustomName(entity.getCustomName());
        }
        if (entity.isAiDisabled()) {
            nbtCompound.putBoolean("NoAI", entity.isAiDisabled());
        }
        if (entity.isSilent()) {
            nbtCompound.putBoolean("Silent", entity.isSilent());
        }
        if (entity.hasNoGravity()) {
            nbtCompound.putBoolean("NoGravity", entity.hasNoGravity());
        }
        if (entity.isGlowingLocal()) {
            nbtCompound.putBoolean("Glowing", entity.isGlowingLocal());
        }
        if (entity.isInvulnerable()) {
            nbtCompound.putBoolean("Invulnerable", entity.isInvulnerable());
        }
        nbtCompound.putFloat("Health", entity.getHealth());
    }

    @Deprecated
    static void copyDataFromNbt(MobEntity entity, NbtCompound nbt) {
        if (nbt.contains("NoAI")) {
            entity.setAiDisabled(nbt.getBoolean("NoAI"));
        }
        if (nbt.contains("Silent")) {
            entity.setSilent(nbt.getBoolean("Silent"));
        }
        if (nbt.contains("NoGravity")) {
            entity.setNoGravity(nbt.getBoolean("NoGravity"));
        }
        if (nbt.contains("Glowing")) {
            entity.setGlowing(nbt.getBoolean("Glowing"));
        }
        if (nbt.contains("Invulnerable")) {
            entity.setInvulnerable(nbt.getBoolean("Invulnerable"));
        }
        if (nbt.contains("Health", NbtElement.NUMBER_TYPE)) {
            entity.setHealth(nbt.getFloat("Health"));
        }
    }

    static Optional<ActionResult> tryBottle(PlayerEntity player, Hand hand, FireflyEntity entity) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.GLASS_BOTTLE && entity.isAlive()) {
            entity.playSound(((Bottleable)entity).getBottledSound(), 1.0f, 0.5f);
            ItemStack itemStack2 = ((Bottleable)entity).getBottleItem();
            ((Bottleable)entity).copyDataToStack(itemStack2);
            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, player, itemStack2, false);
            player.setStackInHand(hand, itemStack3);
            World world = entity.getWorld();
            // advancement stuff
            /*
            if (!world.isClient) {
                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)player, itemStack2);
            }
             */
            entity.discard();
            return Optional.of(ActionResult.success(world.isClient));
        }
        return Optional.empty();
    }
}
