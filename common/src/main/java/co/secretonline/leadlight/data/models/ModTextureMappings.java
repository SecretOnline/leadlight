package co.secretonline.leadlight.data.models;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.data.FrameMaterial;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;

public class ModTextureMappings {
	public static TextureMapping IRON = createTextureMapping("iron");

	public static TextureMapping ofMaterial(FrameMaterial frameMaterial) {
		return switch (frameMaterial) {
			case IRON ->  IRON;
		};
	}

	private static Material createFrameMaterial(String material) {
		return new Material(Leadlight.id("item/" + material + "_frame"));
	}

	private static TextureMapping createTextureMapping(String material) {
		return new TextureMapping()
			.put(TextureSlot.TEXTURE, createFrameMaterial(material));
	}
}
