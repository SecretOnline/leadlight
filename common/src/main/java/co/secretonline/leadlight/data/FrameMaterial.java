package co.secretonline.leadlight.data;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.jspecify.annotations.NonNull;

public record FrameMaterial(String name, ItemLike craftingMaterial, NoteBlockInstrument instrument, SoundType soundType) implements StringRepresentable {
	public static final FrameMaterial IRON = new FrameMaterial("iron", Items.IRON_NUGGET, NoteBlockInstrument.HAT, SoundType.IRON);

	@Override
	public @NonNull String getSerializedName() {
		return name;
	}
}
