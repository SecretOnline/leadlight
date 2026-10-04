package co.secretonline.leadlight.datagen;

import net.minecraft.data.loot.LootTableProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(value = Dist.CLIENT)
public class NeoForgeTinyFlowersDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		event.createProvider(NeoForgeDefaultModelProvider::new);
		event.createProvider(NeoForgeModRecipeProvider::new);
		event.createProvider(NeoForgeBlockTagProvider::new);
		event.createProvider(NeoForgeModSoundsProvider::new);
		event.createProvider(NeoForgeItemTagProvider::new);

		event.createProvider(NeoForgeModLootTableProvider::get);
	}
}
