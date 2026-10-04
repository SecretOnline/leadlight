package co.secretonline.leadlight;

import co.secretonline.leadlight.item.ModCreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = Leadlight.MOD_ID)
public class NeoForgeCreativeTabHandler {

	@SubscribeEvent
	public static void addItems(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == ModCreativeModeTabs.LEADLIGHT_TAB_KEY) {
			ModCreativeModeTabs.addItems(event::accept);
		}
	}
}
