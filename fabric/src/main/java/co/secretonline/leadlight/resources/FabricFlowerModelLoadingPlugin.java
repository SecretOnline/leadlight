package co.secretonline.leadlight.resources;

import co.secretonline.leadlight.LeadlightClientState;
import co.secretonline.leadlight.data.TinyFlowerResources;
import co.secretonline.leadlight.platform.ClientServiceLoader;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin.Context;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class FabricFlowerModelLoadingPlugin
	implements PreparableModelLoadingPlugin<Map<Identifier, TinyFlowerResources>> {

	@Override
	public void initialize(Map<Identifier, TinyFlowerResources> data, @NonNull Context pluginContext) {
		LeadlightClientState.RESOURCE_INSTANCES = data;

		for (var entry : data.entrySet()) {
			TinyFlowerResources resources = entry.getValue();

			ClientServiceLoader.PANE_SECTION_MODELS.registerModel(resources.model1(), pluginContext);
			ClientServiceLoader.PANE_SECTION_MODELS.registerModel(resources.model2(), pluginContext);
			ClientServiceLoader.PANE_SECTION_MODELS.registerModel(resources.model3(), pluginContext);
			ClientServiceLoader.PANE_SECTION_MODELS.registerModel(resources.model4(), pluginContext);

			if (resources.modelPotted().isPresent()) {
				ClientServiceLoader.PANE_SECTION_MODELS.registerModel(resources.modelPotted().get(), pluginContext);
			}
		}
	}
}
