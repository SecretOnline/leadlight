package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.datagen.recipes.LeadlightRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class FabricModRecipeProvider extends FabricRecipeProvider {
	public FabricModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
		LeadlightRecipeProvider.NeededTags tags = new LeadlightRecipeProvider.NeededTags(ConventionalItemTags.GLASS_PANES);

		return new LeadlightRecipeProvider(tags, registries, output);
	}

	@Override
	public @NonNull String getName() {
		return "Leadlight Recipes";
	}
}
