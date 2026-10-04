package co.secretonline.leadlight.data;

import co.secretonline.leadlight.block.WindowFrameBlock;
import co.secretonline.leadlight.renderer.blockentity.WindowFrameBlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public record ConnectionInfo(boolean north, boolean east, boolean south, boolean west) {
	public static ConnectionInfo fromBlockState(BlockState blockState) {
		if (!(blockState.getBlock() instanceof WindowFrameBlock)) {
			throw new IllegalArgumentException("BlockState is not a WindowFrameBlock: " + blockState);
		}

		return new ConnectionInfo(
			blockState.getValue(WindowFrameBlock.NORTH),
			blockState.getValue(WindowFrameBlock.EAST),
			blockState.getValue(WindowFrameBlock.SOUTH),
			blockState.getValue(WindowFrameBlock.WEST));
	}

	public boolean has(Direction direction) {
		return switch (direction) {
			case NORTH -> this.north;
			case EAST -> this.east;
			case SOUTH -> this.south;
			case WEST -> this.west;
			default -> throw new IllegalArgumentException("Invalid direction: " + direction);
		};
	}
}
