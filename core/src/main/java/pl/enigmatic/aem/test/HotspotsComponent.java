package pl.enigmatic.aem.test;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import pl.enigmatic.aem.ComponentModel;

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

	/** Default constructor */
	public HotspotsComponent(final SlingHttpServletRequest request) {
		super(request);
	}

	/**
	 * @return list of sorted hotspots
	 */
	public List<Point2D> getItems() {
		if (size != null && StringUtils.isNotBlank(map)) {
			final HotspotsParser parser = new HotspotsParser(size);
			return parser.parseImageMap(map);
		}
		return new LinkedList<>();
	}
}