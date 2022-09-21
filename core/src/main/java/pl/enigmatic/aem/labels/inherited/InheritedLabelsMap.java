package pl.enigmatic.aem.labels.inherited;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import pl.enigmatic.aem.config.ConfigurationMap;
import pl.enigmatic.aem.labels.Constants;

import static pl.enigmatic.aem.labels.Constants.PN_VALUE;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = Resource.class)
public final class InheritedLabelsMap extends ConfigurationMap {

	/** default serial version UUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public InheritedLabelsMap(final Resource resource) {
		super(Constants.NN_LABELS, InheritedLabelsDefinitions.create(resource).toDefaults(), resource, PN_VALUE);
	}

}
