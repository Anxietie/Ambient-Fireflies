package com.mod.item;

import com.mod.entity.Bottleable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class EntityBottleItem extends GlassBottleItem {
	private final EntityType<?> entityType;
	private final SoundEvent emptyingSound;

	public EntityBottleItem(EntityType<?> type, SoundEvent emptyingSound, Item.Settings settings) {
		super(settings);
		this.entityType = type;
		this.emptyingSound = emptyingSound;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		BlockHitResult blockHitResult = EntityBottleItem.raycast(world, user, RaycastContext.FluidHandling.NONE);
		BlockPos blockPos = blockHitResult.getBlockPos();
		ItemStack itemStack = user.getStackInHand(hand);
		this.onEmptied(user, world, itemStack, blockPos);
		return TypedActionResult.success(EntityBottleItem.getEmptiedStack(itemStack, user));
	}

	public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
		if (world instanceof ServerWorld) {
			this.spawnEntity((ServerWorld)world, stack, pos);
			world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
			this.playEmptyingSound(world, pos);
		}
	}

	protected void playEmptyingSound(WorldAccess world, BlockPos pos) {
		world.playSound(null, pos, this.emptyingSound, SoundCategory.NEUTRAL, 1.0f, 1.0f);
	}

	private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
		Object entity = this.entityType.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
		if (entity instanceof Bottleable bottleable) {
			bottleable.copyDataFromNbt(stack.getOrCreateNbt());
			bottleable.setFromBottle(true);
		}
	}

	public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
		if (!player.getAbilities().creativeMode) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		return stack;
	}
}
