package pl.enigmatic.mobirise.aem.comps.global;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import pl.enigmatic.aem.config.ConfigurationMap;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class GlobalConfig extends ConfigurationMap {

	/** default serial version UUID */
	private static final long serialVersionUID = 1L;

	private static final Map<String, Object> DEFAULTS = new HashMap<>();
	static {
		DEFAULTS.put("date/format", "MMMMM dd, yyyy");
	}

	/**
	 * Default constructor
	 */
	public GlobalConfig(final SlingHttpServletRequest request) {
		super("configs", DEFAULTS, request);
	}

}