package pl.enigmatic.aem.labels.shared.dialog;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import pl.enigmatic.aem.labels.shared.KeyTools;
import pl.enigmatic.aem.labels.shared.SharedLabelSources;
import pl.enigmatic.tools.PathTools;

import static pl.enigmatic.aem.labels.Constants.DEFAULT_LANG_ROOT_DEPTH;
import static pl.enigmatic.aem.labels.Constants.PN_VALUE;

public final class SharedLabel {

	private final Resource widget;
	private final String name;
	private final int depth;
	private final Resource target;

	public SharedLabel(final SlingHttpServletRequest request) {
		widget = request.getResource();
		final ValueMap widgetProps = widget.getValueMap();
		name = widgetProps.get("name", String.class);
		depth = widgetProps.get("depth", DEFAULT_LANG_ROOT_DEPTH);
		final SharedLabelSources sources = new SharedLabelSources(request.getRequestPathInfo().getSuffixResource(), depth);
		target = name.startsWith(PathTools.separator)
				? sources.getGlobalLabel(name.substring(PathTools.separator.length()))
				: sources.getLangRootLabel(name);
	}

	public String getPath() {
		return target.getPath();
	}

	public String getValuePath() {
		return KeyTools.keyToPath(target.getPath(), PN_VALUE);
	}

	public String getValue() {
		return target.getValueMap().get(PN_VALUE, String.class);
	}

}
