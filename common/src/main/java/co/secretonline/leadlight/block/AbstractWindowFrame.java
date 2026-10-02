package co.secretonline.leadlight.block;

import co.secretonline.leadlight.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.function.Function;

public abstract class AbstractWindowFrame extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty NORTH = PipeBlock.NORTH;
	public static final BooleanProperty EAST = PipeBlock.EAST;
	public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
	public static final BooleanProperty WEST = PipeBlock.WEST;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION
		.entrySet()
		.stream()
		.filter(e -> e.getKey().getAxis().isHorizontal())
		.collect(Util.toMap());
	private final Function<BlockState, VoxelShape> shapes;

	protected AbstractWindowFrame(Properties properties) {
		super(properties);

		this.shapes = this.makeShapes();
	}

	protected BlockState getAbstractWindowFrameBlockState() {
		return this.stateDefinition.any()
			.setValue(NORTH, false)
			.setValue(EAST, false)
			.setValue(SOUTH, false)
			.setValue(WEST, false)
			.setValue(WATERLOGGED, false);
	}

	protected Function<BlockState, VoxelShape> makeShapes(
		) {
		VoxelShape post = Block.column(2.f, 0.f, 16.f);
		Map<Direction, VoxelShape> arms = Shapes.rotateHorizontal(Block.boxZ(2.f, 0.f, 16.f, 0.f, 8.f));
		return this.getShapeForEachState(state -> {
			VoxelShape shape = post;

			for (Map.Entry<Direction, BooleanProperty> entry : PROPERTY_BY_DIRECTION.entrySet()) {
				if ((Boolean)state.getValue((Property)entry.getValue())) {
					shape = Shapes.or(shape, arms.get(entry.getKey()));
				}
			}

			return shape;
		}, WATERLOGGED);
	}

	@Override
	protected boolean propagatesSkylightDown(final BlockState state) {
		return !(Boolean)state.getValue(WATERLOGGED);
	}

	@Override
	protected @NonNull VoxelShape getShape(final @NonNull BlockState state, final @NonNull BlockGetter level, final @NonNull BlockPos pos, final @NonNull CollisionContext context) {
		return this.shapes.apply(state);
	}

	@Override
	protected @NonNull VoxelShape getCollisionShape(final @NonNull BlockState state, final @NonNull BlockGetter level, final @NonNull BlockPos pos, final @NonNull CollisionContext context) {
		return this.shapes.apply(state);
	}

	@Override
	protected @NonNull FluidState getFluidState(final BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected boolean isPathfindable(final @NonNull BlockState state, final @NonNull PathComputationType type) {
		return false;
	}

	@Override
	protected @NonNull BlockState rotate(final @NonNull BlockState state, final Rotation rotation) {
		return switch (rotation) {
			case CLOCKWISE_180 -> state.setValue(NORTH, state.getValue(SOUTH))
				.setValue(EAST, state.getValue(WEST))
				.setValue(SOUTH, state.getValue(NORTH))
				.setValue(WEST, state.getValue(EAST));
			case COUNTERCLOCKWISE_90 -> state.setValue(NORTH, state.getValue(EAST))
				.setValue(EAST, state.getValue(SOUTH))
				.setValue(SOUTH, state.getValue(WEST))
				.setValue(WEST, state.getValue(NORTH));
			case CLOCKWISE_90 -> state.setValue(NORTH, state.getValue(WEST))
				.setValue(EAST, state.getValue(NORTH))
				.setValue(SOUTH, state.getValue(EAST))
				.setValue(WEST, state.getValue(SOUTH));
			default -> state;
		};
	}

	@Override
	protected @NonNull BlockState mirror(final @NonNull BlockState state, final Mirror mirror) {
		return switch (mirror) {
			case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
			case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
			default -> super.mirror(state, mirror);
		};
	}

	@Override
	public BlockState getStateForPlacement(final BlockPlaceContext context) {
		BlockGetter level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		FluidState replacedFluidState = context.getLevel().getFluidState(context.getClickedPos());
		BlockPos north = pos.north();
		BlockPos south = pos.south();
		BlockPos west = pos.west();
		BlockPos east = pos.east();
		BlockState northState = level.getBlockState(north);
		BlockState southState = level.getBlockState(south);
		BlockState westState = level.getBlockState(west);
		BlockState eastState = level.getBlockState(east);
		return this.defaultBlockState()
			.setValue(NORTH, this.attachesTo(northState, northState.isFaceSturdy(level, north, Direction.SOUTH)))
			.setValue(SOUTH, this.attachesTo(southState, southState.isFaceSturdy(level, south, Direction.NORTH)))
			.setValue(WEST, this.attachesTo(westState, westState.isFaceSturdy(level, west, Direction.EAST)))
			.setValue(EAST, this.attachesTo(eastState, eastState.isFaceSturdy(level, east, Direction.WEST)))
			.setValue(WATERLOGGED, replacedFluidState.is(Fluids.WATER));
	}

	@Override
	protected @NonNull BlockState updateShape(
		final BlockState state,
		final @NonNull LevelReader level,
		final @NonNull ScheduledTickAccess ticks,
		final @NonNull BlockPos pos,
		final @NonNull Direction directionToNeighbour,
		final @NonNull BlockPos neighbourPos,
		final @NonNull BlockState neighbourState,
		final @NonNull RandomSource random
	) {
		if (state.getValue(WATERLOGGED)) {
			ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return directionToNeighbour.getAxis().isHorizontal()
			? state.setValue(
			PROPERTY_BY_DIRECTION.get(directionToNeighbour),
			this.attachesTo(neighbourState, neighbourState.isFaceSturdy(level, neighbourPos, directionToNeighbour.getOpposite()))
		)
			: super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
	}

	@Override
	protected @NonNull VoxelShape getVisualShape(final @NonNull BlockState state, final @NonNull BlockGetter level, final @NonNull BlockPos pos, final @NonNull CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected boolean skipRendering(final @NonNull BlockState state, final BlockState neighborState, final @NonNull Direction direction) {
		if (neighborState.is(this)
			|| neighborState.is(BlockTags.BARS)
			&& state.is(BlockTags.BARS)
			&& neighborState.hasProperty(PROPERTY_BY_DIRECTION.get(direction.getOpposite()))) {
			if (!direction.getAxis().isHorizontal()) {
				return true;
			}

			if ((Boolean)state.getValue((Property)PROPERTY_BY_DIRECTION.get(direction))
				&& (Boolean)neighborState.getValue((Property)PROPERTY_BY_DIRECTION.get(direction.getOpposite()))) {
				return true;
			}
		}

		return super.skipRendering(state, neighborState, direction);
	}

	public final boolean attachesTo(final BlockState state, final boolean faceSolid) {
		return !isExceptionForConnection(state) && faceSolid || state.getBlock() instanceof IronBarsBlock || state.is(BlockTags.WALLS) || state.is(ModBlockTags.WINDOW_FRAME);
	}

	@Override
	protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
	}
}
