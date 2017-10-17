package pl.enigmatic.aem;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.models.annotations.Model;

import pl.enigmatic.GetPath;
import pl.enigmatic.tools.PathTools;

/**
 * Simple class for getting the resource path from request URI
 * 
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class PathFromURI implements GetPath {

	private final String path;

	/** Default constructor. */
	public PathFromURI(final SlingHttpServletRequest request) {
		final RequestPathInfo rpi = request.getRequestPathInfo();
		final String fullSuffix = PathTools.name(rpi.getSelectorString(), rpi.getExtension()) + StringUtils.stripToEmpty(rpi.getSuffix());
		final String uri = request.getRequestURI();
		path = uri.substring(0, uri.length() - fullSuffix.length() - 1);
	}

	@Override
	public String getPath() {
		return path;
	}
}