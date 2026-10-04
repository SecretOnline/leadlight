package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.sound.ModSounds;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class FabricModSoundsProvider extends FabricSoundsProvider {
	public FabricModSoundsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.@NonNull Provider registryLookup, SoundExporter exporter) {
		exporter.add(ModSounds.WINDOW_FRAME_ADD_PANE.get(), SoundTypeBuilder.of(ModSounds.WINDOW_FRAME_ADD_PANE.get())
			.sound(SoundTypeBuilder.RegistrationBuilder.ofEvent(SoundEvents.ITEM_FRAME_PLACE))
			.subtitle("subtitles.block.window_frame.add_pane"));
		exporter.add(ModSounds.WINDOW_FRAME_REMOVE_PANE.get(), SoundTypeBuilder.of(ModSounds.WINDOW_FRAME_REMOVE_PANE.get())
			.sound(SoundTypeBuilder.RegistrationBuilder.ofEvent(SoundEvents.ITEM_FRAME_REMOVE_ITEM))
			.subtitle("subtitles.block.window_frame.remove_pane"));
	}

	@Override
	public @NonNull String getName() {
		return "Leadlight sounds";
	}
}
