package co.secretonline.leadlight.data.models;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.data.FrameShape;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;

import java.util.Optional;

public class ModModelTemplates {
	public static final ShapeModelTemplates LARGE = new ShapeModelTemplates(FrameShape.LARGE);
	public static final ShapeModelTemplates VERTICAL = new ShapeModelTemplates(FrameShape.VERTICAL);
	public static final ShapeModelTemplates HORIZONTAL = new ShapeModelTemplates(FrameShape.HORIZONTAL);
	public static final ShapeModelTemplates SQUARE = new ShapeModelTemplates(FrameShape.SQUARE);
	public static final ShapeModelTemplates CROSS = new ShapeModelTemplates(FrameShape.CROSS);
	public static final ShapeModelTemplates DIAMOND = new ShapeModelTemplates(FrameShape.DIAMOND);

	public static ShapeModelTemplates ofShape(FrameShape frameShape) {
		return switch (frameShape) {
			case LARGE -> LARGE;
			case VERTICAL -> VERTICAL;
			case HORIZONTAL -> HORIZONTAL;
			case SQUARE -> SQUARE;
			case CROSS -> CROSS;
			case DIAMOND -> DIAMOND;
		};
	}

	public static class ShapeModelTemplates {
		public final ModelTemplate ItemBlock;
		public final ModelTemplate Post;
		public final ModelTemplate Side;
		public final ModelTemplate SideAlt;
		public final ModelTemplate NoSide;
		public final ModelTemplate NoSideAlt;

		ShapeModelTemplates(FrameShape frameShape) {
			TextureSlot[] slots = switch (frameShape) {
				case CROSS -> ModTextureSlots.CROSS_SLOTS;
				case DIAMOND -> ModTextureSlots.DIAMOND_SLOTS;
				default -> ModTextureSlots.BASIC_SLOTS;
			};

			ItemBlock = createItemBlockTemplate(frameShape.getPrefix(), slots);

			Post = createTemplate(frameShape.getPrefix(), "post", slots);
			Side = createTemplate(frameShape.getPrefix(), "side", slots);
			SideAlt = createTemplate(frameShape.getPrefix(), "side_alt", slots);
			NoSide = createTemplate(frameShape.getPrefix(), "no_side", slots);
			NoSideAlt = createTemplate(frameShape.getPrefix(), "no_side_alt", slots);
		}

		private static ModelTemplate createItemTemplate(String shape, TextureSlot[] slots) {
			return new ModelTemplate(
				Optional.of(Leadlight.id("item/template_" + shape + "_window_frame")),
				Optional.empty(),
				slots);
		}

		private static ModelTemplate createItemBlockTemplate(String shape, TextureSlot[] slots) {
			return new ModelTemplate(
				Optional.of(Leadlight.id("block/template_" + shape + "_window_frame")),
				Optional.empty(),
				slots);
		}

		private static ModelTemplate createTemplate(String shape, String part, TextureSlot[] slots) {
			return new ModelTemplate(
				Optional.of(Leadlight.id("block/template_" + shape + "_window_frame" + "_" + part)),
				Optional.of("_" + part),
				slots);
		}
	}
}
