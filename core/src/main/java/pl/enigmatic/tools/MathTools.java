package pl.enigmatic.tools;

/**
 * Class for exposing common useful mathematical methods.
 * 
 * @author Radosław Wesołowski
 */
public final class MathTools {

	/** const for 100 percent */
	public static final int FULL_PERCENTAGE = 100;

	/** the default constructor */
	private MathTools() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Simple percentage calculation.
	 * 
	 * @param part
	 *			the part constituting the percentage
	 * @param total
	 *			the total value equivalent to 100%
	 * @return the percent of <code>total</code> that <code>part</code>
	 *		 constitutes
	 */
	public static double percent(final double part, final double total) {
		return MathTools.FULL_PERCENTAGE * part / total;
	}

	/**
	 * Simple clamping method.
	 * 
	 * @param x
	 *			the number to be clamped
	 * @param left
	 *			the left bound to clamp to
	 * @param right
	 *			the right bound to clamp to
	 * @return <ul>
	 *		 <li>x, if left <= x <= right</li>
	 *		 <li>x, if left > right</li>
	 *		 <li>left, if left > x</li>
	 *		 <li>right, if x > right</li>
	 *		 </ul>
	 */
	public static double clamp(final double x, final double left, final double right) {
		if (left <= right) {
			if (x < left) {
				return left;
			} else if (x > right) {
				return right;
			}
		}
		return x;
	}

}