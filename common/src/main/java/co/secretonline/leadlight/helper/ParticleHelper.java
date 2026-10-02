package co.secretonline.leadlight.helper;

import co.secretonline.leadlight.LeadlightClientState;
import co.secretonline.leadlight.block.ModBlocks;
import co.secretonline.leadlight.block.entity.TinyGardenBlockEntity;
import co.secretonline.leadlight.data.TinyFlowerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ParticleHelper {
	public static @Nullable TextureAtlasSprite getOverrideSprite(@NonNull ClientLevel level, @NonNull BlockPos blockPos) {
		BlockState blockState = level.getBlockState(blockPos);
		if (!(blockState.is(ModBlocks.TINY_GARDEN_BLOCK.get()))) {
			return null;
		}

		if (!(level.getBlockEntity(blockPos) instanceof TinyGardenBlockEntity gardenBlockEntity)) {
			// If there's no block entity, don't do anything
			return null;
		}

		// Select a random flower variant to render as the particle
		List<Identifier> flowers = gardenBlockEntity.getFlowers();
		if (flowers.isEmpty()) {
			return null;
		}

		Identifier flowerId = Util.getRandom(flowers, LeadlightClientState.RANDOM);
		TinyFlowerData flowerData = TinyFlowerData.findById(level.registryAccess(), flowerId);
		if (flowerData == null) {
			return null;
		}

		Minecraft client = Minecraft.getInstance();
		ItemStack stack = flowerData.getItemStack(1);

		LeadlightClientState.ITEM_RENDER_STATE.clear();
		client.getItemModelResolver()
			.appendItemLayers(LeadlightClientState.ITEM_RENDER_STATE, stack,
				ItemDisplayContext.GROUND, level, null, 0);

		Material.Baked material = LeadlightClientState.ITEM_RENDER_STATE.pickParticleMaterial(LeadlightClientState.RANDOM);
		if (material == null) {
			return null;
		}

		return material.sprite();
	}
}
