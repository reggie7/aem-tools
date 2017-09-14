package pl.enigmatic.mobirise.aem.core;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.AccessDeniedException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFormatException;

import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.apache.sling.models.annotations.Model;

import pl.enigmatic.aem.ComponentModel;

/**
 * @author Radosław Wesołowski
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class GetUser extends ComponentModel {

	@Inject
	private String id;

	private User user;

	/**
	 * Default constructor
	 * @param request
	 */
	public GetUser(final SlingHttpServletRequest request) {
		super(request);
	}

	@PostConstruct
	protected void init() throws AccessDeniedException, UnsupportedRepositoryOperationException, RepositoryException {
		final Session session = request.getResourceResolver().adaptTo(Session.class);
		final UserManager um = AccessControlUtil.getUserManager(session);
		user = (User) um.getAuthorizable(id);
	}

	public User getUser() {
		return user;
	}

	public String getGivenName() throws ValueFormatException, IllegalStateException, RepositoryException {
		return user.getProperty("profile/givenName")[0].getString();
	}

	public String getFamilyName() throws ValueFormatException, IllegalStateException, RepositoryException {
		return user.getProperty("profile/familyName")[0].getString();
	}

}