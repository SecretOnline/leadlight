package co.secretonline.leadlight.platform;

import java.util.ServiceLoader;

public class ClientServiceLoader {

	public static final PaneSectionModelHelper PANE_SECTION_MODELS = load(PaneSectionModelHelper.class);

	public static <T> T load(Class<T> clazz) {
		return ServiceLoader.load(clazz, clazz.getClassLoader())
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
	}
}
