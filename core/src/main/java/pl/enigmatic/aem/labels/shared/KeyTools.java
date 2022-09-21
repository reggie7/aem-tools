package pl.enigmatic.aem.labels.shared;

import org.apache.commons.lang3.StringUtils;
import pl.enigmatic.tools.PathTools;

public final class KeyTools {

	public static String keyToPath(final String key) {
		return StringUtils.replace(key, ".", PathTools.separator);
	}

	public static String keyToPath(final Object first, final Object second, final Object... rest) {
		return keyToPath(PathTools.path(first, second, rest));
	}

}
