package co.secretonline.leadlight.block;

import co.secretonline.leadlight.block.entity.WindowFrameBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class IronWindowFrame extends AbstractWindowFrame {
	public static final MapCodec<IronWindowFrame> CODEC = simpleCodec(IronWindowFrame::new);

	public IronWindowFrame(Properties settings) {
		super(settings);
		this.registerDefaultState(getAbstractWindowFrameBlockState());
	}

	@Override
	protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
		return new WindowFrameBlockEntity(blockPos, blockState);
	}
}
