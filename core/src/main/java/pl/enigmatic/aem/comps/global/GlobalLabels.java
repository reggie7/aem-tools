package pl.enigmatic.aem.comps.global;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import pl.enigmatic.aem.config.ConfigurationMap;

import static pl.enigmatic.aem.comps.global.GlobalLabelsDefinitions.PN_VALUE;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = Resource.class)
public final class GlobalLabels extends ConfigurationMap {

	/** default serial version UUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public GlobalLabels(final Resource resource) {
		super(GlobalLabelsDefinitions.NN_LABELS, GlobalLabelsDefinitions.create(resource).toDefaults(), resource, PN_VALUE);
	}

}
