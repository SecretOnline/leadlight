package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.datagen.recipes.LeadlightRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class NeoForgeModRecipeProvider extends RecipeProvider.Runner {

	protected NeoForgeModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(packOutput, registries);
	}

	@Override
	protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
		LeadlightRecipeProvider.NeededTags tags = new LeadlightRecipeProvider.NeededTags(Tags.Items.GLASS_PANES);

		return new LeadlightRecipeProvider(tags, registries, output);
	}

	@Override
	public @NonNull String getName() {
		return "FloristsShearsRecipeProvider";
	}
}
