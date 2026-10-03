package co.secretonline.leadlight.item;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModCreativeModeTabs {
	private static final Identifier LEADLIGHT_TAB_ID = Leadlight.id("leadlight");
	public static final ResourceKey<CreativeModeTab> LEADLIGHT_TAB_KEY = ResourceKey
		.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), LEADLIGHT_TAB_ID);

	public static final Supplier<CreativeModeTab> LEADLIGHT_TAB = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.CREATIVE_MODE_TAB,
		LEADLIGHT_TAB_ID,
		() -> CreativeModeTab
			.builder(CreativeModeTab.Row.TOP, 0)
			.title(Component.translatable("itemGroup." + Leadlight.MOD_ID))
			.icon(() -> new ItemStack(ModBlocks.LARGE_IRON_WINDOW_FRAME_BLOCK.get()))
			.build());

	public static void addItems(Consumer<ItemLike> consumer) {
		ModItems.ALL_CUT_STAINED_GLASS_PANE_ITEMS.get().forEach(consumer);
		ModBlocks.ALL_FRAME_BLOCKS.get().forEach(consumer);
	}

	public static void initialize(){}
}
