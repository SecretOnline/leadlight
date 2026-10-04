package co.secretonline.leadlight.platform;

import co.secretonline.leadlight.Leadlight;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements RegistryHelper {
	public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(BuiltInRegistries.BLOCK, Leadlight.MOD_ID);
	public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPE = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, Leadlight.MOD_ID);
	public static final DeferredRegister<BlockEntityType<? extends BlockEntity>> BLOCK_ENTITY_TYPE = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Leadlight.MOD_ID);
	public static final DeferredRegister<Item> ITEM = DeferredRegister.create(BuiltInRegistries.ITEM, Leadlight.MOD_ID);
	public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Leadlight.MOD_ID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Leadlight.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Leadlight.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Leadlight.MOD_ID);

	public void registerToBus(IEventBus modBus) {
		BLOCK.register(modBus);
		BLOCK_TYPE.register(modBus);
		BLOCK_ENTITY_TYPE.register(modBus);
		ITEM.register(modBus);
		DATA_COMPONENT_TYPE.register(modBus);
		RECIPE_SERIALIZER.register(modBus);
		CREATIVE_MODE_TAB.register(modBus);
		SOUND_EVENT.register(modBus);
	}

	@SuppressWarnings("unchecked")
	private static <T> DeferredRegister<T> deferredRegisterFor(Registry<T> registry) {
		if (registryEquals(registry, BuiltInRegistries.BLOCK)) {
			return (DeferredRegister<T>) BLOCK;
		}
		if (registryEquals(registry, BuiltInRegistries.BLOCK_TYPE)) {
			return (DeferredRegister<T>) BLOCK_TYPE;
		}
		if (registryEquals(registry, BuiltInRegistries.BLOCK_ENTITY_TYPE)) {
			return (DeferredRegister<T>) BLOCK_ENTITY_TYPE;
		}
		if (registryEquals(registry, BuiltInRegistries.ITEM)) {
			return (DeferredRegister<T>) ITEM;
		}
		if (registryEquals(registry, BuiltInRegistries.DATA_COMPONENT_TYPE)) {
			return (DeferredRegister<T>) DATA_COMPONENT_TYPE;
		}
		if (registryEquals(registry, BuiltInRegistries.RECIPE_SERIALIZER)) {
			return (DeferredRegister<T>) RECIPE_SERIALIZER;
		}
		if (registryEquals(registry, BuiltInRegistries.CREATIVE_MODE_TAB)) {
			return (DeferredRegister<T>) CREATIVE_MODE_TAB;
		}
		if (registryEquals(registry, BuiltInRegistries.SOUND_EVENT)) {
			return (DeferredRegister<T>) SOUND_EVENT;
		}

		throw new IllegalArgumentException("No registry linked in NeoForge to register type: " + registry.key());
	}

	private static boolean registryEquals(Registry<?> a, Registry<?> b) {
		return a.key().registry().equals(b.key().registry()) && a.key().identifier().equals(b.key().identifier());
	}

	@Override
	public <T, U extends T> DeferredRegistryObject<U> register(Registry<T> objRegistry, Identifier id, Supplier<U> objSupplier) {
		DeferredRegister<T> registry = deferredRegisterFor(objRegistry);
		return new NeoForgeDeferredRegistryObject<>(registry.register(id.getPath(), objSupplier));
	}

	public static class NeoForgeDeferredRegistryObject<T> implements DeferredRegistryObject<T> {

		private final Supplier<T> supplier;

		public NeoForgeDeferredRegistryObject(Supplier<T> supplier) {
			this.supplier = supplier;
		}

		public T get() {
			return this.supplier.get();
		}
	}
}
