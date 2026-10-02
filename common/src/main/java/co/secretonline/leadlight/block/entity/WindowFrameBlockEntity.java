package co.secretonline.leadlight.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WindowFrameBlockEntity extends BlockEntity {
	public WindowFrameBlockEntity(BlockPos worldPosition, BlockState blockState) {
		super(ModBlockEntities.WINDOW_FRAME_BLOCK_ENTITY.get(), worldPosition, blockState);
	}
}
