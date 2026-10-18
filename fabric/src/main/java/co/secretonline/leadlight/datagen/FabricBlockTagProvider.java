package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemId;
import net.minecraft.tags.BlockTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class FabricBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	public FabricBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
		tag(ModBlockTags.WINDOW_FRAME).addAll(ModBlocks.ALL_WINDOW_FRAME_BLOCK_ITEM_IDS.map(BlockItemId::block));

		tag(BlockTags.BARS).addTag(ModBlockTags.WINDOW_FRAME);
	}
}
