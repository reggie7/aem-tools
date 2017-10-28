package pl.enigmatic.aem.config.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationConstants;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;

import pl.enigmatic.aem.config.CloudServiceResolver;

@Service
@Component
public class CloudServiceResolverImpl implements CloudServiceResolver {

	@Override
	public Configuration resolve(final Page page, final String name) {
		final ResourceResolver resourceResolver = page.getContentResource().getResourceResolver();
		final ConfigurationManager manager = resourceResolver.adaptTo(ConfigurationManager.class);
		if (manager != null) {
			final InheritanceValueMap pageProperties = new HierarchyNodeInheritanceValueMap(page.getContentResource());
			final String[] paths = pageProperties.getInherited(ConfigurationConstants.PN_CONFIGURATIONS, new String[]{});
			return manager.getConfiguration(name, paths);
		}
		return null;
	}
}