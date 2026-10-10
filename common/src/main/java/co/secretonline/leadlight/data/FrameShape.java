package co.secretonline.leadlight.data;

import co.secretonline.leadlight.component.WindowFrameContentsComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3d;
import org.jspecify.annotations.NonNull;

public enum FrameShape implements StringRepresentable {
	LARGE("large", HitProcessor::LARGE, true, false, false, false),
	VERTICAL("vertical", HitProcessor::VERTICAL, false, false, true, false),
	HORIZONTAL("horizontal", HitProcessor::HORIZONTAL, true, true, false, false),
	SQUARE("square", HitProcessor::SQUARE, false, false, true, true),
	CROSS("cross", HitProcessor::CROSS, true, true, true, false),
	DIAMOND("diamond", HitProcessor::DIAMOND, true, false, true, true);

	private final String prefix;
	private final HitProcessor hitProcessor;
	private final boolean hasCenterTopSlot;
	private final boolean hasCenterBottomSlot;
	private final boolean hasSideTopSlot;
	private final boolean hasSideBottomSlot;

	FrameShape(String prefix, HitProcessor hitProcessor, boolean hasCenterTopSlot, boolean hasCenterBottomSlot, boolean hasSideTopSlot, boolean hasSideBottomSlot) {
		this.prefix = prefix;
		this.hitProcessor = hitProcessor;
		this.hasCenterTopSlot = hasCenterTopSlot;
		this.hasCenterBottomSlot = hasCenterBottomSlot;
		this.hasSideTopSlot = hasSideTopSlot;
		this.hasSideBottomSlot = hasSideBottomSlot;
	}

	public String getPrefix() {
		return prefix;
	}

	public boolean hasCenterTopSlot() {
		return hasCenterTopSlot;
	}

	public boolean hasCenterBottomSlot() {
		return hasCenterBottomSlot;
	}

	public boolean hasSideTopSlot() {
		return hasSideTopSlot;
	}

	public boolean hasSideBottomSlot() {
		return hasSideBottomSlot;
	}

	@Override
	public @NonNull String getSerializedName() {
		return prefix;
	}

	public WindowFrameContentsComponent.Slot processHit(BlockHitResult blockHitResult) {
		return hitProcessor.getSlot(blockHitResult);
	}

	@FunctionalInterface
	public interface HitProcessor {
		WindowFrameContentsComponent.Slot getSlot(BlockHitResult blockHitResult);

		static Vector3d getRelativeCenterCoords(BlockHitResult hitResult) {
			BlockPos blockPos = hitResult.getBlockPos();

			double x = hitResult.getLocation().x - blockPos.getX();
			double y = hitResult.getLocation().y - blockPos.getY();
			double z = hitResult.getLocation().z - blockPos.getZ();

			return new Vector3d(x - 0.5, y - 0.5, z - 0.5);
		}

		static Direction getDirection(Vector3d relativeCoords) {
			return Math.abs(relativeCoords.x) > Math.abs(relativeCoords.z)
				? (relativeCoords.x > 0 ? Direction.EAST : Direction.WEST)
				: (relativeCoords.z > 0 ? Direction.SOUTH : Direction.NORTH);
		}

		static WindowFrameContentsComponent.Slot LARGE(@NonNull BlockHitResult hitResult) {
			return WindowFrameContentsComponent.Slot.CENTER_TOP;
		}

		static WindowFrameContentsComponent.Slot VERTICAL(@NonNull BlockHitResult hitResult) {
			Vector3d relativeCoords = getRelativeCenterCoords(hitResult);
			Direction direction = getDirection(relativeCoords);

			return switch (direction) {
				case NORTH -> WindowFrameContentsComponent.Slot.NORTH_TOP;
				case EAST -> WindowFrameContentsComponent.Slot.EAST_TOP;
				case SOUTH -> WindowFrameContentsComponent.Slot.SOUTH_TOP;
				case WEST -> WindowFrameContentsComponent.Slot.WEST_TOP;
				default -> throw new IllegalStateException("Unknown direction: " + direction);
			};
		}

