package pl.enigmatic.tools;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;

/**
 * Class for exposing common useful methods to process paths.
 *
 * @author Radosław Wesołowski
 */
public final class PathTools {

	/** content repository path separator char
	 * @see java.io.File#separatorChar
	 * @see #separator
	  */
	public static final char separatorChar = '/';
	/** content repository path separator
	 * @see java.io.File#separator
	 * @see #separatorChar
	  */
	public static final String separator = String.valueOf(separatorChar);

	/** an extension separator char */
	public static final char extensionSeparatorChar = '.';
	/** an extension separator */
	public static final String extensionSeparator = String.valueOf(extensionSeparatorChar);

	/** an arbitrary id separator char */
	public static final char idSeparatorChar = '_';
	/** an arbitrary id separator */
	public static final String idSeparator = String.valueOf(idSeparatorChar);

	/** joiner for path parts */
	private static final Joiner PATH_JOINER = Joiner.on(separator).skipNulls();
	/** joiner for path parts */
	private static final Joiner NAME_JOINER = Joiner.on(extensionSeparator).skipNulls();

	/** the default constructor */
	private PathTools() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Joins the passed parameters using {@link #separator} as separator
	 * @param first
	 * @param second
	 * @param rest
	 * @return
	 * @see com.google.common.base.Joiner#join(java.lang.Object, java.lang.Object, java.lang.Object[])
	 */
	public static String path(final Object first, final Object second, final Object... rest) {
		return PATH_JOINER.join(first, second, rest);
	}

	/**
	 * Joins the passed parameters using {@link #separator} as separator
	 * @param first
	 * @param second
	 * @param rest
	 * @return
	 * @see com.google.common.base.Joiner#join(java.lang.Object, java.lang.Object, java.lang.Object[])
	 */
	public static String name(final Object first, final Object second, final Object... rest) {
		return NAME_JOINER.join(first, second, rest);
	}

	/**
	 * Replaces {@link #separator path separators} and other non-alphanumeric
	 * characters with {@link #idSeparator}.
	 */
	public static String createId(final String string) {
		String result = StringUtils.replaceAll(string, separator, idSeparator);
		result = StringUtils.replaceAll(result, ":", idSeparator);
		return result;
	}

	/**
	 * Check if <code>ancestor</code> path is on <code>descendant</code> path in
	 * order to determine whether indeed those strings represent locations of
	 * objects in ancestor-descendant relationship. In the special case
	 * <code>descendant</code> will be the <code>child</code> of
	 * <code>ancestor</code> and the <code>ancestor</code> will be the
	 * <b>parent</b> of <code>descendant</code>.
	 *
	 * @param descendant the path representing descendant object (in a special case -
	 *			child)
	 * @param ancestor the path representing ancestor object (in a special case -
	 *			parent)
	 * @return true if the paths given represent ancestor-descendant
	 *		 relationship
	 */
	public static boolean isAncestor(final String descendant, final String ancestor) {
		// Path separator is added to match whole node names. Otherwise
		// '/content/cow' would be an ancestor of '/content/cowboy/home'
		return StringTools.areAllNonBlank(descendant, ancestor) &&
				StringUtils.startsWith(StringUtils.join(descendant, separator), StringUtils.join(ancestor, separator));
	}

	/**
	 * @param descendant the path representing descendant object (in a special case - child)
	 * @param ancestor the path representing ancestor object (in a special case - parent)
	 * @return relative path of descendant in relation to ancestor.
	 */
	public static String getRelativePath(final String descendant, final String ancestor) {
		return isAncestor(descendant, ancestor) ? descendant.substring(1 + ancestor.length()) : null;
	}

	/**
	 * Checks whether the <code>path</code> starts with /content/
	 * @param path
	 * @return <code>true</code> if the path is on the content path of the repository
	 */
	public static boolean isContent(final String path) {
		return StringUtils.defaultString(path).toLowerCase().matches("^/content/.+$");
	}
}