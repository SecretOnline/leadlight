package co.secretonline.leadlight.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class CutStainedGlassPaneItem extends Item {
	private static final Map<DyeColor, CutStainedGlassPaneItem> COLOR_REGISTRY = new HashMap<>();
	private final DyeColor color;

	public static CutStainedGlassPaneItem ofColor(DyeColor color) {
		CutStainedGlassPaneItem item = COLOR_REGISTRY.get(color);
		if (item == null) {
			throw new IllegalArgumentException("Color " + color + " is not registered");
		}
		return item;
	}

	public CutStainedGlassPaneItem(DyeColor color, Properties properties) {
		super(properties);

		this.color = color;
		COLOR_REGISTRY.put(color, this);
	}

	public DyeColor getColor() {
		return color;
	}

	public Block getPaneBlock() {
		return Blocks.STAINED_GLASS_PANE.pick(color);
	}

	public Block getGlassBlock() {
		return Blocks.STAINED_GLASS.pick(color);
	}
}
