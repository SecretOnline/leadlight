package co.secretonline.leadlight.data;

import co.secretonline.leadlight.component.WindowFrameContentsComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;

public enum FrameShape implements StringRepresentable {
	LARGE("large", HitProcessor::LARGE, true, false, false, false),
	VERTICAL("vertical", HitProcessor::VERTICAL, false, false, true, false),
	HORIZONTAL("horizontal", HitProcessor::HORIZONTAL, true, true, false, false),
	SQUARE("square", HitProcessor::SQUARE, false, false, true, true);

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

		static WindowFrameContentsComponent.Slot LARGE(@NonNull BlockHitResult hitResult) {
			return WindowFrameContentsComponent.Slot.CENTER_TOP;
		}

		static WindowFrameContentsComponent.Slot VERTICAL(@NonNull BlockHitResult hitResult) {
			BlockPos blockPos = hitResult.getBlockPos();
			double x = hitResult.getLocation().x - blockPos.getX();
			double z = hitResult.getLocation().z - blockPos.getZ();

			return Math.abs(x - 0.5) > Math.abs(z - 0.5)
				? (x >= 0.5
				? WindowFrameContentsComponent.Slot.EAST_TOP
				: WindowFrameContentsComponent.Slot.WEST_TOP)
				: (z >= 0.5
				? WindowFrameContentsComponent.Slot.SOUTH_TOP
				: WindowFrameContentsComponent.Slot.NORTH_TOP);
		}

		static WindowFrameContentsComponent.Slot HORIZONTAL(@NonNull BlockHitResult hitResult) {
			boolean isTopSlot = hitResult.getLocation().y - hitResult.getBlockPos().getY() >= 0.5;

			return isTopSlot
				? WindowFrameContentsComponent.Slot.CENTER_TOP
				: WindowFrameContentsComponent.Slot.CENTER_BOTTOM;
		}

		static WindowFrameContentsComponent.Slot SQUARE(@NonNull BlockHitResult hitResult) {
			BlockPos blockPos = hitResult.getBlockPos();
			boolean isTopSlot = hitResult.getLocation().y - blockPos.getY() >= 0.5;

			double x = hitResult.getLocation().x - blockPos.getX();
			double z = hitResult.getLocation().z - blockPos.getZ();
			double dx = x - 0.5;
			double dz = z - 0.5;

			Direction direction = Math.abs(dx) > Math.abs(dz)
				? (dx > 0 ? Direction.EAST : Direction.WEST)
				: (dz > 0 ? Direction.SOUTH : Direction.NORTH);

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
