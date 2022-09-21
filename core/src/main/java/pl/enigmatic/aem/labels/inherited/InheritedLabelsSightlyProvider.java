package pl.enigmatic.aem.labels.inherited;

import org.apache.sling.scripting.api.BindingsValuesProvider;
import org.osgi.service.component.annotations.Component;
import pl.enigmatic.aem.labels.SightlyProviderBase;

import static pl.enigmatic.aem.labels.inherited.InheritedLabelsDefinitions.NN_LABELS;

/**
 * HTL specific {@code BindingsValuesProvider}.
 */
@Component(property = { SightlyProviderBase.COMPONENT_PROPERTY }, service = BindingsValuesProvider.class)
public final class InheritedLabelsSightlyProvider extends SightlyProviderBase {

    public InheritedLabelsSightlyProvider() {
        super(NN_LABELS, InheritedLabels.class);
    }

}
