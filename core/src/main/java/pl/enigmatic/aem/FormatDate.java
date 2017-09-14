package pl.enigmatic.aem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class FormatDate extends ComponentModel {

	@Inject
	@Optional
	private Calendar calendar;
	@Inject
	@Optional
	private Date date;
	@Inject
	@Optional
	private String format;

	private String string = null;

	/**
	 * Default constructor
	 * @param request
	 */
	public FormatDate(final SlingHttpServletRequest request) {
		super(request);
	}

	@PostConstruct
	protected void init() {
		final DateFormat formatter = new SimpleDateFormat(format, currentPage.getLanguage(false));
		if (calendar != null) {
			string = formatter.format(calendar.getTime());
		} else {
			string = formatter.format(date);
		}
	}

	@Override
	public String toString() {
		return string;
	}
}