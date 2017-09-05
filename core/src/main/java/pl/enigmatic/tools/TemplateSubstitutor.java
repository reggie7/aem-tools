package pl.enigmatic.tools;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TemplateSubstitutor extends HashMap<String, Object> {

	/**
	 * default serial version uuid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Substitutes all given elements from the given map in the given pattern and returns the result.
	 *
	 * @param pattern The pattern used a basis for the substitutions.
	 * @param substitutes The {@link Map} containing key/values pairs being used to replace parts in given
	 * {@code pattern}.
	 * @return The resulting string.
	 */
	public String substitute(final String pattern) {
		String result = pattern;
		for (final Map.Entry<String, Object> e : entrySet()) {
			final String value = String.valueOf(e.getValue());
			if (StringUtils.isNotBlank(value)) {
				result =  result.replace("${" + e.getKey() + "}", value);
			}
		}
		return result;
	}

}