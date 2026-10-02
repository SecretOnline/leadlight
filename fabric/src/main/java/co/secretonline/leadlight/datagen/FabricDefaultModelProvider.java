package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.datagen.models.WindowFrameModelGenerator;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import org.jspecify.annotations.NonNull;

public class FabricDefaultModelProvider extends FabricModelProvider {
	public FabricDefaultModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
		WindowFrameModelGenerator generator = new WindowFrameModelGenerator(
			blockModelGenerators.blockStateOutput, blockModelGenerators.itemModelOutput, blockModelGenerators.modelOutput);

		generator.generateBlockStateModels();
	}

	@Override
	public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerators) {
	}

	@Override
	public @NonNull String getName() {
		return "Mod models provider";
	}
}

