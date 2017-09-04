package pl.enigmatic.aem;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * Helper to work with resource properties in general cases
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class PropertiesComponent extends HashMap<String, String> {

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link ValueMap current resource's properties}
	 */
	@Inject
	@Optional
	private transient ValueMap properties;

	/**
	 * default constructor
	 */
	public PropertiesComponent() {
		super();
	}

	/**
	 * camelCaseName => Camel Case Name
	 * @param name property name
	 * @return user friendly property name
	 */
	public static String toLabel(final String name) {
		final StringBuilder s = new StringBuilder(name);
		int i = 1;
		while (i < s.length()) {
			final String x = s.substring(i - 1, i);
			final String y = s.substring(i, i + 1);
			if (StringUtils.isAllLowerCase(x) && StringUtils.isAllUpperCase(y)) {
				s.insert(i++, " ");
			}
			i++;
		}
		return WordUtils.capitalize(s.toString());
	}

	/**
	 * initialization
	 */
	@PostConstruct
	protected void init() {
		for (final String key : properties.keySet()) {
			if (!StringUtils.contains(key, ":")) {
				put(key, toLabel(key));
			}
		}
	}

}