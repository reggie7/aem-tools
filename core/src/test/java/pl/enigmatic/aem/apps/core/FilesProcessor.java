package pl.enigmatic.aem.apps.core;

import java.io.File;
import java.io.FileFilter;

import pl.enigmatic.aem.TestingTools;

/**
 * A simple class meant to be a base for any test that wants to go through
 * files of the project and check their consistency to a certain set of rules.
 * 
 * @author Radoslaw Wesolowski
 *
 * @param <T> the processing result class
 */
public abstract class FilesProcessor <T> {

	/**
	 * file filter to accept directories only
	 */
	private final FileFilter directoriesFilter = new FileFilter() {

		/**
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		@Override
		public boolean accept(final File file) {
			return file.isDirectory();
		}
	};

	/**
	 * file filter to accept files only
	 */
	private final FileFilter filesFilter = new FileFilter() {

		/**
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		@Override
		public boolean accept(final File file) {
			return file.isFile();
		}
	};

	/**
	 * should this object read the files' content to pass it to
	 * {@link #accept(File, String)} method?
	 */
	private final boolean read;

	/**
	 * Default constructor
	 * @param read {@link #read}
	 */
	public FilesProcessor(boolean read) {
		super();
		this.read = read;
	}

	/**
	 * Checks whether the file should be accepted to be processed later by {@link #processFile(File, String, Object)}
	 * @param file the file to check
	 * @param content the file's content, if {@link #read} is true
	 * @return true, if we should process this file
	 */
	protected abstract boolean accept(final File file, final String content);

	/**
	 * Processes the files that got accepted by {@link #accept(File, String)}
	 * @param file the file to be processed
	 * @param content content the file's content, if {@link #read} is true
	 * @param param a wildcard param for implementations
	 */
	protected abstract void processFile(final File file, final String content, final T param);

	/**
	 * 
	 * @param folder the folder to check recursively
	 * @param param a token object to use in implementations
	 */
	private void processFolder(final File folder, final T param) {
		final File[] dirs = folder.listFiles(this.directoriesFilter);
		if (dirs == null) {
			return;
		}
		for (final File dir : dirs) {
			processFolder(dir, param);
		}
		
		final File[] files = folder.listFiles(this.filesFilter);
		if (files == null) {
			return;
		}
		for (final File file : files) {
			final String content = this.read ? TestingTools.readFileContent(file.getAbsolutePath()) : null;
			if (accept(file, content)) {
				processFile(file, content, param);
			}
		}
	}

	/**
	 * Validates the structure of files starting from <code>file</code>. Supplies
	 * the template method and relies on {@link #accept(File, String)} and
	 * {@link #processFile(File, String, Object)} implementations
	 * @param file the file/dir object to check recursively
	 * @param param an additional param object to handle in implementations
	 */
	public final T process(final File file, final T param) {
		if (file.isDirectory()) {
			processFolder(file, param);
		} else {
			processFile(file, null, param);
		}
		return param;
	}
}