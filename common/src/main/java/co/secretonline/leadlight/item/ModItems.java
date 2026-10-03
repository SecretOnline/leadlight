package co.secretonline.leadlight.item;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModItems {
	public static final Supplier<CutStainedGlassPaneItem> WHITE_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.WHITE);
	public static final Supplier<CutStainedGlassPaneItem> ORANGE_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.ORANGE);
	public static final Supplier<CutStainedGlassPaneItem> MAGENTA_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.MAGENTA);
	public static final Supplier<CutStainedGlassPaneItem> LIGHT_BLUE_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.LIGHT_BLUE);
	public static final Supplier<CutStainedGlassPaneItem> YELLOW_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.YELLOW);
	public static final Supplier<CutStainedGlassPaneItem> LIME_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.LIME);
	public static final Supplier<CutStainedGlassPaneItem> PINK_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.PINK);
	public static final Supplier<CutStainedGlassPaneItem> GRAY_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.GRAY);
	public static final Supplier<CutStainedGlassPaneItem> LIGHT_GRAY_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.LIGHT_GRAY);
	public static final Supplier<CutStainedGlassPaneItem> CYAN_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.CYAN);
	public static final Supplier<CutStainedGlassPaneItem> PURPLE_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.PURPLE);
	public static final Supplier<CutStainedGlassPaneItem> BLUE_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.BLUE);
	public static final Supplier<CutStainedGlassPaneItem> BROWN_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.BROWN);
	public static final Supplier<CutStainedGlassPaneItem> GREEN_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.GREEN);
	public static final Supplier<CutStainedGlassPaneItem> RED_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.RED);
	public static final Supplier<CutStainedGlassPaneItem> BLACK_CUT_STAINED_GLASS_PANE_ITEM = registerCutStainedGlassPaneItem(DyeColor.BLACK);

	public static final Supplier<Stream<CutStainedGlassPaneItem>> ALL_CUT_STAINED_GLASS_PANE_ITEMS = () -> Stream.of(
		WHITE_CUT_STAINED_GLASS_PANE_ITEM.get(),
		ORANGE_CUT_STAINED_GLASS_PANE_ITEM.get(),
		MAGENTA_CUT_STAINED_GLASS_PANE_ITEM.get(),
		LIGHT_BLUE_CUT_STAINED_GLASS_PANE_ITEM.get(),
		YELLOW_CUT_STAINED_GLASS_PANE_ITEM.get(),
		LIME_CUT_STAINED_GLASS_PANE_ITEM.get(),
		PINK_CUT_STAINED_GLASS_PANE_ITEM.get(),
		GRAY_CUT_STAINED_GLASS_PANE_ITEM.get(),
		LIGHT_GRAY_CUT_STAINED_GLASS_PANE_ITEM.get(),
		CYAN_CUT_STAINED_GLASS_PANE_ITEM.get(),
		PURPLE_CUT_STAINED_GLASS_PANE_ITEM.get(),
		BLUE_CUT_STAINED_GLASS_PANE_ITEM.get(),
		BROWN_CUT_STAINED_GLASS_PANE_ITEM.get(),
		GREEN_CUT_STAINED_GLASS_PANE_ITEM.get(),
		RED_CUT_STAINED_GLASS_PANE_ITEM.get(),
		BLACK_CUT_STAINED_GLASS_PANE_ITEM.get()
	);

	public static void initialize() {
	}

	private static Supplier<CutStainedGlassPaneItem> registerCutStainedGlassPaneItem(DyeColor color) {
		Identifier id = Leadlight.id(color.getName() + "_cut_stained_glass_pane");
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);

		return ServerServiceLoader.REGISTRY.register(
			BuiltInRegistries.ITEM,
			id,
			()-> new CutStainedGlassPaneItem(color, new Item.Properties().useBlockDescriptionPrefix().setId(itemKey)));
	}
}
