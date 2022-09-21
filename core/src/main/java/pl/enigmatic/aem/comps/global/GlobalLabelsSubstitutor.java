package pl.enigmatic.aem.comps.global;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import pl.enigmatic.aem.ComponentModel;
import pl.enigmatic.tools.TemplateSubstitutor;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class GlobalLabelsSubstitutor extends ComponentModel {

	@Inject
	private String text;

	private TemplateSubstitutor substitutions;

	/**
	 * Default constructor
	 * @param request
	 */
	public GlobalLabelsSubstitutor(final SlingHttpServletRequest request) {
		super(request);
		substitutions = (TemplateSubstitutor) request.getAttribute(getClass().getName());
	}

	@PostConstruct
	protected void init() {
		if (substitutions == null) {
			substitutions = new TemplateSubstitutor();
			final GlobalLabels labels = new GlobalLabels(request.getResource());
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
