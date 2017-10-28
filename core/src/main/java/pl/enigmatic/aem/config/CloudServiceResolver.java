package pl.enigmatic.aem.config;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.webservicesupport.Configuration;

public interface CloudServiceResolver {

	Configuration resolve(final Page page, final String name);

}