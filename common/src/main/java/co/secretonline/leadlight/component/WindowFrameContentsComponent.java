package co.secretonline.leadlight.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;

import java.util.Optional;

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
		CENTER_TOP,
		CENTER_BOTTOM,
		NORTH_TOP,
		NORTH_BOTTOM,
		SOUTH_TOP,
		SOUTH_BOTTOM,
		EAST_TOP,
		EAST_BOTTOM,
		WEST_TOP,
		WEST_BOTTOM
	}
}
