package co.secretonline.leadlight.data.models;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.data.FrameMaterial;
import co.secretonline.leadlight.mixin.client.data.TextureSlotAccessor;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;

public class ModTextureMappings {
	public static TextureMapping ofMaterial(FrameMaterial frameMaterial) {
		return createTextureMapping(frameMaterial.getSerializedName());
	}

	private static Material createFrameMaterial(String material, String part) {
		return new Material(Leadlight.id("block/" + material + "_window_frame_" + part));
	}

	private static TextureMapping createTextureMapping(String material) {
		return new TextureMapping()
			.put(ModTextureSlots.FRONT, createFrameMaterial(material, "front"))
			.put(ModTextureSlots.BACK, createFrameMaterial(material, "back"))
			.put(ModTextureSlots.SIDES, createFrameMaterial(material, "sides"))
			.put(ModTextureSlots.SURFACE, createFrameMaterial(material, "surface"))
			.put(ModTextureSlots.CROSS, createFrameMaterial(material, "cross"))
			.put(ModTextureSlots.DIAMOND, createFrameMaterial(material, "diamond"));
	}
}
