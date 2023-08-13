package com.mod.anxff.entity;

import com.mod.anxff.registry.ItemRegister;
import com.mod.anxff.registry.ParticleRegister;
import com.mod.anxff.registry.SoundRegister;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FireflyEntity extends PathAwareEntity implements Flutterer, Bottleable {
    private static final TrackedData<Integer> ILLUMINATING_TICKS = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> FROM_BOTTLE = DataTracker.registerData(FireflyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final int MAX_GLOWING_TICKS = 60; // 2 seconds
    private static final int MIN_GLOWING_TICKS = 10; // 1/2 second

    public FireflyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 0, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ILLUMINATING_TICKS, 0);
        this.dataTracker.startTracking(FROM_BOTTLE, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FlyRandomlyGoal(this));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (spawnReason == SpawnReason.BUCKET) {
            return entityData;
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) return false;
        if (this.isIlluminant()) this.resetLuminance();
        return super.damage(source, amount);
    }

    @Override
    public void tickMovement() {
        if (this.isIlluminant()) {
            this.decrementIlluminatingTicks();
            if (this.getLuminance() == 3 && this.random.nextInt(20) == 0)
                this.getEntityWorld().addParticle(ParticleRegister.FIREFLY_EMISSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
        else if (this.getWorld().isNight()) {
            if (this.age % 20 == 0 && this.random.nextInt(6) == 0) // 1 in 6 chance to glow every second
                this.setIlluminatingTicks(MAX_GLOWING_TICKS/*chooseRandomIlluminationTime()*/);
        }
        super.tickMovement();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world){
            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.EVENTS;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {}

    @Override
    protected void tickCramming() {}

    public static DefaultAttributeContainer.Builder createFireflyAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 0.1)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setIlluminatingTicks(nbt.getInt("Illuminant"));
        this.setFromBottle(nbt.getBoolean("FromBucket"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Illuminant", this.dataTracker.get(ILLUMINATING_TICKS));
        nbt.putBoolean("FromBottle", this.dataTracker.get(FROM_BOTTLE));
    }

    public static boolean canSpawn(EntityType<FireflyEntity> entityType, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (pos.getY() < world.getSeaLevel()) return false;
        if (world.getLightLevel(LightType.BLOCK, pos) > 9 || isNightTime(world)) return false;
        return FireflyEntity.canMobSpawn(entityType, world, spawnReason, pos, random);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return Bottleable.tryBottle(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegister.FIREFLY_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegister.FIREFLY_AMBIENT;
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBottle();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBottle() && !this.hasCustomName();
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {}

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height / 2.0f;
    }

    @Override
    public int getLimitPerChunk() {
        return 8;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    public int getLuminance() {
        if (!this.isIlluminant())
            return 0;
        int ticks = this.getIlluminatingTicks();
        if (MAX_GLOWING_TICKS - ticks < 5 || 5 - ticks > 0)
            return 1;
        if (MAX_GLOWING_TICKS - ticks < 10 || 10 - ticks > 0)
            return 2;
        return 3;
    }

    public boolean isIlluminant() {
        return this.dataTracker.get(ILLUMINATING_TICKS) > 0;
    }

    private int getIlluminatingTicks() {
        return this.dataTracker.get(ILLUMINATING_TICKS);
    }

    private void setIlluminatingTicks(int ticks) {
        this.dataTracker.set(ILLUMINATING_TICKS, ticks);
    }

    private void resetLuminance() {
        setIlluminatingTicks(0);
    }

    private int chooseRandomIlluminationTime() {
        return this.random.nextBetween(MIN_GLOWING_TICKS, MAX_GLOWING_TICKS);
    }

    private void decrementIlluminatingTicks() {
        this.dataTracker.set(ILLUMINATING_TICKS, this.getIlluminatingTicks() - 1);
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround(); // always flying
    }

    @Override
    public boolean isFromBottle() {
        return this.dataTracker.get(FROM_BOTTLE);
    }

    @Override
    public void setFromBottle(boolean fromBottle) {
        this.dataTracker.set(FROM_BOTTLE, true);
    }

    @Override
    public void copyDataToStack(ItemStack itemStack) {
        Bottleable.copyDataToStack(this, itemStack);
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bottleable.copyDataFromNbt(this, nbt);
    }

    @Override
    public ItemStack getBottleItem() {
        return new ItemStack(ItemRegister.FIREFLY_BOTTLE);
    }

    @Override
    public SoundEvent getBottledSound() {
        return SoundRegister.FIREFLY_CATCH;
    }

    private static boolean isNightTime(WorldAccess world) {
        return (24000 - (world.getLunarTime() % 24000)) > 12000;
    }

    static class FlyRandomlyGoal extends Goal {
        private final FireflyEntity firefly;

        public FlyRandomlyGoal(FireflyEntity firefly) {
            this.firefly = firefly;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return firefly.navigation.isIdle() && firefly.random.nextInt(6) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return firefly.navigation.isFollowingPath();
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2 = firefly.getRotationVec(0.0f);
            Vec3d vec3d3 = AboveGroundTargeting.find(firefly, 4, 2, vec3d2.x, vec3d2.z, 1.5707964f, 3, 1);
            if (vec3d3 != null)
                return vec3d3;
            return NoPenaltySolidTargeting.find(firefly, 4, 2, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }

        @Override
        public void start() {
            Vec3d vec = this.getRandomLocation();
            if (vec != null)
                this.firefly.navigation.startMovingAlong(firefly.navigation.findPathTo(BlockPos.ofFloored(vec), 1), 0.5);
        }
    }
}
