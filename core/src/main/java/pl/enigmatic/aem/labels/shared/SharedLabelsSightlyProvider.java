package pl.enigmatic.aem.labels.shared;

import org.apache.sling.scripting.api.BindingsValuesProvider;
import org.osgi.service.component.annotations.Component;
import pl.enigmatic.aem.labels.SightlyProviderBase;

import static pl.enigmatic.aem.labels.Constants.DEFAULT_RESOURCE_NAME;

/**
 * HTL specific {@code BindingsValuesProvider}.
 */
@Component(property = { SightlyProviderBase.COMPONENT_PROPERTY }, service = BindingsValuesProvider.class)
public final class SharedLabelsSightlyProvider extends SightlyProviderBase {

    public SharedLabelsSightlyProvider() {
        super(DEFAULT_RESOURCE_NAME, SharedLabelsMap.class);
    }

}
