package pl.enigmatic.mobirise.aem.comps;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;

import pl.enigmatic.aem.GetPath;

/**
 *
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ParentPage implements GetPath {

	/**
	 * current page
	 */
	@Inject
	private Page currentPage;

	/**
	 * Default constructor
	 *
	 */
	public ParentPage() {
		super();
	}

	@Override
	public String getPath() {
		return currentPage.getParent().getPath();
	}

}