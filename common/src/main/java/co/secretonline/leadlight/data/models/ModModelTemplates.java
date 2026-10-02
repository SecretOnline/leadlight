package co.secretonline.leadlight.data.models;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.data.FrameShape;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;

import java.util.Optional;

public class ModModelTemplates {
	public static final ShapeModelTemplates LARGE = new ShapeModelTemplates(FrameShape.LARGE);

	public static ShapeModelTemplates ofShape(FrameShape frameShape) {
		return switch (frameShape) {
			case LARGE -> LARGE;
		};
	}

	public static class ShapeModelTemplates {
		public final ModelTemplate Item;
		public final ModelTemplate Post;
		public final ModelTemplate Side;
		public final ModelTemplate SideAlt;
		public final ModelTemplate NoSide;
		public final ModelTemplate NoSideAlt;

		ShapeModelTemplates(FrameShape frameShape) {
			Item = createItemTemplate(frameShape.getPrefix());

			Post = createTemplate(frameShape.getPrefix(), "post");
			Side = createTemplate(frameShape.getPrefix(), "side");
			SideAlt = createTemplate(frameShape.getPrefix(), "side_alt");
			NoSide = createTemplate(frameShape.getPrefix(), "no_side");
			NoSideAlt = createTemplate(frameShape.getPrefix(), "no_side_alt");
		}

		private static ModelTemplate createItemTemplate(String shape) {
			return new ModelTemplate(Optional.of(Leadlight.id("item/template_" + shape + "_window_frame")), Optional.empty(), TextureSlot.TEXTURE);
		}

		private static ModelTemplate createTemplate(String shape, String part) {
			return new ModelTemplate(Optional.of(Leadlight.id("block/template_" + shape + "_" + part + "_window_frame")), Optional.of("_" + part), TextureSlot.TEXTURE);
		}
	}
}
