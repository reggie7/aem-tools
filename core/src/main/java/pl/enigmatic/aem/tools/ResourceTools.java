package pl.enigmatic.aem.tools;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.NonExistingResource;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.api.resource.SyntheticResource;

import com.day.cq.commons.jcr.JcrConstants;

import pl.enigmatic.tools.PathTools;

/**
 * Utility with common useful methods to process {@link Resource}.
 *
 * @author Radosław Wesołowski
 */
public final class ResourceTools {

	/** default constructor */
	private ResourceTools() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks whether the <code>resource</code> actually exists in the
	 * repository.
	 *
	 * @param resource
	 *			the resource to check
	 * @return <code>true</code> if resource is not <code>null</code>,
	 *		 {@link NonExistingResource}, {@link SyntheticResource} or
	 *		 {@link org.apache.sling.resourceresolver.impl.helper.StarResource
	 *		 StarResource}
	 */
	public static boolean exists(final Resource resource) {
		if (resource == null) {
			return false;
		} else if (resource instanceof ResourceWrapper) {
			return exists(((ResourceWrapper) resource).getResource());
		} else if (StringUtils.isBlank(resource.getPath())) {
			return false;
		}
		return !(ResourceUtil.isNonExistingResource(resource) || ResourceUtil.isStarResource(resource) || ResourceUtil.isSyntheticResource(resource));
	}

	/**
	 * Gets the path of the resource, replaces {@link #separator path separators}
	 * with {@link #idSeparator}.
	 *
	 * @param resource
	 *			the resource to get an id for
	 * @return absolute id of the resource
	 */
	public static String createId(final Resource resource) {
		return PathTools.createId(resource.getPath());
	}

	/**
	 * Gets the path of the resource, finds the part after
	 * {@link JcrConstants#JCR_CONTENT jcr:content}, replaces {@link #separator
	 * path separators} with {@link #idSeparator}.
	 *
	 * @param resource
	 *			the resource to get an id for
	 * @return id of the resource relative to the page
	 */
	public static String createShortId(final Resource resource) {
		return PathTools.createId(StringUtils.substringAfter(resource.getPath(), JcrConstants.JCR_CONTENT + PathTools.separator));
	}

	/**
	 * Finds the ancestor that matches the given name pattern.
	 *
	 * @param resource
	 *			the resource being the descendant of the ancestor to be found
	 * @param namePattern
	 *			the pattern to be matched by the ancestor name
	 * @return the ancestor that matches the given name pattern
	 */
	public static Resource findAncestorByName(final Resource resource, final String namePattern) {
		Resource r = resource;
		while (r != null && !r.getName().matches(namePattern)) {
			r = r.getParent();
		}
		return r;
	}

	/**
	 * Returns parent at given level.
	 *
	 * @param resource
	 *			the resource being the descendant of the ancestor to be found
	 * @param parentLevel
	 * @return the ancestor at level given as parameter
	 */
	public static Resource getParentAtLevel(final Resource resource, final int level) {
		if (resource == null || level < 1) {
			return null;
		}
		Resource r = resource;
		int i = level;
		while (r != null && i > 0) {
			r = r.getParent();
			i--;
		}
		return r;
	}

	/**
	 * Finds first child of the given resource
	 * that is of one of the given resource types
	 * @param parent
	 */
	public static Resource findFirstChildOfType(final Resource parent, final String... types) {
		final Iterator<Resource> i = parent.listChildren();
		while (i.hasNext()) {
			final Resource r = i.next();
			for (final String t : types) {
				if (r.isResourceType(t)) {
					return r;
				}
			}
		}
		return null;
	}

	/**
	 * @param descendant descendant object (in a special case - child)
	 * @param ancestor ancestor object (in a special case - parent)
	 * @return relative path of descendant in relation to ancestor.
	 */
	public static String getRelativePath(final Resource descendant, final Resource ancestor) {
		return PathTools.getRelativePath(descendant.getPath(), ancestor.getPath());
	}
}