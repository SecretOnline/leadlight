package co.secretonline.leadlight.block;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.component.ModComponents;
import co.secretonline.leadlight.component.WindowFrameContentsComponent;
import co.secretonline.leadlight.data.BlockItemId;
import co.secretonline.leadlight.data.FrameMaterial;
import co.secretonline.leadlight.data.FrameShape;
import co.secretonline.leadlight.data.FrameShapeCollection;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModBlocks {
	public static final FrameShapeCollection<BlockItemId> IRON_WINDOW_FRAME_BLOCK_ITEM_IDS = createKeys(FrameMaterial.IRON);
	public static final FrameShapeCollection<Supplier<WindowFrameBlock>> IRON_WINDOW_FRAMES = FrameShapeCollection.zipMap(FrameShapeCollection.SHAPES, IRON_WINDOW_FRAME_BLOCK_ITEM_IDS, registerWindowFrame(FrameMaterial.IRON));

	// TODO: Stream.concat() when there are more material types
	public static final Stream<BlockItemId> ALL_WINDOW_FRAME_BLOCK_ITEM_IDS = IRON_WINDOW_FRAME_BLOCK_ITEM_IDS.stream();
	public static final Supplier<Stream<WindowFrameBlock>> ALL_WINDOW_FRAME_BLOCKS = () -> IRON_WINDOW_FRAMES.stream().map(Supplier::get);

	public static FrameShapeCollection<BlockItemId> createKeys(FrameMaterial material) {
		return FrameShapeCollection.SHAPES.map((shape) -> {
			Identifier id = Leadlight.id(shape.getPrefix() + "_" + material.name() + "_window_frame");
			return BlockItemId.create(id, id);
		});
	}

	public static BiFunction<FrameShape, BlockItemId, Supplier<WindowFrameBlock>> registerWindowFrame(FrameMaterial material) {
		return (shape, blockItemId) -> {
			Supplier<WindowFrameBlock> block = ServerServiceLoader.REGISTRY.register(
				BuiltInRegistries.BLOCK,
				blockItemId.block().identifier(),
				() -> new WindowFrameBlock(material, shape, BlockBehaviour.Properties.of()
					.instrument(material.instrument())
					.strength(0.3F)
					.sound(material.soundType())
					.noOcclusion()
					.setId(blockItemId.block())));

			Supplier<MapCodec<? extends BaseEntityBlock>> mapCodec = ServerServiceLoader.REGISTRY.register(
				BuiltInRegistries.BLOCK_TYPE,
				blockItemId.block().identifier(),
				() -> block.get().codec());

			Supplier<Item> item = ServerServiceLoader.REGISTRY.register(
				BuiltInRegistries.ITEM,
				blockItemId.item().identifier(),
				() -> new BlockItem(block.get(), new Item.Properties()
					.useBlockDescriptionPrefix()
					.component(ModComponents.WINDOW_FRAME_CONTENTS.get(), WindowFrameContentsComponent.empty())
					.setId(blockItemId.item())));

			return block;
		};
	}

	public static void initialize() {
	}
}
