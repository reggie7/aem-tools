package pl.enigmatic.aem;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * Simple integers list for sightly usage.
 *
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class Range {

	/** the end point of iteration, included */
	@Inject
	@Optional
	private long to;

	/**
	 * Default Constructor.
	 */
	public Range() {
		super();
	}

	/**
	 *
	 * @return an iterator to go through all the integers in range [from, to]
	 */
	public Iterator<Integer> getIterator() {
		return new RangeIterator(this);
	}

	/**
	 * Simple iterator for iterating over the simple list.
	 *
	 * @author Radosław Wesołowski
	 *
	 */
	private static class RangeIterator implements Iterator<Integer> {

		/** range object */
		private final Range range;

		/** current index */
		private int current;

		/**
		 * Simple Constructor.
		 */
		protected RangeIterator(final Range range) {
			this.range = range;
			current = 1;
		}

		/**
		 * {@link Iterator#hasNext} simple implementation
		 */
		@Override
		public boolean hasNext() {
			return current <= range.to;
		}

		/**
		 * {@link Iterator#next} simple implementation
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return current++;
		}

		/**
		 * {@link Iterator#remove} simple implementation
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
