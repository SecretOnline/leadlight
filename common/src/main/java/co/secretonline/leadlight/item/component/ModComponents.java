package co.secretonline.leadlight.item.component;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class ModComponents {
	public static final Supplier<DataComponentType<TinyFlowerComponent>> TINY_FLOWER = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		Leadlight.id("tiny_flower"),
		() -> DataComponentType.<TinyFlowerComponent>builder()
			.persistent(TinyFlowerComponent.CODEC)
			.build());

	public static final Supplier<DataComponentType<GardenContentsComponent>> GARDEN_CONTENTS = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		Leadlight.id("garden_contents"),
		() -> DataComponentType.<GardenContentsComponent>builder()
			.persistent(GardenContentsComponent.CODEC)
			.build());

	public static void initialize() {
	}
}
