package co.secretonline.leadlight.sound;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ModSounds {
	public static final Identifier WINDOW_FRAME_ADD_PANE_ID = Leadlight.id("block.window_frame.add_pane");
	public static final Supplier<SoundEvent> WINDOW_FRAME_ADD_PANE = registerSound(WINDOW_FRAME_ADD_PANE_ID);
	public static final Identifier WINDOW_FRAME_REMOVE_PANE_ID = Leadlight.id("block.window_frame.remove_pane");
	public static final Supplier<SoundEvent> WINDOW_FRAME_REMOVE_PANE = registerSound(WINDOW_FRAME_REMOVE_PANE_ID);

	private static Supplier<SoundEvent> registerSound(Identifier identifier) {
		return ServerServiceLoader.REGISTRY.register(
			BuiltInRegistries.SOUND_EVENT,
			identifier,
			()-> SoundEvent.createVariableRangeEvent(identifier));
	}
	public static void initialize() {
	}
}
