package pl.enigmatic.aem.labels.inherited;

import org.apache.sling.scripting.api.BindingsValuesProvider;
import org.osgi.service.component.annotations.Component;
import pl.enigmatic.aem.labels.SightlyProviderBase;

/**
 * HTL specific {@code BindingsValuesProvider}.
 */
@Component(property = { SightlyProviderBase.COMPONENT_PROPERTY }, service = BindingsValuesProvider.class)
public final class InheritedLabelsSightlyProvider extends SightlyProviderBase {

    public InheritedLabelsSightlyProvider() {
        super("inheritedLabels", InheritedLabels.class);
    }

}
