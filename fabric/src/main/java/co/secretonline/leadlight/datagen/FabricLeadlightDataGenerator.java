package co.secretonline.leadlight.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FabricLeadlightDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(FabricDefaultModelProvider::new);
		pack.addProvider(FabricModLootTableProvider::new);
		pack.addProvider(FabricModRecipeProvider::new);
		pack.addProvider(FabricModSoundsProvider::new);
		pack.addProvider(FabricBlockTagProvider::new);
		pack.addProvider(FabricItemTagProvider::new);
	}
}