		static WindowFrameContentsComponent.Slot HORIZONTAL(@NonNull BlockHitResult hitResult) {
			boolean isTopSlot = hitResult.getLocation().y - hitResult.getBlockPos().getY() >= 0.5;

			return isTopSlot
				? WindowFrameContentsComponent.Slot.CENTER_TOP
				: WindowFrameContentsComponent.Slot.CENTER_BOTTOM;
		}

		static WindowFrameContentsComponent.Slot SQUARE(@NonNull BlockHitResult hitResult) {
			Vector3d relativeCoords = getRelativeCenterCoords(hitResult);
			Direction direction = getDirection(relativeCoords);
			boolean isTopSlot = relativeCoords.y >= 0;

			return switch (direction) {
				case NORTH -> isTopSlot
					? WindowFrameContentsComponent.Slot.NORTH_TOP
					: WindowFrameContentsComponent.Slot.NORTH_BOTTOM;
				case EAST -> isTopSlot
					? WindowFrameContentsComponent.Slot.EAST_TOP
					: WindowFrameContentsComponent.Slot.EAST_BOTTOM;
				case SOUTH -> isTopSlot
					? WindowFrameContentsComponent.Slot.SOUTH_TOP
					: WindowFrameContentsComponent.Slot.SOUTH_BOTTOM;
				case WEST -> isTopSlot
					? WindowFrameContentsComponent.Slot.WEST_TOP
					: WindowFrameContentsComponent.Slot.WEST_BOTTOM;
				default -> throw new IllegalStateException();
			};
		}

		static WindowFrameContentsComponent.Slot CROSS(@NonNull BlockHitResult hitResult) {
			Vector3d relativeCoords = getRelativeCenterCoords(hitResult);
			Direction direction = getDirection(relativeCoords);
			boolean isTopSlot = relativeCoords.y >= 0;

			double absY = Math.abs(relativeCoords.y);
			double absDir = Math.max(Math.abs(relativeCoords.x), Math.abs(relativeCoords.z));

			// If the hit position is more up/down than in/out
			if (absY > absDir) {
				return isTopSlot
					? WindowFrameContentsComponent.Slot.CENTER_TOP
					: WindowFrameContentsComponent.Slot.CENTER_BOTTOM;
			}

			return switch (direction) {
				case NORTH -> WindowFrameContentsComponent.Slot.NORTH_TOP;
				case EAST -> WindowFrameContentsComponent.Slot.EAST_TOP;
				case SOUTH -> WindowFrameContentsComponent.Slot.SOUTH_TOP;
				case WEST -> WindowFrameContentsComponent.Slot.WEST_TOP;
				default -> throw new IllegalStateException();
			};
		}

		static WindowFrameContentsComponent.Slot DIAMOND(@NonNull BlockHitResult hitResult) {
			Vector3d relativeCoords = getRelativeCenterCoords(hitResult);
			Direction direction = getDirection(relativeCoords);
			boolean isTopSlot = relativeCoords.y >= 0;

			double absY = Math.abs(relativeCoords.y);
			double absDir = Math.max(Math.abs(relativeCoords.x), Math.abs(relativeCoords.z));

			// If the hit position is within the center
			if ((absY + absDir) < 0.5) {
				return WindowFrameContentsComponent.Slot.CENTER_TOP;
			}

			return switch (direction) {
				case NORTH -> isTopSlot
					? WindowFrameContentsComponent.Slot.NORTH_TOP
					: WindowFrameContentsComponent.Slot.NORTH_BOTTOM;
				case EAST -> isTopSlot
					? WindowFrameContentsComponent.Slot.EAST_TOP
					: WindowFrameContentsComponent.Slot.EAST_BOTTOM;
				case SOUTH -> isTopSlot
					? WindowFrameContentsComponent.Slot.SOUTH_TOP
					: WindowFrameContentsComponent.Slot.SOUTH_BOTTOM;
				case WEST -> isTopSlot
					? WindowFrameContentsComponent.Slot.WEST_TOP
					: WindowFrameContentsComponent.Slot.WEST_BOTTOM;
				default -> throw new IllegalStateException();
			};
		}
	}
}
