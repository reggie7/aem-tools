package pl.enigmatic.aem.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationConstants;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;

@Model(adaptables = SlingHttpServletRequest.class)
public class CloudService extends ResourceWrapper {

	@Inject
	@Optional
	private String name;

	/**
	 * {@link Page current page}
	 */
	@Inject
	@Optional
	private Page currentPage;

	/**
	 * {@link ResourceResolver resource resolver}
	 */
	@Inject
	private ResourceResolver resourceResolver;

	private Configuration configuration = null;

	/** Default empty constructor. */
	public CloudService(final SlingHttpServletRequest request) {
		super(request.getResource());
	}

	@PostConstruct
	protected void init() {
		final ConfigurationManager manager = resourceResolver.adaptTo(ConfigurationManager.class);
		if (manager != null) {
			final InheritanceValueMap pageProperties = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
			final String[] paths = pageProperties.getInherited(ConfigurationConstants.PN_CONFIGURATIONS, new String[]{});
			configuration = manager.getConfiguration(name, paths);
		}
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public Resource getResource() {
		return configuration.getContentResource();
	}
}