package pl.enigmatic.aem.tools;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.Optional;

/**
 * Utility with common useful methods to process {@link Page}.
 *
 * @author Radosław Wesołowski
 */
public final class PageTools {

	/** default constructor */
	private PageTools() {
		throw new UnsupportedOperationException();
	}

	public static Page getContainingPage(final Resource resource) {
		return Optional.ofNullable(resource)
				.map(r -> r.getResourceResolver())
				.map(rr -> rr.adaptTo(PageManager.class))
				.map(pageManager -> pageManager.getContainingPage(resource))
				.orElse(null);
	}

	/**
	 * @param descendant descendant object (in a special case - child)
	 * @param ancestor ancestor object (in a special case - parent)
	 * @return relative path of descendant in relation to ancestor.
	 */
	public static String getRelativePath(final Resource descendant, final Page ancestor) {
		return ResourceTools.getRelativePath(descendant, ancestor.getContentResource());
	}
}