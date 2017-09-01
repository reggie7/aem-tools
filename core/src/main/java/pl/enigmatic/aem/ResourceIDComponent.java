package pl.enigmatic.aem;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import pl.enigmatic.aem.tools.ResourceTools;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ResourceIDComponent {

	/**
	 * {@link Resource resource}
	 */
	@Inject
	private Resource resource;

	/** Path of resource to be retrieved. */
	@Optional
	@Inject
	private String path;

	/**
	 * Default empty constructor
	 */
	public ResourceIDComponent() {
		super();
	}

	/**
	 * Simple initialization method for the use-class.
	 */
	@PostConstruct
	protected void init() {
		if (StringUtils.isNotEmpty(path)) {
			resource = resource.getChild(path);
		}
	}

	/**
	 * Accessor for {@link #id}
	 *
	 * @return {@link #id}
	 */
	public String getId() {
		return ResourceTools.createId(resource);
	}

	/**
	 * Accessor for {@link #shortId}
	 *
	 * @return {@link #shortId}
	 */
	public String getShortId() {
		return ResourceTools.createShortId(resource);
	}
}