package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class FabricModLootTableProvider extends FabricBlockLootSubProvider {
	protected FabricModLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(packOutput, registriesFuture);
	}

	@Override
	public void generate() {
		ModBlocks.ALL_FRAME_BLOCKS.get().forEach(this::dropSelf);
	}
}
