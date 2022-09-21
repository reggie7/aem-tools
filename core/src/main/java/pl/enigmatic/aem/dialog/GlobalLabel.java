package pl.enigmatic.aem.dialog;

import com.day.cq.commons.jcr.JcrConstants;
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

import java.util.Locale;
import java.util.Optional;

import static pl.enigmatic.aem.comps.global.GlobalLabelsDefinitions.PN_VALUE;

public final class GlobalLabel {

	private static final String SLASH = "/";

	private final ResourceResolver resolver;
	private final Resource widget;
	private final String name;
	private final int depth;
	private final Page page;
	private final Resource target;

	public GlobalLabel(final SlingHttpServletRequest request) {
		resolver = request.getResourceResolver();
		widget = request.getResource();
		final ValueMap widgetProps = widget.getValueMap();
		name = widgetProps.get("name", String.class);
		depth = widgetProps.get("depth", 4);
		page = Optional.of(request.getRequestPathInfo()).map(RequestPathInfo::getSuffixResource)
				.map(resolver.adaptTo(PageManager.class)::getContainingPage)
				.map(page -> page.getParent(page.getDepth() - depth))
				.get();
		target = name.startsWith(SLASH) ? resolveGlobalResource() : resolveLocalResource();
	}

	private Resource resolveGlobalResource() {
		final Locale locale = page.getLanguage();
		final String path = SLASH + join(
				"content",
				GlobalLabelsDefinitions.NN_LABELS,
				locale.getLanguage().toLowerCase(),
				locale.getCountry().toLowerCase(),
				JcrConstants.JCR_CONTENT,
				name
		);
		return Optional.of(path).map(resolver::getResource).orElseGet(() -> syntheticResource(path));
	}

	private Resource resolveLocalResource() {
		final String path = join(GlobalLabelsDefinitions.NN_LABELS, name);
		return Optional.of(path).map(page::getContentResource)
				.orElseGet(() -> syntheticResource(join(page.getContentResource().getPath(), path)));
	}

	private static String join(final Object... path) {
		return StringUtils.join(path, SLASH);
	}

	private Resource syntheticResource(final String path) {
		return new SyntheticResource(resolver, path, "");
	}

	public String getPath() {
		return join(target.getPath(), PN_VALUE);
	}

	public String getValue() {
		return target.getValueMap().get(PN_VALUE, String.class);
	}

}
