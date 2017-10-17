package pl.enigmatic.aem.test;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * Parses <code>imageMap</code> property
	 */
	public List<Hotspot> parse(final String imageMap) {
		final List<Hotspot> map = new ArrayList<>();
		final Matcher matcher = PATTERN.matcher(imageMap);
		while (matcher.find()) {
			double x = Double.parseDouble(matcher.group(1));
			x = MathTools.percent(x, size.getWidth());
			double y = Double.parseDouble(matcher.group(2));
			y = MathTools.percent(y, size.getHeight());
			map.add(new Hotspot(x, y));
		}
		return map;
	}
}