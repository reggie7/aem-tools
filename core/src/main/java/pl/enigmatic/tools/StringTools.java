package pl.enigmatic.tools;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class with common useful methods to process {@link String strings}.
 * Only those not found in {@link StringUtils}
 *
 * @author Radosław Wesołowski
 */
public final class StringTools {

	/**
	 * The default constructor
	 * @throws UnsupportedOperationException
	 */
	private StringTools() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks whether the <code>strings</code> parameters passed are all
	 * {@link StringUtils#isBlank(CharSequence) blank}.
	 *
	 * @param strings strings to be checked
	 * @return <code>true</code> if all the strings are
	 *		 {@link StringUtils#isBlank(CharSequence) blank}
	 */
	public static boolean areAllNonBlank(final String... strings) {
		for (final String s : strings) {
			if (StringUtils.isBlank(s)) {
				return false;
			}
		}
		return true;
	}

}