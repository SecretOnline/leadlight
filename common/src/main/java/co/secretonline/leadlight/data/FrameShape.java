package co.secretonline.leadlight.data;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum FrameShape implements StringRepresentable {
	LARGE("large", 1, 1);

	private final String prefix;
	private final int segmentsPerSide;
	private final int segmentsSharedAtPost;

	FrameShape(String prefix, int segmentsPerSide, int segmentsSharedAtPost) {
		this.prefix = prefix;
		this.segmentsPerSide = segmentsPerSide;
		this.segmentsSharedAtPost = segmentsSharedAtPost;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getSegmentsPerSide() {
		return segmentsPerSide;
	}

	public int getSegmentsSharedAtPost() {
		return segmentsSharedAtPost;
	}

	@Override
	public @NonNull String getSerializedName() {
		return prefix;
	}
}
