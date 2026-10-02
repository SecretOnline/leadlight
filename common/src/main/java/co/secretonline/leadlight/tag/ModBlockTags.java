package co.secretonline.leadlight.tag;

import co.secretonline.leadlight.Leadlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
	public static final TagKey<Block> WINDOW_FRAME = create(Leadlight.id("window_frame"));

	private static TagKey<Block> create(final Identifier id) {
		return TagKey.create(Registries.BLOCK, id);
	}
}
