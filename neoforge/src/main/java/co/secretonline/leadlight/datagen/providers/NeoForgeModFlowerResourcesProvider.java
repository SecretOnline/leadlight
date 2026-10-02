package co.secretonline.leadlight.datagen.providers;

import co.secretonline.leadlight.data.ModRegistries;
import co.secretonline.leadlight.data.TinyFlowerResources;
import co.secretonline.leadlight.datagen.mods.FlowerProvider;
import co.secretonline.leadlight.datagen.mods.Flower;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.JsonCodecProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class NeoForgeModFlowerResourcesProvider extends JsonCodecProvider<TinyFlowerResources> {
	private final FlowerProvider modData;

	public NeoForgeModFlowerResourcesProvider(FlowerProvider modData, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(
			packOutput,
			PackOutput.Target.RESOURCE_PACK,
			Registries.elementsDirPath(ModRegistries.TINY_FLOWER),
			TinyFlowerResources.CODEC,
			lookupProvider,
			modData.getModId());

		this.modData = modData;
	}

	@Override
	public @NonNull String getName() {
		return "Mod flowers resources provider [" + this.modData.getModId() + "]";
	}


	@Override
	protected void gather() {
		for (Flower tuple : this.modData.getFlowers()) {
			this.unconditional(tuple.resources().id(), tuple.resources());
		}
	}
}
