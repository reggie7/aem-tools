package pl.enigmatic.aem;

import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class GetPage {

	// =====================================================
	// input variables
	/**
	 * page path
	 */
	@Inject
	private String input;
	/**
	 * {@link PageManager page manager}
	 */
	@Inject
	private PageManager pageManager;

	// =====================================================
	// output variables
	/**
	 * resolved page
	 */
	private Page page;

	/**
	 * Default empty constructor
	 */
	public GetPage() {
		super();
	}

	/**
	 * Simple initialization method for the use-class.
	 *
	 * @throws UnsupportedEncodingException
	 */
	@PostConstruct
	protected void init() {
		page = pageManager.getPage(input);
	}

	/**
	 * Accessor for {@link #page}
	 *
	 * @return {@link #page}
	 */
	public Page getPage() {
		return page;
	}
}