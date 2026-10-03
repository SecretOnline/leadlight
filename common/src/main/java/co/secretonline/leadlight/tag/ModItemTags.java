package co.secretonline.leadlight.tag;

import co.secretonline.leadlight.Leadlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
	public static final TagKey<Item> CUT_STAINED_GLASS_PANE = create(Leadlight.id("cut_stained_glass_pane"));

	private static TagKey<Item> create(final Identifier id) {
		return TagKey.create(Registries.ITEM, id);
	}
}
