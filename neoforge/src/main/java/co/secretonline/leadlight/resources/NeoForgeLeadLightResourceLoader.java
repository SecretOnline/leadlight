package co.secretonline.leadlight.resources;

import co.secretonline.leadlight.LeadlightClientState;
import co.secretonline.leadlight.data.TinyFlowerResources;
import co.secretonline.leadlight.helper.FlowerModelHelper;
import co.secretonline.leadlight.platform.ClientServiceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.client.event.ModelEvent;
import org.jspecify.annotations.NonNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NeoForgeLeadLightResourceLoader extends SimplePreparableReloadListener<Map<Identifier, TinyFlowerResources>> {
	private final Set<Identifier> knownIds = new HashSet<>();

	@Override
	protected Map<Identifier, TinyFlowerResources> prepare(@NonNull ResourceManager resourceManager, @NonNull ProfilerFiller profilerFiller) {
		var resources = FlowerModelHelper.readResourceFiles(resourceManager);

		LeadlightClientState.RESOURCE_INSTANCES = resources;

		knownIds.clear();
		for (TinyFlowerResources flowerResources : resources.values()) {
			knownIds.add(flowerResources.model1());
			knownIds.add(flowerResources.model2());
			knownIds.add(flowerResources.model3());
			knownIds.add(flowerResources.model4());

			if (flowerResources.modelPotted().isPresent()) {
				knownIds.add(flowerResources.modelPotted().get());
			}
		}

		return resources;
	}

	@Override
	protected void apply(Map<Identifier, TinyFlowerResources> identifierTinyFlowerResourcesMap, @NonNull ResourceManager resourceManager, @NonNull ProfilerFiller profilerFiller) {
	}

	public void registerModels(ModelEvent.RegisterStandalone event) {
		for (Identifier id : knownIds) {
			ClientServiceLoader.PANE_SECTION_MODELS.registerModel(id, event);
		}
	}
}
