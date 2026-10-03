package co.secretonline.leadlight.datagen;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.datagen.models.CutStainedGlassPaneItemModelGenerator;
import co.secretonline.leadlight.datagen.models.WindowFrameModelGenerator;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

public class NeoForgeDefaultModelProvider extends ModelProvider {
	public NeoForgeDefaultModelProvider(PackOutput output) {
		super(output, Leadlight.MOD_ID);
	}

	@Override
	protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
		this.generateBlockStateModels(blockModels);
		this.generateItemModels(itemModels);
	}


	public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
		WindowFrameModelGenerator frameModelGenerator = new WindowFrameModelGenerator(
			blockModelGenerators.blockStateOutput, blockModelGenerators.itemModelOutput, blockModelGenerators.modelOutput);
		frameModelGenerator.generateBlockStateModels();
	}

	public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerators) {
		CutStainedGlassPaneItemModelGenerator glassModelGenerator = new CutStainedGlassPaneItemModelGenerator(itemModelGenerators.itemModelOutput, itemModelGenerators.modelOutput);
		glassModelGenerator.generateItemModels();
	}
}
