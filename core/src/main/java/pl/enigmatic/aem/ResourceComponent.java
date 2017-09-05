package pl.enigmatic.aem;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import pl.enigmatic.aem.tools.ResourceTools;

/**
 * A simple class for easing access to named child resource of the current
 * resource. Extends the {@link ResourceWrapper}.
 *
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ResourceComponent extends ResourceWrapper {

	/** Path of child resource to be retrieved. */
	@Inject
	@Optional
	private String path;

	/** The target resource. */
	private Resource target;

	/**
	 * Default empty constructor
	 */
	public ResourceComponent(final SlingHttpServletRequest request) {
		super(request.getResource());
	}

	/**
	 * Simple initialization method for the use-class.
	 */
	@PostConstruct
	protected void init() {
		final Resource resource = super.getResource();
		if (StringUtils.isNotEmpty(path)) {
			target = resource.getChild(path);
		}
		if (target == null) {
			target = resource;
		}
	}

	@Override
	public Resource getResource() {
		return target;
	}

	/**
	 * Accessor for {@link #id}
	 *
	 * @return {@link #id}
	 */
	public String getId() {
		return ResourceTools.createId(getResource());
	}

	/**
	 * Accessor for {@link #shortId}
	 *
	 * @return {@link #shortId}
	 */
	public String getShortId() {
		return ResourceTools.createShortId(getResource());
	}
}