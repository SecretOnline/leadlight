package co.secretonline.leadlight.datagen.models;

import co.secretonline.leadlight.block.WindowFrameBlock;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class BaseFrameModels {
	protected static final VariantMutator Y_ROT_90 = VariantMutator.Y_ROT.withValue(Quadrant.R90);
	protected static final VariantMutator Y_ROT_180 = VariantMutator.Y_ROT.withValue(Quadrant.R180);
	protected static final VariantMutator Y_ROT_270 = VariantMutator.Y_ROT.withValue(Quadrant.R270);

	protected final Consumer<BlockModelDefinitionGenerator> blockStateOutput;
	protected final ItemModelOutput itemModelOutput;
	protected final BiConsumer<Identifier, ModelInstance> modelOutput;

	protected BaseFrameModels(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
		this.blockStateOutput = blockStateOutput;
		this.itemModelOutput = itemModelOutput;
		this.modelOutput = modelOutput;
	}


	public abstract MultiPartGenerator createGenerator(final WindowFrameBlock block, final TextureMapping textureMapping);

	protected static ConditionBuilder condition() {
		return new ConditionBuilder();
	}

	protected static net.minecraft.client.renderer.block.dispatch.Variant plainModel(final Identifier model) {
		return new net.minecraft.client.renderer.block.dispatch.Variant(model);
	}

	protected static MultiVariant variant(final net.minecraft.client.renderer.block.dispatch.Variant variant) {
		return new MultiVariant(WeightedList.of(variant));
	}

	protected static MultiVariant plainVariant(final Identifier model) {
		return variant(plainModel(model));
	}
}
