package pl.enigmatic.aem;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

/**
 * A simple class for easing access to named parameters of the current request
 *
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class GetParameter extends AbstractMap<String, Object> {

	/** The request. */
	private SlingHttpServletRequest request;

	/**
	 * Default empty constructor
	 */
	public GetParameter(final SlingHttpServletRequest request) {
		super();
		this.request = request;
	}

	@Override
	public Object get(final Object key) {
		return request.getParameter(String.valueOf(key));
	}

	/**
	 * Not used, needed only for complying with the interface definition.
	 *
	 * @see java.util.AbstractMap#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return new HashSet<>();
	}
}