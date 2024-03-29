package pl.enigmatic.aem.labels.inherited;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import pl.enigmatic.aem.ComponentModel;
import pl.enigmatic.tools.TemplateSubstitutor;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class InheritedLabelsSubstitutor extends ComponentModel {

	@Inject
	private String text;

	private TemplateSubstitutor substitutions;

	/**
	 * Default constructor
	 * @param request
	 */
	public InheritedLabelsSubstitutor(final SlingHttpServletRequest request) {
		super(request);
		substitutions = (TemplateSubstitutor) request.getAttribute(getClass().getName());
	}

	@PostConstruct
	protected void init() {
		if (substitutions == null) {
			substitutions = new TemplateSubstitutor();
			final InheritedLabelsMap labels = new InheritedLabelsMap(request.getResource());
			for (final String placeholder : labels.keySet()) {
				final String value = labels.get(placeholder, String.class);
				if (StringUtils.isNotBlank(value)) {
					substitutions.put(placeholder, value);
				}
			}

			request.setAttribute(getClass().getName(), substitutions);
		}

		text = substitutions.substitute(text);
	}

	public String getText() {
		return text;
	}

}
