package pl.enigmatic.aem.comps.global;

import javax.script.Bindings;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.scripting.api.BindingsValuesProvider;
import org.osgi.service.component.annotations.Component;

/**
 * HTL specific {@code BindingsValuesProvider}.
 */
@Component(property = { "javax.script.name=sightly" })
public final class SightlyGlobalLabelsProvider implements BindingsValuesProvider {

    private static final String NAME = GlobalLabelsDefinitions.LABELS;

    @Override
    public void addBindings(final Bindings bindings) {
        if (bindings.containsKey(NAME)) {
            return;
        }

        bindings.put(NAME, getResource(bindings).adaptTo(GlobalLabels.class));
    }

    private static Resource getResource(final Bindings bindings) {
        return (Resource) bindings.get(SlingBindings.RESOURCE);
    }

}
