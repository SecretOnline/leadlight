package co.secretonline.leadlight;

import co.secretonline.leadlight.block.entity.ModBlockEntities;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.item.ModCreativeModeTabs;
import co.secretonline.leadlight.component.ModComponents;
import co.secretonline.leadlight.item.ModItems;
import co.secretonline.leadlight.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

public class FabricLeadlight implements ModInitializer {

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModBlockEntities.initialize();
		ModComponents.initialize();
		ModItems.initialize();
		ModCreativeModeTabs.initialize();
		ModSounds.initialize();
	}
}
