package co.secretonline.leadlight.datagen.models;

import co.secretonline.leadlight.block.WindowFrameBlock;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.data.models.ModTextureMappings;
import co.secretonline.leadlight.data.models.ModModelTemplates;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WindowFrameModelGenerator {
	public static final VariantMutator Y_ROT_90 = VariantMutator.Y_ROT.withValue(Quadrant.R90);
	public static final VariantMutator Y_ROT_270 = VariantMutator.Y_ROT.withValue(Quadrant.R270);

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
		ModModelTemplates.ShapeModelTemplates modelTemplates = ModModelTemplates.ofShape(block.getFrameShape());
		TextureMapping frameMapping = ModTextureMappings.ofMaterial(block.getFrameMaterial());

		MultiVariant post = plainVariant(modelTemplates.Post.create(block, frameMapping, this.modelOutput));
		MultiVariant side = plainVariant(modelTemplates.Side.create(block, frameMapping, this.modelOutput));
		MultiVariant sideAlt = plainVariant(modelTemplates.SideAlt.create(block, frameMapping, this.modelOutput));
		MultiVariant noSide = plainVariant(modelTemplates.NoSide.create(block, frameMapping, this.modelOutput));
		MultiVariant noSideAlt = plainVariant(modelTemplates.NoSideAlt.create(block, frameMapping, this.modelOutput));
		this.blockStateOutput
			.accept(
				MultiPartGenerator.multiPart(block)
					.with(post)
					.with(condition().term(BlockStateProperties.NORTH, true), side)
					.with(condition().term(BlockStateProperties.EAST, true), side.with(Y_ROT_90))
					.with(condition().term(BlockStateProperties.SOUTH, true), sideAlt)
					.with(condition().term(BlockStateProperties.WEST, true), sideAlt.with(Y_ROT_90))
					.with(condition().term(BlockStateProperties.NORTH, false), noSide)
					.with(condition().term(BlockStateProperties.EAST, false), noSideAlt)
					.with(condition().term(BlockStateProperties.SOUTH, false), noSideAlt.with(Y_ROT_90))
					.with(condition().term(BlockStateProperties.WEST, false), noSide.with(Y_ROT_270))
			);
	}

	private static ConditionBuilder condition() {
		return new ConditionBuilder();
	}


	private static net.minecraft.client.renderer.block.dispatch.Variant plainModel(final Identifier model) {
		return new net.minecraft.client.renderer.block.dispatch.Variant(model);
	}

	private static MultiVariant variant(final net.minecraft.client.renderer.block.dispatch.Variant variant) {
		return new MultiVariant(WeightedList.of(variant));
	}

	private static MultiVariant plainVariant(final Identifier model) {
		return variant(plainModel(model));
	}














}
