package pl.enigmatic.aem;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks date format pattern.
 *
 * @author Radosław Wesołowski
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class DateFormatValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatValidator.class);

	@Inject
	private String pattern;

	/** default constructor */
	public DateFormatValidator() {
		super();
	}

	/**
	 * Checks if the given date pattern is correct.
	 *
	 * @param pattern the pattern to be checked
	 * @return <code>true</code>, if the <code>pattern</code> is correct
	 */
	public static boolean check(final String pattern) {
		try {
			new SimpleDateFormat(pattern).toString();
			return true;
		} catch (final IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Pattern is not correct", e);
		}
		return false;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return check(this.pattern);
	}

}