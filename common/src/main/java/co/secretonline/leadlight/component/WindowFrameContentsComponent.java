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
}
