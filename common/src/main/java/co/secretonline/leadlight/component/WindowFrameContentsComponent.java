package co.secretonline.leadlight.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public record WindowFrameContentsComponent(Optional<DyeColor> centerTop, Optional<DyeColor> centerBottom,
																					 Optional<DyeColor> northTop, Optional<DyeColor> northBottom,
																					 Optional<DyeColor> eastTop, Optional<DyeColor> eastBottom,
																					 Optional<DyeColor> southTop, Optional<DyeColor> southBottom,
																					 Optional<DyeColor> westTop, Optional<DyeColor> westBottom) {

	public static WindowFrameContentsComponent empty() {
		return new WindowFrameContentsComponent(Optional.empty(),Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
			Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}

	public WindowFrameContentsComponent with(Slot slot, Optional<DyeColor> value) {
		return new WindowFrameContentsComponent(
			slot == Slot.CENTER_TOP ? value : centerTop,
			slot == Slot.CENTER_BOTTOM ? value : centerBottom,
			slot == Slot.NORTH_TOP ? value : northTop,
			slot == Slot.NORTH_BOTTOM ? value : northBottom,
			slot == Slot.EAST_TOP ? value : eastTop,
			slot == Slot.EAST_BOTTOM ? value : eastBottom,
			slot == Slot.SOUTH_TOP ? value : southTop,
			slot == Slot.SOUTH_BOTTOM ? value : southBottom,
			slot == Slot.WEST_TOP ? value : westTop,
			slot == Slot.WEST_BOTTOM ? value : westBottom
		);
	}

	public Optional<DyeColor> slot(Slot slot) {
		return switch (slot) {
			case CENTER_TOP -> centerTop;
			case CENTER_BOTTOM -> centerBottom;
			case NORTH_TOP -> northTop;
			case NORTH_BOTTOM -> northBottom;
			case EAST_TOP -> eastTop;
			case EAST_BOTTOM -> eastBottom;
			case SOUTH_TOP -> southTop;
			case SOUTH_BOTTOM -> southBottom;
			case WEST_TOP -> westTop;
			case WEST_BOTTOM -> westBottom;
		};
	}

	public Optional<DyeColor> sideTop(Direction direction) {
		return switch (direction) {
			case NORTH -> northTop;
			case EAST -> eastTop;
			case SOUTH -> southTop;
			case WEST -> westTop;
			default -> Optional.empty();
		};
	}

	public Optional<DyeColor> sideBottom(Direction direction) {
		return switch (direction) {
			case NORTH -> northBottom;
			case EAST -> eastBottom;
			case SOUTH -> southBottom;
			case WEST -> westBottom;
			default -> Optional.empty();
		};
	}

	public void forEach(Consumer<Optional<DyeColor>> consumer) {
		consumer.accept(centerTop);
		consumer.accept(centerBottom);
		consumer.accept(northTop);
		consumer.accept(northBottom);
		consumer.accept(eastTop);
		consumer.accept(eastBottom);
		consumer.accept(southTop);
		consumer.accept(southBottom);
		consumer.accept(westTop);
		consumer.accept(westBottom);
	}

	public static final Codec<WindowFrameContentsComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			DyeColor.CODEC.optionalFieldOf("center_top").forGetter(WindowFrameContentsComponent::centerTop),
			DyeColor.CODEC.optionalFieldOf("center_bottom").forGetter(WindowFrameContentsComponent::centerBottom),
			DyeColor.CODEC.optionalFieldOf("north_top").forGetter(WindowFrameContentsComponent::northTop),
			DyeColor.CODEC.optionalFieldOf("north_bottom").forGetter(WindowFrameContentsComponent::northBottom),
			DyeColor.CODEC.optionalFieldOf("east_top").forGetter(WindowFrameContentsComponent::eastTop),
			DyeColor.CODEC.optionalFieldOf("east_bottom").forGetter(WindowFrameContentsComponent::eastBottom),
			DyeColor.CODEC.optionalFieldOf("south_top").forGetter(WindowFrameContentsComponent::southTop),
			DyeColor.CODEC.optionalFieldOf("south_bottom").forGetter(WindowFrameContentsComponent::southBottom),
			DyeColor.CODEC.optionalFieldOf("west_top").forGetter(WindowFrameContentsComponent::westTop),
			DyeColor.CODEC.optionalFieldOf("west_bottom").forGetter(WindowFrameContentsComponent::westBottom))
		.apply(instance, WindowFrameContentsComponent::new));

	public enum Slot {
		CENTER_TOP(null),
		CENTER_BOTTOM(null),
		NORTH_TOP(Direction.NORTH),
		NORTH_BOTTOM(Direction.NORTH),
		SOUTH_TOP(Direction.SOUTH),
		SOUTH_BOTTOM(Direction.SOUTH),
		EAST_TOP(Direction.EAST),
		EAST_BOTTOM(Direction.EAST),
		WEST_TOP(Direction.WEST),
		WEST_BOTTOM(Direction.WEST);

		public static Slot of(Direction direction, boolean isTop) {
			return switch (direction) {
				case NORTH -> isTop ? NORTH_TOP : NORTH_BOTTOM;
				case EAST -> isTop ? EAST_TOP : EAST_BOTTOM;
				case SOUTH -> isTop ? SOUTH_TOP : SOUTH_BOTTOM;
				case WEST -> isTop ? WEST_TOP : WEST_BOTTOM;
				default -> throw new IllegalStateException("Unexpected value: " + direction);
			};
		}

		private final @Nullable Direction direction;

		Slot(@Nullable Direction direction) {
			this.direction = direction;
		}

		public @Nullable Direction getDirection() {
			return direction;
		}

		public boolean isDirection(Direction direction) {
			return this.direction != null && direction == this.direction;
		}
	}
}
