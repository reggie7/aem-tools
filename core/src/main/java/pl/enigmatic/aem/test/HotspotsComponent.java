package pl.enigmatic.aem.test;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import pl.enigmatic.aem.ComponentModel;
import pl.enigmatic.aem.IndexChildren;

@Model(adaptables = SlingHttpServletRequest.class)
public class HotspotsComponent extends ComponentModel {

	/** image size */
	@Optional
	@Inject
	private Dimension size;

	/** image map */
	@Optional
	@Inject
	private String map;

	/** items */
	@Optional
	@Inject
	private Resource items;

	/** Default constructor */
	public HotspotsComponent(final SlingHttpServletRequest request) {
		super(request);
	}

	/**
	 * @return list of hotspots
	 */
	public List<Hotspot> getItems() {
		if (size != null && StringUtils.isNotBlank(map)) {
			final HotspotsParser parser = new HotspotsParser(size);
			return parser.parseImageMap(map);
		}
		return new LinkedList<>();
	}

	/**
	 * @return list of sorted hotspots
	 */
	public List<Hotspot> getSorted() {
		final List<Hotspot> result = new LinkedList<>();
		final List<Hotspot> hotspots = getItems();
		boolean[] taken = new boolean[hotspots.size()];
		for (int i = 0; i < taken.length; i++) {
			taken[i] = false;
		}
		final Map<Integer, Resource> indexer = IndexChildren.index(items, "item_(\\d+)");
		for (final Map.Entry<Integer, Resource> e : indexer.entrySet()) {
			final int i = e.getKey();
			if (i < taken.length) {
				taken[i] = true;
				final Hotspot h = hotspots.get(i);
				h.setPath(e.getValue().getPath());
				result.add(h);
			}
		}
		for (int i = 0; i < taken.length; i++) {
			if (!taken[i]) {
				final Hotspot h = hotspots.get(i);
				h.setPath(String.format("item_%d", i));
				result.add(h);
			}
		}
		return result;
	}
}