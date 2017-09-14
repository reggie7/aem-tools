package pl.enigmatic.aem.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;

import pl.enigmatic.aem.tools.ResourceTools;
import pl.enigmatic.tools.PathTools;

/**
 * @author Radosław Wesołowski
 */
public class ConfigurationMap extends HashMap<String, Object> implements ValueMap {

	/** default serial version UUID */
	private static final long serialVersionUID = 1L;

	private final transient String path;
	private final transient String suffix;
	private final transient Map<String, ?> defaults;
	private final transient InheritanceValueMap properties;

	public ConfigurationMap(final String path, final Map<String, ?> defaults, final Page currentPage, final String suffix) {
		super();
		this.path = path;
		this.defaults = defaults;
		properties = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
		this.suffix = suffix;
	}

	public ConfigurationMap(final String path, final Map<String, ?> defaults, final Page currentPage) {
		this(path, defaults, currentPage, null);
	}

	public ConfigurationMap(final String path, final Map<String, ?> defaults, final Resource resource, final String suffix) {
		this(path, defaults, ResourceTools.getContainingPage(resource), suffix);
	}

	public ConfigurationMap(final String path, final Map<String, ?> defaults, final Resource resource) {
		this(path, defaults, resource, null);
	}

	public ConfigurationMap(final String path, final Map<String, ?> defaults, final SlingHttpServletRequest request, final String suffix) {
		this(path, defaults, request.getResource(), suffix);
	}

	public ConfigurationMap(final String path, final Map<String, ?> defaults, final SlingHttpServletRequest request) {
		this(path, defaults, request, null);
	}

	private Object get(final Object key, final Object defaultValue) {
		if (super.containsKey(key)) {
			return super.get(key);
		} else {
			Object value = properties.getInherited(PathTools.path(path, key, suffix), Object.class);
			if (value == null) {
				value = defaults.get(key);
				if (value == null) {
					return defaultValue;
				}
			}
			return value;
		}
	}

	@Override
	public Object getOrDefault(final Object key, final Object defaultValue) {
		return get(key, defaultValue);
	}

	@Override
	public Object get(final Object key) {
		return get(key, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(final String key, final Class<T> type) {
		final Object value = get(key);
		if (value == null || type == null) {
			return (T) value;
		}
		if (String.class.equals(type)) {
			return (T) String.valueOf(value);
		}
		return type.cast(value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(final String key, final T defaultValue) {
		return (T) get((Object) key, defaultValue);
	}

	@Override
	public boolean containsKey(Object key) {
		return defaults.containsKey(key);
	}

	@Override
	public Set<String> keySet() {
		return defaults.keySet();
	}

}