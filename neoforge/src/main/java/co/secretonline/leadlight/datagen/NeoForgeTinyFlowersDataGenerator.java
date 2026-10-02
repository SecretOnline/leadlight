package co.secretonline.leadlight.datagen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public class NeoForgeTinyFlowersDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
	}
}
