package pl.enigmatic.aem.config.impl;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationConstants;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;

import pl.enigmatic.aem.config.CloudServiceResolver;

@Service
@Component
public class CloudServiceResolverImpl implements CloudServiceResolver {

	private static final String[] EMPTY_PATHS = new String[]{};

	@Override
	public Configuration resolve(final Page page, final String name) {
		final ResourceResolver resourceResolver = page.getContentResource().getResourceResolver();
		final ConfigurationManager manager = resourceResolver.adaptTo(ConfigurationManager.class);
		if (manager != null) {
			final ArrayList<String> paths = new ArrayList<>();
			Page p = page;
			while (p != null) {
				Collections.addAll(paths, p.getProperties().get(ConfigurationConstants.PN_CONFIGURATIONS, EMPTY_PATHS));
				p = p.getParent();
			}
			return manager.getConfiguration(name, paths.toArray(new String[paths.size()]));
		}
		return null;
	}
}