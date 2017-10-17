package pl.enigmatic.aem;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Optional;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.Component;

import pl.enigmatic.aem.tools.PageTools;

/**
 * Base for component model classes. Exposes fundamental objects used to perform
 * further calculations.
 * 
 * @author "Radosław Wesołowski<radoslaw.wesolowski@hi-res.de>"
 */
public class ComponentModel extends ResourceWrapper {

	/** {@link SlingHttpServletRequest the request} */
	protected final SlingHttpServletRequest request;

	/**
	 * {@link Resource current resource}
	 */
	protected final Resource resource;

	/**
	 * {@link Component current component}
	 */
	@Inject
	@Optional
	protected Component component;

	/**
	 * {@link ValueMap current resource's properties}
	 */
	@Inject
	@Optional
	protected ValueMap properties;

	/**
	 * {@link Page current page}
	 */
	@Inject
	@Optional
	protected Page currentPage;

	/**
	 * {@link Page resource page}
	 */
	@Inject
	@Optional
	protected Page resourcePage;

	/**
	 * {@link ResourceResolver resource resolver}
	 */
	@Inject
	protected ResourceResolver resourceResolver;
	
	/**
	 * {@link PageManager page manager}
	 */
	@Inject
	protected PageManager pageManager;


	/**
	 * the request + resource based constructor
	 */
	private ComponentModel(final SlingHttpServletRequest request, final Resource resource) {
		super(resource);
		this.request = request;
		this.resource = resource;
	}

	/**
	 * the request based constructor
	 */
	public ComponentModel(final SlingHttpServletRequest request) {
		this(request, request.getResource());
	}

	/**
	 * the resource based constructor
	 */
	public ComponentModel(final Resource resource) {
		this(null, resource);
	}

	public String getRelativePath() {
		return PageTools.getRelativePath(this, currentPage);
	}
}