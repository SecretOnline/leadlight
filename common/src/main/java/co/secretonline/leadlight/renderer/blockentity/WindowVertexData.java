package co.secretonline.leadlight.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;

public class WindowVertexData {
	private static final float START = 1f;
	private static final float START_MID = 7f;
	private static final float TRUE_MID = 8f;
	private static final float END_MID = 9f;
	private static final float END = 15f;
	private static final float DEPTH_OFFSET = 0.95f;
	private static final float START_EDGE = TRUE_MID - DEPTH_OFFSET;
	private static final float END_EDGE = TRUE_MID + DEPTH_OFFSET;

	public static final WindowVertexData FULL_STANDARD = WindowVertexData.quad(
		START, START,
		END, START,
		END, END,
		START, END
	);
	public static final WindowVertexData FULL_STANDARD_OPPOSITE = FULL_STANDARD.withDirection(Direction.SOUTH);
	public static final WindowVertexData VERTICAL_LEFT_STANDARD = WindowVertexData.quad(
		START, START,
		START_MID, START,
		START_MID, END,
		START, END
	);
	public static final WindowVertexData VERTICAL_LEFT_STANDARD_OPPOSITE = VERTICAL_LEFT_STANDARD.withDirection(Direction.SOUTH);
	public static final WindowVertexData VERTICAL_LEFT_OUTSIDE = WindowVertexData.quad(
		START, START,
		END_EDGE, START,
		END_EDGE, END,
		START, END
	);
	public static final WindowVertexData VERTICAL_LEFT_OUTSIDE_OPPOSITE = VERTICAL_LEFT_OUTSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData VERTICAL_LEFT_INSIDE = WindowVertexData.quad(
		START, START,
		START_EDGE, START,
		START_EDGE, END,
		START, END
	);
	public static final WindowVertexData VERTICAL_LEFT_INSIDE_OPPOSITE = VERTICAL_LEFT_INSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData VERTICAL_RIGHT_STANDARD = WindowVertexData.quad(
		END_MID, START,
		END, START,
		END, END,
		END_MID, END
	);
	public static final WindowVertexData VERTICAL_RIGHT_STANDARD_OPPOSITE = VERTICAL_RIGHT_STANDARD.withDirection(Direction.SOUTH);
	public static final WindowVertexData VERTICAL_RIGHT_OUTSIDE = WindowVertexData.quad(
		START_EDGE, START,
		END, START,
		END, END,
		START_EDGE, END
	);
	public static final WindowVertexData VERTICAL_RIGHT_OUTSIDE_OPPOSITE = VERTICAL_RIGHT_OUTSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData VERTICAL_RIGHT_INSIDE = WindowVertexData.quad(
		END_EDGE, START,
		END, START,
		END, END,
		END_EDGE, END
	);
	public static final WindowVertexData VERTICAL_RIGHT_INSIDE_OPPOSITE = VERTICAL_RIGHT_INSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData HORIZONTAL_TOP_FULL = WindowVertexData.quad(
		START, START,
		END, START,
		END, START_MID,
		START, START_MID
	);
	public static final WindowVertexData HORIZONTAL_TOP_FULL_OPPOSITE = HORIZONTAL_TOP_FULL.withDirection(Direction.SOUTH);
	public static final WindowVertexData HORIZONTAL_BOTTOM_FULL = WindowVertexData.quad(
		START, END_MID,
		END, END_MID,
		END, END,
		START, END
	);
	public static final WindowVertexData HORIZONTAL_BOTTOM_FULL_OPPOSITE = HORIZONTAL_BOTTOM_FULL.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_TOP_LEFT_STANDARD = WindowVertexData.quad(
		START, START,
		START_MID, START,
		START_MID, START_MID,
		START, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_LEFT_STANDARD_OPPOSITE = SQUARE_TOP_LEFT_STANDARD.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_TOP_LEFT_OUTSIDE = WindowVertexData.quad(
		START, START,
		END_EDGE, START,
		END_EDGE, START_MID,
		START, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_LEFT_OUTSIDE_OPPOSITE = SQUARE_TOP_LEFT_OUTSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_TOP_LEFT_INSIDE = WindowVertexData.quad(
		START, START,
		START_EDGE, START,
		START_EDGE, START_MID,
		START, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_LEFT_INSIDE_OPPOSITE = SQUARE_TOP_LEFT_INSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_TOP_RIGHT_STANDARD = WindowVertexData.quad(
		END_MID, START,
		END, START,
		END, START_MID,
		END_MID, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_RIGHT_STANDARD_OPPOSITE = SQUARE_TOP_RIGHT_STANDARD.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_TOP_RIGHT_OUTSIDE = WindowVertexData.quad(
		START_EDGE, START,
		END, START,
		END, START_MID,
		START_EDGE, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_RIGHT_OUTSIDE_OPPOSITE = SQUARE_TOP_RIGHT_OUTSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_TOP_RIGHT_INSIDE = WindowVertexData.quad(
		END_EDGE, START,
		END, START,
		END, START_MID,
		END_EDGE, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_RIGHT_INSIDE_OPPOSITE = SQUARE_TOP_RIGHT_INSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_STANDARD = WindowVertexData.quad(
		START, END_MID,
		START_MID, END_MID,
		START_MID, END,
		START, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_STANDARD_OPPOSITE = SQUARE_BOTTOM_LEFT_STANDARD.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_OUTSIDE = WindowVertexData.quad(
		START, END_MID,
		END_EDGE, END_MID,
		END_EDGE, END,
		START, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_OUTSIDE_OPPOSITE = SQUARE_BOTTOM_LEFT_OUTSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_INSIDE = WindowVertexData.quad(
		START, END_MID,
		START_EDGE, END_MID,
		START_EDGE, END,
		START, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_INSIDE_OPPOSITE = SQUARE_BOTTOM_LEFT_INSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_STANDARD = WindowVertexData.quad(
		END_MID, END_MID,
		END, END_MID,
		END, END,
		END_MID, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_STANDARD_OPPOSITE = SQUARE_BOTTOM_RIGHT_STANDARD.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_OUTSIDE = WindowVertexData.quad(
		START_EDGE, END_MID,
		END, END_MID,
		END, END,
		START_EDGE, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_OUTSIDE_OPPOSITE = SQUARE_BOTTOM_RIGHT_OUTSIDE.withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_INSIDE = WindowVertexData.quad(
		END_EDGE, END_MID,
		END, END_MID,
		END, END,
		END_EDGE, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_INSIDE_OPPOSITE = SQUARE_BOTTOM_RIGHT_INSIDE.withDirection(Direction.SOUTH);

	public static WindowVertexData triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		return triangle(new Vector2f(x1, y1), new Vector2f(x2, y2), new Vector2f(x3, y3));
	}

	public static WindowVertexData triangle(Vector2f pos1, Vector2f pos2, Vector2f pos3) {
		return quad(pos1, pos2, pos3, pos1);
	}

	public static WindowVertexData quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		return fromPixelList(new Vector2f(x1, y1), new Vector2f(x2, y2), new Vector2f(x3, y3), new Vector2f(x4, y4));
	}

	public static WindowVertexData quad(Vector2f pos1, Vector2f pos2, Vector2f pos3, Vector2f pos4) {
		return fromPixelList(pos1, pos2, pos3, pos4);
	}

	public static WindowVertexData fromPixelList(Vector2f... pixels) {
		return new WindowVertexData(Arrays.stream(pixels)
			.map(vec -> vec.mul(0.0625f))
			.map(Vertex::fromUv).toList());
	}

	private final List<Vertex> vertices;

	private WindowVertexData(List<Vertex> vertices) {
		this.vertices = vertices;
	}

	public WindowVertexData withDirection(Direction newDirection) {
		return new WindowVertexData(vertices.stream()
			.map(vertex -> vertex.withDirection(newDirection))
			.toList());
	}

	public VertexConsumer submit(VertexConsumer consumer, PoseStack.Pose pose, TextureAtlasSprite sprite, int lightCoords) {
		VertexConsumer current = consumer;
		for (Vertex vertex : vertices) {
			current = vertex.submit(current, pose, sprite, lightCoords);
		}

		return current;
	}

	public record Vertex(Vector3f position, Vector2f uv) {
		private static final Vector3f PLAIN_NORMAL = new Vector3f(0, 1, 0);

		public static Vertex fromUv(Vector2f uv) {
			return new Vertex(new Vector3f(0.5f - DEPTH_OFFSET, 1 - uv.y, uv.x), uv);
		}

		public Vertex withDirection(Direction newDirection) {
			float angle = Direction.getYRot(newDirection) + 180f;
			float rad = (float) Math.toRadians(angle);
			float cos = Mth.cos(rad);
			float sin = Mth.sin(rad);

			float x = position.x() - 0.5f;
			float z = position.z() - 0.5f;

			float newX = x * cos + z * sin + 0.5f;
			float newZ = -x * sin + z * cos + 0.5f;

			return new Vertex(new Vector3f(newX, position.y(), newZ), uv);
		}

		public VertexConsumer submit(VertexConsumer consumer, PoseStack.Pose pose, TextureAtlasSprite sprite, int lightCoords) {
			float spriteWidth = sprite.getU1() - sprite.getU0();
			float spriteHeight = sprite.getV1() - sprite.getV0();
			float u = sprite.getU0() + (uv.x * spriteWidth);
			float v = sprite.getV0() + (uv.y * spriteHeight);

			return consumer
				.addVertex(pose, position.x(), position.y(), position.z())
				.setColor(-1)
				.setUv(u, v)
				.setOverlay(OverlayTexture.NO_OVERLAY)
				.setLight(lightCoords)
				.setNormal(pose, PLAIN_NORMAL);
		}
	}
}
