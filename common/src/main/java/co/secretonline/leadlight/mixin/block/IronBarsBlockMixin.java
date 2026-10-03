package co.secretonline.leadlight.mixin.block;

import co.secretonline.leadlight.tag.ModBlockTags;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IronBarsBlock.class)
public class IronBarsBlockMixin {
	// Love the typo in the method name
	@WrapMethod(method = "attachsTo")
	public boolean leadlight$wrapAttachsTo(BlockState state, boolean faceSolid, Operation<Boolean> original) {
		return original.call(state, faceSolid) || state.is(ModBlockTags.WINDOW_FRAME);
	}
}
