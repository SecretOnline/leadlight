package co.secretonline.leadlight.renderer.blockentity;

import co.secretonline.leadlight.block.entity.WindowFrameBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class WindowFrameBlockEntityRenderer implements BlockEntityRenderer<WindowFrameBlockEntity, WindowFrameBlockEntityRenderState> {

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
	}

	@Override
	public void submit(@NonNull WindowFrameBlockEntityRenderState windowFrameBlockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {

		poseStack.pushPose();

		submitNodeCollector.submitCustomGeometry(
			poseStack,
			RenderTypes.translucentMovingBlock(),
			(pose, buffer) -> renderCustomGeometry(windowFrameBlockEntityRenderState, pose, buffer));

		poseStack.popPose();
	}

	public void renderCustomGeometry(@NonNull WindowFrameBlockEntityRenderState windowFrameBlockEntityRenderState, PoseStack.@NonNull Pose pose, @NonNull VertexConsumer buffer) {
		SpriteId spriteId = new SpriteId(TextureAtlas.LOCATION_BLOCKS, Identifier.withDefaultNamespace("block/red_stained_glass"));
		TextureAtlasSprite sprite = spriteGetter.get(spriteId);

		Vector3f normal = new Vector3f(0, 1, 0);

		int overlay = OverlayTexture.NO_OVERLAY;

		sprite.wrap(buffer)
			.addVertex(pose, 0, 0, 0)
			.setColor(-1)
			.setUv(sprite.getU0(), sprite.getV0())
			.setOverlay(overlay)
			.setLight(windowFrameBlockEntityRenderState.lightCoords)
			.setNormal(pose, normal)
			.addVertex(pose, 0, 0, 1)
			.setColor(-1)
			.setUv(sprite.getU0(), sprite.getV1())
			.setOverlay(overlay)
			.setLight(windowFrameBlockEntityRenderState.lightCoords)
			.setNormal(pose, normal)
			.addVertex(pose, 0, 1, 1)
			.setColor(-1)
			.setUv(sprite.getU1(), sprite.getV1())
			.setOverlay(overlay)
			.setLight(windowFrameBlockEntityRenderState.lightCoords)
			.setNormal(pose, normal)
			.addVertex(pose, 0, 0, 0)
			.setColor(-1)
			.setUv(sprite.getU0(), sprite.getV0())
			.setOverlay(overlay)
			.setLight(windowFrameBlockEntityRenderState.lightCoords)
			.setNormal(pose, normal);
	}
}
