package co.secretonline.leadlight.renderer.blockentity;

import co.secretonline.leadlight.block.WindowFrameBlock;
import co.secretonline.leadlight.block.entity.WindowFrameBlockEntity;
import co.secretonline.leadlight.data.ConnectionInfo;
import co.secretonline.leadlight.item.CutStainedGlassPaneItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WindowFrameBlockEntityRenderer implements BlockEntityRenderer<WindowFrameBlockEntity, WindowFrameBlockEntityRenderState> {
	private static final Direction[] HORIZONTAL_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

	private final SpriteGetter spriteGetter;

	public WindowFrameBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		spriteGetter = context.sprites();
	}

	@Override
	public @NonNull WindowFrameBlockEntityRenderState createRenderState() {
		return new WindowFrameBlockEntityRenderState();
	}

	@Override
	public void extractRenderState(@NonNull WindowFrameBlockEntity blockEntity, @NonNull WindowFrameBlockEntityRenderState state, float partialTicks, @NonNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

		Block block = blockEntity.getBlockState().getBlock();
		if (!(block instanceof WindowFrameBlock windowFrameBlock)) {
			throw new IllegalArgumentException("Block is not a WindowFrameBlock");
		}

		state.frameShape = windowFrameBlock.getFrameShape();
		state.windowFrameContents = blockEntity.getState();
		state.connections = ConnectionInfo.fromBlockState(blockEntity.getBlockState());
	}

	@Override
	public void submit(@NonNull WindowFrameBlockEntityRenderState renderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
		poseStack.pushPose();


		submitNodeCollector.submitCustomGeometry(
			poseStack,
			RenderTypes.translucentMovingBlock(),
			(pose, buffer) -> renderCustomGeometry(renderState, pose, buffer));

		poseStack.popPose();
	}

	private void renderCustomGeometry(@NonNull WindowFrameBlockEntityRenderState renderState, PoseStack.@NonNull Pose pose, @NonNull VertexConsumer buffer) {
		Map<DyeColor, List<WindowVertexData>> collection = new HashMap<>();
		BiConsumer<DyeColor, WindowVertexData> vertexDataBiConsumer = (color, vertex) -> {
			if (!collection.containsKey(color)) {
				collection.put(color, new ArrayList<>());
			}
			collection.get(color).add(vertex);
		};

		DirectionCollector collector = switch (renderState.frameShape) {
			case LARGE -> WindowFrameBlockEntityRenderer::collectLargeFrameVertexData;
			case VERTICAL -> null;
			case HORIZONTAL -> null;
			case SQUARE -> null;
		};

		if (collector != null) {
			for (Direction direction : HORIZONTAL_DIRECTIONS) {
				if (renderState.connections.has(direction)) {
					collector.collect(direction, renderState, vertexDataBiConsumer);
				}
			}
		}

		for (Map.Entry<DyeColor, List<WindowVertexData>> dyeColorListEntry : collection.entrySet()) {
			Block paneBlock = CutStainedGlassPaneItem.ofColor(dyeColorListEntry.getKey()).getPaneBlock();
			Identifier blockId = BuiltInRegistries.BLOCK.getKey(paneBlock);
			SpriteId spriteId = new SpriteId(TextureAtlas.LOCATION_BLOCKS, blockId);
			TextureAtlasSprite sprite = spriteGetter.get(spriteId);

			VertexConsumer consumer = sprite.wrap(buffer);
			for (WindowVertexData data : dyeColorListEntry.getValue()) {
				data.submit(consumer, pose, sprite, renderState.lightCoords);
			}
		}
	}


	private static void collectLargeFrameVertexData(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> colorOptional = renderState.windowFrameContents.centerTop();
		if (colorOptional.isEmpty()) {
			return;
		}

		DyeColor color = colorOptional.get();

		Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

		collectOpenCenter(renderState.connections, direction, vertexConsumer,
			WindowVertexData.FULL_STANDARD, WindowVertexData.VERTICAL_LEFT_STANDARD, WindowVertexData.VERTICAL_LEFT_INSIDE, WindowVertexData.VERTICAL_LEFT_OUTSIDE,
			WindowVertexData.VERTICAL_RIGHT_INSIDE_OPPOSITE, WindowVertexData.VERTICAL_RIGHT_OUTSIDE_OPPOSITE);
	}

	private static void collectOpenCenter(@NonNull ConnectionInfo connections, @NonNull Direction direction, Consumer<WindowVertexData> output,
																				WindowVertexData frontFull, WindowVertexData frontHalf, WindowVertexData frontInner, WindowVertexData frontOuter,
																				WindowVertexData backInner, WindowVertexData backOuter) {
		Direction front = direction.getCounterClockWise();
		Direction opposite = direction.getOpposite();
		Direction back = direction.getClockWise();

		// Front faces
		if (connections.has(front)) {
			output.accept(frontInner);
		} else if (connections.has(opposite)) {
			output.accept(frontFull);
		} else if (connections.has(back)) {
			output.accept(frontOuter);
		} else {
			output.accept(frontHalf);
		}

		// Back faces.
		if (connections.has(back)) {
			output.accept(backInner);
		} else if (connections.has(opposite)) {
			// Don't render back face if the center is open, as this will be handled by the opposite front face.
		} else if (connections.has(back)) {
			output.accept(backOuter);
		}
	}

	private static void collectGeneric(@NonNull DyeColor color, boolean isOpenCenter, @NonNull ConnectionInfo connectionState, @NonNull Direction direction, @NonNull BiConsumer<DyeColor, WindowVertexData> output,
																		 WindowVertexData frontStandard, WindowVertexData frontInner, WindowVertexData frontOuter,
																		 WindowVertexData backStandard, WindowVertexData backInner, WindowVertexData backOuter) {

		Direction front = direction.getCounterClockWise();
		Direction opposite = direction.getOpposite();
		Direction back = direction.getClockWise();

		// Front faces
		if (connectionState.has(front)) {
			output.accept(color, frontInner.withDirection(direction));
		} else if (connectionState.has(opposite)) {
			output.accept(color, frontStandard.withDirection(direction));
		} else if (connectionState.has(back) && isOpenCenter) {
			output.accept(color, frontOuter.withDirection(direction));
		} else {
			output.accept(color, frontStandard.withDirection(direction));
		}

		// Back faces.
		if (connectionState.has(back)) {
			output.accept(color, backInner.withDirection(direction));
		} else if (connectionState.has(opposite) && !isOpenCenter) {
			// Don't render back face if the center is open, as this will be handled by the opposite front face.
			output.accept(color, backStandard.withDirection(direction));
		} else if (connectionState.has(back)) {
			if (isOpenCenter) {
				output.accept(color, backOuter.withDirection(direction));
			} else {
				output.accept(color, backStandard.withDirection(direction));
			}
		}
	}

	@FunctionalInterface
	private interface DirectionCollector {
		void collect(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> addVertexData);
	}
}
