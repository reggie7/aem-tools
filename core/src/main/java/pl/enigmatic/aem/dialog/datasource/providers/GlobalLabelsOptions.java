package pl.enigmatic.aem.dialog.datasource.providers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceWrapper;

import org.apache.sling.models.annotations.Model;
import pl.enigmatic.aem.comps.global.GlobalLabelsDefinitions;
import pl.enigmatic.aem.comps.global.GlobalLabelsDefinitions.Label;
import pl.enigmatic.aem.dialog.datasource.Option;
import pl.enigmatic.aem.dialog.datasource.DataSourceOptionsProvider;

@Model(adaptables = SlingHttpServletRequest.class)
public class GlobalLabelsOptions extends ResourceWrapper implements DataSourceOptionsProvider {

	public GlobalLabelsOptions(final SlingHttpServletRequest request) {
		super(request.getRequestPathInfo().getSuffixResource());
	}

	@Override
	public Collection<Option> getOptions() {
		final GlobalLabelsDefinitions labels = GlobalLabelsDefinitions.create(this);
		final List<Option> list = new LinkedList<>();
		list.add(new Option(StringUtils.EMPTY, StringUtils.EMPTY));
		for (final Map.Entry<String, Label> l : labels.entrySet()) {
			final String key = l.getKey();
			String label = l.getValue().getText();
			list.add(new Option(key, label));
		}
		return list;
	}
}