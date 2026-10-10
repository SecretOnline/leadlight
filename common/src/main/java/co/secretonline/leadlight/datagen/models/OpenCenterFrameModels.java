package co.secretonline.leadlight.datagen.models;

import co.secretonline.leadlight.block.WindowFrameBlock;
import co.secretonline.leadlight.data.models.ModModelTemplates;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class OpenCenterFrameModels extends BaseFrameModels {
	protected OpenCenterFrameModels(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
		super(blockStateOutput, itemModelOutput, modelOutput);
	}

	@Override
	public MultiPartGenerator createGenerator(WindowFrameBlock block, TextureMapping textureMapping) {
		ModModelTemplates.ShapeModelTemplates modelTemplates = ModModelTemplates.ofShape(block.getFrameShape());

		MultiVariant post = plainVariant(modelTemplates.Post.create(block, textureMapping, this.modelOutput));
		MultiVariant side = plainVariant(modelTemplates.Side.create(block, textureMapping, this.modelOutput));
		MultiVariant sideAlt = plainVariant(modelTemplates.SideAlt.create(block, textureMapping, this.modelOutput));
		MultiVariant noSide = plainVariant(modelTemplates.NoSide.create(block, textureMapping, this.modelOutput));
		MultiVariant noSideAlt = plainVariant(modelTemplates.NoSideAlt.create(block, textureMapping, this.modelOutput));

		modelTemplates.ItemBlock.create(block, textureMapping, this.modelOutput);

		return MultiPartGenerator.multiPart(block)
			// Central bits are always there
			.with(post)
			// Add side parts to sides
			.with(condition().term(BlockStateProperties.NORTH, true), side)
			.with(condition().term(BlockStateProperties.EAST, true), side.with(Y_ROT_90))
			.with(condition().term(BlockStateProperties.SOUTH, true), sideAlt)
			.with(condition().term(BlockStateProperties.WEST, true), sideAlt.with(Y_ROT_90))
			// Middle pillar caps should only be present if opposite sides are true and all others are false
			.with(condition()
				.term(BlockStateProperties.NORTH, true)
				.term(BlockStateProperties.EAST, false)
				.term(BlockStateProperties.SOUTH, false)
				.term(BlockStateProperties.WEST, false), noSide)
			.with(condition()
				.term(BlockStateProperties.NORTH, false)
				.term(BlockStateProperties.EAST, false)
				.term(BlockStateProperties.SOUTH, true)
				.term(BlockStateProperties.WEST, false), noSideAlt)
			.with(condition()
				.term(BlockStateProperties.NORTH, false)
				.term(BlockStateProperties.EAST, true)
				.term(BlockStateProperties.SOUTH, false)
				.term(BlockStateProperties.WEST, false), noSide.with(Y_ROT_90))
			.with(condition()
				.term(BlockStateProperties.NORTH, false)
				.term(BlockStateProperties.EAST, false)
				.term(BlockStateProperties.SOUTH, false)
				.term(BlockStateProperties.WEST, true), noSideAlt.with(Y_ROT_90));
	}
}
