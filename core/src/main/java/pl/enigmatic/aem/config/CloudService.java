package pl.enigmatic.aem.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.webservicesupport.Configuration;

@Model(adaptables = SlingHttpServletRequest.class)
public class CloudService extends ResourceWrapper {

	@OSGiService
	private CloudServiceResolver resolver;

	@Inject
	@Optional
	private String name;

	/**
	 * {@link Page current page}
	 */
	@Inject
	@Optional
	private Page currentPage;

	private Configuration configuration = null;

	/** Default empty constructor. */
	public CloudService(final SlingHttpServletRequest request) {
		super(request.getResource());
	}

	@PostConstruct
	protected void init() {
		configuration = resolver.resolve(currentPage, name);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public Resource getResource() {
		return configuration.getContentResource();
	}
}