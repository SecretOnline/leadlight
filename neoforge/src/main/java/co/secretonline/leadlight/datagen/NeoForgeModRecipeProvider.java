package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.datagen.recipes.LeadlightRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class NeoForgeModRecipeProvider {

	public static @NonNull RecipeProvider createRecipeProvider(@NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
		LeadlightRecipeProvider.NeededTags tags = new LeadlightRecipeProvider.NeededTags(Tags.Items.GLASS_PANES);

		return new LeadlightRecipeProvider(tags, recipes, advancements);
	}
}
