package co.secretonline.leadlight.helper;

import co.secretonline.leadlight.Leadlight;
import co.secretonline.leadlight.LeadlightClientState;
import co.secretonline.leadlight.item.component.TinyFlowerComponent;
import co.secretonline.leadlight.renderer.item.TinyFlowerProperty;
import co.secretonline.leadlight.data.TinyFlowerResources;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public final class ItemModelHelper {

	public static ItemModel.Unbaked createTinyFlowerItemModel() {
		List<SelectItemModel.SwitchCase<TinyFlowerComponent>> cases = new ArrayList<>();

		for (var entry : LeadlightClientState.RESOURCE_INSTANCES.entrySet()) {
			TinyFlowerResources res = entry.getValue();
			cases.add(ItemModelUtils.when(
				new TinyFlowerComponent(res.id()),
				modelForIdentifier(res.itemModel())));
		}

		return ItemModelUtils.select(
			new TinyFlowerProperty(),
			modelForIdentifier(Leadlight.id("item/tiny_garden")),
			cases);
	}

	private static ItemModel.Unbaked modelForIdentifier(Identifier id) {
		return ItemModelUtils.plainModel(id);
	}
}
