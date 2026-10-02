package co.secretonline.leadlight.block.entity;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {
	public static final Supplier<BlockEntityType<WindowFrameBlockEntity>> WINDOW_FRAME_BLOCK_ENTITY = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.BLOCK_ENTITY_TYPE,
		Leadlight.id("window_frame"),
		() -> ServerServiceLoader.PLATFORM_REGISTRATION
			.createBlockEntityType(WindowFrameBlockEntity::new, ModBlocks.IRON_WINDOW_FRAME_BLOCK.get()));

	public static void initialize() {
	}
}
