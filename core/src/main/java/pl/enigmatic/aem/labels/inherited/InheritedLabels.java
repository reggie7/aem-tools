package pl.enigmatic.aem.labels.inherited;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import pl.enigmatic.aem.config.ConfigurationMap;

import static pl.enigmatic.aem.labels.inherited.InheritedLabelsDefinitions.PN_VALUE;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = Resource.class)
public final class InheritedLabels extends ConfigurationMap {

	/** default serial version UUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public InheritedLabels(final Resource resource) {
		super(InheritedLabelsDefinitions.NN_LABELS, InheritedLabelsDefinitions.create(resource).toDefaults(), resource, PN_VALUE);
	}

}
