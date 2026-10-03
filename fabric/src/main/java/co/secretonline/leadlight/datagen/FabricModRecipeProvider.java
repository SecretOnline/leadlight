package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class FabricModRecipeProvider extends FabricRecipeProvider {
	public FabricModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
		return new RecipeProvider(registries, output) {
			@Override
			public void buildRecipes() {
				HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

				ModBlocks.ALL_FRAME_BLOCKS.get().forEach(block -> {
					ItemLike material = block.getFrameMaterial().getCraftingMaterial();

					shaped(RecipeCategory.BUILDING_BLOCKS, block, 2)
						.define('m', material)
						.pattern("mmm")
						.pattern("m m")
						.pattern("mmm")
						.unlockedBy("has_nugget", has(material))
						.unlockedBy("has_stained_glass_pane", has(ConventionalItemTags.GLASS_PANES));
				});

				ModItems.ALL_CUT_STAINED_GLASS_PANE_ITEMS.get().forEach(item -> {
					Item pane = switch (item.getColor()) {
						case WHITE -> Items.WHITE_STAINED_GLASS_PANE;
						case ORANGE -> Items.ORANGE_STAINED_GLASS_PANE;
						case MAGENTA -> Items.MAGENTA_STAINED_GLASS_PANE;
						case LIGHT_BLUE -> Items.LIGHT_BLUE_STAINED_GLASS_PANE;
						case YELLOW -> Items.YELLOW_STAINED_GLASS_PANE;
						case LIME -> Items.LIME_STAINED_GLASS_PANE;
						case PINK -> Items.PINK_STAINED_GLASS_PANE;
						case GRAY -> Items.GRAY_STAINED_GLASS_PANE;
						case LIGHT_GRAY -> Items.LIGHT_GRAY_STAINED_GLASS_PANE;
						case CYAN -> Items.CYAN_STAINED_GLASS_PANE;
						case PURPLE -> Items.PURPLE_STAINED_GLASS_PANE;
						case BLUE -> Items.BLUE_STAINED_GLASS_PANE;
						case BROWN -> Items.BROWN_STAINED_GLASS_PANE;
						case GREEN -> Items.GREEN_STAINED_GLASS_PANE;
						case RED -> Items.RED_STAINED_GLASS_PANE;
						case BLACK -> Items.BLACK_STAINED_GLASS_PANE;
					};

					stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, item, pane, 4);
				});

			}
		};
	}

	@Override
	public @NonNull String getName() {
		return "Leadlight Recipes";
	}
}
