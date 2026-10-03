package co.secretonline.leadlight;

import co.secretonline.leadlight.block.entity.ModBlockEntities;
import co.secretonline.leadlight.item.ModCreativeModeTabs;
import co.secretonline.leadlight.renderer.blockentity.WindowFrameBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class FabricLeadlightClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(ModBlockEntities.WINDOW_FRAME_BLOCK_ENTITY.get(), WindowFrameBlockEntityRenderer::new);

//		ModelLoadingPluginManager.registerPlugin(new FabricFlowerModelDataLoader(), new FabricFlowerModelLoadingPlugin());

		CreativeModeTabEvents.modifyOutputEvent(ModCreativeModeTabs.LEADLIGHT_TAB_KEY)
			.register((itemGroup) -> ModCreativeModeTabs.addItems(itemGroup::accept));
	}
}
