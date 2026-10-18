package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.item.ModItems;
import co.secretonline.leadlight.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class NeoForgeItemTagProvider extends ItemTagsProvider {
	public NeoForgeItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Leadlight.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider provider) {
		tag(ModItemTags.CUT_STAINED_GLASS_PANE).addAll(ModItems.CUT_STAINED_GLASS_PANES.map(Supplier::get).map(Item::asItem).asList());
	}
}
