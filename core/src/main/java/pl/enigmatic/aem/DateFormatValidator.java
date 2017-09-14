package pl.enigmatic.aem;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for checking date format. It gets the input values from
 * <code>resource</code>, <code>request</code> and <code>properties</code> to
 * expose processed values driving behavior and final appearance of the news
 * teaser component.
 *
 * @author Radosław Wesołowski
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class DateFormatValidator {

	/**
	 * Logger used to log warning when exception occurs.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatValidator.class);

	@Inject
	private String pattern;

	private boolean valid;

	/** the default constructor */
	public DateFormatValidator() {
		super();
	}

	/**
	 * Checks correctness of the date pattern.
	 *
	 * @param pattern
	 *			the pattern to be checked
	 * @return <code>true</code>, if the <code>pattern</code> is correct
	 */
	public static boolean checkDateFormat(final String pattern) {
		try {
			new SimpleDateFormat(pattern).toString();
		} catch (final IllegalArgumentException | NullPointerException e) {
			DateFormatValidator.LOGGER.error("Pattern is not correct", e);
			return false;
		}
		return true;
	}

	/**
	 * Initializes all the <i>output</i> values of this object.
	 */
	@PostConstruct
	protected void init() {
		this.valid = checkDateFormat(this.pattern);
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return this.valid;
	}

}