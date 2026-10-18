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
			.icon(() -> new ItemStack(ModBlocks.IRON_WINDOW_FRAMES.large().get()))
			.build());

	public static void addItems(Consumer<ItemLike> consumer) {
		ModItems.CUT_STAINED_GLASS_PANES.map(Supplier::get).forEach(consumer::accept);
		ModBlocks.IRON_WINDOW_FRAMES.map(Supplier::get).forEach(consumer::accept);
	}

	public static void initialize(){}
}
