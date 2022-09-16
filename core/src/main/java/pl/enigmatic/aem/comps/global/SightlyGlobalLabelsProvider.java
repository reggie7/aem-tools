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

    private static final String NAME = "labels";

    @Override
    public void addBindings(final Bindings bindings) {
        if (bindings.containsKey(NAME)) {
            return;
        }

        bindings.put(NAME, ((Resource) bindings.get(SlingBindings.RESOURCE)).adaptTo(GlobalLabels.class));
    }
}
