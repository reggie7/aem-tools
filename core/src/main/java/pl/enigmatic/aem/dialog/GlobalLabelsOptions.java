package pl.enigmatic.aem.dialog;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;

import pl.enigmatic.aem.comps.global.GlobalLabelsDefinitions;
import pl.enigmatic.aem.comps.global.GlobalLabelsDefinitions.Label;

public class GlobalLabelsOptions extends ResourceWrapper implements GetOptions {

	public GlobalLabelsOptions(final Resource resource) {
		super(resource);
	}

	@Override
	public Collection<Entry> getOptions() {
		final GlobalLabelsDefinitions labels = GlobalLabelsDefinitions.create(this);
		final List<Entry> list = new LinkedList<>();
		list.add(new Entry(StringUtils.EMPTY, StringUtils.EMPTY));
		for (final Map.Entry<String, Label> l : labels.entrySet()) {
			final String key = l.getKey();
			String text = l.getValue().getText();
			list.add(new Entry(key, text));
		}
		return list;
	}
}