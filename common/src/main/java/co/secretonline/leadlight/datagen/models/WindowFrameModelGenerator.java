package co.secretonline.leadlight.datagen.models;

import co.secretonline.leadlight.block.WindowFrameBlock;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.data.models.ModTextureMappings;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WindowFrameModelGenerator {
	private final Consumer<BlockModelDefinitionGenerator> blockStateOutput;
	private final ItemModelOutput itemModelOutput;
	private final BiConsumer<Identifier, ModelInstance> modelOutput;

	public WindowFrameModelGenerator(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
		this.blockStateOutput = blockStateOutput;
		this.itemModelOutput = itemModelOutput;
		this.modelOutput = modelOutput;
	}

	public void generateBlockStateModels() {
		ModBlocks.ALL_FRAME_BLOCKS.get().forEach(this::createWindowFrameBlock);
	}

	private void createWindowFrameBlock(final WindowFrameBlock block) {
		BaseFrameModels models = block.getFrameShape().getSegmentsSharedAtPost() > 0
			? new OpenCenterFrameModels(blockStateOutput, itemModelOutput, modelOutput)
			: new ClosedCenterFrameModels(blockStateOutput, itemModelOutput, modelOutput);

		TextureMapping frameMapping = ModTextureMappings.ofMaterial(block.getFrameMaterial());

		this.blockStateOutput
			.accept(models.createGenerator(block, frameMapping));
	}
}
