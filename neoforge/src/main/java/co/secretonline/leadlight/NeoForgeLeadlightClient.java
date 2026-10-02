package co.secretonline.leadlight;

import co.secretonline.leadlight.block.entity.ModBlockEntities;
import co.secretonline.leadlight.renderer.blockentity.TinyFlowerPotBlockEntityRenderer;
import co.secretonline.leadlight.renderer.blockentity.TinyGardenBlockEntityRenderer;
import co.secretonline.leadlight.renderer.item.ModSelectItemModelProperties;
import co.secretonline.leadlight.resources.NeoForgeLeadLightResourceLoader;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.resources.VanillaClientListeners;

@Mod(value = Leadlight.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Leadlight.MOD_ID, value = Dist.CLIENT)
public class NeoForgeLeadlightClient {
	private static final NeoForgeLeadLightResourceLoader tinyFlowerResourceLoader = new NeoForgeLeadLightResourceLoader();

	@SubscribeEvent
	public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
		event.register(ModSelectItemModelProperties.TINY_FLOWER_PROPERTY_ID, ModSelectItemModelProperties.TINY_FLOWER_PROPERTY);
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.TINY_GARDEN_BLOCK_ENTITY.get(), TinyGardenBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.TINY_FLOWER_POT_BLOCK_ENTITY.get(), TinyFlowerPotBlockEntityRenderer::new);
	}

	@SubscribeEvent
	public static void registerResourceLoader(AddClientReloadListenersEvent event) {
		Identifier flowerModelReload = Leadlight.id("flower_models");
		event.addListener(flowerModelReload, NeoForgeLeadlightClient.tinyFlowerResourceLoader);
		event.addDependency(flowerModelReload, VanillaClientListeners.MODELS);
	}

	@SubscribeEvent
	public static void registerStandaloneModels(ModelEvent.RegisterStandalone event) {
		NeoForgeLeadlightClient.tinyFlowerResourceLoader.registerModels(event);
	}
}
