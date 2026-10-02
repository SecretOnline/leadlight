package co.secretonline.leadlight.block.entity;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {
	public static final Supplier<BlockEntityType<TinyGardenBlockEntity>> TINY_GARDEN_BLOCK_ENTITY = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.BLOCK_ENTITY_TYPE,
		Leadlight.id("tiny_garden"),
		() -> ServerServiceLoader.PLATFORM_REGISTRATION
			.createBlockEntityType(TinyGardenBlockEntity::new, ModBlocks.TINY_GARDEN_BLOCK.get()));

	public static final Supplier<BlockEntityType<TinyFlowerPotBlockEntity>> TINY_FLOWER_POT_BLOCK_ENTITY = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.BLOCK_ENTITY_TYPE,
		Leadlight.id("tiny_flower_pot"),
		() -> ServerServiceLoader.PLATFORM_REGISTRATION
			.createBlockEntityType(TinyFlowerPotBlockEntity::new, ModBlocks.TINY_FLOWER_POT_BLOCK.get()));

	public static void initialize() {
	}
}
