package co.secretonline.leadlight.item;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.data.ColorCollection;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModItems {
	public static final ColorCollection<ResourceKey<Item>> CUT_STAINED_GLASS_PANE_KEYS = createCutStainedGlassPaneKeys();
	public static final ColorCollection<Supplier<CutStainedGlassPaneItem>> CUT_STAINED_GLASS_PANES = ColorCollection.zipMap(ColorCollection.VALUES, CUT_STAINED_GLASS_PANE_KEYS, ModItems::registerCutStainedGlassPaneItem);

	public static void initialize() {
	}

	private static ColorCollection<ResourceKey<Item>> createCutStainedGlassPaneKeys() {
		return ColorCollection.prefixWithColor(ColorCollection.create("cut_stained_glass_pane"))
			.map(str -> ResourceKey.create(Registries.ITEM, Leadlight.id(str)));
	}

	private static Supplier<CutStainedGlassPaneItem> registerCutStainedGlassPaneItem(DyeColor color, ResourceKey<Item> itemKey) {
		return ServerServiceLoader.REGISTRY.register(
			BuiltInRegistries.ITEM,
			itemKey.identifier(),
			()-> new CutStainedGlassPaneItem(color, new Item.Properties().useBlockDescriptionPrefix().setId(itemKey)));
	}
}
