package pl.enigmatic.aem;

import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * A simple class setting a request attribute
 * 
 * @author Radoslaw Wesolowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class GetRequestAttribute extends ComponentModel {

	/** key */
	@Optional
	@Inject
	private String key;

	/** Default constructor. */
	public GetRequestAttribute(final SlingHttpServletRequest request) {
		super(request);
	}

	/**
	 * Gets the map
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T get(final SlingHttpServletRequest request, final String key) {
		final Map<String, Object> map = SetRequestAttribute.map(request);
		if (map != null) {
			return (T) map.get(key);
		}
		return null;
	}

	/**
	 * get value
	 */
	public Object getValue() {
		return get(request, key);
	}
}