package pl.enigmatic.aem.dialog;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.SyntheticResource;
import org.apache.sling.api.resource.ValueMap;
import pl.enigmatic.aem.comps.global.GlobalLabelsDefinitions;

import java.util.Optional;

public final class GlobalLabel {

	public static final String PN_VALUE = "value";
	private final Resource resource;

	public GlobalLabel(final SlingHttpServletRequest request) {
		final ResourceResolver resolver = request.getResourceResolver();
		final ValueMap widget = request.getResource().getValueMap();
		final String path = join(GlobalLabelsDefinitions.LABELS, widget.get("name", String.class));
		final int depth = widget.get("depth", 4);
		final Optional<Page> pageOpt = Optional.of(request.getRequestPathInfo()).map(RequestPathInfo::getSuffixResource)
				.map(resolver.adaptTo(PageManager.class)::getContainingPage)
				.map(page -> page.getParent(page.getDepth() - depth));
		this.resource = pageOpt.map(p -> p.getContentResource(path)).orElseGet(() -> pageOpt.map(Page::getContentResource)
				.map(Resource::getPath)
				.map(p -> new SyntheticResource(resolver, join(p, path), ""))
				.get()
		);
	}

	private static String join(final Object... path) {
		return StringUtils.join(path, "/");
	}

	public String getPath() {
		return join(resource.getPath(), PN_VALUE);
	}

	public String getValue() {
		return resource.getValueMap().get(PN_VALUE, String.class);
	}

}
