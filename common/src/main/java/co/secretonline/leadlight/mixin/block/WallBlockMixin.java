package co.secretonline.leadlight.mixin.block;

import co.secretonline.leadlight.tag.ModBlockTags;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WallBlock.class)
public class WallBlockMixin {
	@WrapMethod(method = "connectsTo")
	public boolean leadlight$wrapConnectsTo(BlockState state, boolean faceSolid, Direction direction, Operation<Boolean> original) {
		return original.call(state, faceSolid, direction) || state.is(ModBlockTags.WINDOW_FRAME);
	}
}
