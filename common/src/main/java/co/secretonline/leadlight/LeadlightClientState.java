package co.secretonline.leadlight;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.RandomSource;

public class LeadlightClientState {
	public static final RandomSource RANDOM = RandomSource.create();
	public static final ItemStackRenderState ITEM_RENDER_STATE = new ItemStackRenderState();
}
