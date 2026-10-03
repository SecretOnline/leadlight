package co.secretonline.leadlight.data;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;

public enum FrameMaterial implements StringRepresentable {
	IRON("iron", Items.IRON_NUGGET);

	private final String prefix;
	private final ItemLike craftingMaterial;

	FrameMaterial(String prefix, ItemLike craftingMaterial) {
		this.prefix = prefix;
		this.craftingMaterial = craftingMaterial;
	}

	public String getPrefix() {
		return prefix;
	}

	public ItemLike getCraftingMaterial() {
		return craftingMaterial;
	}

	@Override
	public @NonNull String getSerializedName() {
		return prefix;
	}
}
