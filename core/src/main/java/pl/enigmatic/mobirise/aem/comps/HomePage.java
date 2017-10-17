package pl.enigmatic.mobirise.aem.comps;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import pl.enigmatic.GetPath;
import pl.enigmatic.mobirise.aem.core.SiteStructure;

/**
 *
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class HomePage implements GetPath {

	/**
	 * site structure
	 */
	private final SiteStructure site;

	/**
	 * Default constructor
	 *
	 */
	public HomePage(final SlingHttpServletRequest request) {
		super();
		site = request.adaptTo(SiteStructure.class);
	}

	@Override
	public String getPath() {
		return site.getHome().getPath();
	}

}