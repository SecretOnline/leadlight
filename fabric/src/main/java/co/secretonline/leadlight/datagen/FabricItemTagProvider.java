package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.item.ModItems;
import co.secretonline.leadlight.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FabricItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public FabricItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
	super(output, registriesFuture);
}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
		tag(ModItemTags.CUT_STAINED_GLASS_PANE).addAll(ModItems.CUT_STAINED_GLASS_PANE_KEYS.asList());
	}
}
