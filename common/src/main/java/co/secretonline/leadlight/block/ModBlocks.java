package co.secretonline.leadlight.block;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.data.FrameMaterial;
import co.secretonline.leadlight.data.FrameShape;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModBlocks {
	public static final Supplier<WindowFrameBlock> LARGE_IRON_WINDOW_FRAME_BLOCK = registerWindowFrameBlock(FrameMaterial.IRON, FrameShape.LARGE);

	public static final Supplier<Stream<WindowFrameBlock>> ALL_FRAME_BLOCKS = () -> Stream.of(
		LARGE_IRON_WINDOW_FRAME_BLOCK.get()
	);

	private static Supplier<WindowFrameBlock> registerWindowFrameBlock(FrameMaterial material, FrameShape shape) {
		Identifier id = Leadlight.id(shape.getPrefix() + "_" + material.getPrefix() + "_window_frame");
		ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);

		Supplier<WindowFrameBlock> block = ServerServiceLoader.REGISTRY.register(
			BuiltInRegistries.BLOCK,
			id,
			() -> new WindowFrameBlock(material, shape, BlockBehaviour.Properties.of()
				.instrument(NoteBlockInstrument.HAT)
				.strength(0.3F)
				.sound(SoundType.IRON)
				.noOcclusion()
				.setId(key)));

		Supplier<MapCodec<? extends BaseEntityBlock>> mapCodec = ServerServiceLoader.REGISTRY.register(
			BuiltInRegistries.BLOCK_TYPE,
			id,
			() -> block.get().codec());

		return block;
	}

	public static void initialize() {
	}
}
