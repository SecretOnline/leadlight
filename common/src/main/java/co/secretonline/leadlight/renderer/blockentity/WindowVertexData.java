package co.secretonline.leadlight.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
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

	private static final float CORNER_INSET = 1.f / Mth.SQRT_OF_TWO;
	private static final float START_CORNER = START + CORNER_INSET;
	private static final float START_EDGE_INNER_CORNER = START_EDGE - CORNER_INSET;
	private static final float START_MID_OUTER_CORNER = START_MID - CORNER_INSET;
	private static final float START_MID_INNER_CORNER = START_MID + CORNER_INSET;
	private static final float END_MID_INNER_CORNER = END_MID - CORNER_INSET;
	private static final float END_MID_OUTER_CORNER = END_MID + CORNER_INSET;
	private static final float END_EDGE_INNER_CORNER = END_EDGE + CORNER_INSET;
	private static final float END_CORNER = END - CORNER_INSET;

	public static final WindowVertexData FULL_STANDARD = WindowVertexData.quad(
		START, START,
		END, END
	);

	public static final WindowVertexData VERTICAL_LEFT_STANDARD = WindowVertexData.quad(
		START, START,
		TRUE_MID, END
	);
	public static final WindowVertexData VERTICAL_LEFT_OUTSIDE = WindowVertexData.quad(
		START, START,
		END_EDGE, END
	);
	public static final WindowVertexData VERTICAL_LEFT_INSIDE = WindowVertexData.quad(
		START, START,
		START_EDGE, END
	);
	public static final WindowVertexData VERTICAL_RIGHT_STANDARD_OPPOSITE = WindowVertexData.quad(
		TRUE_MID, START,
		END, END
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData VERTICAL_RIGHT_OUTSIDE_OPPOSITE = WindowVertexData.quad(
		START_EDGE, START,
		END, END
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData VERTICAL_RIGHT_INSIDE_OPPOSITE = WindowVertexData.quad(
		END_EDGE, START,
		END, END
	).withDirection(Direction.SOUTH);

	public static final WindowVertexData HORIZONTAL_TOP_FULL = WindowVertexData.quad(
		START, START,
		END, START_MID
	);
	public static final WindowVertexData HORIZONTAL_BOTTOM_FULL = WindowVertexData.quad(
		START, END_MID,
		END, END
	);

	public static final WindowVertexData SQUARE_TOP_LEFT_STANDARD = WindowVertexData.quad(
		START, START,
		TRUE_MID, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_LEFT_OUTSIDE = WindowVertexData.quad(
		START, START,
		END_EDGE, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_LEFT_INSIDE = WindowVertexData.quad(
		START, START,
		START_EDGE, START_MID
	);
	public static final WindowVertexData SQUARE_TOP_RIGHT_STANDARD_OPPOSITE = WindowVertexData.quad(
		TRUE_MID, START,
		END, START_MID
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_TOP_RIGHT_OUTSIDE_OPPOSITE = WindowVertexData.quad(
		START_EDGE, START,
		END, START_MID
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_TOP_RIGHT_INSIDE_OPPOSITE = WindowVertexData.quad(
		END_EDGE, START,
		END, START_MID
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_STANDARD = WindowVertexData.quad(
		START, END_MID,
		TRUE_MID, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_OUTSIDE = WindowVertexData.quad(
		START, END_MID,
		END_EDGE, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_LEFT_INSIDE = WindowVertexData.quad(
		START, END_MID,
		START_EDGE, END
	);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_STANDARD_OPPOSITE = WindowVertexData.quad(
		TRUE_MID, END_MID,
		END, END
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_OUTSIDE_OPPOSITE = WindowVertexData.quad(
		START_EDGE, END_MID,
		END, END
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData SQUARE_BOTTOM_RIGHT_INSIDE_OPPOSITE = WindowVertexData.quad(
		END_EDGE, END_MID,
		END, END
	).withDirection(Direction.SOUTH);

	public static final WindowVertexData POST_FULL = post(START, END);
	public static final WindowVertexData POST_TOP = post(START, START_MID);
	public static final WindowVertexData POST_BOTTOM = post(END_MID, END);

	public static final WindowVertexData CROSS_LEFT_STANDARD = WindowVertexData.quad(
		START, START_CORNER,
		START, END_CORNER,
		START_MID, END_MID_INNER_CORNER,
		START_MID, START_MID_INNER_CORNER);
	public static final WindowVertexData CROSS_RIGHT_STANDARD_OPPOSITE = WindowVertexData.quad(
		END, START_CORNER,
		END_MID, START_MID_INNER_CORNER,
		END_MID, END_MID_INNER_CORNER,
		END, END_CORNER
	).withDirection(Direction.SOUTH);

	public static final WindowVertexData CROSS_TOP_FULL = WindowVertexData.quad(
		START_CORNER, START,
		START_MID_INNER_CORNER, START_MID,
		END_MID_INNER_CORNER, START_MID,
		END_CORNER, START);
	public static final WindowVertexData CROSS_TOP_LEFT_STANDARD = WindowVertexData.quad(
		START_CORNER, START,
		START_MID_INNER_CORNER, START_MID,
		TRUE_MID, START_MID,
		TRUE_MID, START);
	public static final WindowVertexData CROSS_TOP_LEFT_OUTSIDE = WindowVertexData.quad(
		START_CORNER, START,
		START_MID_INNER_CORNER, START_MID,
		END_EDGE, START_MID,
		END_EDGE, START);
	public static final WindowVertexData CROSS_TOP_LEFT_INSIDE = WindowVertexData.triangle(
		START_CORNER, START,
		START_EDGE, START_EDGE_INNER_CORNER,
		START_EDGE, START);
	public static final WindowVertexData CROSS_TOP_RIGHT_STANDARD_OPPOSITE = WindowVertexData.quad(
		END_CORNER, START,
		TRUE_MID, START,
		TRUE_MID, START_MID,
		END_MID_INNER_CORNER, START_MID
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData CROSS_TOP_RIGHT_OUTSIDE_OPPOSITE = WindowVertexData.quad(
		END_CORNER, START,
		START_EDGE, START,
		START_EDGE, START_MID,
		END_MID_INNER_CORNER, START_MID
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData CROSS_TOP_RIGHT_INSIDE_OPPOSITE = WindowVertexData.triangle(
		END_CORNER, START,
		END_EDGE, START,
		END_EDGE, START_EDGE_INNER_CORNER
	).withDirection(Direction.SOUTH);

	public static final WindowVertexData CROSS_BOTTOM_FULL = WindowVertexData.quad(
		START_CORNER, END,
		END_CORNER, END,
		END_MID_INNER_CORNER, END_MID,
		START_MID_INNER_CORNER, END_MID
	);
	public static final WindowVertexData CROSS_BOTTOM_LEFT_STANDARD = WindowVertexData.quad(
		START_CORNER, END,
		TRUE_MID, END,
		TRUE_MID, END_MID,
		START_MID_INNER_CORNER, END_MID
	);
	public static final WindowVertexData CROSS_BOTTOM_LEFT_OUTSIDE = WindowVertexData.quad(
		START_CORNER, END,
		END_EDGE, END,
		END_EDGE, END_MID,
		START_MID_INNER_CORNER, END_MID
	);
	public static final WindowVertexData CROSS_BOTTOM_LEFT_INSIDE = WindowVertexData.triangle(
		START_CORNER, END,
		START_EDGE, END,
		START_EDGE, END_EDGE_INNER_CORNER
	);
	public static final WindowVertexData CROSS_BOTTOM_RIGHT_STANDARD_OPPOSITE = WindowVertexData.quad(
		END_CORNER, END,
		END_MID_INNER_CORNER, END_MID,
		TRUE_MID, END_MID,
		TRUE_MID, END
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData CROSS_BOTTOM_RIGHT_OUTSIDE_OPPOSITE = WindowVertexData.quad(
		END_CORNER, END,
		END_MID_INNER_CORNER, END_MID,
		START_EDGE, END_MID,
		START_EDGE, END
	).withDirection(Direction.SOUTH);
	public static final WindowVertexData CROSS_BOTTOM_RIGHT_INSIDE_OPPOSITE = WindowVertexData.triangle(
		END_CORNER, END,
		END_EDGE, END_EDGE_INNER_CORNER,
		END_EDGE, END
	).withDirection(Direction.SOUTH);

	public static WindowVertexData post(float y1, float y2) {
		WindowVertexData side = quad(TRUE_MID - DEPTH_OFFSET, y1, TRUE_MID + DEPTH_OFFSET, y2);
		WindowVertexData corner = merge(side, side.withDirection(Direction.EAST));
		return merge(corner, corner.withDirection(Direction.SOUTH));
	}

	public static WindowVertexData triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		return fromPixelList(new Vector2f(x1, y1), new Vector2f(x2, y2), new Vector2f(x3, y3), new Vector2f(x1, y1));
	}

	public static WindowVertexData quad(float x1, float y1, float x2, float y2) {
		return fromPixelList(new Vector2f(x1, y1), new Vector2f(x1, y2), new Vector2f(x2, y2), new Vector2f(x2, y1));
	}

	public static WindowVertexData quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		return fromPixelList(new Vector2f(x1, y1), new Vector2f(x2, y2), new Vector2f(x3, y3), new Vector2f(x4, y4));
	}

	public static WindowVertexData merge(WindowVertexData a, WindowVertexData b) {
		List<Vertex> newVertices = new ArrayList<>(a.vertices.size() + b.vertices.size());
		newVertices.addAll(a.vertices);
		newVertices.addAll(b.vertices);

		return new WindowVertexData(newVertices);
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
			return new Vertex(new Vector3f(0.5f - (DEPTH_OFFSET / 16f), 1 - uv.y, uv.x), uv);
		}

		public Vertex withDirection(Direction newDirection) {
			float angle = 180f - Direction.getYRot(newDirection);
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
