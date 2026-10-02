package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.tag.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class NeoForgeBlockTagProvider extends BlockTagsProvider {
	public NeoForgeBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Leadlight.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider provider) {
		tag(ModBlockTags.WINDOW_FRAME).addAll(ModBlocks.ALL_FRAME_BLOCKS.get().map(block -> block));

		tag(BlockTags.BARS).addTag(ModBlockTags.WINDOW_FRAME);
	}
}
