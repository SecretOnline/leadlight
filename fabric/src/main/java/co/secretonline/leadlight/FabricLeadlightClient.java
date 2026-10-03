package co.secretonline.leadlight;

import co.secretonline.leadlight.item.ModCreativeModeTabs;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

public class FabricLeadlightClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
//		BlockEntityRenderers.register(ModBlockEntities.TINY_GARDEN_BLOCK_ENTITY.get(), co.secretonline.tinyflowers.renderer.blockentity.TinyGardenBlockEntityRenderer::new);
//		BlockEntityRenderers.register(ModBlockEntities.TINY_FLOWER_POT_BLOCK_ENTITY.get(), co.secretonline.tinyflowers.renderer.blockentity.TinyFlowerPotBlockEntityRenderer::new);
//
//		ModelLoadingPluginManager.registerPlugin(new FabricFlowerModelDataLoader(), new FabricFlowerModelLoadingPlugin());

		CreativeModeTabEvents.modifyOutputEvent(ModCreativeModeTabs.LEADLIGHT_TAB_KEY)
			.register((itemGroup) -> ModCreativeModeTabs.addItems(itemGroup::accept));
	}
}
