package co.secretonline.leadlight.mixin.client.data;

import net.minecraft.client.data.models.model.TextureSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TextureSlot.class)
public interface TextureSlotAccessor {
	@Invoker("create")
	static TextureSlot create(String id) {
		throw new AssertionError("Untransformed @Accessor");
	}

	@Invoker("create")
	static TextureSlot create(String id, TextureSlot parent) {
		throw new AssertionError("Untransformed @Accessor");
	}
}
