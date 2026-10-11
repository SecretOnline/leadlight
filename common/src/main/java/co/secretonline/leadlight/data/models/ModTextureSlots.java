package co.secretonline.leadlight.data.models;

import co.secretonline.leadlight.mixin.client.data.TextureSlotAccessor;
import net.minecraft.client.data.models.model.TextureSlot;

public class ModTextureSlots {
	public static final TextureSlot FRONT = TextureSlotAccessor.create("frame_front");
	public static final TextureSlot BACK = TextureSlotAccessor.create("frame_back");
	public static final TextureSlot SIDES = TextureSlotAccessor.create("frame_sides");
	public static final TextureSlot SURFACE = TextureSlotAccessor.create("frame_surface");
	public static final TextureSlot CROSS = TextureSlotAccessor.create("cross");
	public static final TextureSlot DIAMOND = TextureSlotAccessor.create("diamond");

	public static final TextureSlot[] BASIC_SLOTS = new TextureSlot[]{FRONT, BACK, SIDES, SURFACE};
	public static final TextureSlot[] CROSS_SLOTS = new TextureSlot[]{FRONT, BACK, SIDES, SURFACE, CROSS};
	public static final TextureSlot[] DIAMOND_SLOTS = new TextureSlot[]{FRONT, BACK, SIDES, SURFACE, DIAMOND};
}
