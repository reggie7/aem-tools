package pl.enigmatic.aem;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;

import com.google.common.collect.Lists;

/**
 * Simple accessor for properties needed in the limited paragraph system
 * component.
 *
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class LimitedParsysComponent extends ResourceWrapper {

	/** the min limit of the list size injected externally */
	@Inject
	@Default(longValues = 0L)
	private Long min;

	/** the max limit of the list size injected externally */
	@Inject
	@Default(longValues = 0L)
	private Long max;

	/**
	 * A list containing the child resources.
	 */
	private List<Resource> children = new LinkedList<>();

	/** Default simple constructor. */
	public LimitedParsysComponent(final SlingHttpServletRequest request) {
		super(request.getResource());
	}

	/**
	 * Initialization.
	 */
	@PostConstruct
	protected void init() {
		children.addAll(Lists.newLinkedList(getChildren()));
	}

	/**
	 * {@link #min} property accessor.
	 *
	 * @return the lower limit or zero if there is no limit
	 */
	public int getLowerLimit() {
		return min.intValue();
	}

	/**
	 * {@link #limit} property accessor.
	 *
	 * @return the limit or zero if there is no limit
	 */
	public int getUpperLimit() {
		return max.intValue();
	}

	/**
	 * Checks whether the parsys is indeed limited.
	 *
	 * @return <code>true</code> if {@link #min} is valid, so the parsys is
	 *		 indeed limited in size
	 */
	public boolean isLowerLimited() {
		return min.intValue() > 0;
	}

	/**
	 * Checks whether the parsys is indeed limited.
	 *
	 * @return <code>true</code> if {@link #max} is valid, so the parsys is
	 *		 indeed limited in size
	 */
	public boolean isUpperLimited() {
		return max.intValue() > 0;
	}

	/**
	 * Checks whether the limitation is reached.
	 *
	 * @return <code>true</code> if {@link #max} is reached by the resource children size
	 */
	public boolean isLowerLimitReached() {
		return isLowerLimited() && min <= children.size();
	}

	/**
	 * Checks whether the limitation is reached.
	 *
	 * @return <code>true</code> if {@link #max} is reached by the resource
	 *		 children size
	 */
	public boolean isUpperLimitReached() {
		return isUpperLimited() && max <= children.size();
	}

	/**
	 * @return number of components that exceed the upper limit
	 */
	public int getExtraComponentsCount() {
		return Math.max(isUpperLimited() ? children.size() - getUpperLimit() : 0, 0);
	}

	/**
	 * Checks whether the upper limit is exceeded.
	 *
	 * @return children count.
	 */
	public boolean isUpperLimitExceeded() {
		return getExtraComponentsCount() > 0;
	}
}