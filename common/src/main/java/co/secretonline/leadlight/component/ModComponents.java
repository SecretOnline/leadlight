package co.secretonline.leadlight.component;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class ModComponents {
	public static final Supplier<DataComponentType<WindowFrameContentsComponent>> WINDOW_FRAME_CONTENTS = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		Leadlight.id("window_frame_contents"),
		() -> DataComponentType.<WindowFrameContentsComponent>builder()
			.persistent(WindowFrameContentsComponent.CODEC)
			.build());

	public static void initialize() {
	}
}
