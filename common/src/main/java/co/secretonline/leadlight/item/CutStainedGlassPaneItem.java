package co.secretonline.leadlight.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public class CutStainedGlassPaneItem extends Item {
	private final DyeColor color;

	public CutStainedGlassPaneItem(DyeColor color, Properties properties) {
		super(properties);

		this.color = color;
	}

	public DyeColor getColor() {
		return color;
	}
}
