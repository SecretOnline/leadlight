package co.secretonline.leadlight.datagen.recipes;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.block.WindowFrameBlock;
import co.secretonline.leadlight.data.FrameMaterial;
import co.secretonline.leadlight.data.FrameShape;
import co.secretonline.leadlight.item.ModItems;
import co.secretonline.leadlight.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import java.util.Map;
import java.util.stream.Collectors;

public class LeadlightRecipeProvider extends RecipeProvider {
	private final NeededTags tags;

	public LeadlightRecipeProvider(NeededTags tags, HolderLookup.Provider registries, RecipeOutput output) {
		super(registries, output);
		this.tags = tags;
	}

	@Override
	public void buildRecipes() {
		buildFrameRecipes();
		buildCutStainedGlassPaneRecipes();
	}

	private void buildFrameRecipes() {
		Map<FrameMaterial, WindowFrameBlock> largeFrames = ModBlocks.ALL_FRAME_BLOCKS.get()
			.filter(block -> block.getFrameShape() == FrameShape.LARGE)
			.collect(Collectors.toMap(WindowFrameBlock::getFrameMaterial, block -> block));

		ModBlocks.ALL_FRAME_BLOCKS.get().forEach(block -> {
			FrameShape shape = block.getFrameShape();
			FrameMaterial material = block.getFrameMaterial();
			ItemLike recipeMaterial = material.getCraftingMaterial();

			WindowFrameBlock largeFrame = largeFrames.get(material);
			if (largeFrame == null) {
				throw new UnsupportedOperationException("Missing LARGE frame for material " + material.getSerializedName());
			}

			switch (shape) {
				case LARGE -> shaped(RecipeCategory.BUILDING_BLOCKS, block, 2)
					.define('m', recipeMaterial)
					.pattern("mmm")
					.pattern("m m")
					.pattern("mmm")
					.unlockedBy("has_material", has(recipeMaterial))
					.unlockedBy("has_stained_glass_pane", has(tags.glassPanes()))
					.save(output);
				case VERTICAL -> shaped(RecipeCategory.BUILDING_BLOCKS, block, 1)
					.define('f', largeFrame)
					.define('m', recipeMaterial)
					.pattern("m")
					.pattern("f")
					.pattern("m")
					.unlockedBy("has_material", has(recipeMaterial))
					.unlockedBy("has_stained_glass_pane", has(tags.glassPanes()))
					.save(output);
				case HORIZONTAL -> shaped(RecipeCategory.BUILDING_BLOCKS, block, 1)
					.define('f', largeFrame)
					.define('m', recipeMaterial)
					.pattern("mfm")
					.unlockedBy("has_material", has(recipeMaterial))
					.unlockedBy("has_stained_glass_pane", has(tags.glassPanes()))
					.save(output);
				case SQUARE -> shaped(RecipeCategory.BUILDING_BLOCKS, block, 1)
					.define('f', largeFrame)
					.define('m', recipeMaterial)
					.pattern(" m ")
					.pattern("mfm")
					.pattern(" m ")
					.unlockedBy("has_material", has(recipeMaterial))
					.unlockedBy("has_stained_glass_pane", has(tags.glassPanes()))
					.save(output);
				case CROSS -> shaped(RecipeCategory.BUILDING_BLOCKS, block, 1)
					.define('f', largeFrame)
					.define('m', recipeMaterial)
					.pattern("m m")
					.pattern(" f ")
					.pattern("m m")
					.unlockedBy("has_material", has(recipeMaterial))
					.unlockedBy("has_stained_glass_pane", has(tags.glassPanes()))
					.save(output);
				case DIAMOND -> shaped(RecipeCategory.BUILDING_BLOCKS, block, 1)
					.define('f', largeFrame)
					.define('m', recipeMaterial)
					.pattern("mmm")
					.pattern("mfm")
					.pattern("mmm")
					.unlockedBy("has_material", has(recipeMaterial))
					.unlockedBy("has_stained_glass_pane", has(tags.glassPanes()))
					.save(output);
			}
		});
	}

	private void buildCutStainedGlassPaneRecipes() {
		ModItems.ALL_CUT_STAINED_GLASS_PANE_ITEMS.get().forEach(item -> {
			stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, item, item.getPaneBlock(), 4);

			var builder = shaped(RecipeCategory.BUILDING_BLOCKS, item.getPaneBlock(), 1)
				.define('c', item)
				.pattern("cc")
				.pattern("cc")
				.unlockedBy("has_cut_stained_glass_pane", has(item));
			ResourceKey<Recipe<?>> key = ResourceKey.create(
				Registries.RECIPE,
				Leadlight.id(builder.defaultId().identifier().getPath() + "_from_cut_stained_glass_pane"));
			builder
				.save(output, key);
		});
	}

	public record NeededTags(TagKey<Item> glassPanes) {
	}
}
