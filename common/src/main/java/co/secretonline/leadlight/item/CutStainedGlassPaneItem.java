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
		return switch (color) {
			case WHITE -> Blocks.WHITE_STAINED_GLASS_PANE;
			case ORANGE -> Blocks.ORANGE_STAINED_GLASS_PANE;
			case MAGENTA -> Blocks.MAGENTA_STAINED_GLASS_PANE;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_STAINED_GLASS_PANE;
			case YELLOW -> Blocks.YELLOW_STAINED_GLASS_PANE;
			case LIME -> Blocks.LIME_STAINED_GLASS_PANE;
			case PINK -> Blocks.PINK_STAINED_GLASS_PANE;
			case GRAY -> Blocks.GRAY_STAINED_GLASS_PANE;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_STAINED_GLASS_PANE;
			case CYAN -> Blocks.CYAN_STAINED_GLASS_PANE;
			case PURPLE -> Blocks.PURPLE_STAINED_GLASS_PANE;
			case BLUE -> Blocks.BLUE_STAINED_GLASS_PANE;
			case BROWN -> Blocks.BROWN_STAINED_GLASS_PANE;
			case GREEN -> Blocks.GREEN_STAINED_GLASS_PANE;
			case RED -> Blocks.RED_STAINED_GLASS_PANE;
			case BLACK -> Blocks.BLACK_STAINED_GLASS_PANE;
		};
	}

	public Block getGlassBlock() {
		return switch (color) {
			case WHITE -> Blocks.WHITE_STAINED_GLASS;
			case ORANGE -> Blocks.ORANGE_STAINED_GLASS;
			case MAGENTA -> Blocks.MAGENTA_STAINED_GLASS;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_STAINED_GLASS;
			case YELLOW -> Blocks.YELLOW_STAINED_GLASS;
			case LIME -> Blocks.LIME_STAINED_GLASS;
			case PINK -> Blocks.PINK_STAINED_GLASS;
			case GRAY -> Blocks.GRAY_STAINED_GLASS;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_STAINED_GLASS;
			case CYAN -> Blocks.CYAN_STAINED_GLASS;
			case PURPLE -> Blocks.PURPLE_STAINED_GLASS;
			case BLUE -> Blocks.BLUE_STAINED_GLASS;
			case BROWN -> Blocks.BROWN_STAINED_GLASS;
			case GREEN -> Blocks.GREEN_STAINED_GLASS;
			case RED -> Blocks.RED_STAINED_GLASS;
			case BLACK -> Blocks.BLACK_STAINED_GLASS;
		};
	}
}
