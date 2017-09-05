package pl.enigmatic.mobirise.aem.comps.contact;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.http.client.utils.URIBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

import pl.enigmatic.tools.TemplateSubstitutor;

/**
 * After submit send an email to admin(s) and a copy to user
 * containing contact form inf. (name, email, phone and message)
 * and redirect user to redirect page
 *
 * @author Radosław Wesołowski
 * @author Payam Salman
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ContactUs {

	/**
	 * Logger used to log warning when exception occurs
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactUs.class);

	/**
	 * Sling http servlet request
	 */
	private final SlingHttpServletRequest request;

	/**
	 * Response object
	 */
	@Inject
	private SlingHttpServletResponse response;

	@Inject
	@ChildResource
	private EmailModel inquiry;

	@Inject
	@ChildResource
	private EmailModel autoreply;

	/**
	 * Message gateway service
	 */
	@OSGiService
	private MessageGatewayService messageGatewayService;

	/**
	 * Redirect path
	 */
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String successPage;

	/**
	 * Redirect path
	 */
	@ValueMapValue
	@Default(values = StringUtils.EMPTY)
	private String failPage;

	/**
	 * Default constructor
	 *
	 * @throws IOException
	 */
	public ContactUs(final SlingHttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
	 * Gets redirect url
	 *
	 * @return String redirect url
	 */
	private String buildURL(final String path) {
		final URIBuilder uri = new URIBuilder();
		uri.setHost(request.getServerName());
		uri.setPort(request.getServerPort());
		uri.setScheme(request.getScheme());
		uri.setPath(path + ".html");
		return uri.toString();
	}

	/**
	 * Method to send an email and redirect to the next page
	 */
	@PostConstruct
	protected void send() throws IOException {
		String redirect = failPage;
		final MessageGateway<Email> messageGateway = messageGatewayService.getGateway(Email.class);
		if(messageGateway != null) {
			final TemplateSubstitutor substitutor = new TemplateSubstitutor();
			substitutor.put("name", request.getParameter("name"));
			final String email = request.getParameter("email");
			substitutor.put("email", email);
			substitutor.put("phone", request.getParameter("phone"));
			final String subject = request.getParameter("subject");
			substitutor.put("subject", subject);
			substitutor.put("message", request.getParameter("message"));
	
			try {
				inquiry.setSubject(subject);
				inquiry.setFrom(email);
				final HtmlEmail fromUser = inquiry.buildHtmlEmail(substitutor);

				autoreply.setTo(new String[]{ email });
				messageGateway.send(fromUser);
				final HtmlEmail toUser = autoreply.buildHtmlEmail(substitutor);
				messageGateway.send(toUser);

				redirect = successPage;
			} catch (EmailException | AddressException e) {
				LOGGER.error("Error sending e-mail.", e);
			}
		}
		this.response.sendRedirect(this.buildURL(redirect));
	}

}