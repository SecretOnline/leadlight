package co.secretonline.leadlight.data;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum FrameShape implements StringRepresentable {
	LARGE("large", true, false, false, false),
	VERTICAL("vertical", false, false, true, true),
	HORIZONTAL("horizontal", true, true, false, false),
	SQUARE("square", false, false, true, true);

	private final String prefix;
	private final boolean hasCenterTopSlot;
	private final boolean hasCenterBottomSlot;
	private final boolean hasSideTopSlot;
	private final boolean hasSideBottomSlot;

	FrameShape(String prefix, boolean hasCenterTopSlot, boolean hasCenterBottomSlot, boolean hasSideTopSlot, boolean hasSideBottomSlot) {
		this.prefix = prefix;
		this.hasCenterTopSlot = hasCenterTopSlot;
		this.hasCenterBottomSlot = hasCenterBottomSlot;
		this.hasSideTopSlot = hasSideTopSlot;
		this.hasSideBottomSlot = hasSideBottomSlot;
	}

	public String getPrefix() {
		return prefix;
	}

	public boolean hasCenterTopSlot() {
		return hasCenterTopSlot;
	}

	public boolean hasCenterBottomSlot() {
		return hasCenterBottomSlot;
	}

	public boolean hasSideTopSlot() {
		return hasSideTopSlot;
	}

	public boolean hasSideBottomSlot() {
		return hasSideBottomSlot;
	}

	@Override
	public @NonNull String getSerializedName() {
		return prefix;
	}
}
