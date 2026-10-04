package co.secretonline.leadlight.block.entity;

import co.secretonline.leadlight.component.ModComponents;
import co.secretonline.leadlight.component.WindowFrameContentsComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class WindowFrameBlockEntity extends BlockEntity {
	@NonNull
	private WindowFrameContentsComponent state = WindowFrameContentsComponent.empty();

	public WindowFrameBlockEntity(BlockPos worldPosition, BlockState blockState) {
		super(ModBlockEntities.WINDOW_FRAME_BLOCK_ENTITY.get(), worldPosition, blockState);
	}

	public @NonNull WindowFrameContentsComponent getState() {
		return state;
	}

	public void setState(WindowFrameContentsComponent state) {
		this.state = state;
		markUpdated();
	}

	@Override
	protected void saveAdditional(@NonNull ValueOutput output) {
		super.saveAdditional(output);

		output.storeNullable("window_frame_contents", WindowFrameContentsComponent.CODEC, state);
	}

	@Override
	protected void loadAdditional(@NonNull ValueInput input) {
		super.loadAdditional(input);

		state = input.read("window_frame_contents", WindowFrameContentsComponent.CODEC).orElse(WindowFrameContentsComponent.empty());
	}

	@Override
	public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
		return saveWithoutMetadata(registries);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	protected void applyImplicitComponents(@NonNull DataComponentGetter components) {
		super.applyImplicitComponents(components);

		state = components.getOrDefault(ModComponents.WINDOW_FRAME_CONTENTS.get(),  WindowFrameContentsComponent.empty());
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.@NonNull Builder components) {
		super.collectImplicitComponents(components);

		components.set(ModComponents.WINDOW_FRAME_CONTENTS.get(), state);
	}

	@Override
	public void removeComponentsFromTag(ValueOutput output) {
		output.discard("window_frame_contents");
	}

	private void markUpdated() {
		this.setChanged();
		Level level = this.getLevel();
		if (level != null) {
			level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}
}
