package pl.enigmatic.aem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables = SlingHttpServletRequest.class)
public class IndexChildren {

	/** parent resource */
	@Optional
	@Inject
	private Resource parent;

	/** child name pattern */
	@Optional
	@Inject
	private String regex;

	/** Default constructor */
	public IndexChildren() {
		super();
	}

	public static Map<Integer, Resource> index(final Resource parent, final String regex) {
		final Pattern pattern = Pattern.compile(regex);
		final LinkedHashMap<Integer, Resource> map = new LinkedHashMap<>();
		for (final Resource r : parent.getChildren()) {
			final Matcher m = pattern.matcher(r.getName());
			if (m.find()) {
				map.put(Integer.parseInt(m.group(1)), r);
			}
		}
		return map;
	}

	public Map<Integer, Resource> getIndexed() {
		return index(parent, regex);
	}
}