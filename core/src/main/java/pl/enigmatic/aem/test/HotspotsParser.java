package pl.enigmatic.aem.test;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.RepositoryException;

import pl.enigmatic.tools.MathTools;

public class HotspotsParser {

	private static final Pattern PATTERN = Pattern.compile("\\((\\d+),(\\d+),.+?\\)");

	private final Dimension size;

	/** Default constructor */
	public HotspotsParser(final Dimension size) {
		super();
		this.size = size;
	}

	/**
	 * Method for parsing imageMap with hotspots. Example input:
	 * [circle(583,79,24)||"Hotspot1"][circle(360,71,18)"http://#"||"Hotspot2"]
	 * Only first 3 hotspots are taken into list. If there are more than 3
	 * hotspots they are ignored.
	 * @param parent
	 * @param imgHeight
	 * @param imgWidth
	 *
	 * @throws RepositoryException
	 * @throws IOException
	 */
	public List<Point2D> parseImageMap(final String imageMap) {
		final List<Point2D> map = new LinkedList<>();
		final Matcher matcher = PATTERN.matcher(imageMap);
		while (matcher.find()) {
			double x = Double.parseDouble(matcher.group(1));
			x = MathTools.percent(x, size.getWidth());
			double y = Double.parseDouble(matcher.group(2));
			y = MathTools.percent(y, size.getHeight());
			map.add(new Point2D.Double(x, y));
		}
		return map;
	}
}