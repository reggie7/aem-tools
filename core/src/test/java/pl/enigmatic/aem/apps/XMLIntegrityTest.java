package pl.enigmatic.aem.apps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import pl.enigmatic.aem.apps.core.XMLValidator;

/**
 * @author Radosław Wesołowski
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class XMLIntegrityTest extends XMLValidator {

	@Test
	public void testComponentsPackage() throws NoSuchFieldException {
		test("ui.apps");
	}

	@Test
	public void testContentPackage() throws NoSuchFieldException {
		test("ui.content");
	}

}