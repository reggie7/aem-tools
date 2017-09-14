package pl.enigmatic.aem.dialog;

import java.util.Collection;

public interface GetOptions {

	Collection<Entry> getOptions();

	class Entry {

		private final String value;
		private final String text;

		public Entry(final String value, final String text) {
			this.value = value;
			this.text = text;
		}

		public String getValue() {
			return value;
		}

		public String getText() {
			return text;
		}
	}
}