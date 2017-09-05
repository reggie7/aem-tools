package pl.enigmatic.aem;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import pl.enigmatic.tools.PathTools;

/**
 * Simple class to include .html extension on URLs.
 *
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class URLNormalizer {

	/** the request */
	private final SlingHttpServletRequest request;

	/** the url/path to be normalized */
	@Inject
	@Optional
	private String input;

	/**
	 * Default Constructor.
	 */
	public URLNormalizer(final SlingHttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
	 *
	 * @return an iterator to go through all the integers in range [from, to]
	 */
	public String getUrl() {
		return PathTools.isContent(input) ? PathTools.name(input, request.getRequestPathInfo().getExtension()) : input;
	}

}