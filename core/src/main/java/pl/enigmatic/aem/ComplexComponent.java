package pl.enigmatic.aem;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import pl.enigmatic.tools.PathTools;

/**
 * Simple class for utilizing the complex component concept. Will be useful for
 * both the single page and expand approach of zooming into the components.
 *
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ComplexComponent extends ComponentModel {

	/**
	 * the parameter name pointing at the resource to get zoomed
	 */
	private static final String TARGET = "target";
	// =============================================================
	// zoom-in approach output variables
	/** should the current component get expanded? */
	private boolean expand;
	/**
	 * is the current component outer in respect of the destination zoomed
	 * component?
	 */
	private boolean outer;
	/**
	 * is the current component inner in respect of the destination zoomed
	 * component?
	 */
	private boolean inner;
	/**
	 * relative child path to the next component on the path to the destination
	 * component
	 */
	private String child;
	/**
	 * is the current component the parent of the destination zoomed component?
	 */
	private boolean parent = false;
	/**
	 * target resource path
	 */
	private String target;

	/** Default empty constructor. */
	public ComplexComponent(final SlingHttpServletRequest request) {
		super(request);
	}

	/** Initialization of all necessary fields. */
	@PostConstruct
	protected void init() {
		target = StringUtils.stripToEmpty(request.getParameter(TARGET));
		final String path = resource.getPath();
		expand = StringUtils.isNotBlank(target) && path.equals(target);
		inner = !expand && PathTools.isAncestor(path, target);
		outer = !expand && PathTools.isAncestor(target, path);
		if (this.outer) {
			child = PathTools.getRelativePath(target, path);
			if (child.contains(PathTools.separator)) {
				child = StringUtils.substringBefore(child, PathTools.separator);
			} else {
				parent = true;
			}
		}
	}

	/**
	 * {@link #expand} property accessor.
	 *
	 * @return {@link #expand}
	 */
	public boolean isExpand() {
		return expand;
	}

	/**
	 * {@link #outer} property accessor.
	 *
	 * @return {@link #outer}
	 */
	public boolean isOuter() {
		return outer;
	}

	/**
	 * {@link #inner} property accessor.
	 *
	 * @return {@link #inner}
	 */
	public boolean isInner() {
		return inner;
	}

	/**
	 * Is the current resource none of {@link #expand} {@link #outer} {@link #inner}
	 */
	public boolean isUnrelated() {
		return !expand && !outer && !inner;
	}

	/**
	 * {@link #child} property accessor.
	 *
	 * @return {@link #child}
	 */
	public String getChild() {
		return child;
	}

	/**
	 * {@link #parent} property accessor.
	 *
	 * @return {@link #parent}
	 */
	public boolean isParent() {
		return parent;
	}

	/**
	 * {@link #target} property accessor.
	 *
	 * @return {@link #target}
	 */
	public String getTarget() {
		return target;
	}
}