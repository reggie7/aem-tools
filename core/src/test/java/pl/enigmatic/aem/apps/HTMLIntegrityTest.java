package pl.enigmatic.aem.apps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import pl.enigmatic.aem.apps.core.HTMLValidator;

/**
 * @author Radosław Wesołowski
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HTMLIntegrityTest extends HTMLValidator {

	@Test
	public void testComponentsPackage() throws NoSuchFieldException {
		test("ui.apps");
	}

}