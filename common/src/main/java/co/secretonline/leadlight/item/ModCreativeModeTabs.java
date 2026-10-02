package co.secretonline.leadlight.item;

import co.secretonline.leadlight.Leadlight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeModeTabs {
	private static final Identifier LEADLIGHT_TAB_ID = Leadlight.id("leadlight");
	public static final ResourceKey<CreativeModeTab> LEADLIGHT_TAB_KEY = ResourceKey
		.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), LEADLIGHT_TAB_ID);

//	public static final Supplier<CreativeModeTab> LEADLIGHT_TAB = ServerServiceLoader.REGISTRY.register(
//		BuiltInRegistries.CREATIVE_MODE_TAB,
//		LEADLIGHT_TAB_ID,
//		() -> CreativeModeTab
//			.builder(CreativeModeTab.Row.TOP, 0)
//			.title(Component.translatable("itemGroup." + Leadlight.MOD_ID))
//			.icon(() -> new ItemStack(ModItems.IRON_WINDOW_FRAME.get()))
//			.build());

	public static void initialize(){}
}
