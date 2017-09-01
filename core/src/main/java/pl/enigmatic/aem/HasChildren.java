package pl.enigmatic.aem;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import pl.enigmatic.aem.tools.ResourceTools;

/**
 * A simple class for easing checking of children underneath given paths. It
 * extends {@link Map} to be easily used throughout markup expressions.
 *
 * @author Radoslaw Wesolowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class HasChildren extends AbstractMap<String, Boolean> {

	/** The calling resource. */
	@Inject
	private Resource resource;

	/** Default constructor. */
	public HasChildren() {
		super();
	}

	/**
	 * The {@link Map#get(Object)} method implementation that allows us to
	 * dynamically use any given path of current resource's child in order to
	 * check it for children.
	 *
	 * @param key
	 *			the object representing path of the child to be checked for
	 *			children
	 * @return <code>true</code> only if the child under the given
	 *		 <code>key</code> path has anyc children
	 *
	 * @see java.util.AbstractMap#get(java.lang.Object)
	 */
	@Override
	public Boolean get(final Object key) {
		final String path = String.valueOf(key);
		final Resource child;
		if (key == null || StringUtils.isBlank(path)) {
			child = resource;
		} else {
			child = resource.getChild(path);
		}
		if (child != null && ResourceTools.exists(child)) {
			return child.hasChildren();
		}
		return false;
	}

	/**
	 * Not used, needed only for complying with the interface definition.
	 *
	 * @see java.util.AbstractMap#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<String, Boolean>> entrySet() {
		return new HashSet<>();
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof HasChildren) {
			final HasChildren hc = (HasChildren) o;
			return (resource == null || resource.equals(hc.resource)) && super.equals(o);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int hashCode = super.hashCode();
		if (resource == null) {
			return hashCode;
		}
		return hashCode * resource.hashCode();
	}
}