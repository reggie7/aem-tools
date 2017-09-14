package pl.enigmatic.aem.apps.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Validates HTML in a simple manner. Gets the file content and then transforms it.
 * We define some simple rules to enable html being transformed into xml.
 * Then we check if the xml is correct. It is not perfect, but automatically checks
 * for some trivial mistakes.
 * @author Radosław Wesołowski
 *
 */
public class HTMLValidator extends XMLValidator {

	private static final Pattern EMPTY_ATTRIBUTES_2 = Pattern.compile("\\s[a-zA-Z0-9\\-\\.]+>");

	private static final Pattern EMPTY_ATTRIBUTES_1 = Pattern.compile("\\s[a-zA-Z0-9\\-\\.]+\\s");

	public static final String HTML_FILE_TYPE = "html";

	/**
	 * default constructor
	 */
	public HTMLValidator() {
		super(HTML_FILE_TYPE);
	}

	/** transforms html to some kind of xml that we can easily check
	 * @see com.kyocera.aem.apps.core.bmw.aems2.digitals2.apps.XMLValidator#transform(java.lang.String)
	 */
	protected String transform(final String content) {
		String result = content;
		result = "<root>" + result + "</root>";
		result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + result;

		result = result.replaceAll("data-sly-unwrap=", "xxx--xxx");
		result = result.replaceAll("data-sly-unwrap", "data-sly-unwrap=\"\"");
		result = result.replaceAll("xxx--xxx", "data-sly-unwrap=");

		result = result.replaceAll("<!--\\W*/\\*", "<comment>");
		result = result.replaceAll("\\*/\\W*-->", "</comment>");

		result = result.replaceAll("<!DOCTYPE HTML>", StringUtils.EMPTY);
		result = result.replaceAll("&nbsp;", StringUtils.EMPTY);
		result = result.replaceAll(" < ", " > ");
		result = result.replaceAll(" <= ", " >= ");
		result = result.replaceAll(" && ", " and ");
		result = result.replaceAll("&", " and ");

		result = result.replaceAll("<!--googleoff: all-->", StringUtils.EMPTY);
		result = result.replaceAll("<!--googleon: all-->", StringUtils.EMPTY);

		Matcher m = EMPTY_ATTRIBUTES_1.matcher(result);
		while (m.find()) {
			result = m.replaceAll(" ");
			m = EMPTY_ATTRIBUTES_1.matcher(result);
		}
		m = EMPTY_ATTRIBUTES_2.matcher(result);
		while (m.find()) {
			result = m.replaceAll(">");
			m = EMPTY_ATTRIBUTES_2.matcher(result);
		}
		return result;
	}

}