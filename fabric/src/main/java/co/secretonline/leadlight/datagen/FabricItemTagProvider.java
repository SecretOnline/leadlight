package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.item.ModItems;
import co.secretonline.leadlight.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class FabricItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public FabricItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
	super(output, registriesFuture);
}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
		valueLookupBuilder(ModItemTags.CUT_STAINED_GLASS_PANE).addAll(ModItems.ALL_CUT_STAINED_GLASS_PANE_ITEMS.get().map(item -> item));
	}
}
