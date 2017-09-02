package pl.enigmatic.mobirise.aem.core;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * 
 * @author Radoslaw Wesolowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class SiteStructure {

	/** current page */
	@Inject
	private Page currentPage;
	private Page brand;
	private Page domain;
	private Page home;

	/** Default constructor. */
	public SiteStructure() {
		super();
	}

	/**
	 * Initialization. Parameters path and resource are used to set child
	 * (pointed to by path).
	 */
	@PostConstruct
	protected void init() {
		int level = 0;
		brand = currentPage.getAbsoluteParent(++level);
		domain = currentPage.getAbsoluteParent(++level);
		home = currentPage.getAbsoluteParent(++level);
	}

	public Page getBrand() {
		return brand;
	}

	public Page getDomain() {
		return domain;
	}

	public Page getHome() {
		return home;
	}
}