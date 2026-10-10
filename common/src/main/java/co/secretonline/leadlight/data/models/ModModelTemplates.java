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
		public final ModelTemplate Item;
		public final ModelTemplate ItemBlock;
		public final ModelTemplate Post;
		public final ModelTemplate Side;
		public final ModelTemplate SideAlt;
		public final ModelTemplate NoSide;
		public final ModelTemplate NoSideAlt;

		ShapeModelTemplates(FrameShape frameShape) {
			Item = createItemTemplate(frameShape.getPrefix());
			ItemBlock = createItemBlockTemplate(frameShape.getPrefix());

			Post = createTemplate(frameShape.getPrefix(), "post");
			Side = createTemplate(frameShape.getPrefix(), "side");
			SideAlt = createTemplate(frameShape.getPrefix(), "side_alt");
			NoSide = createTemplate(frameShape.getPrefix(), "no_side");
			NoSideAlt = createTemplate(frameShape.getPrefix(), "no_side_alt");
		}

		private static ModelTemplate createItemTemplate(String shape) {
			return new ModelTemplate(Optional.of(Leadlight.id("item/template_" + shape + "_window_frame")), Optional.empty(),
				ModTextureSlots.POST, ModTextureSlots.BAR, ModTextureSlots.CROSS, ModTextureSlots.DIAMOND);
		}

		private static ModelTemplate createItemBlockTemplate(String shape) {
			return new ModelTemplate(Optional.of(Leadlight.id("block/template_" + shape + "_window_frame")), Optional.empty(),
				ModTextureSlots.POST, ModTextureSlots.BAR, ModTextureSlots.CROSS, ModTextureSlots.DIAMOND);
		}

		private static ModelTemplate createTemplate(String shape, String part) {
			return new ModelTemplate(Optional.of(Leadlight.id("block/template_" + shape + "_window_frame" + "_" + part)), Optional.of("_" + part),
				ModTextureSlots.POST, ModTextureSlots.BAR, ModTextureSlots.CROSS, ModTextureSlots.DIAMOND);
		}
	}
}
