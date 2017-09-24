package pl.enigmatic.aem;

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
public class UnsetRequestAttribute extends ComponentModel {

	/** key */
	@Optional
	@Inject
	private String key;

	/** Default constructor. */
	public UnsetRequestAttribute(final SlingHttpServletRequest request) {
		super(request);
	}

	/**
	 * Initialization.
	 */
	@PostConstruct
	protected void init() {
		final Map<String, Object> map = SetRequestAttribute.map(this.request);
		if (map != null) {
			map.remove(this.key);
		}
	}
}