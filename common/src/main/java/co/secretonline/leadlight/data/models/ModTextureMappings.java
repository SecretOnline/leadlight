package co.secretonline.leadlight.data.models;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.data.FrameMaterial;
import co.secretonline.leadlight.mixin.client.data.TextureSlotAccessor;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;

public class ModTextureMappings {
	public static final TextureMapping IRON = createTextureMapping("iron");


	public static TextureMapping ofMaterial(FrameMaterial frameMaterial) {
		return switch (frameMaterial) {
			case IRON ->  IRON;
		};
	}

	private static Material createFrameMaterial(String material, String part) {
		return new Material(Leadlight.id("block/" + material + "_window_frame_" + part));
	}

	private static TextureMapping createTextureMapping(String material) {
		return new TextureMapping()
			.put(ModTextureSlots.POST, createFrameMaterial(material, "post"))
			.put(ModTextureSlots.BAR, createFrameMaterial(material, "bar"))
			.put(ModTextureSlots.CROSS, createFrameMaterial(material, "cross"))
			.put(ModTextureSlots.DIAMOND, createFrameMaterial(material, "diamond"));
	}
}
