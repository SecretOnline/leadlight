package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.item.ModItems;
import co.secretonline.leadlight.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class NeoForgeItemTagProvider extends ItemTagsProvider {
	public NeoForgeItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Leadlight.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider provider) {
		tag(ModItemTags.CUT_STAINED_GLASS_PANE).addAll(ModItems.ALL_CUT_STAINED_GLASS_PANE_ITEMS.get().map(item -> item));
	}
}
