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
	public int getViewDistance() {
		return 128;
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
		state.windowFrameContents = blockEntity.getContents();
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

		// Collect side faces
		DirectionCollector collector = switch (renderState.frameShape) {
			case LARGE -> WindowFrameBlockEntityRenderer::collectLargeFrameVertexData;
			case VERTICAL -> WindowFrameBlockEntityRenderer::collectVerticalFrameVertexData;
			case HORIZONTAL -> WindowFrameBlockEntityRenderer::collectHorizontalFrameVertexData;
			case SQUARE -> WindowFrameBlockEntityRenderer::collectSquareFrameVertexData;
			case CROSS -> WindowFrameBlockEntityRenderer::collectCrossFrameVertexData;
			case DIAMOND -> WindowFrameBlockEntityRenderer::collectDiamondFrameVertexData;
		};
		for (Direction direction : HORIZONTAL_DIRECTIONS) {
			if (renderState.connections.has(direction)) {
				collector.collect(direction, renderState, vertexDataBiConsumer);
			}
		}

		// Collect center faces for any center
		if (renderState.frameShape.hasCenterTopSlot() || renderState.frameShape.hasCenterBottomSlot()) {
			CenterCollector centerCollector = switch (renderState.frameShape) {
				case LARGE, DIAMOND -> WindowFrameBlockEntityRenderer::collectLargeFrameCenterVertexData;
				case HORIZONTAL, CROSS -> WindowFrameBlockEntityRenderer::collectHorizontalFrameCenterVertexData;
				default -> null;
			};
			if (centerCollector != null && renderState.connections.isEmpty()) {
				centerCollector.collect(renderState, vertexDataBiConsumer);
			}
		}

		for (Map.Entry<DyeColor, List<WindowVertexData>> dyeColorListEntry : collection.entrySet()) {
			Block paneBlock = CutStainedGlassPaneItem.ofColor(dyeColorListEntry.getKey()).getGlassBlock();
			Identifier blockId = BuiltInRegistries.BLOCK.getKey(paneBlock);
			SpriteId spriteId = new SpriteId(TextureAtlas.LOCATION_BLOCKS, blockId.withPrefix("block/"));
			TextureAtlasSprite sprite = spriteGetter.get(spriteId);

			VertexConsumer consumer = sprite.wrap(buffer);
			for (WindowVertexData data : dyeColorListEntry.getValue()) {
				data.submit(consumer, pose, sprite, renderState.lightCoords);
			}
		}
	}

	private static void collectLargeFrameVertexData(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> colorOptional = renderState.windowFrameContents.centerTop();
		if (colorOptional.isPresent()) {
			DyeColor color = colorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectOpenCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.FULL_STANDARD, WindowVertexData.VERTICAL_LEFT_STANDARD,
				WindowVertexData.VERTICAL_RIGHT_STANDARD_OPPOSITE);
		}
	}

	private static void collectLargeFrameCenterVertexData(@NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> colorOptional = renderState.windowFrameContents.centerTop();
		colorOptional.ifPresent(color -> output.accept(color, WindowVertexData.POST_FULL));
	}

	private static void collectVerticalFrameVertexData(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> colorOptional = renderState.windowFrameContents.sideTop(direction);
		if (colorOptional.isPresent()) {
			DyeColor color = colorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectClosedCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.VERTICAL_LEFT_STANDARD,
				WindowVertexData.VERTICAL_RIGHT_STANDARD_OPPOSITE);
		}
	}

	private static void collectHorizontalFrameVertexData(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> topColorOptional = renderState.windowFrameContents.centerTop();
		if (topColorOptional.isPresent()) {
			DyeColor color = topColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectOpenCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.HORIZONTAL_TOP_FULL, WindowVertexData.SQUARE_TOP_LEFT_STANDARD,
				WindowVertexData.SQUARE_TOP_RIGHT_STANDARD_OPPOSITE);
		}

		Optional<DyeColor> bottomColorOptional = renderState.windowFrameContents.centerBottom();
		if (bottomColorOptional.isPresent()) {
			DyeColor color = bottomColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectOpenCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.HORIZONTAL_BOTTOM_FULL, WindowVertexData.SQUARE_BOTTOM_LEFT_STANDARD,
				WindowVertexData.SQUARE_BOTTOM_RIGHT_STANDARD_OPPOSITE);
		}
	}

	private static void collectHorizontalFrameCenterVertexData(@NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> topColorOptional = renderState.windowFrameContents.centerTop();
		topColorOptional.ifPresent(color -> output.accept(color, WindowVertexData.POST_TOP));

		Optional<DyeColor> bottomColorOptional = renderState.windowFrameContents.centerBottom();
		bottomColorOptional.ifPresent(color -> output.accept(color, WindowVertexData.POST_BOTTOM));
	}

	private static void collectSquareFrameVertexData(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> topColorOptional = renderState.windowFrameContents.sideTop(direction);
		if (topColorOptional.isPresent()) {
			DyeColor color = topColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectClosedCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.SQUARE_TOP_LEFT_STANDARD,
				WindowVertexData.SQUARE_TOP_RIGHT_STANDARD_OPPOSITE);
		}

		Optional<DyeColor> bottomColorOptional = renderState.windowFrameContents.sideBottom(direction);
		if (bottomColorOptional.isPresent()) {
			DyeColor color = bottomColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectClosedCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.SQUARE_BOTTOM_LEFT_STANDARD,
				WindowVertexData.SQUARE_BOTTOM_RIGHT_STANDARD_OPPOSITE);
		}
	}

	private static void collectCrossFrameVertexData(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> topColorOptional = renderState.windowFrameContents.centerTop();
		if (topColorOptional.isPresent()) {
			DyeColor color = topColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectOpenCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.CROSS_TOP_FULL, WindowVertexData.CROSS_TOP_LEFT_STANDARD,
				WindowVertexData.CROSS_TOP_RIGHT_STANDARD_OPPOSITE);
		}

		Optional<DyeColor> bottomColorOptional = renderState.windowFrameContents.centerBottom();
		if (bottomColorOptional.isPresent()) {
			DyeColor color = bottomColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectOpenCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.CROSS_BOTTOM_FULL, WindowVertexData.CROSS_BOTTOM_LEFT_STANDARD,
				WindowVertexData.CROSS_BOTTOM_RIGHT_STANDARD_OPPOSITE);
		}

		Optional<DyeColor> sideColorOptional = renderState.windowFrameContents.sideTop(direction);
		if (sideColorOptional.isPresent()) {
			DyeColor color = sideColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectClosedCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.CROSS_LEFT_STANDARD, WindowVertexData.CROSS_RIGHT_STANDARD_OPPOSITE);
		}
	}

	private static void collectDiamondFrameVertexData(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> output) {
		Optional<DyeColor> centerColorOptional = renderState.windowFrameContents.centerTop();
		if (centerColorOptional.isPresent()) {
			DyeColor color = centerColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectOpenCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.DIAMOND_CENTER_FULL, WindowVertexData.DIAMOND_CENTER_LEFT_STANDARD,
				WindowVertexData.DIAMOND_CENTER_RIGHT_STANDARD_OPPOSITE);
		}

		Optional<DyeColor> sideTopColorOptional = renderState.windowFrameContents.sideTop(direction);
		if (sideTopColorOptional.isPresent()) {
			DyeColor color = sideTopColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectClosedCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.DIAMOND_TOP_LEFT_STANDARD, WindowVertexData.DIAMOND_TOP_RIGHT_STANDARD_OPPOSITE);
		}

		Optional<DyeColor> sideBottomColorOptional = renderState.windowFrameContents.sideBottom(direction);
		if (sideBottomColorOptional.isPresent()) {
			DyeColor color = sideBottomColorOptional.get();

			Consumer<WindowVertexData> vertexConsumer = vertexData -> output.accept(color, vertexData.withDirection(direction));

			collectClosedCenter(renderState.connections, direction, vertexConsumer,
				WindowVertexData.DIAMOND_BOTTOM_LEFT_STANDARD, WindowVertexData.DIAMOND_BOTTOM_RIGHT_STANDARD_OPPOSITE);
		}
	}

	private static void collectOpenCenter(@NonNull ConnectionInfo connections, @NonNull Direction direction, Consumer<WindowVertexData> output,
																				WindowVertexData frontFull, WindowVertexData frontHalf,
																				WindowVertexData backHalf) {
		Direction front = direction.getCounterClockWise();
		Direction opposite = direction.getOpposite();
		Direction back = direction.getClockWise();

		boolean isFullCross = connections.has(front) && connections.has(opposite) && connections.has(back);

		// Front faces
		if (isFullCross) {
			// Revert to smaller segments when  it's a cross to avoid fighting issues.
			output.accept(frontHalf);
		} else if (connections.has(opposite)) {
			output.accept(frontFull);
		} else {
			output.accept(frontHalf);
		}

		// Back faces.
		// Don't render back face if the center is open, as this will be handled by the opposite front face.
		// However if it's a full cross then we do need to render the back face ourselves.
		if (isFullCross || !connections.has(opposite)) {
			output.accept(backHalf);
		}
	}

	private static void collectClosedCenter(@NonNull ConnectionInfo connections, @NonNull Direction direction, Consumer<WindowVertexData> output,
																					WindowVertexData frontHalf,
																					WindowVertexData backHalf) {
		output.accept(frontHalf);
		output.accept(backHalf);
	}

	@FunctionalInterface
	private interface DirectionCollector {
		void collect(@NonNull Direction direction, @NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> addVertexData);
	}

	@FunctionalInterface
	private interface CenterCollector {
		void collect(@NonNull WindowFrameBlockEntityRenderState renderState, @NonNull BiConsumer<DyeColor, WindowVertexData> addVertexData);
	}
}
