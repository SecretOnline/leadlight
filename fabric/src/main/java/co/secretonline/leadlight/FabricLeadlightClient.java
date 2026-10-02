package co.secretonline.leadlight;

import co.secretonline.leadlight.block.entity.ModBlockEntities;
import co.secretonline.leadlight.renderer.blockentity.TinyFlowerPotBlockEntityRenderer;
import co.secretonline.leadlight.renderer.blockentity.TinyGardenBlockEntityRenderer;
import co.secretonline.leadlight.renderer.item.ModSelectItemModelProperties;
import co.secretonline.leadlight.resources.FabricFlowerModelDataLoader;
import co.secretonline.leadlight.resources.FabricFlowerModelLoadingPlugin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.client.model.loading.ModelLoadingPluginManager;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;

public class FabricLeadlightClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SelectItemModelProperties.ID_MAPPER.put(co.secretonline.tinyflowers.renderer.item.ModSelectItemModelProperties.TINY_FLOWER_PROPERTY_ID, co.secretonline.tinyflowers.renderer.item.ModSelectItemModelProperties.TINY_FLOWER_PROPERTY);

		BlockEntityRenderers.register(ModBlockEntities.TINY_GARDEN_BLOCK_ENTITY.get(), co.secretonline.tinyflowers.renderer.blockentity.TinyGardenBlockEntityRenderer::new);
		BlockEntityRenderers.register(ModBlockEntities.TINY_FLOWER_POT_BLOCK_ENTITY.get(), co.secretonline.tinyflowers.renderer.blockentity.TinyFlowerPotBlockEntityRenderer::new);

		ModelLoadingPluginManager.registerPlugin(new FabricFlowerModelDataLoader(), new FabricFlowerModelLoadingPlugin());
	}
}
