package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.sound.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class NeoForgeModSoundsProvider extends SoundDefinitionsProvider {
	protected NeoForgeModSoundsProvider(PackOutput output) {
		super(output, Leadlight.MOD_ID);
	}

	@Override
	public void registerSounds() {
		add(ModSounds.WINDOW_FRAME_ADD_PANE.get(), SoundDefinition.definition()
			.with(sound(SoundEvents.ITEM_FRAME_PLACE.location()))
			.subtitle("subtitles.block.window_frame.add_pane"));
		add(ModSounds.WINDOW_FRAME_REMOVE_PANE.get(), SoundDefinition.definition()
			.with(sound(SoundEvents.ITEM_FRAME_REMOVE_ITEM.location()))
			.subtitle("subtitles.block.window_frame.remove_pane"));
	}
}
