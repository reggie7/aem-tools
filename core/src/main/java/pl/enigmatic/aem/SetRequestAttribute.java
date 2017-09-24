package pl.enigmatic.aem;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * A simple class for setting a request attribute
 * to pass along
 * 
 * @author Radoslaw Wesolowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class SetRequestAttribute extends ComponentModel {

	public static final String MAIN_KEY = SetRequestAttribute.class.getName();

	/** key */
	@Optional
	@Inject
	private String key;

	/** key */
	@Optional
	@Inject
	private Object value;

	/** Default constructor. */
	public SetRequestAttribute(final SlingHttpServletRequest request) {
		super(request);
	}

	/**
	 * Get the map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> map(final SlingHttpServletRequest request) {
		final Object o = request.getAttribute(MAIN_KEY);
		if (o instanceof Map) {
			return (Map) o;
		} else {
			return null;
		}
	}

	/**
	 * Initialization.
	 */
	@PostConstruct
	protected void init() {
		Map<String, Object> map = map(this.request);
		if (map == null) {
			map = new HashMap<>();
			this.request.setAttribute(MAIN_KEY, map);
		}
		map.put(key, value);
	}
}