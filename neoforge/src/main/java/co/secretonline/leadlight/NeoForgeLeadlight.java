package co.secretonline.leadlight;

import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.block.entity.ModBlockEntities;
import co.secretonline.leadlight.item.ModCreativeModeTabs;
import co.secretonline.leadlight.item.ModItems;
import co.secretonline.leadlight.component.ModComponents;
import co.secretonline.leadlight.platform.NeoForgeRegistryHelper;
import co.secretonline.leadlight.platform.ServerServiceLoader;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@Mod(value = Leadlight.MOD_ID)
@EventBusSubscriber(modid = Leadlight.MOD_ID)
public class NeoForgeLeadlight {
	public NeoForgeLeadlight(IEventBus modBus) {
		ModBlocks.initialize();
		ModBlockEntities.initialize();
		ModComponents.initialize();
		ModItems.initialize();
		ModCreativeModeTabs.initialize();

		if (ServerServiceLoader.REGISTRY instanceof NeoForgeRegistryHelper neoForgeRegistryHelper) {
			neoForgeRegistryHelper.registerToBus(modBus);
		} else {
			throw new NullPointerException("Registry helper was not for NeoForge");
		}
	}
}
