package co.secretonline.leadlight.block;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class ModBlocks {
	private static final Identifier IRON_WINDOW_FRAME_ID = Leadlight.id("iron_window_frame");
	public static final ResourceKey<Block> IRON_WINDOW_FRAME_KEY = ResourceKey.create(Registries.BLOCK, IRON_WINDOW_FRAME_ID);
	public static final Supplier<Block> IRON_WINDOW_FRAME_BLOCK = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.BLOCK,
		IRON_WINDOW_FRAME_ID,
		() -> new IronWindowFrame(BlockBehaviour.Properties.of()
			.instrument(NoteBlockInstrument.HAT)
			.strength(0.3F)
			.sound(SoundType.GLASS)
			.noOcclusion()
			.setId(IRON_WINDOW_FRAME_KEY)));
	public static final Supplier<MapCodec<IronWindowFrame>> IRON_WINDOW_FRAME_TYPE = ServerServiceLoader.REGISTRY.register(
		BuiltInRegistries.BLOCK_TYPE,
		IRON_WINDOW_FRAME_ID,
		() -> IronWindowFrame.CODEC);

	public static void initialize() {
	}
}
