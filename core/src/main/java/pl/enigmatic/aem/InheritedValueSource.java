package pl.enigmatic.aem;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;

import pl.enigmatic.tools.PathTools;

@Model(adaptables = SlingHttpServletRequest.class)
public class InheritedValueSource extends ComponentModel {

	@Inject
	private String propertyPath;
	private Page source;

	public InheritedValueSource(final SlingHttpServletRequest request) {
		super(request);
	}

	/**
	 * initialization
	 */
	@PostConstruct
	protected void init() {
		String propertyName = StringUtils.substringAfterLast(propertyPath, PathTools.separator);
		String path = null;
		if (StringUtils.isBlank(propertyName)) {
			propertyName = propertyPath;
		} else {
			path = propertyPath.substring(0, propertyPath.length() - propertyName.length() - 1);
		}
		final String relPath = PathTools.path(getRelativePath(), path);
		Page page = currentPage;
		while (page != null) {
			Resource r = page.getContentResource(relPath);
			if (r != null && r.getValueMap().containsKey(propertyName)) {
				source = page;
				return;
			}
			page = page.getParent();
		}
	}

	public Page getSource() {
		return source;
	}

}