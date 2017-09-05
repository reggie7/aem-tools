package pl.enigmatic.mobirise.aem.comps.contact;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import pl.enigmatic.tools.TemplateSubstitutor;

/**
 * After submit send an email to admin(s) and a copy to user
 * containing contact form inf. (name, email, phone and message)
 * and redirect user to redirect page
 *
 * @author Radosław Wesołowski
 * @author Payam Salman
 */
@Model(adaptables = Resource.class)
public class EmailModel {

	/**
	 * From email address
	 */
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String from;

	/**
	 * To email address
	 */
	@ValueMapValue
	@Default(values = {})
	private String[] to;

	/**
	 * Subject for email
	 */
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String subject;

	/**
	 * Template for email to send to user
	 */
	@ValueMapValue
	@Default(values = "${message}")
	private String template;

	/**
	 * Default constructor
	 *
	 * @throws IOException
	 */
	public EmailModel() {
		super();
	}

	/**
	 * Sets from email address
	 */
	public void setFrom(final String from) {
		this.from = from;
	}

	/**
	 * Sets to email address
	 */
	public void setTo(final String[] to) {
		this.to = to;
	}

	/**
	 * Sets subject for email
	 */
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	/**
	 * Sets template for email to send to user
	 */
	public void setTemplate(final String template) {
		this.template = template;
	}

	/**
	 * Gets from email address
	 *
	 * @return String subject
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Gets to email address
	 *
	 * @return String[] emails
	 */
	public String[] getTo() {
		return to;
	}

	/**
	 * Gets subject for email
	 *
	 * @return String email
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Gets template for email to send to user
	 *
	 * @return String Template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * builds an email
	 * @throws EmailException
	 * @throws AddressException
	 */
	public HtmlEmail buildHtmlEmail(final TemplateSubstitutor substitutor) throws EmailException, AddressException {
		final List<InternetAddress> recipients = new LinkedList<>();
		final HtmlEmail email = new HtmlEmail();
		email.setCharset("UTF-8");
		email.setSubject(subject);
		email.setFrom(from);
		for (final String u : to) {
			recipients.add(new InternetAddress(u));
		}
		email.setTo(recipients);
		email.setHtmlMsg(substitutor.substitute(template));
		return email;
	}

}