package pl.enigmatic.aem.comps.global;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import pl.enigmatic.aem.config.ConfigurationMap;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class GlobalLabels extends ConfigurationMap {

	/** default serial version UUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public GlobalLabels(final SlingHttpServletRequest request) {
		super("labels", GlobalLabelsDefinitions.create(request.getResource()).toDefaults(), request, "value");
	}

}