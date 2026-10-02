package co.secretonline.leadlight;

import co.secretonline.leadlight.block.entity.ModBlockEntities;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.item.ModCreativeModeTabs;
import co.secretonline.leadlight.item.component.ModComponents;
import co.secretonline.leadlight.data.ModRegistries;
import co.secretonline.leadlight.data.TinyFlowerData;
import co.secretonline.leadlight.item.ModItems;
import co.secretonline.leadlight.item.crafting.ModRecipeSerializers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

public class FabricLeadlight implements ModInitializer {

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModBlockEntities.initialize();
		ModComponents.initialize();
		ModItems.initialize();
		ModRecipeSerializers.initialize();
		ModCreativeModeTabs.initialize();

		DynamicRegistries.registerSynced(ModRegistries.TINY_FLOWER, TinyFlowerData.CODEC);

		FabricCreativeTabHandler.addShearsItems();
		FabricCreativeTabHandler.addTinyFlowerItems();
	}
}
