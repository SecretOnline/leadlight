package co.secretonline.leadlight.platform;

import java.util.function.Supplier;

public interface DeferredRegistryObject<T> extends Supplier<T> {
	T get();
}
