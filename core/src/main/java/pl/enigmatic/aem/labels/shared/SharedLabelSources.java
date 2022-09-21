package pl.enigmatic.aem.labels.shared;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import pl.enigmatic.aem.labels.Constants;
import pl.enigmatic.tools.PathTools;

import java.util.Locale;
import java.util.Optional;

import static java.util.Objects.isNull;

public final class SharedLabelSources {

	private final String name = Constants.DEFAULT_RESOURCE_NAME;
	private final ResourceResolver resolver;
	private final Resource langRootLabels;
	private final Resource globalLabels;

	public SharedLabelSources(final Resource resource, final int langRootDepth) {
		resolver = resource.getResourceResolver();
		final Page page = Optional.of(resource)
				.map(resolver.adaptTo(PageManager.class)::getContainingPage)
				.map(p -> p.getParent(p.getDepth() - langRootDepth))
				.get();
		langRootLabels = defaultIfNull(page.getContentResource(name), PathTools.path(page.getContentResource().getPath(), name));
		{
			final Locale locale = page.getLanguage();
			final String path = PathTools.separator + PathTools.path(
					Constants.CONTENT,
					name,
					locale.getLanguage().toLowerCase(),
					locale.getCountry().toLowerCase(),
					JcrConstants.JCR_CONTENT
			);
			globalLabels = defaultIfNull(resolver.getResource(path), path);
		}
	}

	private Resource defaultIfNull(final Resource resource, final String path) {
		return isNull(resource) ? new SyntheticResource(resolver, path, "") : resource;
	}

	private Resource defaultChildIfNull(final Resource parent, final String path) {
		return defaultIfNull(parent.getChild(path), PathTools.path(parent.getPath(), path));
	}

	public Resource getLangRootLabels() {
		return langRootLabels;
	}

	public Resource getLangRootLabel(final String path) {
		return defaultChildIfNull(langRootLabels, path);
	}

	public Resource getGlobalLabels() {
		return globalLabels;
	}

	public Resource getGlobalLabel(final String path) {
		return defaultChildIfNull(globalLabels, path);
	}

}
