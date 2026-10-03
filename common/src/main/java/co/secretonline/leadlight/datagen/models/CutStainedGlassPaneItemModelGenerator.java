package co.secretonline.leadlight.datagen.models;

import co.secretonline.leadlight.item.CutStainedGlassPaneItem;
import co.secretonline.leadlight.item.ModItems;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.Identifier;

import java.util.function.BiConsumer;

public class CutStainedGlassPaneItemModelGenerator {
	private final ItemModelOutput itemModelOutput;
	private final BiConsumer<Identifier, ModelInstance> modelOutput;

	public CutStainedGlassPaneItemModelGenerator(ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
		this.itemModelOutput = itemModelOutput;
		this.modelOutput = modelOutput;
	}

	public void generateItemModels() {
		ModItems.ALL_CUT_STAINED_GLASS_PANE_ITEMS.get().forEach(this::createCutGlassPaneItem);
	}

	private void createCutGlassPaneItem(final CutStainedGlassPaneItem item) {
		Identifier id = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), this.modelOutput);
		this.itemModelOutput.accept(item, ItemModelUtils.plainModel(id));
	}
}
