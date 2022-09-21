package pl.enigmatic.aem.labels.inherited;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.api.components.ComponentManager;

import pl.enigmatic.aem.tools.PageTools;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class InheritedLabelsDefinitions extends LinkedHashMap<String, InheritedLabelsDefinitions.Label> {

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	public static final String NN_LABELS = "labels";
	public static final String PN_VALUE = "value";
	private static final String DEFINITIONS = "definitions";
	private final transient Page currentPage;
	private final String relPath;
	private final transient Component component;

	private InheritedLabelsDefinitions(final Resource resource, final Page currentPage) {
		this.currentPage = currentPage;
		relPath = PageTools.getRelativePath(resource, currentPage);
		final ComponentManager componentManager = resource.getResourceResolver().adaptTo(ComponentManager.class);
		component = componentManager.getComponentOfResource(resource);
		for (final Label l : getDefinitions(getPages())) {
			final String name = l.getName();
			if (!containsKey(name)) {
				put(name,  l);
			}
		}
	}

	/**
	 * Default constructor
	 */
	public InheritedLabelsDefinitions(final Resource resource) {
		this(resource, PageTools.getContainingPage(resource));
	}

	/**
	 * Default constructor
	 */
	public InheritedLabelsDefinitions(final SlingHttpServletRequest request) {
		this(request.getResource());
	}

	public static InheritedLabelsDefinitions create(final Resource resource) {
		final Page currentPage = PageTools.getContainingPage(resource);
		return new InheritedLabelsDefinitions(currentPage.getContentResource(NN_LABELS), currentPage);
	}

	/**
	 * Camel Case Name => camelCaseName
	 * @param label property label
	 * @return property name
	 */
	public static String toProperty(final String label) {
		if (StringUtils.isBlank(label)) {
			return null;
		}

		String result = WordUtils.capitalize(label);
		result = result.replaceAll("\\W", StringUtils.EMPTY);
		result = result.substring(0, 1).toLowerCase() + result.substring(1);
		return result;
	}

	private List<Page> getPages() {
		final List<Page> list = new LinkedList<>();
		Page page = currentPage;
		while (page != null) {
			list.add(0, page);
			page = page.getParent();
		}

		return list;
	}

	private List<Label> getDefinitions(final List<Page> pages) {
		final List<Label> list = new LinkedList<>();
		String[] definitions = component.getProperties().get(DEFINITIONS, new String[0]);
		for (final String d : definitions) {
			list.add(new Label(d));
		}

		for (final Page p : pages) {
			final ValueMap vm = p.getProperties(relPath);
			definitions = vm.get(DEFINITIONS, new String[0]);
			for (final String d : definitions) {
				list.add(new Label(d, p));
			}
		}

		return list;
	}

	public Map<String, String> toDefaults() {
		final Map<String, String> map = new HashMap<>();
		for (final Map.Entry<String, Label> e : entrySet()) {
			map.put(e.getKey(), e.getValue().getText());
		}

		return map;
	}

	public static class Label {

		private final String name;
		private final String text;
		private final Page source;

		public Label(final String text, final Page source) {
			this.text = text;
			name = toProperty(text);
			this.source = source;
		}

		public Label(final String text) {
			this(text, null);
		}

		public String getName() {
			return name;
		}

		public String getText() {
			return text;
		}

		public Page getSource() {
			return source;
		}

		@Override
		public String toString() {
			return text;
		}

	}

}
