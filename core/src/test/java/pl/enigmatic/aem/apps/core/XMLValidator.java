package pl.enigmatic.aem.apps.core;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.xerces.parsers.DOMParser;
import org.junit.Assert;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Joiner;

/**
 * XML Validation tester class
 * @author Radoslaw Wesolowski
 *
 */
public abstract class XMLValidator extends FilesProcessor<Map<File, Throwable>> {

	/**
	 * xml extension
	 */
	private static final String XML = "xml";
	/**
	 * Joiner joining on {@link java.io.File} separator.
	 */
	protected static final Joiner PATH_JOINER = Joiner.on(File.separator).skipNulls();
	/**
	 * DOM Parser
	 */
	private final DOMParser parser = new DOMParser();

	/**
	 * file extension to be accepted
	 */
	private final String extension;

	/**
	 * base project's path
	 */
	private final String basePath;
	/**
	 * subproject's path to the directory that needs to be checked
	 */
	protected String contentSuffix = PATH_JOINER.join("src", "main", "content", "jcr_root");

	/**
	 * Constructor that allows one to set the extension
	 * @param extension
	 */
	public XMLValidator(final String extension) {
		super(true);
		this.extension = extension;
		String current = System.getProperty("user.dir");
		current = StringUtils.substringBeforeLast(current, File.separator);
		this.basePath = current;
	}

	/**
	 * Default constructor for xml files
	 */
	public XMLValidator() {
		this(XML);
	}

	/**
	 * Optionally overriden method that allows one to prepare a file before getting checked
	 * @param content the file's contents
	 * @return transformed content
	 */
	protected String transform(final String content) {
		return content;
	}

	/**
	 * Accepts only files with the {@link #extension}
	 * @see com.bmw.aems2.digitals2.apps.FilesProcessor#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(final File file, final String content) {
		final String current = FilenameUtils.getExtension(file.getName());
		return file.isFile() && StringUtils.equalsIgnoreCase(this.extension, current);
	}

	/**
	 * Implementation that gathers errors from processing an xml file
	 * @see com.bmw.aems2.digitals2.apps.FilesProcessor#processFile(java.io.File, java.lang.String, java.lang.Object)
	 */
	@Override
	protected void processFile(final File file, final String content, final Map<File, Throwable> errors) {
		if (StringUtils.isNotBlank(content)) {
			String transform = transform(content);
			try {
				final InputSource inputSource = new InputSource();
				inputSource.setCharacterStream(new StringReader(transform));
				this.parser.parse(inputSource);
			} catch (SAXException | IOException e) {
				errors.put(file, e);
			}
		}
	}

	/**
	 * Validates the structure of files starting from <code>file</code>
	 * @param file
	 * @return the map of exceptions that come from processing the file
	 */
	public final Map<File, Throwable> validate(final File file) {
		return process(file, new HashMap<File, Throwable>());
	}

	/**
	 * Creates a simple String representation of what was found wrong with the files
	 * @param path initial path
	 * @param errors errors found
	 * @return nice String representation of the errors
	 */
	protected static String toString(final String path, final Map<File, Throwable> errors) {
		final StringBuilder s = new StringBuilder();
		for (final Entry<File, Throwable> e : errors.entrySet()) {
			s.append('\n');
			s.append(e.getKey().getAbsolutePath().substring(1 + path.length()));
			s.append(": ");
			s.append(e.getValue().getMessage());
		}
		s.append('\n');
		return s.toString();
	}

	/**
	 * Tests the given project with the given tolerance level
	 * @param tolerance number of errors we allow the project to have
	 * @param relative project path to the project
	 */
	protected void test(final int tolerance, final String... project) {
		final String path = PATH_JOINER.join(this.basePath, PATH_JOINER.join(project), this.contentSuffix);
		final Map<File, Throwable> errors = validate(new File(path));
		Assert.assertTrue(toString(path, errors), errors.size() <= tolerance);
	}

	/**
	 * Tests the given project with the level 0
	 * @param project relative project path to the project
	 * @see XMLValidator#test(int, String...)
	 */
	protected void test(final String... project) {
		test(0, project);
	}
}