package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.platform.NeoForgeRegistryHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NeoForgeModLootTableProvider extends BlockLootSubProvider {
	public NeoForgeModLootTableProvider(HolderLookup.Provider provider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, provider);
	}

	public static LootTableProvider get(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		return new LootTableProvider(output, Set.of(),
			List.of(
				new LootTableProvider.SubProviderEntry(
					NeoForgeModLootTableProvider::new,
					LootContextParamSets.BLOCK
				)
			),
			lookupProvider
		);
	}

	@Override
	protected @NonNull Iterable<Block> getKnownBlocks() {
		return NeoForgeRegistryHelper.BLOCK.getEntries()
			.stream()
			.map(e -> (Block) e.value())
			.toList();
	}

	@Override
	protected void generate() {
		ModBlocks.ALL_FRAME_BLOCKS.get().forEach(this::dropSelf);
	}
}
