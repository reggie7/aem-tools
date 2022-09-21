package pl.enigmatic.aem.labels;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.scripting.api.BindingsValuesProvider;

import javax.script.Bindings;

/**
 * HTL specific {@code BindingsValuesProvider}.
 */
public abstract class SightlyProviderBase implements BindingsValuesProvider {

    public static final String COMPONENT_PROPERTY = "javax.script.name=sightly";
    private final String name;
    private final Class<?> type;

    public SightlyProviderBase(final String name, final Class<?> type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void addBindings(final Bindings bindings) {
        if (bindings.containsKey(name)) {
            return;
        }

        bindings.put(name, ((Resource) bindings.get(SlingBindings.RESOURCE)).adaptTo(type));
    }

}
