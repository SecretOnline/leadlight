package co.secretonline.leadlight.data;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum FrameMaterial implements StringRepresentable {
	IRON("iron");

	private final String prefix;

	FrameMaterial(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	@Override
	public @NonNull String getSerializedName() {
		return prefix;
	}
}
