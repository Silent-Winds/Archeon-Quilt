package fr.firstmegagame4.archeon.entities;

import com.mmodding.mmodding_lib.library.entities.goals.FlyingAroundFarGoal;
import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PickaxeItem;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class NatureCoreEntity extends HostileEntity {

	// TODO : Save The Phase Into An NBT
	private static final TrackedData<Integer> PHASE = DataTracker.registerData(NatureCoreEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private final AtomicBoolean hasShield = new AtomicBoolean();
	private final ServerBossBar bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.WHITE, BossBar.Style.PROGRESS);

	public NatureCoreEntity(EntityType<? extends NatureCoreEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new FlightMoveControl(this, 10, false);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new TargetGoal<>(this, PlayerEntity.class, true));
		this.goalSelector.add(1, new NatureCoreAttackGoal(this, 2.0f, false));
		this.goalSelector.add(2, new NatureCoreFlyingAround(this, 1.0));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(NatureCoreEntity.PHASE, 0);
	}

	public void onSummoned() {
		this.bossBar.setPercent(0.0f);
	}

	@Override
	public void onStartedTrackingBy(ServerPlayerEntity player) {
		super.onStartedTrackingBy(player);
		this.bossBar.addPlayer(player);
	}

	@Override
	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		super.onStoppedTrackingBy(player);
		this.bossBar.removePlayer(player);
	}

	private int getPhaseIndex() {
		return MathHelper.clamp(this.dataTracker.get(NatureCoreEntity.PHASE), 0, 4);
	}

	public Phase getPhase() {
		return Phase.values()[this.getPhaseIndex()];
	}

	public boolean switchPhase() {
		if (this.getPhase() != Phase.DEFEATED) {
			this.dataTracker.set(NatureCoreEntity.PHASE, MathHelper.clamp(this.getPhaseIndex() + 1, 0, 4));
			this.bossBar.setColor(switch (this.getPhase()) {
				case PETRIFIED -> BossBar.Color.WHITE;
				case NORMAL -> BossBar.Color.BLUE;
				case POISONOUS -> BossBar.Color.GREEN;
				case EXPLOSIVE -> BossBar.Color.RED;
				case DEFEATED -> BossBar.Color.PURPLE;
			});
			return true;
		}
		return false;
	}

	@Override
	protected void mobTick() {
		super.mobTick();

		if (this.getPhase().equals(Phase.PETRIFIED)) {
			this.setVelocity(Vec3d.ZERO);
		}

		if (this.getPhase().equals(Phase.DEFEATED)) {
			this.addVelocity(0.0, (0.3 - this.getVelocity().y) * 0.3, 0.0);
			this.setHealth(this.getHealth() - 1.0f);
		}

		if (this.isDead()) {
			new Explosion(this.world, this, this.getX(), this.getY(), this.getZ(), 5, false, Explosion.DestructionType.NONE).affectWorld(true);
			this.onDeath(DamageSource.GENERIC);
		}

		this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
	}

	@Override
	public void tickMovement() {
		super.tickMovement();

		if (!this.isOnGround() && this.getVelocity().y < 0.0) {
			this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
		}

		DefaultParticleType particleType = switch (this.getPhase()) {
			case PETRIFIED -> ParticleTypes.ASH;
			case NORMAL -> ParticleTypes.ENCHANT;
			case POISONOUS -> ParticleTypes.FALLING_SPORE_BLOSSOM;
			case EXPLOSIVE -> ParticleTypes.LAVA;
			case DEFEATED -> ParticleTypes.SMOKE;
		};

		for (int i = 0; i < 2; i++) {
			world.addParticle(
				particleType,
				this.getX() + this.random.nextFloat() - 0.5f,
				this.getY() + this.random.nextFloat() - 0.0f,
				this.getZ() + this.random.nextFloat() - 0.5f,
				(this.random.nextFloat() - 0.5f) * 0.5f,
				(this.random.nextFloat() - 0.5f) * 0.5f,
				(this.random.nextFloat() - 0.5f) * 0.5f
			);
		}
	}

	@Override
	public void setTarget(@Nullable LivingEntity target) {
		if (target instanceof PlayerEntity) {
			super.setTarget(target);
		}
	}

	public static DefaultAttributeContainer createNatureCoreAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0f)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0f)
			.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
			.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
			.build();
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(true);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean tryAttack(Entity target) {
		return super.tryAttack(target);
	}

	@Override
	public boolean isImmuneToExplosion() {
		return true;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (this.getPhase().equals(Phase.PETRIFIED)) {
			if (source.getAttacker() instanceof PlayerEntity entity) {
				if (entity.getMainHandStack().getItem() instanceof PickaxeItem) {
					this.switchPhase();
					return true;
				}
			}
		}
		else {
			if (!this.hasShield.get()) {
				if (this.getHealth() - amount <= 0.0f) {
					if (this.switchPhase()) {
						this.hasShield.set(true);
						this.setHealth(this.getPhase().equals(Phase.DEFEATED) ? 50.0f : 0.1f);
						if (!this.getPhase().equals(Phase.DEFEATED) && this.world instanceof ServerWorld serverWorld) {
							WorldUtils.repeatTaskUntil(serverWorld, 50, () -> this.heal(1.0f));
							WorldUtils.doTaskAfter(serverWorld, 50, () -> this.hasShield.set(false));
						}
						return true;
					}
				}
				else {
					return super.damage(source, amount);
				}
			}
		}
		return false;
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.BLOCK_BEACON_AMBIENT;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_BEACON_DEACTIVATE;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_BEACON_ACTIVATE;
	}

	public enum Phase {
		PETRIFIED,
		NORMAL,
		POISONOUS,
		EXPLOSIVE,
		DEFEATED
	}

	public static class NatureCoreAttackGoal extends MeleeAttackGoal {

		public NatureCoreEntity natureCore() {
			return (NatureCoreEntity) this.mob;
		}

		public NatureCoreAttackGoal(NatureCoreEntity pathAwareEntity, double speed, boolean pauseWhenMobIdle) {
			super(pathAwareEntity, speed, pauseWhenMobIdle);
		}

		@Override
		public boolean canStart() {
			return this.natureCore().getPhase() != Phase.PETRIFIED && this.natureCore().getPhase() != Phase.DEFEATED;
		}

		@Override
		public boolean shouldContinue() {
			return this.natureCore().getPhase() != Phase.PETRIFIED && this.natureCore().getPhase() != Phase.DEFEATED;
		}
	}

	public static class NatureCoreFlyingAround extends FlyingAroundFarGoal {

		public NatureCoreEntity natureCore() {
			return (NatureCoreEntity) this.mob;
		}

		public NatureCoreFlyingAround(NatureCoreEntity pathAwareEntity, double speed) {
			super(pathAwareEntity, speed);
		}

		@Override
		public boolean canStart() {
			return this.natureCore().getPhase() != Phase.PETRIFIED && this.natureCore().getPhase() != Phase.DEFEATED;
		}

		@Override
		public boolean shouldContinue() {
			return this.natureCore().getPhase() != Phase.PETRIFIED && this.natureCore().getPhase() != Phase.DEFEATED;
		}
	}
}
