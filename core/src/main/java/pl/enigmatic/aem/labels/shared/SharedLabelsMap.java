package pl.enigmatic.aem.labels.shared;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static pl.enigmatic.aem.labels.Constants.DEFAULT_LANG_ROOT_DEPTH;
import static pl.enigmatic.aem.labels.Constants.PN_VALUE;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = Resource.class)
public final class SharedLabelsMap extends HashMap<String, String> {

	/** default serial version UUID */
	private static final long serialVersionUID = 1L;

	private final List<ValueMap> sources = new LinkedList<>();

	/**
	 * Default constructor
	 */
	public SharedLabelsMap(final Resource resource) {
		final SharedLabelSources resources = new SharedLabelSources(resource, DEFAULT_LANG_ROOT_DEPTH);
		sources.add(resources.getLangRootLabels().getValueMap());
		sources.add(resources.getGlobalLabels().getValueMap());
	}

	@Override
	public String get(final Object key) {
		final String path = KeyTools.keyToPath(key, PN_VALUE);
		return sources.stream().map(vm -> vm.get(path))
				.filter(Objects::nonNull).map(String::valueOf).filter(StringUtils::isNotBlank)
				.findFirst().orElseGet(key::toString);
	}

}
